package com.project.coronainfowithmvvm.ui.CoronaGlobal.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.project.coronainfowithmvvm.R
import com.project.coronainfowithmvvm.ui.CoronaGlobal.data.Attributes
import com.project.coronainfowithmvvm.ui.CoronaGlobal.data.WorldData
import kotlinx.android.synthetic.main.item_list_world.view.*
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class WordlAdapter(private val data : List<WorldData>?) :
    RecyclerView.Adapter<WordlAdapter.MyHolder>(),
    Filterable {

    var dataFileter : List<WorldData>?

    init {
      dataFileter = data
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(attributes : Attributes?) {
            val dec = DecimalFormat("#,###.##")
            itemView.negara.text = attributes?.Country_Region
            itemView.active.text = dec.format(attributes?.Active)
            itemView.Confirmed.text = dec.format(attributes?.Confirmed)
            itemView.Deaths.text = dec.format(attributes?.Deaths)
            itemView.Recovered.text = dec.format(attributes?.Recovered)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int)
            = MyHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list_world,viewGroup,false))


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
                    val filteredList: ArrayList<WorldData> = ArrayList()
                    if (data != null) {
                        for (row in data) {
                            if (row.getAttributes()?.Country_Region?.toLowerCase()?.contains(charString.toLowerCase())!!) {
                                //Log.e("row", row.getAttributes()!!.Country_Region.toString());
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
                    filterResults.values as List<WorldData>?
                notifyDataSetChanged()
            }
        }
    }


}
