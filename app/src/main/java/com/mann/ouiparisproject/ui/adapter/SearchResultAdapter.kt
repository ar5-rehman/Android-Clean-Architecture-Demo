package com.mann.ouiparisproject.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.location.LocationManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mann.ouiparisproject.R
import com.mann.ouiparisproject.data.model.ResultModel
import com.mann.ouiparisproject.ui.view.SearchFragment

class SearchResultAdapter(private var list: List<ResultModel>, private val context: Context, private val which: String) :
    RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>() {

    private lateinit var json: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view: View

        if (which == "recent") {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recents_layout, parent, false)
        } else if (which == "nearBy") {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.nearby_layout, parent, false)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_results_layout, parent, false)
        }

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item: ResultModel = list[position]

        if (which == "activities") {
            Glide.with(context).load(item.imgSrc).into(holder.image)
            holder.title.text = "" + item.title
            if (isLocationEnabled(context)) {
                holder.distance.text =
                    "" + java.lang.String.format("%.1f", item.distance) + " km"
            } else {
                holder.distance.visibility = View.GONE
                holder.icon.visibility = View.GONE
            }
            val openCloseCheck: String = item.hour.toString()
            if (openCloseCheck == "open") {
                holder.hour.setTextColor(Color.GREEN)
            } else if (openCloseCheck == "close") {
                holder.hour.setTextColor(Color.RED)
            }
            holder.hour.text = "" + item.hour
            holder.name.text = "" + item.name
            SearchFragment.saveList.add(
                ResultModel(
                    null,
                    item.activePin,
                    item.id,
                    item.imgSrc,
                    item.title,
                    "",
                    item.hour,
                    item.distance,
                    item.name
                )
            )
        }
        if (which == "events") {
            Glide.with(context).load(item.imgSrc).into(holder.image)
            holder.title.text = "" + item.title
            if (isLocationEnabled(context)) {
                holder.distance.text =
                    "" + java.lang.String.format("%.1f", item.distance) + " km"
            } else {
                holder.distance.visibility = View.GONE
                holder.icon.visibility = View.GONE
            }
            val openCloseCheck: String = item.hour.toString()
            if (openCloseCheck == "open") {
                holder.hour.setTextColor(Color.GREEN)
            } else if (openCloseCheck == "close") {
                holder.hour.setTextColor(Color.RED)
            }
            holder.hour.text = "" + item.hour
            holder.name.text = "" + item.name
            SearchFragment.saveList.add(
                ResultModel(
                    null,
                    item.activePin,
                    item.id,
                    item.imgSrc,
                    item.title,
                    "",
                    item.hour,
                    item.distance,
                    item.name
                )
            )
        }
        if (which == "guides") {
            Glide.with(context).load(item.imgSrc).into(holder.image)
            holder.title.text = "" + item.title
            holder.distance.visibility = View.GONE
            holder.hour.visibility = View.GONE
            holder.name.visibility = View.GONE
            holder.icon.visibility = View.GONE

            SearchFragment.saveList.add(
                ResultModel(
                    null,
                    item.activePin,
                    item.id,
                    item.imgSrc,
                    item.title,
                    "",
                    item.hour,
                    item.distance,
                    item.name
                )
            )
        }
        if (which == "nearBy") {
           /* Glide.with(context).load(item.imgSrc).into(holder.image)
            Glide.with(context).load(item.activePin).into(holder.catSubCatIcon)
            holder.title.text = "" + item.title
            if (isLocationEnabled(context)) {
                holder.distance.text =
                    "" + java.lang.String.format("%.1f", item.distance) + " km"
            } else {
                holder.distance.visibility = View.GONE
                holder.icon.visibility = View.GONE
            }
            val openCloseCheck: String = item.hour
            if (openCloseCheck == "open") {
                holder.hour.setTextColor(Color.GREEN)
            } else if (openCloseCheck == "close") {
                holder.hour.setTextColor(Color.RED)
            }
            holder.hour.text = "" + item.hour
            holder.name.text = "" + item.name*/
        }
        if (which == "recent") {
            Glide.with(context).load(item.imgSrc).into(holder.image)
            holder.title.text = "" + item.title
            if (isLocationEnabled(context)) {
                holder.distance.text =
                    "" + java.lang.String.format("%.1f", item.distance) + " km"
            } else {
                holder.distance.visibility = View.GONE
                holder.icon.visibility = View.GONE
            }
            val openCloseCheck: String = item.hour.toString()
            if (openCloseCheck == "open") {
                holder.hour.setTextColor(Color.GREEN)
            } else if (openCloseCheck == "close") {
                holder.hour.setTextColor(Color.RED)
            }
            holder.hour.text = "" + item.hour
            holder.name.text = "" + item.name
        } else {
            if (SearchFragment.gson != null) {
                json = SearchFragment.gson.toJson(SearchFragment.saveList)
                SearchFragment.editor.putString("recentResults", json)
                SearchFragment.editor.commit()
            }
        }
        holder.parentLayout.setOnClickListener {
           /* val intent = Intent(context, ActivitiesDetailsActivity::class.java)
            intent.putExtra("id", item.id)
            context.startActivity(intent)*/
        }
    }

    override fun getItemCount(): Int {
        return if (which == "recent") {
            3
        } else {
            list.size
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parentLayout: RelativeLayout = itemView.findViewById(R.id.parentLayout)
        var image: ImageView = itemView.findViewById(R.id.imgSrc)
        var icon: ImageView = itemView.findViewById(R.id.icon)
   //     var catSubCatIcon: ImageView = itemView.findViewById(R.id.catSubCatIcon)
        var title: TextView = itemView.findViewById(R.id.title)
        var distance: TextView = itemView.findViewById(R.id.distance)
        var hour: TextView = itemView.findViewById(R.id.hour)
        var name: TextView = itemView.findViewById(R.id.name)

    }

    private fun isLocationEnabled(mContext: Context): Boolean {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}