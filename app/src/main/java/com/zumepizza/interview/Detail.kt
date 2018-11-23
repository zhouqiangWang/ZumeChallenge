package com.zumepizza.interview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zumepizza.interview.ui.detail.DetailFragment

class Detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment.newInstance())
                .commitNow()
        }
    }

}
