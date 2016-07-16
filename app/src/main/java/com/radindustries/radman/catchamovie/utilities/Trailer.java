package com.radindustries.radman.catchamovie.utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by radman on 7/15/16.
 */
public class Trailer implements Parcelable {

    private String name;
    private String url;

    public Trailer(String name, String url) {
        this.name = name;
        this.url = url;
    }

    protected Trailer(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }
}
