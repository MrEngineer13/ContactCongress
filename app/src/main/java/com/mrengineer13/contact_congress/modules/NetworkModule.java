package com.mrengineer13.contact_congress.modules;

import com.mrengineer13.contact_congress.BuildConfig;
import com.mrengineer13.contact_congress.data.api.ApiService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jon on 6/26/16.
 */
@Module
public class NetworkModule {

    private static final String BASE_URL = "https://congress.api.sunlightfoundation.com";
    private static final String API_KEY = "apikey";

    @Provides
    @Singleton
    public OkHttpClient provideOkhttp(Interceptor interceptor) {

        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    public Interceptor provideInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(API_KEY, BuildConfig.API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public ApiService provideApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
