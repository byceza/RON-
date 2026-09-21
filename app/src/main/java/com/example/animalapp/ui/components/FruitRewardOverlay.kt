package com.example.animalapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class RewardFruit(
    val fruitName: String,
    val colorName: String,
    val emoji: String,
    val voicePrompt: String,
    val color: Color
) {
    STRAWBERRY("Çilek", "Kırmızı", "🍓", "Hadi şu tatlı kırmızı çileği ısır bakalım.", Color(0xFFFF4D6D)),
    APPLE("Elma", "Kırmızı", "🍎", "Hadi şu tatlı kırmızı elmayı ısır bakalım.", Color(0xFFE63946)),
    BANANA("Muz", "Sarı", "🍌", "Hadi şu sarı muzu ye bakalım.", Color(0xFFFFB703)),
    ORANGE("Portakal", "Turuncu", "🍊", "Hadi şu tatlı turuncu portakalı ısır bakalım.", Color(0xFFF77F00)),
    MANDARIN("Mandalina", "Turuncu", "🍊", "Hadi şu tatlı turuncu mandalinayı ye bakalım.", Color(0xFFFB8500)),
    WATERMELON("Karpuz", "Kırmızı", "🍉", "Hadi şu sulu kırmızı karpuzu ısır bakalım.", Color(0xFF06D6A0)),
    BLUEBERRY("Yaban Mersini", "Mavi", "🫐", "Hadi şu minik mavi yaban mersinini ye bakalım.", Color(0xFF3A86FF)),
    BLACKBERRY("Böğürtlen", "Mor", "🫐", "Hadi şu tatlı mor böğürtleni ye bakalım.", Color(0xFF7209B7)),
    MANGO("Mango", "Sarı", "🥭", "Hadi şu tatlı sarı mangoyu ye bakalım.", Color(0xFFFF9E00)),
    AVOCADO("Avokado", "Yeşil", "🥑", "Hadi şu taze yeşil avokadoyu ye bakalım.", Color(0xFF588157)),
    CHERRY("Kiraz", "Kırmızı", "🍒", "Hadi şu tatlı kırmızı kirazı ye bakalım.", Color(0xFFD90429)),
    GRAPES("Üzüm", "Mor", "🍇", "Hadi şu tatlı mor üzümü ye bakalım.", Color(0xFF8338EC)),
    PINEAPPLE("Ananas", "Sarı", "🍍", "Hadi şu tatlı sarı ananası ısır bakalım.", Color(0xFFFFBE0B)),
    KIWI("Kivi", "Yeşil", "🥝", "Hadi şu taze yeşil kiviyi ısır bakalım.", Color(0xFF8CB369))
}

@Composable
fun FruitRewardOverlay(
    fruit: RewardFruit = RewardFruit.APPLE,
    onSpeak: (String) -> Unit,
    onBiteSound: () -> Unit,
    onCompleted: () -> Unit
) {
    var isBitten by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isBitten) 0.85f else 1.05f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "FruitScale"
    )

    // Doğru cevap seslendirmesi ("Aferin, doğru.") tamamlandıktan sonra meyve ödülü yönergesini seslendir
    LaunchedEffect(fruit) {
        delay(900)
        onSpeak(fruit.voicePrompt)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xB3000000))
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        event.changes.forEach { it.consume() }
                    }
                }
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(28.dp)
        ) {
            // Renk ve Meyve Öğrenim Kartı
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp)
                ) {
                    // Renk Rozeti
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(fruit.color.copy(alpha = 0.15f))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${fruit.colorName} Renk",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = fruit.color
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = fruit.voicePrompt,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF2B2D42)
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Meyve Butonu (Isır/Ye)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        enabled = !isBitten
                    ) {
                        if (!isBitten) {
                            isBitten = true
                            onBiteSound() // Sadece organik kıtır ısırık sesi, sesli tekrar yok
                        }
                    }
                    .testTag("clickable_fruit_target")
            ) {
                Text(
                    text = if (isBitten) "${fruit.emoji}✨" else fruit.emoji,
                    fontSize = 110.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = if (isBitten) "Afiyet olsun! 😋" else "Meyveye dokun ve ye! 👆",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            // Isırık alındıktan sonra sessizce ve hızla bir sonraki soruya geç
            if (isBitten) {
                LaunchedEffect(Unit) {
                    delay(550)
                    onCompleted()
                }
            }
        }
    }
}
