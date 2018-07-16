package com.example.nelsonluz.financask.delegate

import com.example.nelsonluz.financask.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}