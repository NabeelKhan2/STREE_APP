package com.example.stree20.ui.fragments.settingfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.stree20.R
import com.example.stree20.databinding.FragmentSettingBinding
import com.example.stree20.utils.extensions.alert
import com.example.stree20.utils.extensions.negativeButton
import com.example.stree20.utils.extensions.positiveButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment(R.layout.fragment_setting) {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingBinding.bind(view)

        binding.clSetting.setOnClickListener {

            val token = viewModel.getToken()
            if (token == null) {
                findNavController().navigate(
                    SettingFragmentDirections.actionSettingFragmentToSlackSetupFragment()
                )
                return@setOnClickListener
            }
            alert {
                setTitle(getString(R.string.authenticated))
                setMessage(getString(R.string.already_authenticated))
                positiveButton(getString(R.string.reset)) {
                    findNavController().navigate(
                        SettingFragmentDirections.actionSettingFragmentToSlackSetupFragment()
                    )
                }
                negativeButton(getString(R.string.cancel))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}