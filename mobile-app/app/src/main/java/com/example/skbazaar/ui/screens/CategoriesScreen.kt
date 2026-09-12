package com.example.skbazaar.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CategoriesScreen(navController: NavController) {
    Text(text = "Categories Screen", modifier = Modifier.padding(16.dp))
}
