package com.mgeows.milo;

import android.app.Application;

import com.mgeows.milo.di.DaggerPetComponent;
import com.mgeows.milo.di.PetComponent;
import com.mgeows.milo.di.PetModule;

import timber.log.Timber;


public class PetApplication extends Application {

    private final PetComponent petComponent = createPetComponent();

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    protected PetComponent createPetComponent() {
        return DaggerPetComponent.builder()
                                 .petModule(new PetModule(this))
                                 .build();
    }

    public PetComponent getPetComponent() {
        return petComponent;
    }
}
