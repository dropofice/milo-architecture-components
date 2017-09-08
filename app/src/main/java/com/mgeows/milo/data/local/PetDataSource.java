package com.mgeows.milo.data.local;

import android.arch.lifecycle.LiveData;

import com.mgeows.milo.db.entity.Pet;

import java.util.List;


public interface PetDataSource {

    void addPet(Pet pet);

    void updatePet(Pet pet);

    void addAllPets(List<Pet> pets);

    LiveData<List<Pet>> getPets();

    LiveData<Pet> getPet(String id);

    void deletePet(Pet pet);

    void deletePetById(String id);

    void deleteAllPets();
}
