package com.marknjunge.musicapp

import androidx.annotation.DrawableRes

data class Song(val title: String, val artists: String, val duration: String, @DrawableRes val cover: Int)