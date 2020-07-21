package com.myoptimind.lilo_xpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginFragment
import com.myoptimind.lilo_xpress.selectuser.SelectUserFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container,
                GuestLoginFragment.newInstance())
            .commit()
    }
}