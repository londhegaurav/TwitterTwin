
package com.codepath.apps.mysimpletweets.models;


import android.os.Parcel;
import android.os.Parcelable;

import org.parceler.Generated;

@Generated("org.jsonschema2pojo")
public class Sender implements Parcelable {

    public boolean contributorsEnabled;
    public String createdAt;
    public boolean defaultProfile;
    public boolean defaultProfileImage;
    public String description;
    public int favouritesCount;
    public boolean followRequestSent;
    public int followersCount;
    public boolean following;
    public int friendsCount;
    public boolean geoEnabled;
    public int id;
    public String idStr;
    public boolean isTranslator;
    public String lang;
    public int listedCount;
    public String location;
    public String name;
    public boolean notifications;
    public String profileBackgroundColor;
    public String profileBackgroundImageUrl;
    public String profileBackgroundImageUrlHttps;
    public boolean profileBackgroundTile;
    public String profileImageUrl;
    public String profileImageUrlHttps;
    public String profileLinkColor;
    public String profileSidebarBorderColor;
    public String profileSidebarFillColor;
    public String profileTextColor;
    public boolean profileUseBackgroundImage;
    public boolean _protected;
    public String screenName;
    public boolean showAllInlineMedia;
    public int statusesCount;
    public String timeZone;
    public Object url;
    public int utcOffset;
    public boolean verified;

    protected Sender(Parcel in) {
        createdAt = in.readString();
        description = in.readString();
        favouritesCount = in.readInt();
        followersCount = in.readInt();
        friendsCount = in.readInt();
        id = in.readInt();
        idStr = in.readString();
        lang = in.readString();
        listedCount = in.readInt();
        location = in.readString();
        name = in.readString();
        profileBackgroundColor = in.readString();
        profileBackgroundImageUrl = in.readString();
        profileBackgroundImageUrlHttps = in.readString();
        profileImageUrl = in.readString();
        profileImageUrlHttps = in.readString();
        profileLinkColor = in.readString();
        profileSidebarBorderColor = in.readString();
        profileSidebarFillColor = in.readString();
        profileTextColor = in.readString();
        screenName = in.readString();
        statusesCount = in.readInt();
        timeZone = in.readString();
        utcOffset = in.readInt();
    }


    public static final Creator<Sender> CREATOR = new Creator<Sender>() {
        @Override
        public Sender createFromParcel(Parcel in) {
            return new Sender(in);
        }

        @Override
        public Sender[] newArray(int size) {
            return new Sender[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdAt);
        dest.writeString(description);
        dest.writeInt(favouritesCount);
        dest.writeInt(followersCount);
        dest.writeInt(friendsCount);
        dest.writeInt(id);
        dest.writeString(idStr);
        dest.writeString(lang);
        dest.writeInt(listedCount);
        dest.writeString(location);
        dest.writeString(name);
        dest.writeString(profileBackgroundColor);
        dest.writeString(profileBackgroundImageUrl);
        dest.writeString(profileBackgroundImageUrlHttps);
        dest.writeString(profileImageUrl);
        dest.writeString(profileImageUrlHttps);
        dest.writeString(profileLinkColor);
        dest.writeString(profileSidebarBorderColor);
        dest.writeString(profileSidebarFillColor);
        dest.writeString(profileTextColor);
        dest.writeString(screenName);
        dest.writeInt(statusesCount);
        dest.writeString(timeZone);
        dest.writeInt(utcOffset);
    }
}
