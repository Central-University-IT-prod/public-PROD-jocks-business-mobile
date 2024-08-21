package ru.jocks.swipecsadbusiness.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.jocks.domain.business.model.FormItemModel
import ru.jocks.swipecsadbusiness.R
import ru.jocks.uikit.CustomButton

@Composable
fun FormItem(
    name: String,
    onValueChange: (String) -> Unit,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    field: @Composable (String, (String) -> Unit) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            end = 12.dp
        )
    ) {
        IconButton(
            onClick = onClick,
            enabled = name.isNotEmpty()
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color(0xFF6B4EFF)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        field(name, onValueChange)
    }

    Spacer(Modifier.height(16.dp))
}

@Composable
fun ListForm(
    startPack: List<FormItemModel>,
    fieldForm: @Composable (String, (String) -> Unit) -> Unit,
    actionName: String,
    onClick: (List<FormItemModel>) -> Unit,
    onValueChange: (List<FormItemModel>) -> Unit = {}
) {
    var items by remember {
        mutableStateOf(startPack)
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
                items.forEach { field ->
                    var localValue by remember { mutableStateOf(field.name) }
                    LaunchedEffect(items) {
                        localValue = field.name
                    }
                    FormItem(
                        name = localValue,
                        onValueChange = { fieldName ->
                            localValue = fieldName
                            field.name = fieldName
                        },
                        R.drawable.remove,
                        onClick = {
                            items = items.toMutableList().minus(field)
                            onValueChange(items)
                        },
                        fieldForm
                    )
                }

                    var newFiledValue by remember {
                        mutableStateOf("")
                    }

                    FormItem(
                        name = newFiledValue,
                        onValueChange = {
                            newFiledValue = it
                        },
                        icon = R.drawable.add,
                        {
                            items = items.toMutableList().plus(
                                FormItemModel(
                                    null,
                                    newFiledValue,
                                )
                            )
                            onValueChange(items)
                            newFiledValue = ""
                        },
                        fieldForm
                    )
        }

        if(actionName.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CustomButton(
                    text = actionName,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = items.all { item -> item.name.isNotEmpty() },
                    onClick = { onClick(items) }
                )
            }
        }
    }
}