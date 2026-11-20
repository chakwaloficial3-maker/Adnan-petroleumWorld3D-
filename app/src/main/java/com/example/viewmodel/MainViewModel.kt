package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.FavoriteEquipmentEntity
import com.example.data.local.FlashcardMasteryEntity
import com.example.data.local.LessonProgressEntity
import com.example.data.local.PetroleumDatabase
import com.example.data.local.QuizScoreEntity
import com.example.data.local.RecentlyViewedEntity
import com.example.data.local.SearchHistoryEntity
import com.example.data.local.UserPreferences
import com.example.data.local.UserPreferencesRepository
import com.example.data.repository.PetroleumContentRepository
import com.example.model.AppLanguage
import com.example.model.DailyFact
import com.example.model.EquipmentItem
import com.example.model.PetroleumCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class AppUiState(
    val preferences: UserPreferences = UserPreferences(),
    val favoriteIds: Set<String> = emptySet(),
    val recentIds: List<String> = emptyList(),
    val searchHistory: List<String> = emptyList(),
    val lessonProgress: Map<String, LessonProgressEntity> = emptyMap(),
    val masteredFlashcardIds: Set<String> = emptySet(),
    val quizScore: QuizScoreEntity = QuizScoreEntity(),
    val dailyFactIndex: Int = 0
)

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val repository = PetroleumContentRepository()
    private val dao = PetroleumDatabase.getInstance(application).petroleumDao()
    private val prefsRepo = UserPreferencesRepository(application)

    private val initialFactIndex = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) %
        repository.dailyFacts.size.coerceAtLeast(1)
    private val factIndexFlow = MutableStateFlow(initialFactIndex)

    private val baseFlows = combine(
        prefsRepo.preferencesFlow,
        dao.observeFavoriteIds(),
        dao.observeRecentIds(),
        dao.observeSearchHistory(),
        dao.observeLessonProgress()
    ) { prefs, favs, recents, history, lessons ->
        Quint(prefs, favs, recents, history, lessons)
    }

    val uiState: StateFlow<AppUiState> = combine(
        baseFlows,
        dao.observeMasteredFlashcards(),
        dao.observeQuizScore(),
        factIndexFlow
    ) { base, mastered, quizScore, factIdx ->
        AppUiState(
            preferences = base.first,
            favoriteIds = base.second.toSet(),
            recentIds = base.third,
            searchHistory = base.fourth,
            lessonProgress = base.fifth.associateBy { it.lessonId },
            masteredFlashcardIds = mastered.toSet(),
            quizScore = quizScore ?: QuizScoreEntity(),
            dailyFactIndex = factIdx
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppUiState(dailyFactIndex = initialFactIndex)
    )

    fun toggleLanguage() {
        viewModelScope.launch {
            val next = if (uiState.value.preferences.language == AppLanguage.EN) {
                AppLanguage.UR
            } else {
                AppLanguage.EN
            }
            prefsRepo.setLanguage(next)
        }
    }

    fun setLanguage(lang: AppLanguage) {
        viewModelScope.launch { prefsRepo.setLanguage(lang) }
    }

    fun setAutoRotate3d(enabled: Boolean) {
        viewModelScope.launch { prefsRepo.setAutoRotate3d(enabled) }
    }

    fun setShow3dLabels(enabled: Boolean) {
        viewModelScope.launch { prefsRepo.setShow3dLabels(enabled) }
    }

    fun setLowEndMode(enabled: Boolean) {
        viewModelScope.launch { prefsRepo.setLowEndPerformanceMode(enabled) }
    }

    fun toggleFavorite(equipmentId: String) {
        viewModelScope.launch {
            if (equipmentId in uiState.value.favoriteIds) {
                dao.removeFavorite(equipmentId)
            } else {
                dao.addFavorite(FavoriteEquipmentEntity(equipmentId))
            }
        }
    }

    fun recordEquipmentView(equipmentId: String) {
        viewModelScope.launch {
            dao.upsertRecentlyViewed(RecentlyViewedEntity(equipmentId))
        }
    }

    fun recordSearch(query: String) {
        val trimmed = query.trim()
        if (trimmed.length < 2) return
        viewModelScope.launch {
            dao.upsertSearchHistory(SearchHistoryEntity(trimmed))
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch { dao.clearSearchHistory() }
    }

    fun toggleLessonCompleted(lessonId: String) {
        viewModelScope.launch {
            val current = uiState.value.lessonProgress[lessonId]
            val updated = (current ?: LessonProgressEntity(lessonId)).copy(
                isCompleted = !(current?.isCompleted ?: false),
                updatedAt = System.currentTimeMillis()
            )
            dao.upsertLessonProgress(updated)
        }
    }

    fun toggleLessonOffline(lessonId: String) {
        viewModelScope.launch {
            val current = uiState.value.lessonProgress[lessonId]
            val updated = (current ?: LessonProgressEntity(lessonId)).copy(
                isSavedOffline = !(current?.isSavedOffline ?: false),
                updatedAt = System.currentTimeMillis()
            )
            dao.upsertLessonProgress(updated)
        }
    }

    fun toggleFlashcardMastered(flashcardId: String) {
        viewModelScope.launch {
            if (flashcardId in uiState.value.masteredFlashcardIds) {
                dao.unmarkFlashcardMastered(flashcardId)
            } else {
                dao.markFlashcardMastered(FlashcardMasteryEntity(flashcardId, true))
            }
        }
    }

    fun recordQuizResult(correct: Int, total: Int) {
        viewModelScope.launch {
            val prev = uiState.value.quizScore
            val pct = if (total > 0) (correct * 100) / total else 0
            dao.upsertQuizScore(
                QuizScoreEntity(
                    id = 1,
                    totalAttempted = prev.totalAttempted + total,
                    totalCorrect = prev.totalCorrect + correct,
                    bestPercentage = maxOf(prev.bestPercentage, pct)
                )
            )
        }
    }

    fun nextDailyFact() {
        val count = repository.dailyFacts.size.coerceAtLeast(1)
        factIndexFlow.value = (factIndexFlow.value + 1) % count
    }

    fun currentDailyFact(): DailyFact =
        repository.dailyFacts[uiState.value.dailyFactIndex % repository.dailyFacts.size]

    fun getCategory(id: String): PetroleumCategory? = repository.getCategoryById(id)
    fun getEquipment(id: String): EquipmentItem? = repository.getEquipmentById(id)

    private data class Quint<A, B, C, D, E>(
        val first: A,
        val second: B,
        val third: C,
        val fourth: D,
        val fifth: E
    )
}
