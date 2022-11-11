package es.jccarrillo.lazyverticalmaxgridexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jccarrillo.lazyverticalmaxgrid.LazyVerticalMaxGrid
import es.jccarrillo.lazyverticalmaxgridexample.ui.component.MainItem
import es.jccarrillo.lazyverticalmaxgridexample.ui.component.MainList
import es.jccarrillo.lazyverticalmaxgridexample.ui.component.MainScreen
import es.jccarrillo.lazyverticalmaxgridexample.ui.theme.LazyVerticalMaxGridExampleTheme
import es.jccarrillo.lazyverticalmaxgridexample.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyVerticalMaxGridExampleTheme {

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

