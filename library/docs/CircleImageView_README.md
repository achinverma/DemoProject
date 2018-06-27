CircleImageView
===============

A circular ImageView perfect for profile images. 


As this is just a custom ImageView and not a custom Drawable or a combination of both, it can be used with all kinds of drawables, i.e. a PicassoDrawable from [Picasso](https://github.com/square/picasso).



Usage
-----
```xml
<com.signity.library.utils.CircleImageView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/profile_image"
    android:layout_width="96dp"
    android:layout_height="96dp"
    android:src="@drawable/profile"
    app:civ_border_width="2dp"
    app:civ_border_color="#FF000000"/>
```

Limitations
-----------
* The ScaleType is always CENTER_CROP and you'll get an exception if you try to change it. This is (currently) by design as it's perfectly fine for profile images.
* Enabling `adjustViewBounds` is not supported as this requires an unsupported ScaleType
* If you use an image loading library like Picasso or Glide, you need to disable their fade animations to avoid messed up images. For Picasso use the `noFade()` option, for Glide use `dontAnimate()`. If you want to keep the fadeIn animation, you have to fetch the image into a `Target` and apply a custom animation yourself when receiving the `Bitmap`.






