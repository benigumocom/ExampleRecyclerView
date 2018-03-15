package com.benigumo.erv

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import com.benigumo.erv.data.RemoteRepository
import com.benigumo.erv.model.Item
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.android.synthetic.main.activity_main.swipe_refresh_layout
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
    val onDelete = DeleteItemHandler(this, remoteRepository)
    val onShare = ShareItemHandler(this)

    val callback = object : ItemAdapter.Callback {
      override fun onItemClicked(item: Item) = onToast(item)
      override fun onDeleteClicked(item: Item) {
        onDelete(item)
        load(false)
      }

      override fun onShareClicked(item: Item) = onShare(item)
    }
    adapter = ItemAdapter(layoutInflater, callback)

    recycler_view.adapter = adapter

    swipe_refresh_layout.setOnRefreshListener {
      load(true)
    }
  }

  override fun onResume() {
    super.onResume()
    load(false)
  }

  override fun onPause() {
    job.cancel()
    super.onPause()
  }

  private fun load(shuffle: Boolean) {
    job = launch(UI) {

      swipe_refresh_layout.isRefreshing = true

      val newItems = remoteRepository.load(shuffle).await()
      val result = async(CommonPool) {
        DiffUtil.calculateDiff(DiffUtilCallback(items, newItems))
      }.await()

      swipe_refresh_layout.isRefreshing = false

      adapter.updateItems(newItems)
      result.dispatchUpdatesTo(adapter)

      items = newItems
    }
  }
}
