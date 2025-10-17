package com.desafiodevspace.devquiz.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.desafiodevspace.devquiz.data.Question
import com.desafiodevspace.devquiz.data.ShuffledOption
import com.desafiodevspace.devquiz.ui.theme.DevQuizTheme
import kotlin.random.Random


val androidQuestions = listOf(
    Question(
        "Qual é o principal componente usado para construir a interface de usuário no Android moderno (a partir do 2020)?",
        listOf("Activity", "Fragment", "View", "Composable"), 3
    ),
    Question("Qual arquivo é obrigatório e contém informações essenciais sobre o aplicativo (permissões, componentes, etc.)?",
        listOf("build.gradle", "AndroidManifest.xml", "MainActivity.kt", "strings.xml"), 1),
    Question("Qual classe é a mais recomendada para armazenar e gerenciar dados relacionados à UI de forma segura ao ciclo de vida?",
        listOf("ViewModel", "ContentProvider", "AsyncTask", "Service"), 0),
    Question("Qual é o propósito principal da biblioteca Retrofit em um projeto Android?",
        listOf("Gerenciar o banco de dados local", "Implementar navegação", "Criar chamadas de rede REST", "Tratar injeção de dependência"), 2),
    Question("No contexto do Kotlin Coroutines em Android, qual é a principal diferença entre `launch` e `async`?",
        listOf("`launch` retorna um `Job`, `async` retorna um `Deferred`.",
            "`launch` é síncrono, `async` é assíncrono.",
            "`launch` só pode ser usado na UI, `async` só no background.",
            "Não há diferença funcional."), 0),
    Question("Qual padrão de arquitetura é o mais encorajado pelo Google para aplicativos Android modernos?",
        listOf("MVP (Model-View-Presenter)", "MVC (Model-View-Controller)", "MVVM (Model-View-ViewModel)", "VIPER"), 2),
    Question("O que o termo 'Lifecycle-aware' significa para um componente Android?",
        listOf("Ele executa código apenas na Activity principal.",
            "Ele pode sobreviver a mudanças de configuração.",
            "Ele monitora o estado de um `LifecycleOwner` (como Activity ou Fragment).",
            "Ele usa `SharedPreference` para salvar o estado."), 2),
    Question("Em Kotlin, a diferença entre `var` e `val` é que:",
        listOf("`var` é mutável e `val` é imutável.",
            "`var` é usado para tipos primitivos, `val` para objetos.",
            "`val` é global, `var` é local.",
            "`val` permite nulo, `var` não."), 0),
    Question("Qual o principal benefício do uso do Hilt/Dagger em projetos Android grandes?",
        listOf("Otimização de animações da UI.",
            "Simplificação de chamadas RESTful.",
            "Injeção de dependência para código mais testável e desacoplado.",
            "Gerenciamento de recursos de memória."), 2),
    Question("O que é um `Intent` no Android?",
        listOf("Um contêiner de layout para views.",
            "Uma mensagem para solicitar uma ação de outro componente.",
            "Um thread de background para operações longas.",
            "Um arquivo XML de estilo."), 1)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(navController: NavController) {
    val questions = androidQuestions
    var currentQuestion by remember { mutableIntStateOf(0) }
    var selectedAnswerIndex by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }
    val totalQuestions = questions.size
    val question = questions[currentQuestion]

    val shuffledOptions = remember(currentQuestion) {
        question.options.mapIndexed { index, option ->
            ShuffledOption(option, index)
        }.shuffled(Random(currentQuestion.toLong()))
    }

    val progress = (currentQuestion + 1).toFloat() / totalQuestions

    Scaffold(
        topBar = {
            QuizTopBar(
                currentQuestion = currentQuestion,
                totalQuestions = totalQuestions,
                progress = progress,
                onBackClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Pergunta ${currentQuestion + 1} de $totalQuestions",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                question.text,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp,
                    fontSize = 22.sp
                )
            )

            Spacer(Modifier.height(24.dp))

            shuffledOptions.forEachIndexed { index, option ->
                QuizOptionCard(
                    option = option.text,
                    isSelected = selectedAnswerIndex == index,
                    onClick = { selectedAnswerIndex = index }
                )
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.height(20.dp))

            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    if (selectedAnswerIndex != -1) {
                        val originalCorrectIndex = shuffledOptions[selectedAnswerIndex].originalIndex
                        if (originalCorrectIndex == question.correctAnswerIndex) score++

                        if (currentQuestion < questions.size - 1) {
                            currentQuestion++
                            selectedAnswerIndex = -1
                        } else {
                            navController.navigate("result?score=$score")
                        }
                    }
                },
                enabled = selectedAnswerIndex != -1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = if (currentQuestion < questions.size - 1) "Próxima Pergunta" else "Ver Resultados",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}

@Composable
fun QuizTopBar(
    currentQuestion: Int,
    totalQuestions: Int,
    progress: Float,
    onBackClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = "DevQuiz",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(modifier = Modifier.width(48.dp))
            }

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun QuizOptionCard(option: String, isSelected: Boolean, onClick: () -> Unit) {
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
    val borderStroke: BorderStroke? = if (isSelected) null else BorderStroke(2.dp, MaterialTheme.colorScheme.outline)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
        border = borderStroke,
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = option,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizScreenPreview() {
    DevQuizTheme {
        val navController = rememberNavController()
        QuizScreen(navController)
    }
}
