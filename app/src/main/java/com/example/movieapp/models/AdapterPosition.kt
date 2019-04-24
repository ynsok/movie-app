package com.example.movieapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdapterPosition(val horizontalVerticalPosition: MutableList<Int>) :
    Parcelable