package com.mgeows.milo.db.entity;

/**
 * Created by JC on 08/28/2017.
 */

public class Pet {

    private String petId;
    public String petName;
    public String petBreed;

    public Pet(String petName, String petBreed) {
        this.petName = petName;
        this.petBreed = petBreed;
    }
}
