package com.example.util

/**
 * Registry for mapping sound types to audio resource names.
 * Makes it effortless to plug in new authentic real animal, nature, or effect sounds.
 */
object AnimalSoundRegistry {

    private val soundMap = mutableMapOf(
        // Çiftlik ve Ev Hayvanları
        "cat" to "sound_cat",
        "dog" to "sound_dog",
        "cow" to "sound_cow",
        "sheep" to "sound_sheep",
        "goat" to "sound_goat",
        "duck" to "sound_duck",
        "rooster" to "sound_rooster",
        "horse" to "sound_horse",
        "donkey" to "sound_horse", // Gelecek gerçek eşek sesi için yedek

        // Vahşi Doğa ve Orman Hayvanları
        "lion" to "sound_lion",
        "tiger" to "sound_lion", // Gelecek kaplan sesi için yedek
        "elephant" to "sound_elephant",
        "monkey" to "sound_monkey",
        "wolf" to "sound_wolf",
        "bear" to "sound_bear",
        "giraffe" to "sound_giraffe",
        "panda" to "sound_bear",
        "rabbit" to "sound_frog", // Tavşan için doğa canlısı yedek sesi

        // Kuşlar, Böcekler ve Küçük Canlılar
        "bird" to "sound_bird",
        "owl" to "sound_bird", // Gelecek baykuş sesi için yedek
        "frog" to "sound_frog",
        "bee" to "sound_bee",

        // Doğa & Çevre Sesleri
        "water" to "sfx_water",
        "wind" to "sfx_wind",
        "sparkle" to "sfx_sparkle",

        // Etkileşim & Ödül Sesleri
        "crunch" to "sfx_bite",
        "bite" to "sfx_bite",
        "success" to "sfx_success",
        "wrong" to "sfx_wrong",
        "pop" to "sfx_pop"
    )

    /**
     * Retrieves the resource name for a given sound type key.
     */
    fun getResourceName(soundType: String): String {
        return soundMap[soundType.lowercase().trim()] ?: "sfx_pop"
    }

    /**
     * Allows dynamic or future registration of new real animal/ambient sound files
     * without modifying the core player logic.
     */
    fun registerSound(soundType: String, resourceName: String) {
        soundMap[soundType.lowercase().trim()] = resourceName
    }

    /**
     * Checks whether a soundType has an assigned mapping.
     */
    fun hasSound(soundType: String): Boolean {
        return soundMap.containsKey(soundType.lowercase().trim())
    }
}
