package ru.jocks.swipecsadbusiness.ui.screens.editform

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.getViewModel
import ru.jocks.domain.business.model.BusinessEditFormState
import ru.jocks.domain.business.model.BusinessFormState
import ru.jocks.domain.business.model.FormItemModel
import ru.jocks.swipecsadbusiness.R
import ru.jocks.swipecsadbusiness.ui.common.ListForm
import ru.jocks.uikit.FormField


@Composable
fun EditFormScreenFormItem(
    name: String,
    onValueChange: (String, Boolean) -> Unit,
) {
    FormField(
        title = stringResource(id = R.string.hint_field_name),
        value = name,
        onValueChange = onValueChange
    )
}

@Composable
fun EditFormScreen() {
    val vm: EditFormViewModel = getViewModel()

    if (vm.editFormState is BusinessEditFormState.Error) {
        Text(
            text = (vm.editFormState as BusinessEditFormState.Error).message
        )
    }

    when (vm.formRemoteState) {
        is BusinessFormState.Loading -> {
            CircularProgressIndicator()
        }
        is BusinessFormState.Error -> {

        }
        is BusinessFormState.Success -> {
            ListForm(
                startPack = (vm.formRemoteState as BusinessFormState.Success).fields.map {
                    FormItemModel(0, it)
                },
                fieldForm = { value, onValueChange ->
                    EditFormScreenFormItem(
                        name = value,
                        onValueChange = { it, _ ->
                            onValueChange(it)
                        })
                },
                actionName = stringResource(id = R.string.button_save),
                onClick = {
                    vm.updateForm(
                        it
                    )
                }
            )
        }
    }
}
