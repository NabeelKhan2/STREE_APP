package com.example.stree20.ui.fragments.slacksetupfragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stree20.R
import com.example.stree20.databinding.FragmentSlackSetupBinding
import com.example.stree20.utils.constants.Constants
import com.example.stree20.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@Suppress("SameParameterValue")
@AndroidEntryPoint
class SlackSetupFragment : Fragment(R.layout.fragment_slack_setup) {

    private var _binding: FragmentSlackSetupBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SlackSetupViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSlackSetupBinding.bind(view)

        openSlackWeb(Constants.URI)
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.auth.collect { state ->
                when (state) {
                    is SlackSetupViewModel.State.Success -> {
                        viewModel.saveToken(state.auth?.access_token!!)

                        toast("welcome ${state.auth.team_name}")
                        findNavController().navigateUp()
                        binding.progressBar.visibility = View.GONE

                    }

                    is SlackSetupViewModel.State.Error -> {
                        toast(state.msg ?: getString(R.string.unknown_error))
                        binding.progressBar.visibility = View.GONE
                    }

                    is SlackSetupViewModel.State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    else -> {
                    }
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openSlackWeb(uri: String) {
        binding.progressBar.isVisible = true
        binding.webView.apply {

            loadUrl(uri)
            settings.javaScriptEnabled = true

            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressBar.isVisible = false
                    if (url != null) {
                        if (url.contains("code=")) {

                            val substring: String =
                                url.substring(url.indexOf("code=") + 5, url.indexOf("&"))

                            viewModel.saveCode(substring)

                            destroy()

                            viewModel.getCode()?.let {
                                viewModel.getAuth(
                                    Constants.CLIENT_ID,
                                    Constants.CLIENT_SECRET,
                                    it,
                                    Constants.BASE_URL
                                )
                            }
                        }
                        super.onPageStarted(view, url, favicon)
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