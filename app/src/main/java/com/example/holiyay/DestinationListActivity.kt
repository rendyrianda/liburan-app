package com.example.holiyay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.holiyay.adapter.RecyclerViewAdapter
import com.example.holiyay.model.Model
import com.example.holiyay.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_destination_list.*
import kotlinx.android.synthetic.main.activity_destination_list.iv_backstage
import kotlinx.android.synthetic.main.activity_detail_popular_destination.*

class DestinationListActivity : AppCompatActivity() {
    private lateinit var popularHotelAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_list)

        supportActionBar?.hide()
        showRecyclerGrid()

        showBackHome()

    }

    private fun getListHotel(): ArrayList<Model> {
        val dataName = resources.getStringArray(R.array.title)
        val dataDesc = resources.getStringArray(R.array.desc)
        val dataAddress = resources.getStringArray(R.array.address)
        val dataharga = resources.getStringArray(R.array.data_price)
        val dataPhoto = resources.obtainTypedArray(R.array.image)

        val listHotel = ArrayList<Model>()
        for (position in dataName.indices) {
            val hotel = Model(
                dataName[position],
                dataDesc[position],
                dataAddress[position],
                dataharga[position],
                dataPhoto.getResourceId(position, -1)

            )
            listHotel.add(hotel)
        }
        return listHotel
    }

    private fun showRecyclerGrid() {
        //buat variable untuk nampung klass ini
        popularHotelAdapter = RecyclerViewAdapter { showSelected(it) }
        popularHotelAdapter.notifyDataSetChanged()
        popularHotelAdapter.setData(getListHotel())
        rv_all_popular.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        rv_all_popular.layoutManager = layoutManager
        rv_all_popular.adapter = popularHotelAdapter


    }

    private fun showSelected(it: Model) {
        val page = Intent(this, DetailPopularDestinationActivity::class.java)
        page.putExtra(DetailPopularDestinationActivity.KEY_POPULAR_HOTEL, it)
        startActivity(page)
    }

    private fun showBackHome() {
        iv_backstage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}