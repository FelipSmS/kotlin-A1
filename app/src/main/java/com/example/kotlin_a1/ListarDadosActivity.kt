package com.example.kotlin_a1

import com.example.kotlin_a1.Modals.ModalMostrarInfoIp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.kotlin_a1.Modals.ModalEditarDados
import com.example.kotlin_a1.model.GeolocalizacaoSalva
import com.example.kotlin_a1.service.FirebaseService

class ListarDadosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListarDadosScreen()
        }
    }
}

@Composable
fun ListarDadosScreen() {
    var dadosSalvos by remember { mutableStateOf<List<GeolocalizacaoSalva>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var registroSelecionado by remember { mutableStateOf<GeolocalizacaoSalva?>(null) }
    var showModalDetalhes by remember { mutableStateOf(false) }
    var showModalEditar by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        FirebaseService.obterDadosSalvos { dados ->
            dadosSalvos = dados
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Button(
                onClick = { (context as? ComponentActivity)?.finish() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Voltar para a Tela Principal")
            }

            Text(text = "Dados Salvos", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(dadosSalvos) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            // Clique na coluna para mostrar os detalhes
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        registroSelecionado = item
                                        showModalDetalhes = true
                                    }
                            ) {
                                Text(item.nome, style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(item.descricao, style = MaterialTheme.typography.bodyMedium)
                            }

                            // Ícones de Editar e Excluir
                            Row {
                                IconButton(onClick = {
                                    registroSelecionado = item
                                    showModalEditar = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                                }
                                IconButton(onClick = {
                                    FirebaseService.removerGeolocalizacao(item.id)
                                    dadosSalvos = dadosSalvos.filter { it.id != item.id }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Excluir")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal para exibir os detalhes
    if (showModalDetalhes && registroSelecionado != null) {
        ModalMostrarInfoIp(
            geolocalizacaoSalva = registroSelecionado!!,
            onClose = { showModalDetalhes = false }
        )
    }

    if (showModalEditar && registroSelecionado != null) {
        ModalEditarDados(
            geolocalizacaoSalva = registroSelecionado!!,
            onClose = { showModalEditar = false },
            onSave = { id, nome, descricao ->
                FirebaseService.atualizarGeolocalizacao(id, nome, descricao)
                // Atualizar lista localmente
                dadosSalvos = dadosSalvos.map {
                    if (it.id == id) it.copy(nome = nome, descricao = descricao) else it
                }
            }
        )
    }

}
