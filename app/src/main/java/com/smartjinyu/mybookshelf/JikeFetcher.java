package com.smartjinyu.mybookshelf;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class JikeFetcher extends BookFetcher {
    private static final String TAG = "JikeFetcher";


    @Override
    public void getBookInfo(final Context context, final String isbn, final int mode) {
        mContext = context;
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.jike.xyz/situ/book/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Jike_API api = mRetrofit.create(Jike_API.class);
        //create an instance of jike api
        byte[] data = Base64.decode(BuildConfig.jikeApiKey, Base64.DEFAULT);
        Call<JikeJson> call = api.getDBResult(isbn, new String(data, StandardCharsets.UTF_8));

        call.enqueue(new Callback<JikeJson>() {
            @Override
            public void onResponse(Call<JikeJson> call, Response<JikeJson> response) {
                if (response.code() == 200) {
                    Log.i(TAG, "GET Jike information successfully, id = " + response.body().data.id
                            + ", title = " + response.body().data.name);
                    mBook = new Book();
                    mBook.setTitle(response.body().data.name);

                    mBook.setIsbn(isbn);
                    ArrayList<String> authors = new ArrayList<String>();
                    authors.add(response.body().data.author);
                    mBook.setAuthors(authors);

                    ArrayList<String> translators = new ArrayList<String>();
                    translators.add(response.body().data.translator);
                    mBook.setTranslators(translators);

                    mBook.getWebIds().put("douban", String.valueOf(response.body().data.douban));
                    mBook.setPublisher(response.body().data.publishing);

                    String rawDate = response.body().data.published;
                    Log.i(TAG, "Date raw = " + rawDate);
                    String year, month;
                    if (rawDate.contains("-")) {
                        // 2016-11
                        String[] date = rawDate.split("-");
                        year = date[0];
                        // rawDate sometimes is "2016-11", sometimes is "2000-10-1", sometimes is "2010-1"
                        month = date[1];
                    } else if (rawDate.contains(".")) {
                        String[] date = rawDate.split("\\.");
                        year = date[0];
                        // rawDate sometimes is "2016-11", sometimes is "2000-10-1", sometimes is "2010-1"
                        month = date[1];
                    } else {
                        year = "9999";
                        month = "1";
                    }
                    Log.i(TAG, "Get PubDate Year = " + year + ", month = " + month);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
                    mBook.setPubTime(calendar);
                    final String imageURL = response.body().data.photoUrl;
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
                    boolean addWebsite = pref.getBoolean("settings_pref_acwebsite", true);
                    if (addWebsite) {
                        mBook.setWebsite("https://book.douban.com/subject/" + response.body().data.douban);
                    }
                    if (mode == 0) {
                        ((SingleAddActivity) mContext).fetchSucceed(mBook, imageURL);
                    } else if (mode == 1) {
                        ((BatchAddActivity) mContext).fetchSucceed(mBook, imageURL);
                    }
                } else {
                    Log.w(TAG, "Unexpected response code " + response.code() + ", isbn = " + isbn);
                    if (mode == 0) {
                        ((SingleAddActivity) mContext).fetchFailed(
                                BookFetcher.fetcherID_DB, 0, isbn
                        );
                    } else if (mode == 1) {
                        ((BatchAddActivity) mContext).fetchFailed(
                                BookFetcher.fetcherID_DB, 0, isbn);
                    }
                }

            }

            @Override
            public void onFailure(Call<JikeJson> call, Throwable t) {
                Log.w(TAG, "GET Jike information failed, " + t.toString());
                if (mode == 0) {
                    ((SingleAddActivity) mContext).fetchFailed(
                            BookFetcher.fetcherID_DB, 1, isbn
                    );
                } else if (mode == 1) {
                    ((BatchAddActivity) mContext).fetchFailed(
                            BookFetcher.fetcherID_DB, 1, isbn);
                }
            }
        });


    }

    private interface Jike_API {
        @GET("isbn/{isbn}")
        Call<JikeJson> getDBResult(@Path("isbn") String isbn, @Query("apikey") String apikey);
    }

}






