package com.mgeows.milo.vm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.mgeows.milo.PetApplication;
import com.mgeows.milo.di.PetComponent;


public class PetViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private PetApplication mApplication;

    public PetViewModelFactory(PetApplication application) {
        this.mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof PetComponent.Injectable) {
            ((PetComponent.Injectable) t).inject(mApplication.getPetComponent());
            return t;
        }
        throw new IllegalArgumentException("Unknown ViewModel class" + modelClass);
    }
}
