package com.company.pharmaflow.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.pharmaflow.R
import com.company.pharmaflow.ui.theme.PharmaFlowTheme
import com.company.pharmaflow.R.drawable as ApplicationDrawable

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignInCLick: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.padding(20.dp))
        Image(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = painterResource(id = ApplicationDrawable.login_image400px),
            contentDescription = null
        )
        Spacer(modifier = modifier.padding(8.dp))

        Text(
            modifier = modifier.semantics { invisibleToUser() },
            text = stringResource(R.string.terhubung_dengan_kami),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = modifier.padding(5.dp))
        Text(
            text = stringResource(R.string.selamat_datang_di_pharmaflow),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = modifier.padding(8.dp))
        Button(
            onClick = onSignInCLick,
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .width(280.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 5.dp,
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            ),
            content = {
                Text(
                    stringResource(R.string.masuk_dengan_google),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = modifier.padding(8.dp))
                Image(
                    painterResource(id =ApplicationDrawable.google_logo_24px),
                    contentDescription = null
                )
            }
        )
    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    PharmaFlowTheme {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
            LoginScreen(
                onSignInCLick = {},
            )
        }
    }
}