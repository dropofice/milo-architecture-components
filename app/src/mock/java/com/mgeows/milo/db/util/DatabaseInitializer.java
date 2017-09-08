package com.mgeows.milo.db.util;

import com.mgeows.milo.db.entity.Pet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class DatabaseInitializer {

    public static List<Pet> initData() {
        List<Pet> pets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Calendar calendar = Calendar.getInstance();
            int year = new Random().nextInt(5) + 2012;
            int month = new Random().nextInt(11) + 1;
            int day = new Random().nextInt(30) + 1;
            // int picker = new Random().nextInt(5) + 1;
            calendar.set(year, month, day);
            Date date = calendar.getTime();

            String name = "Argo";
            String breed = "Alaskan Malamute";
            int gender = 1;
            String weight = "8";
            String owner = "Alex";
            String address = "909 Anchorage, Alaska";
            String contactNo = "+63918-1234567";

            Pet pet = new Pet(name, breed, gender, date, weight, owner, address, contactNo);
            pets.add(pet);
        }
        return pets;
    }
}
