package com.benigumo.erv

import com.benigumo.erv.model.Item

interface ItemHandler {
  operator fun invoke(item: Item)
}
