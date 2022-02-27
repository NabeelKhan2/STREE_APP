package com.example.stree20.ui.fragments.addandupdategroupfragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stree20.R
import com.example.stree20.databinding.FragmentAddAndUpdateBaseBinding
import com.example.stree20.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
abstract class FragmentGroupManager : Fragment(R.layout.fragment_add_and_update_base) {

    private var _binding: FragmentAddAndUpdateBaseBinding? = null
    val binding get() = _binding!!


    val viewModel by viewModels<GroupManagerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddAndUpdateBaseBinding.bind(view)

        val token = viewModel.getToken()

        token?.let {
            viewModel.getChannel("Bearer $it")
        }
        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.channel.collect { state ->
                when (state) {
                    is GroupManagerViewModel.State.Success -> {
                        val list = state.channel?.channels?.map { it.name }
                        val adapter = ArrayAdapter(
                            requireContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            list as MutableList<String>
                        )
                        binding.spText.setAdapter(adapter)

                    }
                    is GroupManagerViewModel.State.Error -> {
                        toast(state.msg ?: getString(R.string.unknown_error))
                    }

                    else -> {
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFLow.collect { event ->
                when (event) {
                    is GroupManagerViewModel.Event.Success -> {

                        toast(getString(R.string.group_added))
                        findNavController().navigateUp()

                    }

                    is GroupManagerViewModel.Event.Error -> {
                        toast(event.msg ?: getString(R.string.unknown_error))
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