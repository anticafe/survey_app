package com.myapp.utils

/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.lifecycle.Observer

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * Note: it MUST BE data class (so that we can use equal() and hashCode() method in test case)
 */
data class LiveEvent<out T>(private val content: T) {

  private var hasBeenHandled = false

  /**
   * Returns the content and prevents its use again.
   */
  fun getContentIfNotHandled(): T? {
    return if (hasBeenHandled) {
      null
    } else {
      hasBeenHandled = true
      content
    }
  }

  /**
   * Returns the content, even if it's already been handled.
   */
  fun peekContent(): T = content

}

/**
 * An [Observer] for [LiveEvent]s, simplifying the pattern of checking if the [LiveEvent]'s
 * content has already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [LiveEvent]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<LiveEvent<T>> {
  override fun onChanged(event: LiveEvent<T>?) {
    event?.getContentIfNotHandled()
        ?.let { value ->
          onEventUnhandledContent(value)
        }
  }
}