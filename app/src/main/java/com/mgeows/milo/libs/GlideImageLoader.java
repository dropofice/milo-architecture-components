package com.mgeows.milo.libs;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.mgeows.milo.R;


public class GlideImageLoader implements ImageLoader {

    private RequestManager glideRequestManager;
    private RequestListener onFinishedLoadingListener;

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }

    @Override
    public void load(ImageView view, String path) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_take_photo);
        if (onFinishedLoadingListener != null) {
            glideRequestManager
                    .asBitmap()
                    .load(path)
                    .apply(options)
                    .listener(onFinishedLoadingListener)
                    .into(view);
        } else {
            glideRequestManager
                    .asBitmap()
                    .load(path)
                    .apply(options)
                    .into(view);
        }
    }

    @Override
    public void loadCircleCrop(ImageView view, String path) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_take_photo)
               .circleCrop();
        glideRequestManager
                .asBitmap()
                .load(path)
                .apply(options)
                .into(view);
    }

    @Override
    public void setOnFinishedImageLoadingListener(Object listener) {
        if (listener instanceof  RequestListener) {
            this.onFinishedLoadingListener = (RequestListener) listener;
        }
    }
}
