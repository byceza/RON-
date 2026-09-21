package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.animalapp.ui.components.RewardFruit

enum class AgeGroup(
    val age: Int,
    val label: String,
    val title: String,
    val badge: String,
    val optionsCount: Int,
    val description: String,
    val teacherAdvice: String
) {
    AGE_2(
        age = 2,
        label = "2 Yaş",
        title = "2 Yaş Dünyası",
        badge = "👶 İlk Adımlar",
        optionsCount = 2,
        description = "Büyük sevimli hayvanlar ve temel görsel kavramlar",
        teacherAdvice = "2 Yaş İpucu: 2 büyük seçenek ve sevecen öğretmen yönlendirmesiyle bebeğinizin görsel bağ kurmasını sağlayın."
    ),
    AGE_3(
        age = 3,
        label = "3 Yaş",
        title = "3 Yaş Dünyası",
        badge = "🐣 Minikler",
        optionsCount = 2,
        description = "Sevimli dostlar, renkli meyveler ve temel şekiller",
        teacherAdvice = "3 Yaş İpucu: 2 seçenekli sorular ve renk odaklı meyve ödülleriyle görsel odaklanmayı destekleyin."
    ),
    AGE_4(
        age = 4,
        label = "4 Yaş",
        title = "4 Yaş Dünyası",
        badge = "🌟 Meraklılar",
        optionsCount = 3,
        description = "Çiftlik, orman, zengin doğa ve sayılar",
        teacherAdvice = "4 Yaş İpucu: 3 seçenekli eşleştirmeler ve şefkatli öğretmen anlatımıyla kavram derinliğini artırın."
    ),
    AGE_5(
        age = 5,
        label = "5 Yaş",
        title = "5 Yaş Dünyası",
        badge = "🚀 Küçük Dâhiler",
        optionsCount = 4,
        description = "Vahşi doğa, coğrafya, semboller ve akıllı eşleştirmeler",
        teacherAdvice = "5 Yaş İpucu: 4 seçenekli dikkat sorularıyla okul öncesi odaklanma becerisini güçlendirin."
    )
}

enum class LearningCategory(
    val title: String,
    val icon: String
) {
    ALL("Karışık Macera", "🎲"),
    ANIMALS("Hayvanlar", "🦁"),
    FRUITS("Meyveler", "🍓"),
    SHAPES("Şekiller", "🔺"),
    NATURE("Doğa & Coğrafya", "🌍"),
    NUMBERS("Sayılar", "🔢"),
    SYMBOLS("Semboller", "⚡")
}

enum class VoiceGender(
    val label: String,
    val icon: String
) {
    FEMALE("Kadın Sesi", "👩‍🏫"),
    MALE("Erkek Sesi", "👨‍🏫")
}

data class LearningItem(
    val id: String,
    val name: String,
    val emoji: String,
    val soundText: String,
    val questionVoice: String,
    val accentColor: Color,
    val cardBgColor: Color,
    val rewardFruit: RewardFruit,
    val category: LearningCategory,
    val minAge: Int = 3,
    val soundType: String = "pop",
    val teacherExplanation: String = "",
    val countVisual: String? = null
)

typealias Animal = LearningItem

object AnimalRepository {

    // 1. Hayvanlar (36 Tür)
    val animals: List<LearningItem> = LearningContentCatalog.animalsList

    // 2. Meyveler (100 Tür)
    val fruits: List<LearningItem> = LearningContentCatalog.fruitsList

    // 3. Şekiller (50 Tür)
    val shapes: List<LearningItem> = LearningContentCatalog.shapesList

    // 4. Doğa ve Coğrafya (50 Tür)
    val nature: List<LearningItem> = LearningContentCatalog.natureList

    // 5. Sayılar (20 Öğe)
    val numbers: List<LearningItem> = LearningContentCatalog.numbersList

    // 6. Semboller (50 Öğe)
    val symbols: List<LearningItem> = LearningContentCatalog.symbolsList

    val allItems: List<LearningItem> = animals + fruits + shapes + nature + numbers + symbols

    fun getItemsForAge(ageGroup: AgeGroup, category: LearningCategory = LearningCategory.ALL): List<LearningItem> {
        val pool = when (category) {
            LearningCategory.ALL -> allItems
            LearningCategory.ANIMALS -> animals
            LearningCategory.FRUITS -> fruits
            LearningCategory.SHAPES -> shapes
            LearningCategory.NATURE -> nature
            LearningCategory.NUMBERS -> numbers
            LearningCategory.SYMBOLS -> symbols
        }
        return pool.filter { it.minAge <= ageGroup.age }.ifEmpty { pool }
    }

    fun getAnimalsForAge(ageGroup: AgeGroup): List<LearningItem> {
        return animals.filter { it.minAge <= ageGroup.age }.ifEmpty { animals }
    }

    fun getItemsForCategory(category: LearningCategory): List<LearningItem> {
        return when (category) {
            LearningCategory.ANIMALS -> animals
            LearningCategory.FRUITS -> fruits
            LearningCategory.SHAPES -> shapes
            LearningCategory.NATURE -> nature
            LearningCategory.NUMBERS -> numbers
            LearningCategory.SYMBOLS -> symbols
            LearningCategory.ALL -> allItems
        }
    }
}
