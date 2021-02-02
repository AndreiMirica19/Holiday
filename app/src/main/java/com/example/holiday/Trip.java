package com.example.holiday;

import android.annotation.SuppressLint;
import android.graphics.Picture;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.DatePicker;
import android.widget.RatingBar;


public class Trip implements Parcelable {
   private Picture picture;
    private String TripName;
    private String Destination;
    private int Price;
    private String TripType;
    private int ratingBar;
    private String startDate;
    private String endDate;
    private Boolean Bookmark;

    public Trip() {

    }

    protected Trip(Parcel in) {
        TripName = in.readString();
        Destination = in.readString();
        if (in.readByte() == 0) {
            Price =0;
        } else {
            Price = in.readInt();
        }
        TripType = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        ratingBar=in.readInt();
        byte tmpBookmark = in.readByte();
        Bookmark = tmpBookmark == 0 ? null : tmpBookmark == 1;
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getTripName() {
        return TripName;
    }

    public void setTripName(String tripName) {
        TripName = tripName;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getTripType() {
        return TripType;
    }

    public void setTripType(String tripType) {
        TripType = tripType;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getBookmark() {
        return Bookmark;
    }

    public void setBookmark(Boolean bookmark) {
        Bookmark = bookmark;
    }

    public int getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(int ratingBar) {
        this.ratingBar = ratingBar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TripName);
        dest.writeString(Destination);
        if (Price == 0) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Price);
        }
        dest.writeString(TripType);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeInt(ratingBar);
        dest.writeByte((byte) (Bookmark == null ? 0 : Bookmark ? 1 : 2));
    }
}
