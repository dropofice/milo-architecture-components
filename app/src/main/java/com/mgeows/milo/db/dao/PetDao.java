package com.mgeows.milo.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mgeows.milo.db.entity.Pet;

import java.util.List;

@Dao
public interface PetDao {

    @Query("SELECT * FROM pets")
    LiveData<List<Pet>> loadAllPets();

    @Query("SELECT * FROM pets WHERE petId = :petId")
    LiveData<Pet> loadPetById(String petId);

    @Insert
    void addPet(Pet pet);

    @Insert
    void addAllPets(List<Pet> pets);

    @Update
    void updatePet(Pet pet);

    @Delete
    void deletePet(Pet pet);

    @Query("DELETE FROM pets WHERE petId = :petId")
    void deletePetById(String petId);
}
