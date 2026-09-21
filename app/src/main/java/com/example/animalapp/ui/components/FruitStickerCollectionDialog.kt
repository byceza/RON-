package com.example.animalapp.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FruitStickerCollectionDialog(
    unlockedFruitNames: Set<String>,
    newlyUnlockedFruit: RewardFruit?,
    onDismiss: () -> Unit,
    onFruitSelected: (RewardFruit) -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "StickerGlow")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )

    val shimmerRotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.72f))
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
        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(16.dp),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.86f)
                .testTag("fruit_sticker_collection_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Başlık Alanı ve Kapatma Butonu
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🍓", fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Meyve Çıkartma Albümü",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 19.sp,
                                    color = Color(0xFF2B2D42)
                                )
                            )
                            Text(
                                text = "${unlockedFruitNames.size} / ${RewardFruit.values().size} Çıkartma Açıldı",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF06D6A0)
                                )
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFFF1F3F5))
                            .testTag("btn_close_stickers")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Kapat",
                            tint = Color(0xFF495057)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Yeni Açılan Çıkartma Vurgusu (Modül tamamlandığında)
                if (newlyUnlockedFruit != null) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = newlyUnlockedFruit.color.copy(alpha = 0.12f)
                        ),
                        border = BorderStroke(2.dp, newlyUnlockedFruit.color.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(pulseScale)
                            .padding(bottom = 14.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = newlyUnlockedFruit.emoji,
                                fontSize = 42.sp,
                                modifier = Modifier.rotate(shimmerRotation)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Tebrikler! Yeni Çıkartma 🎉",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = newlyUnlockedFruit.color
                                    )
                                }
                                Text(
                                    text = "${newlyUnlockedFruit.fruitName} (${newlyUnlockedFruit.colorName}) çıkartması albümüne eklendi!",
                                    fontSize = 12.sp,
                                    color = Color(0xFF2B2D42),
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }

                // Çıkartma Koleksiyonu Izgarası
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(RewardFruit.values().toList(), key = { it.name }) { fruit ->
                        val isUnlocked = unlockedFruitNames.contains(fruit.name)
                        val isJustUnlocked = newlyUnlockedFruit == fruit

                        FruitStickerItem(
                            fruit = fruit,
                            isUnlocked = isUnlocked,
                            isJustUnlocked = isJustUnlocked,
                            pulseScale = pulseScale,
                            onClick = {
                                if (isUnlocked) {
                                    onFruitSelected(fruit)
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tamamla ve Devam Et Butonu
                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6584)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("btn_continue_playing")
                ) {
                    Text(
                        text = "Harika! Öğrenmeye Devam Et 🚀",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun FruitStickerItem(
    fruit: RewardFruit,
    isUnlocked: Boolean,
    isJustUnlocked: Boolean,
    pulseScale: Float,
    onClick: () -> Unit
) {
    val scale = if (isJustUnlocked) pulseScale else 1f

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) fruit.color.copy(alpha = 0.14f) else Color(0xFFF1F3F5)
        ),
        elevation = CardDefaults.cardElevation(if (isUnlocked) 4.dp else 0.dp),
        border = BorderStroke(
            width = if (isJustUnlocked) 2.5.dp else if (isUnlocked) 1.5.dp else 1.dp,
            color = if (isJustUnlocked) fruit.color else if (isUnlocked) fruit.color.copy(alpha = 0.4f) else Color.LightGray.copy(alpha = 0.4f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .scale(scale)
            .clip(RoundedCornerShape(20.dp))
            .clickable(enabled = isUnlocked, onClick = onClick)
            .testTag("sticker_${fruit.name.lowercase()}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isUnlocked) {
                Text(
                    text = fruit.emoji,
                    fontSize = 42.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = fruit.fruitName,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFF2B2D42)
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = fruit.colorName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = fruit.color
                    )
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Kilitli",
                        tint = Color(0xFFADB5BD),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "???",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFFADB5BD)
                    )
                )
            }
        }
    }
}
