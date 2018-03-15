package com.benigumo.erv

import android.R.style
import android.graphics.Color
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import com.benigumo.erv.model.Item
import kotlin.LazyThreadSafetyMode.NONE

class ItemViewHolder(
    private val root: View,
    private val callback: ItemAdapter.Callback
) : ViewHolder(root), OnClickListener, OnMenuItemClickListener {

  private var item: Item? = null

  private val name = root.findViewById<TextView>(R.id.text_name)
  private val age = root.findViewById<TextView>(R.id.text_age)
  private val more = root.findViewById<ImageButton>(R.id.button_more)

  private val popup by lazy(NONE) {
    val window = PopupMenu(root.context, more, Gravity.NO_GRAVITY,
        0, style.Widget_Material_PopupMenu_Overflow)
    more.setOnTouchListener(window.dragToOpenListener)
    window.menuInflater.inflate(R.menu.item, window.menu)
    window.setOnMenuItemClickListener(this)
    return@lazy window
  }
  init {
    root.setOnClickListener(this)
    more.setOnClickListener(this)
  }

  fun setItem(item: Item) {
    this.item = item

    name.text = item.name
    age.text = item.age.toString()
    root.setBackgroundColor(Color.parseColor(item.color))
  }

  override fun onClick(view: View) {
    when(view) {
      root -> callback.onItemClicked(item!!)
      more -> popup.show()
      else -> throw IllegalArgumentException("wrong view clicked!")
    }
  }

  override fun onMenuItemClick(menuItem: MenuItem) = when (menuItem.itemId) {
    R.id.delete -> {
      callback.onDeleteClicked(item!!)
      true
    }
    R.id.share -> {
      callback.onShareClicked(item!!)
      true
    }
    else -> throw IllegalArgumentException("Unknown menu item: $menuItem")
  }

}