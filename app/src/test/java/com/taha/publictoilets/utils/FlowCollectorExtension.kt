package com.taha.publictoilets.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope

@ExperimentalCoroutinesApi
internal fun <T> Flow<T>.testCollect(scope: TestScope = TestScope()): List<T> {
  val uiStates = mutableListOf<T>()
  val collectJob = scope.launch {
    toList(uiStates)
  }
  scope.testScheduler.advanceUntilIdle()
  collectJob.cancel()
  return uiStates
}