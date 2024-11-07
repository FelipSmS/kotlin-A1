package com.example.kotlin_a1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_a1.Domain.IPGeolocation
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TelaGeolocalizacaoIP()
            }
        }
    }

    @Composable
    fun TelaGeolocalizacaoIP() {
        var enderecoIP by remember { mutableStateOf(TextFieldValue("")) }
        var textoResultado by remember { mutableStateOf("Informações do IP serão exibidas aqui") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Digite o endereço IP:",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            BasicTextField(
                value = enderecoIP,
                onValueChange = { novoValor ->
                    enderecoIP = formatarEntradaIP(novoValor)
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                    .padding(16.dp),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (enderecoIP.text.isNotBlank()) {
                    buscarGeolocalizacaoIP(enderecoIP.text) { resultado ->
                        textoResultado = resultado
                    }
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Buscar Geolocalização")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = textoResultado,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

    private fun buscarGeolocalizacaoIP(ip: String, onResult: (String) -> Unit) {
        val servicoGeolocalizacao = IPGeolocation()

        lifecycleScope.launch {
            try {
                val resultado = servicoGeolocalizacao.getIpInfo(ip)
                if (resultado != null) {
                    val informacoes = """
                        IP: ${resultado.query}
                        Status: ${resultado.status}
                        País: ${resultado.country}
                        Região: ${resultado.regionName}
                        Cidade: ${resultado.city}
                        CEP: ${resultado.zip}
                        Latitude: ${resultado.lat}
                        Longitude: ${resultado.lon}
                        Fuso Horário: ${resultado.timezone}
                        Provedor: ${resultado.isp}
                        Organização: ${resultado.org}
                    """.trimIndent()
                    onResult(informacoes)
                } else {
                    onResult("Erro ao buscar informações de geolocalização.")
                }
            } catch (e: Exception) {
                onResult("Erro: ${e.message}")
            }
        }
    }

    private fun formatarEntradaIP(entrada: TextFieldValue): TextFieldValue {
        val digitos = entrada.text.filter { it.isDigit() || it == '.' }
        val segmentos = digitos.split(".").take(4) // Máximo de 4 segmentos para IP
        val textoFormatado = segmentos.joinToString(".").take(15)
        val novaPosicaoCursor = textoFormatado.length.coerceAtMost(15)

        return TextFieldValue(
            text = textoFormatado,
            selection = TextRange(novaPosicaoCursor)
        )
    }
}
