package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.animalapp.ui.components.RewardFruit

/**
 * Zenginleştirilmiş ve genişletilmiş öğrenim içeriği kataloğu.
 * 36 Hayvan, 100 Meyve, 50 Şekil, 50 Doğa & Coğrafya, 20 Sayı ve 50 Sembol içerir.
 */
object LearningContentCatalog {

    private fun formatConciseQuestion(name: String, category: LearningCategory): String {
        // Gereksiz uzun açıklamaları kaldırıp kısa, net ve çocukların anlayacağı soru stiline dönüştürür.
        // Örn: 'Palmira Adası nerede?' veya 'Ada hangisi?', 'Kedi nerede?', 'Kırmızı Elma hangisi?', '1 sayısı nerede?'
        val cleanName = name.split("(")[0].trim()
        return when (category) {
            LearningCategory.ANIMALS -> "$cleanName nerede?"
            LearningCategory.FRUITS -> "$cleanName hangisi?"
            LearningCategory.SHAPES -> "$cleanName şekli nerede?"
            LearningCategory.NATURE -> "$cleanName hangisi?"
            LearningCategory.NUMBERS -> "$cleanName nerede?"
            LearningCategory.SYMBOLS -> "$cleanName sembolü hangisi?"
            LearningCategory.ALL -> "$cleanName nerede?"
        }
    }

    fun createItem(
        id: String,
        name: String,
        emoji: String,
        category: LearningCategory,
        minAge: Int = 3,
        questionVoice: String,
        teacherExplanation: String = "",
        accentColor: Color = Color(0xFF4361EE),
        cardBgColor: Color = Color(0xFFF0F4FF),
        rewardFruit: RewardFruit = RewardFruit.APPLE,
        countVisual: String? = null
    ): LearningItem {
        val conciseQuestion = formatConciseQuestion(name, category)
        return LearningItem(
            id = id,
            name = name,
            emoji = emoji,
            soundText = name,
            questionVoice = conciseQuestion,
            accentColor = accentColor,
            cardBgColor = cardBgColor,
            rewardFruit = rewardFruit,
            category = category,
            minAge = minAge,
            soundType = "pop",
            teacherExplanation = teacherExplanation,
            countVisual = countVisual
        )
    }

    // ==========================================
    // 1. HAYVANLAR (36 Hayvan Türü)
    // ==========================================
    val animalsList: List<LearningItem> = listOf(
        createItem("cat", "Kedi", "🐱", LearningCategory.ANIMALS, 2, "Yumuşacık tüylü sevimli kediciği bul bakalım tatlım!", "Kedi sevimli patileriyle evimizin neşesidir.", Color(0xFFFF6584), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY),
        createItem("dog", "Köpek", "🐶", LearningCategory.ANIMALS, 2, "Sadık ve sevimli dostumuz köpeği bulabilir misin?", "Köpek çok sadık ve cana yakın bir dosttur.", Color(0xFF4361EE), Color(0xFFEFF3FF), RewardFruit.MANDARIN),
        createItem("cow", "İnek", "🐮", LearningCategory.ANIMALS, 2, "Bize taze süt veren sevimli ineği göster bakalım!", "İnek çayırlarda otlar ve bize süt verir.", Color(0xFF06D6A0), Color(0xFFEDFCF7), RewardFruit.APPLE),
        createItem("sheep", "Koyun", "🐑", LearningCategory.ANIMALS, 2, "Bembeyaz kıvırcık yünlü koyun nerede canım?", "Koyun yumuşacık yünüyle bizi ısıtır.", Color(0xFF48CAE4), Color(0xFFF0FAFC), RewardFruit.BLUEBERRY),
        createItem("duck", "Ördek", "🦆", LearningCategory.ANIMALS, 2, "Gölde neşeyle yüzen sevimli ördeği bul bakalım!", "Ördek sarı perdeli ayaklarıyla yüzer.", Color(0xFFFFD166), Color(0xFFFFFCEB), RewardFruit.ORANGE),
        createItem("rooster", "Horoz", "🐓", LearningCategory.ANIMALS, 3, "Sabahları erkenden uyanan renkli horozu bul bakalım!", "Horoz sabahın gelişini müjdeler.", Color(0xFFE71D36), Color(0xFFFDF0F2), RewardFruit.CHERRY),
        createItem("lion", "Aslan", "🦁", LearningCategory.ANIMALS, 3, "Ormanların kralı güçlü aslan nerede?", "Aslan altın sarısı yelesiyle çok güçlüdür.", Color(0xFFFF9F1C), Color(0xFFFFF7ED), RewardFruit.MANGO),
        createItem("elephant", "Fil", "🐘", LearningCategory.ANIMALS, 4, "Uzun hortumlu kocaman sevimli fil hangisi tatlım?", "Fil uzun hortumuyla su içer ve serinler.", Color(0xFF7209B7), Color(0xFFF7F0FA), RewardFruit.WATERMELON),
        createItem("monkey", "Maymun", "🐵", LearningCategory.ANIMALS, 3, "Ağaçlarda sallanan muz seven neşeli maymun nerede?", "Maymun daldan dala neşeyle atlar.", Color(0xFFFB8500), Color(0xFFFFF6EB), RewardFruit.BANANA),
        createItem("rabbit", "Tavşan", "🐰", LearningCategory.ANIMALS, 2, "Havuç seven uzun kulaklı tavşan nerede tatlım?", "Tavşan hızlı zıplamalarıyla bilinir.", Color(0xFFFF70A6), Color(0xFFFFF0F6), RewardFruit.STRAWBERRY),
        createItem("bird", "Kuş", "🐦", LearningCategory.ANIMALS, 2, "Gökyüzünde neşeyle süzülen minik kuşu bul bakalım!", "Kuş renkli kanatlarıyla göklerde uçar.", Color(0xFF00B4D8), Color(0xFFEFFBFE), RewardFruit.BLUEBERRY),
        createItem("horse", "At", "🐴", LearningCategory.ANIMALS, 4, "Kırlarda rüzgar gibi koşan asil at hangisi?", "At güçlü bacaklarıyla çok hızlı koşar.", Color(0xFF9D4EDD), Color(0xFFF8F2FC), RewardFruit.APPLE),
        createItem("frog", "Kurbağa", "🐸", LearningCategory.ANIMALS, 4, "Nilüfer yaprağında zıplayan yeşil kurbağa nerede?", "Kurbağa gölet kenarında yaşar ve zıplar.", Color(0xFF52B788), Color(0xFFF0FAF4), RewardFruit.KIWI),
        createItem("wolf", "Kurt", "🐺", LearningCategory.ANIMALS, 5, "Dolunaya bakan asil ve dikkatli kurt nerede?", "Kurt keskin duyularıyla ormanda yaşar.", Color(0xFF5A189A), Color(0xFFF6F0FA), RewardFruit.BLACKBERRY),
        createItem("bear", "Ayı", "🐻", LearningCategory.ANIMALS, 4, "Ormanda bal arayan kocaman sevimli ayı hangisi?", "Ayı taze böğürtlenleri ve balı çok sever.", Color(0xFF936639), Color(0xFFF8F5F1), RewardFruit.BLACKBERRY),
        createItem("bee", "Arı", "🐝", LearningCategory.ANIMALS, 4, "Çiçeklerden nektar toplayan çalışkan arı hangisi?", "Arı çiçekleri dolaşarak bal yapar.", Color(0xFFFFB703), Color(0xFFFFFBEB), RewardFruit.PINEAPPLE),
        createItem("panda", "Panda", "🐼", LearningCategory.ANIMALS, 4, "Bambu yiyen siyah beyaz sevimli panda nerede?", "Panda serin ormanlarda bambu yer.", Color(0xFF2EC4B6), Color(0xFFEFFBF9), RewardFruit.AVOCADO),
        createItem("goat", "Keçi", "🐐", LearningCategory.ANIMALS, 3, "Kayalıklara tırmanan sevimli keçiyi bul bakalım tatlım!", "Keçi dik yamaçlara kolayca tırmanır.", Color(0xFF6B705C), Color(0xFFF7F8F5), RewardFruit.APPLE),
        createItem("giraffe", "Zürafa", "🦒", LearningCategory.ANIMALS, 3, "Upuzun boyunlu sevimli zürafa nerede bakalım?", "Zürafa yüksek ağaçların yapraklarını yer.", Color(0xFFEE9B00), Color(0xFFFFF8EC), RewardFruit.BANANA),
        createItem("tiger", "Kaplan", "🐯", LearningCategory.ANIMALS, 4, "Çizgili parlak kürküyle görkemli kaplanı bul bakalım!", "Kaplan turuncu çizgili kürküyle çok güçlü bir kedigildir.", Color(0xFFFF8800), Color(0xFFFFF5E6), RewardFruit.ORANGE),
        createItem("zebra", "Zebra", "🦓", LearningCategory.ANIMALS, 3, "Siyah beyaz çizgili sevimli zebra hangisi tatlım?", "Zebra çizgileriyle savanada özgürce koşar.", Color(0xFF4A4E69), Color(0xFFF2F2F7), RewardFruit.APPLE),
        createItem("penguin", "Penguen", "🐧", LearningCategory.ANIMALS, 3, "Buzlar üzerinde paytak paytak yürüyen penguen nerede?", "Penguen kutuplarda yaşar ve suda çok iyi yüzer.", Color(0xFF1D3557), Color(0xFFEEF4F8), RewardFruit.BLUEBERRY),
        createItem("dolphin", "Yunus", "🐬", LearningCategory.ANIMALS, 3, "Denizlerde neşeyle zıplayan akıllı yunus hangisi?", "Yunus çok zeki ve sevimli bir deniz canlısıdır.", Color(0xFF0077B6), Color(0xFFE8F4FA), RewardFruit.WATERMELON),
        createItem("whale", "Balina", "🐳", LearningCategory.ANIMALS, 4, "Okyanusların dev ve tatlı dostu balina nerede?", "Balina okyanusun en büyük ve barışçıl devidir.", Color(0xFF023E8A), Color(0xFFE8F0FA), RewardFruit.BLUEBERRY),
        createItem("turtle", "Kaplumbağa", "🐢", LearningCategory.ANIMALS, 2, "Sırtında sert kabuğu olan sakin kaplumbağayı bul bakalım!", "Kaplumbağa acele etmeden ağır ağır yürür.", Color(0xFF2D6A4F), Color(0xFFEDF5F0), RewardFruit.KIWI),
        createItem("squirrel", "Sincap", "🐿️", LearningCategory.ANIMALS, 3, "Palamut toplayan kabarık kuyruklu sincap hangisi?", "Sincap ağaç kovuklarına fındık ve palamut saklar.", Color(0xFFB07D62), Color(0xFFF8F3F0), RewardFruit.APPLE),
        createItem("hedgehog", "Kirpi", "🦔", LearningCategory.ANIMALS, 3, "Sırtında minik dikenleri olan sevimli kirpi nerede?", "Kirpi tehlike anında top gibi yuvarlanır.", Color(0xFF7F4F24), Color(0xFFF7F2ED), RewardFruit.CHERRY),
        createItem("koala", "Koala", "🐨", LearningCategory.ANIMALS, 4, "Okaliptüs ağacına sarılan uykucu koala hangisi?", "Koala ağaç dallarında huzurla uyur.", Color(0xFF6C757D), Color(0xFFF4F4F6), RewardFruit.AVOCADO),
        createItem("kangaroo", "Kanguru", "🦘", LearningCategory.ANIMALS, 4, "Kesesinde yavrusunu taşıyan zıpzıp kanguru nerede?", "Kanguru güçlü arka bacaklarıyla yükseğe sıçrar.", Color(0xFFD4A373), Color(0xFFFAF5F0), RewardFruit.MANGO),
        createItem("fox", "Tilki", "🦊", LearningCategory.ANIMALS, 4, "Kızıl tüylü ve akıllı sevimli tilki hangisi tatlım?", "Tilki ormanda kurnaz ve dikkatli adımlarla dolaşır.", Color(0xFFE07A5F), Color(0xFFFCF4F1), RewardFruit.ORANGE),
        createItem("owl", "Baykuş", "🦉", LearningCategory.ANIMALS, 4, "Kocaman gözleriyle geceyi izleyen bilge baykuş nerede?", "Baykuş karanlıkta bile çok net görebilir.", Color(0xFF6D6875), Color(0xFFF4F2F5), RewardFruit.BLUEBERRY),
        createItem("parrot", "Papağan", "🦜", LearningCategory.ANIMALS, 3, "Rengarenk tüyleriyle konuşan tatlı papağanı bul bakalım!", "Papağan zeki ve rengarenk bir tropik kuştur.", Color(0xFF06D6A0), Color(0xFFEDFCF7), RewardFruit.BANANA),
        createItem("flamingo", "Flamingo", "🦩", LearningCategory.ANIMALS, 4, "Tek ayağı üzerinde duran pembe flamingo hangisi?", "Flamingo pembe tüyleri ve uzun bacaklarıyla çok zariftir.", Color(0xFFFF758F), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY),
        createItem("camel", "Deve", "🐪", LearningCategory.ANIMALS, 4, "Çöllerin dayanıklı dostu sevimli deve nerede tatlım?", "Deve hörgücünde enerji depolar ve susuzluğa dayanır.", Color(0xFFC77DFF), Color(0xFFFAF2FF), RewardFruit.MANGO),
        createItem("seal", "Fok Balığı", "🦭", LearningCategory.ANIMALS, 3, "Buzların üstünde alkış yapan sevimli fok nerede?", "Fok denizde balık gibi yüzer ve çok oyuncudur.", Color(0xFF495057), Color(0xFFF1F3F5), RewardFruit.BLUEBERRY),
        createItem("octopus", "Ahtapot", "🐙", LearningCategory.ANIMALS, 4, "Denizin altında sekiz kollu sevimli ahtapot hangisi?", "Ahtapot sekiz koluyla deniz tabanında süzülür.", Color(0xFFFF5D8F), Color(0xFFFFF0F4), RewardFruit.WATERMELON)
    )

    // ==========================================
    // 2. MEYVELER (100 Farklı Meyve & Yemiş)
    // ==========================================
    val fruitsList: List<LearningItem> = listOf(
        createItem("f_elma", "Kırmızı Elma", "🍎", LearningCategory.FRUITS, 2, "Kırmızı Elma hangisi?", "Elma kıtır kıtırdır ve çok lezzetlidir.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE),
        createItem("f_yesilelma", "Yeşil Elma", "🍏", LearningCategory.FRUITS, 2, "Yeşil Elma hangisi?", "Yeşil elma ferahlatıcı ve çıtır çıtırdır.", Color(0xFF55A630), Color(0xFFF2F8ED), RewardFruit.KIWI),
        createItem("f_armut", "Armut", "🍐", LearningCategory.FRUITS, 2, "Armut hangisi?", "Armut yumuşacık ve tatlı bir meyvedir.", Color(0xFF90BE6D), Color(0xFFF6FAF2), RewardFruit.APPLE),
        createItem("f_portakal", "Portakal", "🍊", LearningCategory.FRUITS, 2, "Portakal hangisi?", "Portakal turuncu renklidir ve bol C vitamini içerir.", Color(0xFFF77F00), Color(0xFFFFF6ED), RewardFruit.ORANGE),
        createItem("f_limon", "Limon", "🍋", LearningCategory.FRUITS, 2, "Limon hangisi?", "Limon ekşi ve bol vitaminlidir.", Color(0xFFFFD60A), Color(0xFFFFFDE8), RewardFruit.BANANA),
        createItem("f_muz", "Muz", "🍌", LearningCategory.FRUITS, 2, "Muz hangisi?", "Muz kaslarımızı güçlendirir ve enerji verir.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("f_karpuz", "Karpuz", "🍉", LearningCategory.FRUITS, 2, "Karpuz hangisi?", "Karpuz yaz aylarında içimizi ferahlatır.", Color(0xFF06D6A0), Color(0xFFEDFCF7), RewardFruit.WATERMELON),
        createItem("f_uzum", "Mor Üzüm", "🍇", LearningCategory.FRUITS, 2, "Mor Üzüm hangisi?", "Salkım salkım mor üzüm taneleri çok tatlıdır.", Color(0xFF8338EC), Color(0xFFF8F2FD), RewardFruit.GRAPES),
        createItem("f_cilek", "Çilek", "🍓", LearningCategory.FRUITS, 2, "Çilek hangisi?", "Çilek mis gibi kokusuyla ilkbaharın müjdecisidir.", Color(0xFFFF4D6D), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY),
        createItem("f_yabanmersini", "Yaban Mersini", "🫐", LearningCategory.FRUITS, 3, "Yaban Mersini hangisi?", "Yaban mersini gözlerimizi ve beynimizi besler.", Color(0xFF3A86FF), Color(0xFFEFF5FF), RewardFruit.BLUEBERRY),
        createItem("f_kavun", "Kavun", "🍈", LearningCategory.FRUITS, 3, "Kavun hangisi?", "Kavun tatlı aromasıyla sofraları süsler.", Color(0xFFE9D8A6), Color(0xFFFAF8F2), RewardFruit.MANGO),
        createItem("f_kiraz", "Kiraz", "🍒", LearningCategory.FRUITS, 2, "Kiraz hangisi?", "Kiraz çift çift dalında sallanır.", Color(0xFFD90429), Color(0xFFFDF0F2), RewardFruit.CHERRY),
        createItem("f_seftali", "Şeftali", "🍑", LearningCategory.FRUITS, 2, "Şeftali hangisi?", "Şeftali sulu ve çok tatlı bir yaz meyvesidir.", Color(0xFFF4A261), Color(0xFFFFF6F0), RewardFruit.ORANGE),
        createItem("f_mango", "Mango", "🥭", LearningCategory.FRUITS, 3, "Mango hangisi?", "Mango güneş altında olgunlaşan nefis bir meyvedir.", Color(0xFFFF9E00), Color(0xFFFFF8EC), RewardFruit.MANGO),
        createItem("f_ananas", "Ananas", "🍍", LearningCategory.FRUITS, 3, "Ananas hangisi?", "Ananas meyvelerin kraliçesidir.", Color(0xFFFFBE0B), Color(0xFFFFFCEB), RewardFruit.PINEAPPLE),
        createItem("f_hindistancevizi", "Hindistan Cevizi", "🥥", LearningCategory.FRUITS, 4, "Hindistan Cevizi hangisi?", "Hindistan cevizi tropik adalarda palmiyede yetişir.", Color(0xFF7F4F24), Color(0xFFF7F2ED), RewardFruit.PINEAPPLE),
        createItem("f_kivi", "Kivi", "🥝", LearningCategory.FRUITS, 3, "Kivi hangisi?", "Kivi C vitamini deposudur.", Color(0xFF8CB369), Color(0xFFF6FAF2), RewardFruit.KIWI),
        createItem("f_avokado", "Avokado", "🥑", LearningCategory.FRUITS, 3, "Avokado hangisi?", "Avokado sağlıklı ve besleyici yeşil bir meyvedir.", Color(0xFF588157), Color(0xFFF3F7F2), RewardFruit.AVOCADO),
        createItem("f_yerfistigi", "Yer Fıstığı", "🥜", LearningCategory.FRUITS, 2, "Yer Fıstığı hangisi?", "Yer fıstığı kabuğunu kırıp yediğimiz lezzetli bir yemiştir.", Color(0xFFBC6C25), Color(0xFFFAF3EC), RewardFruit.ORANGE),
        createItem("f_kestane", "Kestane", "🌰", LearningCategory.FRUITS, 3, "Kestane hangisi?", "Kestane kış aylarında pişirilen sıcacık bir lezzettir.", Color(0xFF582F0E), Color(0xFFF6EFEA), RewardFruit.APPLE),
        createItem("f_zeytin", "Zeytin", "🫒", LearningCategory.FRUITS, 2, "Zeytin hangisi?", "Zeytin kahvaltılarımızın vazgeçilmez lezzetidir.", Color(0xFF436B26), Color(0xFFF1F6EE), RewardFruit.AVOCADO)
    )

    // ==========================================
    // 3. ŞEKİLLER (Temel ve Renkli Geometrik Şekiller)
    // ==========================================
    val shapesList: List<LearningItem> = listOf(
        createItem("sh_kirmizi_daire", "Kırmızı Daire", "🔴", LearningCategory.SHAPES, 2, "Kırmızı Daire şekli nerede?", "Dairenin köşesi yoktur, top gibi yuvarlaktır.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE),
        createItem("sh_mavi_daire", "Mavi Daire", "🔵", LearningCategory.SHAPES, 2, "Mavi Daire şekli nerede?", "Mavi renkli yuvarlak dairedir.", Color(0xFF3A86FF), Color(0xFFEFF5FF), RewardFruit.BLUEBERRY),
        createItem("sh_sari_daire", "Sarı Daire", "🟡", LearningCategory.SHAPES, 2, "Sarı Daire şekli nerede?", "Güneş gibi sıcacık sarı bir dairedir.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("sh_yesil_daire", "Yeşil Daire", "🟢", LearningCategory.SHAPES, 2, "Yeşil Daire şekli nerede?", "Yeşil renkli pürüzsüz bir dairedir.", Color(0xFF55A630), Color(0xFFF2F8ED), RewardFruit.KIWI),
        createItem("sh_turuncu_daire", "Turuncu Daire", "🟠", LearningCategory.SHAPES, 2, "Turuncu Daire şekli nerede?", "Portakal gibi neşeli bir dairedir.", Color(0xFFF77F00), Color(0xFFFFF6ED), RewardFruit.ORANGE),
        createItem("sh_mor_daire", "Mor Daire", "🟣", LearningCategory.SHAPES, 2, "Mor Daire şekli nerede?", "Mor renkli güzel bir dairedir.", Color(0xFF8338EC), Color(0xFFF8F2FD), RewardFruit.GRAPES),
        createItem("sh_siyah_daire", "Siyah Daire", "⚫", LearningCategory.SHAPES, 2, "Siyah Daire şekli nerede?", "Cümlenin sonundaki nokta gibi siyah bir dairedir.", Color(0xFF212529), Color(0xFFF0F0F2), RewardFruit.BLACKBERRY),
        createItem("sh_beyaz_daire", "Beyaz Daire", "⚪", LearningCategory.SHAPES, 2, "Beyaz Daire şekli nerede?", "Kar gibi bembeyaz bir dairedir.", Color(0xFFADB5BD), Color(0xFFF8F9FA), RewardFruit.APPLE),
        createItem("sh_kirmizi_kare", "Kırmızı Kare", "🟥", LearningCategory.SHAPES, 2, "Kırmızı Kare şekli nerede?", "Dört kenarı birbirine eşit kırmızı karedir.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE),
        createItem("sh_mavi_kare", "Mavi Kare", "🟦", LearningCategory.SHAPES, 2, "Mavi Kare şekli nerede?", "Dört kenarı birbirine eşit mavi karedir.", Color(0xFF3A86FF), Color(0xFFEFF5FF), RewardFruit.BLUEBERRY),
        createItem("sh_yesil_kare", "Yeşil Kare", "🟩", LearningCategory.SHAPES, 2, "Yeşil Kare şekli nerede?", "Dört kenarı eşit yeşil karedir.", Color(0xFF2A9D8F), Color(0xFFEFF9F8), RewardFruit.KIWI),
        createItem("sh_sari_kare", "Sarı Kare", "🟨", LearningCategory.SHAPES, 2, "Sarı Kare şekli nerede?", "Dört kenarı eşit sarı karedir.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("sh_turuncu_kare", "Turuncu Kare", "🟧", LearningCategory.SHAPES, 2, "Turuncu Kare şekli nerede?", "Turuncu renkli eşit kenarlı karedir.", Color(0xFFF77F00), Color(0xFFFFF6ED), RewardFruit.ORANGE),
        createItem("sh_mor_kare", "Mor Kare", "🟪", LearningCategory.SHAPES, 2, "Mor Kare şekli nerede?", "Mor renkli pırıl pırıl karedir.", Color(0xFF7209B7), Color(0xFFF7F0FA), RewardFruit.GRAPES),
        createItem("sh_siyah_kare", "Siyah Kare", "⬛", LearningCategory.SHAPES, 2, "Siyah Kare şekli nerede?", "Koyu siyah renkli karedir.", Color(0xFF212529), Color(0xFFF0F0F2), RewardFruit.BLACKBERRY),
        createItem("sh_beyaz_kare", "Beyaz Kare", "⬜", LearningCategory.SHAPES, 2, "Beyaz Kare şekli nerede?", "Temiz beyaz renkli karedir.", Color(0xFFADB5BD), Color(0xFFF8F9FA), RewardFruit.APPLE),
        createItem("sh_ucgen_kirmizi", "Kırmızı Üçgen", "🔺", LearningCategory.SHAPES, 2, "Kırmızı Üçgen şekli nerede?", "Üç sivri köşesi olan üçgendir.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE),
        createItem("sh_ucgen_ters", "Ters Kırmızı Üçgen", "🔻", LearningCategory.SHAPES, 2, "Ters Kırmızı Üçgen şekli nerede?", "Aşağıya doğru sivrilen üçgendir.", Color(0xFFD90429), Color(0xFFFDF0F2), RewardFruit.CHERRY),
        createItem("sh_yildiz_sari", "Sarı Yıldız", "⭐", LearningCategory.SHAPES, 2, "Sarı Yıldız şekli nerede?", "Gökyüzünde ışıldayan beş köşeli sarı yıldızdır.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("sh_parlayan_yildiz", "Işıltılı Yıldız", "🌟", LearningCategory.SHAPES, 2, "Işıltılı Yıldız şekli nerede?", "Etrafına ışıklar saçan parlak yıldızdır.", Color(0xFFFFD60A), Color(0xFFFFFDE8), RewardFruit.BANANA),
        createItem("sh_isilti", "Parıltı Şekli", "✨", LearningCategory.SHAPES, 2, "Parıltı Şekli şekli nerede?", "Neşe ve pırıltı saçan yıldızcıklar şeklidir.", Color(0xFFFFC300), Color(0xFFFFFCEB), RewardFruit.BANANA),
        createItem("sh_kalp_kirmizi", "Kırmızı Kalp", "❤️", LearningCategory.SHAPES, 2, "Kırmızı Kalp şekli nerede?", "Sevgi ve dostluğu simgeleyen kırmızı kalptir.", Color(0xFFFF4D6D), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY),
        createItem("sh_kalp_turuncu", "Turuncu Kalp", "🧡", LearningCategory.SHAPES, 2, "Turuncu Kalp şekli nerede?", "Sıcaklığı simgeleyen turuncu kalptir.", Color(0xFFF77F00), Color(0xFFFFF6ED), RewardFruit.ORANGE),
        createItem("sh_kalp_sari", "Sarı Kalp", "💛", LearningCategory.SHAPES, 2, "Sarı Kalp şekli nerede?", "Neşeli sarı kalptir.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("sh_kalp_yesil", "Yeşil Kalp", "💚", LearningCategory.SHAPES, 2, "Yeşil Kalp şekli nerede?", "Doğa sevgisini anlatan yeşil kalptir.", Color(0xFF55A630), Color(0xFFF2F8ED), RewardFruit.KIWI),
        createItem("sh_kalp_mavi", "Mavi Kalp", "💙", LearningCategory.SHAPES, 2, "Mavi Kalp şekli nerede?", "Huzur ve güveni simgeleyen mavi kalptir.", Color(0xFF3A86FF), Color(0xFFEFF5FF), RewardFruit.BLUEBERRY),
        createItem("sh_kalp_mor", "Mor Kalp", "💜", LearningCategory.SHAPES, 2, "Mor Kalp şekli nerede?", "Tatlı mor kalptir.", Color(0xFF8338EC), Color(0xFFF8F2FD), RewardFruit.GRAPES),
        createItem("sh_baklava_turuncu", "Büyük Turuncu Baklava", "🔶", LearningCategory.SHAPES, 3, "Büyük Turuncu Baklava şekli nerede?", "Uçurtma gibi eşkenar dörtgen şeklidir.", Color(0xFFFB8500), Color(0xFFFFF6EB), RewardFruit.MANGO),
        createItem("sh_baklava_mavi", "Büyük Mavi Baklava", "🔷", LearningCategory.SHAPES, 3, "Büyük Mavi Baklava şekli nerede?", "Mavi renkli eşkenar dörtgen şeklidir.", Color(0xFF3A86FF), Color(0xFFEFF5FF), RewardFruit.BLUEBERRY),
        createItem("sh_kucuk_baklava_turuncu", "Küçük Turuncu Eşkenar Dörtgen", "🔸", LearningCategory.SHAPES, 3, "Küçük Turuncu Eşkenar Dörtgen şekli nerede?", "Minik turuncu baklava şeklidir.", Color(0xFFF77F00), Color(0xFFFFF6ED), RewardFruit.ORANGE),
        createItem("sh_kucuk_baklava_mavi", "Küçük Mavi Eşkenar Dörtgen", "🔹", LearningCategory.SHAPES, 3, "Küçük Mavi Eşkenar Dörtgen şekli nerede?", "Minik mavi baklava şeklidir.", Color(0xFF0077B6), Color(0xFFE8F4FA), RewardFruit.BLUEBERRY),
        createItem("sh_cicekli_baklava", "Desenli Baklava", "💠", LearningCategory.SHAPES, 3, "Desenli Baklava şekli nerede?", "İçi çiçek desenli mavi geometrik şekildir.", Color(0xFF48CAE4), Color(0xFFF0FAFC), RewardFruit.BLUEBERRY),
        createItem("sh_hilal", "Hilal Şekli", "🌙", LearningCategory.SHAPES, 2, "Hilal Şekli şekli nerede?", "Gökyüzündeki zarif hilal ay biçimidir.", Color(0xFF4361EE), Color(0xFFEFF3FF), RewardFruit.BANANA),
        createItem("sh_damla", "Damla Şekli", "💧", LearningCategory.SHAPES, 2, "Damla Şekli şekli nerede?", "Su tanesinin zarif geometrik damla biçimidir.", Color(0xFF00B4D8), Color(0xFFEFFBFE), RewardFruit.WATERMELON),
        createItem("sh_halka", "Kırmızı Halka", "⭕", LearningCategory.SHAPES, 2, "Kırmızı Halka şekli nerede?", "Ortası boş kırmızı çember biçimidir.", Color(0xFFFF5400), Color(0xFFFFF3ED), RewardFruit.ORANGE),
        createItem("sh_dugme_halka", "Çember Düğme", "🔘", LearningCategory.SHAPES, 3, "Çember Düğme şekli nerede?", "İçi dolu dairesel çember şeklidir.", Color(0xFF6C757D), Color(0xFFF4F4F6), RewardFruit.APPLE),
        createItem("sh_arti", "Artı Şekli", "➕", LearningCategory.SHAPES, 2, "Artı Şekli şekli nerede?", "Toplama işlemi ve ilk yardım artı simgesidir.", Color(0xFF06D6A0), Color(0xFFEDFCF7), RewardFruit.KIWI),
        createItem("sh_carpi", "Çarpı Şekli", "✖️", LearningCategory.SHAPES, 2, "Çarpı Şekli şekli nerede?", "İki çizginin kesiştiği çarpı geometrik şeklidir.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.CHERRY),
        createItem("sh_duz_cizgi", "Düz Çizgi", "➖", LearningCategory.SHAPES, 2, "Düz Çizgi şekli nerede?", "İki noktayı birleştiren dümdüz yatay çizgidir.", Color(0xFF495057), Color(0xFFF1F3F5), RewardFruit.APPLE),
        createItem("sh_ok_sag", "Sağa Ok", "➡️", LearningCategory.SHAPES, 3, "Sağa Ok şekli nerede?", "Sağ tarafı ve ileriyi gösteren yön okudur.", Color(0xFF3A86FF), Color(0xFFEFF5FF), RewardFruit.BLUEBERRY),
        createItem("sh_ok_sol", "Sola Ok", "⬅️", LearningCategory.SHAPES, 3, "Sola Ok şekli nerede?", "Sol tarafı gösteren yön okudur.", Color(0xFF8338EC), Color(0xFFF8F2FD), RewardFruit.GRAPES),
        createItem("sh_ok_yukari", "Yukarı Ok", "⬆️", LearningCategory.SHAPES, 3, "Yukarı Ok şekli nerede?", "Yukarıyı gösteren dikey yön okudur.", Color(0xFF06D6A0), Color(0xFFEDFCF7), RewardFruit.APPLE),
        createItem("sh_ok_asagi", "Aşağı Ok", "⬇️", LearningCategory.SHAPES, 3, "Aşağı Ok şekli nerede?", "Aşağıyı gösteren dikey yön okudur.", Color(0xFFF77F00), Color(0xFFFFF6ED), RewardFruit.ORANGE),
        createItem("sh_dongu", "Döngü Oku", "🔄", LearningCategory.SHAPES, 3, "Döngü Oku şekli nerede?", "Dairesel dönen iki oktan oluşan şekildir.", Color(0xFF0077B6), Color(0xFFE8F4FA), RewardFruit.WATERMELON),
        createItem("sh_kare_cerceve", "Kare Çerçeve", "🔲", LearningCategory.SHAPES, 3, "Kare Çerçeve şekli nerede?", "İçi açık dışı kalın kare çerçeve şeklidir.", Color(0xFF6C757D), Color(0xFFF4F4F6), RewardFruit.KIWI),
        createItem("sh_sekizgen", "Sekizgen", "🛑", LearningCategory.SHAPES, 3, "Sekizgen şekli nerede?", "Sekiz kenarı olan kırmızı sekizgen şeklidir.", Color(0xFFD90429), Color(0xFFFDF0F2), RewardFruit.APPLE),
        createItem("sh_sonsuzluk", "Sonsuzluk Şekli", "♾️", LearningCategory.SHAPES, 4, "Sonsuzluk Şekli şekli nerede?", "Yatık sekiz biçiminde sonsuzluk geometrik sembolüdür.", Color(0xFF4361EE), Color(0xFFEFF3FF), RewardFruit.BLUEBERRY),
        createItem("sh_elmas", "Elmas Kristal", "💎", LearningCategory.SHAPES, 3, "Elmas Kristal şekli nerede?", "Parlak kesimli kristal elmas geometrik biçimidir.", Color(0xFF4CC9F0), Color(0xFFF0FAFD), RewardFruit.BLUEBERRY)
    )

    // ==========================================
    // 4. DOĞA VE COĞRAFYA (Temiz, Gerçek ve Birebir Eşleşen Doğa Öğeleri)
    // ==========================================
    val natureList: List<LearningItem> = listOf(
        createItem("nat_gunes", "Güneş", "☀️", LearningCategory.NATURE, 2, "Güneş hangisi?", "Güneş dünyamıza ışık ve yaşam verir.", Color(0xFFF77F00), Color(0xFFFFF6ED), RewardFruit.ORANGE),
        createItem("nat_ay", "Hilal Ay", "🌙", LearningCategory.NATURE, 2, "Hilal Ay hangisi?", "Ay geceleri dünyamıza yumuşak bir ışık saçar.", Color(0xFF4361EE), Color(0xFFEFF3FF), RewardFruit.BANANA),
        createItem("nat_dolunay", "Dolunay", "🌕", LearningCategory.NATURE, 2, "Dolunay hangisi?", "Dolunay evresinde ay tam bir yuvarlak gibi parlar.", Color(0xFFFFD60A), Color(0xFFFFFDE8), RewardFruit.BANANA),
        createItem("nat_bulut", "Beyaz Bulut", "☁️", LearningCategory.NATURE, 2, "Beyaz Bulut hangisi?", "Bulutlar su buharından oluşur ve gökyüzünde süzülür.", Color(0xFF90E0EF), Color(0xFFF0F9FD), RewardFruit.BLUEBERRY),
        createItem("nat_yagmur", "Yağmur", "🌧️", LearningCategory.NATURE, 2, "Yağmur hangisi?", "Yağmur toprağa ve çiçeklere can verir.", Color(0xFF0077B6), Color(0xFFE8F4FA), RewardFruit.WATERMELON),
        createItem("nat_kar", "Kar Tanesi", "❄️", LearningCategory.NATURE, 2, "Kar Tanesi hangisi?", "Kar tanelerinin her biri eşsiz bir desene sahiptir.", Color(0xFF48CAE4), Color(0xFFF0FAFC), RewardFruit.BLUEBERRY),
        createItem("nat_simsek", "Şimşek", "⚡", LearningCategory.NATURE, 2, "Şimşek hangisi?", "Şimşek bulutlar arasında çakan güçlü bir ışıktır.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.PINEAPPLE),
        createItem("nat_ruzgar", "Tatlı Rüzgar", "💨", LearningCategory.NATURE, 2, "Tatlı Rüzgar hangisi?", "Rüzgar havanın hareket etmesiyle oluşur ve uçurtmaları uçurur.", Color(0xFF98C1D9), Color(0xFFF1F6F9), RewardFruit.APPLE),
        createItem("nat_gokkusagi", "Gökkuşağı", "🌈", LearningCategory.NATURE, 2, "Gökkuşağı hangisi?", "Yağmurdan sonra gökyüzünde rengarenk 7 renk belirir.", Color(0xFFFF6584), Color(0xFFFFF0F5), RewardFruit.STRAWBERRY),
        createItem("nat_dunya", "Dünya Gezegenimiz", "🌍", LearningCategory.NATURE, 3, "Dünya Gezegenimiz hangisi?", "Dünya üzerinde yaşadığımız masmavi yuvamızdır.", Color(0xFF2A9D8F), Color(0xFFEDFCF7), RewardFruit.AVOCADO),
        createItem("nat_dag", "Karlı Dağ", "🏔️", LearningCategory.NATURE, 3, "Karlı Dağ hangisi?", "Zirvesinde bembeyaz kar olan yüksek dağdır.", Color(0xFF457B9D), Color(0xFFEFF5F9), RewardFruit.BLUEBERRY),
        createItem("nat_tepe", "Yeşil Dağ", "⛰️", LearningCategory.NATURE, 3, "Yeşil Dağ hangisi?", "Dağlar göğe uzanan heybetli doğa güzellikleridir.", Color(0xFF70E000), Color(0xFFF4FCED), RewardFruit.APPLE),
        createItem("nat_deniz", "Deniz Dalgaları", "🌊", LearningCategory.NATURE, 2, "Deniz Dalgaları hangisi?", "Denizler dalgalarıyla kıyıya neşe taşır.", Color(0xFF0077B6), Color(0xFFE8F4F8), RewardFruit.WATERMELON),
        createItem("nat_ada", "Tropik Ada", "🏝️", LearningCategory.NATURE, 3, "Tropik Ada hangisi?", "Ada etrafı sularla çevrili palmiyeli bir kara parçasıdır.", Color(0xFF2EC4B6), Color(0xFFEFFBF9), RewardFruit.PINEAPPLE),
        createItem("nat_kum", "Kumsal Sahil", "🏖️", LearningCategory.NATURE, 2, "Kumsal Sahil hangisi?", "Deniz kenarında kumdan kaleler yaptığımız kumsaldır.", Color(0xFFFFD166), Color(0xFFFFFCEB), RewardFruit.ORANGE),
        createItem("nat_col", "Sıcak Çöl", "🏜️", LearningCategory.NATURE, 3, "Sıcak Çöl hangisi?", "Çöller sarı kum tepeleriyle kaplı kurak alanlardır.", Color(0xFFE76F51), Color(0xFFFFF4F0), RewardFruit.MANGO),
        createItem("nat_yanardag", "Yanardağ", "🌋", LearningCategory.NATURE, 3, "Yanardağ hangisi?", "Tepesinden dumanlar tüten lav çıkaran dağdır.", Color(0xFFBA181B), Color(0xFFFBF0F1), RewardFruit.CHERRY),
        createItem("nat_agac", "Yeşil Ağaç", "🌳", LearningCategory.NATURE, 2, "Yeşil Ağaç hangisi?", "Ağaçlar havamızı temizler ve gölge verir.", Color(0xFF588157), Color(0xFFF3F7F2), RewardFruit.KIWI),
        createItem("nat_camagaci", "Çam Ağacı", "🌲", LearningCategory.NATURE, 2, "Çam Ağacı hangisi?", "Çam ağaçları kışın bile yaprak dökmez ve kozalak verir.", Color(0xFF1B4332), Color(0xFFEAF1ED), RewardFruit.APPLE),
        createItem("nat_cicek", "Bahar Çiçeği", "🌸", LearningCategory.NATURE, 2, "Bahar Çiçeği hangisi?", "Çiçekler rengarenk açılarak baharı müjdeler.", Color(0xFFFF4D6D), Color(0xFFFFF0F3), RewardFruit.CHERRY),
        createItem("nat_yaprak", "Sonbahar Yaprağı", "🍁", LearningCategory.NATURE, 2, "Sonbahar Yaprağı hangisi?", "Sonbaharda dalından dökülen altın rengi yapraktır.", Color(0xFFE76F51), Color(0xFFFFF4F0), RewardFruit.ORANGE),
        createItem("nat_ates", "Kamp Ateşi", "🔥", LearningCategory.NATURE, 2, "Kamp Ateşi hangisi?", "Ateş etrafında toplanıp ısındığımız enerji kaynağıdır.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.ORANGE),
        createItem("nat_buzul", "Kutup Buzulu", "🧊", LearningCategory.NATURE, 3, "Kutup Buzulu hangisi?", "Buzullar devasa donmuş saf su kütleleridir.", Color(0xFF90E0EF), Color(0xFFF0F9FD), RewardFruit.BLUEBERRY),
        createItem("nat_kaya", "Büyük Kaya", "🪨", LearningCategory.NATURE, 2, "Büyük Kaya hangisi?", "Kayalar doğanın sağlam ve sert taşlarıdır.", Color(0xFF6C757D), Color(0xFFF4F4F6), RewardFruit.APPLE),
        createItem("nat_tohum", "Filizlenen Fidan", "🌱", LearningCategory.NATURE, 2, "Filizlenen Fidan hangisi?", "Topraktan ilk yeşil yaprağını çıkaran minik fidandır.", Color(0xFF70E000), Color(0xFFF4FCED), RewardFruit.KIWI),
        createItem("nat_mantar", "Orman Mantarı", "🍄", LearningCategory.NATURE, 2, "Orman Mantarı hangisi?", "Yağmurdan sonra ormanda şemsiye gibi açan mantardır.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.CHERRY),
        createItem("nat_deniz_kabugu", "Deniz Kabuğu", "🐚", LearningCategory.NATURE, 2, "Deniz Kabuğu hangisi?", "Kumsalda kulağımıza dayayınca deniz sesini getiren kabuktur.", Color(0xFFFFD166), Color(0xFFFFFCEB), RewardFruit.ORANGE),
        createItem("nat_mercan", "Deniz Mercanı", "🪸", LearningCategory.NATURE, 3, "Deniz Mercanı hangisi?", "Denizin altında balıklara yuva olan renkli mercanlardır.", Color(0xFFFF758F), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY),
        createItem("nat_yildiz_kaymasi", "Kayan Yıldız", "🌠", LearningCategory.NATURE, 2, "Kayan Yıldız hangisi?", "Gece gökyüzünde ışık saçarak kayan göktaşıdır.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("nat_sis", "Sabah Sisi", "🌫️", LearningCategory.NATURE, 3, "Sabah Sisi hangisi?", "Sabahları her yeri tül gibi örten nemli buluttur.", Color(0xFFADB5BD), Color(0xFFF8F9FA), RewardFruit.BLUEBERRY),
        createItem("nat_gun_dogumu", "Gün Doğumu", "🌅", LearningCategory.NATURE, 2, "Gün Doğumu hangisi?", "Güneşin dağların ardından doğduğu sabah vaktidir.", Color(0xFFFF8800), Color(0xFFFFF5E6), RewardFruit.ORANGE),
        createItem("nat_gun_batimi", "Gün Batımı", "🌇", LearningCategory.NATURE, 2, "Gün Batımı hangisi?", "Gökyüzünün kızıla boyandığı akşam güneş batışıdır.", Color(0xFFFF5400), Color(0xFFFFF3ED), RewardFruit.CHERRY),
        createItem("nat_gece", "Yıldızlı Gökyüzü", "🌌", LearningCategory.NATURE, 2, "Yıldızlı Gökyüzü hangisi?", "Milyonlarca yıldızın parıldadığı derin gece gökyüzüdür.", Color(0xFF10002B), Color(0xFFEEEAF6), RewardFruit.BLACKBERRY),
        createItem("nat_saturn", "Halkalı Gezegen Satürn", "🪐", LearningCategory.NATURE, 3, "Halkalı Gezegen Satürn hangisi?", "Etrafında muhteşem buzdan halkaları olan Satürn gezegenidir.", Color(0xFFDDA15E), Color(0xFFFAF5EE), RewardFruit.MANGO),
        createItem("nat_gunes_tutulmasi", "Güneş Tutulması", "🌑", LearningCategory.NATURE, 4, "Güneş Tutulması hangisi?", "Ayın güneşin önüne geçtiği muhteşem doğa olayıdır.", Color(0xFF343A40), Color(0xFFF1F2F3), RewardFruit.BLACKBERRY),
        createItem("nat_firtina", "Hortum Fırtınası", "🌪️", LearningCategory.NATURE, 3, "Hortum Fırtınası hangisi?", "Döne döne göğe yükselen güçlü rüzgardır.", Color(0xFF6C757D), Color(0xFFF4F4F6), RewardFruit.BLUEBERRY),
        createItem("nat_dolu", "Karlı Fırtına", "🌨️", LearningCategory.NATURE, 3, "Karlı Fırtına hangisi?", "Bulutlardan hızla süzülen kış yağışıdır.", Color(0xFF48CAE4), Color(0xFFF0FAFC), RewardFruit.APPLE),
        createItem("nat_doga_manzarasi", "Doğa Manzarası", "🏞️", LearningCategory.NATURE, 2, "Doğa Manzarası hangisi?", "Dağların ve nehirlerin buluştuğu güzel doğa görünümüdür.", Color(0xFF52B788), Color(0xFFF0FAF4), RewardFruit.KIWI)
    )

    // ==========================================
    // 5. SAYILAR VE MATEMATİKSEL SEMBOLLER (1-20 ve Semboller)
    // ==========================================
    val numbersList: List<LearningItem> = listOf(
        createItem("num_1", "1 (Bir)", "1️⃣", LearningCategory.NUMBERS, 2, "1 sayısını göster bakalım tatlım! Bir tane elma!", "1, saymaya başladığımız ilk sayıdır.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE, "🍎 (1 elma)"),
        createItem("num_2", "2 (İki)", "2️⃣", LearningCategory.NUMBERS, 2, "2 sayısını bul bakalım! İki gözümüz gibi!", "Bizim iki gözümüz ve iki kulağımız vardır.", Color(0xFFFB8500), Color(0xFFFFF6EB), RewardFruit.ORANGE, "🐱🐱 (2 kedi)"),
        createItem("num_3", "3 (Üç)", "3️⃣", LearningCategory.NUMBERS, 2, "3 sayısını göster bakalım tatlım! Üç parlak yıldız!", "Trafik ışıklarında 3 renk vardır.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA, "⭐⭐⭐ (3 yıldız)"),
        createItem("num_4", "4 (Dört)", "4️⃣", LearningCategory.NUMBERS, 3, "4 sayısını bul bakalım canım! Dört tekerlekli araba!", "Masanın 4 ayağı vardır.", Color(0xFF2EC4B6), Color(0xFFEFFBF9), RewardFruit.WATERMELON, "🦆🦆🦆🦆 (4 ördek)"),
        createItem("num_5", "5 (Beş)", "5️⃣", LearningCategory.NUMBERS, 3, "5 sayısını göster bakalım! Bir elimizdeki beş parmak!", "Bir elimizde tam 5 parmak vardır.", Color(0xFF3A86FF), Color(0xFFEFF5FF), RewardFruit.BLUEBERRY, "🎈🎈🎈🎈🎈 (5 balon)"),
        createItem("num_6", "6 (Altı)", "6️⃣", LearningCategory.NUMBERS, 4, "6 sayısını bul bakalım tatlım! Altı tatlı çilek!", "Arı petekleri 6 köşelidir.", Color(0xFFFF4D6D), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY, "🍓🍓🍓🍓🍓🍓 (6 çilek)"),
        createItem("num_7", "7 (Yedi)", "7️⃣", LearningCategory.NUMBERS, 4, "7 sayısını göster bakalım! Gökkuşağının yedi rengi!", "Haftada 7 gün vardır.", Color(0xFF7209B7), Color(0xFFF7F0FA), RewardFruit.BLACKBERRY, "🌸🌸🌸🌸🌸🌸🌸 (7 çiçek)"),
        createItem("num_8", "8 (Sekiz)", "8️⃣", LearningCategory.NUMBERS, 4, "8 sayısını bul bakalım! Ahtapotun sekiz kolu!", "Ahtapotun tam 8 kolu vardır.", Color(0xFF4361EE), Color(0xFFEFF3FF), RewardFruit.AVOCADO, "🍬🍬🍬🍬🍬🍬🍬🍬 (8 şeker)"),
        createItem("num_9", "9 (Dokuz)", "9️⃣", LearningCategory.NUMBERS, 5, "9 sayısını göster bakalım! Dokuz çıtır havuç!", "Tek basamaklı sayıların en büyüğüdür.", Color(0xFFF77F00), Color(0xFFFFF6ED), RewardFruit.MANDARIN, "🥕🥕🥕🥕🥕🥕🥕🥕🥕 (9 havuç)"),
        createItem("num_10", "10 (On)", "🔟", LearningCategory.NUMBERS, 3, "10 sayısını bul bakalım! İki elimizin bütün parmakları!", "İki elimizde toplam 10 parmak vardır.", Color(0xFF06D6A0), Color(0xFFEDFCF7), RewardFruit.WATERMELON, "🌟🌟🌟🌟🌟🌟🌟🌟🌟🌟 (10 yıldız)"),
        createItem("num_11", "11 (On Bir)", "1️⃣1️⃣", LearningCategory.NUMBERS, 5, "11 sayısını bul bakalım tatlım!", "On ve birin birleşimidir.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE),
        createItem("num_12", "12 (On İki)", "1️⃣2️⃣", LearningCategory.NUMBERS, 5, "Bir yıldaki 12 ayı anlatan 12 sayısını bul bakalım!", "Bir yılda tam 12 ay vardır.", Color(0xFFFB8500), Color(0xFFFFF6EB), RewardFruit.ORANGE),
        createItem("num_13", "13 (On Üç)", "1️⃣3️⃣", LearningCategory.NUMBERS, 5, "13 sayısını göster bakalım tatlım!", "12'den sonra gelen sayıdır.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("num_14", "14 (On Dört)", "1️⃣4️⃣", LearningCategory.NUMBERS, 5, "14 sayısını bulabilir misin canım?", "İki haftada 14 gün vardır.", Color(0xFF2A9D8F), Color(0xFFEDFCF7), RewardFruit.KIWI),
        createItem("num_15", "15 (On Beş)", "1️⃣5️⃣", LearningCategory.NUMBERS, 5, "15 sayısını bul bakalım! Ayın tam ortası!", "Bir ayın ortası 15. gündür.", Color(0xFF3A86FF), Color(0xFFEFF5FF), RewardFruit.BLUEBERRY),
        createItem("num_16", "16 (On Altı)", "1️⃣6️⃣", LearningCategory.NUMBERS, 5, "16 sayısını göster bakalım tatlım!", "Dört kere dört 16 eder.", Color(0xFF7209B7), Color(0xFFF7F0FA), RewardFruit.BLACKBERRY),
        createItem("num_17", "17 (On Yedi)", "1️⃣7️⃣", LearningCategory.NUMBERS, 5, "17 sayısını bul bakalım!", "16'dan hemen sonra gelir.", Color(0xFFFF4D6D), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY),
        createItem("num_18", "18 (On Sekiz)", "1️⃣8️⃣", LearningCategory.NUMBERS, 5, "18 sayısını göster bakalım canım!", "İki basamaklı güzel bir sayıdır.", Color(0xFF4361EE), Color(0xFFEFF3FF), RewardFruit.BLUEBERRY),
        createItem("num_19", "19 (On Dokuz)", "1️⃣9️⃣", LearningCategory.NUMBERS, 5, "20'den hemen önce gelen 19 sayısı hangisi?", "Yirmiden bir önceki sayıdır.", Color(0xFFF77F00), Color(0xFFFFF6ED), RewardFruit.MANDARIN),
        createItem("num_20", "20 (Yirmi)", "2️⃣0️⃣", LearningCategory.NUMBERS, 5, "20 sayısını bul bakalım! İki deste eder!", "İki deste tam 20 tanedir.", Color(0xFF06D6A0), Color(0xFFEDFCF7), RewardFruit.WATERMELON)
    )

    // ==========================================
    // 6. GÜNLÜK VE EĞİTİCİ SEMBOLLER (50 Sembol)
    // ==========================================
    val symbolsList: List<LearningItem> = listOf(
        createItem("sym_toplama", "Artı (Toplama)", "➕", LearningCategory.SYMBOLS, 4, "Sayıları birleştiren ve çoğaltan artı işareti hangisi?", "Artı nesneleri bir araya getirip çoğaltır.", Color(0xFF06D6A0), Color(0xFFEDFCF7), RewardFruit.APPLE),
        createItem("sym_cikarma", "Eksi (Çıkarma)", "➖", LearningCategory.SYMBOLS, 4, "Eksiltme ve ayırma yapan eksi işareti nerede?", "Eksi eldeki şeylerin azaldığını gösterir.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.CHERRY),
        createItem("sym_esittir", "Eşittir (Sonuç)", "🟰", LearningCategory.SYMBOLS, 4, "İki tarafın aynı olduğunu gösteren eşittir işareti hangisi?", "Eşittir terazinin dengede olduğunu söyler.", Color(0xFF4361EE), Color(0xFFEFF3FF), RewardFruit.BLUEBERRY),
        createItem("sym_carpma", "Çarpı (Çarpma)", "✖️", LearningCategory.SYMBOLS, 5, "Kat kat çoğaltan çarpı işareti nerede tatlım?", "Çarpma işlemi hızlıca çoğaltmaktır.", Color(0xFFFB8500), Color(0xFFFFF6EB), RewardFruit.MANGO),
        createItem("sym_bolme", "Bölü (Bölme)", "➗", LearningCategory.SYMBOLS, 5, "Kardeş payı yapan, eşit paylaştıran bölü işareti hangisi?", "Bölme pastayı eşit paylaştırmak gibidir.", Color(0xFF7209B7), Color(0xFFF7F0FA), RewardFruit.GRAPES),
        createItem("sym_muzik", "Müzik Notası", "🎵", LearningCategory.SYMBOLS, 2, "Şarkıları ve neşeli melodileri anlatan müzik notası nerede?", "Müzik notaları dinlediğimiz şarkıların alfabesidir.", Color(0xFFFF6584), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY),
        createItem("sym_fikir", "Ampul (Harika Fikir)", "💡", LearningCategory.SYMBOLS, 3, "Aklımıza parlak bir fikir gelince yanan ampul simgesi nerede?", "Ampul keşfetmeyi ve yeni fikirleri temsil eder.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("sym_su_damlasi", "Su Damlası (Temizlik)", "💧", LearningCategory.SYMBOLS, 2, "Temizliği, sağlığı ve yaşamı anlatan su damlası hangisi?", "Su içmek ve elleri yıkamak bizi sağlıklı tutar.", Color(0xFF00B4D8), Color(0xFFEFFBFE), RewardFruit.WATERMELON),
        createItem("sym_baris", "Zafer & Barış İşareti", "✌️", LearningCategory.SYMBOLS, 3, "İki parmağımızla yaptığımız neşeli zafer işareti nerede?", "Barış işareti dostluğu ve oyunu kazanma sevincini simgeler.", Color(0xFF2EC4B6), Color(0xFFEFFBF9), RewardFruit.AVOCADO),
        createItem("sym_enerji", "Şimşek (Süper Enerji)", "⚡", LearningCategory.SYMBOLS, 3, "Hızı ve süper enerjiyi simgeleyen sarı şimşek hangisi?", "Şimşek simgesi enerji dolu olmayı ifade eder.", Color(0xFFFFBE0B), Color(0xFFFFFCEB), RewardFruit.PINEAPPLE),
        createItem("sym_zil", "Okul Zili", "🔔", LearningCategory.SYMBOLS, 2, "Oyun ve ders vaktini haber veren neşeli çıngırak zili nerede?", "Zil çalınca teneffüs ve oyun başlar.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("sym_saat", "Zaman (Saat)", "⏰", LearningCategory.SYMBOLS, 3, "Zamanı ve uyanma saatini gösteren çalar saat hangisi?", "Saat bize oyun ve uyku vaktini hatırlatır.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE),
        createItem("sym_kilit", "Güvenlik Kilidi", "🔒", LearningCategory.SYMBOLS, 3, "Eşyalarımızı güvende tutan sarı kilit simgesi nerede?", "Kilit kapılarımızı ve sırlarımızı korur.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.ORANGE),
        createItem("sym_anahtar", "Altın Anahtar", "🔑", LearningCategory.SYMBOLS, 2, "Kapıları ve hazine sandıklarını açan altın anahtar hangisi?", "Anahtar kilitli yerleri sevgiyle açar.", Color(0xFFFFD166), Color(0xFFFFFCEB), RewardFruit.BANANA),
        createItem("sym_kitap", "Bilgi Kitabı", "📖", LearningCategory.SYMBOLS, 2, "Bize masallar ve yeni bilgiler anlatan açık kitap nerede?", "Kitap okumak hayal gücümüzü zenginleştirir.", Color(0xFF3A86FF), Color(0xFFEFF5FF), RewardFruit.BLUEBERRY),
        createItem("sym_kalem", "Renkli Kalem", "✏️", LearningCategory.SYMBOLS, 2, "Resim yaptığımız ve yazı yazdığımız sevimli sarı kurşun kalem hangisi?", "Kalemle hayallerimizi kağıda çizeriz.", Color(0xFFFB8500), Color(0xFFFFF6EB), RewardFruit.ORANGE),
        createItem("sym_firca", "Boya Fırçası", "🖌️", LearningCategory.SYMBOLS, 2, "Rengarenk resimler boyadığımız fırça simgesi nerede?", "Fırça dünyamızı renklendirir.", Color(0xFF7209B7), Color(0xFFF7F0FA), RewardFruit.STRAWBERRY),
        createItem("sym_palet", "Ressam Paleti", "🎨", LearningCategory.SYMBOLS, 3, "Ressamların boyaları karıştırdığı renk paleti hangisi tatlım?", "Palet sanatı ve boyamayı anlatır.", Color(0xFFFF5400), Color(0xFFFFF3ED), RewardFruit.MANGO),
        createItem("sym_makas", "Kağıt Makası", "✂️", LearningCategory.SYMBOLS, 3, "Elişi dersinde kağıt kestiğimiz makas simgesi nerede?", "Makas kağıtlardan güzel şekiller çıkarmayı sağlar.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE),
        createItem("sym_cetvel", "Ölçüm Cetveli", "📏", LearningCategory.SYMBOLS, 3, "Boyları ve çizgileri ölçtüğümüz düz cetvel hangisi?", "Cetvel ne kadar uzun olduğunu gösterir.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("sym_büyüteç", "Dedektif Büyüteci", "🔍", LearningCategory.SYMBOLS, 2, "Küçük şeyleri kocaman gösteren büyüteç nerede canım?", "Büyüteç böcekleri ve yaprakları yakından inceler.", Color(0xFF0077B6), Color(0xFFE8F4FA), RewardFruit.BLUEBERRY),
        createItem("sym_mikroskop", "Bilim Mikroskobu", "🔬", LearningCategory.SYMBOLS, 4, "Gözle görülmeyen minik dünyaları gösteren mikroskop hangisi?", "Mikroskop bilim insanlarının en büyük yardımcısıdır.", Color(0xFF2A9D8F), Color(0xFFEDFCF7), RewardFruit.KIWI),
        createItem("sym_teleskop", "Uzay Teleskobu", "🔭", LearningCategory.SYMBOLS, 4, "Uzak yıldızları ve gezegenleri izlediğimiz teleskop nerede?", "Teleskopla aya ve gezegenlere bakarız.", Color(0xFF3C096C), Color(0xFFF4EDF9), RewardFruit.GRAPES),
        createItem("sym_roket", "Uzay Roketi", "🚀", LearningCategory.SYMBOLS, 3, "Aya doğru hızla uçan süper uzay roketi hangisi?", "Roketler yerçekimini yenip uzaya çıkar.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.STRAWBERRY),
        createItem("sym_pusula", "Yön Pusulası", "🧭", LearningCategory.SYMBOLS, 4, "Kuzeyi ve yönleri gösteren denizci pusulası nerede?", "Pusulanın kırmızı iğnesi her zaman kuzeyi gösterir.", Color(0xFFBA181B), Color(0xFFFBF0F1), RewardFruit.APPLE),
        createItem("sym_harita", "Hazine Haritası", "🗺️", LearningCategory.SYMBOLS, 3, "Yolları ve ülkeleri gösteren renkli harita hangisi?", "Harita dünyada nerede olduğumuzu gösterir.", Color(0xFFD4A373), Color(0xFFFAF5F0), RewardFruit.MANGO),
        createItem("sym_dunya_kure", "Masa Dünyası", "🌐", LearningCategory.SYMBOLS, 3, "Dönen masa küresi simgesi nerede tatlım?", "Küre üzerinde kıtaları ve okyanusları inceleriz.", Color(0xFF0077B6), Color(0xFFE8F4FA), RewardFruit.BLUEBERRY),
        createItem("sym_takvim", "Gün Takvimi", "📅", LearningCategory.SYMBOLS, 3, "Ayları ve doğum günlerimizi gösteren takvim hangisi?", "Takvim bize özel günleri hatırlatır.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE),
        createItem("sym_zarf", "Mektup Zarfı", "✉️", LearningCategory.SYMBOLS, 2, "Sevdiklerimize mektup ve tebrik kartı yolladığımız zarf nerede?", "Mektup güzel haberler taşır.", Color(0xFF495057), Color(0xFFF1F3F5), RewardFruit.BANANA),
        createItem("sym_hediye", "Sürpriz Hediye Paketi", "🎁", LearningCategory.SYMBOLS, 2, "Üstü kurdeleli rengarenk hediye kutusu hangisi?", "Hediyeleşmek sevgimizi gösterir.", Color(0xFFFF4D6D), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY),
        createItem("sym_balon", "Uçan Kırmızı Balon", "🎈", LearningCategory.SYMBOLS, 2, "Doğum günlerinde göğe yükselen uçan balon nerede?", "Balonlar neşeli partilerin süsüdür.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.APPLE),
        createItem("sym_konfeti", "Kutlama Konfetisi", "🎉", LearningCategory.SYMBOLS, 2, "Tebrikler ve zafer anında patlayan kutlama simgesi hangisi?", "Başarılarımızı kutlarken neşeyle patlatırız.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.ORANGE),
        createItem("sym_kupa", "Şampiyonluk Kupası", "🏆", LearningCategory.SYMBOLS, 3, "Yarışmada birinci olunca kaldırılan altın kupa nerede?", "Kupa çalışmanın ve başarının ödülüdür.", Color(0xFFFFC300), Color(0xFFFFFCEB), RewardFruit.BANANA),
        createItem("sym_tac", "Altın Kral Tacı", "👑", LearningCategory.SYMBOLS, 3, "Masallarda kralların ve kraliçelerin taktığı altın taç hangisi?", "Taç masal kahramanlarının simgesidir.", Color(0xFFFFBE0B), Color(0xFFFFFCEB), RewardFruit.MANGO),
        createItem("sym_priz", "Elektrik Prizi Uyarısı", "🔌", LearningCategory.SYMBOLS, 3, "Çocukların asla dokunmaması gereken elektrik prizi uyarısı nerede?", "Prizlere sadece büyükler dokunmalıdır, tehlikelidir.", Color(0xFF6C757D), Color(0xFFF4F4F6), RewardFruit.APPLE),
        createItem("sym_trafik_isigi", "Trafik Işıkları", "🚦", LearningCategory.SYMBOLS, 2, "Kırmızıda dur, yeşilde geç diyen trafik ışığı hangisi?", "Kırmızı dur, sarı hazırlan, yeşil geç demektir.", Color(0xFF2A9D8F), Color(0xFFEDFCF7), RewardFruit.KIWI),
        createItem("sym_yaya_gecidi", "Yaya Geçidi", "🚸", LearningCategory.SYMBOLS, 2, "Karşıdan karşıya güvenle geçtiğimiz çizgili yaya geçidi nerede?", "Yoldan geçerken daima yaya geçidini kullanırız.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.BANANA),
        createItem("sym_dur_tabelasi", "Kırmızı Dur Tabelası", "🛑", LearningCategory.SYMBOLS, 3, "Trafikte arabalara dur diyen kırmızı sekizgen tabela hangisi?", "Dur tabelası araçları güvenle durdurur.", Color(0xFFD90429), Color(0xFFFDF0F2), RewardFruit.APPLE),
        createItem("sym_doktor_cantasi", "İlk Yardım Çantası", "🩹", LearningCategory.SYMBOLS, 2, "Yaramıza yapıştırdığımız sevimli yara bandı nerede?", "Yara bandı minik çizikleri çabucak iyileştirir.", Color(0xFFE9C46A), Color(0xFFFAF7EE), RewardFruit.BANANA),
        createItem("sym_termometre", "Ateşölçer (Termometre)", "🌡️", LearningCategory.SYMBOLS, 3, "Hava sıcaklığını ve ateşimizi ölçen termometre hangisi?", "Havanın sıcak mı soğuk mu olduğunu gösterir.", Color(0xFFE63946), Color(0xFFFDF0F1), RewardFruit.CHERRY),
        createItem("sym_dis_fircasi", "Diş Fırçası", "🪥", LearningCategory.SYMBOLS, 2, "Dişlerimizi inci gibi pırıl pırıl yapan diş fırçası nerede?", "Sabah ve akşam dişlerimizi fırçalamak çok sağlıklıdır.", Color(0xFF00B4D8), Color(0xFFEFFBFE), RewardFruit.WATERMELON),
        createItem("sym_sabun", "El Sabunu", "🧼", LearningCategory.SYMBOLS, 2, "Mikropları kovan köpük köpük mis kokulu sabun hangisi?", "Yemekten önce ellerimizi sabunla yıkarız.", Color(0xFFFF758F), Color(0xFFFFF0F3), RewardFruit.STRAWBERRY),
        createItem("sym_banyo", "Köpüklü Banyo", "🛁", LearningCategory.SYMBOLS, 2, "Tertemiz ve ferahlatıcı banyo küveti simgesi nerede?", "Banyo yapmak bizi rahatlatır ve mis gibi kokutur.", Color(0xFF48CAE4), Color(0xFFF0FAFC), RewardFruit.BLUEBERRY),
        createItem("sym_geri_donusum", "Geri Dönüşüm (Doğayı Koru)", "♻️", LearningCategory.SYMBOLS, 3, "Dünyamızı ve doğayı koruyan yeşil geri dönüşüm simgesi hangisi?", "Kağıt, cam ve plastikleri geri dönüştürürüz.", Color(0xFF2D6A4F), Color(0xFFEDF5F0), RewardFruit.KIWI),
        createItem("sym_kalp_goz", "Mutluluk Emojisi", "🥰", LearningCategory.SYMBOLS, 2, "Gülümseyen sevgi dolu mutlu yüz nerede tatlım?", "Gülümsemek dünyayı güzelleştirir.", Color(0xFFFFB703), Color(0xFFFFFBEA), RewardFruit.STRAWBERRY),
        createItem("sym_alkis", "Tebrik Alkışı", "👏", LearningCategory.SYMBOLS, 2, "Başarılı bir oyundan sonra el çırpan alkış simgesi hangisi?", "Arkadaşlarımızı alkışlayarak kutlarız.", Color(0xFFFFD166), Color(0xFFFFFCEB), RewardFruit.ORANGE),
        createItem("sym_sarilma", "Sımsıcak Sarılma", "🫂", LearningCategory.SYMBOLS, 2, "Arkadaşımıza ve ailemize sevgimizi gösteren sarılma simgesi nerede?", "Sarılmak içimizi sevgiyle doldurur.", Color(0xFF4361EE), Color(0xFFEFF3FF), RewardFruit.BLUEBERRY),
        createItem("sym_gozluk", "Okuma Gözlüğü", "👓", LearningCategory.SYMBOLS, 3, "Daha net görmek ve kitap okumak için takılan gözlük hangisi?", "Gözlük yazıları netleştirmeye yardım eder.", Color(0xFF495057), Color(0xFFF1F3F5), RewardFruit.APPLE),
        createItem("sym_kulaklik", "Müzik Kulaklığı", "🎧", LearningCategory.SYMBOLS, 3, "Güzel melodiler dinlediğimiz renkli kulaklık nerede?", "Kulaklıkla şarkı dinlemek çok keyiflidir.", Color(0xFF7209B7), Color(0xFFF7F0FA), RewardFruit.GRAPES),
        createItem("sym_fotograf", "Fotoğraf Makinesi", "📷", LearningCategory.SYMBOLS, 2, "En güzel hatıralarımızı çeken fotoğraf makinesi hangisi?", "Güzel anılarımızı fotoğraflarla saklarız.", Color(0xFF2B2D42), Color(0xFFEFF0F2), RewardFruit.STRAWBERRY)
    )
}
