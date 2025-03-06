package com.naveenapps.expensemanager.feature.onboarding.into

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.repository.ShareRepository
import com.naveenapps.expensemanager.feature.onboarding.R
import androidx.navigation.NavHostController
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerScreens
import com.naveenapps.expensemanager.feature.onboarding.AuthViewModel



@Composable
fun IntroScreen(
    shareRepository: ShareRepository?,
    viewModel: IntroViewModel = hiltViewModel()
) {
    ScaffoldContent(viewModel::navigate, shareRepository)
}

@Composable
private fun ScaffoldContent(
    navigate: () -> Unit,
    //navController: NavHostController,
    shareRepository: ShareRepository?
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
    val viewModel: IntroViewModel = hiltViewModel()
    val context = LocalContext.current

   // LaunchedEffect(authState.value) {
     //   when(authState.value){
            //is AuthState.Authenticated -> navController.navigate("home")
            //is AuthState.Error -> Toast.makeText(context,
               // (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
          //  else -> Unit
      //  }
  //  }

    Scaffold {

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

                Text(text = "Login", fontSize = 32.sp)

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

                /* Button(onClick = {
                    //authViewModel.login(email,password)
                   // AppComposeNavigator.navigate(AppComposeNavigator.Home)
                    navigate.invoke()
                },
                    //enabled = authState.value != AuthState.Loading
                ) */
                Button(onClick = {
                    viewModel.loginUser(email, password)
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    navigate.invoke()

                })

                {
                    Text(text = "Login")
                }


                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = {
                   // navController.navigate(ExpenseManagerScreens.Onboarding)
                    navigate.invoke()
                    //AppComposeNavigator.navigate(ExpenseManagerScreens.Signup)
                }) {
                    Text(text = "Don't have an account, Signup")
                }

            }



            /*Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f)
                ) {



                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.expense_1))

                    /*LottieAnimation(
                        composition = composition,
                        reverseOnRepeat = true,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )*/
                }
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.welcome_message_title),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.welcome_message_description),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = {
                    navigate.invoke()
                },
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = stringResource(id = R.string.get_started).uppercase())
            }

            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                    .fillMaxWidth(),
                text = buildAnnotatedString {
                    val text = stringResource(id = R.string.privacy_text)
                    append(text)
                    addLink(
                        url = LinkAnnotation.Url(
                            url = "",
                            linkInteractionListener = {
                                shareRepository?.openPrivacy()
                            },
                            styles = TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.secondary))
                        ),
                        start = text.indexOf("privacy policy"),
                        end = text.length - 1,
                    )
                },
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            ) */
        }
    }
}


@AppPreviewsLightAndDarkMode
@Composable
fun IntroScreenPreview() {
    ExpenseManagerTheme {
        ScaffoldContent({}, null)
    }
}