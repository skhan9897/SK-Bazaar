package com.example.skbazaar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.skbazaar.data.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // Dummy data for Flipkart-style UI
    val products = listOf(
        Product(1, "Nike Running Shoes", 2999.0, 4999.0, 40.0, "", "Comfortable running shoes", "Nike", "Shoes"),
        Product(2, "Samsung Galaxy M34", 16999.0, 24999.0, 32.0, "", "5G Smartphone", "Samsung", "Electronics"),
        Product(3, "Casual Cotton Shirt", 899.0, 1999.0, 55.0, "", "Slim fit casual shirt", "Roadster", "Fashion"),
        Product(4, "Boat Airdopes 131", 1299.0, 2990.0, 56.0, "", "True Wireless Earbuds", "Boat", "Electronics")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Search for products...") },
                        modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = MaterialTheme.shapes.small
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2874F0)) // Flipkart Blue
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Categories") },
                    label = { Text("Categories") },
                    selected = false,
                    onClick = { navController.navigate("categories") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                    label = { Text("Cart") },
                    selected = false,
                    onClick = { navController.navigate("cart") }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Banner Section
            Surface(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                color = Color(0xFFBBDEFB)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("Flash Sale: Up to 80% Off!", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }

            Text(
                "Deals of the Day",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    ProductCard(product)
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Placeholder for Image
            Surface(
                modifier = Modifier.fillMaxWidth().height(120.dp),
                color = Color.LightGray
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("Product Image", color = Color.Gray)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(text = product.name, maxLines = 1, fontWeight = FontWeight.Medium)
            Text(text = product.brand, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "₹${product.price}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "₹${product.mrp}",
                    style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough),
                    color = Color.Gray
                )
            }
            
            Text(text = "${product.discount}% off", color = Color(0xFF388E3C), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)) // Yellow Flipkart button
            ) {
                Text("Add to Cart", color = Color.Black)
            }
        }
    }
}
