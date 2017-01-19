package com.example.jon_thornburg.smalltrgandroid.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jon_thornburg on 1/11/17.
 */

public class HttpClient {

    public final static String TAG = HttpClient.class.getSimpleName();

    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.richards.com/locator/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRoomsClient() {
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()
                    .baseUrl("https://api.robinpowered.com/v1.0/free-busy/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(geturlInterceptor())
                    .build();
        }
        return retrofit1;
    }

    public static OkHttpClient getClientWithHeaders() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("Content-type", "application/json")

                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        return client;

    }

    private static OkHttpClient geturlInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        OkHttpClient again = httpClient.build();
        return again;
    }
}
