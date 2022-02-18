package com.example.stree20.ui.fragments.slacksetupfragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
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
    private lateinit var sharePreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSlackSetupBinding.bind(view)

        sharePreferences =
            requireActivity().getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)
        editor = sharePreferences.edit()

        openSlackWeb(Constants.URI)
        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.auth.collect { state ->
                when (state) {
                    is SlackSetupViewModel.State.Success -> {
                        editor.apply {
                            putString(Constants.TOKEN_KEY, state.auth?.access_token!!)
                            apply()
                        }
                        toast("welcome ${state.auth?.team_name}")
                        Log.e("SlackSetup", "Token ${state.auth?.access_token}")
                        findNavController().navigateUp()
                        binding.progressBar.visibility = View.GONE

                    }

                    is SlackSetupViewModel.State.Error -> {
                        toast(state.msg ?: "An unknown error occurred.")
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
                            Log.e("SlackSetup", url)
                            val substring: String =
                                url.substring(url.indexOf("code=") + 5, url.indexOf("&"))
                            Log.e("SlackSetup", substring)
                            editor.apply {
                                putString(Constants.CODE_KEY, substring)
                                apply()
                            }
                            destroy()
                            sharePreferences.getString(Constants.CODE_KEY, null)?.let {
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