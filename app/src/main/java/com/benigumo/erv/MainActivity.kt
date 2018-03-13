package com.benigumo.erv

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.util.DiffUtil
import com.benigumo.erv.model.Item
import kotlinx.android.synthetic.main.activity_main.recycler_view

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: ItemAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val onToast = ToastItemHandler(this)
    val onShare = ShareItemHandler(this)

    val callback = object : ItemAdapter.Callback {
        override fun onItemClicked(item: Item) = onToast(item)
        override fun onButtonClicked(item: Item) = onShare(item)
    }
    adapter = ItemAdapter(layoutInflater, callback)

    recycler_view.adapter = adapter

    loadItems()
  }

  private fun loadItems() {

    // todo async
    val items: MutableList<Item> = mutableListOf()
    for (i in 0..50) {
      val item = Item(
          System.currentTimeMillis().toInt(),
          "Name $i",
          i * 3
      )
      items.add(item)
    }

    adapter.updateItems(items)
    val result = DiffUtil.calculateDiff(DiffUtilCallback(adapter.items, items))
    result.dispatchUpdatesTo(adapter)

  }

}
