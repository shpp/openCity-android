package me.kowo.opencity.activities;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kowo.opencity.R;
import me.kowo.opencity.adapters.RecyclerSuggestionAdapter;
import me.kowo.opencity.app.App;
import me.kowo.opencity.dialogs.LowTrolleyBusDialog;
import me.kowo.opencity.dialogs.SocialTaxiDialog;
import me.kowo.opencity.eventbus.Event;
import me.kowo.opencity.eventbus.EventMessage;
import me.kowo.opencity.models.Category;
import me.kowo.opencity.models.CategoryPlaces;
import me.kowo.opencity.models.Place;
import me.kowo.opencity.providers.DataProvider;
import me.kowo.opencity.utils.Utils;

import static me.kowo.opencity.constants.Constants.MIN_LEN_TO_QUERY;
import static me.kowo.opencity.constants.Constants.PREF_TAG;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private List<Category> mCategoryList;
    private Menu navigationViewMenu;
    private RecyclerView suggestionRecycler;
    private SearchView search;
    private RecyclerSuggestionAdapter recyclerSuggestionAdapter;
    private HashMap<String, Integer> ids;
    private boolean isMenuCreated = false;
    final public static String TAG = "MainActivity";

    DialogFragment socialTaxi, lowTrolleyBus;

    @Inject
    DataProvider dataProvider;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplicationContext()).getAppComponent().inject(this);
        ButterKnife.bind(this);
        setUpBarAndDrawerLayout();
        getCategories();
    }





    public void setUpBarAndDrawerLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationViewMenu = mNavigationView.getMenu();



        initSearch(toolbar);

        toggle.syncState();
        //mNavigationView.setNavigationItemSelectedListener(this);

        socialTaxi = new SocialTaxiDialog();
        lowTrolleyBus = new LowTrolleyBusDialog();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.contact_us:
                        Toast.makeText(MainActivity.this, "Contact Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.social_taxi:
                        socialTaxi.show(getFragmentManager(), "socialTaxi");
                        return true;
                    case R.id.low_trolley_bus:
                        lowTrolleyBus.show(getFragmentManager(), "lowTrolleyBus");
                        return true;
                    default:
                        return true;
                }

            }
        });

        suggestionRecycler = (RecyclerView) findViewById(R.id.suggestion_recycler);

        mDrawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getSelectedCategoriesPlaces();
                Utils.hideKeyboard(MainActivity.this);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }



    public void setUpSuggestionAdapter(ArrayList<Place> places) {
        if (recyclerSuggestionAdapter == null) {
            recyclerSuggestionAdapter = new RecyclerSuggestionAdapter(this);
            suggestionRecycler.setAdapter(recyclerSuggestionAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            suggestionRecycler.setLayoutManager(layoutManager);
            recyclerSuggestionAdapter.setPlaces(places);
            recyclerSuggestionAdapter.notifyDataSetChanged();
        } else {
            recyclerSuggestionAdapter.setPlaces(places);
            recyclerSuggestionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    public void initSearch(Toolbar toolbar) {
        search = (SearchView) toolbar.findViewById(R.id.search_view);
        search.setOnClickListener(v -> {
            search.setIconified(false);
        });
        search.setOnCloseListener(() -> {
            if (recyclerSuggestionAdapter != null) {
                recyclerSuggestionAdapter.clear();
            }
            return false;
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > MIN_LEN_TO_QUERY) {
                    dataProvider.getSearchPlaces(newText);
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (!search.isIconified()) {
            search.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Subscribe
    public void onEvent(Event event) {
        EventMessage message = event.getEventMessage();
        switch (message) {
            case ON_PLACES_RECEIVED:
                break;
            case ON_CATEGORIES_RECEIVED:
                onCategoriesReceived((List<Category>) event.getTransferObject());
                break;
            case ON_SEARCH_PLACES_RECEIVED:
                onSearchPlacedReceived(event);
                break;
            case ON_INTERNET_CONNECTED:
                onInternetConnected();
                break;
        }
    }

    public void onSearchPlacedReceived(Event event) {
        ArrayList<Place> places = ((CategoryPlaces) event.getTransferObject()).getPlaces();
        if (places.size() == 0) {
            places.add(createNothingFindPlace());
        }
        setUpSuggestionAdapter(places);
    }

    private void onInternetConnected() {
        if (!isMenuCreated) {
            getCategories();
        }
    }


    public void onCategoriesReceived(List<Category> categories) {
        this.mCategoryList = categories;
        saveCategories(categories);
        createMenu(categories);
    }

    public void saveCategories(List<Category> categories) {
        Log.v(TAG, "Categories saved in cache");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        editor.putString(PREF_TAG, json);
        editor.apply();
    }

    public List<Category> getCacheCategories() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(PREF_TAG, null);
        Type type = new TypeToken<List<Category>>() {
        }.getType();
        List<Category> categories = gson.fromJson(json, type);
        return categories;
    }

    public Place createNothingFindPlace() {
        Place place = new Place();
        place.setShortName("По запиту нічого не знайдено");
        return place;
    }

    public void getCategories() {
        mCategoryList = new ArrayList<>();
        List<Category> categories = getCacheCategories();
        if (categories != null && categories.size() > 0 && !categories.equals("")) {
            Log.v(TAG, "Categories get from cache");
            createMenu(categories);
            mCategoryList = categories;
        } else {
            Log.v(TAG, "Categories get from request server");
            dataProvider.getCategories();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // dataProvider.getCategoryPlaces(ids.get(item.getTitle()));
        // mDrawer.closeDrawer(Gravity.LEFT);
        CheckBox checkBox = (CheckBox) item.getActionView();
        uncheckAllCategories();
        if (checkBox != null)
            checkBox.setChecked(true);
        mDrawer.closeDrawer(Gravity.LEFT);
        return true;
    }

    public void uncheckAllCategories() {
        for (int i = 0; i < mCategoryList.size(); i++) {
            MenuItem currentItem = mNavigationView.getMenu().getItem(i);
            ((CheckBox) currentItem.getActionView()).setChecked(false);
        }
    }

    public void getSelectedCategoriesPlaces() {
        int[] selectedIds = new int[mCategoryList.size()];
        for (int i = 0, j = 0; i < mCategoryList.size(); i++) {
            MenuItem currentItem = mNavigationView.getMenu().getItem(i);
            CheckBox checkBox = (CheckBox) currentItem.getActionView();
            if (checkBox.isChecked()) {
                selectedIds[j] = ids.get(currentItem.getTitle().toString());
                j++;
            }
        }
        dataProvider.getCategoryPlaces(selectedIds);
    }

    public void createMenu(List<Category> categories) {
        if (navigationViewMenu == null && categories == null || isMenuCreated) {
            return;
        }
        ids = new HashMap<>();
        for (int i = 0; i < categories.size(); i++) {
            String menuItem = categories.get(i).getName();
            int id = categories.get(i).getId();
            menuItem = menuItem.substring(0, 1).toUpperCase() + menuItem.substring(1).toLowerCase();
            ids.put(menuItem, id);
            addMenuItem(navigationViewMenu, menuItem, false);
        }
        isMenuCreated = true;
    }


    public MenuItem addMenuItem(Menu menu, String itemName, boolean checked) {
        return menu.add(R.id.places_list, Menu.NONE, 0, itemName).setCheckable(true)
                .setChecked(checked).setActionView(R.layout.checkbox);
    }


}
