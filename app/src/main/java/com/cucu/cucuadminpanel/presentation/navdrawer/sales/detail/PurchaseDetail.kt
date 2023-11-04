package com.cucu.cucuadminpanel.presentation.navdrawer.sales.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cucu.cucuadminpanel.R
import com.cucu.cucuadminpanel.application.Constants
import com.cucu.cucuadminpanel.data.models.cart.CartProduct
import com.cucu.cucuadminpanel.data.models.purchase.Purchase
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseState
import com.cucu.cucuadminpanel.presentation.navdrawer.sales.viewmodel.SalesViewModel
import com.cucu.cucuadminpanel.presentation.products.add.view.TextFieldCommon
import com.cucu.cucuadminpanel.ui.theme.Purple40
import com.cucu.cucuadminpanel.ui.theme.Purple80
import com.cucu.cucuadminpanel.ui.theme.PurpleGrey80
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseDetail(
    purchaseRef: PurchaseReference?,
    mainNavController: NavHostController,
    viewModel: SalesViewModel = hiltViewModel()
) {
    purchaseRef?.let { viewModel.getSaleById(purchaseRef) }

    var showCancelDialog by remember { mutableStateOf(false) }
    var state by rememberSaveable { mutableStateOf(viewModel.sale.state) }

    Scaffold(
        topBar = {
            TopBarPurchase(mainNavController, state) {
                showCancelDialog = it
            }
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val (list, footer) = createRefs()

            DialogCancelPurchase(
                show = showCancelDialog,
                onDismiss = { showCancelDialog = !showCancelDialog },
                onContinue = { cancelReason ->
                    viewModel.cancelSale(purchaseRef, cancelReason)
                    state = PurchaseState.Cancelled().description
                    showCancelDialog = !showCancelDialog
                }
            )

            LazyColumn(
                modifier = Modifier
                    .constrainAs(list) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(0.dp, 0.dp, 0.dp, 140.dp)
            ) {
                viewModel.sale.products?.let { products ->
                    items(products, key = { it.product.id!! }) { purchaseItem ->
                        PurchaseDetailItem(purchaseItem)
                    }
                }
            }

            state?.let {
                PurchaseFooter(
                    it,
                    viewModel.sale,
                    modifier = Modifier.constrainAs(footer) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPurchase(
    mainNavController: NavHostController,
    state:String?,
    onShowDialog:(Boolean) -> Unit
) {
    Surface(shadowElevation = 3.dp){
        TopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                navigationIconContentColor = Purple80,
                titleContentColor = Purple80,
                scrolledContainerColor = Purple40,
            ),
            title = { Text(text = "Purchase")},
            navigationIcon = {
                IconButton(onClick = { mainNavController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                state?.let {
                    if (it == PurchaseState.PendingPayment().description){
                        IconButton(onClick = {
                            onShowDialog(true)
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = "cancel", tint = Purple80)
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun DialogCancelPurchase(
    show: Boolean,
    onDismiss: () -> Unit,
    onContinue: (String) -> Unit,
){
    var cancelReason by rememberSaveable { mutableStateOf("") }

    if (show){
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(colors = CardDefaults.cardColors(containerColor = Color.DarkGray)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "¿Estas seguro que quieres cancelar esta venta?",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        text = "Se le notificará al cliente la decision, detalla debajo el por que se ha cancelado su compra.")

                    TextFieldCommon(label = "Observacion", onValueChange = { cancelReason = it })

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PurpleGrey80,
                                contentColor = Purple40
                            ),
                            onClick = { onDismiss() }) {
                            Text(text = "Volver atras")
                        }
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Purple40,
                                contentColor = Color.White
                            ),
                            onClick = { onContinue(cancelReason) }) {
                            Text(text = "Cancelar venta")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PurchaseFooter(state: String, purchase: Purchase?, modifier: Modifier) {

    var showDialog by remember { mutableStateOf(false) }

    DialogPurchaseStates(
        state = state,
        show = showDialog
    ) { showDialog = !showDialog }

    Column(
        modifier
            .shadow(3.dp, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(Purple40, RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))) {

        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Estado: $state",
                textAlign = TextAlign.Center
            )
            InfoButton { showDialog = it }

        }
        Row(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                .background(Purple80, RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                .padding(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "${purchase?.getQuantity()} unidades", fontSize = 24.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Total: $${purchase?.getAmount()}", fontSize = 24.sp)
        }
    }
}

@Composable
fun PurchaseDetailItem(cart: CartProduct) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(4.dp)) {
        AsyncImage(
            modifier = Modifier
                .size(150.dp, 150.dp)
                .weight(1.5f)
                .clip(RoundedCornerShape(10.dp)),
            model = cart.product.img,
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
        Column(modifier = Modifier
            .weight(2f)
            .padding(4.dp)) {
            Text(text = "${cart.product.name}", Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = "Cantidad: ${cart.quantity}", Modifier.weight(1f))
            Text(text = "Precio unitario:\n$${cart.product.newPrice!!.roundToInt()}", Modifier.weight(1f))
        }
        Text(
            modifier = Modifier
                .weight(1.25f)
                .padding(4.dp),
            text = "Total:\n$${cart.product.newPrice!!.roundToInt() * cart.quantity!!.toInt()}",
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}

@Composable
fun InfoButton(onClick:(Boolean) -> Unit) {
    IconButton(onClick = { onClick(true) }) {
        Icon(imageVector = Icons.Outlined.Info, contentDescription = "")
    }
}

@Composable
fun DialogPurchaseStates(
    state:String,
    show: Boolean,
    onDismiss:() -> Unit,
){
    if (show){
        when (state){
            PurchaseState.PendingPayment().description -> {
                Dialog(onDismissRequest = { onDismiss() }) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                    ) {
                        Column(
                            Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "¿Como pagar?",
                                textAlign = TextAlign.Center,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(text = "1- Realiza la transferencia o deposito del total de la compra a la cuenta bancaria con los datos que aparecen a continuacion")
                            AsyncImage(model = R.drawable.cucu_logo, contentDescription = "")
                            Text(text = "2- Una vez realizado el pago, envía el comprabante del mismo al siguiente enlace de Whatsapp")
                            Text(text = "**Enlace de Whatsapp**")
                            Text(text = "3- ¡Listo! Podrás ver el avance de tu compra ingresando a esta misma pestaña, si tienes dudas puedes comunicarte al numero del paso 2")
                        }
                    }
                }
            }
            PurchaseState.PaymentPendingConfirmation().description,
            PurchaseState.PaymentConfirmed().description,
            PurchaseState.PreparingProducts().description,
            PurchaseState.ReadyForPickup().description,
            PurchaseState.Delivered().description,
            PurchaseState.Cancelled().description -> {
                Dialog(onDismissRequest = { onDismiss() }) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                    ) {
                        Column(
                            Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Estados de compra:",
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Constants.purchaseStates.forEach {
                                Text(
                                    modifier = Modifier.padding(top = 16.dp),
                                    text = it.title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(text = it.description)
                            }
                        }
                    }
                }

            }
        }
    }
}