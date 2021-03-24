package com.example.favlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.favlistapp.ui.theme.FavListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FavListAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    hostComposable()
                }
            }
        }
    }
}

@Composable
fun hostComposable() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { ContactList(navController) }
        composable("contact/{userId}") { backStackEntry ->
            ContactDetails(navController, backStackEntry.arguments?.getString("userId")) }
    }
}

@Composable
fun ContactDetails(navController: NavController?, userId: String?) {
    TopAppBar {
        Text(text = "Contact $userId",
            textAlign = TextAlign.Center)
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        contentAlignment = Alignment.Center) {
        Button(onClick = {
            navController?.navigate("home") {
                launchSingleTop = true
            }
        }) {
            Text(text = "Update conversation timestamp for Contact $userId")
        }
    }
}


@Composable
fun ContactList(navController: NavController?) {
    ClickableText(modifier = Modifier.padding(vertical = 5.dp),
        style = TextStyle(fontSize = 36.sp),
        text = AnnotatedString(testList[0].name),
        onClick = { offset ->
            navController?.navigate("contact/${testList[0].id}")
        })
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    FavListAppTheme {
        ContactList(null)
    }
}

@Preview(showBackground = true)
@Composable
fun ContactPreview() {
    FavListAppTheme {
        ContactDetails(null, "TestContact")
    }
}