package com.example.polycampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.polycampus.ui.auth.PolyCampusApp
import com.example.polycampus.ui.theme.PolyCampusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.enableEdgeToEdge(window)
        setContent { PolyCampusTheme { PolyCampusApp() } }
    }
}
