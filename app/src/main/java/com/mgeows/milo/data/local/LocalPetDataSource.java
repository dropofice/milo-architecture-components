package com.mgeows.milo.data.local;


import android.arch.lifecycle.LiveData;

import com.mgeows.milo.db.dao.PetDao;
import com.mgeows.milo.db.entity.Pet;

import java.util.List;

public class LocalPetDataSource implements PetDataSource{

    private final PetDao mPetDao;

    public LocalPetDataSource(PetDao petDao) {
        this.mPetDao = petDao;
    }

    @Override
    public void addPet(Pet pet) {
        mPetDao.addPet(pet);
    }

    @Override
    public void addAllPets(List<Pet> pets) {
        mPetDao.addAllPets(pets);
    }

    @Override
    public LiveData<List<Pet>> getPets() {
        return mPetDao.loadAllPets();
    }

    @Override
    public void deletePet(Pet pet) {
        mPetDao.deletePet(pet);
    }
}
