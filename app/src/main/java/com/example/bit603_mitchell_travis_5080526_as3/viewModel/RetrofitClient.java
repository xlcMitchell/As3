package com.example.bit603_mitchell_travis_5080526_as3.viewModel;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Class used to build Retrofit object to send http request to youtube API
public class RetrofitClient {
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static Retrofit retrofit = null;

    // Returns a single shared Retrofit instance
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Create logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY); // Log headers and body

            // Create OkHttpClient and add interceptor
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            // Build Retrofit instance with logging client
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)  // Attach OkHttp client with logging
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
