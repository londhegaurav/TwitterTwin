package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by glondhe on 2/26/16.
 */


public class Statuses implements Parcelable {
    public ArrayList<Tweet> tw;
    //list out the attribute

    public String body;
    public long uid;
    public String createdAt;
    public User user;
    public ArrayList<Media> medias;
    public int reTweetCnt;
    public int favCnt;
    public String addKey;
    public Statuses(){

    }


    public static Statuses fromJson(JSONObject jsonObject) {

        Statuses st = new Statuses();

        try {
            st.tw = Tweet.fromJsonArray(jsonObject.getJSONArray("statuses"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return st;
    }

    //Tweet.fromJsonArray
    public static ArrayList<Statuses> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Statuses> st = new ArrayList<>();
        for (int i=0; i < jsonArray.length(); i++){
            try {
                JSONObject stJson = jsonArray.getJSONObject(i);
                Statuses stw = Statuses.fromJson(stJson);
                if (stw != null){
                    st.add(stw);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }
        return st;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.tw, 0);
;
    }

    protected Statuses(Parcel in) {
        this.tw = in.readParcelable(Tweet.class.getClassLoader());
    }

    public static final Parcelable.Creator<Statuses> CREATOR = new Parcelable.Creator<Statuses>() {
        public Statuses createFromParcel(Parcel source) {
            return new Statuses(source);
        }

        public Statuses[] newArray(int size) {
            return new Statuses[size];
        }
    };
}

