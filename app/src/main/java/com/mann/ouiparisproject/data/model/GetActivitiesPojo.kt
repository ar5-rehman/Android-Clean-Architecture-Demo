package com.mann.ouiparisproject.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ActivityCategory {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}

 class ActivityDisplayCatSubCat {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("icon_active")
    @Expose
    var iconActive: String? = null

    @SerializedName("pin")
    @Expose
    var pin: String? = null

    @SerializedName("pin_active")
    @Expose
    var pinActive: String? = null

    @SerializedName("category")
    @Expose
    var category: Int? = null
}

class GetActivitiesPojo {
    @SerializedName("next")
    @Expose
    var next: String? = null

    @SerializedName("previous")
    @Expose
    var previous: Any? = null

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
}

class Result {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lng")
    @Expose
    var lng: Double? = null

    @SerializedName("categories")
    @Expose
    var categories: List<ActivityCategory>? = null

    @SerializedName("location_name")
    @Expose
    var locationName: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("pictures")
    @Expose
    var pictures: List<String>? = null

    @SerializedName("working_hours")
    @Expose
    var workingHours: List<ActivityWorkingHour>? = null

    @SerializedName("display_cat_sub_cat")
    @Expose
    var displayCatSubCat: ActivityDisplayCatSubCat? = null

    @SerializedName("date_text")
    @Expose
    var dateText: Any? = null

    @SerializedName("hours_text")
    @Expose
    var hoursText: Any? = null
}

class ActivityWorkingHour {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("activity")
    @Expose
    var activity: Int? = null

    @SerializedName("day_of_week")
    @Expose
    var dayOfWeek: Int? = null

    @SerializedName("closed")
    @Expose
    var closed: Boolean? = null

    @SerializedName("open_time1")
    @Expose
    var openTime1: String? = null

    @SerializedName("close_time1")
    @Expose
    var closeTime1: String? = null

    @SerializedName("open_time2")
    @Expose
    var openTime2: Any? = null

    @SerializedName("close_time2")
    @Expose
    var closeTime2: Any? = null
}