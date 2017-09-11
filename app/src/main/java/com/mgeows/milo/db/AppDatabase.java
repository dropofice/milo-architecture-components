package com.mgeows.milo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.mgeows.milo.db.converter.DateConverter;
import com.mgeows.milo.db.dao.PetDao;
import com.mgeows.milo.db.entity.Pet;

@Database(version = 3, entities = Pet.class)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PetDao petDao();
}
