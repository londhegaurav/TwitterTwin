package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by glondhe on 2/17/16.
 */
@Table(name = "User")
public class User extends Model implements Parcelable{

    @Column(name = "name")
    public String name;
    @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long uid;
    @Column(name = "screenName")
    public String screenName;
    @Column(name = "profileIamgeURL")
    public String profileIamgeURL;
    @Column(name = "tagLine")
    public String tagLine;
    @Column(name = "followersCount")
    public int followersCount;
    @Column(name = "followingsCount")
    public int followingsCount;
    @Column(name = "profileBannerUrl")
    public String profileBannerUrl;
    @Column(name = "tweetCount")
    public String tweetCount;
    @Column(name = "favCnt")
    public int favCnt;

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileIamgeURL() {
        return profileIamgeURL;
    }

    public void setProfileIamgeURL(String profileIamgeURL) {
        this.profileIamgeURL = profileIamgeURL;
    }

    //Tweet.fromJsonArray
    public static ArrayList<User> getArrayList(JSONObject json) {
        ArrayList<User> Users = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = json.getJSONArray("users");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i=0; i < jsonArray.length(); i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                User user = User.findOrCreateFromJson(tweetJson);
                Log.d("DEBUG bodyall",user.screenName);
                if (user != null){
                    Users.add(user);
                    Log.d("DEBUG bodyall89", Users.get(i).screenName);
                    user.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }
        Log.d("DEBUG bodyall899", Users.get(0).screenName);
        return Users;
    }
    public static User parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        User userListResponse = gson.fromJson(response, User.class);
        return userListResponse;
    }

    public static User fromJSON(JSONObject json){
        User u = new User();

        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileIamgeURL = json.getString("profile_image_url");
            u.tagLine = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingsCount = json.getInt("friends_count");
            u.profileBannerUrl = json.optString("profile_banner_url");
            u.tweetCount = json.getString("statuses_count");
            if(String.valueOf(json.getInt("favourites_count")).length() > 0) {
                u.favCnt = json.getInt("favourites_count");
            }
            else{
                u.favCnt = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return u;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Finds existing user based on remoteId or creates new user and returns
    public static User findOrCreateFromJson(JSONObject json) {
        User u = new User();
        long rId = 0; // get just the remote id
        try {
            rId = json.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        User existingUser =
                new Select().from(User.class).where("uid = ?", rId).executeSingle();
        if (existingUser != null) {
            // found and return existing
            return existingUser;
        } else {
            // create and return new user
            User user = User.fromJSON(json);
            return user;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.uid);
        dest.writeString(this.screenName);
        dest.writeString(this.profileIamgeURL);
        dest.writeString(this.tagLine);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.followingsCount);
        dest.writeString(this.profileBannerUrl);
        dest.writeString(this.tweetCount);
        dest.writeInt(this.favCnt);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.uid = in.readLong();
        this.screenName = in.readString();
        this.profileIamgeURL = in.readString();
        this.tagLine = in.readString();
        this.followersCount = in.readInt();
        this.followingsCount = in.readInt();
        this.profileBannerUrl = in.readString();
        this.tweetCount = in.readString();
        this.favCnt = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };


    @SerializedName("previous_cursor")
    @Expose
    private Long previousCursor;
    @SerializedName("previous_cursor_str")
    @Expose
    private String previousCursorStr;
    @SerializedName("next_cursor")
    @Expose
    private Long nextCursor;
    @SerializedName("users")
    @Expose
    private ArrayList<User> users = new ArrayList<User>();
    @SerializedName("next_cursor_str")
    @Expose
    private String nextCursorStr;

    /**
     *
     * @return
     *     The previousCursor
     */
    public Long getPreviousCursor() {
        return previousCursor;
    }

    /**
     *
     * @param previousCursor
     *     The previous_cursor
     */
    public void setPreviousCursor(Long previousCursor) {
        this.previousCursor = previousCursor;
    }

    /**
     *
     * @return
     *     The previousCursorStr
     */
    public String getPreviousCursorStr() {
        return previousCursorStr;
    }

    /**
     *
     * @param previousCursorStr
     *     The previous_cursor_str
     */
    public void setPreviousCursorStr(String previousCursorStr) {
        this.previousCursorStr = previousCursorStr;
    }

    /**
     *
     * @return
     *     The nextCursor
     */
    public Long getNextCursor() {
        return nextCursor;
    }

    /**
     *
     * @param nextCursor
     *     The next_cursor
     */
    public void setNextCursor(Long nextCursor) {
        this.nextCursor = nextCursor;
    }

    /**
     *
     * @return
     *     The users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     *
     * @param users
     *     The users
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /**
     *
     * @return
     *     The nextCursorStr
     */
    public String getNextCursorStr() {
        return nextCursorStr;
    }

    /**
     *
     * @param nextCursorStr
     *     The next_cursor_str
     */
    public void setNextCursorStr(String nextCursorStr) {
        this.nextCursorStr = nextCursorStr;
    }

}
