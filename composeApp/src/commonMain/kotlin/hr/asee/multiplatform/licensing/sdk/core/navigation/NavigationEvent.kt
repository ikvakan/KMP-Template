package hr.asee.multiplatform.licensing.sdk.core.navigation

typealias OnBack = () -> Unit

sealed interface NavigationEvent {
    data class Navigate(val screen: Any) : NavigationEvent
    object Back : NavigationEvent
}