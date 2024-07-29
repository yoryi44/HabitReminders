package com.example.home_presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.home_presentation.home.components.HomeAskPermission
import com.example.home_presentation.home.components.HomeDateSelector
import com.example.home_presentation.home.components.HomeHabit
import com.example.home_presentation.home.components.HomeQuote
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.home_presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNewHabit: () -> Unit,
    onSettings: () -> Unit,
    onEditHabit: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onSettings() }) {
                        Icon(Icons.Default.Settings, contentDescription = "settings")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNewHabit() },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add a new habit",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    ) { padding ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            HomeAskPermission(permission = Manifest.permission.POST_NOTIFICATIONS)
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding(), bottom = 16.dp)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                HomeQuote(
                    modifier = Modifier.padding(end = 20.dp),
                    quote = "We first make our habits, and then our habits make us.",
                    author = "anonimus",
                    image = R.drawable.onboarding1
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Habits".uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(Modifier.width(16.dp))
                    HomeDateSelector(
                        selectedDate = state.selectDate,
                        mainDateTime = state.currentDate,
                        onDateClick = {
                            viewModel.onEvent(HomeEvent.changeDate(it))
                        }
                    )
                }
            }
            items(state.habits)
            {
                HomeHabit(
                    habit = it,
                    selectedDate = state.selectDate.toLocalDate(),
                    onCheckedChange = { viewModel.onEvent(HomeEvent.completeHabit(it)) },
                    onHabitClick = { onEditHabit(it.id) })
            }
        }
    }
}