package com.mann.ouiparisproject.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.LocationManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.mann.ouiparisproject.R
import com.mann.ouiparisproject.data.model.ResultModel
import com.mann.ouiparisproject.utils.MaskedCardView
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import java.util.*

class ActivitiesAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    private var isLoadingAdded = false

    private lateinit var list : MutableList<ResultModel>
    private lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
        this.list = LinkedList<ResultModel>()
    }

   /* constructor(list: ArrayList<ResultModel>, context: Context) : this() {
        this.list = list
        this.context = context
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var myViewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> {
                val viewItem: View = inflater.inflate(R.layout.details_layout, parent, false)
                myViewHolder = MyViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View =
                    inflater.inflate(R.layout.progress_bar_layout, parent, false)
                myViewHolder = LoadingViewHolder(viewLoading)
            }
        }

        /* View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_layout, parent, false);
        myViewHolder = new ActivityDetailsAdapter.MyViewHolder(view);*/

        return myViewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: ResultModel = list[position]
        when (getItemViewType(position)) {
            ITEM -> {
                val itemHolder = holder as MyViewHolder?
                itemHolder!!.title.text = "" + item.title
                itemHolder.subTitle.text = "" + item.name
                if (isLocationEnabled(context)) {
                    itemHolder.distance.text =
                        "" + java.lang.String.format("%.1f", item.distance) + " km"
                } else {
                    itemHolder.distance.visibility = View.GONE
                    itemHolder.distanceIcon.visibility = View.GONE
                }
                Glide.with(context).load(item.activePin).into(itemHolder.displayCatSubCatIcon)
                val openCloseCheck: String = item.hour.toString()
                if (openCloseCheck == "open") {
                    itemHolder.hour.setTextColor(Color.GREEN)
                } else if (openCloseCheck == "close") {
                    itemHolder.hour.setTextColor(Color.RED)
                }
                itemHolder.hour.text = "" + item.hour

                if(item.imgsList!=null){
                    itemHolder.imageViewPager.adapter = SlidingImage_Adapter(context, item.imgsList!!, "", item.id!!)
                    itemHolder.indicator.setViewPager(itemHolder.imageViewPager)
                }

                itemHolder.layout.setOnClickListener {
                   /* val activitiesDetailsIntent = Intent(
                        context,
                        ActivitiesDetailsActivity::class.java
                    )
                    activitiesDetailsIntent.putExtra("id", item.getId())
                    context.startActivity(activitiesDetailsIntent)*/
                }
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder?
                loadingViewHolder!!.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(ResultModel())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = list.size - 1
        val result: ResultModel = getItem(position)
        if (result != null) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun add(activitiesPojo: ResultModel) {
        list.add(activitiesPojo)
        notifyItemInserted(list.size - 1)
    }

    fun addAll(moveResults: List<ResultModel>) {
        for (result in moveResults) {
            add(result)
        }
    }

    fun getItem(position: Int): ResultModel {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: RelativeLayout = itemView.findViewById(R.id.layout)
        var title: TextView = itemView.findViewById(R.id.title)
        var subTitle: TextView = itemView.findViewById(R.id.subTitle)
        var distance: TextView = itemView.findViewById(R.id.distance)
        var hour: TextView = itemView.findViewById(R.id.workingHour)
        var imageViewPager: ViewPager = itemView.findViewById(R.id.pager)
        var indicator: WormDotsIndicator = itemView.findViewById(R.id.worm_dots_indicator)
        var maskedCardView: MaskedCardView = itemView.findViewById(R.id.rLayout)
        var displayCatSubCatIcon: ImageView = itemView.findViewById(R.id.displayCatSubCatIcon)
        var distanceIcon: ImageView = itemView.findViewById(R.id.distanceIcon)

    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: RelativeLayout

        init {
            progressBar = itemView.findViewById(R.id.progress)
        }
    }

    private fun isLocationEnabled(mContext: Context): Boolean {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }


}