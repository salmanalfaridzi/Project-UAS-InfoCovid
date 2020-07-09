package com.project.coronainfowithmvvm.ui.CoronaGlobal

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.project.coronainfowithmvvm.R
import com.project.coronainfowithmvvm.ui.CoronaGlobal.adapter.WordlAdapter
import com.project.coronainfowithmvvm.ui.CoronaGlobal.data.WorldData
import kotlinx.android.synthetic.main.corona_global_fragment.*

class CoronaGlobal : Fragment() {

    companion object {
        fun newInstance() = CoronaGlobal()
    }

    private lateinit var viewModel: CoronaGlobalViewModel
    private lateinit var list : RecyclerView
    private var wordlAdapter: WordlAdapter? = null
    private var worldDatalist : ArrayList<WorldData>? = null
    lateinit var cari : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val root = inflater.inflate(R.layout.corona_global_fragment, container, false)
             (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.menu_dataglobal)
        cari = root.findViewById(R.id.carinegara)
        list = root.findViewById(R.id.listGlobalworld)
        val layoutManager = LinearLayoutManager(requireContext())
        list.setLayoutManager(layoutManager)
        worldDatalist = ArrayList()
        wordlAdapter = WordlAdapter(worldDatalist)
        list.adapter = wordlAdapter

         return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CoronaGlobalViewModel::class.java)

        cari.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                wordlAdapter!!.filter.filter(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        viewModel.getStatus().observe(viewLifecycleOwner, Observer {
            if (it == true ) {
                progressBar.visibility = View.VISIBLE
            }else {
                progressBar.visibility = View.GONE
            }
        })

        viewModel.getData().observe(viewLifecycleOwner, Observer {
            showDataGlobal(it)
        })
    }

    private fun showDataGlobal(data: List<WorldData>) {
        worldDatalist?.clear()
        worldDatalist?.addAll(data)
        wordlAdapter?.notifyDataSetChanged()
    }


}
