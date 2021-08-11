package com.example.moviesearch.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.moviesearch.BoottomNavigationVA2ViewModels
import com.example.moviesearch.R
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val customViewModel: BoottomNavigationVA2ViewModels by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_films, container, false)
        val imageViewPoster = view.findViewById<ImageView>(R.id.imageViewFragmentFilms)
        val textViewTitle = view.findViewById<TextView>(R.id.textViewFragmentFilmsTitle)
        val textViewYear = view.findViewById<TextView>(R.id.textViewFragmentFilmsYear)
        val textViewType = view.findViewById<TextView>(R.id.textViewFragmentFilmsType)
        val buttonSave = view.findViewById<Button>(R.id.bottomFragmentFilms)

        Picasso.get().load(customViewModel.viewElementMov.Poster)
            .into(imageViewPoster)
        textViewTitle.text = customViewModel.viewElementMov.Title
        textViewYear.text = customViewModel.viewElementMov.Year
        textViewType.text = customViewModel.viewElementMov.Type

        buttonSave.setOnClickListener {
            if (customViewModel.viewElementMov.flag) {
                Toast.makeText(requireActivity(), "Данный фильм уже сохранен", Toast.LENGTH_LONG)
                    .show()
            } else {
                customViewModel.viewElementMov.flag = true
                customViewModel.addListSaveData(customViewModel.viewElementMov)
                Toast.makeText(
                    requireActivity(),
                    "Фильм добавлен в закладки ${customViewModel.viewElementMov.Title}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FilmsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FilmsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}