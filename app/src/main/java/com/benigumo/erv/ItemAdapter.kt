package com.benigumo.erv

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.benigumo.erv.model.Item

class ItemAdapter(
    private val inflater: LayoutInflater,
    private val callback: Callback
) : RecyclerView.Adapter<ItemViewHolder>() {

  var items: List<Item> = emptyList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    val view = inflater.inflate(R.layout.item, parent, false)
    return ItemViewHolder(view, callback)
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.setItem(items[position])
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int, payloads: List<Any>) {

    if (payloads.isEmpty()) {
      onBindViewHolder(holder, position)
      return
    }

    for (payload in payloads) {
    }
  }

  fun updateItems(items: List<Item>) {
    this.items = items
  }

  interface Callback {
    fun onItemClicked(item: Item)
    fun onButtonClicked(item: Item)
  }
}