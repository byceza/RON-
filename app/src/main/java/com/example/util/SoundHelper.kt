package com.example.util

import android.content.Context
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import com.example.model.VoiceGender
import java.util.Locale

class SoundHelper(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private var currentVoiceGender: VoiceGender = VoiceGender.FEMALE
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val trLocale = Locale("tr", "TR")
            val langResult = tts?.setLanguage(trLocale)
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w("SoundHelper", "Turkish TTS missing, using default locale")
                tts?.setLanguage(Locale.getDefault())
            }

            applyVoiceConfiguration(currentVoiceGender)
            isTtsReady = true
        } else {
            Log.e("SoundHelper", "TTS Initialization failed")
        }
    }

    /**
     * Kullanıcı isteği: Kadın veya erkek sesi seçimi
     */
    fun setVoiceGender(gender: VoiceGender) {
        currentVoiceGender = gender
        if (isTtsReady) {
            applyVoiceConfiguration(gender)
        }
    }

    private fun applyVoiceConfiguration(gender: VoiceGender) {
        try {
            val trLocale = Locale("tr", "TR")
            val voices: Set<Voice>? = tts?.voices
            if (!voices.isNullOrEmpty()) {
                val trVoices = voices.filter { it.locale.language.equals("tr", ignoreCase = true) }
                val candidateList = if (trVoices.isNotEmpty()) trVoices else voices.toList()

                if (gender == VoiceGender.MALE) {
                    // Erkek sesi seçimi
                    val explicitMaleVoice = candidateList.firstOrNull { voice ->
                        val n = voice.name.lowercase()
                        n.contains("male") || n.contains("erkek") || n.contains("cma") ||
                                n.contains("ama") || n.contains("gma") || n.contains("bma") ||
                                n.contains("-m-") || n.endsWith("-m") || n.contains("tr-tr-x-cma")
                    } ?: candidateList.firstOrNull()

                    if (explicitMaleVoice != null) {
                        tts?.voice = explicitMaleVoice
                    }
                    // Erkek sesi için doğal ton ve hız
                    tts?.setPitch(0.95f)
                    tts?.setSpeechRate(0.92f)
                    Log.d("SoundHelper", "Selected male voice: ${tts?.voice?.name}")
                } else {
                    // Kadın sesi seçimi
                    val nonMaleVoices = candidateList.filter { voice ->
                        val n = voice.name.lowercase()
                        !n.contains("male") && !n.contains("erkek") && !n.contains("cma") &&
                                !n.contains("ama") && !n.contains("gma") && !n.contains("bma") &&
                                !n.contains("-m-") && !n.endsWith("-m")
                    }

                    val explicitFemaleVoice = nonMaleVoices.firstOrNull { voice ->
                        val n = voice.name.lowercase()
                        n.contains("female") || n.contains("fem") || n.contains("dfc") ||
                                n.contains("efc") || n.contains("bayan") || n.contains("kadin") ||
                                n.contains("-f-") || n.endsWith("-f") || n.contains("tr-tr-x-dfc") ||
                                n.contains("tr-tr-x-efc")
                    } ?: nonMaleVoices.firstOrNull() ?: candidateList.first()

                    tts?.voice = explicitFemaleVoice
                    // Kadın sesi için net perde (pitch: 1.40f) ve sevecen anaokulu hızı (0.90f)
                    tts?.setPitch(1.40f)
                    tts?.setSpeechRate(0.90f)
                    Log.d("SoundHelper", "Selected female voice: ${explicitFemaleVoice.name}")
                }
            } else {
                // Voices listesi alınamıyorsa pitch/rate ile cinsiyeti belirle
                if (gender == VoiceGender.MALE) {
                    tts?.setPitch(0.95f)
                    tts?.setSpeechRate(0.92f)
                } else {
                    tts?.setPitch(1.40f)
                    tts?.setSpeechRate(0.90f)
                }
            }
        } catch (e: Exception) {
            Log.w("SoundHelper", "Voice selection fallback: ${e.message}")
            if (gender == VoiceGender.MALE) {
                tts?.setPitch(0.95f)
                tts?.setSpeechRate(0.92f)
            } else {
                tts?.setPitch(1.40f)
                tts?.setSpeechRate(0.90f)
            }
        }
    }

    /**
     * Tüm konuşmaları hemen durdurur
     */
    fun stopAllAudio() {
        try {
            tts?.stop()
        } catch (_: Exception) {}
    }

    /**
     * Seçilen ses tipiyle (kadın veya erkek) Türkçe metin seslendirmesi
     */
    fun speak(text: String, flush: Boolean = true) {
        if (!isTtsReady || tts == null) return
        try {
            if (currentVoiceGender == VoiceGender.MALE) {
                tts?.setPitch(0.95f)
                tts?.setSpeechRate(0.92f)
            } else {
                tts?.setPitch(1.40f)
                tts?.setSpeechRate(0.90f)
            }

            if (flush) {
                tts?.stop()
            }
            val queueMode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
            tts?.speak(text, queueMode, null, "TeacherVoice_${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Log.e("SoundHelper", "Error speaking text: $text", e)
        }
    }

    // Kullanıcı talebi: Hayvan sesleri, efekt sesleri, geçiş sesleri tamamen kaldırıldı.
    fun playItemSound(soundType: String, onCompletion: (() -> Unit)? = null) {
        onCompletion?.invoke()
    }

    fun playRealAnimalSound(soundType: String, onCompletion: (() -> Unit)? = null) {
        onCompletion?.invoke()
    }

    fun playBiteSound() {
        // Efekt sesi kaldırıldı
    }

    fun playSuccessSound() {
        // Efekt sesi kaldırıldı
    }

    fun playPopSound() {
        // Efekt sesi kaldırıldı
    }

    fun playWrongSound() {
        // Efekt sesi kaldırıldı
    }

    fun release() {
        try {
            stopAllAudio()
            tts?.shutdown()
            tts = null
        } catch (_: Exception) {}
    }
}
