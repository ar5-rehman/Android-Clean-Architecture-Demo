package com.mann.ouiparisproject.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mann.ouiparisproject.data.model.ResultModel
import com.mann.ouiparisproject.data.model.SearchPojo
import com.mann.ouiparisproject.databinding.FragmentSearchBinding
import com.mann.ouiparisproject.ui.adapter.CatSubCatSearchResultAdapter
import com.mann.ouiparisproject.ui.adapter.SearchResultAdapter
import com.mann.ouiparisproject.ui.viewmodel.MainViewModel
import com.mann.ouiparisproject.utils.ApiState
import com.mann.ouiparisproject.utils.Keys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var key: Keys

    private lateinit var activitiesAdapter: RecyclerView.Adapter<*>
    private lateinit var recentLinearLayoutManager: LinearLayoutManager
    private lateinit var categoriesLinearLayoutManager: LinearLayoutManager
    private lateinit var subCategoriesLinearLayoutManager: LinearLayoutManager
    private lateinit var activitiesLinearLayoutManager: LinearLayoutManager
    private lateinit var eventsLinearLayoutManager: LinearLayoutManager
    private lateinit var guideLinearLayoutManager: LinearLayoutManager

    private val recentList: ArrayList<ResultModel> = ArrayList<ResultModel>()
    private var activityList: ArrayList<ResultModel> = ArrayList<ResultModel>()
    private var eventsList: ArrayList<ResultModel> = ArrayList<ResultModel>()
    private var guidesList: ArrayList<ResultModel> = ArrayList<ResultModel>()
    private var categoriesList: ArrayList<ResultModel> = ArrayList<ResultModel>()
    private var subCategoriesList: ArrayList<ResultModel> = ArrayList<ResultModel>()
    private var activitiesImagesList = ArrayList<String>()
    private var eventsImagesList: ArrayList<String> = ArrayList<String>()
    private var guidesImagesList: ArrayList<String> = ArrayList<String>()

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        setUpViews()
        setUpListeners()

        return binding.root
    }

    private fun setUpViews(){
        binding.searchBar.requestFocus()

        binding.activitiesRecycler.setHasFixedSize(true)
        binding.eventsRecycler.setHasFixedSize(true)
        binding.guidesRecycler.setHasFixedSize(true)
        binding.categoriesChipsRecycler.setHasFixedSize(true)
        binding.subCategoriesChipsRecycler.setHasFixedSize(true)
        binding.recentsRecycler.setHasFixedSize(true)

        categoriesLinearLayoutManager = LinearLayoutManager(context)
        categoriesLinearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.categoriesChipsRecycler.layoutManager = categoriesLinearLayoutManager

        subCategoriesLinearLayoutManager = LinearLayoutManager(context)
        subCategoriesLinearLayoutManager.orientation = RecyclerView.HORIZONTAL
        binding.subCategoriesChipsRecycler.layoutManager = subCategoriesLinearLayoutManager

        activitiesLinearLayoutManager = LinearLayoutManager(context)
        activitiesLinearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.activitiesRecycler.layoutManager = activitiesLinearLayoutManager

        eventsLinearLayoutManager = LinearLayoutManager(context)
        eventsLinearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.eventsRecycler.layoutManager = eventsLinearLayoutManager

        guideLinearLayoutManager = LinearLayoutManager(context)
        guideLinearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.guidesRecycler.layoutManager = guideLinearLayoutManager

        recentLinearLayoutManager = LinearLayoutManager(context)
        recentLinearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.recentsRecycler.layoutManager = recentLinearLayoutManager

        recentSharedPreferences = requireContext()!!.getSharedPreferences("recent", Context.MODE_PRIVATE)
        editor = recentSharedPreferences.edit()

        val json: String = recentSharedPreferences.getString("recentResults", "")!!
        if (json.isEmpty()) {
//            Toast.makeText(context,"There is something error",Toast.LENGTH_LONG).show();
        } else {
            binding.recentLayout.visibility = View.VISIBLE
            val type = object : TypeToken<List<ResultModel?>?>() {}.type
            val arrPackageData: List<ResultModel> =
                gson.fromJson<List<ResultModel>>(json, type)
            for (data in arrPackageData) {
                recentList.add(
                    ResultModel(
                        null,
                        "",
                        data.id,
                        data.imgSrc,
                        data.title,
                        "",
                        data.hour,
                        data.distance,
                        data.name
                    )
                )
            }
            activitiesAdapter = SearchResultAdapter(recentList, requireContext(), "recent")
            binding.recentsRecycler.adapter = activitiesAdapter
        }
    }

    @InternalCoroutinesApi
    private fun setUpListeners(){
        binding.searchBar.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                getSearchResult()
                true
            } else {
                false
            }
        }
    }

    @InternalCoroutinesApi
    private fun getSearchResult() {

        val term = binding.searchBar.text.toString()

        mainViewModel.getSearchResult("en", term , getHashKey(), getAPIKey())

        lifecycleScope.launchWhenStarted {
            mainViewModel._getSearchStateFlow.collect {it->
                when(it){
                    is ApiState.Loading->{
                        /* binding.recyclerview.isVisible=false
                         binding.progressBar.isVisible=true*/
                    }
                    is ApiState.Failure -> {
                        /* binding.recyclerview.isVisible = false
                         binding.progressBar.isVisible = false
                         Log.d("main", "onCreate: ${it.msg}")*/
                    }
                    is ApiState.SearchSuccess->{

                        searchAPICall(it.data)

                    }
                    is ApiState.Empty->{

                    }
                }
            }
        }

    }

    private fun getAPIKey(): String{

        return key.getRequestTime()!!
    }

    private fun getHashKey(): String{
        val api_key = "abcdef12345"
        val path = "/api/v2.3/activities/search/"
        return key.md5(path+getAPIKey()+api_key)!!
    }

    fun searchAPICall(response: Response<SearchPojo>){

        binding.recentLayout.visibility = View.GONE
        binding.parent.visibility = View.VISIBLE

        if(response.isSuccessful){

            val categorySize: Int = response.body()!!.categories!!.size
            if (categorySize != 0) {
                categoriesList = ArrayList()
                binding.categoriesChipsRecycler.visibility = View.VISIBLE
            } else {
                binding.categoriesChipsRecycler.visibility = View.GONE
            }
            for (n in 0 until categorySize) {
                val title: String = response.body()!!.categories!!.get(n).name!!
                val id: Int = response.body()!!.categories!!.get(n).id!!
                categoriesList.add(ResultModel(null, "", id, "", title, "", "", 0.0F, ""))
            }
            if (categorySize != 0) {
                activitiesAdapter = CatSubCatSearchResultAdapter(1, categoriesList, context)
                binding.categoriesChipsRecycler.adapter = activitiesAdapter
            }

            if (response.body()!!.categories != null) {
                val subCategorySize: Int = response.body()!!.subCategories!!.size
                if (subCategorySize != 0) {
                    subCategoriesList = ArrayList()
                    binding.subCategoriesChipsRecycler.visibility = View.VISIBLE
                } else {
                    binding.subCategoriesChipsRecycler.visibility = View.GONE
                }
                for (n in 0..subCategorySize - 1) {
                    val title: String = response.body()!!.subCategories!!.get(n).name!!
                    val id: Int = response.body()!!.subCategories!!.get(n).id!!
                    subCategoriesList.add(ResultModel(null, "", id, "", title, "", "", 0.0F, ""))
                }
                if (subCategorySize != 0) {
                    activitiesAdapter = CatSubCatSearchResultAdapter(2, subCategoriesList, context)
                    binding.subCategoriesChipsRecycler.adapter = activitiesAdapter
                }
            }

            if (response.body()!!.activities != null) {
                val activitiesSize: Int = response.body()!!.activities!!.size
                if (activitiesSize != 0) {
                    activityList = ArrayList()
                    binding.activitiesTxt.visibility = View.VISIBLE
                    binding.activitiesRecycler.visibility = View.VISIBLE
                } else {
                    binding.activitiesTxt.visibility = View.GONE
                    binding.activitiesRecycler.visibility = View.GONE
                }
                var activitiesPictures = ""
                var hour = ""
                for (i in 0..activitiesSize - 1) {
                    activitiesImagesList = ArrayList()
                    activitiesPictures = ""
                    val id: Int = response.body()!!.activities!!.get(i).id!!
                    val pictureSize: Int = response.body()!!.activities!!.get(i)!!.pictures!!.size
                    val activePin: String =
                        response.body()!!.activities!!.get(i).displayCatSubCat!!.pinActive!!
                    for (ii in 0..pictureSize - 1) {
                        activitiesPictures = response.body()!!.activities!!.get(i).pictures!!.get(ii)
                        activitiesImagesList.add(activitiesPictures)
                    }
                    val title: String = response.body()!!.activities!!.get(i).locationName!!
                    val subTitle: String = response.body()!!.activities!!.get(i).name!!
                    val name: String =
                        response.body()!!.activities!!.get(i).displayCatSubCat!!.name!!
                    val workingHoursSize: Int =
                        response.body()!!.activities!!.get(i).workingHours!!.size
                    for (n in 0..workingHoursSize - 1) {
                        val closeOrOpen: Boolean =
                            response.body()!!.activities!!.get(i).workingHours!!.get(n).closed!!
                        if (!closeOrOpen) {
                            val c = Calendar.getInstance()
                            val dayOfWeek = c[Calendar.DAY_OF_WEEK]
                            var day = dayOfWeek - 1
                            if (day == 0) {
                                day = 7
                            }
                            val dayOfTheWeek: Int =
                                response.body()!!.activities!!.get(i).workingHours!!.get(n)
                                    .dayOfWeek!!
                            if (dayOfTheWeek == day) {
                                val openTime1: String =
                                    response.body()!!.activities!!.get(i).workingHours!!.get(n)
                                        .openTime1!!
                                val closeTime1: String =
                                    response.body()!!.activities!!.get(i).workingHours!!.get(n)
                                        .closeTime1!!
                                var openTime2 = ""
                                var closeTime2 = ""
                                if (response.body()!!.activities!!.get(i).workingHours!!.get(n)
                                        .openTime2 != null && response.body()!!.activities!!.get(i)
                                        .workingHours!!.get(n).openTime2 != null
                                ) {
                                    openTime2 =
                                        response.body()!!.activities!!.get(i).workingHours!!.get(n)
                                            .openTime2.toString()
                                    closeTime2 =
                                        response.body()!!.activities!!.get(i).workingHours!!.get(n)
                                            .openTime2.toString()
                                }
                                val currentTime =
                                    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(
                                        Date()
                                    )
                                if (currentTime.compareTo(openTime1) > 0 && currentTime.compareTo(
                                        closeTime1
                                    ) < 0
                                ) {
                                    hour = "open"
                                } else {
                                    hour = "close"
                                    if (openTime2 != "" && closeTime2 != "") {
                                        hour =
                                            if (currentTime.compareTo(openTime2) > 0 && currentTime.compareTo(
                                                    closeTime2
                                                ) < 0
                                            ) {
                                                "open"
                                            } else {
                                                "close"
                                            }
                                    }
                                }
                            }
                        }
                    }
                    val lat: Double = response.body()!!.activities!!.get(i).lat!!
                    val lng: Double = response.body()!!.activities!!.get(i).lng!!
                    val loc1 = Location("")

                    /*if (MainActivity.mylat !== 0.0) {
                         loc1.latitude = 33.994648
                         loc1.longitude = 72.910622
                     }*/

                    loc1.latitude = 33.994648
                    loc1.longitude = 72.910622

                    val loc2 = Location("")
                    loc2.latitude = lat
                    loc2.longitude = lng
                    val distanceinKM = loc1.distanceTo(loc2) / 1000
                    activityList.add(
                        ResultModel(
                            activitiesImagesList,
                            activePin,
                            id,
                            activitiesPictures,
                            title,
                            subTitle,
                            hour,
                            distanceinKM,
                            name
                        )
                    )
                }
                if (activitiesSize != 0) {
                    activitiesAdapter = SearchResultAdapter(activityList, requireContext(), "activities")
                    binding.activitiesRecycler.adapter = activitiesAdapter
                }
            }
            if (response!!.body()!!.events != null) {
                val eventSize: Int = response!!.body()!!.events!!.size
                if (eventSize != 0) {
                    eventsList = ArrayList()
                    binding.eventsTxt.visibility = View.VISIBLE
                    binding.eventsRecycler.visibility = View.VISIBLE
                } else {
                    binding.eventsTxt.visibility = View.GONE
                    binding.eventsRecycler.visibility = View.GONE
                }
                var eventsPictures = ""

                for (j in 0..eventSize - 1) {
                    eventsImagesList = ArrayList()
                    eventsPictures = ""
                    val id: Int =  response!!.body()!!.events!!.get(j).id!!
                    val pictureSize: Int =  response!!.body()!!.events!!.get(j).pictures!!.size
                    //String activePin = searchResult.getEvents().get(j).getDisplayCatSubCat().getPinActive();
                    val activePin = ""
                    for (jj in 0..pictureSize - 1) {
                        eventsPictures =
                            response!!.body()!!.events!!.get(j).pictures!!.get(jj).picture!!
                        eventsImagesList.add(eventsPictures)
                    }
                    val title: String =  response!!.body()!!.events!!.get(j).name!!
                    val subTitle: String =  response!!.body()!!.events!!.get(j).address!!
                    val lat: Double =  response!!.body()!!.events!!.get(j).lat!!
                    val lng: Double =  response!!.body()!!.events!!.get(j).lng!!
                    val loc1 = Location("")

                    /*if (MainActivity.mylat !== 0.0) {
                        loc1.latitude = 33.994648
                        loc1.longitude = 72.910622
                    }*/

                    loc1.latitude = 33.994648
                    loc1.longitude = 72.910622

                    val loc2 = Location("")
                    loc2.latitude = lat
                    loc2.longitude = lng
                    val distanceinKM = loc1.distanceTo(loc2) / 1000
                    eventsList.add(
                        ResultModel(
                            eventsImagesList,
                            activePin,
                            id,
                            eventsPictures,
                            title,
                            subTitle,
                            "",
                            distanceinKM,
                            ""
                        )
                    )
                }
                if (eventSize != 0) {
                    activitiesAdapter = SearchResultAdapter(eventsList, requireContext(), "events")
                    binding.eventsRecycler.adapter = activitiesAdapter
                }
            }
            if (response!!.body()!!.guides != null) {
                val guideSize: Int = response!!.body()!!.guides!!.size
                if (guideSize != 0) {
                    guidesList = ArrayList()
                    binding.guidesTxt.visibility = View.VISIBLE
                    binding.guidesRecycler.visibility = View.VISIBLE
                } else {
                    binding.guidesTxt.visibility = View.GONE
                    binding.guidesRecycler.visibility = View.GONE
                }

                for (k in 0 until guideSize) {

                    guidesImagesList = ArrayList()
                    val id: Int = response!!.body()!!.guides!!.get(k).id!!
                    val imgSrc: String = response!!.body()!!.guides!!.get(k).picture!!
                    val title: String = response!!.body()!!.guides!!.get(k).name!!
                    val subTitle: String = response!!.body()!!.guides!!.get(k).subtitle!!

                    guidesList.add(
                        ResultModel(
                            guidesImagesList,
                            "",
                            id,
                            imgSrc,
                            title,
                            subTitle,
                            "",
                            12.0F,
                            ""
                        )
                    )
                }
                if (guideSize != 0) {
                    activitiesAdapter = SearchResultAdapter(guidesList, requireContext(), "guides")
                    binding.guidesRecycler.adapter = activitiesAdapter
                }
            } else {
               /* binding.shimmerLayout.stopShimmer()
                binding.parent.visibility = View.VISIBLE
                binding.shimmerLayout.setVisibility(View.GONE)*/
                Toast.makeText(context, "Sorry, No record found!", Toast.LENGTH_SHORT).show()
            }

        }



    }

    companion object {

        lateinit var recentSharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        var saveList: ArrayList<ResultModel> = ArrayList<ResultModel>()
        var gson = Gson()

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                    putString("ARG_PARAM2", param2)
                }
            }
    }
}