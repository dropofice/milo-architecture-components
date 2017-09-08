package com.mgeows.milo.data;

import android.arch.lifecycle.LiveData;

import com.mgeows.milo.data.local.PetDataSource;
import com.mgeows.milo.db.entity.Pet;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.functions.Action;


public class PetRepositoryImpl implements PetRepository {

    private PetDataSource petDataSource;

    public PetRepositoryImpl(PetDataSource petDataSource) {
        this.petDataSource = petDataSource;
    }

    @Override
    public Completable addPet(final Pet pet) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                petDataSource.addPet(pet);
            }
        });
    }

    @Override
    public Completable updatePet(final Pet pet) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                petDataSource.updatePet(pet);
            }
        });
    }

    @Override
    public Completable addAllPets(final List<Pet> pets) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                petDataSource.addAllPets(pets);
            }
        });
    }

    @Override
    public LiveData<List<Pet>> getPets() {
        //Here is where we would do more complex logic, like getting events from a cache
        //then inserting into the database etc. In this example we just go straight to the dao.
        return petDataSource.getPets();
    }

    @Override
    public LiveData<Pet> getPet(String id) {
        return petDataSource.getPet(id);
    }

    @Override
    public Completable deletePet(final Pet pet) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                petDataSource.deletePet(pet);
            }
        });
    }

    @Override
    public Completable deletePetById(final String id) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                petDataSource.deletePetById(id);
            }
        });
    }

    @Override
    public Completable deleteAllPets() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                petDataSource.deleteAllPets();
            }
        });
    }
}
