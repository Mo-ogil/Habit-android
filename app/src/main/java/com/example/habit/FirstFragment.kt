package com.example.habit

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showSystemUi = true)
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
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(top = 10.dp)
        ) {
            TransformableSegmentedButton(scale, offset)
            TransformableHorizontalLine(scale, offset)
            TransformableInputChip(
                text = "Моя новая привычка",
                onDismiss = {},
                scale = scale,
                offset = offset,
            )
        }
    }
}

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
            .padding(vertical = 20.dp, horizontal = 58.dp)
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { selectedIndex = index },
                selected = index == selectedIndex,
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = Color(0xFFADD8E6),
                    activeContentColor = Color.Black,

                    inactiveContainerColor = Color.Transparent,
                    inactiveContentColor = Color.Black,

                    activeBorderColor = Color.Gray,
                    inactiveBorderColor = Color.Gray
                ),
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
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 0.dp)
        )

        Divider(
            color = Color.Gray,
            thickness = 2.dp,
            modifier = Modifier
                .padding(bottom = 35.dp)
                .height(2.dp)
                .fillMaxSize()
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
    Column (modifier = Modifier
        .padding(35.dp, 0.dp)){
        InputChip(
            onClick = {},
            label = { Text(text, style = MaterialTheme.typography.bodyMedium) },
            selected = enabled,
            colors = SelectableChipColors(
                Color.White, Color.Black,
                Color.Black, Color.Black,
                Color.White, Color.Black,
                Color.Black, Color.Black,
                Color.White, Color.Black,
                Color.Black, Color.Black,
                Color.Black
            ),
            trailingIcon = {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .width(17.dp)
                        .height(17.dp)
                        .padding()
                )
            },
            modifier = Modifier
                .height(50.dp)
                .width(325.dp)
                .border(0.5.dp, Color.Black, RoundedCornerShape(50))
                .padding(
                    horizontal = 0.dp,
                    vertical = 0.dp
                ),
            shape = RoundedCornerShape(50) // FIXME
        )


    }
}

@Composable
fun TransformableFloatingActionButton(scale: Float, offset: Offset) {
    var isExpanded by remember { mutableStateOf(false) }
    Column (modifier = Modifier
        .padding(start = 280.dp,
            bottom = 8.dp
        )
    ){
        LargeFloatingActionButton(
            onClick = {
                isExpanded = !isExpanded
            },
            shape = CircleShape,
            modifier = Modifier
                .size(70.dp)
                .padding(horizontal = 0.dp),
            containerColor = Color(0xFF709BFB)
        ) {
            if (isExpanded) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Закрыть",
                    tint = Color.White
                )
            } else {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Добавить",
                    tint = Color.White
                )
            }
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


