package br.senai.sp.jandira.mobile_aquarela.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.mobile_aquarela.R
import br.senai.sp.jandira.mobile_aquarela.service.RetrofitFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Feed(controleDeNavegacao: NavHostController, id: String?) {
    var searchText by remember { mutableStateOf("") }
    val feedService = RetrofitFactory().getFeedService()

    val coroutineScope = rememberCoroutineScope()
    var feedPreco by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = feedService.getFeedPosts(1).execute()
                if (response.isSuccessful) {
                    feedPreco = response.body()?.firstOrNull()?.preco
                    Log.i("Feed", "+++++++++++++++++++++++++++++++++++++++++++++++++++")
                    Log.i("Feed", "Preço do primeiro post: ${feedPreco}")
                } else {
                    println("Erro na requisição: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Erro ao buscar dados do feed: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE2E8EB))
    ) {
        Image(
            painter = painterResource(id = R.drawable.aquarelaazul),
            contentDescription = "Logo da Aquarela",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Pesquisar...", color = Color(0xFF3E7D8D)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 90.dp),
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.FilterList,
                        contentDescription = "Filtrar",
                        tint = Color(0xFF3E7D8D)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Pesquisar",
                        tint = Color(0xFF3E7D8D)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color(0xFF3E7D8D),
                containerColor = Color(0xFFB8CED4),
                disabledPlaceholderColor = Color(0xFF3E7D8D)
            )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(1f))

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (isLoading) {
                    Text("Carregando...", modifier = Modifier.align(Alignment.Center))
                } else if (feedPreco != null) {
                    Text("Preço: R$${feedPreco}", modifier = Modifier.align(Alignment.Center))
                } else {
                    Text("Erro ao carregar o preço.", modifier = Modifier.align(Alignment.Center))
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter),
            color = Color(0xFFF6F6F6)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 64.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home",
                        tint = Color(0xFF204149),
                        modifier = Modifier.size(36.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.History,
                        contentDescription = "Histórico",
                        tint = Color(0xFF204149),
                        modifier = Modifier.size(36.dp)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Chat,
                        contentDescription = "Chat",
                        tint = Color(0xFF204149),
                        modifier = Modifier.size(36.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Usuário",
                        tint = Color(0xFF204149),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-15).dp)
                .size(75.dp)
                .border(width = 4.dp, color = Color.White, shape = RoundedCornerShape(47.dp)),
            shape = RoundedCornerShape(47.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF204149)),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("+", fontSize = 38.sp, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedPreview() {
    Feed(controleDeNavegacao = NavHostController(LocalContext.current), id = "1")

}