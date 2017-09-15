package com.mgeows.milo.ui.addeditpet;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.mgeows.milo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ImageChooserFragment extends DialogFragment {

    @BindView(R.id.action_take_photo)
    TextView mTakePhoto;
//    @BindView(R.id.action_choose_image)
//    TextView mChooseImage;

    Unbinder unbinder;

    Listener mListener;

    public ImageChooserFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View imageChooser = getActivity()
                .getLayoutInflater().inflate(R.layout.image_source_chooser_dialog, null);
        unbinder = ButterKnife.bind(this, imageChooser);
        builder.setView(imageChooser)
               .setTitle("Add cute picture");
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @OnClick(R.id.action_take_photo)
    public void onTakePhotoClick() {
        mListener.onTakePhoto();
    }

//    @OnClick(R.id.action_choose_image)
//    public void onChooseImageClick() {
//        mListener.onChooseImage();
//    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    interface Listener {
        void onTakePhoto();
        void onChooseImage();
    }
}
