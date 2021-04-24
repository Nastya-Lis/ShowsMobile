package com.example.shows.model.database.entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import java.io.Serializable;

@Entity(tableName = "scenarist_performance",primaryKeys = {"scenarist_id","performance_id"},
foreignKeys = {
        @ForeignKey(entity = Scenarist.class, parentColumns = "id", childColumns = "scenarist_id"),
        @ForeignKey(entity = Performance.class,parentColumns = "id", childColumns = "performance_id")
})
public class ScenaristPerformance implements Serializable {
    private int scenarist_id;
    private int performance_id;

    public int getScenarist_id() {
        return scenarist_id;
    }

    public void setScenarist_id(int scenarist_id) {
        this.scenarist_id = scenarist_id;
    }

    public int getPerformance_id() {
        return performance_id;
    }

    public void setPerformance_id(int performance_id) {
        this.performance_id = performance_id;
    }
}
