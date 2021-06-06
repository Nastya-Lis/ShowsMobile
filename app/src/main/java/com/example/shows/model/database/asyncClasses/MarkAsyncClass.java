package com.example.shows.model.database.asyncClasses;

import android.os.AsyncTask;

import com.example.shows.model.database.dao.ActorDao;
import com.example.shows.model.database.dao.MarkDao;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Mark;

public class MarkAsyncClass extends AsyncTask<Integer,Void, Mark> {

    private MarkDao dao;

    public MarkAsyncClass(MarkDao dao) {
        this.dao = dao;
    }

    @Override
    protected Mark doInBackground(Integer... ids) {
        Integer id = ids[0];
        Mark mark = dao.getMarkByPerformanceId(id);
        return mark;
    }
}
