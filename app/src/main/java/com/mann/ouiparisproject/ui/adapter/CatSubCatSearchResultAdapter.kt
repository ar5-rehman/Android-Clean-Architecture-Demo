package com.mann.ouiparisproject.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mann.ouiparisproject.R
import com.mann.ouiparisproject.data.model.ResultModel

class CatSubCatSearchResultAdapter(val checkCategoryOrSubCategory: Int, val list: List<ResultModel>, val context: Context?) :
    RecyclerView.Adapter<CatSubCatSearchResultAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_subcategory_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ResultModel = list[position]
        holder.categoryChip.setText(item.title)
        holder.categoryChip.setOnClickListener {
            /*val fragment =
                LocationFragment(checkCategoryOrSubCategory, context, activitiesPojo.getId())
            val transaction: FragmentTransaction =
                activity.getSupportFragmentManager().beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()*/
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryChip: Chip = itemView.findViewById(R.id.categoryChip)

    }
}

