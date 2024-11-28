package com.example.kotlin_a1.Modals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlin_a1.model.GeolocalizacaoSalva
import io.ktor.websocket.Frame

@Composable
fun ModalMostrarInfoIp(
    geolocalizacaoSalva: GeolocalizacaoSalva,
    onClose: () -> Unit
)
 {
     AlertDialog(
         onDismissRequest = onClose,
         confirmButton = {
             Button(onClick = onClose) {
                 Text("Fechar")
             }
         },
         text = {
             Column {
                 Text("Detalhes da Geolocalização", style = MaterialTheme.typography.titleLarge)
                 Spacer(modifier = Modifier.height(16.dp))

                 Text("Nome: ${geolocalizacaoSalva.nome}", style = MaterialTheme.typography.bodyMedium)
                 Text("Descrição: ${geolocalizacaoSalva.descricao}", style = MaterialTheme.typography.bodyMedium)
                 Text("IP: ${geolocalizacaoSalva.resultado?.ip}", style = MaterialTheme.typography.bodyMedium)
                 Text("Hostname: ${geolocalizacaoSalva.resultado?.hostname}", style = MaterialTheme.typography.bodyMedium)
                 Text("Código do Continente: ${geolocalizacaoSalva.resultado?.continent_code}", style = MaterialTheme.typography.bodyMedium)
                 Text("Nome do Continente: ${geolocalizacaoSalva.resultado?.continent_name}", style = MaterialTheme.typography.bodyMedium)
                 Text("Código do País: ${geolocalizacaoSalva.resultado?.country_code2}", style = MaterialTheme.typography.bodyMedium)
                 Text("Estado/Província: ${geolocalizacaoSalva.resultado?.state_prov}", style = MaterialTheme.typography.bodyMedium)
                 Text("Cidade: ${geolocalizacaoSalva.resultado?.city}", style = MaterialTheme.typography.bodyMedium)
                 Text("Latitude: ${geolocalizacaoSalva.resultado?.latitude}", style = MaterialTheme.typography.bodyMedium)
                 Text("Longitude: ${geolocalizacaoSalva.resultado?.longitude}", style = MaterialTheme.typography.bodyMedium)
                 Text("ISP: ${geolocalizacaoSalva.resultado?.isp}", style = MaterialTheme.typography.bodyMedium)
                 Text("Emoji do País: ${geolocalizacaoSalva.resultado?.country_emoji}", style = MaterialTheme.typography.bodyMedium)
                 Text("Organização: ${geolocalizacaoSalva.resultado?.organization}", style = MaterialTheme.typography.bodyMedium)
             }
         }
     )

}
