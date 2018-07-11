package com.example.nelsonluz.financask.ui

import android.view.View
import com.example.nelsonluz.financask.extension.formataParaBrasileiro
import com.example.nelsonluz.financask.model.Tipo
import com.example.nelsonluz.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val view: View,
                 private val transacoes: List<Transacao>) {

    fun adicionaReceita() {
        var totalReceita = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.RECEITA) {
                totalReceita = totalReceita.plus(transacao.valor)
            }

        }
        view.resumo_card_receita.text = totalReceita.formataParaBrasileiro()
    }

     fun adicionaDespesa() {
        var totalDespesas = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.DESPESA) {
                totalDespesas = totalDespesas.plus(transacao.valor)
            }

        }
        view.resumo_card_despesa.text = totalDespesas.formataParaBrasileiro()
    }

}