package com.myapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.myapp.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.detail_fragment.backButton
import kotlinx.android.synthetic.main.detail_fragment.contentTextView
import javax.inject.Inject

class DetailFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val args: DetailFragmentArgs by navArgs()

  @Suppress("unused")
  private val viewModel by viewModels<DetailViewModel> { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.detail_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    contentTextView.text = args.surveyItem.title
    backButton.setOnClickListener { goBack() }
  }

  private fun goBack() {
    findNavController().navigateUp()
  }
}
