package com.mgeows.milo.ui.petslist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.mgeows.milo.data.PetDataSource;
import com.mgeows.milo.data.PetRepository;
import com.mgeows.milo.db.AppDatabase;
import com.mgeows.milo.db.entity.Pet;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class PetListViewModel extends AndroidViewModel {

    private PetDataSource dataSource;

    public PetListViewModel(Application application) {
        super(application);

        AppDatabase db = AppDatabase.getInstance(getApplication());
        dataSource = new PetRepository(db);

    }

    public LiveData<List<Pet>> getPets() {
        return dataSource.getPets();
    }

    public void insertPet(final Pet pet) {
        dataSource.addPet(pet)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeOn(Schedulers.io())
                  .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Timber.d("onComplete - successfully added Pet");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.d("onError - add:", e);
            }
        });
    }

    public void deletetPet(final Pet pet) {
        dataSource.deletePet(pet)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeOn(Schedulers.io())
                  .subscribe(new CompletableObserver() {
                      @Override
                      public void onSubscribe(@NonNull Disposable d) {

                      }

                      @Override
                      public void onComplete() {
                          Timber.d("onComplete - successfully deleted Pet");
                      }

                      @Override
                      public void onError(@NonNull Throwable e) {
                          Timber.d("onError - delete:", e);
                      }
                  });
    }
}
