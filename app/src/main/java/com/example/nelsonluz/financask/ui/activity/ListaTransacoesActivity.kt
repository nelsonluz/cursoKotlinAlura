package com.example.nelsonluz.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.example.nelsonluz.financask.R
import com.example.nelsonluz.financask.delegate.TransacaoDelegate
import com.example.nelsonluz.financask.model.Tipo
import com.example.nelsonluz.financask.model.Transacao
import com.example.nelsonluz.financask.ui.ResumoView
import com.example.nelsonluz.financask.ui.adapter.ListaTransacoesAdapter
import com.example.nelsonluz.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity: AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()

    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.RECEITA)
                }
        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.DESPESA)
                }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
                .chama(tipo, object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        atualizaTransacoes(transacao)
                        lista_transacoes_adiciona_menu.close(true)
                    }
                })
    }


    private fun atualizaTransacoes(transacaoCriada: Transacao) {
        transacoes.add(transacaoCriada)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val decorView: View = window.decorView
        val resumoView = ResumoView(this, decorView, transacoes)
        resumoView.atualiza()
    }


    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

//    private fun transacoesDeExemplo(): List<Transacao> {
//        return listOf(
//                Transacao(valor = BigDecimal(100.0),
//                        tipo = Tipo.DESPESA,
//                        categoria = "Almoço de final de semana"),
//                Transacao(valor = BigDecimal(100.0),
//                        tipo = Tipo.RECEITA,
//                        categoria = "Economia"),
//                Transacao(valor = BigDecimal(100.0),
//                        tipo = Tipo.DESPESA),
//                Transacao(valor = BigDecimal(200.0),
//                        categoria = "Prêmio",
//                        tipo = Tipo.RECEITA))
//    }
}