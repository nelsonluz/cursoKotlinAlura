package com.example.nelsonluz.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.nelsonluz.financask.R
import com.example.nelsonluz.financask.extension.formataParaBrasileiro
import com.example.nelsonluz.financask.model.Resumo
import com.example.nelsonluz.financask.model.Tipo
import com.example.nelsonluz.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val context: Context,
                 private val view: View,
                 transacoes: List<Transacao>) {

    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza(){
        adicionaReceita()
        adicionaDespesa()
        adcionaTotal()
    }

    private fun adicionaReceita() {

        val totalReceita = resumo.receita
        with(view.resumo_card_receita){
            setTextColor(corReceita)
            text = totalReceita.formataParaBrasileiro()
        }
    }


    private fun adicionaDespesa() {

         val totalDespesas = resumo.despesa
        with(view.resumo_card_despesa){
            setTextColor(corDespesa)
            text = totalDespesas.formataParaBrasileiro()
        }
    }

    private fun adcionaTotal(){
        val total = resumo.total
        val cor = corPor(total)
        with(view.resumo_card_total){
            setTextColor(cor)
            text = resumo.total.formataParaBrasileiro()
        }

    }

    private fun corPor(total: BigDecimal): Int {
        if (total >= BigDecimal.ZERO) {
           return corReceita
        }
        return corDespesa
    }

}