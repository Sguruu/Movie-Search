package com.example.moviesearch.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.BoottomNavigationVA2ViewModels
import com.example.moviesearch.BottomNavigationViewActivity2
import com.example.moviesearch.R
import com.example.moviesearch.data.CustomSearch
import com.example.moviesearch.view.FilmsFragment
import com.example.moviesearch.view.ProfilFragment
import com.squareup.picasso.Picasso

class CustomRecyclerAdapter_save(
    // для открытия элемента
    private val context: Context,
    private val listSaveMovie: MutableList<CustomSearch>
) : RecyclerView.Adapter<CustomRecyclerAdapter_save.MyViewHolder>() {
    private val fragment = FilmsFragment()
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageViewPoster: ImageView? = null
        var imageViewSave: ImageView? = null
        var textViewTitle: TextView? = null
        var imageViewBack: ImageView? = null

        init {
            imageViewPoster = itemView.findViewById(R.id.imageViewRecylerSavePoster)
            imageViewSave = itemView.findViewById(R.id.imageViewRecylerSaveSave)
            textViewTitle = itemView.findViewById(R.id.editTextRecyclerSaveTitle)
            imageViewBack = itemView.findViewById(R.id.imageView2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerveiw_saves, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(listSaveMovie[position].Poster).into(holder.imageViewPoster)
        holder.textViewTitle?.text = listSaveMovie[position].Title

        //рисую флажок
        if (listSaveMovie[position].flag) {
            holder.imageViewSave?.setImageResource(R.drawable.ic_bookmark_true)
        } else {
            holder.imageViewSave?.setImageResource(R.drawable.ic_bi_bookmark_false)
        }

        holder.imageViewBack?.setOnClickListener {
            BoottomNavigationVA2ViewModels.viewElementMov = listSaveMovie[position]
            loadFragment()
        }

        holder.imageViewSave?.setOnClickListener {
            if (listSaveMovie[position].flag) {
                deleteItem(position, holder)
            } else {
                holder.imageViewSave?.setImageResource(R.drawable.ic_bookmark_true)
                listSaveMovie[position].flag = true
            }
        }
    }

    override fun getItemCount(): Int {
        return listSaveMovie.size
    }

    private fun deleteItem(position: Int, holder: MyViewHolder) {
        // Costul.updatelistRep(listRepositories[position])
        listSaveMovie.removeAt(position)
        // notifyDataSetChanged()
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, 1)
        holder.itemView.visibility = View.GONE

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