package com.example.shows.model.database.entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "mark")
public class Mark extends CommonEntity{
    private Integer performance_id;
    private Integer user_id;

    @Nullable
    private boolean likeOrNot;

    public void setLikeOrNot(boolean likeOrNot) {
        this.likeOrNot = likeOrNot;
    }

}
