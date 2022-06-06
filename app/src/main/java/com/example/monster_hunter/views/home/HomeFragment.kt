package com.example.monster_hunter.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monster_hunter.R
import com.example.monster_hunter.databinding.FragmentHomeBinding
import com.example.monster_hunter.views.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override val viewModel by viewModel<HomeViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var armorAdapter: ArmorAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            armorAdapter = ArmorAdapter(requireContext(), { selectedArmor ->
                ArmorDetailDialog(selectedArmor)
                    .show(requireFragmentManager(), this@HomeFragment.tag)
            }, { selectedArmor, isChecked, position ->
                selectedArmor.id?.let { armorId ->
                    if (isChecked) {
                        viewModel.saveFavArmor(armorId, position)
                    } else {
                        viewModel.deleteFavArmor(armorId, position)
                    }
                }

            }
            )
            rvArmors.adapter = armorAdapter
            rvArmors.layoutManager = LinearLayoutManager(requireContext())

            if (viewModel.armors.isEmpty()) {
                viewModel.getArmors()
            } else {
                armorAdapter?.addItems(viewModel.armors, viewModel.favArmorsId)
            }

            etSearch.doAfterTextChanged {
                viewModel.searchArmorByName(it.toString())
            }

            swFavArmors.setOnCheckedChangeListener { compoundButton, isChecked ->
                viewModel.filterFavArmors(isChecked)
            }

        }

    }

    override fun observeDataSources() {

        lifecycleScope.launch {
            viewModel.getArmorsResponse.collect { response ->
                when (response) {
                    is GetArmorsSuccess -> {
                        armorAdapter?.addItems(viewModel.armors, viewModel.favArmorsId)
                        binding.progressBar.visibility = View.GONE
                    }
                    is GetArmorsLoading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.updateFavArmorResponse.collect { response ->
                when (response) {
                    is UpdateFavArmorsSuccess -> {
                        armorAdapter?.favArmorsIds = response.ids
                        armorAdapter?.notifyItemChanged(response.position)
                    }
                }

            }
        }

        lifecycleScope.launch {
            viewModel.searchArmorsResponse.collect { response ->
                with(binding){
                    when(response) {
                        is SearchArmorsSuccess -> {
                            armorAdapter?.addItems(response.data)
                            emptyState.llEmptyMuseum.visibility = View.GONE
                            rvArmors.visibility = View.VISIBLE
                        }
                        is SearchArmorsEmpty -> {
                           emptyState.llEmptyMuseum.visibility = View.VISIBLE
                            emptyState.animationView.playAnimation()
                            rvArmors.visibility = View.GONE
                        }
                    }
                }

            }
        }

        lifecycleScope.launch {
            viewModel.favoriteArmorsResponse.collect { response ->
                with(binding){
                    when(response) {
                        is FavArmorsSuccess -> {
                            armorAdapter?.addItems(response.data)
                            emptyState.llEmptyMuseum.visibility = View.GONE
                            rvArmors.visibility = View.VISIBLE
                        }
                        is FavArmorsUnchecked -> {
                            armorAdapter?.addItems(response.data)
                            emptyState.llEmptyMuseum.visibility = View.GONE
                            rvArmors.visibility = View.VISIBLE
                        }
                        is FavArmorsEmpty -> {
                            emptyState.llEmptyMuseum.visibility = View.VISIBLE
                            emptyState.animationView.playAnimation()
                            rvArmors.visibility = View.GONE
                        }
                    }
                }

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}