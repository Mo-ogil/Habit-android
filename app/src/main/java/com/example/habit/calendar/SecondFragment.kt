package com.example.habit.calendar

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun SecondFragment(
) {
    var scale by remember { mutableFloatStateOf(1.2f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 3f)
        offset = Offset(
            x = offset.x + scale * panChange.x,
            y = offset.y + scale * panChange.y,
        )
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            NavigationBar()
        },
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = transformableState)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(top = 10.dp)
        ) {
            DatePickerDocked(onDateSelected = {})
        }
    }
}

@Composable
fun NavigationBar() {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf("Главная", "Статистика", "Профиль")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Settings,
        Icons.Default.Person
    )

    NavigationBar(
        containerColor = Color(0xFFEEEEEE)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                colors = NavigationBarItemDefaults.colors(

                    selectedIconColor = Color.White,
                    selectedTextColor = Color.Black,

                    indicatorColor = Color(0xFFADD8E6),

                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    onDateSelected: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            val selectedDate = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            onDateSelected(selectedDate)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Card(
                modifier = Modifier
                    .height(610.dp)
                    .padding(33.dp, 37.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(Color(0xFFECE6F0))
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    colors = DatePickerDefaults.colors(
                        selectedDayContentColor = Color.White,
                        selectedDayContainerColor = Color(0xFF709BFB),
                        todayContentColor = Color(0xFF709BFB),
                        todayDateBorderColor = Color(0xFF709BFB)
                    )
                )
            }
        }
        item {
            Divider(
                color = Color.LightGray,
                thickness = 3.dp,
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .height(2.dp)
                    .fillMaxSize()
            )
        }
        item {
            CheckboxParentExample()
        }
    }
}

@Composable
fun CheckboxParentExample() {
    val childCheckedStates = remember { mutableStateListOf(false, false, false) }

    val parentState = when {
        childCheckedStates.all { it } -> ToggleableState.On
        childCheckedStates.none { it } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.padding(start = 25.dp, end = 25.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .drawBehind {
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 4f
                        )
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 4f
                        )
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                    modifier = Modifier.width(360.dp)
                ) {
                    Text(
                        "Выбрать всё",
                        modifier = Modifier
                            .padding(end = 30.dp, start = 30.dp)
                            .size(width = 180.dp, height = 20.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    TriStateCheckbox(
                        state = parentState as ToggleableState,
                        onClick = {
                            val newState = parentState != ToggleableState.On
                            childCheckedStates.forEachIndexed { index, _ ->
                                childCheckedStates[index] = newState
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF709BFB))
                    )
                }
            }

            childCheckedStates.forEachIndexed { index, checked ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .drawBehind {
                            drawLine(
                                color = Color.LightGray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 4f
                            )
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                        modifier = Modifier.width(360.dp)
                    ) {
                        Text(
                            "Привычка номер ${index + 1}",
                            modifier = Modifier
                                .padding(end = 30.dp, start = 30.dp)
                                .size(width = 180.dp, height = 20.dp),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Checkbox(
                            modifier = Modifier.padding(end = 0.dp),
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF709BFB)),
                            checked = checked,
                            onCheckedChange = { isChecked ->
                                childCheckedStates[index] = isChecked
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondFragmentPreview() {
    SecondFragment()
}
