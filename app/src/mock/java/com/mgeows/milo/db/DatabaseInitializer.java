package com.mgeows.milo.db;

import android.content.Context;

import com.mgeows.milo.db.entity.Pet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JC on 09/01/2017.
 */

public class DatabaseInitializer {

    public void initData(Context context) {

        Pet petA = new Pet("Milo", "Beagle");
        Pet petB = new Pet("Pogi", "Aspin");
        Pet petC = new Pet("Tipo", "Mix");
        Pet petD = new Pet("Shadow", "Aspin");
        Pet petE = new Pet("Chelly", "Siberian");
        Pet petF = new Pet("MuyMuy", "German Sheperd");
        List<Pet> pets = new ArrayList<>();
        pets.add(petA);
        pets.add(petB);
        pets.add(petC);
        pets.add(petD);
        pets.add(petE);
        pets.add(petF);

    }
}
