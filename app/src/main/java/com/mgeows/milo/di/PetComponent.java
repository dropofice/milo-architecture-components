package com.mgeows.milo.di;


import com.mgeows.milo.vm.PetViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = PetModule.class)
public interface PetComponent {

    void inject(PetViewModel petViewModel);

    interface Injectable {
        void inject(PetComponent petComponent);
    }
}
