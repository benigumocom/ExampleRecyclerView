package com.benigumo.erv

import android.app.Activity
import android.support.v4.app.ShareCompat.IntentBuilder
import com.benigumo.erv.model.Item

internal class ShareItemHandler(
    private val activity: Activity
) : ItemHandler {

  override fun invoke(item: Item) {
    IntentBuilder.from(activity)
        .setType("text/plain")
        .setChooserTitle("Share $item to ?")
        .setText("$item")
        .startChooser()
  }
}