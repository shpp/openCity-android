package me.kowo.opencity.providers;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.greenrobot.eventbus.EventBus;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import me.kowo.opencity.eventbus.Event;
import me.kowo.opencity.eventbus.EventMessage;
import me.kowo.opencity.interfaces.CityGuideApi;
import me.kowo.opencity.models.Category;
import me.kowo.opencity.models.CategoryPlaces;
import me.kowo.opencity.models.Info;
import me.kowo.opencity.models.Parameter;
import me.kowo.opencity.utils.Utils;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static me.kowo.opencity.constants.Constants.ACCESS;
import static me.kowo.opencity.constants.Constants.GET_PLACES_URL;
import static me.kowo.opencity.constants.Constants.PARAM;

public class DataProvider {

    CityGuideApi cityGuideApi;
    private String LOG = "dataProvider";
    private static HashMap<String, Parameter> parameters;

    public DataProvider(CityGuideApi cityGuideApi) {
        this.cityGuideApi = cityGuideApi;
    }


    public void getCategoryPlaces(int... ids) {

        if (ids.length == 0)
            return;
        cityGuideApi.getPlaces(GET_PLACES_URL + Utils.getCategoriesById(ids))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoryPlaces>() {
                    @Override
                    public void onNext(CategoryPlaces categoryPlaces) {
                        EventBus.getDefault().post(new Event()
                                .setTransferObject(categoryPlaces)
                                .setEventMessage(EventMessage.ON_PLACES_RECEIVED));
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(LOG, "OnCompleted getCategoryPlaces");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(LOG, "OnError" + e.toString());
                    }
                });
    }

    public void getSearchPlaces(String searchText) {
        cityGuideApi.getSearchPlaces(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoryPlaces>() {
                    @Override
                    public void onNext(CategoryPlaces categoryPlaces) {
                        Log.v(LOG, "Search Places received");
                        EventBus.getDefault().post(new Event()
                                .setTransferObject(categoryPlaces)
                                .setEventMessage(EventMessage.ON_SEARCH_PLACES_RECEIVED));
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(LOG, "OnCompleted getSearchPlaces");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(LOG, "OnError" + e.toString());
                    }
                });

    }

    public void getPlaceInfo(int id) {
        cityGuideApi.getInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        setDetailedInfo(responseBody);
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(LOG, "OnCompleted getPlacesInfo");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(LOG, "OnError" + e.toString());
                    }

                });
    }

    public void getCategories() {
        cityGuideApi.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public void onNext(List<Category> categories) {
                        EventBus.getDefault().post(new Event()
                                .setTransferObject(categories)
                                .setEventMessage(EventMessage.ON_CATEGORIES_RECEIVED));
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(LOG, "OnCompleted getCategories");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(LOG, "OnError getCategories" + e.toString());
                    }
                });
    }

    public void getParameters() {
        cityGuideApi.getParameters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            createParameters(responseBody.toString());
                        } catch (Exception e) {
                            Log.v(LOG, e.toString());
                        }
                    }

                    @Override
                    public void onCompleted() {
                        Log.v(LOG, "OnCompleted getPlacesInfo");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(LOG, "OnError" + e.toString());
                    }

                });
    }

    public void createParameters(String response) {
        try {
            JsonReader reader = new JsonReader(new StringReader(response));
            reader.setLenient(true);
            Type mapType = new TypeToken<HashMap<Integer, Parameter>>() {
            }.getType();
            parameters = new Gson().fromJson(reader, mapType);
        } catch (Exception e) {
            Log.v(LOG, "Error: " + e);
        }

    }

    public void setDetailedInfo(ResponseBody responseBody) {
        try {
            String body = responseBody.string();
            JsonElement root = new JsonParser().parse(body);
            String hashmap = root.getAsJsonObject().get(PARAM).getAsJsonObject().toString();
            Type mapType = new TypeToken<HashMap<Integer, String>>() {
            }.getType();
            HashMap<Integer, String> hashMap = new Gson().fromJson(hashmap, mapType);
            Type listType = new TypeToken<List<Integer>>() {
            }.getType();
            String accessebilities = root.getAsJsonObject().get(ACCESS).getAsJsonArray().toString();
            List<Integer> listAccess = new Gson().fromJson(accessebilities, listType);
            Info info = new Info();
            info.setAccessibilities(listAccess).setParameters(hashMap);
            EventBus.getDefault().post(new Event()
                    .setTransferObject(info)
                    .setEventMessage(EventMessage.ON_DETAILED_INFO_RECEIVED));
        } catch (Exception e) {
            Log.v(LOG, e.toString());
        }
    }
}
