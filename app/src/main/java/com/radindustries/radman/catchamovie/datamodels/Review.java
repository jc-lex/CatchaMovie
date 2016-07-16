package com.radindustries.radman.catchamovie.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by radman on 7/15/16.
 */
public class Review implements Parcelable {

    private String reviewStr;
    private String author;

    public Review(String reviewStr, String author) {
        this.reviewStr = reviewStr;
        this.author = author;
    }

    protected Review(Parcel in) {
        reviewStr = in.readString();
        author = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getReviewStr() {
        return reviewStr;
    }

    public void setReviewStr(String reviewStr) {
        this.reviewStr = reviewStr;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewStr);
        dest.writeString(author);
    }
}
