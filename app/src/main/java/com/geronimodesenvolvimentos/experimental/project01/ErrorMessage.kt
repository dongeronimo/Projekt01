package com.geronimodesenvolvimentos.experimental.project01

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(msg:String) {
    val invisible = 0.0f
    val visible = 1.0f
    val alpha by animateFloatAsState(if(msg.isNotEmpty())visible else invisible)
    Row(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp)
        .alpha(alpha),
        verticalAlignment = Alignment.CenterVertically){
        Image(painter = painterResource(id = R.drawable.warning),contentDescription = "warning")
        Text(msg)
    }
}