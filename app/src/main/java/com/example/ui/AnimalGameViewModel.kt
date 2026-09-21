package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.animalapp.ui.components.RewardFruit
import com.example.model.AgeGroup
import com.example.model.Animal
import com.example.model.AnimalRepository
import com.example.model.LearningCategory
import com.example.model.LearningItem
import com.example.model.VoiceGender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class QuizQuestion(
    val targetAnimal: LearningItem,
    val options: List<LearningItem>,
    val answeredCorrectly: Boolean = false,
    val wrongAttempts: Set<String> = emptySet()
)

data class GameUiState(
    val selectedAgeGroup: AgeGroup? = null, // null = Ana Sayfada Yaş Seçimi Ekranı
    val currentAgeGroup: AgeGroup = AgeGroup.AGE_3,
    val selectedCategory: LearningCategory = LearningCategory.ALL,
    val voiceGender: VoiceGender = VoiceGender.FEMALE,
    val currentQuestion: QuizQuestion,
    val currentFruitReward: RewardFruit = RewardFruit.APPLE,
    val showRewardOverlay: Boolean = false,
    val starsCount: Int = 0,
    val roundNumber: Int = 1,
    // State-based reward sticker collection
    val unlockedFruitStickers: Set<String> = emptySet(),
    val newlyUnlockedSticker: RewardFruit? = null,
    val showStickerCollectionDialog: Boolean = false,
    val moduleRoundsCompleted: Int = 0,
    val moduleTargetRounds: Int = 5 // Her 5 başarılı soruda bir öğrenme modülü tamamlanır ve yeni bir çıkartma kilidi açılır
)

class AnimalGameViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<GameUiState>

    init {
        val initialAge = AgeGroup.AGE_3
        val initialCategory = LearningCategory.ALL
        val initialQuestion = generateQuestion(
            ageGroup = initialAge,
            category = initialCategory,
            excludeItemId = null
        )

        _uiState = MutableStateFlow(
            GameUiState(
                selectedAgeGroup = null, // Uygulama İLK AÇILDIĞINDA ANA SAYFADA YAŞ SEÇİMİ AÇILIR
                currentAgeGroup = initialAge,
                selectedCategory = initialCategory,
                currentQuestion = initialQuestion,
                unlockedFruitStickers = setOf(RewardFruit.APPLE.name) // Başlangıçta 1 adet hoş geldin çıkartması
            )
        )
    }

    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private fun generateQuestion(
        ageGroup: AgeGroup,
        category: LearningCategory = LearningCategory.ALL,
        excludeItemId: String? = null
    ): QuizQuestion {
        val pool = AnimalRepository.getItemsForAge(ageGroup, category)
            .ifEmpty { AnimalRepository.allItems }

        val eligiblePool = if (excludeItemId != null && pool.size > 1) {
            pool.filter { it.id != excludeItemId }.ifEmpty { pool }
        } else {
            pool
        }

        val target = eligiblePool.random()

        // Seçenek sayısı yaş grubuna özel: 2 Yaş & 3 Yaş -> 2 seçenek, 4 Yaş -> 3 seçenek, 5 Yaş -> 4 seçenek
        val desiredOptions = ageGroup.optionsCount.coerceAtMost(pool.size).coerceAtLeast(2)
        val decoyCount = (desiredOptions - 1).coerceAtLeast(1)

        val decoys = pool.filter { it.id != target.id }
            .shuffled()
            .take(decoyCount)

        val options = (decoys + target).shuffled()
        return QuizQuestion(targetAnimal = target, options = options)
    }

    fun selectAgeGroup(ageGroup: AgeGroup) {
        val nextQuestion = generateQuestion(
            ageGroup = ageGroup,
            category = _uiState.value.selectedCategory,
            excludeItemId = null
        )
        _uiState.update { state ->
            state.copy(
                selectedAgeGroup = ageGroup,
                currentAgeGroup = ageGroup,
                currentQuestion = nextQuestion,
                showRewardOverlay = false,
                starsCount = 0,
                roundNumber = 1,
                moduleRoundsCompleted = 0
            )
        }
    }

    fun selectCategory(category: LearningCategory) {
        val nextQuestion = generateQuestion(
            ageGroup = _uiState.value.currentAgeGroup,
            category = category,
            excludeItemId = null
        )
        _uiState.update { state ->
            state.copy(
                selectedCategory = category,
                currentQuestion = nextQuestion,
                showRewardOverlay = false
            )
        }
    }

    fun setVoiceGender(voiceGender: VoiceGender) {
        _uiState.update { state ->
            state.copy(voiceGender = voiceGender)
        }
    }

    fun returnToAgeSelection() {
        _uiState.update { state ->
            state.copy(
                selectedAgeGroup = null,
                showRewardOverlay = false,
                showStickerCollectionDialog = false
            )
        }
    }

    fun onAnimalSelected(selectedAnimal: Animal): Boolean {
        return onItemSelected(selectedAnimal)
    }

    fun onItemSelected(selectedItem: LearningItem): Boolean {
        val currentTarget = _uiState.value.currentQuestion.targetAnimal
        if (selectedItem.id == currentTarget.id) {
            val fruit = currentTarget.rewardFruit
            _uiState.update { state ->
                state.copy(
                    currentFruitReward = fruit,
                    currentQuestion = state.currentQuestion.copy(answeredCorrectly = true)
                )
            }
            return true
        } else {
            _uiState.update { state ->
                val newWrong = state.currentQuestion.wrongAttempts + selectedItem.id
                state.copy(
                    currentQuestion = state.currentQuestion.copy(wrongAttempts = newWrong)
                )
            }
            return false
        }
    }

    fun showRewardScreen() {
        _uiState.update { state ->
            state.copy(showRewardOverlay = true)
        }
    }

    fun onRewardCompleted() {
        _uiState.update { state ->
            val nextRound = state.roundNumber + 1
            val nextStars = state.starsCount + 1
            val nextModuleProgress = state.moduleRoundsCompleted + 1

            // Modül tamamlandı mı kontrol et (her moduleTargetRounds soruda bir modül biter)
            val isModuleFinished = nextModuleProgress >= state.moduleTargetRounds

            var newlyUnlocked: RewardFruit? = null
            var updatedStickers = state.unlockedFruitStickers

            if (isModuleFinished) {
                // Henüz açılmamış meyvelerden birini seç veya mevcut ödül meyvesini ekle
                val allFruits = RewardFruit.values().toList()
                val lockedFruits = allFruits.filter { !state.unlockedFruitStickers.contains(it.name) }
                val stickerToUnlock = lockedFruits.firstOrNull() ?: state.currentFruitReward
                newlyUnlocked = stickerToUnlock
                updatedStickers = state.unlockedFruitStickers + stickerToUnlock.name
            }

            val nextQuestion = generateQuestion(
                ageGroup = state.currentAgeGroup,
                category = state.selectedCategory,
                excludeItemId = state.currentQuestion.targetAnimal.id
            )

            state.copy(
                showRewardOverlay = false,
                starsCount = nextStars,
                roundNumber = nextRound,
                currentQuestion = nextQuestion,
                moduleRoundsCompleted = if (isModuleFinished) 0 else nextModuleProgress,
                unlockedFruitStickers = updatedStickers,
                newlyUnlockedSticker = newlyUnlocked,
                showStickerCollectionDialog = isModuleFinished
            )
        }
    }

    fun openStickerCollection() {
        _uiState.update { it.copy(showStickerCollectionDialog = true, newlyUnlockedSticker = null) }
    }

    fun closeStickerCollection() {
        _uiState.update { it.copy(showStickerCollectionDialog = false, newlyUnlockedSticker = null) }
    }

    fun restartGame() {
        _uiState.update { state ->
            state.copy(
                currentQuestion = generateQuestion(state.currentAgeGroup, state.selectedCategory, null),
                starsCount = 0,
                roundNumber = 1,
                moduleRoundsCompleted = 0,
                showRewardOverlay = false,
                showStickerCollectionDialog = false
            )
        }
    }
}
