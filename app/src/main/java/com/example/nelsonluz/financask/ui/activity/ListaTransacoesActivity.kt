package com.example.nelsonluz.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.nelsonluz.financask.R
import com.example.nelsonluz.financask.dao.TransacaoDAO
import com.example.nelsonluz.financask.model.Tipo
import com.example.nelsonluz.financask.model.Transacao
import com.example.nelsonluz.financask.ui.ResumoView
import com.example.nelsonluz.financask.ui.adapter.ListaTransacoesAdapter
import com.example.nelsonluz.financask.ui.dialog.AdicionaTransacaoDialog
import com.example.nelsonluz.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity: AppCompatActivity() {

    private val dao = TransacaoDAO()
    private val transacoes = dao.transacoes
    private val viewDaActivity: View by lazy {
        window.decorView
    }
    private val viewGroupDoActivity by lazy {
        viewDaActivity as ViewGroup
    }

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
        AdicionaTransacaoDialog(viewGroupDoActivity, this)
                .chama(tipo) {transacaoCriada ->
                        adiciona(transacaoCriada)
                        lista_transacoes_adiciona_menu.close(true)
                }
    }

    private fun adiciona(transacao: Transacao) {
        dao.adciona(transacao)
        atualizaTransacoes()
    }


    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewDaActivity, transacoes)
        resumoView.atualiza()
    }


    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview){
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, posicao, _ ->
                val transacao = transacoes[posicao]
                chamaDiologDeAlteracao(transacao, posicao)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE,1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDoMenu = item?.itemId
        if (idDoMenu == 1){
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val positionDaTransacao = adapterMenuInfo.position
            removeTransacao(positionDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun removeTransacao(positionDaTransacao: Int) {
        dao.remove(positionDaTransacao)
        atualizaTransacoes()
    }

    private fun chamaDiologDeAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewGroupDoActivity, this)
                .chama(transacao) { transacaoAlterada ->
                    altera(transacaoAlterada, posicao)
                }
    }

    private fun altera(transacao: Transacao, posicao: Int) {
        dao.altera(transacao, posicao)
        atualizaTransacoes()
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