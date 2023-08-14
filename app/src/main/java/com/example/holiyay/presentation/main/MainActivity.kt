package com.example.holiyay.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.holiyay.R
import com.example.holiyay.databinding.ActivityMainBinding
import com.example.holiyay.model.User
import com.example.holiyay.presentation.user.UserActivity
import com.example.holiyay.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.jetbrains.anko.startActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.holiyay.fragment.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_POSITION = "extra_position"
    }
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userDatabase: DatabaseReference
    private lateinit var materialDatabase: DatabaseReference
    private var currentUser: FirebaseUser? = null
    val defaultMainView = HomeFragment.defaultFragment()

    private var listenerUser = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            hideLoading()
            val user = snapshot.getValue(User::class.java)
            user?.let {
                mainBinding.apply {
                    tvNameUserMain.text = it.nameUser

                    Glide
                        .with(this@MainActivity)
                        .load(it.avatarUser)
                        .placeholder(android.R.color.darker_gray)
                        .into(ivAvatarMain)
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            hideLoading()
            Log.e("MainActivity", "[onCancelled] - ${error.message}")
            showDialogError(this@MainActivity, error.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        supportActionBar?.hide()
        nav_main.setOnNavigationItemSelectedListener  (onNavigationItemSelectedListener)
        addFragment(defaultMainView)

        //Init
        userDatabase = FirebaseDatabase.getInstance().getReference("users")
        currentUser = FirebaseAuth.getInstance().currentUser

        getDataFirebase()
        onAction()
    }

    private fun getDataFirebase() {
        showLoading()
        userDatabase
            .child(currentUser?.uid.toString())
            .addValueEventListener(listenerUser)
    }

    override fun onResume() {
        super.onResume()
        if (intent != null){
            val position = intent.getIntExtra(EXTRA_POSITION, 0)
        }
    }

    private fun showLoading() {
        mainBinding.swipeMain.isRefreshing = true
    }

    private fun hideLoading() {
        mainBinding.swipeMain.isRefreshing = false
    }

    private fun onAction() {
        mainBinding.apply {
            ivAvatarMain.setOnClickListener {
                startActivity<UserActivity>()
            }

            swipeMain.setOnRefreshListener {
                getDataFirebase()
            }
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                val homeFragment = HomeFragment()
                addFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fm_main_container, fragment, fragment::class.java.simpleName)
            .addToBackStack(null).commit()
    }
}