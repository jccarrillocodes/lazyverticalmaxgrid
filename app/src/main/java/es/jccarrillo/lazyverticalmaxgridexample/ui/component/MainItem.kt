package es.jccarrillo.lazyverticalmaxgridexample.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import es.jccarrillo.lazyverticalmaxgridexample.ui.model.Model

@Composable
fun MainItem(model: Model, onRemoveClicked: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(Color.Cyan)
            .fillMaxSize(),
    ) {
        Column {
            Text(text = model.title, fontWeight = FontWeight.Bold)
            Text(text = model.description)
        }

        Button(
            onClick = onRemoveClicked
        ) {
            Text(text = "Remove")
        }
    }
}