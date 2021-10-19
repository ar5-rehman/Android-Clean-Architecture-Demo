package com.mann.ouiparisproject.ui.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mann.ouiparisproject.R
import com.mann.ouiparisproject.data.model.CategoryModel
import com.mann.ouiparisproject.data.model.SectionModel
import com.mann.ouiparisproject.data.model.WeatherPojo
import com.mann.ouiparisproject.databinding.FragmentDiscoveryBinding
import com.mann.ouiparisproject.ui.adapter.CategoriesAdapter
import com.mann.ouiparisproject.ui.adapter.SectionsAdapter
import com.mann.ouiparisproject.ui.adapter.WeatherAdapter
import com.mann.ouiparisproject.ui.viewmodel.MainViewModel
import com.mann.ouiparisproject.utils.ApiState
import com.mann.ouiparisproject.utils.Keys
import com.mann.ouiparisproject.utils.WeatherLayoutDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DiscoveryFragment : Fragment() {

    private var weatherTemperature: Int = 0
    private lateinit var time: String
    private var weatherIcon: String = ""
    private lateinit var currentTime: Date
    private lateinit var currentDate: String

    private lateinit var binding: FragmentDiscoveryBinding

    private lateinit var categoryAdapter: CategoriesAdapter
    private lateinit var sectionAdapter: SectionsAdapter
    private lateinit var categoryList: ArrayList<CategoryModel>
    private lateinit var sectionList: ArrayList<SectionModel>
    private lateinit var weatherList: ArrayList<WeatherPojo>

    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var key: Keys
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var linearlayoutManager: LinearLayoutManager

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDiscoveryBinding.inflate(layoutInflater, container, false)

        setUpViews()
        clickListeners()
        getDataFromAPI()

        return binding.root
    }

    private fun setUpViews(){

        categoryList = ArrayList()
        sectionList = ArrayList()
        weatherList = ArrayList()
        categoryAdapter = CategoriesAdapter(requireContext(), categoryList)
        sectionAdapter = SectionsAdapter(requireContext(), sectionList)

        //categories recycler
        binding.categoryRecyclerView.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(context, 6)
        binding.categoryRecyclerView.layoutManager = gridLayoutManager
        binding.categoryRecyclerView.adapter = categoryAdapter

        //sections recycler
        binding.sectionsRecycler.setHasFixedSize(true)
        linearlayoutManager = LinearLayoutManager(context)
        linearlayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.sectionsRecycler.layoutManager = linearlayoutManager
        binding.sectionsRecycler.adapter = sectionAdapter

        //set date and time
        currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("EEE dd MMM") // Set your date format
        currentDate = sdf.format(currentTime) // Get Date String according to date format
        time = SimpleDateFormat("HH:mm:ss").format(Date())

        binding.date.text = currentDate
        binding.hour.text = time

    }

    private fun getAPIKey(): String{
        return key.getRequestTime()!!
    }

    private fun getHashKey(): String{
        val api_key = "abcdef12345"
        val path = "/api/v2.3/marketing/home/"
        return key.md5(path+""+ getAPIKey()+""+api_key)!!
    }

    private fun clickListeners(){

        //Search layout
        binding.searchBar.setOnClickListener(View.OnClickListener { v->

            findNavController().navigate(
                R.id.action_discover_to_searchFragment,
                null,
                navOptions { // Use the Kotlin DSL for building NavOptions
                    anim {
                        enter = android.R.animator.fade_in
                        exit = android.R.animator.fade_out
                    }
                }
            )

        })

        //Weather data
        binding.weatherLayout.setOnClickListener {
            val dialog = Dialog(
                requireContext(),
                R.style.CustomDialog
            )
            if(weatherIcon.isNotEmpty()){
            dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val window: Window = dialog!!.getWindow()!!
            window.setGravity(Gravity.CENTER)
            // window.getAttributes().y = 420;
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setTitle(null)
            dialog.setContentView(R.layout.weather_details_layout)
            dialog.show()
            dialog.setCanceledOnTouchOutside(true)

            val date: TextView = dialog.findViewById<TextView>(R.id.date)
            val hour: TextView = dialog.findViewById<TextView>(R.id.hour)
            val icon: ImageView =
                dialog.findViewById<ImageView>(R.id.icon)
            val temp: TextView = dialog.findViewById<TextView>(R.id.temperature)
            val hourlyRecycler: RecyclerView = dialog.findViewById(R.id.weatherHourRecyclerView)
            val weatherAdapter: RecyclerView.Adapter<*>
            val linearLayoutManager: GridLayoutManager = GridLayoutManager(context, 2)
            val spanCount = 2
            val spacing = 160 // 70dp
            val includeEdge = false
            hourlyRecycler.addItemDecoration(
                WeatherLayoutDecoration(
                    spanCount,
                    spacing,
                    includeEdge
                )
            )
            hourlyRecycler.layoutManager = linearLayoutManager
            date.setText(currentDate)
            hour.setText(time)
            temp.text = "$weatherTemperature°C"

                if (weatherIcon == "02n") {
                    icon.setImageResource(R.drawable.ic_two)
                } else if (weatherIcon == "03n") {
                    icon.setImageResource(R.drawable.ic_eight)
                } else if (weatherIcon == "02n") {
                    icon.setImageResource(R.drawable.ic_eleven)
                } else if (weatherIcon == "01dn") {
                    icon.setImageResource(R.drawable.ic_five)
                } else if (weatherIcon == "02n") {
                    icon.setImageResource(R.drawable.ic_nine)
                } else if (weatherIcon == "02n") {
                    icon.setImageResource(R.drawable.ic_one)
                } else if (weatherIcon == "02d") {
                    icon.setImageResource(R.drawable.ic_six)
                } else if (weatherIcon == "02d") {
                    icon.setImageResource(R.drawable.ic_ten)
                } else if (weatherIcon == "01d") {
                    icon.setImageResource(R.drawable.ic_three)
                } else if (weatherIcon == "01d") {
                    icon.setImageResource(R.drawable.ic_four)
                } else if (weatherIcon == "01d") {
                    icon.setImageResource(R.drawable.ic_twelve)
                }
                weatherAdapter = WeatherAdapter(weatherList)
                hourlyRecycler.adapter = weatherAdapter

            }

        }
    }

    private fun getDataFromAPI(){
        mainViewModel.getDiscoveryData("en", getHashKey(), getAPIKey(), "2.315,48.368")

        lifecycleScope.launchWhenStarted {
            mainViewModel._getDiscoveryStateFlow.collect {it->
                when(it){
                    is ApiState.Loading->{
                        binding.categoryRecyclerView.isVisible=false
                    }
                    is ApiState.Failure -> {
                        binding.categoryRecyclerView.isVisible = false
                    }
                    is ApiState.DiscoverySuccess->{
                        binding.categoryRecyclerView.isVisible = true

                        categoryList = ArrayList()
                        sectionList = ArrayList()
                        weatherList = ArrayList()

                        if(it.data.isSuccessful){

                            if (it.data.body()!!.weather!!.size !== 0) {

                                val weatherSize: Int = it.data.body()!!.weather!!.size
                                for (k in 0 until weatherSize - 1) {
                                    val weatherDate = it.data.body()!!.weather!!.get(k).date!!
                                    val weatherHour = it.data.body()!!.weather!!.get(k).hour!!
                                    weatherTemperature = it.data.body()!!.weather!!.get(k).temperature!!
                                    weatherIcon = it.data.body()!!.weather!!.get(k).icon!!

                                    weatherList.add(WeatherPojo(weatherHour, weatherTemperature, weatherIcon))

                                    binding.temperature.text = "$weatherTemperature°C"

                                    if (weatherIcon == "02n") {
                                        binding.icon.setImageResource(R.drawable.ic_two)
                                    } else if (weatherIcon == "03n") {
                                        binding.icon.setImageResource(R.drawable.ic_eight)
                                    } else if (weatherIcon == "02n") {
                                        binding.icon.setImageResource(R.drawable.ic_eleven)
                                    } else if (weatherIcon == "01dn") {
                                        binding.icon.setImageResource(R.drawable.ic_five)
                                    } else if (weatherIcon == "02n") {
                                        binding.icon.setImageResource(R.drawable.ic_nine)
                                    } else if (weatherIcon == "02n") {
                                        binding.icon.setImageResource(R.drawable.ic_one)
                                    } else if (weatherIcon == "02d") {
                                        binding.icon.setImageResource(R.drawable.ic_six)
                                    } else if (weatherIcon == "02d") {
                                        binding.icon.setImageResource(R.drawable.ic_ten)
                                    } else if (weatherIcon == "01d") {
                                        binding.icon.setImageResource(R.drawable.ic_three)
                                    } else if (weatherIcon == "01d") {
                                        binding.icon.setImageResource(R.drawable.ic_four)
                                    } else if (weatherIcon == "01d") {
                                        binding.icon.setImageResource(R.drawable.ic_twelve)
                                    }
                                }
                            }

                            if(it.data.body()!!.categories!!.isNotEmpty()){

                                val categorySize = it.data.body()!!.categories!!.size

                                //All the categories icons and data here
                                for (i in 0..categorySize - 1) {
                                    val categoryID: Int = it.data.body()!!.categories!!.get(i).id!!
                                    val categoryIcon: String = it.data.body()!!.categories!!.get(i).icon!!
                                    val categoryName: String = it.data.body()!!.categories!!.get(i).name!!

                                    categoryList.add(CategoryModel(categoryID, categoryIcon, categoryName))
                                }

                                categoryAdapter.setData(categoryList)
                            }

                            //get all sections
                            val sectionSize: Int = it.data.body()!!.sections!!.size

                            for (j in 0 until sectionSize - 1) {
                                val sectionTitle = it.data.body()!!.sections!!.get(j).sectionTitle!!
                                val sectionItemList = it.data.body()!!.sections!!.get(j).items!!

                                sectionList.add(SectionModel(sectionTitle, sectionItemList))
                            }

                            sectionAdapter.setSectionData(sectionList)


                        }
                    }
                    is ApiState.Empty->{

                    }
                    else -> {

                    }
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoveryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoveryFragment().apply {
                arguments = Bundle().apply {
                    putString("ARG_PARAM1", param1)
                    putString("ARG_PARAM2", param2)
                }
            }
    }
}