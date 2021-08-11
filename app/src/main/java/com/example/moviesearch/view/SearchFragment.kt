package com.example.moviesearch.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.*
import com.example.moviesearch.data.CustomSearch
import com.example.moviesearch.data.DataList
import com.example.moviesearch.network.CustomResponse
import com.example.moviesearch.recycler.CustomRecyclerAdapter_search

//Создаю вложенный интерфейс

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val customHandler = CustomHandler()


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
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        val imageButtonSearch: ImageButton? = view.findViewById(R.id.imageButtonFSSearch)
        val imageButtonClearn: ImageButton? = view.findViewById(R.id.imageButtonFSDelite)
        val editTextSearch: EditText? = view.findViewById(R.id.editTextFragmentSearchTextSearch)

        val listMovie: MutableList<CustomSearch>


        val customResponse = CustomResponse()

        customHandler.initHandler()

        listMovie = BoottomNavigationVA2ViewModels.customMutListData
        //рекуклер
        val recyclerView: RecyclerView? = view.findViewById(R.id.RecyclerviewFSList)
        recyclerView?.layoutManager = LinearLayoutManager(view.context)
        recyclerView?.adapter = CustomRecyclerAdapter_search(
            requireActivity(),
            BoottomNavigationVA2ViewModels.textSearch,
            listMovie
        )
        // recyclerView.adapter = CustomRecyclerAdapter_search(listMovie)


        Log.d("fun", "fun  onCreateView")
        imageButtonSearch?.setOnClickListener {
            //  imageButtonSearch.isEnabled = false
            // тут выполняем запрос
            customHandler.handler.postDelayed({
                val listMovieHandler: List<DataList> =
                    customResponse.responseMovie(editTextSearch?.text.toString(), "1")
                BoottomNavigationVA2ViewModels.newResponsData(listMovieHandler)

                customHandler.mainHandler.post {
                    if (activity != null) {
                      //  (activity as BottomNavigationViewActivity2).customViewModel.checkedButtonMenu
                        Log.d(
                            "fun",
                            "fun setOnClickListener ${BoottomNavigationVA2ViewModels.customMutListData}"
                        )
                        recyclerView?.adapter =
                            CustomRecyclerAdapter_search(
                                requireActivity(),
                                editTextSearch?.text.toString(),
                                BoottomNavigationVA2ViewModels.customMutListData
                            )
                        imageButtonSearch.isEnabled = true
                        BoottomNavigationVA2ViewModels.textSearch = editTextSearch?.text.toString()
                    }
                }
            }, 500)
        }
        imageButtonClearn?.setOnClickListener {
            Log.d("fun", "fun  imageButtonClearn?.setOnClickListener")
            editTextSearch?.setText("")
        }


        // Inflate the layout for this fragment
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroyView() {
        customHandler.quit(true)
        super.onDestroyView()
    }

    override fun onDetach() {

        super.onDetach()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}