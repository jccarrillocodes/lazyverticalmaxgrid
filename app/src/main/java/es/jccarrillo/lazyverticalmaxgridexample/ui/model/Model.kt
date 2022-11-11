package es.jccarrillo.lazyverticalmaxgridexample.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Model(
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String
) : Parcelable
