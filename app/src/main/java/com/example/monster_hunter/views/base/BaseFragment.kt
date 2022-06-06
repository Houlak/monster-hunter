package com.example.monster_hunter.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment(layoutId:Int):Fragment(layoutId) {

    abstract val viewModel: ViewModel

    abstract fun observeDataSources()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDataSources()
    }

}