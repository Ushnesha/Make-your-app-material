package com.example.xyzreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class ImageLoaderHelper {
    private static ImageRequest sInstance;

    public static void load(final SimpleDraweeView imageView, final String url) {
        sInstance = ImageRequest.fromUri(url);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(sInstance);
        BinaryResource resource = ImagePipelineFactory.getInstance().getMainDiskStorageCache().getResource(cacheKey);

        if (resource != null) {
            Uri uri = Uri.parse(url);
            DraweeController draweeController = Fresco.newDraweeControllerBuilder().setUri(uri)
                    .setUri(uri)
                    .build();

            imageView.setController(draweeController);
        } else {
            networkLoad(imageView, url);
        }

    }

    private static  void networkLoad(final SimpleDraweeView imgView, final String url){

        Uri uri = Uri.parse(url);

        sInstance = ImageRequestBuilder.newBuilderWithSource(uri).setProgressiveRenderingEnabled(true).build();

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setImageRequest(sInstance)
                .setTapToRetryEnabled(true)
                .build();

        imgView.setController(draweeController);

    }

    public static ImageRequest getCurrentImageRequest(){
        return sInstance;
    }

}



