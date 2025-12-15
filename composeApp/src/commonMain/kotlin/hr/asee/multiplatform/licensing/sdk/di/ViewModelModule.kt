package hr.asee.multiplatform.licensing.sdk.di

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import hr.asee.multiplatform.licensing.sdk.presentation.screens.DefaultScreenModel

import org.koin.compose.getKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

/**
 * Retrieves a Voyager [ScreenModel] instance from Koin and remembers it
 * for the lifetime of the current Voyager [Screen].
 *
 * This function:
 *  - Integrates Voyager's `rememberScreenModel` with Koin DI
 *  - Ensures the ScreenModel survives recomposition
 *  - Disposes the ScreenModel automatically when the screen is removed
 *  - Supports constructor parameters and qualifiers
 *
 * If the Koin module defines:
 *  - `factory { ... }` → a new ScreenModel instance is created per Screen
 *  - `single { ... }` → the same ScreenModel instance is shared across Screens
 */

@Composable
public inline fun <reified T : ScreenModel> Screen.getScreenModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T {
    val koin = getKoin()
    return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
}


val viewModel = module {
    factory { DefaultScreenModel() }
}