package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.draw.scale
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
import com.example.model.AgeGroup
import com.example.model.AnimalRepository
import com.example.model.LearningCategory
import com.example.model.LearningItem
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

    // Reset lock when question changes or overlay closes
    LaunchedEffect(isQuizActionLocked, uiState.showRewardOverlay) {
        if (isQuizActionLocked && !uiState.showRewardOverlay) {
            delay(400)
            isQuizActionLocked = false
        } else if (!uiState.showRewardOverlay) {
            isQuizActionLocked = false
        }
    }

    // Yaş seçildikten sonra soru geldiğinde öğretmenin sakin sorusunu oku
    LaunchedEffect(uiState.currentQuestion.targetAnimal.id, uiState.selectedAgeGroup, uiState.currentTab) {
        if (uiState.selectedAgeGroup != null && uiState.currentTab == GameTab.QUIZ && !uiState.showRewardOverlay) {
            delay(350)
            soundHelper.stopAllAudio()
            soundHelper.speak(uiState.currentQuestion.targetAnimal.questionVoice)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF9E6), // Yumuşak ve sakin çocuk dostu pastel tonlar
                        Color(0xFFFFECE5),
                        Color(0xFFF0F7FF)
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        if (uiState.selectedAgeGroup == null) {
            // 1. ANA SAYFA: SADECE YAŞ SEÇİMİ (Meyve, hayvan vb. kategoriler yok)
            AgeSelectionHomeScreen(
                onAgeSelected = { age ->
                    soundHelper.stopAllAudio()
                    soundHelper.playPopSound()
                    soundHelper.speak("${age.label} dünyası açılıyor!")
                    viewModel.selectAgeGroup(age)
                }
            )
        } else {
            // 2. SEÇİLEN YAŞA ÖZEL DÜNYA VE BÖLÜMLER
            val currentAge = uiState.selectedAgeGroup!!
            AgeWorldView(
                ageGroup = currentAge,
                uiState = uiState,
                soundHelper = soundHelper,
                isQuizActionLocked = isQuizActionLocked,
                onBackToAgeSelection = {
                    soundHelper.stopAllAudio()
                    soundHelper.playPopSound()
                    viewModel.returnToAgeSelection()
                },
                onSelectTab = { tab ->
                    soundHelper.stopAllAudio()
                    soundHelper.playPopSound()
                    viewModel.selectTab(tab)
                },
                onSelectCategory = { category ->
                    soundHelper.stopAllAudio()
                    soundHelper.playPopSound()
                    viewModel.selectCategory(category)
                },
                onOptionSelected = { item ->
                    if (isQuizActionLocked) return@AgeWorldView
                    isQuizActionLocked = true
                    soundHelper.stopAllAudio()
                    val isCorrect = viewModel.onAnimalSelected(item)
                    if (isCorrect) {
                        // Doğru cevap kuralı: Önce gerçek hayvan sesi çalsın, sonra gerçek insan sesiyle "Aferin, doğru." densin.
                        soundHelper.playItemSound(item.soundType) {
                            coroutineScope.launch {
                                soundHelper.speak("Aferin, doğru.", flush = false)
                                delay(650)
                                viewModel.showRewardScreen()
                            }
                        }
                    } else {
                        soundHelper.playWrongSound()
                        soundHelper.speak("Bir kez daha dene bakalım tatlım.", flush = false)
                    }
                },
                onPlayItemSound = { item ->
                    soundHelper.stopAllAudio()
                    soundHelper.playItemSound(item.soundType)
                    if (item.category != LearningCategory.ANIMALS) {
                        // Hayvanlar dışındaki kategorilerde sakin Türkçe isim okunuşu
                        soundHelper.speak(item.name, flush = false)
                    }
                },
                onRepeatQuestionVoice = {
                    soundHelper.stopAllAudio()
                    soundHelper.speak(uiState.currentQuestion.targetAnimal.questionVoice)
                },
                onRestart = {
                    soundHelper.stopAllAudio()
                    soundHelper.playPopSound()
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
                    soundHelper.stopAllAudio()
                    soundHelper.playBiteSound()
                },
                onCompleted = {
                    soundHelper.stopAllAudio()
                    viewModel.onRewardCompleted()
                }
            )
        }
    }
}

/**
 * İlk açılışta gösterilen temiz ve çocuk dostu Yaş Seçim Ana Sayfası
 * Kategori (meyve, hayvan) barındırmaz, sadece yaş seçimi yaptırır.
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
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Neşeli Başlık Rozeti
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.9f))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "🎈 Neşeli Kaşif Dünyası",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFFFF6584)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Kaşif Kaç Yaşında?",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = Color(0xFF2B2D42)
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Oynamak istediğin yaşı seç bakalım:",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                color = Color(0xFF6C757D)
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Yaş Grupları Listesi (2 Yaş, 3 Yaş, 4 Yaş, 5 Yaş)
        val ageGroups = listOf(
            AgeGroup.AGE_2,
            AgeGroup.AGE_3,
            AgeGroup.AGE_4,
            AgeGroup.AGE_5
        )

        ageGroups.forEach { age ->
            AgeCardItem(
                ageGroup = age,
                onClick = { onAgeSelected(age) }
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun AgeCardItem(
    ageGroup: AgeGroup,
    onClick: () -> Unit
) {
    val (primaryColor, bgGradient, emojis) = when (ageGroup) {
        AgeGroup.AGE_2 -> Triple(
            Color(0xFFFF6584),
            listOf(Color(0xFFFFF0F5), Color(0xFFFFE3EC)),
            "🐱 🍎 🔴 ☀️"
        )
        AgeGroup.AGE_3 -> Triple(
            Color(0xFF4361EE),
            listOf(Color(0xFFEFF3FF), Color(0xFFDEE7FF)),
            "🐑 🍌 🔺 🌈"
        )
        AgeGroup.AGE_4 -> Triple(
            Color(0xFF06D6A0),
            listOf(Color(0xFFEDFCF7), Color(0xFFD4F9EE)),
            "🦁 🍓 🔲 🌍"
        )
        AgeGroup.AGE_5 -> Triple(
            Color(0xFF7209B7),
            listOf(Color(0xFFF7F0FA), Color(0xFFEFE0F7)),
            "🐺 🍉 🔢 ⚡"
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .testTag("age_card_${ageGroup.age}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(bgGradient))
                .border(2.dp, primaryColor.copy(alpha = 0.35f), RoundedCornerShape(24.dp))
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Yaş Rozeti
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(primaryColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${ageGroup.age}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = ageGroup.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2B2D42)
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
}

/**
 * Seçilen Yaşa Özel Oyun Alanı
 */
@Composable
private fun AgeWorldView(
    ageGroup: AgeGroup,
    uiState: GameUiState,
    soundHelper: SoundHelper,
    isQuizActionLocked: Boolean,
    onBackToAgeSelection: () -> Unit,
    onSelectTab: (GameTab) -> Unit,
    onSelectCategory: (LearningCategory) -> Unit,
    onOptionSelected: (LearningItem) -> Unit,
    onPlayItemSound: (LearningItem) -> Unit,
    onRepeatQuestionVoice: () -> Unit,
    onRestart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        // Üst Bar: Yaş Değiştir Butonu, Yaş Başlığı, Yıldız Skoru ve Tekrar
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
            elevation = CardDefaults.cardElevation(3.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
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
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("btn_change_age")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Yaş Değiştir",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Yaş Değiştir", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                // Yaş Rozeti
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFFF0F5))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = ageGroup.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFFFF6584)
                    )
                }

                // Yıldız Sayacı ve Soru Tekrarı
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Yıldız",
                        tint = Color(0xFFFFB703),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${uiState.starsCount}",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = Color(0xFF2B2D42)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = onRestart,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Yeniden Başlat",
                            tint = Color(0xFF6C757D),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bölümler (Kategoriler) Seçim Çubuğu: Tümü, Hayvanlar, Meyveler, Şekiller, Doğa, Sayılar, Semboller
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val categories = listOf(
                LearningCategory.ALL,
                LearningCategory.ANIMALS,
                LearningCategory.FRUITS,
                LearningCategory.SHAPES,
                LearningCategory.NATURE,
                LearningCategory.NUMBERS,
                LearningCategory.SYMBOLS
            )

            items(categories) { category ->
                val isSelected = uiState.selectedCategory == category
                val (catLabel, catEmoji, catColor) = when (category) {
                    LearningCategory.ALL -> Triple("Tümü", "🎲", Color(0xFFFF6584))
                    LearningCategory.ANIMALS -> Triple("Hayvanlar", "🦁", Color(0xFF4361EE))
                    LearningCategory.FRUITS -> Triple("Meyveler", "🍓", Color(0xFF06D6A0))
                    LearningCategory.SHAPES -> Triple("Şekiller", "🔺", Color(0xFFF77F00))
                    LearningCategory.NATURE -> Triple("Doğa & Coğrafya", "🌍", Color(0xFF2EC4B6))
                    LearningCategory.NUMBERS -> Triple("Sayılar", "🔢", Color(0xFF7209B7))
                    LearningCategory.SYMBOLS -> Triple("Semboller", "⚡", Color(0xFFFFB703))
                }

                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectCategory(category) },
                    label = {
                        Text(
                            text = "$catEmoji $catLabel",
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = catColor,
                        selectedLabelColor = Color.White,
                        containerColor = Color.White.copy(alpha = 0.85f),
                        labelColor = Color(0xFF2B2D42)
                    ),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) catColor else Color.LightGray.copy(alpha = 0.4f)
                    ),
                    modifier = Modifier.testTag("category_chip_${category.name.lowercase()}")
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Mod Seçici: Soru & Cevap vs Gerçek Sesler & Keşfet
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.85f))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val isQuiz = uiState.currentTab == GameTab.QUIZ
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isQuiz) Color(0xFFFF6584) else Color.Transparent)
                    .clickable { onSelectTab(GameTab.QUIZ) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎯 Soru & Bulmaca",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (isQuiz) Color.White else Color(0xFF495057)
                )
            }

            val isSoundboard = uiState.currentTab == GameTab.SOUNDBOARD
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSoundboard) Color(0xFF4361EE) else Color.Transparent)
                    .clickable { onSelectTab(GameTab.SOUNDBOARD) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔊 Keşfet & Dinle",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (isSoundboard) Color.White else Color(0xFF495057)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Ana İçerik
        Box(modifier = Modifier.weight(1f)) {
            when (uiState.currentTab) {
                GameTab.QUIZ -> {
                    AgeQuizView(
                        question = uiState.currentQuestion,
                        ageGroup = ageGroup,
                        isActionLocked = isQuizActionLocked,
                        onOptionClicked = onOptionSelected,
                        onPlayItemSound = {
                            onPlayItemSound(uiState.currentQuestion.targetAnimal)
                        },
                        onRepeatVoice = onRepeatQuestionVoice
                    )
                }

                GameTab.SOUNDBOARD -> {
                    val currentItems = remember(ageGroup, uiState.selectedCategory) {
                        AnimalRepository.getItemsForAge(ageGroup, uiState.selectedCategory)
                    }
                    AgeSoundboardView(
                        items = currentItems,
                        teacherAdvice = ageGroup.teacherAdvice,
                        onItemClicked = onPlayItemSound
                    )
                }
            }
        }
    }
}

@Composable
private fun AgeQuizView(
    question: QuizQuestion,
    ageGroup: AgeGroup,
    isActionLocked: Boolean,
    onOptionClicked: (LearningItem) -> Unit,
    onPlayItemSound: () -> Unit,
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val soundButtonLabel = when (question.targetAnimal.category) {
                        LearningCategory.ANIMALS -> "Gerçek Sesi Dinle 🔊"
                        LearningCategory.NATURE -> "Doğa Sesi 🌿"
                        LearningCategory.FRUITS -> "Kıtır Sesi 🍎"
                        LearningCategory.SHAPES -> "Şekil Sesi 🔴"
                        LearningCategory.NUMBERS -> "Sayı Sesi 🔢"
                        LearningCategory.SYMBOLS -> "Sesi Dinle ⚡"
                        LearningCategory.ALL -> "Sesi Dinle 🔊"
                    }

                    // Gerçek Ses Dinleme Butonu
                    Button(
                        onClick = onPlayItemSound,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4361EE)),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                        modifier = Modifier.testTag("btn_listen_item_sound")
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = soundButtonLabel,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = soundButtonLabel,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    // Soruyu Tekrar Dinleme Butonu
                    FilledTonalIconButton(
                        onClick = onRepeatVoice,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Soruyu Tekrar Dinle",
                            tint = Color(0xFF495057),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Seçenekler Izgarası (2 Yaş ve 3 Yaş için 2 dev seçenek, 4 Yaş için 3 seçenek, 5 Yaş için 4 seçenek)
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

@Composable
private fun AgeSoundboardView(
    items: List<LearningItem>,
    teacherAdvice: String,
    onItemClicked: (LearningItem) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Pedagojik İpucu Başlığı
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "💡", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = teacherAdvice,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = Color(0xFF495057),
                        lineHeight = 16.sp
                    )
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(items, key = { it.id }) { item ->
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .clickable { onItemClicked(item) }
                        .testTag("soundboard_${item.id}")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = item.emoji, fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF2B2D42),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
