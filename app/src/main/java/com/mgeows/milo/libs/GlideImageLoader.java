package com.mgeows.milo.libs;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestListener;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class GlideImageLoader implements ImageLoader {

    private RequestManager glideRequestManager;
    private RequestListener onFinishedLoadingListener;

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }

    @Override
    public void load(ImageView view, String path) {
        if (onFinishedLoadingListener != null) {
            glideRequestManager
                    .load(path)
                    .listener(onFinishedLoadingListener)
                    .into(view);
        } else {
            glideRequestManager
                    .load(URL)
                    .into(view);
        }
    }

    @Override
    public void setOnFinishedImageLoadingListener(Object listener) {
        if (listener instanceof  RequestListener) {
            this.onFinishedLoadingListener = (RequestListener) listener;
        }
    }
}
