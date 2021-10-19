package com.mann.ouiparisproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mann.ouiparisproject.R
import com.mann.ouiparisproject.data.model.WeatherPojo


class WeatherAdapter(list: List<WeatherPojo>) :
    RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {
    var list: List<WeatherPojo> = list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_hourly_details_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherPojo: WeatherPojo = list[position]
        holder.hour.text = "" + weatherPojo.hour
        holder.temp.text = "" + weatherPojo.temp.toString() + "°C"
        val icon: String = weatherPojo.icon
        if (icon == "02n") {
            holder.icon.setImageResource(R.drawable.ic_two)
        } else if (icon == "03n") {
            holder.icon.setImageResource(R.drawable.ic_eight)
        } else if (icon == "02n") {
            holder.icon.setImageResource(R.drawable.ic_eleven)
        } else if (icon == "01dn") {
            holder.icon.setImageResource(R.drawable.ic_five)
        } else if (icon == "02n") {
            holder.icon.setImageResource(R.drawable.ic_nine)
        } else if (icon == "02n") {
            holder.icon.setImageResource(R.drawable.ic_one)
        } else if (icon == "02d") {
            holder.icon.setImageResource(R.drawable.ic_six)
        } else if (icon == "02d") {
            holder.icon.setImageResource(R.drawable.ic_ten)
        } else if (icon == "01d") {
            holder.icon.setImageResource(R.drawable.ic_three)
        } else if (icon == "01d") {
            holder.icon.setImageResource(R.drawable.ic_four)
        } else if (icon == "01d") {
            holder.icon.setImageResource(R.drawable.ic_twelve)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon: ImageView
        var hour: TextView
        var temp: TextView

        init {
            icon = itemView.findViewById(R.id.icon)
            hour = itemView.findViewById(R.id.hour)
            temp = itemView.findViewById(R.id.temp)
        }
    }

}
