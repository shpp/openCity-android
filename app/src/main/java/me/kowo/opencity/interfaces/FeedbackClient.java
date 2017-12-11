package me.kowo.opencity.interfaces;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface FeedbackClient {
    @Multipart
    @POST("messages/send")
    public Call<ResponseBody> sendData(@Part("email") RequestBody email,
                                       @Part("text") RequestBody text);
}

