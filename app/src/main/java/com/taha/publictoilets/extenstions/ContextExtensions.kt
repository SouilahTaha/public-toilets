package com.taha.publictoilets.extenstions

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

internal fun Context.openMap(latitude: Double, longitude: Double) {
  val uri = "geo:$latitude,$longitude?q=$latitude,$longitude"
  val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
  startActivity(intent)
}