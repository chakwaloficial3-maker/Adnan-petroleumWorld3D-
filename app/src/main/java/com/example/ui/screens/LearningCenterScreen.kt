package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.AppLanguage
import com.example.model.BiText
import com.example.model.LessonModule
import com.example.ui.components.GlassIndustrialCard
import com.example.ui.components.ScreenTopBar
import com.example.ui.theme.AmberGlow
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.CoolSilver
import com.example.ui.theme.DeepNavy
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.GlassSlate
import com.example.ui.theme.HazardCrimson
import com.example.ui.theme.LuxuryGold
import com.example.ui.theme.SafetyEmerald
import com.example.viewmodel.AppUiState
import com.example.viewmodel.MainViewModel

enum class LearningTab(val label: BiText) {
    LESSONS(BiText("Lessons", "اسباق")),
    DICTIONARY(BiText("Dictionary", "ڈکشنری")),
    FLASHCARDS(BiText("Flashcards", "فلیش کارڈز")),
    QUIZ(BiText("MCQs Quiz", "کوئز")),
    INTERVIEW(BiText("Interview", "انٹرویو"))
}

@Composable
fun LearningCenterScreen(
    viewModel: MainViewModel,
    uiState: AppUiState
) {
    val lang = uiState.preferences.language
    val repo = viewModel.repository
    var activeTab by remember { mutableStateOf(LearningTab.LESSONS) }
    var activeLesson by remember { mutableStateOf<LessonModule?>(null) }

    val completedCount = uiState.lessonProgress.values.count { it.isCompleted }
    val offlineCount = uiState.lessonProgress.values.count { it.isSavedOffline }
    val totalLessons = repo.lessons.size.coerceAtLeast(1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
    ) {
        ScreenTopBar(
            title = activeLesson?.title?.resolve(lang) ?: BiText("Learning Center", "لرننگ سینٹر").resolve(lang),
            subtitle = BiText("Courses • Urdu Dictionary • Quiz • Interview", "کورسز • اردو ڈکشنری • کوئز • انٹرویو").resolve(lang),
            language = lang,
            onToggleLanguage = { viewModel.toggleLanguage() },
            onBack = if (activeLesson != null) {
                { activeLesson = null }
            } else null
        )

        if (activeLesson != null) {
            LessonReaderView(
                lesson = activeLesson!!,
                isCompleted = uiState.lessonProgress[activeLesson!!.id]?.isCompleted == true,
                isOffline = uiState.lessonProgress[activeLesson!!.id]?.isSavedOffline == true,
                language = lang,
                onToggleComplete = { viewModel.toggleLessonCompleted(activeLesson!!.id) },
                onToggleOffline = { viewModel.toggleLessonOffline(activeLesson!!.id) }
            )
            return
        }

        // Progress Overview Banner
        GlassIndustrialCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            borderColor = ElectricBlue.copy(alpha = 0.45f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProgressMiniStat(BiText("Lessons", "اسباق").resolve(lang), "$completedCount/$totalLessons", ElectricBlue)
                ProgressMiniStat(BiText("Offline", "آف لائن").resolve(lang), "$offlineCount", SafetyEmerald)
                ProgressMiniStat(BiText("Cards", "کارڈز").resolve(lang), "${uiState.masteredFlashcardIds.size}/${repo.flashcards.size}", LuxuryGold)
                ProgressMiniStat(BiText("Best Quiz", "بہترین اسکور").resolve(lang), "${uiState.quizScore.bestPercentage}%", AmberGlow)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { completedCount.toFloat() / totalLessons.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = ElectricBlue,
                trackColor = DeepNavy
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tab Row
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(LearningTab.entries) { tab ->
                val isSel = activeTab == tab
                FilterChip(
                    selected = isSel,
                    onClick = { activeTab = tab },
                    modifier = Modifier.testTag("learn_tab_${tab.name.lowercase()}"),
                    label = { Text(tab.label.resolve(lang)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = LuxuryGold.copy(alpha = 0.22f),
                        selectedLabelColor = LuxuryGold,
                        containerColor = GlassSlate,
                        labelColor = CoolSilver
                    )
                )
            }
        }

        when (activeTab) {
            LearningTab.LESSONS -> LessonsTab(viewModel, uiState, lang) { activeLesson = it }
            LearningTab.DICTIONARY -> DictionaryTab(viewModel, lang)
            LearningTab.FLASHCARDS -> FlashcardsTab(viewModel, uiState, lang)
            LearningTab.QUIZ -> QuizTab(viewModel, lang)
            LearningTab.INTERVIEW -> InterviewTab(viewModel, lang)
        }
    }
}

@Composable
private fun ProgressMiniStat(label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = color)
        Text(label, style = MaterialTheme.typography.labelSmall, color = CoolSilver)
    }
}

@Composable
private fun LessonsTab(
    viewModel: MainViewModel,
    uiState: AppUiState,
    lang: AppLanguage,
    onSelectLesson: (LessonModule) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(viewModel.repository.lessons) { lesson ->
            val progress = uiState.lessonProgress[lesson.id]
            val isCompleted = progress?.isCompleted == true
            val isOffline = progress?.isSavedOffline == true
            GlassIndustrialCard(
                borderColor = if (isCompleted) SafetyEmerald.copy(alpha = 0.55f) else BorderSlate,
                onClick = { onSelectLesson(lesson) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Surface(color = ElectricBlue.copy(alpha = 0.16f), shape = RoundedCornerShape(6.dp)) {
                            Text(lesson.level.title.resolve(lang), style = MaterialTheme.typography.labelSmall, color = ElectricBlue, modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp))
                        }
                        Surface(color = LuxuryGold.copy(alpha = 0.16f), shape = RoundedCornerShape(6.dp)) {
                            Text("${lesson.durationMinutes} min", style = MaterialTheme.typography.labelSmall, color = AmberGlow, modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp))
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { viewModel.toggleLessonOffline(lesson.id) }) {
                            Icon(
                                imageVector = if (isOffline) Icons.Default.CloudDone else Icons.Default.CloudDownload,
                                contentDescription = "Save Offline",
                                tint = if (isOffline) SafetyEmerald else CoolSilver
                            )
                        }
                        if (isCompleted) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Completed", tint = SafetyEmerald, modifier = Modifier.size(20.dp))
                        }
                    }
                }
                Text(lesson.track.resolve(lang), style = MaterialTheme.typography.labelMedium, color = LuxuryGold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(lesson.title.resolve(lang), style = MaterialTheme.typography.titleMedium, color = CleanWhite)
                Spacer(modifier = Modifier.height(4.dp))
                Text(lesson.summary.resolve(lang), style = MaterialTheme.typography.bodySmall, color = CoolSilver)
            }
        }
    }
}

@Composable
private fun LessonReaderView(
    lesson: LessonModule,
    isCompleted: Boolean,
    isOffline: Boolean,
    language: AppLanguage,
    onToggleComplete: () -> Unit,
    onToggleOffline: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onToggleComplete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCompleted) SafetyEmerald else ElectricBlue,
                        contentColor = DeepNavy
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        if (isCompleted) BiText("Completed", "مکمل شدہ").resolve(language)
                        else BiText("Mark Complete", "مکمل نشان لگائیں").resolve(language),
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedButton(
                    onClick = onToggleOffline,
                    border = BorderStroke(1.dp, if (isOffline) SafetyEmerald else BorderSlate),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        if (isOffline) Icons.Default.CloudDone else Icons.Default.CloudDownload,
                        contentDescription = null,
                        tint = if (isOffline) SafetyEmerald else CleanWhite,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        if (isOffline) BiText("Saved (${lesson.sizeKb} KB)", "محفوظ (${lesson.sizeKb} KB)").resolve(language)
                        else BiText("Save Offline", "آف لائن محفوظ").resolve(language),
                        color = if (isOffline) SafetyEmerald else CleanWhite
                    )
                }
            }
        }

        items(lesson.sections) { (heading, body) ->
            GlassIndustrialCard {
                Text(heading.resolve(language), style = MaterialTheme.typography.titleMedium, color = ElectricBlue)
                Spacer(modifier = Modifier.height(6.dp))
                Text(body.resolve(language), style = MaterialTheme.typography.bodyMedium, color = CleanWhite)
            }
        }

        item {
            GlassIndustrialCard(borderColor = LuxuryGold.copy(alpha = 0.5f)) {
                Text(BiText("Key Engineering Takeaways", "اہم نکات").resolve(language), style = MaterialTheme.typography.titleMedium, color = LuxuryGold)
                Spacer(modifier = Modifier.height(6.dp))
                lesson.keyTakeaways.forEach { k ->
                    Text("• ${k.resolve(language)}", style = MaterialTheme.typography.bodyMedium, color = CleanWhite, modifier = Modifier.padding(vertical = 3.dp))
                }
            }
        }
    }
}

@Composable
private fun DictionaryTab(viewModel: MainViewModel, lang: AppLanguage) {
    var filter by remember { mutableStateOf("") }
    val filtered = remember(filter) {
        val q = filter.trim()
        if (q.isEmpty()) viewModel.repository.dictionaryTerms
        else viewModel.repository.dictionaryTerms.filter {
            it.termEn.contains(q, true) || it.termUr.contains(q, true) || it.definition.matches(q)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = filter,
            onValueChange = { filter = it },
            placeholder = { Text(BiText("Filter English / Urdu term...", "انگریزی یا اردو اصطلاح تلاش کریں...").resolve(lang), color = CoolSilver) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ElectricBlue) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ElectricBlue,
                unfocusedBorderColor = BorderSlate,
                focusedContainerColor = GlassSlate,
                unfocusedContainerColor = GlassSlate,
                focusedTextColor = CleanWhite,
                unfocusedTextColor = CleanWhite
            )
        )
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(filtered) { term ->
                GlassIndustrialCard {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(term.termEn, style = MaterialTheme.typography.titleMedium, color = LuxuryGold)
                        Text(term.category, style = MaterialTheme.typography.labelSmall, color = ElectricBlue)
                    }
                    Text(term.termUr, style = MaterialTheme.typography.titleSmall, color = AmberGlow)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(term.definition.resolve(lang), style = MaterialTheme.typography.bodySmall, color = CleanWhite)
                }
            }
        }
    }
}

@Composable
private fun FlashcardsTab(viewModel: MainViewModel, uiState: AppUiState, lang: AppLanguage) {
    val cards = viewModel.repository.flashcards
    var index by remember { mutableIntStateOf(0) }
    var flipped by remember { mutableStateOf(false) }
    val current = cards[index % cards.size]
    val isMastered = current.id in uiState.masteredFlashcardIds

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "${BiText("Card", "کارڈ").resolve(lang)} ${index + 1} / ${cards.size} • ${current.topic}",
            style = MaterialTheme.typography.labelMedium,
            color = ElectricBlue
        )

        GlassIndustrialCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .testTag("flashcard_surface"),
            borderColor = if (flipped) LuxuryGold else ElectricBlue,
            onClick = { flipped = !flipped }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Surface(
                    color = if (flipped) LuxuryGold.copy(alpha = 0.2f) else ElectricBlue.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = if (flipped) BiText("ANSWER", "جواب").resolve(lang) else BiText("QUESTION", "سوال").resolve(lang),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (flipped) LuxuryGold else ElectricBlue,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Icon(Icons.Default.Flip, contentDescription = "Flip Card", tint = CoolSilver, modifier = Modifier.align(Alignment.TopEnd))
                Text(
                    text = if (flipped) current.back.resolve(lang) else current.front.resolve(lang),
                    style = MaterialTheme.typography.titleLarge,
                    color = CleanWhite,
                    modifier = Modifier.align(Alignment.Center)
                )
                Text(
                    text = BiText("Tap card to flip", "پلٹنے کے لیے ٹیپ کریں").resolve(lang),
                    style = MaterialTheme.typography.labelSmall,
                    color = CoolSilver,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = {
                    index = if (index == 0) cards.lastIndex else index - 1
                    flipped = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(BiText("Previous", "پچھلا").resolve(lang), color = CleanWhite)
            }
            Button(
                onClick = { viewModel.toggleFlashcardMastered(current.id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isMastered) SafetyEmerald else LuxuryGold,
                    contentColor = DeepNavy
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isMastered) BiText("Mastered ✓", "یاد ہوگیا ✓").resolve(lang) else BiText("Mark Mastered", "یاد نشان لگائیں").resolve(lang), fontWeight = FontWeight.Bold)
            }
            OutlinedButton(
                onClick = {
                    index = (index + 1) % cards.size
                    flipped = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(BiText("Next", "اگلا").resolve(lang), color = ElectricBlue)
            }
        }
    }
}

@Composable
private fun QuizTab(viewModel: MainViewModel, lang: AppLanguage) {
    val questions = viewModel.repository.quizQuestions
    var qIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var correctCount by remember { mutableIntStateOf(0) }
    var finished by remember { mutableStateOf(false) }

    if (finished) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlassIndustrialCard(borderColor = LuxuryGold, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Quiz, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(44.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Text(BiText("Quiz Completed!", "کوئز مکمل ہوگیا!").resolve(lang), style = MaterialTheme.typography.headlineSmall, color = CleanWhite)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${BiText("Score", "اسکور").resolve(lang)}: $correctCount / ${questions.size}",
                    style = MaterialTheme.typography.titleLarge,
                    color = ElectricBlue
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = {
                        qIndex = 0
                        selectedOption = null
                        correctCount = 0
                        finished = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = DeepNavy),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(BiText("Retake Quiz", "دوبارہ شروع کریں").resolve(lang), fontWeight = FontWeight.Bold)
                }
            }
        }
        return
    }

    val current = questions[qIndex]
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text(
                text = "${BiText("Question", "سوال").resolve(lang)} ${qIndex + 1}/${questions.size} • ${current.topic.resolve(lang)}",
                style = MaterialTheme.typography.labelMedium,
                color = LuxuryGold
            )
            Spacer(modifier = Modifier.height(6.dp))
            GlassIndustrialCard {
                Text(current.question.resolve(lang), style = MaterialTheme.typography.titleMedium, color = CleanWhite)
            }
        }

        items(current.options.size) { idx ->
            val optText = current.options[idx].resolve(lang)
            val isChosen = selectedOption == idx
            val isCorrect = idx == current.correctIndex
            val border = when {
                selectedOption == null -> BorderSlate
                isCorrect -> SafetyEmerald
                isChosen -> HazardCrimson
                else -> BorderSlate
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = selectedOption == null) {
                        selectedOption = idx
                        if (idx == current.correctIndex) correctCount++
                    },
                color = GlassSlate,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, border)
            ) {
                Text(optText, style = MaterialTheme.typography.bodyLarge, color = CleanWhite, modifier = Modifier.padding(14.dp))
            }
        }

        if (selectedOption != null) {
            item {
                GlassIndustrialCard(borderColor = ElectricBlue) {
                    Text(
                        text = if (selectedOption == current.correctIndex) BiText("Correct!", "درست جواب!").resolve(lang)
                        else BiText("Explanation", "وضاحت").resolve(lang),
                        style = MaterialTheme.typography.titleSmall,
                        color = if (selectedOption == current.correctIndex) SafetyEmerald else LuxuryGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(current.explanation.resolve(lang), style = MaterialTheme.typography.bodyMedium, color = CleanWhite)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            if (qIndex < questions.lastIndex) {
                                qIndex++
                                selectedOption = null
                            } else {
                                viewModel.recordQuizResult(correctCount, questions.size)
                                finished = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = DeepNavy),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (qIndex < questions.lastIndex) BiText("Next Question →", "اگلا سوال →").resolve(lang)
                            else BiText("Finish & Save Score", "نتیجہ محفوظ کریں").resolve(lang),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InterviewTab(viewModel: MainViewModel, lang: AppLanguage) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(viewModel.repository.interviewQuestions) { iq ->
            var expanded by remember { mutableStateOf(false) }
            GlassIndustrialCard(onClick = { expanded = !expanded }) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.WorkOutline, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(iq.role.resolve(lang), style = MaterialTheme.typography.labelMedium, color = LuxuryGold)
                    }
                    Icon(if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null, tint = CoolSilver)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(iq.question.resolve(lang), style = MaterialTheme.typography.titleMedium, color = CleanWhite)
                AnimatedVisibility(visible = expanded) {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(iq.answer.resolve(lang), style = MaterialTheme.typography.bodyMedium, color = ElectricBlue)
                    }
                }
            }
        }
    }
}
