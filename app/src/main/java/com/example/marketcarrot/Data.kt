package com.example.marketcarrot

import androidx.annotation.DrawableRes

data class Data(
    var Title: String,
    var Info: String,
    var Price: String,
    @DrawableRes
    var Image: Int
)