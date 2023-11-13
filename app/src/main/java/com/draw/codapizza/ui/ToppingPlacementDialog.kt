package com.draw.codapizza.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.draw.codapizza.R
import com.draw.codapizza.model.Topping
import com.draw.codapizza.model.ToppingPlacement

@Composable
fun ToppingPlacementDialog(
    topping: Topping,
    onSetToppingPlacement: (ToppingPlacement?) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(modifier = Modifier.padding(4.dp)) {
            Column {
                val toppingName = stringResource(id = topping.toppingName)

                Text(
                    text = stringResource(id = R.string.placement_prompt, toppingName),
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(24.dp)
                )
                
                ToppingPlacement.values().forEach { placement ->
                    ToppingPlacementOption(
                        placementName = placement.label,
                        onClick = {
                            onSetToppingPlacement(placement)
                            onDismissRequest()
                        }
                    )
                }

                ToppingPlacementOption(
                    placementName = R.string.placement_none,
                    onClick = {
                        onSetToppingPlacement(null)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

@Composable
private fun ToppingPlacementOption(
    @StringRes placementName: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = placementName),
            modifier = Modifier.padding(8.dp)
        )
    }
}