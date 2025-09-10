package com.example.habit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstFragment(onNavigateToFragment2: () -> Unit) {
    // TODO: Make home screen
    Text("It's your first Screen")
}