package es.jccarrillo.lazyverticalmaxgrid

import android.os.Parcelable
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Composable
fun <T : Parcelable> LazyVerticalMaxGrid(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    columns: Int = 2,
    adapter: LazyAnimatedColumnAdapter<T>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(2.dp),
    itemAddTransition: EnterTransition = fadeIn(),
    itemRemoveTransition: ExitTransition = fadeOut(),
    userScrollEnabled: Boolean = true,
    header: LazyListScope.() -> Unit = {},
    itemContent: @Composable (index: Int, item: T) -> Unit
) {
    val collection by adapter.items.collectAsState()

    LaunchedEffect(collection) {
        snapshotFlow {
            collection.entries.firstOrNull { it.isVisible.isIdle && !it.isVisible.targetState }
        }.collect {
            if (it != null) {
                adapter.removeInvisible()
            }
        }
    }

    LazyColumn(
        verticalArrangement = verticalArrangement,
        contentPadding = contentPadding,
        state = state,
        modifier = modifier,
        userScrollEnabled = userScrollEnabled,
    ) {

        header(this)

        collection.entries.chunked(columns).forEachIndexed { index, items ->
            Log.d("LazyVerticalMaxGrid", "$index and ${items.size}")
            item(key = items) {
                Row(
                    horizontalArrangement = horizontalArrangement,
                    modifier = Modifier.height(IntrinsicSize.Min),
                ) {
                    items.forEachIndexed { subIndex, item ->

                        Column(modifier = Modifier.weight(1.0f)) {

                            AnimatedItem(
                                item = item,
                                enterTransition = itemAddTransition,
                                exitTransition = itemRemoveTransition
                            ) { data ->
                                itemContent(index * columns + subIndex, data)
                            }
                        }

                    }

                    // Fill empty space in the row
                    if (items.size < columns) {
                        Spacer(modifier = Modifier.weight(1.0f * (columns - items.size)))
                    }
                }
            }
        }
    }


}

@Composable
private fun <T : Parcelable> AnimatedItem(
    item: ItemState<T>,
    enterTransition: EnterTransition,
    exitTransition: ExitTransition,
    content: @Composable (item: T) -> Unit
) {
    val itemValue by item.item
    AnimatedVisibility(
        visibleState = item.isVisible,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        content(itemValue)
    }
}

open class LazyAnimatedColumnAdapter<T : Parcelable>(
    initialItems: List<T> = emptyList(),
) {
    private val entries = LinkedList<ItemState<T>>().apply {
        addAll(initialItems.map {
            ItemState(initialItem = it)
        })
    }

    private val _items: MutableStateFlow<ItemCollection<T>> =
        MutableStateFlow(ItemCollection(entries))

    val items: StateFlow<ItemCollection<T>> = _items

    fun clear() {
        entries.clear()
        _items.tryEmit(ItemCollection(entries.toList()))
    }

    fun addItem(item: T) {
        entries.add(ItemState(initialItem = item))
        _items.tryEmit(ItemCollection(entries.toList()))
    }

    fun removeItem(index: Int) {
        val item = entries[index]
        item.isVisible.targetState = false
    }

    fun addAllUnique(list: List<T>) {
        var modded = false
        list.forEach { item ->
            val notInOldList = entries.firstOrNull { it.item.value == item } == null
            if (notInOldList) {
                entries.add(ItemState(initialItem = item))
                modded = true
            }
        }

        entries.forEach { item ->
            val notInNewList = list.firstOrNull { it == item.item.value } == null
            if (notInNewList)
                item.isVisible.targetState = false
        }

        if (modded)
            _items.tryEmit(ItemCollection(entries.toList()))
    }

    fun removeInvisible() {
        entries.removeAll(
            entries.filter {
                it.isVisible.isIdle && !it.isVisible.targetState
            }.toSet()
        )
        _items.tryEmit(ItemCollection(entries.toList()))
    }

    fun itemsCount(): Int =
        entries.size

}

@Immutable
data class ItemCollection<T : Parcelable>(
    val entries: List<ItemState<T>>,
    val id: String = UUID.randomUUID().toString()
)

@Stable
@Parcelize
class ItemState<T : Parcelable>(
    val initialItem: T,
    private val initialVisibility: Boolean? = null
) : Parcelable {
    @IgnoredOnParcel
    @Stable
    val item: MutableState<T> = mutableStateOf(initialItem)

    @IgnoredOnParcel
    @Stable
    val isVisible: MutableTransitionState<Boolean> =
        MutableTransitionState(initialVisibility ?: false).apply {
            if (initialVisibility == null)
                targetState = true
        }

    override fun toString(): String {
        return item.value.toString()
    }
}
