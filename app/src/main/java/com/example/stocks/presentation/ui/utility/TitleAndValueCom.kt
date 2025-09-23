package com.example.stocks.presentation.ui.utility

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleAndValue(
    title: String,
    value: String,
    valueColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black
) {
    Row(
        modifier = Modifier
            .wrapContentSize(),
        horizontalArrangement = Arrangement.Absolute.Right
    ) {
        Text(title, color = Color.Gray)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            value,
            color = valueColor,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Preview
@Composable
fun PreviewOfTitleAndValue() {
    TitleAndValue("LTP : ", "₹39399", Color.Red)
}