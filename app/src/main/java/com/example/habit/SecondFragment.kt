package com.example.habit

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.helper.widget.Grid
import androidx.media3.common.util.Clock
import androidx.navigation.fragment.findNavController
import com.example.habit.databinding.FragmentSecondBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import java.time.DayOfWeek
import java.time.Instant
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*




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
            NavigationBar(scale, offset)
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
            DatePickerDocked( onDateSelected = {} )
        }
    }
}



@Composable
fun NavigationBar(scale: Float, offset: Offset) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf("Главная", "Статистика", "Профиль")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Settings,
        Icons.Default.Person
    )

    NavigationBar(
        modifier = Modifier
            .padding(0.dp,
                0.dp),
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
                    unselectedTextColor = Color.Black)
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

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()) {
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
                    colors = DatePickerDefaults.colors(Color(0xFFECE6F0),
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color.Black,
                        Color(0xFF709BFB),
                        Color(0xFF709BFB),
                        Color(0xFF709BFB),
                        Color(0xFF709BFB),
                        Color(0xFF709BFB),
                        Color(0xFF709BFB),
                        Color(0xFF709BFB),
                        Color(0xFF709BFB),
                        Color(0xFF709BFB),
                        Color.LightGray
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
    // Initialize states for the child checkboxes
    val childCheckedStates = remember { mutableStateListOf(false, false, false) }

    // Compute the parent state based on children's states
    val parentState = when {
        childCheckedStates.all { it } -> ToggleableState.On
        childCheckedStates.none { it } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
    {
        Column(modifier = Modifier.padding(start = 25.dp, end = 25.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .drawBehind {
// Верхняя линия
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 0.8f
                        )
// Нижняя линия
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 0.5f
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
                        modifier = Modifier.padding(end = 30.dp, start = 30.dp)
                            .size(width = 180.dp, height = 20.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    TriStateCheckbox(
                        state = parentState as ToggleableState,
                        onClick = {
                            // Determine new state based on current state
                            val newState = parentState != ToggleableState.On
                            childCheckedStates.forEachIndexed { index, _ ->
                                childCheckedStates[index] = newState
                            }
                        }
                    )
                }
            }

            // Child Checkboxes
            childCheckedStates.forEachIndexed { index, checked ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .drawBehind {
// Нижняя линия
                            drawLine(
                                color = Color.Black,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 0.5f
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
                            modifier = Modifier.padding(end = 30.dp, start = 30.dp)
                                .size(width = 180.dp, height = 20.dp),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { isChecked ->
                                // Update the individual child state
                                childCheckedStates[index] = isChecked
                            },
                            modifier = Modifier.padding(end = 0.dp)
                        )
                    }

                }
            }

            if (childCheckedStates.all { it }) {
                Text("All options selected")
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
