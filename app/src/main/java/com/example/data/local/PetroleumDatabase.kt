package com.example.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "favorite_equipment")
data class FavoriteEquipmentEntity(
    @PrimaryKey val equipmentId: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "recently_viewed")
data class RecentlyViewedEntity(
    @PrimaryKey val equipmentId: String,
    val viewedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey val queryText: String,
    val searchedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: String,
    val isCompleted: Boolean = false,
    val isSavedOffline: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "flashcard_mastery")
data class FlashcardMasteryEntity(
    @PrimaryKey val flashcardId: String,
    val isMastered: Boolean = true
)

@Entity(tableName = "quiz_scores")
data class QuizScoreEntity(
    @PrimaryKey val id: Int = 1,
    val totalAttempted: Int = 0,
    val totalCorrect: Int = 0,
    val bestPercentage: Int = 0
)

@Dao
interface PetroleumDao {
    @Query("SELECT equipmentId FROM favorite_equipment ORDER BY savedAt DESC")
    fun observeFavoriteIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(entity: FavoriteEquipmentEntity)

    @Query("DELETE FROM favorite_equipment WHERE equipmentId = :id")
    suspend fun removeFavorite(id: String)

    @Query("SELECT equipmentId FROM recently_viewed ORDER BY viewedAt DESC LIMIT 12")
    fun observeRecentIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecentlyViewed(entity: RecentlyViewedEntity)

    @Query("SELECT queryText FROM search_history ORDER BY searchedAt DESC LIMIT 10")
    fun observeSearchHistory(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSearchHistory(entity: SearchHistoryEntity)

    @Query("DELETE FROM search_history")
    suspend fun clearSearchHistory()

    @Query("SELECT * FROM lesson_progress")
    fun observeLessonProgress(): Flow<List<LessonProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLessonProgress(entity: LessonProgressEntity)

    @Query("SELECT flashcardId FROM flashcard_mastery WHERE isMastered = 1")
    fun observeMasteredFlashcards(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markFlashcardMastered(entity: FlashcardMasteryEntity)

    @Query("DELETE FROM flashcard_mastery WHERE flashcardId = :id")
    suspend fun unmarkFlashcardMastered(id: String)

    @Query("SELECT * FROM quiz_scores WHERE id = 1")
    fun observeQuizScore(): Flow<QuizScoreEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQuizScore(entity: QuizScoreEntity)
}

@Database(
    entities = [
        FavoriteEquipmentEntity::class,
        RecentlyViewedEntity::class,
        SearchHistoryEntity::class,
        LessonProgressEntity::class,
        FlashcardMasteryEntity::class,
        QuizScoreEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PetroleumDatabase : RoomDatabase() {
    abstract fun petroleumDao(): PetroleumDao

    companion object {
        @Volatile
        private var INSTANCE: PetroleumDatabase? = null

        fun getInstance(context: Context): PetroleumDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PetroleumDatabase::class.java,
                    "petroleum_world_3d.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
