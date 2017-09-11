package com.mgeows.milo.di;

import com.mgeows.milo.libs.ImageLoader;
import com.mgeows.milo.ui.petslist.PetListAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = UiModule.class)
public interface UiComponent {
//    void inject(Fragment fragment);
    PetListAdapter getListAdapter();
    ImageLoader getImageLoader();
}
