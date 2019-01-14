package com.solutions.openweatherapp.common.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.app_bar_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)

        if (needToolbar) {
            setSupportActionBar(toolbar)
            supportActionBar?.let { actionBar ->
                if (homeUpEnabled) {
                    actionBar.setDisplayHomeAsUpEnabled(true)
                }

                actionBar.title = toolbarText
            }
        }
    }

    protected abstract val layoutResource: Int

    protected abstract val homeUpEnabled: Boolean

    protected abstract val needToolbar: Boolean

    protected abstract val toolbarText: String

    protected fun setToolbarText(text: String) {
        supportActionBar?.title = text //Clearing default title
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (homeUpEnabled && id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}