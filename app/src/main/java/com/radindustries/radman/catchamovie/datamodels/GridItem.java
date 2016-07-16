package com.radindustries.radman.catchamovie.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GridItem implements Parcelable {

    private int movieId;
    private String image;
    private String title;
    private String releaseDate;
    private String userRating;
    private String plotSynopsis;
    private ArrayList<Trailer> trailerArrayList;
    private ArrayList<Review> reviewArrayList;

    public GridItem() {
        super();
    }

    protected GridItem(Parcel in) {
        movieId = in.readInt();
        image = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        userRating = in.readString();
        plotSynopsis = in.readString();
        trailerArrayList = in.createTypedArrayList(Trailer.CREATOR);
        reviewArrayList = in.createTypedArrayList(Review.CREATOR);
    }

    public static final Creator<GridItem> CREATOR = new Creator<GridItem>() {
        @Override
        public GridItem createFromParcel(Parcel in) {
            return new GridItem(in);
        }

        @Override
        public GridItem[] newArray(int size) {
            return new GridItem[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public ArrayList<Trailer> getTrailerArrayList() {
        return trailerArrayList;
    }

    public void setTrailerArrayList(ArrayList<Trailer> trailerArrayList) {
        this.trailerArrayList = trailerArrayList;
    }

    public ArrayList<Review> getReviewArrayList() {
        return reviewArrayList;
    }

    public void setReviewArrayList(ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(userRating);
        dest.writeString(plotSynopsis);
        dest.writeTypedList((List) trailerArrayList);
        dest.writeTypedList((List) reviewArrayList);
    }
}
