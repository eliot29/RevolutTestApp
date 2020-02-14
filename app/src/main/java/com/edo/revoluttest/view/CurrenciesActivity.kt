package com.edo.revoluttest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edo.revoluttest.R

class CurrenciesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currencies)
        var fragment = CurrenciesFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
