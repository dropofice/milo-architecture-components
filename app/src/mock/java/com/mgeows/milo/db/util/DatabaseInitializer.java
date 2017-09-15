package com.mgeows.milo.db.util;

import android.support.annotation.NonNull;

import com.mgeows.milo.db.entity.Pet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class DatabaseInitializer {

    public static Pet getRandomPet() {
        Calendar calendar = Calendar.getInstance();
        int year = new Random().nextInt(6) + 2012;
        int month = new Random().nextInt(12);
        int day = new Random().nextInt(31);
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        String name = getName();
        String breed = getBreed();
        int gender = getGender();
        String weight = getWeight();
        String owner = getOwner();
        String address = getAddress();
        String contactNo = getContactNo();

        return new Pet(name, breed, gender, date, weight, owner, address, contactNo, null);
    }

    public static List<Pet> getRandomPets() {
        List<Pet> pets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Calendar calendar = Calendar.getInstance();
            int year = new Random().nextInt(5) + 2012;
            int month = new Random().nextInt(11) + 1;
            int day = new Random().nextInt(30) + 1;
            calendar.set(year, month, day);
            Date date = calendar.getTime();

            String name = getName();
            String breed = getBreed();
            int gender = getGender();
            String weight = getWeight();
            String owner = getOwner();
            String address = getAddress();
            String contactNo = getContactNo();

            Pet pet = new Pet(name, breed, gender, date, weight, owner, address, contactNo, null);
            pets.add(pet);
        }
        return pets;
    }

    @NonNull
    private static String getName() {
        int randomNum = new Random().nextInt(6);
        switch (randomNum) {
            case 0:
                return "Biscuit";
            case 1:
                return "Pupcake";
            case 2:
                return "Shadow";
            case 3:
                return "Milo";
            case 4:
                return "Tipo";
            default:
                return "Kira";
        }
    }

    @NonNull
    private static String getBreed() {
        int randomNum = new Random().nextInt(6);
        switch (randomNum) {
            case 0:
                return "Chow Chow";
            case 1:
                return "Akita";
            case 2:
                return "Maltese";
            case 3:
                return "Beagle";
            case 4:
                return "Aspin";
            default:
                return "Poodle";
        }
    }

    private static int getGender() {
        int randomNum = new Random().nextInt(3);
        switch (randomNum) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return 0;
        }
    }

    @NonNull
    private static String getWeight() {
        int randomNum = new Random().nextInt(6);
        switch (randomNum) {
            case 0:
                return "Biscuit";
            case 1:
                return "Pupcake";
            case 2:
                return "Shadow";
            case 3:
                return "Milo";
            case 4:
                return "Tipo";
            default:
                return "Kira";
        }
    }

    @NonNull
    private static String getOwner() {
        int randomNum = new Random().nextInt(6);
        switch (randomNum) {
            case 0:
                return "Adam";
            case 1:
                return "Dexter";
            case 2:
                return "Martha";
            case 3:
                return "Chuck";
            case 4:
                return "Sarah";
            default:
                return "Jacob";
        }
    }

    @NonNull
    private static String getAddress() {
        int randomNum = new Random().nextInt(4) + 1;
        switch (randomNum) {
            case 0:
                return "101 Beef Street, Cow City";
            case 1:
                return "201 Chicken Street, Fowl City";
            case 2:
                return "477 Dark Street, Light City";
            case 3:
                return "632 Hotdog Street, Spam City";
            case 4:
                return "380 Bark Street Howl City";
            default:
                return "516 First Street, Last City";
        }
    }

    @NonNull
    private static String getContactNo() {
        int randomNum = new Random().nextInt(6);
        switch (randomNum) {
            case 0:
                return "+63917-3458907";
            case 1:
                return "577-1578";
            case 2:
                return "0918-7568345";
            case 3:
                return "635-4160";
            case 4:
                return "0922-3804610";
            default:
                return "+63945-2684292";
        }
    }
}
