package com.myapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.myapp.R
import com.myapp.data.repo.SurveyItem
import kotlinx.android.synthetic.main.item_survey.view.coverImageView
import kotlinx.android.synthetic.main.item_survey.view.descriptionTextView
import kotlinx.android.synthetic.main.item_survey.view.nameTextView
import kotlinx.android.synthetic.main.item_survey.view.takeSurveyButton

class SurveyAdapter(private val callback: MainFragment.OpenDetailCallback) :
  RecyclerView.Adapter<PagerVH>() {

  private var surveyItems = mutableListOf<SurveyItem>()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): PagerVH = PagerVH(
    LayoutInflater.from(parent.context).inflate(R.layout.item_survey, parent, false)
  )

  override fun getItemCount(): Int = surveyItems.size

  override fun onBindViewHolder(
    holder: PagerVH,
    position: Int
  ) = holder.itemView.run {
    surveyItems[position].let {
      nameTextView.text = it.title
      descriptionTextView.text = it.description

      if (it.coverHighResImageUrl.isNotEmpty()) {
        coverImageView.load(it.coverHighResImageUrl)
      }

      takeSurveyButton.setOnClickListener { _ ->
        callback.click(it)
      }
    }
  }

  fun setItems(list: MutableList<SurveyItem>) {
    val diffCallback = SurveyDiffCallback(surveyItems, list)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    this.surveyItems = list
    diffResult.dispatchUpdatesTo(this)
  }

  fun clearItems() {
    surveyItems.clear()
    notifyDataSetChanged()
  }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)

internal class SurveyDiffCallback(
  private val oldItems: List<SurveyItem>,
  private val newItems: List<SurveyItem>
) : DiffUtil.Callback() {
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldItems[oldItemPosition].id == newItems[newItemPosition].id
  }

  override fun getOldListSize(): Int {
    return oldItems.size
  }

  override fun getNewListSize(): Int {
    return newItems.size
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldItems[oldItemPosition] == newItems[newItemPosition]
  }

}