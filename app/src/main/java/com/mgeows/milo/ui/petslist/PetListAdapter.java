package com.mgeows.milo.ui.petslist;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgeows.milo.R;
import com.mgeows.milo.ui.db.entity.Pet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.ViewHolder> {

    private List<Pet> petList;

    public PetListAdapter(List<Pet> petList) {
        this.petList = petList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.pet_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.petName.setText(pet.petName);
        holder.petBreed.setText(pet.petBreed);
    }

    @Override
    public int getItemCount() {
        return petList == null ? 0 : petList.size();
    }

    public void setData(List<Pet> pets) {
        petList = null;
        petList = pets;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.petImg)
        ImageView petImg;
        @BindView(R.id.petName)
        TextView petName;
        @BindView(R.id.petBreed)
        TextView petBreed;
        @BindView(R.id.petImgAlert)
        ImageView petImgAlert;
        @BindView(R.id.vhContainer)
        CardView vhContainer;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
