package com.desafiodevspace.devquiz.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.desafiodevspace.devquiz.R
import com.desafiodevspace.devquiz.ui.theme.DevQuizTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavController,
    score: Int,
    totalQuestions: Int,
    userName: String = "Dev"
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center

                ) {
                    Text("Resultados",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold))
                }
                        },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
    ) { padding ->

        val animation: @Composable () -> Unit
        val message: String

        when {
            score == totalQuestions -> {
                animation = { congratulationsAnimation() }
                message = "Incrível, $userName! 🎉 Você acertou todas as questões! Você é um verdadeiro dev campeão! 🏆"
            }
            score >= 7 -> {
                animation = { keepStudyingAnimation() }
                message = "Muito bom, $userName! 💪 Você mandou bem, mas ainda pode melhorar um pouco!"
            }
            else -> {
                animation = { doBetterAnimation() }
                message = "Não desanime, $userName! 🚀 Continue vendo as aulas e praticando!"
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            animation()

            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Você completou o quiz com uma pontuação de $score/$totalQuestions.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = { navController.navigate("quiz") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Reiniciar Quiz", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                shape = RoundedCornerShape(8.dp),
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Voltar ao Início", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun congratulationsAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.champion))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun keepStudyingAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.keepstudying))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun doBetterAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.learning))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResultScreenPreview() {
    DevQuizTheme {
        val navController = rememberNavController()
        ResultScreen(navController, score = 5, totalQuestions = 10)
    }
}
