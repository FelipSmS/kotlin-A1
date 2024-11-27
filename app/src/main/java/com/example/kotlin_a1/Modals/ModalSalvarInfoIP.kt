package com.example.kotlin_a1.Modals

import GeolocalizacaoIP
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Salvar Geolocalização",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo de Nome
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Campo de Descrição
            TextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botões
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onClose,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Fechar")
                }
                Button(
                    onClick = {
                        saveToFirebase(resultado, nome, descricao)
                        onClose()
                    }
                ) {
                    Text("Salvar")
                }
            }
        }
    }
}

// Função para salvar no Firebase
private fun saveToFirebase(resultado: GeolocalizacaoIP, nome: String, descricao: String) {
    val database = Firebase.database.reference

    // Referência para o contador de IDs
    val idCounterRef = database.child("idCounter")

    idCounterRef.get()
        .addOnSuccessListener { snapshot ->
            var currentId = snapshot.getValue(Int::class.java) ?: 0

            currentId += 1
            idCounterRef.setValue(currentId)

            // Cria o mapa de dados com o ID incremental
            val data = mapOf(
                "id" to currentId,
                "nome" to nome,
                "descricao" to descricao,
                "resultado" to resultado.toMap()
            )

            // Salva o registro com o ID incremental no nó geolocalizacoes
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


