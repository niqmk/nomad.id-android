package com.allega.nomad.service.rest.app;


import android.content.Context;

import com.allega.nomad.constant.Constant;
import com.allega.nomad.constant.FlavorsConstant;
import com.allega.nomad.storage.PreferenceProvider;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import io.realm.RealmObject;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by imnotpro on 5/4/15.
 */
public class AppRestController implements RequestInterceptor, ErrorHandler {

    private static AppRestController instance = new AppRestController();
    private AppRestService appRestService;
    private PreferenceProvider preferenceProvider;
    private Context context;

    protected AppRestController() {

    }

    public static AppRestController getInstance() {
        return instance;
    }

    public static AppRestService getAppRestService() {
        if (instance.appRestService == null)
            throw new IllegalStateException("Haven't create rest service first");
        return instance.appRestService;
    }

    public void generateRest(Context context) {
        Gson gson = new GsonBuilder().setDateFormat(Constant.FORMAT_DATE)
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("Rest Service"))
                .setEndpoint(FlavorsConstant.ROOT_URL)
                .setRequestInterceptor(this)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(okHttpClient))
                .setErrorHandler(this)
                .build();
        appRestService = restAdapter.create(AppRestService.class);
        this.context = context;
        preferenceProvider = new PreferenceProvider(context);
    }

    @Override
    public void intercept(RequestFacade request) {
        String authToken = preferenceProvider.getToken();
        if (authToken != null) {
            request.addHeader("auth_token", authToken);
        }
        request.addHeader("Cache-Control", "no-cache");
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        if (cause.getResponse() != null) {
            Response response = cause.getResponse();
            switch (response.getStatus()) {
                case 401:
//                    HomeActivity.startActivityToLogin(context);
                    break;
            }
        }
        return cause;
    }
}
