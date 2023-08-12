package com.abelvolpi.sqliteapp.ext

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImageUsingGlide(context: Context, imageUri: String) {
    Glide.with(context).load(imageUri)
        .into(this)
}