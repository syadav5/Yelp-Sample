package com.rbc.yelp.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.rbc.yelp.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class YelpRetrofit {

    public static final String BASE_URL = "https://api.yelp.com";

    public Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new ApiKeyInterceptor())
                       // .addInterceptor(new LoggingInterceptor())
                        .build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static class ApiKeyInterceptor implements Interceptor {

        @Override
        public @NonNull Response intercept(Chain chain) throws IOException {
            return chain.proceed(chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + BuildConfig.API_KEY)
                    .build());
        }
    }

    private static class LoggingInterceptor implements Interceptor {

        public static final String TAG = LoggingInterceptor.class.getSimpleName();

        @Override
        public @NonNull Response intercept(Chain chain) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append(chain.request().headers()).append("\n").append(chain.request().body());
            //System.out.println("Request Data: "+sb.toString());
            Timber.d(sb.toString());
            return chain.proceed(chain.request());
        }
    }
}
