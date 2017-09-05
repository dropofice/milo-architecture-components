package com.mgeows.milo.data;


import android.arch.lifecycle.LiveData;

import com.mgeows.milo.db.entity.Pet;

import java.util.List;

import io.reactivex.Completable;

public interface PetRepository {

    Completable addPet(Pet pet);

    Completable updatePet(Pet pet);

    Completable addAllPets(List<Pet> pets);

    LiveData<List<Pet>> getPets();

    LiveData<Pet> getPet(String id);

    Completable deletePet(Pet pet);

    Completable deletePetById(String id);
}
