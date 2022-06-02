// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.PermContactCalendar
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.PermContactCalendar
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.tkuenneth.nativeparameterstoreaccess.MacOSDefaults.getDefaultsEntry
import com.github.tkuenneth.nativeparameterstoreaccess.NativeParameterStoreAccess.IS_MACOS
import com.github.tkuenneth.nativeparameterstoreaccess.NativeParameterStoreAccess.IS_WINDOWS
import com.github.tkuenneth.nativeparameterstoreaccess.WindowsRegistry.getWindowsRegistryEntry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import pages.FoodPage
import pages.SubsNextPage
import pages.SubsPage
import kotlin.properties.Delegates.observable


@Composable
@Preview
fun App() {
    var foodOpen by remember { mutableStateOf(true) }
    var subsOpen by remember { mutableStateOf(false) }
    var subsNextOpen by remember { mutableStateOf(false) }
    var colors by remember { mutableStateOf(colors()) }
    onIsInDarkModeChanged = { _, _ ->
        colors = colors()
    }

    CustomTheme(colors = colors, content = {

            Row (modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)){
                CompositionLocalProvider(
                    LocalRippleTheme provides ClearRippleTheme
                ){
                    NavigationRail() {
                        NavigationRailItem(selected = foodOpen,
                            onClick = {
                                foodOpen = true
                                subsOpen = false
                                subsNextOpen = false
                            }, icon = {
                                Crossfade(foodOpen){
                                    if(it){
                                        Icon(Icons.Filled.Fastfood, "Home")
                                    }else{
                                        Icon(Icons.Outlined.Fastfood, "Home")
                                    }
                                }
                            })
                        NavigationRailItem(selected = subsOpen, onClick = {
                            foodOpen = false
                            subsOpen = true
                            subsNextOpen = false
                        }, icon = {

                            Crossfade(subsOpen){
                                if(it){
                                    Icon(Icons.Default.CalendarToday, "Home")
                                }else{
                                    Icon(Icons.Outlined.CalendarToday, "Home")
                                }
                            }
                        })
                        NavigationRailItem(selected = subsNextOpen, onClick = {
                            foodOpen = false
                            subsOpen = false
                            subsNextOpen = true
                        }, icon = {
                            Crossfade(subsNextOpen){
                                if(it){
                                    Icon(Icons.Default.PermContactCalendar, "Home")
                                }else{
                                    Icon(Icons.Outlined.PermContactCalendar, "Home")
                                }
                            }
                        })
                    }
                }

                Box{
                    Column {
                        AnimatedVisibility(foodOpen, enter = EnterTransition.None, exit = fadeOut()) {
                            FoodPage()
                        }
                    }
                    Column {
                        AnimatedVisibility(subsOpen,enter = EnterTransition.None,exit = fadeOut()) {
                            SubsPage()
                        }
                    }
                    Column {
                        AnimatedVisibility(subsNextOpen,enter = EnterTransition.None,  exit = fadeOut()) {
                            SubsNextPage()
                        }
                    }

                }

            }

        })

    }

private var isInDarkMode by observable(false) { _, oldValue, newValue ->
    onIsInDarkModeChanged?.let { it(oldValue, newValue) }
}
private var onIsInDarkModeChanged: ((Boolean, Boolean) -> Unit)? = null
fun main() = application {
    GlobalScope.launch {
        while (isActive) {
            val newMode = isSystemInDarkThemeCustom()
            if (isInDarkMode != newMode) {
                isInDarkMode = newMode
            }
            delay(1000)
        }
    }
    Window(onCloseRequest = ::exitApplication, title = "SchulApp", resizable = false) {

        App()
    }
}
object ClearRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = Color.Transparent

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        draggedAlpha = 0.0f,
        focusedAlpha = 0.0f,
        hoveredAlpha = 0.0f,
        pressedAlpha = 0.1f,
    )

}
@Composable
fun CustomTheme(content: @Composable () -> Unit,colors:Colors) {



        MaterialTheme(
            colors = colors,
            content = content
        )




}
fun isSystemInDarkThemeCustom(): Boolean {
    return when {
        IS_WINDOWS -> {
            val result = getWindowsRegistryEntry(
                "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
                "AppsUseLightTheme")
            result == 0x0
        }
        IS_MACOS -> {
            val result = getDefaultsEntry("AppleInterfaceStyle")
            result == "Dark"
        }
        else -> false
    }
}
private fun colors(): Colors = if (isInDarkMode) {
    darkColors( primary = Color(59,130,247), background = Color(30,30,30), secondaryVariant = Color(62,62,62))
} else {
    lightColors(primary = Color(59,130,247), background = Color.White, secondaryVariant = Color(222,222,222))
}