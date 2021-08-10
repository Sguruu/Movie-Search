package com.example.moviesearch.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.BoottomNavigationVA2ViewModels
import com.example.moviesearch.BottomNavigationViewActivity2
import com.example.moviesearch.CustomHandler
import com.example.moviesearch.R
import com.example.moviesearch.data.CustomSearch
import com.example.moviesearch.network.CustomResponse
import com.example.moviesearch.view.FilmsFragment
import com.squareup.picasso.Picasso

class CustomRecyclerAdapter_search(

    // для открытия элемента
    private val context: Context,
    //используется для запроса
    private val textRespons: String,
    private val listMovie: MutableList<CustomSearch>

) : RecyclerView.Adapter<CustomRecyclerAdapter_search.MyViewHolder>() {

    private var page = 1

    private val customHandler = CustomHandler()
    private val customResponse = CustomResponse()

    private val fragment = FilmsFragment()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageViewPoster: ImageView? = null
        var imageViewSave: ImageView? = null
        var textViewTitle: TextView? = null
        var imageViewBackgraund: ImageView? = null

        init {
            imageViewPoster = itemView.findViewById(R.id.imageViewRecylerPoster)
            imageViewSave = itemView.findViewById(R.id.imageViewRecylerSave)
            textViewTitle = itemView.findViewById(R.id.editTextRecyclerTitle)
            imageViewBackgraund = itemView.findViewById(R.id.imageViewRecylerSaveB)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerveiw, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(listMovie[position].Poster).into(holder.imageViewPoster)
        holder.textViewTitle?.text = listMovie[position].Title

        //Проверяю на схожеть и устанавливаю значение флага
        listMovie[position].flag =
            BoottomNavigationVA2ViewModels.editSimilarity(listMovie[position])

        //рисую флажок  в зависимости от значения в массиве
        if (listMovie[position].flag) {
            holder.imageViewSave?.setImageResource(R.drawable.ic_bookmark_true)
        } else {
            holder.imageViewSave?.setImageResource(R.drawable.ic_bi_bookmark_false)
        }

        //проверяю последний ли это элемент, для совершеня плагинации
        if (position == listMovie.size - 1) {
            BoottomNavigationVA2ViewModels.addPage()
            page = BoottomNavigationVA2ViewModels.page
            customHandler.initHandler()
            customHandler.handler.post {
                val response = customResponse.responseMovie(textRespons, page.toString())
                customHandler.mainHandler.post {
                    addItem(
                        position,
                        holder = holder,
                        BoottomNavigationVA2ViewModels.addDataList(response)
                    )
                }
            }

            customHandler.quit(true)
        }

        // обработка нажатия на эдемент
        holder.imageViewBackgraund?.setOnClickListener {
            BoottomNavigationVA2ViewModels.viewElementMov = listMovie[position]
            loadFragment()
        }

        // обработка нажатия на картинку сохранить (добавить или удалить из заметок)
        holder.imageViewSave?.setOnClickListener {
            if (listMovie[position].flag) {
                listMovie[position].flag = false
                BoottomNavigationVA2ViewModels.delitListSaveData(listMovie[position])
                holder.imageViewSave?.setImageResource(R.drawable.ic_bi_bookmark_false)
            } else {
                holder.imageViewSave?.setImageResource(R.drawable.ic_bookmark_true)
                listMovie[position].flag = true
                BoottomNavigationVA2ViewModels.addListSaveData(listMovie[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    private fun addItem(
        position: Int,
        holder: MyViewHolder,
        listAdd: MutableList<CustomSearch>
    ) {
        // Costul.updatelistRep(listRepositories[position])
        listMovie.addAll(position + 1, listAdd)
        notifyItemRangeInserted(position + 1, listAdd.size)
        //  holder.itemView.visibility = View.GONE

    }

    // получение позиции нажатия
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    // функция для добавления фрагмента
    private fun loadFragment() {
        (context as BottomNavigationViewActivity2).openFragmentProfil(fragment)
    }

}