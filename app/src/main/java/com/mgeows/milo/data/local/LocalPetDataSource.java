package com.mgeows.milo.data.local;


import android.arch.lifecycle.LiveData;

import com.mgeows.milo.db.AppDatabase;
import com.mgeows.milo.db.entity.Pet;

import java.util.List;

public class LocalPetDataSource implements PetDataSource{

    private AppDatabase mDatabase;

    public LocalPetDataSource(AppDatabase database) {
        this.mDatabase = database;
    }

    @Override
    public void addPet(Pet pet) {
        mDatabase.petDao().addPet(pet);
    }

    @Override
    public void updatePet(Pet pet) {
        mDatabase.petDao().updatePet(pet);
    }

    @Override
    public void addAllPets(List<Pet> pets) {
        mDatabase.beginTransaction();
        try {
            mDatabase.petDao().addAllPets(pets);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    @Override
    public LiveData<List<Pet>> getPets() {
        return mDatabase.petDao().loadAllPets();
    }

    @Override
    public LiveData<Pet> getPet(String id) {
        return mDatabase.petDao().loadPetById(id);
    }

    @Override
    public void deletePet(Pet pet) {
        mDatabase.petDao().deletePet(pet);
    }

    @Override
    public void deletePetById(String id) {
        mDatabase.petDao().deletePetById(id);
    }

    @Override
    public void deleteAllPets() {
        mDatabase.petDao().deleteAllPets();
    }
}
