package com.example.shows.model.database.entity;


import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.shows.model.database.entity.converter.ConverterDateType;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(tableName = "performance",foreignKeys = @ForeignKey(entity = Geners.class, parentColumns = "id", childColumns = "genre_id", onDelete = CASCADE))
@TypeConverters({ConverterDateType.class})
public class Performance extends CommonEntity{
    private String name;
    private String description;
    @Nullable
    @TypeConverters(ConverterDateType.class)
    private Timestamp date;
    private String duration;
    private int amountTickets;
    private double price;
    private double rating;

    @Nullable
    @ColumnInfo(name = "genre_id")
    private int genreId;

    @Ignore
    @Nullable
    private Collection<Booking> bookings;
    @Nullable
    @Ignore
    private Geners genre;
    @Ignore
    private Collection<Actor> actors;
    @Ignore
    private Collection<Scenarist> scenarists;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getAmountTickets() {
        return amountTickets;
    }

    public void setAmountTickets(int amountTickets) {
        this.amountTickets = amountTickets;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        if(genre!=null)
            this.genreId = genre.getId();
        else
            this.genreId = genreId;
    }


    public Geners getGenre() {
        return genre;
    }

    public void setGenre(Geners genre) {
        this.genre = genre;
    }

    public Collection<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Collection<Booking> bookings) {
        this.bookings = bookings;
    }

    public Collection<Actor> getActors() {
        return actors;
    }

    public void setActors(Collection<Actor> actors) {
        this.actors = actors;
    }

    public Collection<Scenarist> getScenarists() {
        return scenarists;
    }

    public void setScenarists(Collection<Scenarist> scenarists) {
        this.scenarists = scenarists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Performance clone() throws CloneNotSupportedException {
         Performance copyObj = new Performance();
         copyObj.setName(this.name);
         copyObj.setDate(this.date);
         copyObj.setRating(this.rating);
         copyObj.setPrice(this.price);
         copyObj.setGenreId(this.genreId);
         copyObj.setDuration(this.duration);
         copyObj.setDescription(this.description);
         copyObj.setAmountTickets(this.amountTickets);
         return copyObj;
    }

}
