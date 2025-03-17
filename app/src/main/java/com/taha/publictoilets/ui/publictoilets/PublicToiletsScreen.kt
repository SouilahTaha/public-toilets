package com.taha.publictoilets.ui.publictoilets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ProjectsScreen(
    viewModel: PublicToiletsViewModel = hiltViewModel<PublicToiletsViewModel>(),
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        content = {
            Box(modifier = Modifier.padding(it))
        }
    )
}
