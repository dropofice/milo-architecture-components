package com.mgeows.milo.data.local;


import android.arch.lifecycle.LiveData;

import com.mgeows.milo.db.dao.PetDao;
import com.mgeows.milo.db.entity.Pet;

import java.util.List;

public class LocalPetDataSource implements PetDataSource{

    PetDao mPetDao;

    public LocalPetDataSource(PetDao petDao) {
        this.mPetDao = petDao;
    }

    @Override
    public void addPet(Pet pet) {
        mPetDao.addPet(pet);
    }

    @Override
    public void updatePet(Pet pet) {
        mPetDao.updatePet(pet);
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
    public LiveData<Pet> getPet(String id) {
        return mPetDao.loadPetById(id);
    }

    @Override
    public void deletePet(Pet pet) {
        mPetDao.deletePet(pet);
    }

    @Override
    public void deletePetById(String id) {
        mPetDao.deletePetById(id);
    }
}
