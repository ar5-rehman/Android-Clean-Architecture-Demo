package com.mann.ouiparisproject.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Category{
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

class DiscoveryPojo {
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>? = null

    @SerializedName("sections")
    @Expose
    var sections: List<Section>? = null

    @SerializedName("categories")
    @Expose
    var categories: List<Category>? = null
}

class Section {
    @SerializedName("section_title")
    @Expose
    var sectionTitle: String? = null

    @SerializedName("items")
    @Expose
    var items: List<Item>? = null

    @SerializedName("view_all_request")
    @Expose
    var viewAllRequest: String? = null
}

class Weather {
    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("hour")
    @Expose
    var hour: String? = null

    @SerializedName("temperature")
    @Expose
    var temperature: Int? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null
}

class Item {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("picture")
    @Expose
    var picture: String? = null

    @SerializedName("get_request")
    @Expose
    var getRequest: String? = null
}
