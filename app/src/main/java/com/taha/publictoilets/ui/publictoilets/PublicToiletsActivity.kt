package com.taha.publictoilets.ui.publictoilets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.taha.design_system.theme.PublicToiletsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublicToiletsActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      PublicToiletsTheme {
        ProjectsNavHost()
      }
    }
  }
}