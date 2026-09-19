package com.example.util

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Locale

class SoundHelper(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    private val audioScope = CoroutineScope(Dispatchers.Main)
    private var currentPlayJob: Job? = null
    private var activeMediaPlayer: MediaPlayer? = null
    private val mediaLock = Any()

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

            // Calibrate strictly to a natural, calm, warm female kindergarten teacher voice
            setupNaturalFemaleTeacherVoice(trLocale)

            // Warm, caring kindergarten teacher pacing and sweet female pitch
            tts?.setSpeechRate(0.88f)
            tts?.setPitch(1.26f)
            isTtsReady = true
        } else {
            Log.e("SoundHelper", "TTS Initialization failed")
        }
    }

    /**
     * Finds and selects a high quality, natural female voice for Turkish kindergarten teaching
     */
    private fun setupNaturalFemaleTeacherVoice(locale: Locale) {
        try {
            val voices: Set<Voice>? = tts?.voices
            if (!voices.isNullOrEmpty()) {
                val trVoices = voices.filter { it.locale.language.equals("tr", ignoreCase = true) }
                if (trVoices.isNotEmpty()) {
                    // Filter out any voice marked as male or containing male voice identifiers
                    val nonMaleVoices = trVoices.filter { voice ->
                        val n = voice.name.lowercase()
                        !n.contains("male") && !n.contains("erkek") && !n.contains("cma") &&
                                !n.contains("ama") && !n.contains("gma") && !n.contains("bma") &&
                                !n.contains("-m-") && !n.endsWith("-m")
                    }

                    // Look for explicitly female voice markers
                    val explicitFemaleVoice = nonMaleVoices.firstOrNull { voice ->
                        val n = voice.name.lowercase()
                        n.contains("female") || n.contains("fem") || n.contains("dfc") ||
                                n.contains("efc") || n.contains("bayan") || n.contains("kadin") ||
                                n.contains("-f-") || n.endsWith("-f")
                    } ?: nonMaleVoices.firstOrNull() ?: trVoices.first()

                    tts?.voice = explicitFemaleVoice
                    Log.d("SoundHelper", "Selected female teacher voice: ${explicitFemaleVoice.name}")
                }
            }
        } catch (e: Exception) {
            Log.w("SoundHelper", "Voice selection fallback: ${e.message}")
        }
    }

    /**
     * Stops all speech and playing sounds immediately to avoid audio overlapping
     */
    fun stopAllAudio() {
        try {
            tts?.stop()
            stopActivePlayer()
        } catch (_: Exception) {}
    }

    /**
     * Safely resets and releases the active MediaPlayer instance
     */
    private fun stopActivePlayer() {
        synchronized(mediaLock) {
            currentPlayJob?.cancel()
            currentPlayJob = null
            try {
                activeMediaPlayer?.let { player ->
                    try {
                        player.setOnCompletionListener(null)
                        player.setOnErrorListener(null)
                        if (player.isPlaying) {
                            player.stop()
                        }
                    } catch (_: Exception) {}
                    try {
                        player.reset()
                    } catch (_: Exception) {}
                    try {
                        player.release()
                    } catch (_: Exception) {}
                }
            } catch (_: Exception) {} finally {
                activeMediaPlayer = null
            }
        }
    }

    fun speak(text: String, flush: Boolean = true) {
        if (!isTtsReady || tts == null) return
        try {
            if (flush) {
                tts?.stop()
            }
            val queueMode = if (flush) TextToSpeech.QUEUE_FLUSH else TextToSpeech.QUEUE_ADD
            tts?.speak(text, queueMode, null, "TeacherVoice_${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Log.e("SoundHelper", "Error speaking text: $text", e)
        }
    }

    private fun getRawResId(resName: String): Int {
        return try {
            context.resources.getIdentifier(resName, "raw", context.packageName)
        } catch (_: Exception) {
            0
        }
    }

    /**
     * Plays authentic real sound according to item soundType using AnimalSoundRegistry.
     * Guaranteed safe: Never overlaps with previous sounds, and falls back gracefully
     * without crashing if a sound file is missing.
     * Optional onCompletion callback allows chaining human voice feedback right after animal sound completes.
     */
    fun playItemSound(soundType: String, onCompletion: (() -> Unit)? = null) {
        vibrate(durationMs = 45, amplitude = 130)
        val soundResName = AnimalSoundRegistry.getResourceName(soundType)
        val resId = getRawResId(soundResName)
        if (resId != 0) {
            playRawSound(resId, onCompletion)
        } else {
            val fallbackId = getRawResId("sfx_pop")
            if (fallbackId != 0) {
                playRawSound(fallbackId, onCompletion)
            } else {
                onCompletion?.invoke()
            }
        }
    }

    fun playRealAnimalSound(soundType: String, onCompletion: (() -> Unit)? = null) {
        playItemSound(soundType, onCompletion)
    }

    /**
     * Soft, calm, modern premium fruit bite sound
     */
    fun playBiteSound() {
        vibrate(durationMs = 35, amplitude = 140)
        val resId = getRawResId("sfx_bite")
        if (resId != 0) {
            playRawSound(resId)
        }
    }

    /**
     * Peaceful, celestial chime for victories / correct answers
     */
    fun playSuccessSound() {
        vibrateSuccess()
        val resId = getRawResId("sfx_success")
        if (resId != 0) {
            playRawSound(resId)
        }
    }

    /**
     * Soft, modern wooden droplet/pop for UI touches
     */
    fun playPopSound() {
        vibrate(durationMs = 20, amplitude = 90)
        val resId = getRawResId("sfx_pop")
        if (resId != 0) {
            playRawSound(resId)
        }
    }

    /**
     * Ultra-gentle, encouraging warm marimba chime for wrong attempts
     */
    fun playWrongSound() {
        vibrate(durationMs = 60, amplitude = 100)
        val resId = getRawResId("sfx_wrong")
        if (resId != 0) {
            playRawSound(resId)
        }
    }

    /**
     * Core audio player pipeline:
     * 1. Safely releases any previously playing player.
     * 2. Sets AudioAttributes strictly BEFORE prepare().
     * 3. Prevents calling setAudioAttributes after MediaPlayer enters state 8 (Prepared).
     * 4. Properly releases native resources upon completion or error.
     * 5. Catches all I/O, state and resource exceptions to ensure zero crash.
     */
    private fun playRawSound(resId: Int, onCompletion: (() -> Unit)? = null) {
        synchronized(mediaLock) {
            stopActivePlayer()

            currentPlayJob = audioScope.launch {
                var afd: AssetFileDescriptor? = null
                var player: MediaPlayer? = null
                try {
                    afd = try {
                        context.resources.openRawResourceFd(resId)
                    } catch (e: Exception) {
                        Log.w("SoundHelper", "Resource $resId not found in raw, skipping sound.", e)
                        null
                    }

                    if (afd == null) {
                        onCompletion?.invoke()
                        return@launch
                    }

                    val newPlayer = MediaPlayer()
                    player = newPlayer

                    // CRITICAL: AudioAttributes MUST be set in Idle / Initialized state, BEFORE prepare()
                    newPlayer.setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .build()
                    )

                    newPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                    afd.close()
                    afd = null

                    // Prepared state (state 8) is entered only here. Never call setAudioAttributes after this.
                    newPlayer.prepare()

                    newPlayer.setOnCompletionListener { mp ->
                        synchronized(mediaLock) {
                            try {
                                mp.reset()
                                mp.release()
                            } catch (_: Exception) {}
                            if (activeMediaPlayer == mp) {
                                activeMediaPlayer = null
                            }
                        }
                        onCompletion?.invoke()
                    }

                    newPlayer.setOnErrorListener { mp, what, extra ->
                        Log.w("SoundHelper", "MediaPlayer error: what=$what, extra=$extra")
                        synchronized(mediaLock) {
                            try {
                                mp.reset()
                                mp.release()
                            } catch (_: Exception) {}
                            if (activeMediaPlayer == mp) {
                                activeMediaPlayer = null
                            }
                        }
                        onCompletion?.invoke()
                        true // Handled gracefully without crash
                    }

                    synchronized(mediaLock) {
                        activeMediaPlayer = newPlayer
                    }

                    newPlayer.start()
                } catch (e: Exception) {
                    Log.e("SoundHelper", "Safe playback handling for res $resId: ${e.message}")
                    try {
                        player?.reset()
                        player?.release()
                    } catch (_: Exception) {}
                    synchronized(mediaLock) {
                        if (activeMediaPlayer == player) {
                            activeMediaPlayer = null
                        }
                    }
                    onCompletion?.invoke()
                } finally {
                    try {
                        afd?.close()
                    } catch (_: Exception) {}
                }
            }
        }
    }

    private fun vibrate(durationMs: Long, amplitude: Int = 180) {
        try {
            if (vibrator?.hasVibrator() == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(durationMs, amplitude.coerceIn(1, 255))
                    )
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(durationMs)
                }
            }
        } catch (_: Exception) {}
    }

    private fun vibrateSuccess() {
        try {
            if (vibrator?.hasVibrator() == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val timings = longArrayOf(0, 40, 60, 50)
                    val amplitudes = intArrayOf(0, 120, 0, 160)
                    vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(100)
                }
            }
        } catch (_: Exception) {}
    }

    fun release() {
        try {
            stopAllAudio()
            tts?.shutdown()
            tts = null
        } catch (_: Exception) {}
    }
}

