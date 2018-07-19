package com.example.nelsonluz.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.nelsonluz.financask.R
import com.example.nelsonluz.financask.extension.formataParaBrasileiro
import com.example.nelsonluz.financask.model.Tipo
import com.example.nelsonluz.financask.model.Transacao

class AlteraTransacaoDialog(
        viewGroup: ViewGroup,
        private val context: Context): FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.altera_receita
        }
        return  R.string.altera_despesa
    }

    fun chama(transacao: Transacao,
              delegate: (transacao: Transacao) -> Unit) {

        val tipo = transacao.tipo
        super.chama(tipo, delegate)

        inicializaCampos(transacao, tipo)
    }

    private fun inicializaCampos(transacao: Transacao, tipo: Tipo) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(transacao)
    }

    private fun inicializaCampoCategoria(transacao: Transacao) {
        val tipo = transacao.tipo
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        camporValor.setText(transacao.valor.toString())
    }
}