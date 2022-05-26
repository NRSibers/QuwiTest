package test.quwi.com.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.quwi.com.R
import test.quwi.com.auth.AuthFragment
import test.quwi.com.base.FragmentRoute
import test.quwi.com.chat.ChatFragment

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            mainViewModel.init()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.logout_menu -> {
                mainViewModel.logout()
                true
            }
            else -> {
                false
            }
        }
    }
}