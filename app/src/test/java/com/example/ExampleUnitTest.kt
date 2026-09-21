package com.example

import com.example.animalapp.ui.components.RewardFruit
import com.example.model.AgeGroup
import com.example.model.AnimalRepository
import com.example.model.LearningCategory
import com.example.model.VoiceGender
import com.example.ui.AnimalGameViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun testRewardFruitsData() {
    val fruits = RewardFruit.values()
    assertTrue(fruits.size >= 14)

    val strawberry = RewardFruit.STRAWBERRY
    assertEquals("Çilek", strawberry.fruitName)
    assertEquals("Kırmızı", strawberry.colorName)
    assertEquals("🍓", strawberry.emoji)
    assertTrue(strawberry.voicePrompt.contains("çileği", ignoreCase = true))

    val banana = RewardFruit.BANANA
    assertEquals("Muz", banana.fruitName)
    assertEquals("Sarı", banana.colorName)
    assertEquals("🍌", banana.emoji)
    assertEquals("Hadi şu sarı muzu ye bakalım.", banana.voicePrompt)

    val apple = RewardFruit.APPLE
    assertEquals("Elma", apple.fruitName)
    assertEquals("Kırmızı", apple.colorName)
    assertEquals("🍎", apple.emoji)
    assertEquals("Hadi şu tatlı kırmızı elmayı ısır bakalım.", apple.voicePrompt)

    val avocado = RewardFruit.AVOCADO
    assertEquals("Avokado", avocado.fruitName)
    assertEquals("Yeşil", avocado.colorName)

    val blueberry = RewardFruit.BLUEBERRY
    assertEquals("Yaban Mersini", blueberry.fruitName)
    assertEquals("Mavi", blueberry.colorName)
  }

  @Test
  fun testAnimalRepositoryAndAgeFilter() {
    val animals = AnimalRepository.animals
    assertTrue(animals.isNotEmpty())

    // 2 Yaş hayvanları
    val age2Animals = AnimalRepository.getAnimalsForAge(AgeGroup.AGE_2)
    assertTrue(age2Animals.isNotEmpty())
    assertTrue(age2Animals.any { it.id == "cat" })
    assertTrue(age2Animals.any { it.id == "dog" })
    assertTrue(age2Animals.any { it.id == "cow" })

    // 5 Yaş hayvanları
    val age5Animals = AnimalRepository.getAnimalsForAge(AgeGroup.AGE_5)
    assertTrue(age5Animals.size >= age2Animals.size)
    assertTrue(age5Animals.any { it.id == "wolf" })
  }

  @Test
  fun testViewModelAgeSelectionFirst() {
    val vm = AnimalGameViewModel()

    // Uygulama ilk açıldığında ana sayfada yaş seçimi açık olmalı
    assertNull(vm.uiState.value.selectedAgeGroup)

    // 2 Yaş seçimi -> 2 seçenekli
    vm.selectAgeGroup(AgeGroup.AGE_2)
    assertEquals(AgeGroup.AGE_2, vm.uiState.value.selectedAgeGroup)
    assertEquals(2, vm.uiState.value.currentQuestion.options.size)

    // 3 Yaş seçimi -> 2 seçenekli
    vm.selectAgeGroup(AgeGroup.AGE_3)
    assertEquals(AgeGroup.AGE_3, vm.uiState.value.selectedAgeGroup)
    assertEquals(2, vm.uiState.value.currentQuestion.options.size)

    // 4 Yaş seçimi -> 3 seçenekli
    vm.selectAgeGroup(AgeGroup.AGE_4)
    assertEquals(AgeGroup.AGE_4, vm.uiState.value.selectedAgeGroup)
    assertEquals(3, vm.uiState.value.currentQuestion.options.size)

    // 5 Yaş seçimi -> 4 seçenekli
    vm.selectAgeGroup(AgeGroup.AGE_5)
    assertEquals(AgeGroup.AGE_5, vm.uiState.value.selectedAgeGroup)
    assertEquals(4, vm.uiState.value.currentQuestion.options.size)

    // Yaş seçimine geri dönüş
    vm.returnToAgeSelection()
    assertNull(vm.uiState.value.selectedAgeGroup)
  }

  @Test
  fun testVoiceGenderSelection() {
    val vm = AnimalGameViewModel()
    assertEquals(VoiceGender.FEMALE, vm.uiState.value.voiceGender)

    vm.setVoiceGender(VoiceGender.MALE)
    assertEquals(VoiceGender.MALE, vm.uiState.value.voiceGender)

    vm.setVoiceGender(VoiceGender.FEMALE)
    assertEquals(VoiceGender.FEMALE, vm.uiState.value.voiceGender)
  }

  @Test
  fun testViewModelQuizFlow() {
    val vm = AnimalGameViewModel()
    vm.selectAgeGroup(AgeGroup.AGE_3)

    val initialState = vm.uiState.value
    assertEquals(0, initialState.starsCount)
    assertEquals(1, initialState.roundNumber)
    assertFalse(initialState.showRewardOverlay)

    // Yanlış cevap testi
    val target = initialState.currentQuestion.targetAnimal
    val wrongDecoy = initialState.currentQuestion.options.first { it.id != target.id }
    val resultWrong = vm.onAnimalSelected(wrongDecoy)
    assertFalse(resultWrong)
    assertTrue(vm.uiState.value.currentQuestion.wrongAttempts.contains(wrongDecoy.id))
    assertFalse(vm.uiState.value.showRewardOverlay)

    // Doğru cevap testi
    val resultCorrect = vm.onAnimalSelected(target)
    assertTrue(resultCorrect)
    assertTrue(vm.uiState.value.currentQuestion.answeredCorrectly)
    assertEquals(target.rewardFruit, vm.uiState.value.currentFruitReward)

    vm.showRewardScreen()
    assertTrue(vm.uiState.value.showRewardOverlay)

    // Ödül tamamlandığında sonraki soruya geçiş
    vm.onRewardCompleted()
    assertFalse(vm.uiState.value.showRewardOverlay)
    assertEquals(1, vm.uiState.value.starsCount)
    assertEquals(2, vm.uiState.value.roundNumber)
  }
}
