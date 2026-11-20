package com.example.model

import androidx.annotation.DrawableRes

enum class Model3DType {
    CENTRIFUGAL_PUMP,
    MUD_PUMP,
    SEPARATOR_3PHASE,
    CHRISTMAS_TREE,
    DRILLING_RIG,
    GATE_VALVE,
    DISTILLATION_COLUMN,
    RECIPROCATING_COMPRESSOR,
    BOP_STACK
}

enum class DiagramType {
    GEOLOGY_ANTICLINE_TRAP,
    ROTARY_RIG_CIRCULATION,
    MUD_PUMP_TRIPLEX,
    CENTRIFUGAL_PUMP_FLOW,
    THREE_PHASE_SEPARATOR,
    CHRISTMAS_TREE_STACK,
    DISTILLATION_COLUMN,
    BOP_WELL_CONTROL,
    VALVE_CROSS_SECTION,
    COMPRESSOR_CYCLE,
    HEAT_EXCHANGER_SHELL_TUBE,
    STORAGE_TANK_FLOATING_ROOF,
    PIPELINE_PIGGING,
    INSTRUMENTATION_PID,
    ELECTRICAL_EXPLOSION_PROOF,
    LAB_TESTING_HYDROMETER
}

data class PetroleumCategory(
    val id: String,
    val number: Int,
    val title: BiText,
    val subtitle: BiText,
    val sectorTag: BiText,
    @DrawableRes val heroImageRes: Int? = null,
    val accentColorHex: Long
)

data class EquipmentComponent(
    val id: String,
    val name: BiText,
    val material: String,
    val functionDesc: BiText,
    val colorHex: Long = 0xFF00D4FF
)

data class EquipmentSpec(
    val label: BiText,
    val value: String
)

data class EquipmentItem(
    val id: String,
    val categoryId: String,
    val name: BiText,
    val shortTagline: BiText,
    val has3dModel: Boolean,
    val model3dType: Model3DType? = null,
    val diagramType: DiagramType,
    val isPopular: Boolean = false,
    val isSmallComponent: Boolean = false,
    val definition: BiText,
    val purposeAndFunction: BiText,
    val workingPrinciple: BiText,
    val construction: BiText,
    val mainComponents: List<EquipmentComponent>,
    val typesAndClassification: List<BiText>,
    val applications: List<BiText>,
    val specifications: List<EquipmentSpec>,
    val maintenanceConcepts: List<BiText>,
    val commonProblems: List<BiText>,
    val safetyPrecautions: List<BiText>,
    val relatedIds: List<String>,
    val references: List<String>
)

enum class LessonLevel(val title: BiText) {
    BEGINNER(BiText("Beginner", "ابتدائی (Beginner)")),
    INTERMEDIATE(BiText("Intermediate", "درمیانی (Intermediate)")),
    ADVANCED(BiText("Advanced", "اعلیٰ (Advanced)"))
}

data class LessonModule(
    val id: String,
    val track: BiText,
    val level: LessonLevel,
    val title: BiText,
    val summary: BiText,
    val durationMinutes: Int,
    val sections: List<Pair<BiText, BiText>>,
    val keyTakeaways: List<BiText>,
    val sizeKb: Int
)

data class DictionaryTerm(
    val id: String,
    val termEn: String,
    val termUr: String,
    val category: String,
    val definition: BiText
)

data class FlashcardItem(
    val id: String,
    val topic: String,
    val front: BiText,
    val back: BiText
)

data class QuizQuestion(
    val id: String,
    val topic: BiText,
    val question: BiText,
    val options: List<BiText>,
    val correctIndex: Int,
    val explanation: BiText
)

data class InterviewQuestion(
    val id: String,
    val role: BiText,
    val question: BiText,
    val answer: BiText
)

data class HseTopic(
    val id: String,
    val title: BiText,
    val severityLabel: BiText,
    val overview: BiText,
    val checklist: List<BiText>,
    val criticalLimits: List<Pair<BiText, String>>
)

data class DailyFact(
    val id: String,
    val title: BiText,
    val fact: BiText,
    val categoryTag: BiText
)

data class EngineeringRefTable(
    val id: String,
    val title: BiText,
    val headers: List<String>,
    val rows: List<List<String>>
)
