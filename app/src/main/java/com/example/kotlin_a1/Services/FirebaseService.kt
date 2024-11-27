package com.example.kotlin_a1.service

import com.example.kotlin_a1.model.GeolocalizacaoSalva
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseService {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    /**
     * Salvar dados com ID incremental.
     */
    fun salvarDadosGeolocalizacao(geolocalizacaoSalva: GeolocalizacaoSalva) {
        // Referência para o contador de IDs
        val idCounterRef = database.child("idCounter")

        // Obtenha o valor atual do contador
        idCounterRef.get()
            .addOnSuccessListener { snapshot ->
                var currentId = snapshot.getValue(Int::class.java) ?: 0

                // Incrementa o ID e salva o novo valor no contador
                currentId += 1
                idCounterRef.setValue(currentId)

                // Adiciona o ID incremental ao objeto e salva no banco
                val novoRegistro = geolocalizacaoSalva.copy(id = currentId)
                database.child("geolocalizacoes").child(currentId.toString()).setValue(novoRegistro)
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

    /**
     * Obter todos os dados salvos.
     */
    fun obterDadosSalvos(onResult: (List<GeolocalizacaoSalva>) -> Unit) {
        database.child("geolocalizacoes").get()
            .addOnSuccessListener { dataSnapshot ->
                val listaSalva = mutableListOf<GeolocalizacaoSalva>()
                for (child in dataSnapshot.children) {
                    val geolocalizacao = child.getValue(GeolocalizacaoSalva::class.java)
                    if (geolocalizacao != null) {
                        listaSalva.add(geolocalizacao)
                    }
                }
                onResult(listaSalva)
            }
            .addOnFailureListener { exception ->
                println("Erro ao obter dados: ${exception.message}")
                onResult(emptyList())
            }
    }

    /**
     * Remover registro pelo ID.
     */
    fun removerGeolocalizacao(id: Int) {
        database.child("geolocalizacoes").child(id.toString()).removeValue()
            .addOnSuccessListener {
                println("Registro removido com sucesso!")
            }
            .addOnFailureListener { e ->
                println("Erro ao remover registro: ${e.message}")
            }
    }

    /**
     * Atualizar nome e descrição do registro pelo ID.
     */
    fun atualizarGeolocalizacao(id: Int, nome: String, descricao: String) {
        val updates = mapOf<String, Any>(
            "nome" to nome,
            "descricao" to descricao
        )
        database.child("geolocalizacoes").child(id.toString()).updateChildren(updates)
            .addOnSuccessListener {
                println("Dados atualizados com sucesso!")
            }
            .addOnFailureListener { e ->
                println("Erro ao atualizar dados: ${e.message}")
            }
    }
}
