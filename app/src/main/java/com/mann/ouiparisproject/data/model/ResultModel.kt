package com.mann.ouiparisproject.data.model

class ResultModel{

    var imgsList: List<String>? = null
    var activePin: String? = null
    var id: Int? = null
    var imgSrc: String? = null
    var title: String? = null
    var subTitle: String? = null
    var hour: String? = null
    var distance: Float? = null
    var name: String? = null

    constructor(imgsList: List<String>?, activePin: String?, id: Int?, imgSrc: String?, title: String?, subTitle: String?, hour: String?, distance: Float?, name: String?) : this(){
        this.imgsList = imgsList
        this.activePin = activePin
        this.id = id
        this.imgSrc = imgSrc
        this.title = title
        this.subTitle = subTitle
        this.hour = hour
        this.distance = distance
        this.name = name
    }

    constructor()

}
