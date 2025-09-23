package com.example.stocks.presentation.ui.utility

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stocks.R

@Composable
fun TitleAndValueAsRow(
    title: String,
    value: String,
    status: Boolean = false,
    colorValue: Color = if(isSystemInDarkTheme()) Color.White else Color.Black,
    onArrayClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = title)
            if (status) IconButton(
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp),
                onClick = { onArrayClicked }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(id = R.string.image),
                    tint = Color.Black,
                )
            }
        }
        Text(
            text = value,
            color = colorValue,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Preview
@Composable
fun PreviewTitleAndValueAsRow() {
    TitleAndValueAsRow("Current value*", "27,893.65") {}
}