package com.example.monster_hunter.views.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.monster_hunter.R
import com.example.monster_hunter.databinding.FragmentAboutBinding
import com.example.monster_hunter.views.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment():BaseFragment(R.layout.fragment_about) {

    override val viewModel by viewModel<AboutViewModel>()

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            Glide.with(requireContext())
                .load(R.raw.felyine_gif)
                .into(imFelyne)
        }

    }

    override fun observeDataSources() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}