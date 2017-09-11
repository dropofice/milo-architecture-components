package com.mgeows.milo;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.mgeows.milo.di.DaggerPetComponent;
import com.mgeows.milo.di.DaggerUiComponent;
import com.mgeows.milo.di.PetComponent;
import com.mgeows.milo.di.PetModule;
import com.mgeows.milo.di.UiComponent;
import com.mgeows.milo.di.UiModule;
import com.mgeows.milo.ui.petslist.PetItemClickListener;

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

    public UiComponent getUiComponent(Fragment fragment, PetItemClickListener listener) {
        return DaggerUiComponent.builder()
                                .uiModule(new UiModule(fragment, listener))
                                .build();
    }


}
