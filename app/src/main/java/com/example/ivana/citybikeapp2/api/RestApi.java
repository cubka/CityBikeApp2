package com.example.ivana.citybikeapp2.api;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ivana.citybikeapp2.BikeCompanyList;
import com.example.ivana.citybikeapp2.BuildConfig;
import com.example.ivana.citybikeapp2.models.NetworksModel;
import com.example.ivana.citybikeapp2.other.PreferencesManager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class RestApi {

    private Context context;




    public RestApi(Context context) {
        this.context = context;
    }


    public Retrofit getRetrofitInstance() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(OFFLINE_INTERCEPTOR)
                .addNetworkInterceptor(ONLINE_INTERCEPTOR)
                .cache(provideCache())
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private Cache provideCache()
    {
        Cache cache = null;
        try
        {
            cache = new Cache( new File( context != null ? context.getCacheDir() : null, "responses" ),
                    10 * 1024 * 1024 ); // 10 MB
        }
        catch (Exception e)
        {
            Log.e( TAG, "Could not create Cache!" );
        }
        return cache;
    }

    Interceptor httpLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder().build();
            long t1 = System.nanoTime();
            Log.d("Retrofit", String.format("Sending request %s on %s%n%s", request.url(), chain
                    .connection(), request.headers()) + " Params " + bodyToString(request));
            Response response = chain.proceed(request);
            String responseBodyString = response.body().string();
            long t2 = System.nanoTime();
            Log.d("Retrofit", String.format("Received response for %s in %.1fms%n%s", response.request
                    ().url(), (t2 - t1) / 1e6d, response.headers()) + "Body " + responseBodyString);
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(),
                    responseBodyString)).build();
        }
    };

    Interceptor OFFLINE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!CheckNetwork.CheckInternetCon(context)) {
             int  maxStale = 60*60*24*28;  //4 weeks
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return chain.proceed(request);
         }
    };

    Interceptor ONLINE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            int maxAge;
            int a = PreferencesManager.getLimit(context);
            if (a != 0) {
              maxAge = 60 * a; // read from cache
            }
            else {
            maxAge = 60; }
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
    };

    private String bodyToString(final Request request) {
        try {
            final Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        } catch (NullPointerException e) {
            return "did not work nullPointer";
        } catch (OutOfMemoryError e) {
            return "OutOfMemoryError ";
        }
    }


    public ApiService request() {
        return getRetrofitInstance().create(ApiService.class);
    }

    public Call<NetworksModel> getNetList() {
        return request().getNetList();
    }

}


