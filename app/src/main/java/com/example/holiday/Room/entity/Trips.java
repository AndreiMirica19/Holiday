package com.example.holiday.Room.entity;

import android.widget.RatingBar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Trips")
public class Trips {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int tripId;
    @ColumnInfo(name = "TripName")
    private String TripName;
    @ColumnInfo(name = "Destination")
    private String Destination;
    @ColumnInfo(name = "Price")
    private int Price;
    @ColumnInfo(name = "TripType")
    private String TripType;

    @ColumnInfo(name = "startDate")
    private String startDate;
    @ColumnInfo(name = "endDate")
    private String endDate;
    @ColumnInfo(name = "Bookmark")
    private Boolean Bookmark;
    @ColumnInfo(name = "ratingBar")
   private int Rating;

    public int getRating() {
        return Rating;
    }

    public void setRating(int ratingBar) {
        this.Rating = ratingBar;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
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
}
