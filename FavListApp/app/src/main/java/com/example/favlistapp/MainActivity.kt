package com.example.favlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    val context = LocalContext.current
    val contactModel = remember { ContactModel().apply { loadData(context) } }
    val contacts = contactModel.contacts
    val isLoading = contactModel.isLoading
    if (isLoading.value) {
        SpinnerComposable()
    }
    NavHost(navController, startDestination = "home") {
        composable("home") { ContactList(navController, contacts) }
        composable("contact/{userId}") { backStackEntry ->
            ContactDetails(navController, backStackEntry.arguments?.getString("userId"), contactModel) }
    }
}

@Composable
fun SpinnerComposable() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize() ){
        CircularProgressIndicator()
    }
}


@Composable
fun ContactDetails(navController: NavController?, userId: String?, contactModel: ContactModel) {
    val contacts = contactModel.contacts
    val context = LocalContext.current
    TopAppBar(
        title = {
            Text(text = contacts.value[userId?.toInt() ?: 0].name,
                textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Button(onClick = {
            userId?.toInt()?.let {
                contacts.value[it].timestamp = System.currentTimeMillis()
            }
            contactModel.updateData(context = context)
            navController?.navigate("home") {
                launchSingleTop = true
            }
        }) {
            Text(text = "Update conversation timestamp for ${contacts.value[userId?.toInt() ?: 0].name}")
        }

        Button(onClick = {
            userId?.toInt()?.let {
                contacts.value[it].isFav = !contacts.value[it].isFav
            }
            contactModel.updateData(context = context)
            navController?.navigate("home") {
                launchSingleTop = true
            }
        }) {
            Text(text = "Toggle fav ${contacts.value[userId?.toInt() ?: 0].name}")
        }
    }
}


@Composable
fun ContactList(navController: NavController?, contacts: MutableState<MutableList<Contact>>) {
    contacts.value = contacts.value.sortedWith(
        compareByDescending<Contact>{it.isFav}.thenByDescending{it.timestamp}
    ).toMutableList()
    LazyColumn(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()) {
        items(contacts.value.size) { index ->
            Surface(color = remember {
                if (contacts.value[index].isFav) {
                    Color.Yellow
                } else {
                    Color.Transparent
                }
            }) {
                ClickableText(modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(),
                    style = TextStyle(fontSize = 36.sp, color = MaterialTheme.colors.primary),
                    text = AnnotatedString(contacts.value[index].name),
                    onClick = { offset ->
                        navController?.navigate("contact/$index")
                    })
            }
            Divider(color = MaterialTheme.colors.primary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    FavListAppTheme {
        ContactList(null, mutableStateOf(testList.toMutableList()))
    }
}

@Preview(showBackground = true)
@Composable
fun ContactPreview() {
    val model = ContactModel().apply {
        contacts.value = testList.toMutableList()
    }
    FavListAppTheme {
        ContactDetails(null, "0", model)
    }
}