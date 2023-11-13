package com.draw.codapizza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.draw.codapizza.model.Topping
import com.draw.codapizza.model.ToppingPlacement
import com.draw.codapizza.ui.PizzaBuilderScreen
import com.draw.codapizza.ui.ToppingCell
import com.draw.codapizza.ui.theme.CodaPizzaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            CodaPizzaTheme {
                PizzaBuilderScreen()
            }
        }
    }
}