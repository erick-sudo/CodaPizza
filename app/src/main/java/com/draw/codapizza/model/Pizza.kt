package com.draw.codapizza.model

import androidx.annotation.StringRes
import com.draw.codapizza.R

// @Parcelize
data class Pizza(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    val size: PizzaSize = PizzaSize.Large
) {
    val price: Double
        get() = size.price + toppings.asSequence()
            .sumOf { (_, toppingPlacement) ->
                when(toppingPlacement) {
                    ToppingPlacement.Left, ToppingPlacement.Right -> 0.5
                    ToppingPlacement.All -> 1.0
                }
            }

    fun withTopping(
        topping: Topping,
        placement: ToppingPlacement?
    ) : Pizza {
        return copy(
            toppings = if (placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }

    fun withSize(pizzaSize: PizzaSize) : Pizza {
        return copy(
            size = pizzaSize
        )
    }
}

enum class PizzaSize(
    @StringRes val size: Int,
    val price: Double,
) {
    Small(R.string.pizza_small, 7.99),
    Medium(R.string.pizza_medium, 8.99),
    Large(R.string.pizza_large, 9.99),
    ExtraLarge(R.string.pizza_extra_large, 10.99)
}
