package com.example.nelsonluz.financask.ui.activity

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.nelsonluz.financask.R
import com.example.nelsonluz.financask.extension.formataParaBrasileiro
import com.example.nelsonluz.financask.model.Tipo
import com.example.nelsonluz.financask.model.Transacao
import com.example.nelsonluz.financask.ui.ResumoView
import com.example.nelsonluz.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity: AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraLista()

        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    val decorView: View = window.decorView
                    val viewCriada = LayoutInflater.from(this)
                            .inflate(R.layout.form_transacao,
                                    decorView as ViewGroup,
                                    false)

                    val ano = 2017
                    val mes = 9
                    val dia = 18
                    val hoje = Calendar.getInstance()
                    viewCriada.form_transacao_data.setText(hoje.formataParaBrasileiro())
                    viewCriada.form_transacao_data
                            .setOnClickListener{
                                DatePickerDialog(this,
                                        DatePickerDialog.OnDateSetListener { view, ano, mes, dia ->
                                            val dataSelecionada = Calendar.getInstance()
                                            dataSelecionada.set(ano,mes, dia)
                                            viewCriada.form_transacao_data
                                                    .setText(dataSelecionada.formataParaBrasileiro())
                                        }
                                        , ano, mes, dia)
                                        .show()
                    }

                    val adapter = ArrayAdapter
                            .createFromResource(this,
                                    R.array.categorias_de_receita,
                                    android.R.layout.simple_spinner_dropdown_item)
                    viewCriada.form_transacao_categoria.adapter = adapter


                    AlertDialog.Builder(this)
                            .setTitle(R.string.adiciona_receita)
                            .setView(viewCriada)
                            .setPositiveButton("Adicionar",
                               { dialogInterface, which ->
                                    val valorEmTexto = viewCriada
                                            .form_transacao_valor.text.toString()
                                    val dataEmTexto = viewCriada
                                            .form_transacao_data.text.toString()
                                    val categoriaEmTexto = viewCriada
                                            .form_transacao_categoria.selectedItem.toString()

                                   val valor = try {
                                       BigDecimal(valorEmTexto)
                                   } catch (exception: NumberFormatException) {
                                       Toast.makeText(this,
                                               "Falha na Conversão de valor",
                                               Toast.LENGTH_LONG)
                                               .show()
                                       BigDecimal.ZERO
                                   }

                                    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                                    val dataConvertida: Date = formatoBrasileiro.parse(dataEmTexto)
                                    val data = Calendar.getInstance()
                                    data.time = dataConvertida

                                    val transacaoCriada = Transacao(
                                            tipo = Tipo.RECEITA,
                                            valor = valor,
                                            data = data,
                                            categoria = categoriaEmTexto)

                                    atualizaTransacoes(transacaoCriada)
                                    lista_transacoes_adiciona_menu.close(true)
                            })
                            .setNegativeButton("Cancelar", null)
                            .show()
                }

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