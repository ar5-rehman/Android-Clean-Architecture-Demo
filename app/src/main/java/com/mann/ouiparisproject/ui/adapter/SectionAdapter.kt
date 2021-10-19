package com.mann.ouiparisproject.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mann.ouiparisproject.R
import com.mann.ouiparisproject.data.model.SectionModel

class SectionsAdapter(private val context: Context, private var list: List<SectionModel>) :
    RecyclerView.Adapter<SectionsAdapter.MyViewHolder>() {

    private var discoveryAdapter: RecyclerView.Adapter<*>? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.sections_layout, parent, false)
        val myViewHolder = MyViewHolder(view)
        myViewHolder.sectionRecycler.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager!!.orientation = RecyclerView.HORIZONTAL
        myViewHolder.sectionRecycler.layoutManager = linearLayoutManager
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: SectionModel = list[position]
        holder.sectionTitle.setText(item.title)
        discoveryAdapter = SectionsItemAdapter(item, item.list, context)
        holder.sectionRecycler.adapter = discoveryAdapter
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setSectionData(list: List<SectionModel>){
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sectionTitle: TextView = itemView.findViewById(R.id.sectionTitle)
        var sectionRecycler: RecyclerView = itemView.findViewById(R.id.sectionsRecycler)

    }
}