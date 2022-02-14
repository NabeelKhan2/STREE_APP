package com.example.stree20.ui.fragments.slacksetupfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.stree20.R
import com.example.stree20.databinding.FragmentSlackSetupBinding

class SlackSetupFragment : Fragment(R.layout.fragment_slack_setup) {

    private var _binding: FragmentSlackSetupBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSlackSetupBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}