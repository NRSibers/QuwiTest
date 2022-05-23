package test.quwi.com.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import test.quwi.com.R
import test.quwi.com.auth.AuthFragment
import test.quwi.com.base.FragmentRoute
import test.quwi.com.chat.ChatFragment

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthFragment())
                .commitNow()
        }
        observe()
    }

    private fun observe() {
        mainViewModel.routeLiveData.observe(this, {
            when(it) {
                FragmentRoute.AuthFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, AuthFragment())
                        .commitNow()
                }

                FragmentRoute.ChatFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ChatFragment())
                        .commitNow()
                }
            }
        })
    }
}