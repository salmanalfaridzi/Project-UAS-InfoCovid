package com.project.coronainfowithmvvm.ui.CoronaIndoprov.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.project.coronainfowithmvvm.R
import com.project.coronainfowithmvvm.ui.CoronaIndoprov.data.Attributes
import com.project.coronainfowithmvvm.ui.CoronaIndoprov.data.Provinsi
import kotlinx.android.synthetic.main.item_list_prov.view.*
import kotlinx.android.synthetic.main.item_list_world.view.Confirmed
import kotlinx.android.synthetic.main.item_list_world.view.Deaths
import kotlinx.android.synthetic.main.item_list_world.view.Recovered
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class ProvAdapter(private val data : List<Provinsi>?) :
    RecyclerView.Adapter<ProvAdapter.MyHolder>(),
    Filterable {

    var dataFileter : List<Provinsi>?

    init {
      dataFileter = data
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(attributes : Attributes?) {
            val dec = DecimalFormat("#,###.##")
            itemView.prov.text = attributes?.provinsi
            //itemView.active.text = dec.format(attributes?.kasusPosi)
            itemView.Confirmed.text = dec.format(attributes?.kasusPosi)
            itemView.Deaths.text = dec.format(attributes?.kasusMeni)
            itemView.Recovered.text = dec.format(attributes?.kasusSemb)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int)
            = MyHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list_prov,viewGroup,false))


    override fun getItemCount(): Int {
        return dataFileter?.size ?: 0
    }

    override fun onBindViewHolder(myHolder: MyHolder, i: Int) {
        myHolder.bindView(dataFileter?.get(i)?.getAttributes())
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                Log.e("Filter",charString);
                if (charString.isEmpty()) {
                    dataFileter = data
                } else {
                    Log.e("NotEmpty",charString);
                    val filteredList: ArrayList<Provinsi> = ArrayList()
                    if (data != null) {
                        for (row in data) {
                            if (row.getAttributes()?.provinsi?.toLowerCase()?.contains(charString.toLowerCase())!!) {
                                 filteredList.add(row)
                            }
                        }
                    }
                    dataFileter = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFileter
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                dataFileter =
                    filterResults.values as List<Provinsi>?
                notifyDataSetChanged()
            }
        }
    }


}
