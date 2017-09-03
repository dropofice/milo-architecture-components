package com.mgeows.milo.data.local;

import android.arch.lifecycle.LiveData;

import com.mgeows.milo.db.entity.Pet;

import java.util.List;

/**
 * Created by JC on 09/03/2017.
 */

public interface PetDataSource {

    void addPet(Pet pet);

    void addAllPets(List<Pet> pets);

    LiveData<List<Pet>> getPets();

    void deletePet(Pet pet);
}
