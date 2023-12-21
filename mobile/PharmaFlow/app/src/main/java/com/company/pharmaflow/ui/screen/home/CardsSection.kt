package com.company.pharmaflow.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.pharmaflow.data.Card
import com.company.pharmaflow.ui.theme.BlueEnd
import com.company.pharmaflow.ui.theme.BlueStart
import com.company.pharmaflow.ui.theme.PurpleEnd
import com.company.pharmaflow.ui.theme.PurpleStart

val cards = listOf(

    Card(
        cardNumber = "+10%",
        cardName = "Pemasukan",
        balance = "9.435.000",
        color = getGradient(PurpleStart, PurpleEnd),
    ),

    Card(
        cardNumber = "+16%",
        cardName = "Keuntungan",
        balance = "5.544.000",
        color = getGradient(BlueStart, BlueEnd),
    ),
)

fun getGradient(
    startColor: Color,
    endColor: Color,
): Brush {
    return Brush.horizontalGradient(
        colors = listOf(startColor, endColor)
    )
}

@Preview
@Composable
fun CardsSection() {
    Row()
    {
        cards.forEachIndexed { index, _ ->
            CardItem(index)
        }
    }
}

@Composable
fun CardItem(
    index: Int
) {
    val card = cards[index]
    var lastItemPaddingEnd = 0.dp
    if (index == cards.size - 1) {
        lastItemPaddingEnd = 16.dp
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = lastItemPaddingEnd)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(card.color)
                .width(170.dp)
                .height(110.dp)
                .clickable {}
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = card.cardName,
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Rp${card.balance}",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = card.cardNumber,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )

        }
    }
}