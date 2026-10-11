package com.example.polycampus.ui.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlin.time.Duration.Companion.milliseconds
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PolyCampusApp() {
    var showSplash by rememberSaveable { mutableStateOf(value = true) }
    LaunchedEffect(Unit) { delay(1600.milliseconds); showSplash = false }
    AnimatedContent(
        targetState = showSplash,
        transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(250)) },
        label = "app screen",
    ) { splash -> if (splash) SplashScreen() else LoginScreen() }
}

@Composable
private fun SplashScreen() {
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Column(
            Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        ) {
            Box(
                Modifier.size(96.dp).background(Color.White.copy(alpha = .14f), CircleShape),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.Outlined.School, null, Modifier.size(50.dp), tint = Color.White) }
            Spacer(Modifier.height(24.dp))
            Text("PolyCampus", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(Modifier.height(8.dp))
            Text("Unified Campus Management Platform", style = MaterialTheme.typography.bodyLarge, color = Color.White.copy(alpha = .84f), textAlign = TextAlign.Center)
            Spacer(Modifier.height(72.dp))
            Text("Government Polytechnic Murtizapur", style = MaterialTheme.typography.titleSmall, color = Color.White, textAlign = TextAlign.Center)
            Text("MSBTE Code: 1152", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = .76f))
        }
    }
}

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit = { _, _ -> }) {
    var loginId by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(value = false) }
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
        Column(
            Modifier.widthIn(max = 520.dp).fillMaxWidth().statusBarsPadding()
                .verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InstitutionalHeader()
            Spacer(Modifier.height(26.dp))
            LoginCard(loginId, password, passwordVisible, { loginId = it }, { password = it }, { passwordVisible = !passwordVisible }) { onLogin(loginId.trim(), password) }
            Spacer(Modifier.height(20.dp))
            AssistanceCard()
            Spacer(Modifier.height(36.dp))
            LoginFooter()
        }
    }
}

@Composable
private fun InstitutionalHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(68.dp), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            ) {
                Icon(Icons.Outlined.AccountBalance, "Government Polytechnic emblem", Modifier.padding(18.dp), tint = MaterialTheme.colorScheme.primary)
            }
            Text("MSBTE", Modifier.align(Alignment.BottomEnd).background(MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp)).padding(horizontal = 7.dp, vertical = 3.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(Modifier.height(14.dp))
        Text("Government Polytechnic Murtizapur", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Text("शासकीय तंत्रनिकेतन, मुर्तिजापूर (MSBTE Code: 1152)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
        Spacer(Modifier.height(18.dp))
        Row(Modifier.clip(RoundedCornerShape(14.dp)).background(MaterialTheme.colorScheme.surfaceContainerLow).padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.School, null, Modifier.background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(9.dp)).padding(6.dp).size(24.dp), tint = Color.White)
            Spacer(Modifier.size(10.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("PolyCampus", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.size(6.dp))
                    Text("ERP", Modifier.background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(4.dp)).padding(horizontal = 5.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                }
                Text("Unified Campus Management Platform", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun LoginCard(loginId: String, password: String, passwordVisible: Boolean, onLoginIdChange: (String) -> Unit, onPasswordChange: (String) -> Unit, onVisibilityChange: () -> Unit, onLogin: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.VerifiedUser, null, tint = MaterialTheme.colorScheme.secondary); Spacer(Modifier.size(12.dp)); Text("For Students, Faculty & College Administration", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            Spacer(Modifier.height(16.dp)); HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainerHighest); Spacer(Modifier.height(24.dp))
            LoginField("Enrollment number or staff ID", loginId, onLoginIdChange, "e.g., 2400123 or T-0045", Icons.Outlined.Badge, KeyboardType.Text, ImeAction.Next)
            FieldHint(Icons.Outlined.Info, "Students enter MSBTE 10-digit enrollment number")
            Spacer(Modifier.height(22.dp))
            LoginField("Password", password, onPasswordChange, "Enter your password", Icons.Outlined.Lock, KeyboardType.Password, ImeAction.Done, passwordVisible, onVisibilityChange)
            FieldHint(Icons.Outlined.Key, "First-time users use temporary password issued by office")
            Spacer(Modifier.height(28.dp))
            Button(onLogin, Modifier.fillMaxWidth().height(52.dp), enabled = loginId.isNotBlank() && password.isNotBlank(), shape = RoundedCornerShape(28.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) { Text("LOG IN", fontWeight = FontWeight.Bold); Spacer(Modifier.size(12.dp)); Icon(Icons.AutoMirrored.Outlined.ArrowForward, null) }
        }
    }
}

@Composable
private fun LoginField(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String, icon: ImageVector, keyboardType: KeyboardType, imeAction: ImeAction, passwordVisible: Boolean = true, onVisibilityChange: (() -> Unit)? = null) {
    Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(Modifier.height(6.dp))
    OutlinedTextField(
        value, onValueChange, Modifier.fillMaxWidth(), placeholder = { Text(placeholder) }, leadingIcon = { Icon(icon, null) },
        trailingIcon = onVisibilityChange?.let { { Icon(if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility, if (passwordVisible) "Hide password" else "Show password", Modifier.clip(CircleShape).clickable(onClick = onVisibilityChange).padding(10.dp)) } },
        singleLine = true, visualTransformation = if ((onVisibilityChange == null) || passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction), shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow, focusedBorderColor = MaterialTheme.colorScheme.primaryContainer, unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant, focusedLeadingIconColor = MaterialTheme.colorScheme.primary, unfocusedLeadingIconColor = MaterialTheme.colorScheme.outline),
    )
}

@Composable
private fun FieldHint(icon: ImageVector, text: String) { Row(Modifier.padding(top = 7.dp), verticalAlignment = Alignment.CenterVertically) { Icon(icon, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.outline); Spacer(Modifier.size(5.dp)); Text(text, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant) } }

@Composable
private fun AssistanceCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.Top) { Icon(Icons.Outlined.SupportAgent, null, Modifier.background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp)).padding(7.dp).size(24.dp), tint = MaterialTheme.colorScheme.primary); Spacer(Modifier.size(12.dp)); Column { Text("Login Assistance & Recovery", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold); Spacer(Modifier.height(4.dp)); Text("Forgot your password? Or first time logging in? Please contact the college office / Students Section.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) } }
            Spacer(Modifier.height(18.dp)); HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainerHighest); Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Schedule, null, Modifier.size(18.dp), tint = MaterialTheme.colorScheme.primary); Spacer(Modifier.size(7.dp)); Text("Office: Mon–Sat 10:00 AM – 5:00 PM", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            Spacer(Modifier.height(12.dp)); Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.Call, null, Modifier.size(18.dp), tint = MaterialTheme.colorScheme.secondary); Spacer(Modifier.size(7.dp)); Text("Admin Wing (Room 102)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Medium) }
        }
    }
}

@Composable
private fun LoginFooter() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainerHighest); Spacer(Modifier.height(22.dp))
        Row(Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer).padding(horizontal = 12.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Outlined.WifiOff, null, Modifier.size(16.dp), tint = MaterialTheme.colorScheme.secondary); Spacer(Modifier.size(6.dp)); Text("Supports offline attendance & caching", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary) }
        Spacer(Modifier.height(18.dp)); Text("v1.0.0 (Release 1.0 Core) · Build 2026.10", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant); Spacer(Modifier.height(5.dp)); Text("Directorate of Technical Education, Maharashtra State", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun LoginPreview() { LoginScreen() }
