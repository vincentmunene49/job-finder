package com.example.jobfinder.user.home.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun OrgIcon(
    orgIcon: String?,
    @DrawableRes defaultIcon: Int
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        if (orgIcon == null) {
            Image(
                modifier = Modifier.padding(5.dp),
                painter = painterResource(id = defaultIcon),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                modifier = Modifier.padding(5.dp),
                model = orgIcon,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = defaultIcon)
            )
        }
    }

}