package com.draw.codapizza.ui

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.draw.codapizza.R
import com.draw.codapizza.model.Pizza
import com.draw.codapizza.model.PizzaSize
import com.draw.codapizza.model.Topping
import com.draw.codapizza.model.ToppingPlacement
import com.draw.codapizza.ui.theme.CodaPizzaTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier
) {

    var pizza by rememberSaveable {
        mutableStateOf(Pizza())
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                title = { Text(text = stringResource(id = R.string.app_name)) }
            )
        },
        content = {
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
    )
}

@Composable
private fun PizzaSizeDropdownMenu(
    currentSize: PizzaSize = PizzaSize.Large,
    onSizeChange: (PizzaSize) -> Unit
) {

    var sizeBeingSelected by rememberSaveable {
        mutableStateOf(false)
    }

    Column {

        DropdownMenu(
            expanded = sizeBeingSelected,
            onDismissRequest = { sizeBeingSelected = false },
            modifier = Modifier.padding(16.dp)
        ) {
            PizzaSize.values().forEach { pizzaSize ->
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = pizzaSize.size))
                    },
                    onClick = {
                        onSizeChange(pizzaSize)
                        sizeBeingSelected = false
                    }
                )
            }
        }

        Button(
            onClick = { sizeBeingSelected = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

        }

        Text(
            text = stringResource(R.string.selected_pizza_info, stringResource(id = currentSize.size)),
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Composable
private fun ToppingList(
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit,
    modifier: Modifier = Modifier
) {

    var toppingBeingAdded by rememberSaveable {
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

    val context = LocalContext.current

    Button(
        modifier = modifier,
        onClick = {
            Toast.makeText(context, R.string.order_placed_toast, Toast.LENGTH_LONG).show()
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

// @Preview
@Composable
private fun PizzaSizeDropdownMenuPreview() {

    var pizza by remember {
        mutableStateOf(Pizza(
            toppings = mapOf(
                Topping.Pineapple to ToppingPlacement.All,
                Topping.Pineapple to ToppingPlacement.All,
                Topping.Pineapple to ToppingPlacement.All
            )
        ))
    }

    PizzaSizeDropdownMenu(
        currentSize = pizza.size,
        onSizeChange = { pizza = pizza.withSize(it) }
    )
}

@Preview
@Composable
private fun PizzaBuilderScreenPreview() {
    CodaPizzaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PizzaBuilderScreen()
        }
    }
}