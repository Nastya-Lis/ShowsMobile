package com.example.shows.model.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.shows.model.database.entity.converter.ConverterDateType;

@Entity(tableName = "booking",foreignKeys = {
        @ForeignKey(parentColumns = "id",childColumns = "user_id", entity = User.class),
        @ForeignKey(parentColumns = "id", childColumns = "performance_id", entity = Performance.class)
})
@TypeConverters({ConverterDateType.class})
public class Booking extends CommonEntity{
    private int amount;
    @ColumnInfo(name = "user_id")
    private int userId;
    @ColumnInfo(name = "performance_id")
    private int performanceId;

    @Ignore
    private User user;
    @Ignore
    private Performance performance;


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(int performanceId) {
        this.performanceId = performanceId;
    }

    public User getUser() {
        if(userId!=0){
            user = new User();
            user.setId(userId);
        }
        return user;
    }

    public void setUser(User user) {
        if(userId!=0) {
            this.user = new User();
            this.user.setId(userId);
        }
        else
        this.user = user;
    }

    public Performance getPerformance() {
        if(userId!=0)
        {
            performance = new Performance();
            performance.setId(userId);
        }
        return performance;
    }

    public void setPerformance(Performance performance)
    {
        if(performanceId!=0) {
            this.performance = new Performance();
            this.performance.setId(userId);
        }
        else
        this.performance = performance;
    }
}
