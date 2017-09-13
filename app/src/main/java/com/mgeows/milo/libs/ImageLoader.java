package com.mgeows.milo.libs;


import android.widget.ImageView;

public interface ImageLoader {
    void load(ImageView view, String path);
    void loadCircleCrop(ImageView view, String path);
    void setOnFinishedImageLoadingListener(Object listener);
}
