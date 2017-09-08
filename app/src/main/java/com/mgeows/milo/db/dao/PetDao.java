package com.mgeows.milo.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mgeows.milo.db.entity.Pet;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PetDao {

    @Query("SELECT * FROM pets")
    LiveData<List<Pet>> loadAllPets();

    @Query("SELECT * FROM pets WHERE id = :id")
    LiveData<Pet> loadPetById(String id);

    @Insert(onConflict = REPLACE)
    void addPet(Pet pet);

    @Insert(onConflict = REPLACE)
    void addAllPets(List<Pet> pets);

    @Update(onConflict = REPLACE)
    void updatePet(Pet pet);

    @Delete
    void deletePet(Pet pet);

    @Query("DELETE FROM pets WHERE id = :id")
    void deletePetById(String id);

    @Query("DELETE FROM pets")
    void deleteAllPets();
}
