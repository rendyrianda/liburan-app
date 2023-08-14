package com.example.holiyay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.holiyay.model.Model
import com.example.holiyay.presentation.main.MainActivity
import com.example.holiyay.utils.showDialogSuccess
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_detail_popular_destination.*

class DetailPopularDestinationActivity : AppCompatActivity() {

    companion object {
        const val KEY_POPULAR_HOTEL = "key_popular_hotel"
    }

    private var model: Model? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_popular_destination)
        model = intent.getParcelableExtra(KEY_POPULAR_HOTEL)

        lable_name.text = model?.title
        lable_desc.text = model?.desc
        lable_address.text = model?.address
        txtprice.text = model?.price
        Glide.with(this).load(model?.image).apply(RequestOptions()).override(500)
            .into(iv_image_detail_popular)

        showBackHome()
        berhasilBook()
    }

    private fun showBackHome() {
        iv_backstage.setOnClickListener {
            val intent = Intent(this, DestinationListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun berhasilBook(){
        btn_order.setOnClickListener {
            val dialogSuccess = showDialogSuccess(this, getString(R.string.success_book))
            dialogSuccess.show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}