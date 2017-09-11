package com.mgeows.milo.libs;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestListener;


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
                    .asBitmap()
                    .load(path)
                    .listener(onFinishedLoadingListener)
                    .into(view);
        } else {
            glideRequestManager
                    .asBitmap()
                    .load(path)
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
