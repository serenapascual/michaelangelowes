package com.serenapascual.michaelangelowes.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PhotoViewModel:ViewModel() {

    val imageUri = mutableStateOf("")

}