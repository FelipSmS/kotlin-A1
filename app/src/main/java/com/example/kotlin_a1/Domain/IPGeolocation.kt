package com.example.kotlin_a1.Domain

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class IPGeolocation {

    private val baseUrl: String = "http://ip-api.com/json/"

    fun getIpInfo(ip: String): LocalizacaoIP? {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true // Ignora campos extras no JSON da resposta
                })
            }
        }

        return runBlocking {
            val url = "$baseUrl$ip?lang=pt-BR"
            try {
                val retorno = client.get(url).body<LocalizacaoIP>()

                retorno
            } catch (e: Exception) {
                println("Erro na requisição: ${e.message}")
                null
            } finally {
                client.close()
            }
        }
    }

}
