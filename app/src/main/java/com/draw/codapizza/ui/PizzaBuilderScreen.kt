package com.draw.codapizza.ui

import android.icu.text.NumberFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.draw.codapizza.R
import com.draw.codapizza.model.Pizza
import com.draw.codapizza.model.PizzaSize
import com.draw.codapizza.model.Topping

@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier
) {

    var pizza by remember {
        mutableStateOf(Pizza())
    }

    Column(modifier = modifier) {

        PizzaSizeDropdownMenu(
            currentSize = pizza.size,
            onSizeChange = { pizza = pizza.withSize(it) }
        )

        ToppingList(
            pizza = pizza,
            onEditPizza = { pizza = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
        )
        OrderButton(
            pizza = pizza,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun PizzaSizeDropdownMenu(
    currentSize: PizzaSize = PizzaSize.Large,
    onSizeChange: (PizzaSize) -> Unit
) {

    var sizeBeingSelected by remember {
        mutableStateOf(false)
    }

    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { sizeBeingSelected = true }
    ) {
        Text(text = "Size ${stringResource(id = currentSize.size)}")
    }

    DropdownMenu(
        expanded = sizeBeingSelected,
        onDismissRequest = { sizeBeingSelected = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        PizzaSize.values().forEach { pizzaSize ->
            DropdownMenuItem(
                text = { stringResource(id = pizzaSize.size) },
                onClick = {
                    sizeBeingSelected = false
                    onSizeChange(pizzaSize)
                }
            )
        }
    }
}

@Composable
private fun ToppingList(
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit,
    modifier: Modifier = Modifier
) {

    var toppingBeingAdded by remember {
        mutableStateOf<Topping?>(null)
    }

    toppingBeingAdded?.let { topping ->
        ToppingPlacementDialog(
            topping = topping,
            onSetToppingPlacement = { placement ->
                onEditPizza(pizza.withTopping(topping, placement))
            },
            onDismissRequest =  {
                toppingBeingAdded = null
            }
        )
    }

    LazyColumn(modifier = modifier) {

        item {
            PizzaHeroImage(
                pizza,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(Topping.values()) {topping ->
            ToppingCell(
                topping = topping,
                placement = pizza.toppings[topping],
                onClickTopping = {
                    toppingBeingAdded = topping
                }
            )
        }
    }
}


@Composable
private fun OrderButton(
    pizza: Pizza,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = {
            /*TODO*/
        }
    ) {
        val currencyFormatter = remember {
            NumberFormat.getCurrencyInstance()
        }
        val price = currencyFormatter.format(pizza.price)
        Text(
            text = stringResource(R.string.place_order_button, price).toUpperCase(Locale.current)
        )
    }
}