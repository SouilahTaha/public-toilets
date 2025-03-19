package com.taha.publictoilets.extenstions

import android.location.Location
import com.google.android.gms.maps.model.LatLng

internal fun Location.toLatLng() = LatLng(this.latitude, this.longitude)//todo add test