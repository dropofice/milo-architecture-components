package com.mgeows.milo.db.util;

import com.mgeows.milo.db.entity.Pet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DatabaseInitializer {

    public static List<Pet> initData() {
        List<Pet> pets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(2015 - i, 5 + i, 24 - i);
            Date date = calendar.getTime();
            Pet pet = new Pet("Max", "Belgian", 0, date, "7", "Jude", "567 Texas, Usa", "+6319-4567890");
            pets.add(pet);
        }
        return pets;
    }
}
