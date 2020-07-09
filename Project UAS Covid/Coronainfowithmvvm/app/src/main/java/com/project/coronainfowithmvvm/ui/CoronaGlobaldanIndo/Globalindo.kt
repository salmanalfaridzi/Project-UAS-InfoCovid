package com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.project.coronainfowithmvvm.R
import com.project.coronainfowithmvvm.ui.CoronaGlobal.CoronaGlobalViewModel
import kotlinx.android.synthetic.main.corona_global_fragment.*
import kotlinx.android.synthetic.main.globalindo_fragment.*
import kotlinx.android.synthetic.main.globalindo_fragment.progressBar

class Globalindo : Fragment() {

    companion object {
        fun newInstance() = Globalindo()
    }

    private lateinit var viewModel: GlobalindoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.globalindo_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.menu_dataindonesia)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GlobalindoViewModel::class.java)

        //item = 3
        viewModel.getData().observe(viewLifecycleOwner, Observer {
            meninggal.text = it[0].value
            sembuh.text = it[1].value
            positif.text = it[2].value
        })

        viewModel.getDataIndo().observe(viewLifecycleOwner, Observer {
            indomeninggal.text = it[0].meninggal
            indopos.text = it[0].positif
            indosembu.text = it[0].sembuh
        })
        
        viewModel.getStatus().observe(viewLifecycleOwner, Observer {
            if (it == true ) {
                progressBar.visibility = View.VISIBLE
            }else {
                progressBar.visibility = View.GONE
            }
        })
    }

}
