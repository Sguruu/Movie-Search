package com.example.moviesearch.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.BoottomNavigationVA2ViewModels
import com.example.moviesearch.CustomHandler
import com.example.moviesearch.R
import com.example.moviesearch.data.CustomSearch
import com.example.moviesearch.data.DataList
import com.example.moviesearch.recycler.CustomRecyclerAdapter_save
import com.example.moviesearch.recycler.CustomRecyclerAdapter_search

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val customHandler = CustomHandler()
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
        val view: View = inflater.inflate(R.layout.fragment_saves, container, false)
// Inflate the layout for this fragment
        customHandler.initHandler()

        Log.d("test", "SavesFragment onCreateView test = ${customViewModel.test}")

        val recyclerView: RecyclerView? = view.findViewById(R.id.RecyclerViewSaves)
        recyclerView?.layoutManager = LinearLayoutManager(view.context)
        Log.d("fun", "SavesFragment fun  onCreateView customViewModel.saveListMovie ${customViewModel.saveListMovie}")
        recyclerView?.adapter=CustomRecyclerAdapter_save(requireActivity(),customViewModel.saveListMovie)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SavesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDetach() {
        super.onDetach()
        customHandler.quit(false)
    }
}