package com.mgeows.milo.data;

import android.arch.lifecycle.LiveData;

import com.mgeows.milo.db.AppDatabase;
import com.mgeows.milo.db.entity.Pet;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.functions.Action;


public class PetRepository implements PetDataSource {

    private AppDatabase database;

    public PetRepository(AppDatabase database) {
        this.database = database;
    }

    @Override
    public Completable addPet(final Pet pet) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                database.petDao().addPet(pet);
            }
        });
    }

    @Override
    public Completable addAllPets(List<Pet> pets) {
        return null;
    }

    @Override
    public LiveData<List<Pet>> getPets() {
        //Here is where we would do more complex logic, like getting events from a cache
        //then inserting into the database etc. In this example we just go straight to the dao.
        return database.petDao().loadAllPets();
    }

    @Override
    public Completable deletePet(final Pet pet) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                database.petDao().deletePet(pet);
            }
        });
    }
}
