package com.benigumo.erv

import android.content.Context
import android.widget.Toast
import com.benigumo.erv.data.RemoteRepository
import com.benigumo.erv.model.Item

internal class DeleteItemHandler(
    private val context: Context,
    private val repository: RemoteRepository
) : ItemHandler {

  override fun invoke(item: Item) {
    repository.remove(item)
    Toast.makeText(context, "DELETED!! $item", Toast.LENGTH_SHORT).show()
  }
}