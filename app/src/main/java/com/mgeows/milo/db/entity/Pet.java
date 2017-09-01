package com.mgeows.milo.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "pets")
public class Pet {

    @PrimaryKey
    public String petId;
    public String petName;
    public String petBreed;

    public Pet(String petName, String petBreed) {
        this.petId = UUID.randomUUID().toString();
        this.petName = petName;
        this.petBreed = petBreed;
    }
}
