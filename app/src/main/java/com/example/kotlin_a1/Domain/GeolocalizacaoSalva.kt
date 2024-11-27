package com.example.kotlin_a1.model

import GeolocalizacaoIP
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class GeolocalizacaoSalva @JvmOverloads constructor(
    var id: Int = 0,                // ID com valor padrão
    var nome: String = "",          // Nome com valor padrão
    var descricao: String = "",     // Descrição com valor padrão
    var resultado: GeolocalizacaoIP? = null // Objeto opcional
)