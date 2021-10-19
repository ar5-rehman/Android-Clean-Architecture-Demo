package com.mann.ouiparisproject.ui.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.mann.ouiparisproject.R

class SlidingImage_Adapter(
    private val context: Context,
    private val IMAGES: List<String>,
    private val str: String,
    private val activityID: Int
) :
    PagerAdapter() {


    private val inflater: LayoutInflater? = LayoutInflater.from(context)
    private var optionLayout: RelativeLayout? = null
    private  var layout:RelativeLayout? = null

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return IMAGES.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout: View = inflater!!.inflate(R.layout.slidingimages_layout, view, false)!!
        val imageView = imageLayout.findViewById<View>(R.id.image) as ImageView
        optionLayout = imageLayout.findViewById(R.id.optionLayout)
        optionLayout!!.setOnClickListener(View.OnClickListener {
            /*if (str == "activityDetails") {
                val manager: FragmentManager = activity.getSupportFragmentManager()
                val mydialog = AddPlacesFragment(activityID)
                mydialog.show(manager, "addPlace")
            } else {
                val manager: FragmentManager = mainActivity.getSupportFragmentManager()
                val mydialog = AddPlacesFragment(activityID)
                mydialog.show(manager, "addPlace")
            }*/
        })
        Glide.with(context).load(IMAGES[position]).centerCrop().into(imageView)
        view.addView(imageLayout, 0)
        layout = imageLayout.findViewById(R.id.layout)
        layout!!.setOnClickListener(View.OnClickListener {
            /*if (str == "activityDetails") {
                *//* Intent activitiesDetailsIntent = new Intent(context, ActivitiesDetailsActivity.class);
                        activitiesDetailsIntent.putExtra("id", activityID);
                        activity.startActivity(activitiesDetailsIntent);*//*
            } else {
                val activitiesDetailsIntent = Intent(
                    context,
                    ActivitiesDetailsActivity::class.java
                )
                activitiesDetailsIntent.putExtra("id", activityID)
                context.startActivity(activitiesDetailsIntent)
            }*/
        })
        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}
    override fun saveState(): Parcelable? {
        return null
    }
}
