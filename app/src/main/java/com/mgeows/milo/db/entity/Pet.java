package com.mgeows.milo.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "pets")
public class Pet {

    @PrimaryKey
    public String id;
    public String name;
    public String breed;
    public int gender;
    public Date birthDate;
    public String weight;
    public String owner;
    public String address;
    public String contactNo;

    public Pet() {
    }

    // Use this constructor for new pet
    @Ignore
    public Pet(String name, String breed, int gender, Date date, String weight, String owner, String address,
               String contactNo) {
        this(UUID.randomUUID().toString(), name, breed, gender, date, weight, owner, address, contactNo);
    }

    // Use this constructor for update pet
    @Ignore
    public Pet(String id, String name, String breed, int gender, Date date, String weight, String owner,
               String address, String contactNo) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.birthDate = date;
        this.weight = weight;
        this.owner = owner;
        this.address = address;
        this. contactNo = contactNo;
    }
}
