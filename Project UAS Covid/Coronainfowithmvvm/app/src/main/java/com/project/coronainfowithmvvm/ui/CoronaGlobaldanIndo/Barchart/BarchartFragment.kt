package com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo.Barchart

import android.graphics.Color
import android.graphics.Color.green
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.project.coronainfowithmvvm.R
import com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo.GlobalindoViewModel
import com.project.coronainfowithmvvm.ui.CoronaGlobaldanIndo.data.Global
import kotlinx.android.synthetic.main.fragment_barchart.*
import kotlinx.android.synthetic.main.fragment_barchart.progressBar
import kotlinx.android.synthetic.main.globalindo_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class BarchartFragment : Fragment() {

    private var value_ : ArrayList<BarEntry>? = null
    private lateinit var viewModel: GlobalindoViewModel
    private var name_: ArrayList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_barchart, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.menu_dataglobal)

        name_ = ArrayList()
        value_ = ArrayList()

        return root

    }

    private fun build_label(label : ArrayList<Global>) {
        name_?.clear()
        for (i in label.indices) {
            name_?.add(label[i].name.toString())
        }
    }

    private fun build_value(value : ArrayList<Global>) {
        value_?.clear()
        for (i in value.indices) {
            value[i].value?.replace(",","")?.toFloat()?.let {
                BarEntry(i.toFloat(),
                    it
                )
            }?.let { value_?.add(it) }
        }
    }

    private fun build_chart() {

        val barWidth = 0.3f
        chart.getDescription().setEnabled(false)
        chart.setPinchZoom(false)
        chart.setDrawBarShadow(false)
        chart.setDrawGridBackground(false)
        val l: Legend = chart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(true)
        l.yOffset = 0f
        l.xOffset = 10f
        l.yEntrySpace = 0f
        l.textSize = 8f
        val xAxis: XAxis = chart.getXAxis()
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(false)
        val leftAxis: YAxis = chart.getAxisLeft()
        leftAxis.setDrawGridLines(true)
        leftAxis.isEnabled = false
        leftAxis.spaceTop = 35f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)

        chart.getAxisLeft().setEnabled(true)
        chart.getXAxis().setValueFormatter(IndexAxisValueFormatter(name_))
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM)

        chart.getXAxis().setLabelCount(3)
        val barSet_value: BarDataSet
        // Add bars to a bar set
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            barSet_value = chart.getData().getDataSetByIndex(0) as BarDataSet
            barSet_value.values = value_
            chart.getData().notifyDataChanged()
            chart.notifyDataSetChanged()

        } else {
            barSet_value = BarDataSet(value_, "Data Global")
            barSet_value.setColors(
                ContextCompat.getColor(chart.context, R.color.yellow),
                ContextCompat.getColor(chart.context, R.color.green),
                ContextCompat.getColor(chart.context, R.color.red)
            );
            // Create a BarData object and assign it to the chart
            val barData = BarData(barSet_value)
            // Display it as a percentage
            barData.setValueTextSize(10f)
            barData.setDrawValues(true)
            chart.getXAxis().setDrawGridLines(true)
            chart.getAxisRight().setGridLineWidth(1f)
            chart.getAxisRight().setTextSize(12f)
            chart.getAxisRight().setEnabled(false)
            chart.setData(barData)
        }
        chart.getBarData().setBarWidth(barWidth)
        chart.getData().setHighlightEnabled(false)
        chart.invalidate()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GlobalindoViewModel::class.java)

        viewModel.getData().observe(viewLifecycleOwner, Observer {
                build_label(it)
                build_value(it)
                build_chart()
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
