package com.example.model

enum class AppLanguage(val code: String, val displayLabel: String) {
    EN("en", "EN"),
    UR("ur", "اردو")
}

data class BiText(
    val en: String,
    val ur: String
) {
    fun resolve(language: AppLanguage): String = when (language) {
        AppLanguage.EN -> en
        AppLanguage.UR -> ur
    }

    fun matches(query: String): Boolean {
        val q = query.trim()
        return en.contains(q, ignoreCase = true) || ur.contains(q, ignoreCase = true)
    }
}
