package com.benigumo.erv

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.benigumo.erv.data.RemoteRepository
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

  @Test
  fun useAppContext() {
    val appContext = InstrumentationRegistry.getTargetContext()
    assertEquals("com.benigumo.erv", appContext.packageName)
  }

  @Test
  fun loadItems() {
    val remoteRepository = RemoteRepository()
    val job = launch(UI) {
      val items = remoteRepository.load(true).await()
      println(items)
      assertTrue(items.isNotEmpty())
    }
    job.cancel()
  }

}
