package com.mgeows.milo.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mgeows.milo.PetApplication;
import com.mgeows.milo.data.PetRepository;
import com.mgeows.milo.data.PetRepositoryImpl;
import com.mgeows.milo.data.local.LocalPetDataSource;
import com.mgeows.milo.data.local.PetDataSource;
import com.mgeows.milo.db.AppDatabase;
import com.mgeows.milo.db.dao.PetDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PetModule {

    private PetApplication petApplication;

    public PetModule(PetApplication petApplication) {
        this.petApplication = petApplication;
    }

    @Provides
    Context provideApplicationContext() {
        return this.petApplication;
    }

    @Provides
    @Singleton
    PetRepository providePetRepository(PetDataSource dataSource) {
        return new PetRepositoryImpl(dataSource);
    }

    @Provides
    @Singleton
    PetDataSource providePetDataSource(PetDao petDao) {
        return new LocalPetDataSource(petDao);
    }

    @Provides
    @Singleton
    PetDao providePetDao(AppDatabase database) {
        return database.petDao();
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "pets.db")
                   .build();
    }
}
