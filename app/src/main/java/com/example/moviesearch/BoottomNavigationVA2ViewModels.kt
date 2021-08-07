package com.example.moviesearch

import android.icu.text.CaseMap
import androidx.lifecycle.ViewModel
import com.example.moviesearch.data.CustomSearch
import com.example.moviesearch.data.DataList

object BoottomNavigationVA2ViewModels : ViewModel() {
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
    var checkedButtonMenu = 1
     //   private set

    //выбор текущего номера страницы запроса
    var page = 1
        private set

    // передача данных при нажатии, сохранять не нужно
    var viewElementMov = CustomSearch ("","","","","",false)

    //*************************************
    //Получение всех жанных
    fun update(checkedButtonMenu: Int, saveListMovie: MutableList<CustomSearch>) {
        this.checkedButtonMenu = checkedButtonMenu
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
    fun delitListSaveData(SaveData: CustomSearch) {
        saveListMovie.remove(SaveData)
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