package com.benigumo.erv

import com.benigumo.erv.data.RemoteRepository
import com.benigumo.erv.model.Item

internal class DeleteItemHandler(
    private val repository: RemoteRepository
) : ItemHandler {

  override fun invoke(item: Item) {
    repository.remove(item)
  }
}