package com.afei.bottomtabbar.HttpTools;



import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.Interceptor;


public class Session {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient mOkHttpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).cookieJar(new CookieJarManager()).build();
    private class CookieJarManager implements CookieJar {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>() {
            };
        }
    }
    public String post(String url, JSONObject json) {
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {

            Response response = mOkHttpClient.newCall(request).execute();
            String respStr = response.body().string();
            return respStr;
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
    }
    public JSONObject postJSON(String url,JSONObject json)
    {
        try{
            return  new JSONObject(new JSONTokener(post(url,json)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }
    public JSONArray postJSONArtay(String url,JSONObject json)
    {
        try{
            return  new JSONArray(new JSONTokener(post(url,json)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  null;
    }
    public String get(String url) {
        final Request.Builder builder = new Request.Builder();
        builder.url(url);
        final Request request = builder.build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}

