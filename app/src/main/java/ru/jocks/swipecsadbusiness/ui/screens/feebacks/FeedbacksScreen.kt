package ru.jocks.swipecsadbusiness.ui.screens.feebacks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.getViewModel
import ru.jocks.domain.business.model.BusinessFeedbackModel
import ru.jocks.swipecsadbusiness.R
import ru.jocks.swipecsadbusiness.ui.common.RatingStars
import ru.jocks.swipecsadbusiness.ui.theme.AdditionalTextStyles
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun LoadingIndicator() {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Spacer(Modifier.weight(1f))
        CircularProgressIndicator()
        Spacer(Modifier.weight(1f))
    }
}


@Composable
fun Feedback(
    feedback: BusinessFeedbackModel
) {
    Box {
        Row(
            modifier = Modifier
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    id = R.string.feedback_date,
                    feedback.date
                ),
                style = AdditionalTextStyles.FormRatingText
            )
        }
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = feedback.rating.toString(),
            )
            Spacer(modifier = Modifier.width(6.dp))
            RatingStars(rating = feedback.rating)
        }
    }
    Divider()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedbacksScreen() {
    val vm: FeedbackViewModel = getViewModel()
    val uiFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())


    val pullRefreshState = rememberPullRefreshState(
        refreshing = vm.feedbackState is FeedbackUiState.Loading,
        onRefresh = { vm.getFeedback() }
    )

    Column(Modifier.padding(horizontal = 24.dp)) {
        Text(
            "Отзывы",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 12.dp)
        )
        Spacer(Modifier.height(24.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.pullRefresh(pullRefreshState)
        ) {
            item {}

            when(vm.feedbackState) {
                is FeedbackUiState.Success -> {
                    val feedbackItems = (vm.feedbackState as FeedbackUiState.Success).feedbacks
                    items(feedbackItems) {item ->
                        Feedback(
                            feedback = BusinessFeedbackModel(uiFormat.format(item.created_at) , item.rating)
                        )
                    }
                }

                is FeedbackUiState.Loading -> {
                    item { LoadingIndicator() }
                }

                is FeedbackUiState.Error -> {
                    item { Text((vm.feedbackState as FeedbackUiState.Error).message) }
                }
            }
        }
    }
}