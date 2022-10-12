package com.rental_tool.api;

import com.rental_tool.service.ApiService;

import org.jetbrains.annotations.NotNull;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {

    public static final ApiService API_SERVICE = getRetrofit().create(ApiService.class);

    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new SessionCookieJar())
                .addInterceptor(httpLoggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080")
                .client(okHttpClient)
                .build();
    }

    public static ApiService getApiService(){
        return API_SERVICE;
    }

    private static class SessionCookieJar implements CookieJar {

        private List<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (url.encodedPath().endsWith("login")) {
                this.cookies = new ArrayList<>(cookies);
            }
        }

        @NotNull
        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (!url.encodedPath().endsWith("login") && cookies != null) {
                return cookies;
            }
            return Collections.emptyList();
        }
    }
}
