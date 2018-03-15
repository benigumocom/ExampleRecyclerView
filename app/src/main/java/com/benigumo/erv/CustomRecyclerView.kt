package com.benigumo.erv

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

  init {
    layoutManager = object : GridLayoutManager(context, 3) {

      // https://stackoverflow.com/a/33985508
      override fun supportsPredictiveItemAnimations() = false
    }
  }
}