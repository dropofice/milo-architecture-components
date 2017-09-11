package com.mgeows.milo.ui.petslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgeows.milo.R;
import com.mgeows.milo.db.entity.Pet;
import com.mgeows.milo.libs.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PetListAdapter extends RecyclerView.Adapter<PetListAdapter.ViewHolder> {

    private List<Pet> petList = new ArrayList<>();
    private ImageLoader imageLoader;
    private PetItemClickListener petItemClickListener;

    @Inject
    public PetListAdapter(List<Pet> petList, ImageLoader imageLoader, PetItemClickListener listener) {
        this.petList = petList;
        this.imageLoader = imageLoader;
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
        holder.itemView.setTag(pet.id);
        imageLoader.load(holder.petImg, pet.imagePath);
        holder.petName.setText(pet.name);
        holder.petBreed.setText(pet.breed);
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
           ids.add(pet.id);
        }
        return ids;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.img_item_pet)
        ImageView petImg;
        @BindView(R.id.tv_item_name)
        TextView petName;
        @BindView(R.id.tv_item_breed)
        TextView petBreed;

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
