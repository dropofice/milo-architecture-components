package com.mgeows.milo.libs;


import android.widget.ImageView;

public interface ImageLoader {
    void load(ImageView imageView, String path);
    void setOnFinishedImageLoadingListener(Object listener);
}
