package com.benigumo.erv.data

import com.benigumo.erv.model.Item
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import java.util.concurrent.TimeUnit.SECONDS

class RemoteRepository {

  private var items: MutableList<Item> = mutableListOf()

  init {
    val cs = "0123456789ABCDEF".toCharArray().toList()
    for (i in 0..25) {
      val id = System.currentTimeMillis().toInt()
      val name = (i + 65).toChar().toString().repeat(3)
      val age = i
      val color = "#55" + cs.shuffled().take(6).joinToString("")
      items.add(Item(id, name, age, color))
    }
  }

  fun load(shuffle: Boolean): Deferred<List<Item>> = async(CommonPool) {
    if (shuffle) items.shuffle()
    delay(5, SECONDS)
    println("size : ${items.size}")
    items.toList()
  }

  fun remove(item: Item) {
    items.remove(item)
  }

  private fun log(message: String = "") {
    println("[%s] %s".format(Thread.currentThread().name, message))
  }

}