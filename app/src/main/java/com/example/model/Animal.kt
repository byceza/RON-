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
        description = "Büyük sevimli hayvanlar ve temel gerçek sesler",
        teacherAdvice = "2 Yaş İpucu: 2 büyük seçenek ve gerçek hayvan sesleriyle bebeğinizin işitsel ve görsel bağ kurmasını sağlayın."
    ),
    AGE_3(
        age = 3,
        label = "3 Yaş",
        title = "3 Yaş Dünyası",
        badge = "🐣 Minikler",
        optionsCount = 2,
        description = "Sevimli çiftlik dostları ve renkli meyveler",
        teacherAdvice = "3 Yaş İpucu: 2 seçenekli sorular ve renk odaklı meyve ödülleriyle görsel odaklanmayı destekleyin."
    ),
    AGE_4(
        age = 4,
        label = "4 Yaş",
        title = "4 Yaş Dünyası",
        badge = "🌟 Meraklılar",
        optionsCount = 3,
        description = "Çiftlik, orman ve doğa dostları",
        teacherAdvice = "4 Yaş İpucu: 3 seçenekli eşleştirmeler ve zengin doğa sesleriyle algı derinliğini artırın."
    ),
    AGE_5(
        age = 5,
        label = "5 Yaş",
        title = "5 Yaş Dünyası",
        badge = "🚀 Küçük Dâhiler",
        optionsCount = 4,
        description = "Vahşi doğa ve akıllı eşleştirmeler",
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
    NUMBERS("Sayılar 1-10", "🔢"),
    SYMBOLS("Semboller", "⚡")
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

    // --- 1. HAYVANLAR (Gerçekçi Sesler ve Şefkatli Öğretmen Anlatımı) ---
    val animals = listOf(
        LearningItem(
            id = "cat",
            name = "Kedi",
            emoji = "🐱",
            soundText = "Sevimli Dost 🐾",
            questionVoice = "Yumuşacık tüylü tatlı kediciği bul bakalım canım!",
            accentColor = Color(0xFFFF6584),
            cardBgColor = Color(0xFFFFF0F3),
            rewardFruit = RewardFruit.STRAWBERRY,
            category = LearningCategory.ANIMALS,
            minAge = 2,
            soundType = "cat",
            teacherExplanation = "Kedi yumuşacık tüylere ve sevimli patilere sahiptir!"
        ),
        LearningItem(
            id = "dog",
            name = "Köpek",
            emoji = "🐶",
            soundText = "Sadık Dost 🐾",
            questionVoice = "Sadık ve sevimli köpeği bulabilir misin tatlım?",
            accentColor = Color(0xFF4361EE),
            cardBgColor = Color(0xFFEFF3FF),
            rewardFruit = RewardFruit.MANDARIN,
            category = LearningCategory.ANIMALS,
            minAge = 2,
            soundType = "dog",
            teacherExplanation = "Köpek çok sadık, sevecen ve akıllı bir dosttur!"
        ),
        LearningItem(
            id = "cow",
            name = "İnek",
            emoji = "🐮",
            soundText = "Çiftlik Sakini 🌿",
            questionVoice = "Bize taze ve lezzetli süt veren sevimli ineği göster bakalım!",
            accentColor = Color(0xFF06D6A0),
            cardBgColor = Color(0xFFEDFCF7),
            rewardFruit = RewardFruit.APPLE,
            category = LearningCategory.ANIMALS,
            minAge = 2,
            soundType = "cow",
            teacherExplanation = "İnek tarlada ot yer ve bize taze süt verir!"
        ),
        LearningItem(
            id = "sheep",
            name = "Koyun",
            emoji = "🐑",
            soundText = "Yumuşak Yünlü 🐑",
            questionVoice = "Pamuk gibi bembeyaz kıvırcık koyun nerede bakalım?",
            accentColor = Color(0xFF48CAE4),
            cardBgColor = Color(0xFFF0FAFC),
            rewardFruit = RewardFruit.BLUEBERRY,
            category = LearningCategory.ANIMALS,
            minAge = 2,
            soundType = "sheep",
            teacherExplanation = "Koyun kıvırcık yünleriyle bizi kışın sıcacık tutar!"
        ),
        LearningItem(
            id = "duck",
            name = "Ördek",
            emoji = "🦆",
            soundText = "Göl Sakini 🌊",
            questionVoice = "Gölde neşeyle yüzen sevimli ördek hangisi tatlım?",
            accentColor = Color(0xFFFFD166),
            cardBgColor = Color(0xFFFFFCEB),
            rewardFruit = RewardFruit.ORANGE,
            category = LearningCategory.ANIMALS,
            minAge = 2,
            soundType = "duck",
            teacherExplanation = "Ördek perdeli ayaklarıyla gölde çok güzel yüzer!"
        ),
        LearningItem(
            id = "rooster",
            name = "Horoz",
            emoji = "🐓",
            soundText = "Sabah Habercisi 🌅",
            questionVoice = "Sabahları erkenden uyanan renkli horozu bul bakalım!",
            accentColor = Color(0xFFE71D36),
            cardBgColor = Color(0xFFFDF0F2),
            rewardFruit = RewardFruit.CHERRY,
            category = LearningCategory.ANIMALS,
            minAge = 3,
            soundType = "rooster",
            teacherExplanation = "Horoz parlak tüyleriyle sabahın gelişini haber verir!"
        ),
        LearningItem(
            id = "lion",
            name = "Aslan",
            emoji = "🦁",
            soundText = "Ormanın Kralı 👑",
            questionVoice = "Ormanların kralı güçlü ve görkemli aslan nerede?",
            accentColor = Color(0xFFFF9F1C),
            cardBgColor = Color(0xFFFFF7ED),
            rewardFruit = RewardFruit.MANGO,
            category = LearningCategory.ANIMALS,
            minAge = 3,
            soundType = "lion",
            teacherExplanation = "Aslan altın sarısı yelesiyle savanaların lideridir!"
        ),
        LearningItem(
            id = "elephant",
            name = "Fil",
            emoji = "🐘",
            soundText = "Kocaman Dost 🐘",
            questionVoice = "Uzun hortumlu kocaman sevimli fil hangisi tatlım?",
            accentColor = Color(0xFF7209B7),
            cardBgColor = Color(0xFFF7F0FA),
            rewardFruit = RewardFruit.WATERMELON,
            category = LearningCategory.ANIMALS,
            minAge = 4,
            soundType = "elephant",
            teacherExplanation = "Fil uzun hortumuyla su püskürtür ve meyve toplar!"
        ),
        LearningItem(
            id = "monkey",
            name = "Maymun",
            emoji = "🐵",
            soundText = "Neşeli Dost 🍌",
            questionVoice = "Ağaçlarda sallanan muz seven neşeli maymun nerede?",
            accentColor = Color(0xFFFB8500),
            cardBgColor = Color(0xFFFFF6EB),
            rewardFruit = RewardFruit.BANANA,
            category = LearningCategory.ANIMALS,
            minAge = 3,
            soundType = "monkey",
            teacherExplanation = "Maymun uzun kuyruğuyla daldan dala neşeyle sallanır!"
        ),
        LearningItem(
            id = "rabbit",
            name = "Tavşan",
            emoji = "🐰",
            soundText = "Hızlı Zıpzıp 🥕",
            questionVoice = "Havuç seven sevimli tavşan nerede tatlım?",
            accentColor = Color(0xFFFF70A6),
            cardBgColor = Color(0xFFFFF0F6),
            rewardFruit = RewardFruit.STRAWBERRY,
            category = LearningCategory.ANIMALS,
            minAge = 2,
            soundType = "pop",
            teacherExplanation = "Tavşan uzun kulaklarıyla çevreyi dikkatle dinler!"
        ),
        LearningItem(
            id = "bird",
            name = "Kuş",
            emoji = "🐦",
            soundText = "Kanatlı Dost 🪶",
            questionVoice = "Gökyüzünde neşeyle süzülen minik kuşu bul bakalım!",
            accentColor = Color(0xFF00B4D8),
            cardBgColor = Color(0xFFEFFBFE),
            rewardFruit = RewardFruit.BLUEBERRY,
            category = LearningCategory.ANIMALS,
            minAge = 2,
            soundType = "bird",
            teacherExplanation = "Kuş renkli kanatlarıyla gökyüzünde süzülür!"
        ),
        LearningItem(
            id = "horse",
            name = "At",
            emoji = "🐴",
            soundText = "Asil Koşucu 🐎",
            questionVoice = "Kırlarda rüzgar gibi koşan asil güzel at hangisi?",
            accentColor = Color(0xFF9D4EDD),
            cardBgColor = Color(0xFFF8F2FC),
            rewardFruit = RewardFruit.APPLE,
            category = LearningCategory.ANIMALS,
            minAge = 4,
            soundType = "horse",
            teacherExplanation = "At güçlü toynaklarıyla kırlarda özgürce koşar!"
        ),
        LearningItem(
            id = "frog",
            name = "Kurbağa",
            emoji = "🐸",
            soundText = "Göl Sakini 🐸",
            questionVoice = "Nilüfer yaprağında oturan sevimli yeşil kurbağa nerede?",
            accentColor = Color(0xFF52B788),
            cardBgColor = Color(0xFFF0FAF4),
            rewardFruit = RewardFruit.KIWI,
            category = LearningCategory.ANIMALS,
            minAge = 4,
            soundType = "frog",
            teacherExplanation = "Kurbağa gölet kenarında nilüfer yapraklarında yaşar!"
        ),
        LearningItem(
            id = "wolf",
            name = "Kurt",
            emoji = "🐺",
            soundText = "Orman Sakini 🌲",
            questionVoice = "Dolunaya bakan asil ve dikkatli kurt nerede?",
            accentColor = Color(0xFF5A189A),
            cardBgColor = Color(0xFFF6F0FA),
            rewardFruit = RewardFruit.BLACKBERRY,
            category = LearningCategory.ANIMALS,
            minAge = 5,
            soundType = "wolf",
            teacherExplanation = "Kurt keskin duyularıyla ormanda ailesiyle yaşar!"
        ),
        LearningItem(
            id = "bear",
            name = "Ayı",
            emoji = "🐻",
            soundText = "Bal Sever 🍯",
            questionVoice = "Ormanda bal arayan kocaman sevimli ayı hangisi?",
            accentColor = Color(0xFF936639),
            cardBgColor = Color(0xFFF8F5F1),
            rewardFruit = RewardFruit.BLACKBERRY,
            category = LearningCategory.ANIMALS,
            minAge = 4,
            soundType = "bear",
            teacherExplanation = "Ayı ormanda taze meyveleri ve tatlı balı çok sever!"
        ),
        LearningItem(
            id = "bee",
            name = "Arı",
            emoji = "🐝",
            soundText = "Çalışkan Dost 🌸",
            questionVoice = "Çiçeklerden nektar toplayan çalışkan bal arısı hangisi?",
            accentColor = Color(0xFFFFB703),
            cardBgColor = Color(0xFFFFFBEB),
            rewardFruit = RewardFruit.PINEAPPLE,
            category = LearningCategory.ANIMALS,
            minAge = 4,
            soundType = "bee",
            teacherExplanation = "Arı çiçekleri dolaşarak doğaya ve peteğe bal taşır!"
        ),
        LearningItem(
            id = "panda",
            name = "Panda",
            emoji = "🐼",
            soundText = "Bambu Sever 🎋",
            questionVoice = "Bambu yiyen siyah beyaz sevimli panda nerede?",
            accentColor = Color(0xFF2EC4B6),
            cardBgColor = Color(0xFFEFFBF9),
            rewardFruit = RewardFruit.AVOCADO,
            category = LearningCategory.ANIMALS,
            minAge = 4,
            soundType = "bear",
            teacherExplanation = "Panda serin ormanlarda taze bambu filizleri yer!"
        ),
        LearningItem(
            id = "goat",
            name = "Keçi",
            emoji = "🐐",
            soundText = "İnatçı Dost 🌿",
            questionVoice = "Kayalara tırmanan sevimli keçiyi bul bakalım tatlım!",
            accentColor = Color(0xFF6B705C),
            cardBgColor = Color(0xFFF7F8F5),
            rewardFruit = RewardFruit.APPLE,
            category = LearningCategory.ANIMALS,
            minAge = 3,
            soundType = "goat",
            teacherExplanation = "Keçi dağlarda ve kayalıklarda neşeyle tırmanır!"
        ),
        LearningItem(
            id = "giraffe",
            name = "Zürafa",
            emoji = "🦒",
            soundText = "Uzun Boylu 🌿",
            questionVoice = "Upuzun boyunlu sevimli zürafa nerede bakalım?",
            accentColor = Color(0xFFEE9B00),
            cardBgColor = Color(0xFFFFF8EC),
            rewardFruit = RewardFruit.BANANA,
            category = LearningCategory.ANIMALS,
            minAge = 3,
            soundType = "giraffe",
            teacherExplanation = "Zürafa en yüksek ağaçlardaki taze yaprakları yer!"
        )
    )

    // --- 2. MEYVELER (Orman Meyveleri, Egzotik Meyveler ve Eğitici Sorular) ---
    val fruits = listOf(
        LearningItem(
            id = "fruit_orange",
            name = "Portakal",
            emoji = "🍊",
            soundText = "Sulu portakal!",
            questionVoice = "Aşağıdakilerden hangisi portakaldır? Bul ve tıkla bakalım!",
            accentColor = Color(0xFFF77F00),
            cardBgColor = Color(0xFFFFF6ED),
            rewardFruit = RewardFruit.ORANGE,
            category = LearningCategory.FRUITS,
            minAge = 2,
            soundType = "crunch",
            teacherExplanation = "Portakal turuncudur, mis gibi kokar ve bol vitaminlidir!"
        ),
        LearningItem(
            id = "fruit_apple",
            name = "Elma",
            emoji = "🍎",
            soundText = "Kıtır elma!",
            questionVoice = "Hadi kırmızı tatlı elmayı bul ve tıkla tatlım!",
            accentColor = Color(0xFFE63946),
            cardBgColor = Color(0xFFFDF0F1),
            rewardFruit = RewardFruit.APPLE,
            category = LearningCategory.FRUITS,
            minAge = 2,
            soundType = "crunch",
            teacherExplanation = "Elma kıtır kıtırdır, ısırması çok keyiflidir!"
        ),
        LearningItem(
            id = "fruit_strawberry",
            name = "Çilek",
            emoji = "🍓",
            soundText = "Tatlı çilek!",
            questionVoice = "Üzerinde minik benekleri olan kırmızı çilek hangisi?",
            accentColor = Color(0xFFFF4D6D),
            cardBgColor = Color(0xFFFFF0F3),
            rewardFruit = RewardFruit.STRAWBERRY,
            category = LearningCategory.FRUITS,
            minAge = 2,
            soundType = "crunch",
            teacherExplanation = "Çilek mis gibi bahar kokar ve çok lezzetlidir!"
        ),
        LearningItem(
            id = "fruit_banana",
            name = "Muz",
            emoji = "🍌",
            soundText = "Enerji deposu muz!",
            questionVoice = "Sapsarı ve tatlı muz nerede canım benim?",
            accentColor = Color(0xFFFFB703),
            cardBgColor = Color(0xFFFFFBEA),
            rewardFruit = RewardFruit.BANANA,
            category = LearningCategory.FRUITS,
            minAge = 2,
            soundType = "crunch",
            teacherExplanation = "Muz kaslarımızı güçlendirir ve enerji verir!"
        ),
        LearningItem(
            id = "fruit_watermelon",
            name = "Karpuz",
            emoji = "🍉",
            soundText = "Sulu karpuz!",
            questionVoice = "Dışı yeşil, içi kırmızı serin karpuz hangisi?",
            accentColor = Color(0xFF06D6A0),
            cardBgColor = Color(0xFFEDFCF7),
            rewardFruit = RewardFruit.WATERMELON,
            category = LearningCategory.FRUITS,
            minAge = 2,
            soundType = "crunch",
            teacherExplanation = "Karpuz yaz aylarında içimizi ferahlatır!"
        ),
        LearningItem(
            id = "fruit_blueberry",
            name = "Yaban Mersini",
            emoji = "🫐",
            soundText = "Orman meyvesi!",
            questionVoice = "Masmavi orman meyvesi yaban mersinini göster bakalım!",
            accentColor = Color(0xFF3A86FF),
            cardBgColor = Color(0xFFEFF5FF),
            rewardFruit = RewardFruit.BLUEBERRY,
            category = LearningCategory.FRUITS,
            minAge = 4,
            soundType = "crunch",
            teacherExplanation = "Yaban mersini küçük taneli şifalı bir orman meyvesidir!"
        ),
        LearningItem(
            id = "fruit_blackberry",
            name = "Böğürtlen",
            emoji = "🍇",
            soundText = "Nefis böğürtlen!",
            questionVoice = "Çalıların arasında yetişen leziz böğürtlen hangisi tatlım?",
            accentColor = Color(0xFF7209B7),
            cardBgColor = Color(0xFFF7F0FA),
            rewardFruit = RewardFruit.BLACKBERRY,
            category = LearningCategory.FRUITS,
            minAge = 4,
            soundType = "crunch",
            teacherExplanation = "Böğürtlen koyu mor renkli harika bir orman meyvesidir!"
        ),
        LearningItem(
            id = "fruit_mango",
            name = "Mango",
            emoji = "🥭",
            soundText = "Tropik tat!",
            questionVoice = "Tropik ülkelerden gelen tatlı sulu mango nerede?",
            accentColor = Color(0xFFFF9E00),
            cardBgColor = Color(0xFFFFF8EC),
            rewardFruit = RewardFruit.MANGO,
            category = LearningCategory.FRUITS,
            minAge = 4,
            soundType = "crunch",
            teacherExplanation = "Mango sıcacık ülkelerde güneş altında olgunlaşır!"
        ),
        LearningItem(
            id = "fruit_avocado",
            name = "Avokado",
            emoji = "🥑",
            soundText = "Sağlık deposu!",
            questionVoice = "Kocaman çekirdekli yeşil avokadoyu bul ve tıkla!",
            accentColor = Color(0xFF588157),
            cardBgColor = Color(0xFFF3F7F2),
            rewardFruit = RewardFruit.AVOCADO,
            category = LearningCategory.FRUITS,
            minAge = 4,
            soundType = "crunch",
            teacherExplanation = "Avokado kalbimizi ve beynimizi güçlendiren faydalı yağlar içerir!"
        ),
        LearningItem(
            id = "fruit_kiwi",
            name = "Kivi",
            emoji = "🥝",
            soundText = "Ekşi tatlı kivi!",
            questionVoice = "İçi parlak yeşil ve minik siyah çekirdekli kivi hangisi?",
            accentColor = Color(0xFF8CB369),
            cardBgColor = Color(0xFFF6FAF2),
            rewardFruit = RewardFruit.KIWI,
            category = LearningCategory.FRUITS,
            minAge = 4,
            soundType = "crunch",
            teacherExplanation = "Kivi C vitamini deposudur, hastalıklardan korur!"
        ),
        LearningItem(
            id = "fruit_cherry",
            name = "Kiraz",
            emoji = "🍒",
            soundText = "İkiz kirazlar!",
            questionVoice = "Kulağımıza küpe yaptığımız tatlı kirazlar nerede?",
            accentColor = Color(0xFFD90429),
            cardBgColor = Color(0xFFFDF0F2),
            rewardFruit = RewardFruit.CHERRY,
            category = LearningCategory.FRUITS,
            minAge = 3,
            soundType = "crunch",
            teacherExplanation = "Kiraz dalında çift çift sallanan tatlı bir meyvedir!"
        ),
        LearningItem(
            id = "fruit_grapes",
            name = "Üzüm",
            emoji = "🍇",
            soundText = "Salkım üzüm!",
            questionVoice = "Salkım salkım tatlı üzüm hangisi canım benim?",
            accentColor = Color(0xFF8338EC),
            cardBgColor = Color(0xFFF8F2FD),
            rewardFruit = RewardFruit.GRAPES,
            category = LearningCategory.FRUITS,
            minAge = 3,
            soundType = "crunch",
            teacherExplanation = "Üzüm tanelerini teker teker yemek çok eğlencelidir!"
        ),
        LearningItem(
            id = "fruit_pineapple",
            name = "Ananas",
            emoji = "🍍",
            soundText = "Tropik ananas!",
            questionVoice = "Başında yeşil tacı olan leziz ananas hangisi?",
            accentColor = Color(0xFFFFBE0B),
            cardBgColor = Color(0xFFFFFCEB),
            rewardFruit = RewardFruit.PINEAPPLE,
            category = LearningCategory.FRUITS,
            minAge = 4,
            soundType = "crunch",
            teacherExplanation = "Ananas meyvelerin kraliçesidir, başında tacı vardır!"
        )
    )

    // --- 3. ŞEKİLLER (Daire, Kare, Üçgen, Dikdörtgen, Yıldız, Kalp, Oval...) ---
    val shapes = listOf(
        LearningItem(
            id = "shape_circle",
            name = "Daire",
            emoji = "🔴",
            soundText = "Yuvarlak daire!",
            questionVoice = "Top gibi yuvarlak olan daire şekli hangisi tatlım?",
            accentColor = Color(0xFFE63946),
            cardBgColor = Color(0xFFFDF0F1),
            rewardFruit = RewardFruit.APPLE,
            category = LearningCategory.SHAPES,
            minAge = 2,
            soundType = "pop",
            teacherExplanation = "Dairenin köşesi yoktur, tekerlek gibi döner!"
        ),
        LearningItem(
            id = "shape_square",
            name = "Kare",
            emoji = "🟩",
            soundText = "Dört kenarlı kare!",
            questionVoice = "Dört kenarı da birbirine eşit olan kare nerede?",
            accentColor = Color(0xFF2A9D8F),
            cardBgColor = Color(0xFFEFF9F8),
            rewardFruit = RewardFruit.KIWI,
            category = LearningCategory.SHAPES,
            minAge = 2,
            soundType = "pop",
            teacherExplanation = "Kutular ve pencereler genellikle kare şeklindedir!"
        ),
        LearningItem(
            id = "shape_triangle",
            name = "Üçgen",
            emoji = "🔺",
            soundText = "Üç köşeli üçgen!",
            questionVoice = "Aşağıdakilerden hangisi üçgendir? Üç sivri köşesi var!",
            accentColor = Color(0xFFF4A261),
            cardBgColor = Color(0xFFFFF7F0),
            rewardFruit = RewardFruit.ORANGE,
            category = LearningCategory.SHAPES,
            minAge = 2,
            soundType = "pop",
            teacherExplanation = "Piramitler ve çadırlar üçgen şeklindedir!"
        ),
        LearningItem(
            id = "shape_rectangle",
            name = "Dikdörtgen",
            emoji = "🔲",
            soundText = "Uzun dikdörtgen!",
            questionVoice = "İki kenarı uzun, iki kenarı kısa dikdörtgen hangisi?",
            accentColor = Color(0xFF457B9D),
            cardBgColor = Color(0xFFEFF5F9),
            rewardFruit = RewardFruit.BLUEBERRY,
            category = LearningCategory.SHAPES,
            minAge = 4,
            soundType = "pop",
            teacherExplanation = "Kapılar ve kitaplar dikdörtgen şeklindedir!"
        ),
        LearningItem(
            id = "shape_star",
            name = "Yıldız",
            emoji = "⭐",
            soundText = "Parlak yıldız!",
            questionVoice = "Gökyüzünde ışıl ışıl parlayan yıldız şekli nerede?",
            accentColor = Color(0xFFFFB703),
            cardBgColor = Color(0xFFFFFBEA),
            rewardFruit = RewardFruit.BANANA,
            category = LearningCategory.SHAPES,
            minAge = 2,
            soundType = "pop",
            teacherExplanation = "Yıldız beş sivri ucuyla karanlıkta parıldar!"
        ),
        LearningItem(
            id = "shape_heart",
            name = "Kalp",
            emoji = "❤️",
            soundText = "Sevgi dolu kalp!",
            questionVoice = "Sevgi ve sevecenliği anlatan kalp şekli hangisi?",
            accentColor = Color(0xFFFF4D6D),
            cardBgColor = Color(0xFFFFF0F3),
            rewardFruit = RewardFruit.STRAWBERRY,
            category = LearningCategory.SHAPES,
            minAge = 2,
            soundType = "pop",
            teacherExplanation = "Kalp birbirimize duyduğumuz sevgiyi simgeler!"
        ),
        LearningItem(
            id = "shape_oval",
            name = "Oval (Elips)",
            emoji = "🥚",
            soundText = "Yumurta şekli oval!",
            questionVoice = "Yumurta gibi hafif uzatılmış oval şekli nerede?",
            accentColor = Color(0xFF9D4EDD),
            cardBgColor = Color(0xFFF9F2FC),
            rewardFruit = RewardFruit.GRAPES,
            category = LearningCategory.SHAPES,
            minAge = 4,
            soundType = "pop",
            teacherExplanation = "Kuş yumurtaları ve balonlar oval biçimindedir!"
        ),
        LearningItem(
            id = "shape_diamond",
            name = "Baklava Dilimi",
            emoji = "🔶",
            soundText = "Eşkenar dörtgen!",
            questionVoice = "Uçurtmaya benzeyen baklava dilimi şekli hangisi?",
            accentColor = Color(0xFFFB8500),
            cardBgColor = Color(0xFFFFF6EB),
            rewardFruit = RewardFruit.MANGO,
            category = LearningCategory.SHAPES,
            minAge = 4,
            soundType = "pop",
            teacherExplanation = "Gökyüzünde uçurduğumuz uçurtmalar baklava dilimi gibidir!"
        )
    )

    // --- 4. SAYILAR (1'den 10'a Kadar Görsel Sayma İle) ---
    val numbers = listOf(
        LearningItem(
            id = "num_1",
            name = "1 (Bir)",
            emoji = "1️⃣",
            soundText = "Bir!",
            questionVoice = "1 sayısını göster bakalım tatlım! Bir tane elma var!",
            accentColor = Color(0xFFE63946),
            cardBgColor = Color(0xFFFDF0F1),
            rewardFruit = RewardFruit.APPLE,
            category = LearningCategory.NUMBERS,
            minAge = 2,
            soundType = "pop",
            teacherExplanation = "1, saymaya başladığımız ilk sayıdır!",
            countVisual = "🍎 (1 elma)"
        ),
        LearningItem(
            id = "num_2",
            name = "2 (İki)",
            emoji = "2️⃣",
            soundText = "İki!",
            questionVoice = "2 sayısını bul bakalım! İki sevimli kedi miyavlıyor!",
            accentColor = Color(0xFFFB8500),
            cardBgColor = Color(0xFFFFF6EB),
            rewardFruit = RewardFruit.ORANGE,
            category = LearningCategory.NUMBERS,
            minAge = 2,
            soundType = "pop",
            teacherExplanation = "Bizim iki gözümüz ve iki kulağımız vardır!",
            countVisual = "🐱🐱 (2 kedi)"
        ),
        LearningItem(
            id = "num_3",
            name = "3 (Üç)",
            emoji = "3️⃣",
            soundText = "Üç!",
            questionVoice = "3 sayısını göster bakalım tatlım! Üç parlak yıldız!",
            accentColor = Color(0xFFFFB703),
            cardBgColor = Color(0xFFFFFBEA),
            rewardFruit = RewardFruit.BANANA,
            category = LearningCategory.NUMBERS,
            minAge = 2,
            soundType = "pop",
            teacherExplanation = "Trafik ışıklarında 3 renk vardır: Kırmızı, sarı, yeşil!",
            countVisual = "⭐⭐⭐ (3 yıldız)"
        ),
        LearningItem(
            id = "num_4",
            name = "4 (Dört)",
            emoji = "4️⃣",
            soundText = "Dört!",
            questionVoice = "4 sayısını bul bakalım canım! Dört sevimli ördek suda yüzüyor!",
            accentColor = Color(0xFF2EC4B6),
            cardBgColor = Color(0xFFEFFBF9),
            rewardFruit = RewardFruit.WATERMELON,
            category = LearningCategory.NUMBERS,
            minAge = 3,
            soundType = "pop",
            teacherExplanation = "Bir masanın ve bir sandalyenin 4 ayağı vardır!",
            countVisual = "🦆🦆🦆🦆 (4 ördek)"
        ),
        LearningItem(
            id = "num_5",
            name = "5 (Beş)",
            emoji = "5️⃣",
            soundText = "Beş!",
            questionVoice = "5 sayısını göster bakalım! Bir elimizdeki beş parmak gibi!",
            accentColor = Color(0xFF3A86FF),
            cardBgColor = Color(0xFFEFF5FF),
            rewardFruit = RewardFruit.BLUEBERRY,
            category = LearningCategory.NUMBERS,
            minAge = 3,
            soundType = "pop",
            teacherExplanation = "Bir elimizi açtığımızda tam 5 parmağımız olur!",
            countVisual = "🎈🎈🎈🎈🎈 (5 balon)"
        ),
        LearningItem(
            id = "num_6",
            name = "6 (Altı)",
            emoji = "6️⃣",
            soundText = "Altı!",
            questionVoice = "6 sayısını bul bakalım tatlım! Altı tatlı çilek!",
            accentColor = Color(0xFFFF4D6D),
            cardBgColor = Color(0xFFFFF0F3),
            rewardFruit = RewardFruit.STRAWBERRY,
            category = LearningCategory.NUMBERS,
            minAge = 4,
            soundType = "pop",
            teacherExplanation = "Arıların petekleri 6 köşelidir!",
            countVisual = "🍓🍓🍓🍓🍓🍓 (6 çilek)"
        ),
        LearningItem(
            id = "num_7",
            name = "7 (Yedi)",
            emoji = "7️⃣",
            soundText = "Yedi!",
            questionVoice = "7 sayısını göster bakalım! Gökkuşağının yedi rengi gibi!",
            accentColor = Color(0xFF7209B7),
            cardBgColor = Color(0xFFF7F0FA),
            rewardFruit = RewardFruit.BLACKBERRY,
            category = LearningCategory.NUMBERS,
            minAge = 5,
            soundType = "pop",
            teacherExplanation = "Bir haftada 7 gün ve gökkuşağında 7 renk vardır!",
            countVisual = "🌸🌸🌸🌸🌸🌸🌸 (7 çiçek)"
        ),
        LearningItem(
            id = "num_8",
            name = "8 (Sekiz)",
            emoji = "8️⃣",
            soundText = "Sekiz!",
            questionVoice = "8 sayısını bul bakalım! Ahtapotun sekiz kolu gibi!",
            accentColor = Color(0xFF4361EE),
            cardBgColor = Color(0xFFEFF3FF),
            rewardFruit = RewardFruit.AVOCADO,
            category = LearningCategory.NUMBERS,
            minAge = 5,
            soundType = "pop",
            teacherExplanation = "Denizlerdeki ahtapotun tam 8 kolu vardır!",
            countVisual = "🍬🍬🍬🍬🍬🍬🍬🍬 (8 şeker)"
        ),
        LearningItem(
            id = "num_9",
            name = "9 (Dokuz)",
            emoji = "9️⃣",
            soundText = "Dokuz!",
            questionVoice = "9 sayısını göster bakalım! Dokuz çıtır havuç!",
            accentColor = Color(0xFFF77F00),
            cardBgColor = Color(0xFFFFF6ED),
            rewardFruit = RewardFruit.MANDARIN,
            category = LearningCategory.NUMBERS,
            minAge = 5,
            soundType = "pop",
            teacherExplanation = "Tek basamaklı sayıların en büyüğü 9'dur!",
            countVisual = "🥕🥕🥕🥕🥕🥕🥕🥕🥕 (9 havuç)"
        ),
        LearningItem(
            id = "num_10",
            name = "10 (On)",
            emoji = "🔟",
            soundText = "On!",
            questionVoice = "10 sayısını bul bakalım canım! İki elimizin tüm parmakları!",
            accentColor = Color(0xFF06D6A0),
            cardBgColor = Color(0xFFEDFCF7),
            rewardFruit = RewardFruit.WATERMELON,
            category = LearningCategory.NUMBERS,
            minAge = 5,
            soundType = "pop",
            teacherExplanation = "İki elimizi birleştirdiğimizde tam 10 parmağımız olur!",
            countVisual = "🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟 (10 yıldız)"
        )
    )

    // --- 5. SEMBOLLER (Matematik Sembolleri & Hayatta Kullanılan Genel Semboller) ---
    val symbols = listOf(
        // Matematik Sembolleri
        LearningItem(
            id = "sym_plus",
            name = "Artı (Toplama)",
            emoji = "➕",
            soundText = "Artı işareti!",
            questionVoice = "Sayıları birleştiren ve çoğaltan artı işareti hangisi?",
            accentColor = Color(0xFF06D6A0),
            cardBgColor = Color(0xFFEDFCF7),
            rewardFruit = RewardFruit.APPLE,
            category = LearningCategory.SYMBOLS,
            minAge = 5,
            soundType = "pop",
            teacherExplanation = "Artı işareti nesneleri bir araya getirip çoğaltır!"
        ),
        LearningItem(
            id = "sym_minus",
            name = "Eksi (Çıkarma)",
            emoji = "➖",
            soundText = "Eksi işareti!",
            questionVoice = "Eksiltme ve ayırma yapan eksi işareti nerede?",
            accentColor = Color(0xFFE63946),
            cardBgColor = Color(0xFFFDF0F1),
            rewardFruit = RewardFruit.CHERRY,
            category = LearningCategory.SYMBOLS,
            minAge = 5,
            soundType = "pop",
            teacherExplanation = "Eksi işareti eldeki şeylerden eksildiğini gösterir!"
        ),
        LearningItem(
            id = "sym_equals",
            name = "Eşittir (Sonuç)",
            emoji = "🟰",
            soundText = "Eşittir işareti!",
            questionVoice = "İki tarafın aynı olduğunu gösteren eşittir işareti hangisi?",
            accentColor = Color(0xFF4361EE),
            cardBgColor = Color(0xFFEFF3FF),
            rewardFruit = RewardFruit.BLUEBERRY,
            category = LearningCategory.SYMBOLS,
            minAge = 5,
            soundType = "pop",
            teacherExplanation = "Eşittir işareti dengededir, iki tarafın birbirine eşit olduğunu söyler!"
        ),
        LearningItem(
            id = "sym_multiply",
            name = "Çarpı (Çarpma)",
            emoji = "✖️",
            soundText = "Çarpı işareti!",
            questionVoice = "Kat kat katlayan matematik çarpı işareti nerede?",
            accentColor = Color(0xFFFB8500),
            cardBgColor = Color(0xFFFFF6EB),
            rewardFruit = RewardFruit.MANGO,
            category = LearningCategory.SYMBOLS,
            minAge = 5,
            soundType = "pop",
            teacherExplanation = "Çarpma işlemi hızlıca kat kat artırmak demektir!"
        ),
        LearningItem(
            id = "sym_divide",
            name = "Bölü (Bölme)",
            emoji = "➗",
            soundText = "Bölü işareti!",
            questionVoice = "Kardeş payı yapan, eşit dağıtan bölü işareti hangisi?",
            accentColor = Color(0xFF7209B7),
            cardBgColor = Color(0xFFF7F0FA),
            rewardFruit = RewardFruit.GRAPES,
            category = LearningCategory.SYMBOLS,
            minAge = 5,
            soundType = "pop",
            teacherExplanation = "Bölme işlemi pastayı arkadaşlarımıza eşit paylaştırmak gibidir!"
        ),
        // Hayatta Kullanılan Genel Semboller
        LearningItem(
            id = "sym_music",
            name = "Müzik Notası",
            emoji = "🎵",
            soundText = "Melodi ve müzik!",
            questionVoice = "Şarkıları ve güzel ezgileri simgeleyen müzik notası nerede?",
            accentColor = Color(0xFFFF6584),
            cardBgColor = Color(0xFFFFF0F3),
            rewardFruit = RewardFruit.STRAWBERRY,
            category = LearningCategory.SYMBOLS,
            minAge = 4,
            soundType = "pop",
            teacherExplanation = "Müzik notası dinlediğimiz neşeli çocuk şarkılarını temsil eder!"
        ),
        LearningItem(
            id = "sym_lightbulb",
            name = "Ampul (Fikir)",
            emoji = "💡",
            soundText = "Harika bir fikir!",
            questionVoice = "Aklımıza parlak bir fikir gelince yanan ampul simgesi nerede?",
            accentColor = Color(0xFFFFB703),
            cardBgColor = Color(0xFFFFFBEA),
            rewardFruit = RewardFruit.BANANA,
            category = LearningCategory.SYMBOLS,
            minAge = 4,
            soundType = "pop",
            teacherExplanation = "Işık ampulü zekayı, keşfetmeyi ve yeni fikirleri anlatır!"
        ),
        LearningItem(
            id = "sym_sun",
            name = "Güneş (Gündüz)",
            emoji = "☀️",
            soundText = "Sıcak güneş!",
            questionVoice = "Gündüzleri dünyamızı ısıtan ve aydınlatan güneş simgesi hangisi?",
            accentColor = Color(0xFFF77F00),
            cardBgColor = Color(0xFFFFF6ED),
            rewardFruit = RewardFruit.ORANGE,
            category = LearningCategory.SYMBOLS,
            minAge = 3,
            soundType = "pop",
            teacherExplanation = "Güneş doğunca sabah olur, kuşlar uyanır ve neşeyle oynarız!"
        ),
        LearningItem(
            id = "sym_moon",
            name = "Hilal (Gece)",
            emoji = "🌙",
            soundText = "İyi geceler!",
            questionVoice = "Geceleri gökyüzünde parlayan ve uyku vaktini anlatan ay hangisi?",
            accentColor = Color(0xFF4361EE),
            cardBgColor = Color(0xFFEFF3FF),
            rewardFruit = RewardFruit.BLUEBERRY,
            category = LearningCategory.SYMBOLS,
            minAge = 3,
            soundType = "pop",
            teacherExplanation = "Ay gökyüzüne çıkınca yıldızlar parlar ve tatlı rüyalar başlar!"
        ),
        LearningItem(
            id = "sym_water",
            name = "Su Damlası",
            emoji = "💧",
            soundText = "Tertemiz su!",
            questionVoice = "Temizliği, sağlığı ve yaşamı anlatan su damlası nerede?",
            accentColor = Color(0xFF00B4D8),
            cardBgColor = Color(0xFFEFFBFE),
            rewardFruit = RewardFruit.WATERMELON,
            category = LearningCategory.SYMBOLS,
            minAge = 3,
            soundType = "pop",
            teacherExplanation = "Su içmek ve ellerimizi yıkamak bizi daima sağlıklı tutar!"
        ),
        LearningItem(
            id = "sym_peace",
            name = "Barış / Zafer",
            emoji = "✌️",
            soundText = "Zafer ve barış!",
            questionVoice = "İki parmağımızla yaptığımız neşeli zafer işareti hangisi?",
            accentColor = Color(0xFF2EC4B6),
            cardBgColor = Color(0xFFEFFBF9),
            rewardFruit = RewardFruit.AVOCADO,
            category = LearningCategory.SYMBOLS,
            minAge = 4,
            soundType = "pop",
            teacherExplanation = "Barış işareti dostluğu ve oyunu kazandığımızda duyduğumuz sevinci gösterir!"
        ),
        LearningItem(
            id = "sym_lightning",
            name = "Şimşek (Enerji)",
            emoji = "⚡",
            soundText = "Süper enerji!",
            questionVoice = "Enerjiyi ve gücü simgeleyen sarı şimşek simgesi hangisi?",
            accentColor = Color(0xFFFFBE0B),
            cardBgColor = Color(0xFFFFFCEB),
            rewardFruit = RewardFruit.PINEAPPLE,
            category = LearningCategory.SYMBOLS,
            minAge = 4,
            soundType = "pop",
            teacherExplanation = "Şimşek simgesi hızlı ve enerji dolu olmayı ifade eder!"
        )
    )

    // --- 5. DOĞA VE COĞRAFYA / ÖZEL ŞEKİLLER (Güneş, Ay, Bulut, Su, Gökkuşağı, Dağ, Deniz, Dünya...) ---
    val nature = listOf(
        LearningItem(
            id = "nature_sun",
            name = "Güneş",
            emoji = "☀️",
            soundText = "Sıcak güneş!",
            questionVoice = "Gündüzleri dünyamızı ısıtan sıcacık güneşi bul ve tıkla tatlım!",
            accentColor = Color(0xFFF77F00),
            cardBgColor = Color(0xFFFFF6ED),
            rewardFruit = RewardFruit.ORANGE,
            category = LearningCategory.NATURE,
            minAge = 2,
            soundType = "sparkle",
            teacherExplanation = "Güneş doğunca sabah olur, etraf aydınlanır ve oyun vakti başlar!"
        ),
        LearningItem(
            id = "nature_moon",
            name = "Hilal (Ay)",
            emoji = "🌙",
            soundText = "İyi geceler ayı!",
            questionVoice = "Geceleri gökyüzünde parlayan sevimli hilal ayı göster bakalım!",
            accentColor = Color(0xFF4361EE),
            cardBgColor = Color(0xFFEFF3FF),
            rewardFruit = RewardFruit.BANANA,
            category = LearningCategory.NATURE,
            minAge = 2,
            soundType = "sparkle",
            teacherExplanation = "Ay geceleri gökyüzünü süsler ve uyku vaktini fısıldar!"
        ),
        LearningItem(
            id = "nature_cloud",
            name = "Bulut",
            emoji = "☁️",
            soundText = "Pamuk bulut!",
            questionVoice = "Gökyüzünde pamuk gibi süzülen beyaz bulut nerede tatlım?",
            accentColor = Color(0xFF90E0EF),
            cardBgColor = Color(0xFFF0F9FD),
            rewardFruit = RewardFruit.BLUEBERRY,
            category = LearningCategory.NATURE,
            minAge = 2,
            soundType = "wind",
            teacherExplanation = "Bulutlar su buharından oluşur ve bazen bize yağmur getirir!"
        ),
        LearningItem(
            id = "nature_water",
            name = "Su Damlası",
            emoji = "💧",
            soundText = "Şıp şıp su damlası!",
            questionVoice = "Doğamıza can veren serin su damlasını bul bakalım canım!",
            accentColor = Color(0xFF00B4D8),
            cardBgColor = Color(0xFFEFFBFE),
            rewardFruit = RewardFruit.WATERMELON,
            category = LearningCategory.NATURE,
            minAge = 2,
            soundType = "water",
            teacherExplanation = "Su içmek ve ellerimizi yıkamak bizi hep sağlıklı tutar!"
        ),
        LearningItem(
            id = "nature_rainbow",
            name = "Gökkuşağı",
            emoji = "🌈",
            soundText = "Rengarenk gökkuşağı!",
            questionVoice = "Yağmurdan sonra gökyüzünde beliren rengarenk gökkuşağı hangisi?",
            accentColor = Color(0xFFFF6584),
            cardBgColor = Color(0xFFFFF0F5),
            rewardFruit = RewardFruit.STRAWBERRY,
            category = LearningCategory.NATURE,
            minAge = 3,
            soundType = "sparkle",
            teacherExplanation = "Güneş ışığı yağmur damlalarından geçince rengarenk gökkuşağı oluşur!"
        ),
        LearningItem(
            id = "nature_earth",
            name = "Dünya",
            emoji = "🌍",
            soundText = "Mavi Gezegenimiz!",
            questionVoice = "Üzerinde yaşadığımız masmavi güzel Dünya gezegenimiz nerede?",
            accentColor = Color(0xFF2A9D8F),
            cardBgColor = Color(0xFFEDFCF7),
            rewardFruit = RewardFruit.AVOCADO,
            category = LearningCategory.NATURE,
            minAge = 3,
            soundType = "wind",
            teacherExplanation = "Dünya bizim yuvamızdır; üzerinde denizler, ormanlar ve nehirler vardır!"
        ),
        LearningItem(
            id = "nature_mountain",
            name = "Dağ",
            emoji = "🏔️",
            soundText = "Karlı zirveler!",
            questionVoice = "Zirvesinde bembeyaz kar olan yüksek dağ hangisi tatlım?",
            accentColor = Color(0xFF457B9D),
            cardBgColor = Color(0xFFEFF5F9),
            rewardFruit = RewardFruit.BLUEBERRY,
            category = LearningCategory.NATURE,
            minAge = 3,
            soundType = "wind",
            teacherExplanation = "Dağlar gökyüzüne kadar uzanan kocaman tepe ve kayalardır!"
        ),
        LearningItem(
            id = "nature_sea",
            name = "Deniz & Dalga",
            emoji = "🌊",
            soundText = "Ferahlatıcı dalgalar!",
            questionVoice = "Balıkların yüzdüğü masmavi serin deniz nerede canım benim?",
            accentColor = Color(0xFF0077B6),
            cardBgColor = Color(0xFFE8F4F8),
            rewardFruit = RewardFruit.WATERMELON,
            category = LearningCategory.NATURE,
            minAge = 3,
            soundType = "water",
            teacherExplanation = "Denizler büyük su havzalarıdır, içinde binlerce balık yaşar!"
        ),
        LearningItem(
            id = "nature_tree",
            name = "Ağaç",
            emoji = "🌳",
            soundText = "Koca gövdeli ağaç!",
            questionVoice = "Kuşlara yuva olan yemyeşil ağacı bul ve tıkla bakalım!",
            accentColor = Color(0xFF588157),
            cardBgColor = Color(0xFFF3F7F2),
            rewardFruit = RewardFruit.KIWI,
            category = LearningCategory.NATURE,
            minAge = 2,
            soundType = "wind",
            teacherExplanation = "Ağaçlar bize temiz hava sağlar, meyve verir ve gölgesinde serinletir!"
        ),
        LearningItem(
            id = "nature_flower",
            name = "Çiçek",
            emoji = "🌸",
            soundText = "Mis kokulu çiçek!",
            questionVoice = "Kelebeklerin üzerine konduğu güzel bahar çiçeği nerede?",
            accentColor = Color(0xFFFF4D6D),
            cardBgColor = Color(0xFFFFF0F3),
            rewardFruit = RewardFruit.CHERRY,
            category = LearningCategory.NATURE,
            minAge = 2,
            soundType = "sparkle",
            teacherExplanation = "Çiçekler baharda rengarenk açar ve etrafa harika kokular saçar!"
        ),
        LearningItem(
            id = "nature_star",
            name = "Kutup Yıldızı",
            emoji = "⭐",
            soundText = "Kayan yıldız!",
            questionVoice = "Geceleri gökyüzünde parıldayan ışıltılı yıldız hangisi?",
            accentColor = Color(0xFFFFB703),
            cardBgColor = Color(0xFFFFFBEA),
            rewardFruit = RewardFruit.BANANA,
            category = LearningCategory.NATURE,
            minAge = 2,
            soundType = "sparkle",
            teacherExplanation = "Yıldızlar uzayda yer alan devasa ışık kaynaklarıdır!"
        ),
        LearningItem(
            id = "nature_fire",
            name = "Ateş",
            emoji = "🔥",
            soundText = "Sıcak alevler!",
            questionVoice = "Kamp yaparken etrafında ısındığımız sıcacık ateşi göster bakalım!",
            accentColor = Color(0xFFE63946),
            cardBgColor = Color(0xFFFDF0F1),
            rewardFruit = RewardFruit.ORANGE,
            category = LearningCategory.NATURE,
            minAge = 4,
            soundType = "wind",
            teacherExplanation = "Ateş bize sıcaklık ve ışık verir ama dokunurken dikkatli olmalıyız!"
        ),
        LearningItem(
            id = "nature_island",
            name = "Ada",
            emoji = "🏝️",
            soundText = "Tropik ada!",
            questionVoice = "Etrafı masmavi denizle çevrili palmiyeli ada nerede?",
            accentColor = Color(0xFF06D6A0),
            cardBgColor = Color(0xFFEDFCF7),
            rewardFruit = RewardFruit.PINEAPPLE,
            category = LearningCategory.NATURE,
            minAge = 4,
            soundType = "water",
            teacherExplanation = "Ada, dört bir yanı sularla çevrili kara parçasıdır!"
        ),
        LearningItem(
            id = "nature_volcano",
            name = "Yanardağ (Volkan)",
            emoji = "🌋",
            soundText = "Lav püskürten dağ!",
            questionVoice = "İçinden sıcak lavlar çıkaran heybetli yanardağı bul bakalım!",
            accentColor = Color(0xFFD90429),
            cardBgColor = Color(0xFFFDF0F2),
            rewardFruit = RewardFruit.APPLE,
            category = LearningCategory.NATURE,
            minAge = 5,
            soundType = "wind",
            teacherExplanation = "Yanardağlar yerin altındaki sıcak lavları dışarı püskürten dağlardır!"
        ),
        LearningItem(
            id = "nature_desert",
            name = "Çöl",
            emoji = "🏜️",
            soundText = "Sarı kum tepeleri!",
            questionVoice = "Develerin yürüdüğü uçsuz bucaksız sıcak çöl nerede canım?",
            accentColor = Color(0xFFE76F51),
            cardBgColor = Color(0xFFFFF4F0),
            rewardFruit = RewardFruit.MANGO,
            category = LearningCategory.NATURE,
            minAge = 5,
            soundType = "wind",
            teacherExplanation = "Çöller sarı kum tepeleriyle kaplı çok sıcak ve kurak yerlerdir!"
        )
    )

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
