package com.mgeows.milo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mgeows.milo.db.dao.PetDao;
import com.mgeows.milo.db.entity.Pet;

@Database(version = 1, entities = Pet.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PetDao petDao();
}
