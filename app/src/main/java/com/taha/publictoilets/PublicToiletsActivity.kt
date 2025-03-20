package com.taha.publictoilets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.taha.designsystem.theme.PublicToiletsTheme
import com.taha.publictoilets.navigation.ProjectsNavHost
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