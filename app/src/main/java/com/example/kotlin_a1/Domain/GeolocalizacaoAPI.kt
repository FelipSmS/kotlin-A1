package com.example.kotlin_a1.Domain

import GeolocalizacaoIP
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class GeolocalizacaoAPI {

    private val urlBase: String = "https://api.ipgeolocation.io/ipgeo"
    private val chaveApi: String = "2bdb1a756a3145c19474525c92a483f7" // Substitua pela sua chave de API

    fun obterInformacoesIP(ip: String?): GeolocalizacaoIP? {
        val cliente = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true // Ignora campos extras no JSON da resposta
                })
            }
        }

        return runBlocking {
            val url = if (ip.isNullOrBlank()) "$urlBase?apiKey=$chaveApi" else "$urlBase?apiKey=$chaveApi&ip=$ip"
            try {
                val resposta = cliente.get(url).body<GeolocalizacaoIP>()

                resposta
            } catch (excecao: Exception) {
                println("Erro na requisição: ${excecao.message}")
                null
            } finally {
                cliente.close()
            }
        }
    }
}
