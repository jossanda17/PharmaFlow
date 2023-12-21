package com.company.pharmaflow.data

import androidx.compose.ui.graphics.Brush

data class Card(
    val cardNumber: String,
    val cardName: String,
    val balance: String,
    val color: Brush
)