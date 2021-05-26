package com.example.shows.model.database.asyncClasses;

import android.os.AsyncTask;

import com.example.shows.model.database.dao.ActorDao;
import com.example.shows.model.database.dao.ScenaristDao;
import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.Scenarist;

public class ScenaristForSearch extends AsyncTask<String,Void, Scenarist> {
    private ScenaristDao dao;

    public ScenaristForSearch(ScenaristDao dao) {
        this.dao = dao;
    }

    @Override
    protected Scenarist doInBackground(String... strings) {
        String name = strings[0];
        Scenarist scenarist = dao.findScenaristByName(name);
        return scenarist;
    }
}
