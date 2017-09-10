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
    @BindView(R.id.action_choose_image)
    TextView mChooseImage;

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
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.action_take_photo)
    public void onTakePhotoClick() {
        mListener.onTakePhoto();
    }

    @OnClick(R.id.action_choose_image)
    public void onChooseImageClick() {
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    interface Listener {
        void onTakePhoto();
    }

    //        private File createImageFile() throws IOException {
    //            // Create an image file name
    //            String timeStamp =
    //                    new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    //            String imageFileName = "PET_" + timeStamp;
    //            File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    //            sImageFile = File.createTempFile(
    //                    imageFileName,  /* prefix */
    //                    ".jpg",         /* suffix */
    //                    storageDir      /* directory */
    //            );
    //
    //            // Save a file: path for use with ACTION_VIEW intents
    //            sCurrentPhotoPath = "file:" + sImageFile.getAbsolutePath();
    //            return sImageFile;
    //        }
    //
    //        private void dispatchTakePhotoIntent() {
    //            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //            // Ensure that there's a camera activity to handle the intent
    //            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
    //                // Create the File where the photo should go
    //                File photoFile = null;
    //                try {
    //                    photoFile = createImageFile();
    //                } catch (IOException ex) {
    //                    // Error occurred while creating the File
    //                    Timber.e("Error Create File", ex.getLocalizedMessage());
    //                }
    //                // Continue only if the File was successfully created
    //                if (photoFile != null) {
    //                    Uri photoURI = FileProvider.getUriForFile(getContext(),
    //                                                              AUTHORITY,
    //                                                              photoFile);
    //                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
    //                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    //                }
    //            }
    //        }
    //    }
}
