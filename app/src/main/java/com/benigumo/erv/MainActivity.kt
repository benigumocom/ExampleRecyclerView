package com.benigumo.erv

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import com.benigumo.erv.model.Item
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit.SECONDS

class MainActivity : AppCompatActivity() {

  private lateinit var job: Job
  private lateinit var adapter: ItemAdapter
  private var items = emptyList<Item>()

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
    job = loadItems()
  }

  override fun onPause() {
    job.cancel()
    super.onPause()
  }

  private fun loadItems() = launch(UI) {

    while (true) {
      val newItems = loadFromRemoteFake(items).await()
      val result = async(CommonPool) {
        DiffUtil.calculateDiff(DiffUtilCallback(items, newItems))
      }.await()

      adapter.updateItems(newItems)
      result.dispatchUpdatesTo(adapter)
      recycler_view.scrollToPosition(0)

      items = newItems
    }
  }

  private fun loadFromRemoteFake(items: List<Item>) = async(CommonPool) {

    delay(3, SECONDS)

    if (items.isEmpty()) {
      val cs = "0123456789ABCDEF".toCharArray().toList()
      println(cs)
      (0..30).map {
        log("inside loop $it")
        Item(
            System.currentTimeMillis().toInt(),
            (it + 65).toChar().toString().repeat(3),
            it,
            "#" + cs.shuffled().take(6).joinToString("")
        )
      }
    } else {
      items.shuffled()
    }
  }

  private fun log(message: String = "") {
    println("[%s] %s".format(Thread.currentThread().name, message))
  }
}
