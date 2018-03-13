package com.benigumo.erv

import android.support.v7.util.DiffUtil
import com.benigumo.erv.model.Item

internal class DiffUtilCallback(
    private val oldItems: List<Item>,
    private val newItems: List<Item>
) : DiffUtil.Callback() {

  override fun getOldListSize() = oldItems.size

  override fun getNewListSize() = newItems.size

  // check item's order
  override fun areItemsTheSame(oldPosition: Int, newPosition: Int)
      = oldItems[oldPosition].id == newItems[newPosition].id

  // check item's contents
  override fun areContentsTheSame(oldPosition: Int, newPosition: Int)
      = oldItems[oldPosition] == newItems[newPosition]

/*
  override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
    return if (newItems[newPosition].price > oldItems[oldPosition].price)
      R.color.blue else R.color.pink
  }
*/
}