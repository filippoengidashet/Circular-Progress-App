package com.filippoengidashet.circularprogressapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.filippoengidashet.circularprogressapp.R
import com.filippoengidashet.circularprogressapp.ui.widgets.CircularProgressLayout
import com.filippoengidashet.circularprogressapp.vm.UserViewModel

class MainActivity : AppCompatActivity() {

    companion object {

        private const val TAG = "tag::main::screen"
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionRefresh) {
            reloadInfo()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val subsProgressView = findViewById<CircularProgressLayout>(R.id.cpv_subs_info)
        subsProgressView.setCallback(object : CircularProgressLayout.Callback {

            override fun onUpdate(angle: Float, progress: Int) {
                Log.d(TAG, "Angle -> $angle :: Progress -> $progress")
                findViewById<TextView>(R.id.text_subs_info_progress).text = "$progress"
            }
        })
        val energyProgressView = findViewById<CircularProgressLayout>(R.id.cpv_battery_info)
        energyProgressView.setCallback(object : CircularProgressLayout.Callback {

            override fun onUpdate(angle: Float, progress: Int) {
                Log.d(TAG, "Angle -> $angle :: Progress -> $progress")
                findViewById<TextView>(R.id.text_battery_info_progress).text = "$progress".plus("%")
            }
        })

        val textAnimDelay = findViewById<TextView>(R.id.text_anim_delay)
        findViewById<View>(R.id.button_update).setOnClickListener {
            val delay = textAnimDelay.text.toString().toLong()
            subsProgressView.updateAnimationDelay(delay)
            energyProgressView.updateAnimationDelay(delay)
            showToast("Animation Speed Updated!")
        }

        val vm = ViewModelProvider(this).get(UserViewModel::class.java)

        vm.getUserLiveData().observe(this) { userData ->
            subsProgressView.animateRange(0f, 1000f, userData.subscriptionMilesLeft)
            energyProgressView.animateRange(0f, 100f, userData.lastEnergyLevel)
        }
    }

    private fun reloadInfo() {
        ViewModelProvider(this).get(UserViewModel::class.java).loadUser {
            showToast(it)
        }
    }
}