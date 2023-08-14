package com.example.holiyay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.holiyay.R
import com.example.holiyay.databinding.ActivityBookingBinding
import com.example.holiyay.model.Model
import com.example.holiyay.model.User
import com.example.holiyay.presentation.changepassword.ChangePasswordActivity
import com.example.holiyay.presentation.login.LoginActivity
import com.example.holiyay.utils.showDialogError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.jetbrains.anko.startActivity
import kotlinx.android.synthetic.main.activity_detail_popular_destination.*

class BookingActivity : AppCompatActivity() {

    private lateinit var bookBinding: ActivityBookingBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userDatabase: DatabaseReference
    private var currentUser: FirebaseUser? = null
    private var model: Model? = null

    private var listenerUser = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            val user = snapshot.getValue(User::class.java)
            user?.let {
                bookBinding.tvNameUser.text = it.nameUser
                bookBinding.tvEmailUser.text = it.emailUser
                bookBinding.lableNameBook.text = model?.title
                bookBinding.lableDescBook.text = model?.desc
                bookBinding.lableAddressBook.text = model?.address
                bookBinding.txtPriceBook.text = model?.price
            }
        }

        override fun onCancelled(error: DatabaseError) {
            hideLoading()
            showDialogError(this@BookingActivity, error.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookBinding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail_popular_destination)
        lable_name.text = model?.title
        setContentView(bookBinding.root)

        bookBinding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(bookBinding.root)

        //Init
        firebaseAuth = FirebaseAuth.getInstance()
        userDatabase = FirebaseDatabase.getInstance().getReference("users")
        currentUser = firebaseAuth.currentUser

        getDataFirebase()
        onAction()
    }

    private fun onAction() {
        bookBinding.apply {

            swipeUser.setOnRefreshListener {
                getDataFirebase()
            }
        }
    }

    private fun getDataFirebase() {
        showLoading()
        userDatabase
            .child(currentUser?.uid.toString())
            .addValueEventListener(listenerUser)
    }

    private fun showLoading(){
        bookBinding.swipeUser.isRefreshing = true
    }

    private fun hideLoading(){
        bookBinding.swipeUser.isRefreshing = false
    }
}