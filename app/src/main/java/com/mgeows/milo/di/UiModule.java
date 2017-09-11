package com.mgeows.milo.di;

import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mgeows.milo.db.entity.Pet;
import com.mgeows.milo.libs.GlideImageLoader;
import com.mgeows.milo.libs.ImageLoader;
import com.mgeows.milo.ui.petslist.PetItemClickListener;
import com.mgeows.milo.ui.petslist.PetListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {

    private Fragment fragment;
    private PetItemClickListener listener;

    public UiModule(Fragment fragment, PetItemClickListener listener) {
        this.fragment = fragment;
        this.listener = listener;
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(RequestManager requestManager) {
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager provideRequestManager(Fragment fragment) {
        return Glide.with(fragment);
    }

    @Provides
    @Singleton
    Fragment provideFragment() {
        return this.fragment;
    }

    @Provides
    @Singleton
    PetListAdapter provideListAdapter(List<Pet> pets, ImageLoader imageLoader, PetItemClickListener listener) {
        return new PetListAdapter(pets, imageLoader, listener);
    }

    @Provides
    @Singleton
    PetItemClickListener providePetItemClickListener() {
        return this.listener;
    }

    @Provides
    @Singleton
    List<Pet> provideEmptyList() {
        return new ArrayList<>();
    }
}
