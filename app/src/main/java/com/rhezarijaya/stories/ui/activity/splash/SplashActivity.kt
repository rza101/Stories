package com.rhezarijaya.stories.ui.activity.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import com.rhezarijaya.stories.databinding.ActivitySplashBinding
import com.rhezarijaya.stories.ui.activity.login.LoginActivity
import com.rhezarijaya.stories.ui.activity.main.MainActivity
import com.rhezarijaya.stories.util.AppPreferences
import com.rhezarijaya.stories.util.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {
    private val Context.dataStore by preferencesDataStore(name = Constants.PREFERENCES_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = AppPreferences.getInstance(dataStore)

        var token: String?

        runBlocking {
            token = preferences.getTokenPrefs().first()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (token == null) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }

            finish()
        }, Constants.SPLASH_SCREEN_DELAY)
    }
}