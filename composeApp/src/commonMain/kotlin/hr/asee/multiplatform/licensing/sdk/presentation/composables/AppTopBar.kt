package hr.asee.multiplatform.licensing.sdk.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hr.asee.multiplatform.licensing.sdk.core.navigation.OnBack
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    topBarTitle: String,
    drawerState: DrawerState? = null,
    onBack: OnBack = {},
    navigationIconType: NavigationIconType? = null
) {
    Surface(shadowElevation = 4.dp) {
        CenterAlignedTopAppBar(
            title = { Text(text = topBarTitle) },
            modifier = modifier.fillMaxWidth(),
            navigationIcon = {
                when (navigationIconType) {
                    NavigationIconType.DRAWER_ICON -> {
                        DrawerMenuNavigationIcon(drawerState = drawerState)
                    }

                    NavigationIconType.BACK_ICON -> {
                        DrawerBackNavigationIcon(onBack = onBack)
                    }

                    else -> Unit
                }
            }
        )
    }
}

@Composable
fun DrawerMenuNavigationIcon(drawerState: DrawerState?) {
    val coroutineScope = rememberCoroutineScope()
    IconButton(
        onClick = { coroutineScope.launch { drawerState?.open() } },
        content = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
    )
}

@Preview()
@Composable
fun DrawerMenuNavigationIconPreview() {
    MaterialTheme {
        DrawerMenuNavigationIcon(drawerState = null)
    }
}

@Composable
fun DrawerBackNavigationIcon(onBack: OnBack) {
    IconButton(
        onClick = onBack,
        content = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
    )
}

@Preview()
@Composable
fun DrawerBackNavigationIconPreview() {
    MaterialTheme {
        DrawerBackNavigationIcon(onBack = {})
    }
}

@Preview()
@Composable
fun AppTopBarPreview() {
    MaterialTheme {
        AppTopBar(
            topBarTitle = "Home",
            navigationIconType = NavigationIconType.DRAWER_ICON
        )
    }
}

enum class NavigationIconType {
    DRAWER_ICON,
    BACK_ICON
}
