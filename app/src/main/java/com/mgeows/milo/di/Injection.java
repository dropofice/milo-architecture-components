package com.mgeows.milo.di;

import android.app.Application;
import android.content.Context;

import com.mgeows.milo.data.PetRepository;
import com.mgeows.milo.data.PetRepositoryImpl;
import com.mgeows.milo.data.local.LocalPetDataSource;
import com.mgeows.milo.data.local.PetDataSource;
import com.mgeows.milo.db.AppDatabase;
import com.mgeows.milo.vm.PetViewModelFactory;


public class Injection {

    public static PetViewModelFactory provideViewModelFactory(Context context) {
        PetRepository repository = providePetRepository(context);
        return new PetViewModelFactory((Application) context.getApplicationContext(), repository);
    }

    public static PetRepository providePetRepository(Context context) {
        PetDataSource dataSource = providePetDataSource(context);
        return new PetRepositoryImpl(dataSource);
    }

    public static PetDataSource providePetDataSource(Context context) {
        AppDatabase database = provideAppDatabase(context);
        return new LocalPetDataSource(database.petDao());
    }

    public static AppDatabase provideAppDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }
}
