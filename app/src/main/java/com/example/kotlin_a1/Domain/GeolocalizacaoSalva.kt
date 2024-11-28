package com.example.kotlin_a1.model

import GeolocalizacaoIP
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class GeolocalizacaoSalva @JvmOverloads constructor(
    var id: Int = 0,
    var nome: String = "",
    var descricao: String = "",
    var resultado: GeolocalizacaoIP? = null
)