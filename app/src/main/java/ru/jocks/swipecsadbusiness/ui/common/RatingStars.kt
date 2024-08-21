package ru.jocks.swipecsadbusiness.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.jocks.swipecsadbusiness.R

@Composable
fun RatingStars(
    modifier: Modifier = Modifier,
    rating: Float, size: Dp = 16.dp,
    onRatingClicked: ((Double) -> Unit)? = null
) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = modifier) {
        for (i in 1..5) {
            Image(
                painter = painterResource(id = if (i <= rating) R.drawable.star else R.drawable.star_outlined),
                contentDescription = null,
                Modifier
                    .size(size)
                    .let {
                        if (onRatingClicked != null) {
                            it.clickable {
                                onRatingClicked(i.toDouble())
                            }
                        } else {
                            it
                        }
                    }
            )
        }
    }
}