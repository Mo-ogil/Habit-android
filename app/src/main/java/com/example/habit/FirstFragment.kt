package com.example.habit

import android.graphics.pdf.content.PdfPageGotoLinkContent
import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.ActivityNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.androidgamesdk.gametextinput.Settings


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstFragment() {
    SingleChoiceSegmentedButton()
    HorizontalLineExample()
    InputChipExample(
        text = "Моя новая привычка",
        onDismiss = {
            var isVisible = false // Скрываем чипс
            // Дополнительные действия при удалении
            println("Привычка удалена")
        }
    )
    AnimatedFloatingActionButton()
    SimpleNavigationBar()
}

@Preview(showSystemUi = true)
@Composable
fun FirstFragmentPreview() {
    FirstFragment()
}




@Composable
fun SingleChoiceSegmentedButton(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Day", "Month", "Week")

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Базовое выравнивание

    ) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .align(Alignment.TopStart) // Начальная позиция
                .offset(
                    x = with(LocalDensity.current) {
                        (LocalConfiguration.current.screenWidthDp * 0.4f).toDp()
                    },
                    y = with(LocalDensity.current) {
                        (LocalConfiguration.current.screenHeightDp * 0.2f).toDp()
                    }
                )
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size
                    ),
                    onClick = { selectedIndex = index },
                    selected = index == selectedIndex,
                    label = { Text(label) }
                )
            }
        }
    }
}

@Composable
fun HorizontalLineExample() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Текст вверху
        Text(
            text = "Всего: 1",
            style = MaterialTheme.typography.bodySmall, // ← Мелкий текст,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(
                    x = with(LocalDensity.current) {
                        (LocalConfiguration.current.screenWidthDp * 0.1f).toDp()
                    },
                    y = with(LocalDensity.current) {
                        (LocalConfiguration.current.screenHeightDp * 0.4f).toDp()
                    }
                )
        )

        // Линия в конкретной позиции
        Divider(
            color = Color.Gray,
            thickness = 2.dp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y = with(LocalDensity.current) {
                    (LocalConfiguration.current.screenHeightDp * 0.45f).toDp()
                })
                .fillMaxWidth()
        )
    }
}


@Composable
fun Habit() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Текст вверху
        Text(
            text = "Моя новая привычка",
            style = MaterialTheme.typography.bodySmall, // ← Мелкий текст,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(
                    x = with(LocalDensity.current) {
                        (LocalConfiguration.current.screenWidthDp * 0.1f).toDp()
                    },
                    y = with(LocalDensity.current) {
                        (LocalConfiguration.current.screenHeightDp * 0.4f).toDp()
                    }
                )
        )


    }
}


@Composable
fun InputChipExample(
    text: String,
    onDismiss: () -> Unit,
) {
    var enabled by remember { mutableStateOf(true) }
    if (!enabled) return

    InputChip(
        onClick = {
            onDismiss()
            enabled = !enabled
        },
        label = { Text(text) },
        selected = enabled,
        trailingIcon = {
            Icon(
                Icons.Default.Close,
                contentDescription = "Localized description",
            )
        },
        modifier = Modifier
            .height(50.dp)
            .width(325.dp)
            .offset(
                x = with(LocalDensity.current) {
                    (LocalConfiguration.current.screenWidthDp * 0.225f).toDp()
                },
                y = with(LocalDensity.current) {
                    (LocalConfiguration.current.screenHeightDp * 0.57f).toDp()
                }
            ),
        shape = RoundedCornerShape(50)
    )
}

@Composable
fun AnimatedFloatingActionButton() {
    var isExpanded by remember { mutableStateOf(false) }

    LargeFloatingActionButton(
        onClick = {
            isExpanded = !isExpanded // Меняем состояние при клике
        },
        shape = CircleShape,
        modifier = Modifier
            .height(70.dp)
            .width(70.dp)
            .offset(
                x = with(LocalDensity.current) {
                    (LocalConfiguration.current.screenWidthDp * 2.1f).toDp()
                },
                y = with(LocalDensity.current) {
                    (LocalConfiguration.current.screenHeightDp * 2.1f).toDp()
                }
            )
    ) {
        // Меняем иконку в зависимости от состояния
        if (isExpanded) {
            Icon(
                Icons.Default.Close, // ← Крестик когда expanded
                contentDescription = "Закрыть"
            )
        } else {
            Icon(
                Icons.Default.Add, // ← Плюс когда collapsed
                contentDescription = "Добавить"
            )
        }
    }
}



@Composable
fun SimpleNavigationBar(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf("Главная", "Статистика", "Профиль")
    val routes = listOf("home", "stats", "profile")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Settings,
        Icons.Default.Person
    )

    NavigationBar(modifier = Modifier
        .offset(
            x = with(LocalDensity.current) {
                (LocalConfiguration.current.screenWidthDp * 0f).toDp()
            },
            y = with(LocalDensity.current) {
                (LocalConfiguration.current.screenHeightDp * 2.5f).toDp()
            }
        )) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    navController.navigate(routes[index])
                    selectedItem = index
                },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                modifier = Modifier
                    .offset(
                        x = with(LocalDensity.current) {
                            (LocalConfiguration.current.screenWidthDp * 0f).toDp()
                        },
                        y = with(LocalDensity.current) {
                            (LocalConfiguration.current.screenHeightDp * 0f).toDp()
                        }
                    )
            )
        }
    }
}