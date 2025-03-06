

package com.naveenapps.expensemanager.feature.onboarding

import android.util.Patterns
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.components.AppTopNavigationBar
import com.naveenapps.expensemanager.core.designsystem.ui.components.ClickableTextField
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.model.AccountType
import com.naveenapps.expensemanager.core.model.AccountUiModel
import com.naveenapps.expensemanager.core.model.Amount
import com.naveenapps.expensemanager.core.model.Currency
import com.naveenapps.expensemanager.core.model.StoredIcon
import com.naveenapps.expensemanager.feature.account.list.AccountItem
import com.naveenapps.expensemanager.feature.country.CountryCurrencySelectionDialog
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.repository.ShareRepository
import com.naveenapps.expensemanager.feature.onboarding.R
import com.naveenapps.expensemanager.feature.onboarding.OnboardingAction
import com.naveenapps.expensemanager.feature.onboarding.OnboardingState
import com.naveenapps.expensemanager.feature.onboarding.OnboardingViewModel
import com.naveenapps.expensemanager.feature.onboarding.UserRepository
import com.naveenapps.expensemanager.feature.onboarding.AuthViewModel
import com.naveenapps.expensemanager.feature.onboarding.into.IntroScreen
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerScreens
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    OnboardingContentView(
        state = state,
        onAction = viewModel::processAction
    )
}

@Composable
private fun OnboardingContentView(
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit,
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    //val authState = authViewModel.authState.observeAsState()
    val viewModel: OnboardingViewModel = hiltViewModel()
    val context = LocalContext.current

    //LaunchedEffect(authState.value) {
    // when (authState.value) {
    // is AuthState.Authenticated -> navController.navigate("home")
    //  is AuthState.Error -> Toast.makeText(
    //    context,
    //  (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
    //  ).show()

    //   else -> Unit
    //}
    //   }

    /*if (state.showCurrencySelection) {
        CountryCurrencySelectionDialog { country ->
            onAction.invoke(OnboardingAction.SelectCurrency(country?.currency))
        }
    } */

     Scaffold(
         /* topBar = {
            AppTopNavigationBar(
                title = "",
                navigationIcon = Icons.Default.Close,
                navigationBackClick = {
                    onAction.invoke(OnboardingAction.Next)
                }
            )
        } */
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f)
                ) {



                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.expense_1))

                    LottieAnimation(
                        composition = composition,
                        reverseOnRepeat = true,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                }


                Text(text = "Signup", fontSize = 32.sp)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                            "Invalid email address"
                        } else null
                    },
                    label = {
                        Text(text = "Email")
                    },
                    isError = emailError != null

                )
                emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = if (it.length < 6) {
                            "Password must be at least 6 characters"
                        } else null
                    },
                    label = {
                        Text(text = "Password")
                    },
                    isError = passwordError != null

                )
                passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                Spacer(modifier = Modifier.height(16.dp))

               /* Button(
                    modifier = Modifier
                        .padding(16.dp),
                        //.fillMaxWidth(),
                    onClick = {
                        onAction.invoke(OnboardingAction.Next)
                    },

               // Button(
                   // onClick = {
                      //  authViewModel.signup(email, password)
                        //navigate.invoke()
                //    }, //enabled = authState.value != AuthState.Loading
                ) */

                Button(onClick = {
                    viewModel.registerUser(email, password)
                    Toast.makeText(context, "User Registered", Toast.LENGTH_SHORT).show()
                    //navigate.invoke()
                    onAction.invoke(OnboardingAction.Next)
                    //navController.navigate(ExpenseManagerScreens.IntroScreen)
                })
                {
                   Text(text = "Create account")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = {
                    //navigate.invoke()
                    onAction.invoke(OnboardingAction.Next)
                  //  navController.navigate("login")
                   // navController.navigate(ExpenseManagerScreens.IntroScreen)
                }) {
                    Text(text = "Already have an account, Login")
                }

            }



            /*Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )  {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.setup),
                    style = MaterialTheme.typography.titleLarge,
                )

                ClickableTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                    label = R.string.select_main_currency,
                    value = state.currency.name.ifBlank { stringResource(id = R.string.currency) },
                    onClick = { onAction.invoke(OnboardingAction.ShowCurrencySelection) },
                    trailingIcon = Icons.AutoMirrored.Filled.ArrowRight
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(
                        text = stringResource(id = com.naveenapps.expensemanager.feature.account.R.string.accounts),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                state.accounts.forEach { account ->
                    AccountItem(
                        name = account.name,
                        icon = account.storedIcon.name,
                        iconBackgroundColor = account.storedIcon.backgroundColor,
                        amount = account.amount.amountString,
                        amountTextColor = account.amountTextColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onAction.invoke(OnboardingAction.AccountCreate(account))
                            }
                            .padding(16.dp),
                    )
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        onAction.invoke(OnboardingAction.AccountCreate(null))
                    }
                ) {
                    Text(text = stringResource(id = R.string.create_new).uppercase())
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    onClick = {
                        onAction.invoke(OnboardingAction.Next)
                    },
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(text = stringResource(id = R.string.proceed).uppercase())
                }
            } */
        }
    }
}

@Composable
private fun CurrencyItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
) {
    Row(modifier = modifier) {
        if (icon != null) {
            Icon(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                painter = painterResource(id = icon),
                contentDescription = null,
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = "$title $description",
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 16.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowRight,
            contentDescription = null,
        )
    }
}

@AppPreviewsLightAndDarkMode
@Composable
fun OnboardingScreenPreview() {
    ExpenseManagerTheme {
        OnboardingContentView(
            state = OnboardingState(
                currency = Currency("1", "1"),
                accounts = listOf(
                    AccountUiModel(
                        id = "1",
                        name = "Shopping",
                        type = AccountType.REGULAR,
                        storedIcon = StoredIcon(
                            name = "account_balance",
                            backgroundColor = "#000000",
                        ),
                        amountTextColor = com.naveenapps.expensemanager.core.common.R.color.green_500,
                        amount = Amount(0.0, "$ 0.00"),
                    ),
                ),
                showCurrencySelection = false
            ),
            onAction = {}
        )
    }
}
