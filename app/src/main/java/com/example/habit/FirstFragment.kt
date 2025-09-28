package com.example.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstFragment() {
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
        floatingActionButton = {
            TransformableFloatingActionButton(scale, offset)
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            TransformableNavigationBar(scale, offset)
        },
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = transformableState)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TransformableSegmentedButton(scale, offset)
            TransformableHorizontalLine(scale, offset)
            TransformableInputChip(
                text = "Моя новая привычка",
                onDismiss = {},
                scale = scale,
                offset = offset
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FirstFragmentPreview() {
    FirstFragment()
}

@Composable
fun TransformableSegmentedButton(
    scale: Float,
    offset: Offset,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Day", "Month", "Week")

    SingleChoiceSegmentedButtonRow(
        modifier = modifier
            .offset(
                x = calculateXOffset(offset.x, scale, 0.4f),
                y = calculateYOffset(offset.y, scale, 0.2f)
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

@Composable
fun TransformableHorizontalLine(scale: Float, offset: Offset) {
    Column {
        Text(
            text = "Всего: 1",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier
                .offset(
                    x = calculateXOffset(offset.x, scale, 0.1f),
                    y = calculateYOffset(offset.y, scale, 0.4f)
                )
        )

        Divider(
            color = Color.Gray,
            thickness = 2.dp,
            modifier = Modifier
                .offset(
                    x = with(LocalDensity.current) { offset.x.toDp() },
                    y = calculateYOffset(offset.y, scale, 0.45f)
                )
                .fillMaxWidth()
        )
    }
}

@Composable
fun TransformableInputChip(
    text: String,
    onDismiss: () -> Unit,
    scale: Float,
    offset: Offset
) {
    var enabled by remember { mutableStateOf(true) }
    if (!enabled) return

    InputChip(
        onClick = {},
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
                x = calculateXOffset(offset.x, scale, 0.225f),
                y = calculateYOffset(offset.y, scale, 0.57f)
            ),
        shape = RoundedCornerShape(50)
    )
}

@Composable
fun TransformableFloatingActionButton(scale: Float, offset: Offset) {
    var isExpanded by remember { mutableStateOf(false) }

    LargeFloatingActionButton(
        onClick = {
            isExpanded = !isExpanded
        },
        shape = CircleShape,
        modifier = Modifier
            .size(70.dp)
            .offset(
                x = with(LocalDensity.current) { offset.x.toDp() },
                y = with(LocalDensity.current) { offset.y.toDp() }
            )
    ) {
        if (isExpanded) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Закрыть"
            )
        } else {
            Icon(
                Icons.Default.Add,
                contentDescription = "Добавить"
            )
        }
    }
}

@Composable
fun TransformableNavigationBar(scale: Float, offset: Offset) {
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf("Главная", "Статистика", "Профиль")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Settings,
        Icons.Default.Person
    )

    NavigationBar(
        modifier = Modifier
            .offset(
                x = with(LocalDensity.current) { offset.x.toDp() },
                y = with(LocalDensity.current) { offset.y.toDp() }
            )
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
                label = { Text(item) }
            )
        }
    }
}

@Composable
private fun calculateXOffset(offsetX: Float, scale: Float, multiplier: Float): androidx.compose.ui.unit.Dp {
    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    return with(density) {
        (offsetX + scale * (screenWidthDp * multiplier)).toDp()
    }
}

@Composable
private fun calculateYOffset(offsetY: Float, scale: Float, multiplier: Float): androidx.compose.ui.unit.Dp {
    val density = LocalDensity.current
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    return with(density) {
        (offsetY + scale * (screenHeightDp * multiplier)).toDp()
    }
}