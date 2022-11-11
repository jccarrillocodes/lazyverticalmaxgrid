package es.jccarrillo.lazyverticalmaxgridexample.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import es.jccarrillo.lazyverticalmaxgrid.LazyAnimatedColumnAdapter
import es.jccarrillo.lazyverticalmaxgridexample.ui.model.Model

class MainViewModel : ViewModel() {

    var list by mutableStateOf(emptyList<Model>())
        private set

    val adapter = LazyAnimatedColumnAdapter<Model>()

    init {
        reset()
    }

    fun reset() {
        list = listOf(
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
                description = "Lorem ipsum bla bla bla with something to take into"
            ),
            Model(
                id = "03",
                title = "This is another title",
                imageUrl = "",
                description = "Lorem ipsum bla bla bla with something to take into"
            ),
            Model(
                id = "04",
                title = "This is a title",
                imageUrl = "",
                description = "Lorem ipsum bla bla bla"
            ),
        )
        adapter.addAllUnique(list)
    }

    fun removeItem(index: Int) {
        list = list.filterIndexed { i, _ -> i != index }
        adapter.removeItem(index)
    }

    fun onRemoveClicked(item: Model) {
        val index = list.indexOf(item)
        if (index != -1)
            removeItem(index)
    }
}