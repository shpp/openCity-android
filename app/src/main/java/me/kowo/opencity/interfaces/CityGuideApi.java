package me.kowo.opencity.interfaces;

import java.util.List;

import me.kowo.opencity.models.Category;
import me.kowo.opencity.models.CategoryPlaces;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;


public interface CityGuideApi {
    @GET("/api/v1/getcategories")
    Observable<List<Category>> getCategories();

    @GET
    Observable<CategoryPlaces> getPlaces(@Url String categoriesIds);

    @GET("/api/v1/getinfo")
    Observable<ResponseBody> getInfo(@Query("id") int id);

    @GET("/api/v1/getparameters")
    Observable<ResponseBody> getParameters();

    @GET("/search?")
    Observable<CategoryPlaces> getSearchPlaces(@Query("val") String text);

}
