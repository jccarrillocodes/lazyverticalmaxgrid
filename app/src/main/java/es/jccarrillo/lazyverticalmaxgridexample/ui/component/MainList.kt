package es.jccarrillo.lazyverticalmaxgridexample.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.jccarrillo.lazyverticalmaxgridexample.ui.model.Model

@Composable
fun MainList(list: List<Model>, onRemoveClicked: (Model) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        userScrollEnabled = false,
    ) {
        items(list) { item ->
            MainItem(model = item, onRemoveClicked = { onRemoveClicked(item) })
        }
    }
}

@Preview(widthDp = 200, showBackground = true)
@Composable
fun MainListPreview() {
    val list = listOf(
        Model(
            id = "01",
            title = "This is a title",
            imageUrl = "",
            description = "Lorem ipsum bla bla bla"
        ),
        Model(
            id = "02",
            title = "This is another title",
            imageUrl = "",
            description = "Lorem ipsum bla bla bla with something to take into account"
        ),
        Model(
            id = "03",
            title = "This is another title",
            imageUrl = "",
            description = "Lorem ipsum bla bla bla with something to take into account"
        ),
        Model(
            id = "04",
            title = "This is a title",
            imageUrl = "",
            description = "Lorem ipsum bla bla bla"
        ),
    )
    MainList(list, {})
}