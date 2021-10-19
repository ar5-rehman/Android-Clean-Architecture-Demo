package com.mann.ouiparisproject.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Activity {
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
    var categories: List<Categoryy>? = null

    @SerializedName("location_name")
    @Expose
    var locationName: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("pictures")
    @Expose
    var pictures: List<String>? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("icon_active")
    @Expose
    var iconActive: Any? = null

    @SerializedName("pin")
    @Expose
    var pin: String? = null

    @SerializedName("pin_active")
    @Expose
    var pinActive: String? = null

    @SerializedName("working_hours")
    @Expose
    var workingHours: List<WorkingHour>? = null

    @SerializedName("display_cat_sub_cat")
    @Expose
    var displayCatSubCat: DisplayCatSubCat? = null

    @SerializedName("date_text")
    @Expose
    var dateText: Any? = null

    @SerializedName("hours_text")
    @Expose
    var hoursText: Any? = null
}

class Activity__1 {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lng")
    @Expose
    var lng: Double? = null

    @SerializedName("location_name")
    @Expose
    var locationName: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("display_cat_sub_cat")
    @Expose
    var displayCatSubCat: DisplayCatSubCat__1? = null

    @SerializedName("date_text")
    @Expose
    var dateText: Any? = null

    @SerializedName("hours_text")
    @Expose
    var hoursText: Any? = null
}

class Categoryy {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}

class Category__1 {
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
}

class DisplayCatSubCat {
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
}

 class DisplayCatSubCat__1

 class Entity {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("start_time")
    @Expose
    var startTime: String? = null

    @SerializedName("end_time")
    @Expose
    var endTime: String? = null
}

 class Event {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("activity")
    @Expose
    var activity: Activity__1? = null

    @SerializedName("category")
    @Expose
    var category: Any? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("website")
    @Expose
    var website: Any? = null

    @SerializedName("entities")
    @Expose
    var entities: List<Entity>? = null

    @SerializedName("pictures")
    @Expose
    var pictures: List<Picture>? = null

    @SerializedName("display_cat_sub_cat")
    @Expose
    var displayCatSubCat: DisplayCatSubCat? = null

    @SerializedName("tour_guide")
    @Expose
    var tourGuide: List<Any>? = null

    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lng")
    @Expose
    var lng: Double? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("zip")
    @Expose
    var zip: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null
}

class Guide {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("subtitle")
    @Expose
    var subtitle: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("pictures")
    @Expose
    var pictures: List<String>? = null

    @SerializedName("picture")
    @Expose
    var picture: String? = null

    @SerializedName("duration")
    @Expose
    var duration: String? = null

    @SerializedName("activities_count")
    @Expose
    var activitiesCount: Int? = null

    @SerializedName("tag")
    @Expose
    var tag: Any? = null

    @SerializedName("in_bbox")
    @Expose
    var inBbox: String? = null

    @SerializedName("line")
    @Expose
    var line: List<Any>? = null

    @SerializedName("last_update")
    @Expose
    var lastUpdate: Any? = null

    @SerializedName("tour_guide")
    @Expose
    var tourGuide: List<Any>? = null

    @SerializedName("default_sort")
    @Expose
    var defaultSort: String? = null
}

class Picture {
    @SerializedName("picture")
    @Expose
    var picture: String? = null

    @SerializedName("is_featured")
    @Expose
    var isFeatured: Boolean? = null

    @SerializedName("caption")
    @Expose
    var caption: Any? = null

    @SerializedName("priority")
    @Expose
    var priority: Int? = null
}

class SearchPojo {
    @SerializedName("activities")
    @Expose
    var activities: List<Activity>? = null

    @SerializedName("events")
    @Expose
    var events: List<Event>? = null

    @SerializedName("guides")
    @Expose
    var guides: List<Guide>? = null

    @SerializedName("categories")
    @Expose
    var categories: List<Category__1>? = null

    @SerializedName("sub_categories")
    @Expose
    var subCategories: List<SubCategory>? = null
}

class SubCategory {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("category")
    @Expose
    var category: Int? = null

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
}

class WorkingHour {
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