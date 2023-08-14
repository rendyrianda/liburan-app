package com.example.holiyay.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.holiyay.DetailPopularDestinationActivity
import com.example.holiyay.DestinationListActivity
import com.example.holiyay.R
import com.example.holiyay.adapter.RecyclerViewAdapter
import com.example.holiyay.model.Model
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() { private val hotellist = ArrayList<Model>()

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    companion object {
        fun defaultFragment(): HomeFragment {
            val home_fragment = HomeFragment()
            //ngirim dia ke onCreateval
            val bundle = Bundle()
            //argument = default function untuk mengirim data
            home_fragment.arguments = bundle
            return home_fragment
        }
    }

    val imageContentSlider = intArrayOf(
        R.drawable.asiaheritage,
        R.drawable.alammayang,
        R.drawable.danausari,
        R.drawable.kawahbiru,
        R.drawable.pantaicinta
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    val imageContentListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            imageView.setImageResource( imageContentSlider[position])

            rv_popular.setHasFixedSize(true)
            hotellist.addAll(getListHotel())
        }
    }

    private fun getListHotel(): ArrayList<Model> {
        val dataName = resources.getStringArray(R.array.title)
        val datadesc = resources.getStringArray(R.array.desc)
        val dataaddress = resources.getStringArray(R.array.address)
        val dataharga = resources.getStringArray(R.array.data_price)
        val dataPhoto = resources.obtainTypedArray(R.array.image)

        // for looping
        val listHotel = ArrayList<Model>()
        for (position in dataName.indices) {
            val hotel = Model(
                dataName[position],
                datadesc[position],
                dataaddress[position],
                dataharga[position],
                dataPhoto.getResourceId(position, -1)
            )
            listHotel.add(hotel)
        }
        return listHotel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carouselView = is_main as CarouselView
        //set yg udh ditempel
        carouselView.setImageListener(imageContentListener)
        //membaca maksimum index yang dibaca
        //menghitung panjang
        carouselView.setPageCount(imageContentSlider.count())

        showRecyclerList()

        tv_see_all.setOnClickListener {
            val list = Intent(context, DestinationListActivity::class.java)
            startActivity(list)
        }
    }

    private fun showRecyclerList() {
        rv_popular.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewAdapter = RecyclerViewAdapter { showSelected(it) }
        recyclerViewAdapter.notifyDataSetChanged()
        recyclerViewAdapter.setData(getListHotel())
        rv_popular.setHasFixedSize(true)
        rv_popular.adapter = recyclerViewAdapter
    }

    private fun showSelected(it: Model) {
        val page = Intent(context, DetailPopularDestinationActivity::class.java)
        page.putExtra(DetailPopularDestinationActivity.KEY_POPULAR_HOTEL, it)
        startActivity(page)
    }
}

