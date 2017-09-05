package com.mgeows.milo.ui.petslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgeows.milo.R;
import com.mgeows.milo.db.entity.Pet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.ViewHolder> {

    private List<Pet> petList = new ArrayList<>();
    private PetItemClickListener petItemClickListener;

    public PetListAdapter(List<Pet> petList, PetItemClickListener listener) {
        this.petList = petList;
        this.petItemClickListener = listener;
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
        holder.itemView.setTag(pet.petId);
        holder.petName.setText(pet.petName);
        holder.petBreed.setText(pet.petBreed);
    }

    @Override
    public int getItemCount() {
        return petList == null ? 0 : petList.size();
    }

    public void setData(List<Pet> pets) {
        if (petList != null && petList.size() > 0) {
            petList.clear();
        }
        petList = pets;
        notifyDataSetChanged();
    }

    private ArrayList<String> getIdsMapToPosition() {
        ArrayList<String> ids = new ArrayList<>();
        for (Pet pet : petList) {
           ids.add(pet.petId);
        }
        return ids;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.petImg)
        ImageView petImg;
        @BindView(R.id.petName)
        TextView petName;
        @BindView(R.id.petBreed)
        TextView petBreed;
        @BindView(R.id.petImgAlert)
        ImageView petImgAlert;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ArrayList<String> ids = getIdsMapToPosition();
            petItemClickListener.onItemClick(position, ids);
        }
    }
}
