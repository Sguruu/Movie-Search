package com.example.moviesearch

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.moviesearch.data.CustomListSearch
import com.example.moviesearch.data.CustomSearch
import com.example.moviesearch.view.ProfilFragment
import com.example.moviesearch.view.SavesFragment
import com.example.moviesearch.view.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class BottomNavigationViewActivity2 : AppCompatActivity() {

    //для сохранения данных
    lateinit var prefs: SharedPreferences
    private val APP_PREFERENCES = "ActivitiMovie"
    private val APP_PREFERENCES_LISTSAVE = "Datalist"
    private val APP_PREFERENCES_VALUEMENU = "checkedButtonMenu"

    //
    val customHandler = CustomHandler()

    //для запоминания выбранного пользователем раздела 1 - 3, другие значения не допускаются
    private var checkedButtonMenu = 3
    private var historyCheckedButtonMenu = checkedButtonMenu
    private lateinit var textViewTextToolBar: TextView
    //
    val customViewModel: BoottomNavigationVA2ViewModels by viewModels()
    val ld = LD()

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation_view2)

        val imageButton = findViewById<ImageButton>(R.id.imageButtonABNV2Back)

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewABNV2)
        textViewTextToolBar = findViewById(R.id.textViewABNV2Text)

        val fragmentSearch = SearchFragment()
        val fragmentSaves = SavesFragment()
        val fragmentProfil = ProfilFragment()

        Log.d("fun", "BottomNavigationViewActivity2 fun  onCreateView customViewModel.saveListMovie ${customViewModel.saveListMovie}")

        //***Загрузка данных Начало
        prefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        //проверка на сущетвование контейнеров, только если они есть мы запускаем выгрузку
        if (prefs.contains(APP_PREFERENCES_VALUEMENU) && prefs.contains(APP_PREFERENCES_LISTSAVE)) {

            val gson = Gson()
            val fromJsonBufListSave: CustomListSearch = gson.fromJson(
                prefs.getString(APP_PREFERENCES_LISTSAVE, ""),
                CustomListSearch::class.java
            )
            val bufValueMenu = prefs.getInt(APP_PREFERENCES_VALUEMENU, 0)

            val bufListSave: MutableList<CustomSearch> = mutableListOf()
            bufListSave.addAll(fromJsonBufListSave.Search)

            //выгрузка в нашь объект
            customViewModel.update(bufValueMenu, bufListSave)
        }
        //***Загрузка данных Конец

        //LiveData Подписки
        var testLiveData = ""
        customViewModel.text().observe(this) {
            testLiveData = it
            Log.d("LiveData", " запуск подписчика it = $it")
        }

        customViewModel.checkedButtonMenu().observe(this){
            checkedButtonMenu = it
        }
        //LiveData end

        customHandler.initHandler()

        startFragment(
            checkedButtonMenu,
            fragmentSearch,
            fragmentSaves,
            fragmentProfil,
            bottomNavigationView
        )

        imageButton.setOnClickListener {
            //
            testLiveData += "1"
            customViewModel.addText(testLiveData)
            //
            if (checkedButtonMenu == 4) {
                bottomNavigationView.isVisible = true
            }
            startFragment(
                historyCheckedButtonMenu,
                fragmentSearch,
                fragmentSaves,
                fragmentProfil,
                bottomNavigationView
            )
            updCheckedButtonMenu(historyCheckedButtonMenu)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_navigation_search -> {
                    if (checkedButtonMenu != 1) {
                        textViewTextToolBar.setText(R.string.search_film)
                        loadFragment(fragmentSearch)
                        updCheckedButtonMenu(1)
                    }
                }
                R.id.bottom_navigation_bookmark -> {
                    if (checkedButtonMenu != 2) {
                        textViewTextToolBar.setText(R.string.saves)
                        loadFragment(fragmentSaves)
                        updCheckedButtonMenu(2)
                    }
                }
                R.id.bottom_navigation_profil -> {
                    if (checkedButtonMenu != 3) {
                        textViewTextToolBar.setText(R.string.profil)
                        loadFragment(fragmentProfil)
                        updCheckedButtonMenu(3)
                    }

                }
            }
            return@OnNavigationItemSelectedListener true
        })

    }


    // функция для добавления фрагмента
    fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        //  этот код замещает фраменты уничтожая их, важно учитывать при передачи данных
        fragmentTransaction.replace(R.id.fragmentABNV2, fragment)
        fragmentTransaction.commit()
    }

    //Функция запуска фрагмента с сохранением ползователськой истории выбора
    private fun startFragment(
        checkedButtonMenu: Int,
        fragmentSearch: SearchFragment,
        fragmentSaves: SavesFragment,
        fragmentProfil: ProfilFragment,
        bottomNavigationView: BottomNavigationView
    ) {
        when (checkedButtonMenu) {
            1 -> {
                textViewTextToolBar.setText(R.string.search_film)
                bottomNavigationView.menu.findItem(R.id.bottom_navigation_search).isChecked =
                    true
                loadFragment(fragmentSearch)
            }
            2 -> {
                textViewTextToolBar.setText(R.string.saves)
                bottomNavigationView.menu.findItem(R.id.bottom_navigation_bookmark).isChecked =
                    true
                loadFragment(fragmentSaves)
            }
            3 -> {
                textViewTextToolBar.setText(R.string.profil)
                bottomNavigationView.menu.findItem(R.id.bottom_navigation_profil).isChecked =
                    true
                loadFragment(fragmentProfil)
            }
            4 -> {
                textViewTextToolBar.setText(R.string.film)
                //   bottomNavigationView.menu.findItem(R.id.bottom_navigation_profil).isVisible = false
                //  updCheckedButtonMenu(4)
                loadFragment(fragmentProfil)
            }
        }
    }

    //для наружного применения
    fun openFragmentProfil(fragment: Fragment) {
        textViewTextToolBar.setText(R.string.film)
        bottomNavigationView.isVisible = false
        updCheckedButtonMenu(4)
        loadFragment(fragment)
    }

    // функция изменения состояния checkedButtonMenu
    fun updCheckedButtonMenu(newCheckedButtonMenu: Int) {
        historyCheckedButtonMenu = checkedButtonMenu
        customViewModel.addCheckedButtonMenu(newCheckedButtonMenu)
        Log.d(
            "fun",
            "fun updCheckedButtonMenu : historyCheckedButtonMenu ${historyCheckedButtonMenu}  newCheckedButtonMenu ${checkedButtonMenu}\n"
        )
    }

    override fun onPause() {
        super.onPause()
        //сохранения данных
        val bufListSave = CustomListSearch(customViewModel.saveListMovie)
        val bufValueMenu = checkedButtonMenu

        //конвертирование
        val gson = Gson()
        val jsonBufListSave = gson.toJson(bufListSave)

        val editor = prefs.edit()
        editor.putString(APP_PREFERENCES_LISTSAVE, jsonBufListSave)
        editor.putInt(APP_PREFERENCES_VALUEMENU, bufValueMenu)
        Log.d("Server", "toJson $jsonBufListSave")
        editor.commit()
        //
    }

    override fun onBackPressed() {
    }

    override fun onDestroy() {
        super.onDestroy()
        customHandler.quit(false)
    }
}
