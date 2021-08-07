package com.example.moviesearch

import com.example.moviesearch.data.CustomSearch
import com.example.moviesearch.data.DataList

class BoottomNavigationVA2Repositories {
    // создание новых данных с флагом
    fun addFlags(listData: List<DataList>): MutableList<CustomSearch> {
        val dataMutList: MutableList<CustomSearch> = mutableListOf()
        val size = listData.size
        for (i in 0 until size) {
            val customSearch = CustomSearch(
                listData[i].Title,
                listData[i].Year,
                imdbID = listData[i].imdbID,
                Type = listData[i].Type,
                Poster = listData[i].Poster,
                false
            )
            dataMutList.add(i, customSearch)
        }
        return dataMutList
    }

    // получение нового запроса по страницам
    fun addListData(newListData: List<DataList>, listData: List<DataList>): List<DataList> {
        return listData + newListData
    }


    //проверка на схожеть, перед добавлением
    //true - похожи, false нет похожих
   private fun similarity(elementList: CustomSearch, listSave: MutableList<CustomSearch>):  CustomSearch? {
        if (listSave.size != 0) {
            // возвращает индекс похожего элемента, если его нету возвращает null
            val similarity = listSave.find { it.imdbID == elementList.imdbID }
            if (similarity != null) {
                return similarity
            }
            return null
        }
        return null
    }

    //изменение значения flag похожего элемента на аналогичный, взвращает значение флага
    fun editSimilarity(elementList: CustomSearch, listSave: MutableList<CustomSearch>):Boolean{
       val result = similarity(elementList,listSave)
        if (result!=null){
            elementList.flag = true
            return true
        }
        elementList.flag = false
        return false
    }



}