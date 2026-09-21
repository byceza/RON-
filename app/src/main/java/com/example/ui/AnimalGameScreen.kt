package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animalapp.ui.components.FruitRewardOverlay
import com.example.animalapp.ui.components.FruitStickerCollectionDialog
import com.example.model.AgeGroup
import com.example.model.LearningCategory
import com.example.model.LearningItem
import com.example.model.VoiceGender
import com.example.util.SoundHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimalGameScreen(
    viewModel: AnimalGameViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val soundHelper = remember { SoundHelper(context) }
    DisposableEffect(Unit) {
        onDispose {
            soundHelper.release()
        }
    }

    val uiState by viewModel.uiState.collectAsState()
    var isQuizActionLocked by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Kullanıcının seçtiği ses tipini (kadın/erkek) ses motoruna senkronize et
    LaunchedEffect(uiState.voiceGender) {
        soundHelper.setVoiceGender(uiState.voiceGender)
    }

    // Reset lock when question changes or overlay closes
    LaunchedEffect(isQuizActionLocked, uiState.showRewardOverlay, uiState.showStickerCollectionDialog) {
        if (isQuizActionLocked && !uiState.showRewardOverlay && !uiState.showStickerCollectionDialog) {
            delay(400)
            isQuizActionLocked = false
        } else if (!uiState.showRewardOverlay && !uiState.showStickerCollectionDialog) {
            isQuizActionLocked = false
        }
    }

    // Yaş seçildikten sonra soru geldiğinde öğretmenin sakin sorusunu seçilen sesle oku
    LaunchedEffect(uiState.currentQuestion.targetAnimal.id, uiState.selectedAgeGroup) {
        if (uiState.selectedAgeGroup != null && !uiState.showRewardOverlay && !uiState.showStickerCollectionDialog) {
            delay(350)
            soundHelper.stopAllAudio()
            soundHelper.speak(uiState.currentQuestion.targetAnimal.questionVoice)
        }
    }

    // Modül tamamlanıp yeni çıkartma açıldığında öğretmen sesli tebrik
    LaunchedEffect(uiState.showStickerCollectionDialog, uiState.newlyUnlockedSticker) {
        if (uiState.showStickerCollectionDialog && uiState.newlyUnlockedSticker != null) {
            delay(400)
            soundHelper.stopAllAudio()
            soundHelper.speak("Tebrikler! Öğrenme modülünü başarıyla tamamladın ve yeni bir meyve çıkartması kazandın!")
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF9E6), // Yumuşak pastel tonlar
                        Color(0xFFFFECE5),
                        Color(0xFFF0F7FF)
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        if (uiState.selectedAgeGroup == null) {
            // 1. ANA SAYFA: SADECE YAŞ SEÇİMİ
            AgeSelectionHomeScreen(
                onAgeSelected = { age ->
                    soundHelper.stopAllAudio()
                    soundHelper.speak("${age.label} dünyası açılıyor!")
                    viewModel.selectAgeGroup(age)
                }
            )
        } else {
            // 2. SEÇİLEN YAŞA ÖZEL EĞİTİCİ OYUN ALANI
            val currentAge = uiState.selectedAgeGroup!!
            AgeWorldView(
                ageGroup = currentAge,
                uiState = uiState,
                isQuizActionLocked = isQuizActionLocked,
                onBackToAgeSelection = {
                    soundHelper.stopAllAudio()
                    viewModel.returnToAgeSelection()
                },
                onSelectCategory = { category ->
                    soundHelper.stopAllAudio()
                    viewModel.selectCategory(category)
                },
                onSelectVoiceGender = { gender ->
                    soundHelper.stopAllAudio()
                    viewModel.setVoiceGender(gender)
                    soundHelper.setVoiceGender(gender)
                    val announce = if (gender == VoiceGender.FEMALE) {
                        "Kadın sesi seçildi."
                    } else {
                        "Erkek sesi seçildi."
                    }
                    soundHelper.speak(announce)
                },
                onOptionSelected = { item ->
                    if (isQuizActionLocked) return@AgeWorldView
                    isQuizActionLocked = true
                    soundHelper.stopAllAudio()
                    val isCorrect = viewModel.onAnimalSelected(item)
                    if (isCorrect) {
                        soundHelper.speak("Aferin, doğru.", flush = true)
                        coroutineScope.launch {
                            delay(650)
                            viewModel.showRewardScreen()
                        }
                    } else {
                        soundHelper.speak("Bir kez daha dene bakalım tatlım.", flush = true)
                    }
                },
                onRepeatQuestionVoice = {
                    soundHelper.stopAllAudio()
                    soundHelper.speak(uiState.currentQuestion.targetAnimal.questionVoice)
                },
                onOpenStickers = {
                    soundHelper.stopAllAudio()
                    soundHelper.speak("İşte kazandığın harika meyve çıkartmaları!")
                    viewModel.openStickerCollection()
                },
                onRestart = {
                    soundHelper.stopAllAudio()
                    viewModel.restartGame()
                }
            )
        }

        // Doğru seçenek sonrasında çıkan renk öğrenimli meyve ödülü
        AnimatedVisibility(
            visible = uiState.showRewardOverlay,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            FruitRewardOverlay(
                fruit = uiState.currentFruitReward,
                onSpeak = { text ->
                    soundHelper.speak(text)
                },
                onBiteSound = {
                    // Efekt sesi kaldırıldı
                },
                onCompleted = {
                    soundHelper.stopAllAudio()
                    viewModel.onRewardCompleted()
                }
            )
        }

        // Modül tamamlandığında açılan Animasyonlu Meyve Çıkartma Koleksiyonu
        AnimatedVisibility(
            visible = uiState.showStickerCollectionDialog,
            enter = fadeIn(animationSpec = tween(250)) + scaleIn(initialScale = 0.85f),
            exit = fadeOut(animationSpec = tween(200)) + scaleOut(targetScale = 0.85f)
        ) {
            FruitStickerCollectionDialog(
                unlockedFruitNames = uiState.unlockedFruitStickers,
                newlyUnlockedFruit = uiState.newlyUnlockedSticker,
                onDismiss = {
                    soundHelper.stopAllAudio()
                    viewModel.closeStickerCollection()
                },
                onFruitSelected = { fruit ->
                    soundHelper.stopAllAudio()
                    soundHelper.speak("${fruit.colorName} ${fruit.fruitName}!")
                }
            )
        }

        // Bütün sayfalarda en altta hafif soluk (faint / watermark) şekilde yer alan yapımcı imzası
        Text(
            text = "Mehmet Atay",
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp,
                color = Color(0xFF2B2D42).copy(alpha = 0.28f)
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 6.dp)
        )
    }
}

/**
 * İlk açılışta gösterilen temiz ve çocuk dostu Yaş Seçim Ana Sayfası
 */
@Composable
private fun AgeSelectionHomeScreen(
    onAgeSelected: (AgeGroup) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Uygulamanın İsmi: Roni'nin Dünyası
        Text(
            text = "Roni'nin Dünyası",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                color = Color(0xFF2B2D42)
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Minik Kaşifler İçin Sevimli Öğrenme Dünyası ✨",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF6C757D),
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Lütfen Yaşını Seç:",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF2B2D42)
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        val ageGroups = listOf(
            AgeGroup.AGE_2,
            AgeGroup.AGE_3,
            AgeGroup.AGE_4,
            AgeGroup.AGE_5
        )

        ageGroups.forEach { ageGroup ->
            AgeSelectionCard(
                ageGroup = ageGroup,
                onClick = { onAgeSelected(ageGroup) }
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun AgeSelectionCard(
    ageGroup: AgeGroup,
    onClick: () -> Unit
) {
    val (primaryColor, cardBg, emojis) = when (ageGroup) {
        AgeGroup.AGE_2 -> Triple(Color(0xFFFF6584), Color(0xFFFFF0F5), "🐱 🐶 🐮 🍎")
        AgeGroup.AGE_3 -> Triple(Color(0xFF4361EE), Color(0xFFEFF3FF), "🦁 🐵 🍓 🔺")
        AgeGroup.AGE_4 -> Triple(Color(0xFF06D6A0), Color(0xFFEDFCF7), "🐘 🐴 🌍 🔢")
        AgeGroup.AGE_5 -> Triple(Color(0xFF7209B7), Color(0xFFF7F0FA), "🐺 🐼 ⚡ 🏆")
    }

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, primaryColor.copy(alpha = 0.35f), RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .testTag("age_card_${ageGroup.age}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(cardBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${ageGroup.age}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = primaryColor
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = ageGroup.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = Color(0xFF2B2D42)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = ageGroup.badge,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryColor
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = ageGroup.description,
                    fontSize = 13.sp,
                    color = Color(0xFF555B6E),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = emojis,
                    fontSize = 18.sp
                )
            }
        }
    }
}

/**
 * Seçilen Yaşa Özel Eğitici Oyun Alanı
 */
@Composable
private fun AgeWorldView(
    ageGroup: AgeGroup,
    uiState: GameUiState,
    isQuizActionLocked: Boolean,
    onBackToAgeSelection: () -> Unit,
    onSelectCategory: (LearningCategory) -> Unit,
    onSelectVoiceGender: (VoiceGender) -> Unit,
    onOptionSelected: (LearningItem) -> Unit,
    onRepeatQuestionVoice: () -> Unit,
    onOpenStickers: () -> Unit,
    onRestart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        // Üst Bar: Yaş Değiştir Butonu, Ses Seçeneği (Kadın / Erkek), Çıkartma Albümü, Yıldız Skoru ve Tekrar
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
            elevation = CardDefaults.cardElevation(3.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Yaş Değiştirme Butonu (Geri Dön)
                FilledTonalButton(
                    onClick = onBackToAgeSelection,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0xFFF1F3F5),
                        contentColor = Color(0xFF2B2D42)
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("btn_change_age")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Yaş Değiştir",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Yaş", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                // Kullanıcı İsteği: Ses Seçeneği Butonu (Kadın 👩‍🏫 / Erkek 👨‍🏫 Seçebilme)
                FilledTonalButton(
                    onClick = {
                        val nextGender = if (uiState.voiceGender == VoiceGender.FEMALE) {
                            VoiceGender.MALE
                        } else {
                            VoiceGender.FEMALE
                        }
                        onSelectVoiceGender(nextGender)
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = if (uiState.voiceGender == VoiceGender.FEMALE) Color(0xFFFFEEF3) else Color(0xFFEBF4FF),
                        contentColor = if (uiState.voiceGender == VoiceGender.FEMALE) Color(0xFFFF3366) else Color(0xFF2563EB)
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("btn_toggle_voice_gender")
                ) {
                    Text(
                        text = if (uiState.voiceGender == VoiceGender.FEMALE) "👩 Ses: Kadın" else "👨 Ses: Erkek",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Çıkartma Koleksiyonu Butonu (Açılan Meyve Çıkartmaları Sayacı)
                Button(
                    onClick = onOpenStickers,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF4D6D).copy(alpha = 0.15f),
                        contentColor = Color(0xFFFF4D6D)
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    modifier = Modifier.testTag("btn_open_sticker_collection")
                ) {
                    Text(text = "🍓", fontSize = 15.sp)
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${uiState.unlockedFruitStickers.size}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                // Yıldız Sayacı ve Soru Tekrarı
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Yıldız",
                        tint = Color(0xFFFFB703),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${uiState.starsCount}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                        color = Color(0xFF2B2D42)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    IconButton(
                        onClick = onRestart,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Yeniden Başlat",
                            tint = Color(0xFF6C757D),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Modül İlerleme Göstergesi (Örn: Modül Hedefi 5 Soru)
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "🎯 Modül İlerlemesi (${uiState.moduleRoundsCompleted}/${uiState.moduleTargetRounds})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2B2D42)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    for (i in 1..uiState.moduleTargetRounds) {
                        val isDone = i <= uiState.moduleRoundsCompleted
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(if (isDone) Color(0xFF06D6A0) else Color(0xFFE9ECEF)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isDone) {
                                Text(text = "✓", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Bölümler (Kategoriler) Seçimi: Tümü, Hayvanlar, Meyveler, Şekiller, Doğa, Sayılar, Semboller
        // Tek sayfada kaydırmasız (non-scrolling) 4 sütun + 3 sütun düzeninde net ve aynı anda görünür
        val categories = listOf(
            LearningCategory.ALL,
            LearningCategory.ANIMALS,
            LearningCategory.FRUITS,
            LearningCategory.SHAPES,
            LearningCategory.NATURE,
            LearningCategory.NUMBERS,
            LearningCategory.SYMBOLS
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // İlk Satır: 4 kategori (Tümü, Hayvanlar, Meyveler, Şekiller)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.take(4).forEach { category ->
                    val isSelected = uiState.selectedCategory == category
                    val (catLabel, catEmoji, catColor) = when (category) {
                        LearningCategory.ALL -> Triple("Tümü", "🎲", Color(0xFFFF6584))
                        LearningCategory.ANIMALS -> Triple("Hayvanlar", "🦁", Color(0xFF4361EE))
                        LearningCategory.FRUITS -> Triple("Meyveler", "🍓", Color(0xFF06D6A0))
                        LearningCategory.SHAPES -> Triple("Şekiller", "🔺", Color(0xFFF77F00))
                        else -> Triple("", "", Color.Gray)
                    }

                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectCategory(category) },
                        label = {
                            Text(
                                text = "$catEmoji $catLabel",
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = catColor,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.90f),
                            labelColor = Color(0xFF2B2D42)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isSelected) catColor else Color.LightGray.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("category_chip_${category.name.lowercase()}")
                    )
                }
            }

            // İkinci Satır: 3 kategori (Doğa & Coğrafya, Sayılar, Semboller)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.drop(4).forEach { category ->
                    val isSelected = uiState.selectedCategory == category
                    val (catLabel, catEmoji, catColor) = when (category) {
                        LearningCategory.NATURE -> Triple("Doğa & Coğrafya", "🌍", Color(0xFF2EC4B6))
                        LearningCategory.NUMBERS -> Triple("Sayılar", "🔢", Color(0xFF7209B7))
                        LearningCategory.SYMBOLS -> Triple("Semboller", "⚡", Color(0xFFFFB703))
                        else -> Triple("", "", Color.Gray)
                    }

                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectCategory(category) },
                        label = {
                            Text(
                                text = "$catEmoji $catLabel",
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = catColor,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.90f),
                            labelColor = Color(0xFF2B2D42)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isSelected) catColor else Color.LightGray.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("category_chip_${category.name.lowercase()}")
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Ana İçerik: Soru ve Görsel Seçenek Kartları
        Box(modifier = Modifier.weight(1f)) {
            AgeQuizView(
                question = uiState.currentQuestion,
                ageGroup = ageGroup,
                isActionLocked = isQuizActionLocked,
                onOptionClicked = onOptionSelected,
                onRepeatVoice = onRepeatQuestionVoice
            )
        }

        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Composable
private fun AgeQuizView(
    question: QuizQuestion,
    ageGroup: AgeGroup,
    isActionLocked: Boolean,
    onOptionClicked: (LearningItem) -> Unit,
    onRepeatVoice: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Soru Kartı
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = question.targetAnimal.questionVoice,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        color = Color(0xFF2B2D42)
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Soruyu Kadın Öğretmen Sesinden Tekrar Dinleme Butonu
                FilledTonalButton(
                    onClick = onRepeatVoice,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0xFFF1F3F5),
                        contentColor = Color(0xFF2B2D42)
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("btn_repeat_question_voice")
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Soruyu Tekrar Dinle",
                        tint = Color(0xFF4361EE),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Soruyu Dinle",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Seçenekler Izgarası
        val columnsCount = if (question.options.size <= 2) 2 else 2

        LazyVerticalGrid(
            columns = GridCells.Fixed(columnsCount),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(question.options, key = { it.id }) { option ->
                val isWrong = question.wrongAttempts.contains(option.id)

                AnimalOptionCard(
                    item = option,
                    isWrong = isWrong,
                    isTarget2Or3Yo = ageGroup.age <= 3,
                    onClick = {
                        if (!isActionLocked && !isWrong) {
                            onOptionClicked(option)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun AnimalOptionCard(
    item: LearningItem,
    isWrong: Boolean,
    isTarget2Or3Yo: Boolean,
    onClick: () -> Unit
) {
    val cardHeight = if (isTarget2Or3Yo) 180.dp else 145.dp
    val emojiSize = if (isTarget2Or3Yo) 80.sp else 62.sp

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isWrong) Color(0xFFFFEBEE) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isWrong) 1.dp else 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .border(
                width = if (isWrong) 2.dp else 2.5.dp,
                color = if (isWrong) Color(0xFFEF5350).copy(alpha = 0.6f) else item.accentColor.copy(alpha = 0.4f),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(enabled = !isWrong, onClick = onClick)
            .testTag("option_card_${item.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.emoji,
                fontSize = emojiSize,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = if (isTarget2Or3Yo) 20.sp else 17.sp,
                    color = if (isWrong) Color(0xFFD32F2F) else Color(0xFF2B2D42)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
