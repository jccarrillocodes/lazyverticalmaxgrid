package es.jccarrillo.lazyverticalmaxgridexample.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jccarrillo.lazyverticalmaxgrid.LazyVerticalMaxGrid
import es.jccarrillo.lazyverticalmaxgridexample.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "LazyVertialGrid",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 10.dp
            )
        )

        val list = viewModel.list
        MainList(
            list = list,
            onRemoveClicked = viewModel::onRemoveClicked
        )

        Text(
            text = "LazyVertialMaxGrid",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 10.dp
            )
        )
        LazyVerticalMaxGrid(
            adapter = viewModel.adapter,
            userScrollEnabled = false,
        ) { _, item ->
            MainItem(
                model = item,
                onRemoveClicked = { viewModel.onRemoveClicked(item) })
        }

        Button(
            onClick = viewModel::reset
        ) {
            Text(
                text = "Reset",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}