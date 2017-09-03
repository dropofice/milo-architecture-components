package com.mgeows.milo.data;


import android.arch.lifecycle.LiveData;

import com.mgeows.milo.db.entity.Pet;

import java.util.List;

import io.reactivex.Completable;

public interface PetDataSource {
    Completable addPet(Pet pet);

    Completable addAllPets(List<Pet> pets);

    LiveData<List<Pet>> getPets();

    Completable deletePet(Pet pet);
}
