package com.zumepizza.interview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zumepizza.interview.model.Pizza
import com.zumepizza.interview.ui.detail.DetailFragment
import timber.log.Timber

class Detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        val comePizza = intent.getParcelableExtra<Pizza>("pizza")
        Timber.d("comePizza = " + comePizza)

        if (savedInstanceState == null) {
            val detailFragment =  DetailFragment.newInstance()
            detailFragment.mPizza = comePizza
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, detailFragment)
                .commitNow()
        }

    }

}
