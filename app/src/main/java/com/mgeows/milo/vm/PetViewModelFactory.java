package com.mgeows.milo.vm;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mgeows.milo.data.PetRepository;

/**
 * Created by JC on 09/03/2017.
 */

public class PetViewModelFactory implements ViewModelProvider.Factory {

    private final Application mApplication;


    private final PetRepository mRepository;

    public PetViewModelFactory(Application application, PetRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PetViewModel.class)) {
            return (T) new PetViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
