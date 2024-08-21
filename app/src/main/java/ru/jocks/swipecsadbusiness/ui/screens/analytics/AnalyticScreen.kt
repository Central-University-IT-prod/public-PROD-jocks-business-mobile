package ru.jocks.swipecsadbusiness.ui.screens.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.jocks.domain.business.model.BusinessProfileState
import ru.jocks.domain.business.model.FormFieldRatingModel
import ru.jocks.swipecsadbusiness.R
import ru.jocks.swipecsadbusiness.ui.common.RatingStars
import ru.jocks.swipecsadbusiness.ui.theme.AdditionalTextStyles
import timber.log.Timber


@Composable
fun FormRating(
    formFieldRatingModel: FormFieldRatingModel
) {
    Box {
        Row(
            modifier = Modifier
                .wrapContentHeight().defaultMinSize(minHeight = 64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formFieldRatingModel.name,
                style = AdditionalTextStyles.FormRatingText,
                modifier = Modifier.weight(2.8f).padding(end=16.dp)
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Text (
                text = "%.1f".format(formFieldRatingModel.rating),
            )
            Spacer(modifier = Modifier.width(6.dp))
            RatingStars(rating = formFieldRatingModel.rating)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "(${formFieldRatingModel.reviewsCount})",
                style = AdditionalTextStyles.FormRatingTextSmall,
                modifier = Modifier.defaultMinSize(minWidth = 44.dp),
                textAlign = TextAlign.Center
            )

        }

    }
    Divider()
}

@Composable
fun AnalyticScreen() {
    val vm: AnalyticViewModel = getViewModel()

    when (vm.analyticState) {
        is BusinessProfileState.Loading -> {
            CircularProgressIndicator()
        }
        is BusinessProfileState.Error -> {

        }
        is BusinessProfileState.Success -> {
            val info = (vm.analyticState as BusinessProfileState.Success).businessProfile

            val baseRatings = info.rating.map {
                FormFieldRatingModel(it.name, it.rating, it.reviews_count)
            }
            val productsRatings = info.items.map {
                FormFieldRatingModel(it.name, it.rating, it.ratingCount)
            }

            Timber.tag("Analytic").i(baseRatings.toString())

            LazyColumn(
                modifier = Modifier.padding(24.dp),
                content = {
                    item {
                        Text(
                            text = stringResource(id = R.string.title_base_analytic),
                            style = AdditionalTextStyles.analyticsTitle
                        )
                    }

                    item {
                        Spacer(Modifier.height(12.dp))
                    }

                    items(baseRatings) {
                        FormRating(formFieldRatingModel = it)
                    }

                    item {
                        Spacer(Modifier.height(12.dp))
                    }

                    item {
                        Text(
                            text = stringResource(id = R.string.title_product_analytic),
                            style = AdditionalTextStyles.analyticsTitle
                        )
                    }

                    item {
                        Spacer(Modifier.height(12.dp))
                    }

                    items(productsRatings) {
                        FormRating(formFieldRatingModel = it)
                    }
                }
            )
        }
    }
}