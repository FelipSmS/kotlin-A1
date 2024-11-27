package com.example.kotlin_a1.Modals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlin_a1.model.GeolocalizacaoSalva

@Composable
fun ModalEditarDados(
    geolocalizacaoSalva: GeolocalizacaoSalva,
    onClose: () -> Unit,
    onSave: (Int, String, String) -> Unit
) {
    var nome by remember { mutableStateOf(geolocalizacaoSalva.nome) }
    var descricao by remember { mutableStateOf(geolocalizacaoSalva.descricao) }

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(onClick = {
                onSave(geolocalizacaoSalva.id, nome, descricao) // Utiliza os valores editados
                onClose()
            }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onClose) {
                Text("Cancelar")
            }
        },
        title = {
            Text("Editar Geolocalização")
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    )
}
