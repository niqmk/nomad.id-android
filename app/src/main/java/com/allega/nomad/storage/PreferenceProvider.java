package com.allega.nomad.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.allega.nomad.constant.Constant;
import com.allega.nomad.entity.Member;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by imnotpro on 6/6/15.
 */
public class PreferenceProvider {
    private static final String PREF_NAME = "NOMAD_PREFERENCE";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_MEMBER = "member";
    private static final String KEY_RECENTLY_SEARCH = "recently-search";

    private final Context context;
    private final SharedPreferences pref;
    private final Gson gson;

    public PreferenceProvider(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new GsonBuilder().setDateFormat(Constant.FORMAT_DATE)
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
    }

    public void clearPreference() {
        pref.edit().clear().apply();
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public Member getMember() {
        String member = pref.getString(KEY_MEMBER, null);
        try {
            return gson.fromJson(member, Member.class);
        } catch (Exception e) {
            setMember(null);
        }
        return null;
    }

    public void setMember(Member member) {
        SharedPreferences.Editor editor = pref.edit();
        if (member != null) {
            String json = gson.toJson(member);
            editor.putString(KEY_MEMBER, json);
        } else
            editor.putString(KEY_MEMBER, "");
        editor.apply();
    }

    public List<String> getRecentlySearch() {
        List<String> result = null;
        String member = pref.getString(KEY_RECENTLY_SEARCH, null);
        try {
            result = gson.fromJson(member, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            setRecentlySearch(null);
        }
        if (result == null)
            result = new ArrayList<>();
        return result;
    }

    public void setRecentlySearch(List<String> recentlySearchList) {
        SharedPreferences.Editor editor = pref.edit();
        if (recentlySearchList != null) {
            String json = gson.toJson(recentlySearchList);
            editor.putString(KEY_RECENTLY_SEARCH, json);
        } else
            editor.putString(KEY_RECENTLY_SEARCH, "");
        editor.apply();
    }
}
