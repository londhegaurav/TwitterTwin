
package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.parceler.Generated;
@Generated("org.jsonschema2pojo")
public class Message implements Parcelable {

    public String createdAt;
    public Entities entities;
    public int id;
    public String idStr;
    public Recipient recipient;
    public int recipientId;
    public String recipientScreenName;
    public Sender sender;
    public int senderId;
    public String senderScreenName;
    public String text;

    public static Message parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        Message message = gson.fromJson(response, Message.class);
        return message;
    }

    protected Message(Parcel in) {
        createdAt = in.readString();
        id = in.readInt();
        idStr = in.readString();
        recipientId = in.readInt();
        recipientScreenName = in.readString();
        senderId = in.readInt();
        senderScreenName = in.readString();
        text = in.readString();

    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdAt);
        dest.writeInt(id);
        dest.writeString(idStr);
        dest.writeInt(recipientId);
        dest.writeString(recipientScreenName);
        dest.writeInt(senderId);
        dest.writeString(senderScreenName);
        dest.writeString(text);
        dest.writeParcelable(this.sender, 0);
        dest.writeParcelable(this.recipient, 0);

    }
}
