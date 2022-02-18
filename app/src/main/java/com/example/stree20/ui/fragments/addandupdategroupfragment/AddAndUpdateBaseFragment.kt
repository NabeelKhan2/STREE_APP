package com.example.stree20.ui.fragments.addandupdategroupfragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stree20.R
import com.example.stree20.databinding.FragmentAddAndUpdateBaseBinding
import com.example.stree20.utils.constants.Constants
import com.example.stree20.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
abstract class AddAndUpdateBaseFragment : Fragment(R.layout.fragment_add_and_update_base) {

    private var _binding: FragmentAddAndUpdateBaseBinding? = null
    val binding get() = _binding!!

    private lateinit var sharePreferences: SharedPreferences
    val viewModel by viewModels<AddAndUpdateViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddAndUpdateBaseBinding.bind(view)

        sharePreferences =
            requireActivity().getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)

        val token = sharePreferences.getString(Constants.TOKEN_KEY, null)!!
        viewModel.getChannel("Bearer $token")

        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.channel.collect { state ->
                when (state) {
                    is AddAndUpdateViewModel.State.Success -> {
                        val list = state.channel?.channels?.map { it.name }
                        val adapter = ArrayAdapter(
                            requireContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            list as MutableList<String>
                        )
                        binding.spText.setAdapter(adapter)

                    }
                    is AddAndUpdateViewModel.State.Error -> {
                        toast(state.msg ?: "An unknown error occurred.")
                    }

                    else -> {
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventFLow.collect { event ->
                when (event) {
                    is AddAndUpdateViewModel.Event.Success -> {

                        toast("Added Shopping Item")
                        findNavController().navigateUp()

                    }

                    is AddAndUpdateViewModel.Event.Error -> {
                        toast(event.msg ?: "An unknown error occurred")
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