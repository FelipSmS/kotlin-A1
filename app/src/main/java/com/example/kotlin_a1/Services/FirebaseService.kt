package com.example.kotlin_a1.service

import com.example.kotlin_a1.model.GeolocalizacaoSalva
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseService {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference


    fun salvarDadosGeolocalizacao(geolocalizacaoSalva: GeolocalizacaoSalva) {
        val idCounterRef = database.child("idCounter")

        idCounterRef.get()
            .addOnSuccessListener { snapshot ->
                var currentId = snapshot.getValue(Int::class.java) ?: 0

                currentId += 1
                idCounterRef.setValue(currentId)

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


    fun removerGeolocalizacao(id: Int) {
        database.child("geolocalizacoes").child(id.toString()).removeValue()
            .addOnSuccessListener {
                println("Registro removido com sucesso!")
            }
            .addOnFailureListener { e ->
                println("Erro ao remover registro: ${e.message}")
            }
    }


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
