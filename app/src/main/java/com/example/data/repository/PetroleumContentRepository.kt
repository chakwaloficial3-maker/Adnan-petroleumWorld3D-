package com.example.data.repository

import com.example.model.DailyFact
import com.example.model.DictionaryTerm
import com.example.model.EngineeringRefTable
import com.example.model.EquipmentItem
import com.example.model.FlashcardItem
import com.example.model.HseTopic
import com.example.model.InterviewQuestion
import com.example.model.LessonModule
import com.example.model.PetroleumCategory
import com.example.model.QuizQuestion

class PetroleumContentRepository {
    val categories: List<PetroleumCategory> = CategoriesData.allCategories

    val allEquipment: List<EquipmentItem> =
        EquipmentDataUpstream.items +
            EquipmentDataMidDownstream.items +
            EquipmentDataSupportSafetyLab.items

    val lessons: List<LessonModule> = LearningAndHseData.lessons
    val dictionaryTerms: List<DictionaryTerm> = LearningAndHseData.dictionaryTerms
    val flashcards: List<FlashcardItem> = LearningAndHseData.flashcards
    val quizQuestions: List<QuizQuestion> = LearningAndHseData.quizQuestions
    val interviewQuestions: List<InterviewQuestion> = LearningAndHseData.interviewQuestions
    val hseTopics: List<HseTopic> = LearningAndHseData.hseTopics
    val dailyFacts: List<DailyFact> = LearningAndHseData.dailyFacts
    val referenceTables: List<EngineeringRefTable> = LearningAndHseData.referenceTables

    fun getCategoryById(id: String): PetroleumCategory? = categories.find { it.id == id }

    fun getEquipmentById(id: String): EquipmentItem? = allEquipment.find { it.id == id }

    fun getEquipmentByCategory(categoryId: String): List<EquipmentItem> =
        allEquipment.filter { it.categoryId == categoryId }

    fun get3DEquipment(): List<EquipmentItem> = allEquipment.filter { it.has3dModel }

    fun getPopularEquipment(): List<EquipmentItem> = allEquipment.filter { it.isPopular }

    fun searchEquipment(
        query: String,
        categoryId: String? = null,
        only3D: Boolean = false
    ): List<EquipmentItem> {
        val q = query.trim()
        return allEquipment.filter { item ->
            val catMatch = categoryId == null || item.categoryId == categoryId
            val modelMatch = !only3D || item.has3dModel
            val textMatch = q.isEmpty() ||
                item.name.matches(q) ||
                item.shortTagline.matches(q) ||
                item.definition.matches(q) ||
                item.purposeAndFunction.matches(q) ||
                item.mainComponents.any { it.name.matches(q) || it.functionDesc.matches(q) } ||
                (getCategoryById(item.categoryId)?.title?.matches(q) == true)
            catMatch && modelMatch && textMatch
        }
    }
}
