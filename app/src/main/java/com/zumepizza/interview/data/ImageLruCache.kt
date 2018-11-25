package com.zumepizza.interview.data

import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader
import timber.log.Timber

/**
 * Created by Joe Wang on 2018/11/24.
 */
class ImageLruCache constructor(sizeInKB: Int = defaultLruSize):
    LruCache<String, Bitmap>(sizeInKB), ImageLoader.ImageCache {
    override fun sizeOf(key: String, value: Bitmap): Int {
        return value.rowBytes * value.height / 1024
    }
    override fun getBitmap(url: String): Bitmap? {
//        Timber.d("get(url) = " + get(url))
        return get(url)
    }

    override fun putBitmap(url: String?, bitmap: Bitmap?) {
//        Timber.d("url = " + url)
        put(url, bitmap)
    }
    companion object {
        val defaultLruSize: Int
            get() {
                val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
                Timber.d("maxMemory = " + maxMemory)
                return maxMemory / 16
            }
    }
}