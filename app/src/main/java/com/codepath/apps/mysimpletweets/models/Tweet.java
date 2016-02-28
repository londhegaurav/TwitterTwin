package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by glondhe on 2/17/16.
 */

   //Parse the JSON + store the data, encapsulate state logic or display logic.
@Table(name = "Tweet")
public class Tweet extends Model implements Parcelable{
    //list out the attribute
    @Column(name = "body")
    public String body;
    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long uid;
    @Column(name = "createdAt")
    public String createdAt;
    @Column(name = "user", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public User user;
//    @Column(name = "medias", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public ArrayList<Media> medias;
    @Column(name = "reTweetCnt")
    public int reTweetCnt;
    @Column(name = "favCnt")
    public int favCnt;
    @Column(name = "addKey")
    public String addKey;

    public Tweet(){

    }

    public static Tweet fromJson(JSONObject jsonObject) {

        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.findOrCreateFromJson(jsonObject.getJSONObject("user"));
            tweet.addKey = "null";
            if(String.valueOf(jsonObject.getInt("retweet_count")).length() > 0) {
                tweet.reTweetCnt = jsonObject.getInt("retweet_count");
            }
            else{
                tweet.reTweetCnt = 0;
            }
            if(String.valueOf(jsonObject.getInt("favorite_count")).length() > 0) {
                tweet.favCnt = jsonObject.getInt("favorite_count");
            }
            else{
                tweet.favCnt = 0;
            }
            JSONObject entities = jsonObject.optJSONObject("entities");
            if(entities != null) {
                JSONArray medias = entities.optJSONArray("media");
                if (medias != null) {
                    tweet.medias = Media.fromJSONArray(medias);
                }
            }
            else tweet.medias = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    //Tweet.fromJsonArray
    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i=0; i < jsonArray.length(); i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJson(tweetJson);
                if (tweet != null){
                    tweets.add(tweet);
                    tweet.save();
                    User user= tweet.user;
                    user.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }
        return tweets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeLong(this.uid);
        dest.writeString(this.createdAt);
        dest.writeParcelable(this.user, 0);
        dest.writeTypedList(medias);
        dest.writeInt(this.reTweetCnt);
        dest.writeInt(this.favCnt);
        dest.writeString(this.addKey);
    }

    public static List<Tweet> getAll(){
        final int limit = 25;
        Log.d("DEBUG", "Loading from database - offset " ); // TODO: Remove later
        List<Tweet> tweets = new Select().from(Tweet.class)
                .orderBy("createdAt DESC")
                .limit(limit).execute();

        //TODO: new Delete().from(Tweet.class).execute();
        return tweets;
    }

    protected Tweet(Parcel in) {
        this.body = in.readString();
        this.uid = in.readLong();
        this.createdAt = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.medias = in.createTypedArrayList(Media.CREATOR);
        this.reTweetCnt = in.readInt();
        this.favCnt = in.readInt();
        this.addKey = in.readString();
    }

    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}


