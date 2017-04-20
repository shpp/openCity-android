package me.kowo.opencity.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import me.kowo.opencity.R;
import me.kowo.opencity.app.App;
import me.kowo.opencity.eventbus.Event;
import me.kowo.opencity.eventbus.EventMessage;
import me.kowo.opencity.models.CategoryPlaces;
import me.kowo.opencity.models.Info;
import me.kowo.opencity.models.Parameter;
import me.kowo.opencity.models.Place;
import me.kowo.opencity.providers.DataProvider;
import me.kowo.opencity.utils.Utils;

import static me.kowo.opencity.constants.Constants.BASE_URL;
import static me.kowo.opencity.constants.Constants.FAKE_PARAMETERS;
import static me.kowo.opencity.constants.Constants.LINK_EMAIL;
import static me.kowo.opencity.constants.Constants.LINK_PHONE;
import static me.kowo.opencity.constants.Constants.LINK_WEB;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Inject
    DataProvider dataProvider;

    final private double LAT = 48.509142;
    final private double LON = 32.262258;

    public HashMap<Integer, Parameter> parameters;
    private GoogleMap mMap;
    private SlidingUpPanelLayout slidingPanel;
    private RelativeLayout bottomSheet;
    private ArrayList<Place> places;
    private TextView name;
    private TextView address;
    private LinearLayout itemContainer;
    private ImageView rampIcon;
    private ImageView callButtonIcon;
    private int lastIndex;
    private String LOG = "MapsFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        App app = (App) getActivity().getApplicationContext();
        app.getAppComponent().inject(this);
        return view;
    }

    public void findViews() {
        name = (TextView) bottomSheet.findViewById(R.id.place_name);
        rampIcon = (ImageView) bottomSheet.findViewById(R.id.icon_ramp);
        callButtonIcon = (ImageView) bottomSheet.findViewById(R.id.icon_call_button);
        itemContainer = (LinearLayout) bottomSheet.findViewById(R.id.bottom_sheet_item_container);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onSearchPressed(String location) {
        List<Address> addresses = null;
        if (!location.equals("")) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addresses = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Address address = addresses.get(0);
        if (address != null) {
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("I love Kirovograd"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setCurrentLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
        // Add a marker in Sydney and move the camera
        setCameraToCityView();
        createParameters();
        setBottomSheet();
    }

    public void setCameraToCityView() {
        LatLng location = new LatLng(LAT, LON);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
    }

    public void setCurrentLocationEnabled(boolean enabled) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(enabled);
    }

    private void setBottomSheet() {
        bottomSheet = (RelativeLayout) getActivity().findViewById(R.id.bottom_sheet_container);
        findViews();
        name = (TextView) bottomSheet.findViewById(R.id.place_name);
        address = (TextView) bottomSheet.findViewById(R.id.address);
        slidingPanel = (SlidingUpPanelLayout) getActivity().findViewById(R.id.sliding_panel);
        slidingPanel.setDragView(bottomSheet);
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        slidingPanel.setFadeOnClickListener(v -> {
            if (slidingPanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        bottomSheet.setOnClickListener(v -> {
            if (slidingPanel.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED)
                slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        });
    }

    @Subscribe
    public void onEvent(Event event) {
        EventMessage message = event.getEventMessage();
        switch (message) {
            case GET_CURRENT_LOCATION:

                break;
            case ON_QUERY_TEXT_CHANGE:

                break;
            case SELECT_ITEM:
                onSearchItemSelected(event);
                break;
            case ON_PLACES_RECEIVED:
                setCameraToCityView();
                setMarkersOnMap(event);
                break;
            case ON_DETAILED_INFO_RECEIVED:
                setDetailedInfo(event);
                break;
        }
    }

    public void onSearchItemSelected(Event event) {
        if (event.getTransferObject() != null) {
            Place place = (Place) event.getTransferObject();
            Double lat = Double.parseDouble(place.getMap_lat());
            Double lon = Double.parseDouble(place.getMap_lng());
            goToPosition(new LatLng(lat, lon));
            addSearchPlace(place);
            Utils.hideKeyboard(getActivity());
        }
    }


    protected void goToPosition(LatLng location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f));
    }

    private void setDetailedInfo(Event event) {
        Info info = (Info) event.getTransferObject();
        setHeader(info);
        setSheetList(info);
    }

    public void setHeader(Info info) {
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        Place place = places.get(lastIndex);
        String addressText = getActivity().getResources().getString(R.string.street_prefix) + place.getStreet() + " " + place.getNumber();
        if (name == null) {
            return;
        }
        if (place.getShortName() == null || place.getShortName().equals("")) {
            name.setText(place.getName());
        } else {
            name.setText(place.getShortName());
        }

        address.setText(addressText);
        setAccessibility(info);
    }

    public void setSheetList(Info info) {
        ArrayList<Integer> sortedKeys = new ArrayList<>(info.getParameters().keySet());
        Collections.sort(sortedKeys);
        itemContainer.removeAllViews();
        for (Integer i : sortedKeys) {
            Parameter parameter = parameters.get(i);
            createView(parameter.icon, info.getParameters().get(i), parameter.type);
        }
    }

    public void setAccessibility(Info info) {
        Utils.setVisibilities(View.GONE, rampIcon, callButtonIcon);
        for (Integer i : info.getAccessibilities()) {
            if (i == 1) {
                rampIcon.setVisibility(View.VISIBLE);
            } else if (i == 2) {
                callButtonIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    public void createView(String iconUrl, String text, String link) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_item, itemContainer, false);
        ImageView icon = (ImageView) view.findViewById(R.id.item_icon);
        TextView itemText = (TextView) view.findViewById(R.id.item_text);
        itemText.setAutoLinkMask(Linkify.WEB_URLS);
        Picasso.with(getContext()).load(BASE_URL + iconUrl).into(icon);
        itemText.setAutoLinkMask(findIntAutoLinkMask(link));
        itemText.setText(text);
        itemContainer.addView(view);
    }

    public int findIntAutoLinkMask(String link) {
        if (link.equals(LINK_PHONE)) {
            return Linkify.PHONE_NUMBERS;
        } else if (link.equals(LINK_EMAIL)) {
            return Linkify.EMAIL_ADDRESSES;
        } else if (link.equals(LINK_WEB)) {
            return Linkify.WEB_URLS;
        } else {
            return 0;
        }
    }

    private void setMarkersOnMap(Event event) {
        places = ((CategoryPlaces) event.getTransferObject()).getPlaces();
        mMap.clear();
        for (int i = 0; i < places.size(); i++) {
            setMarker(places.get(i), i);
        }
    }

    private void addSearchPlace(Place place) {
        if (places == null) {
            places = new ArrayList<>();
        }
        places.add(place);
        setMarker(place, places.size() - 1);
    }

    public void setMarker(Place place, int tag) {
        if (place.getMap_lat() == null && place.getMap_lng() == null) {
            return;
        }
        if (place.getAcc_cnt() == 0) {//Nothing available
            createMarker(tag, BitmapDescriptorFactory.HUE_RED, place);

        }
        if (place.getAcc_cnt() == 1) {//One of two is available
            createMarker(tag, BitmapDescriptorFactory.HUE_YELLOW, place);

        }
        if (place.getAcc_cnt() == 2) {//All available
            createMarker(tag, BitmapDescriptorFactory.HUE_GREEN, place);

        }
    }

    public void createMarker(int i, float color, Place place) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(place.getMap_lat()), Double.parseDouble(place.getMap_lng()))));
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(color));
        marker.setTag(i);
    }

    public void createParameters() {
        try {
            JsonReader reader = new JsonReader(new StringReader(FAKE_PARAMETERS));
            reader.setLenient(true);
            Type mapType = new TypeToken<HashMap<Integer, Parameter>>() {
            }.getType();
            parameters = new Gson().fromJson(reader, mapType);
        } catch (Exception e) {
            Log.v(LOG, "Error: " + e);
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Utils.hideKeyboard(getActivity());
        fillBottomSheet(marker);
        return true;
    }

    public void fillBottomSheet(Marker marker) {
        if (places == null) {
            return;
        }
        lastIndex = (int) marker.getTag();
        if (lastIndex >= places.size()) {
            return;
        }
        dataProvider.getPlaceInfo(places.get(lastIndex).getId());
    }
}
