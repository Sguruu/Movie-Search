package com.example.moviesearch

import android.R.attr.data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.data.CustomSearch
import com.example.moviesearch.data.DataList


class BoottomNavigationVA2ViewModels : ViewModel() {
    private val repository = BoottomNavigationVA2Repositories()

    //черновые данные с запроса, сохранять не нужно
    var listData: List<DataList> = listOf()
        private set

    //обработанные данные
    var customMutListData: MutableList<CustomSearch> = mutableListOf()
        private set

    //сохраненный список которые нужно сохранять
    var saveListMovie: MutableList<CustomSearch> = mutableListOf()
        private set

    //для запоминания выбранного пользователем раздела 1 - 3, другие значения не допускаются
    // его нужно запонмить
    private val checkedButtonMenu = MutableLiveData<Int>(3)
    fun checkedButtonMenu(): LiveData<Int> = checkedButtonMenu
    fun addCheckedButtonMenu(newCheckedButtonMenu:Int){
        checkedButtonMenu.postValue(newCheckedButtonMenu)
    }


    //выбор текущего номера страницы запроса
    var page = 1
        private set

    // передача данных при нажатии, сохранять не нужно
    var viewElementMov = CustomSearch("", "", "", "", "", false)

    // текст поиска
    var textSearch = ""

    var test = 1

    //LiveData эксперемент
    private val textTest = MutableLiveData<String>()

    //функция для подписки
    fun text(): LiveData<String> = textTest

    //добавление
    fun addText(newText:String){
        textTest.postValue(newText)
    }

    //*************************************
    //Получение всех данных
    fun update(checkedButtonMenu: Int, saveListMovie: MutableList<CustomSearch>) {
        this.checkedButtonMenu.value = checkedButtonMenu
        this.saveListMovie = saveListMovie
    }

    // создание новых данных с флагом
    private fun addFlags(newListData: List<DataList>) {
        customMutListData = (repository.addFlags(newListData))
    }

    //обновление данных запроса плагинации, обновляет данные возвращает только добавленные данные
    fun addDataList(newDataList: List<DataList>): MutableList<CustomSearch> {
        listData = repository.addListData(newDataList, listData)
        addFlags(listData)
        return repository.addFlags(newDataList)
    }

    //обновление запроса
    fun newResponsData(list: List<DataList>) {
        listData = list
        addFlags(listData)
        page = 1
    }

    //добавление в сохраненый список
    fun addListSaveData(SaveData: CustomSearch) {
        saveListMovie.add(SaveData)
    }

    //удаление из сохраненного списа
    fun delitListSaveData(saveData: CustomSearch) {
        // не корректно работает с новым запросом, для корректной работы необходимо
        // 1. Вычислить похожий массив и его индекс
        // 2. Сохранить массив и его индекс
        // 3. Удалить элемент по индексу по массиву
        val element = saveListMovie.find { it.imdbID == saveData.imdbID }
        saveListMovie.removeAt(saveListMovie.indexOf(element))
    }

    //изменение значения flag похожего элемента на аналогичный, взвращает значение флага true/false
    fun editSimilarity(elementList: CustomSearch): Boolean {
        return repository.editSimilarity(elementList, saveListMovie)
    }

    //увеличение страницы на +1
    fun addPage() {
        if (page != 100) {
            page += 1
        }
    }

}