package com.benigumo.erv

import android.content.Context
import android.widget.Toast
import com.benigumo.erv.model.Item

internal class ToastItemHandler(
    private val context: Context
) : ItemHandler {

  override fun invoke(item: Item) {
    Toast.makeText(context, "$item", Toast.LENGTH_SHORT).show()
  }

}