package com.mgeows.milo.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mgeows.milo.data.PetRepository;
import com.mgeows.milo.db.entity.Pet;
import com.mgeows.milo.di.PetComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class PetViewModel extends ViewModel implements PetComponent.Injectable {

    @Inject
    PetRepository mPetRepository;

    private LiveData<List<Pet>> mLivePets;
    private LiveData<Pet> mLivePet;

    public PetViewModel() {
        mLivePets = new MutableLiveData<>();
        mLivePet = new MutableLiveData<>();
    }

    public LiveData<List<Pet>> getPets() {
        mLivePets = mPetRepository.getPets();
        return mLivePets;
    }

    public LiveData<Pet> getPet(String id) {
        mLivePet = mPetRepository.getPet(id);
        return mLivePet;
    }

    public void insertPet(final Pet pet) {
        mPetRepository.addPet(pet)
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

    public void updatePet(final Pet pet) {
        mPetRepository.updatePet(pet)
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribeOn(Schedulers.io())
                      .subscribe(new CompletableObserver() {
                          @Override
                          public void onSubscribe(@NonNull Disposable d) {

                          }

                          @Override
                          public void onComplete() {
                              Timber.d("onComplete - successfully updated Pet");
                          }

                          @Override
                          public void onError(@NonNull Throwable e) {
                              Timber.d("onError - update:", e);
                          }
                      });
    }

    public void deletetPet(final Pet pet) {
        mPetRepository.deletePet(pet)
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

    public void deletePetById(final String id) {
        mPetRepository.deletePetById(id)
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

    @Override
    public void inject(PetComponent petComponent) {
        petComponent.inject(this);
    }
}
