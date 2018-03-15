package com.benigumo.erv

import android.graphics.Color
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import com.benigumo.erv.model.Item

class ItemViewHolder(
    private val root: View,
    private val callback: ItemAdapter.Callback
) : ViewHolder(root), OnClickListener {

  private var item: Item? = null

  private val name = root.findViewById<TextView>(R.id.text_name)
  private val age = root.findViewById<TextView>(R.id.text_age)
  private val more = root.findViewById<ImageButton>(R.id.button_more)

  init {
    root.setOnClickListener(this)
    more.setOnClickListener(this)
  }

  fun setItem(item: Item) {
    this.item = item

    name.text = item.name
    age.text = item.age.toString()
    root.setBackgroundColor(Color.parseColor(item.color))
  }

  override fun onClick(view: View) {
    when(view) {
      root -> callback.onItemClicked(item!!)
      more -> callback.onButtonClicked(item!!)
      else -> throw IllegalArgumentException("wrong view clicked!")
    }
  }
}