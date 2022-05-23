package test.quwi.com.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.quwi.com.base.FragmentRoute

class MainViewModel : ViewModel() {
    private val mRouteLiveData = MutableLiveData<FragmentRoute>()
    val routeLiveData: LiveData<FragmentRoute>
        get() = mRouteLiveData

    fun openFragment(fragmentRoute: FragmentRoute) {
        mRouteLiveData.value = fragmentRoute
    }
}