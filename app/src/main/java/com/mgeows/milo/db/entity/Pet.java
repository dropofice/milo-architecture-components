package com.mgeows.milo.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "pets")
public class Pet {

    @PrimaryKey
    public String petId;
    public String petName;
    public String petBreed;
    public int petGender;

    // Use this constructor for new pet
    public Pet(String petName, String petBreed) {
        this.petId = UUID.randomUUID().toString();
        this.petName = petName;
        this.petBreed = petBreed;
    }

    // Use this constructor for new pet
    @Ignore
    public Pet(String petName, String petBreed, int petGender) {
        this.petId = UUID.randomUUID().toString();
        this.petName = petName;
        this.petBreed = petBreed;
        this.petGender = petGender;
    }

    // Use this constructor for update pet
    @Ignore
    public Pet(String petId, String petName, String petBreed) {
        this.petId = petId;
        this.petName = petName;
        this.petBreed = petBreed;
    }
}
