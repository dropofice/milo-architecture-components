package com.mgeows.milo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mgeows.milo.db.dao.PetDao;
import com.mgeows.milo.db.entity.Pet;

@Database(version = 1, entities = Pet.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PetDao petDao();

    /** The only instance */
    private static AppDatabase INSTANCE;

    /**
     * Gets the singleton instance of AppDatabase.
     *
     * @param context The context
     * @return he singleton instance of SampleDatabase.
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "pets_db")
                    .build();
        }
        return INSTANCE;
    }

}
