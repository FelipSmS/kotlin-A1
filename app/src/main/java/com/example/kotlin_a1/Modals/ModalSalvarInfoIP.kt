package com.example.kotlin_a1.Modals

import GeolocalizacaoIP
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.database.database

@Composable
fun ModalSaveData(
    resultado: GeolocalizacaoIP,
    onClose: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(onClick = {
                saveToFirebase(resultado, nome, descricao)
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
            Text("Salvar Geolocalização")
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

private fun saveToFirebase(resultado: GeolocalizacaoIP, nome: String, descricao: String) {
    val database = Firebase.database.reference

    val idCounterRef = database.child("idCounter")

    idCounterRef.get()
        .addOnSuccessListener { snapshot ->
            var currentId = snapshot.getValue(Int::class.java) ?: 0

            currentId += 1
            idCounterRef.setValue(currentId)

            val data = mapOf(
                "id" to currentId,
                "nome" to nome,
                "descricao" to descricao,
                "resultado" to resultado.toMap()
            )

            database.child("geolocalizacoes").child(currentId.toString()).setValue(data)
                .addOnSuccessListener {
                    println("Dados salvos com sucesso com ID incremental!")
                }
                .addOnFailureListener { e ->
                    println("Erro ao salvar dados: ${e.message}")
                }
        }
        .addOnFailureListener { e ->
            println("Erro ao acessar contador de IDs: ${e.message}")
        }
}
