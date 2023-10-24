package com.cucu.cucuadminpanel.presentation.products.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cucu.cucuadminpanel.R
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.items.ItemCategory
import com.cucu.cucuadminpanel.presentation.products.detail.view.FabIcon
import com.cucu.cucuadminpanel.presentation.products.detail.view.TopBarNavigateBack
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navController: NavHostController,
    viewModel:AddViewModel = hiltViewModel()
) {
    var image by rememberSaveable { mutableStateOf<Uri?>(null) }
    var stock by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var newPrice by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var code by rememberSaveable { mutableStateOf("") }

    var getImage by remember { mutableStateOf(false) }

    if (getImage){
        PickImageFromGallery {
            image = it
            getImage = false
        }
    }

    Scaffold(
        topBar = { TopBarNavigateBack(navController) },
        floatingActionButton = {
            FabIcon(
                icon = Icons.Rounded.Check,
                onClick = {
                    val newProduct = Product(
                        name= name,
                        stock = stock.toInt(),
                        description = description,
                        newPrice = newPrice.toDouble(),
                        code = code.toLong(),
                        category = ItemCategory(category = category)
                    )
                    image?.let {
                        viewModel.addProduct(newProduct, it)
                    }
                }
            )
        }
    ) {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                TextFieldCommon(label = "Nombre", onValueChange = { name = it })

                AsyncImage(
                    model = image ?: R.drawable.cucu_logo,
                    contentScale = if (image == null) ContentScale.Fit else ContentScale.Crop,
                    contentDescription = null,
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clickable { getImage = true }
                )

                TextFieldCommon(
                    label = "Precio",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { newPrice = it}
                )

                TextFieldCommon(
                    label = "Stock",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { stock = it}
                )

                TextFieldCommon(
                    label = "Codigo",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { code = it}
                )

                SpinnerCountries(viewModel.getCountries(), "Categoria"){ category = it }

                TextFieldCommon(label = "Descripcion", onValueChange = { description = it })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerCountries(
    list: List<String>,
    label: String,
    value: String? = "",
    onSelectedCategory: (String) -> Unit
) {
    var selectedText by remember { mutableStateOf(value ?: "") }
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            label = { Text(text = label) },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = true })
        DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            list.forEach {
                DropdownMenuItem(text = { Text(text = it) }, onClick = {
                    isExpanded = false
                    selectedText = it
                    onSelectedCategory(selectedText)
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCommon(
    modifier: Modifier = Modifier,
    value: String? = null,
    label:String,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange:(String)->Unit
) {
    var textValue by rememberSaveable { mutableStateOf(value ?: "") }

    TextField(
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = label) },
        singleLine = singleLine,
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(textValue)
        },
        colors = colors,
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun PickImageFromGallery(onImageSelected:(Uri?) -> Unit) {
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        onImageSelected(uri)
    }

    LaunchedEffect(key1 = "", block = { scope.launch { launcher.launch("image/*") } })
}
