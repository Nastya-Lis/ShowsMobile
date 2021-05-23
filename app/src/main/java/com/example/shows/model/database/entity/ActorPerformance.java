package com.example.shows.model.database.entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "actor_performance",primaryKeys = {"actor_id","performance_id"},
        foreignKeys = { @ForeignKey(entity = Actor.class, parentColumns = "id", childColumns = "actor_id", onDelete = CASCADE, onUpdate = CASCADE),
        @ForeignKey(entity = Performance.class,parentColumns = "id", childColumns = "performance_id", onDelete = CASCADE, onUpdate = CASCADE)
})
public class ActorPerformance implements Serializable {
    private int actor_id;
    private int performance_id;

    public int getActor_id() {
        return actor_id;
    }

    public void setActor_id(int actor_id) {
        this.actor_id = actor_id;
    }

    public int getPerformance_id() {
        return performance_id;
    }

    public void setPerformance_id(int performance_id) {
        this.performance_id = performance_id;
    }
}
