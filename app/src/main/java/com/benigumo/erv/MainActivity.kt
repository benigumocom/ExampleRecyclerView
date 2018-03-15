package com.benigumo.erv

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import com.benigumo.erv.data.RemoteRepository
import com.benigumo.erv.model.Item
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class MainActivity : AppCompatActivity() {

  private lateinit var job: Job
  private lateinit var adapter: ItemAdapter
  private var items = emptyList<Item>()
  private val remoteRepository = RemoteRepository()

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
  }

  override fun onResume() {
    super.onResume()
    job = loadItemsRepeatedly()
  }

  override fun onPause() {
    job.cancel()
    super.onPause()
  }

  private fun loadItemsRepeatedly() = launch(UI) {

    while (true) {
      val newItems = remoteRepository.loadItems().await()
      val result = async(CommonPool) {
        DiffUtil.calculateDiff(DiffUtilCallback(items, newItems))
      }.await()

      adapter.updateItems(newItems)
      result.dispatchUpdatesTo(adapter)
      recycler_view.scrollToPosition(0)

      items = newItems
    }
  }
}
