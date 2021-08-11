package com.example.moviesearch


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.security.auth.callback.Callback


class LD {
    //LiveData эксперемент
    private val textTest = MutableLiveData<String>()

    //функция для подписки
    fun text(): LiveData<String> = textTest

    //добавление
    fun addText(newText:String){
        textTest.postValue(newText)
    }
}