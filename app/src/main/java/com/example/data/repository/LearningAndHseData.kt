package com.example.data.repository

import com.example.model.BiText
import com.example.model.DailyFact
import com.example.model.DictionaryTerm
import com.example.model.EngineeringRefTable
import com.example.model.FlashcardItem
import com.example.model.HseTopic
import com.example.model.InterviewQuestion
import com.example.model.LessonLevel
import com.example.model.LessonModule
import com.example.model.QuizQuestion

object LearningAndHseData {

    val lessons: List<LessonModule> = listOf(
        LessonModule(
            id = "les_fund_1",
            track = BiText("Petroleum Fundamentals", "بنیادی پیٹرولیم انجینئرنگ"),
            level = LessonLevel.BEGINNER,
            title = BiText("Upstream, Midstream & Downstream Explained", "اپ اسٹریم، مڈ اسٹریم اور ڈاؤن اسٹریم کی مکمل وضاحت"),
            summary = BiText(
                "Understand the complete oil & gas value chain from seismic exploration to refinery fuels.",
                "تیل و گیس کی تلاش سے لے کر ریفائنری مصنوعات تک مکمل انڈسٹری کا بنیادی ڈھانچہ سمجھیں۔"
            ),
            durationMinutes = 8,
            sizeKb = 140,
            sections = listOf(
                BiText("1. Upstream (Exploration & Production)", "1. اپ اسٹریم (تلاش اور پیداوار)") to BiText(
                    "Upstream covers geological surveys, seismic imaging, exploratory and development drilling, well completion, and lifting raw hydrocarbons to the surface wellhead.",
                    "اپ اسٹریم میں سیسمک سروے، کنویں کی ڈرلنگ، ویل کمپلیشن اور زیرِ زمین سے خام تیل و گیس سطح زمین تک نکالنا شامل ہے۔"
                ),
                BiText("2. Midstream (Processing & Transport)", "2. مڈ اسٹریم (پروسیسنگ اور ترسیل)") to BiText(
                    "Midstream includes 3-phase separation, gas sweetening (Amine), dehydration (TEG), cross-country pipelines, compressor stations, LNG tankers, and tank terminals.",
                    "مڈ اسٹریم میں گیس/تیل/پانی کی علیحدگی، گیس سویٹننگ، پائپ لائنز، کمپریسر اسٹیشنز اور اسٹوریج ٹرمینلز شامل ہیں۔"
                ),
                BiText("3. Downstream (Refining & Petrochemicals)", "3. ڈاؤن اسٹریم (ریفائننگ اور مصنوعات)") to BiText(
                    "Downstream refines crude oil through Atmospheric & Vacuum Distillation, FCC, Hydrocracking, and Reforming into Gasoline, Jet-A1, Diesel, LPG, and petrochemical feedstocks.",
                    "ڈاؤن اسٹریم میں ریفائنریز خام تیل کو پیٹرول، ڈیزل، جیٹ فیول اور ایل پی جی میں تبدیل کرتی ہیں۔"
                )
            ),
            keyTakeaways = listOf(
                BiText("Upstream = Find & Produce; Midstream = Treat & Move; Downstream = Refine & Sell.", "اپ اسٹریم = نکالنا؛ مڈ اسٹریم = صاف کر کے بھیجنا؛ ڈاؤن اسٹریم = ریفائن کرنا۔"),
                BiText("Hydrostatic well control and HSE barriers govern every phase.", "ہر مرحلے میں سیفٹی اور پریشر کنٹرول سب سے اہم ہیں۔")
            )
        ),
        LessonModule(
            id = "les_drill_1",
            track = BiText("Drilling Engineering", "ڈرلنگ انجینئرنگ"),
            level = LessonLevel.INTERMEDIATE,
            title = BiText("Primary Well Control & Hydrostatic Pressure", "پرائمری ویل کنٹرول اور ہائیڈروسٹیٹک پریشر"),
            summary = BiText(
                "Master the 0.052 × MW × TVD formula, kick indicators, and BOP shut-in discipline.",
                "ہائیڈروسٹیٹک پریشر کے فارمولے، کِک کی علامات اور BOP بند کرنے کے اصول سیکھیں۔"
            ),
            durationMinutes = 12,
            sizeKb = 210,
            sections = listOf(
                BiText("1. Hydrostatic Head Formula", "1. ہائیڈروسٹیٹک پریشر کا فارمولا") to BiText(
                    "Hydrostatic Pressure (psi) = 0.052 × Mud Weight (ppg) × True Vertical Depth (ft). Maintaining bottomhole hydrostatic pressure slightly above formation pore pressure provides primary well control.",
                    "فارمولا: P = 0.052 × MW (ppg) × TVD (ft)۔ کنویں کے اندر مڈ کا دباؤ فارمیشن کے دباؤ سے تھوڑا زیادہ رکھنا پرائمری ویل کنٹرول کہلاتا ہے۔"
                ),
                BiText("2. Early Kick Warning Signs", "2. کِک (Kick) کی ابتدائی علامات") to BiText(
                    "Watch for: (a) Pit volume gain, (b) Flow rate increase with pumps constant, (c) Drilling break (sudden ROP jump), and (d) Well flowing with pumps off.",
                    "علامات: مڈ ٹینک میں سطح بڑھنا، پمپ کی رفتار بدلے بغیر فلو بڑھنا، اچانک تیز ڈرلنگ (Drilling Break) اور پمپ بند ہونے پر بھی کنویں سے بہاؤ جاری رہنا۔"
                ),
                BiText("3. Secondary Well Control (BOP)", "3. سیکنڈری ویل کنٹرول (BOP)") to BiText(
                    "If a kick occurs, perform flow check, shut in the well using Annular or Pipe Ram BOP, record SIDPP and SICP pressures, and circulate out the influx using Wait-and-Weight or Driller's Method.",
                    "کِک آنے پر فوری فلو چیک کریں، BOP بند کریں، SIDPP اور SICP پریشر نوٹ کریں اور بھاری کِل مڈ سے کِک کو باہر نکالیں۔"
                )
            ),
            keyTakeaways = listOf(
                BiText("Always use True Vertical Depth (TVD)—never Measured Depth (MD)—for hydrostatic calculations.", "ہائیڈروسٹیٹک حساب میں ہمیشہ TVD استعمال کریں، MD نہیں۔"),
                BiText("SIDPP reflects true formation pressure: Pf = Ph + SIDPP.", "SIDPP اصل فارمیشن پریشر بتاتا ہے۔")
            )
        ),
        LessonModule(
            id = "les_prod_1",
            track = BiText("Production Operations", "پروڈکشن آپریشنز"),
            level = LessonLevel.INTERMEDIATE,
            title = BiText("Three-Phase Separation & Emulsion Treatment", "تھری فیز سیپریشن اور ایملشن ٹریٹمنٹ"),
            summary = BiText(
                "How separators use gravity, residence time, coalescers, and demulsifiers to clean oil and gas.",
                "سیپریٹرز کس طرح کششِ ثقل، ویئر پلیٹ اور مسٹ پیڈ سے تیل، گیس اور پانی الگ کرتے ہیں۔"
            ),
            durationMinutes = 10,
            sizeKb = 175,
            sections = listOf(
                BiText("1. Momentum & Gravity Zones", "1. مومنٹم اور کششِ ثقل کے مراحل") to BiText(
                    "An inlet diverter deflects incoming fluid to break kinetic momentum, allowing gas to rise into the mist-extractor section while oil and water settle by density difference.",
                    "انلیٹ ڈائیورٹر بہاؤ کو توڑتا ہے جس سے گیس اوپر مسٹ پیڈ کی طرف اٹھتی ہے اور تیل و پانی کثافت کے فرق سے نیچے الگ ہوتے ہیں۔"
                ),
                BiText("2. Controlling Carryover & Carryunder", "2. کیری اوور اور کیری انڈر کی روک تھام") to BiText(
                    "Too high liquid level causes liquid carryover into gas compressors; too low interface level causes gas blowby or oil carryunder into produced water.",
                    "لیول زیادہ ہونے سے تیل گیس لائن میں جا سکتا ہے اور کم ہونے سے گیس پانی والی لائن میں جا سکتی ہے۔"
                )
            ),
            keyTakeaways = listOf(
                BiText("Retention time (2–5 min) + chemical demulsifier + heat resolve tight water-in-oil emulsions.", "رٹینشن ٹائم، حرارت اور ڈیملسیفائر مل کر سخت ایملشن توڑتے ہیں۔")
            )
        ),
        LessonModule(
            id = "les_ref_1",
            track = BiText("Refinery Engineering", "ریفائنری انجینئرنگ"),
            level = LessonLevel.ADVANCED,
            title = BiText("Crude Distillation, Cracking & Octane Upgrading", "کروڈ ڈسٹیلیشن، کریکنگ اور آکٹین اپ گریڈنگ"),
            summary = BiText(
                "Deep dive into CDU/VDU fractionation, Fluid Catalytic Cracking (FCC), and Hydrotreating.",
                "ایٹماسفیرک ڈسٹیلیشن، FCC کریکنگ اور ہائیڈروٹریٹنگ کے کیمیائی و تھرمل اصول۔"
            ),
            durationMinutes = 14,
            sizeKb = 240,
            sections = listOf(
                BiText("1. Desalting Before Distillation", "1. ڈسٹیلیشن سے پہلے ڈی سالٹنگ") to BiText(
                    "Raw crude contains brine chlorides (NaCl, MgCl2, CaCl2) that hydrolyze into corrosive HCl inside the overhead condenser. Electrostatic desalters wash crude with fresh water at 120–140°C before the furnace.",
                    "خام تیل میں موجود نمک گرم ہو کر تیزابی HCl گیس بناتا ہے، اس لیے فرنس سے پہلے الیکٹرواسٹیٹک ڈی سالٹر سے نمک دھویا جاتا ہے۔"
                ),
                BiText("2. Atmospheric vs Vacuum Towers", "2. ایٹماسفیرک اور ویکیوم ٹاورز") to BiText(
                    "Above ~375°C crude thermally cracks into coke; therefore atmospheric residue is routed to a Vacuum Distillation Unit (25–50 mmHg) where lowered pressure drops boiling points so heavy gas oil distills cleanly.",
                    "375°C سے اوپر تیل جل کر کوک بننے لگتا ہے، اس لیے باقی بچے بھاری تیل کو کم دباؤ والے ویکیوم ٹاور میں الگ کیا جاتا ہے۔"
                )
            ),
            keyTakeaways = listOf(
                BiText("Desalting protects CDU overhead metallurgy; VDU lowers boiling point without thermal coking.", "ڈی سالٹنگ ٹاور کو زنگ سے اور ویکیوم ٹاور بھاری تیل کو جلنے سے بچاتا ہے۔")
            )
        )
    )

    val dictionaryTerms: List<DictionaryTerm> = listOf(
        DictionaryTerm("d1", "API Gravity", "API گریویٹی", "Properties", BiText("Standard industry scale measuring how heavy or light crude oil is compared to water (Water = 10° API).", "خام تیل کے ہلکے یا بھاری پن کا معیاری پیمانہ۔")),
        DictionaryTerm("d2", "Annulus", "اینولس (Annulus)", "Drilling", BiText("The ring-shaped space between drill pipe/tubing and casing or borehole wall.", "ڈرل پائپ اور کیسنگ یا کنویں کی دیوار کے درمیان خالی جگہ۔")),
        DictionaryTerm("d3", "BOP (Blowout Preventer)", "بی او پی", "Well Control", BiText("High-pressure hydraulic valve stack mounted over a well to seal uncontrolled formation pressure.", "کنویں کے بے قابو دباؤ کو روکنے والا ہائیڈرولک حفاظتی نظام۔")),
        DictionaryTerm("d4", "BS&W", "بی ایس اینڈ ڈبلیو", "Laboratory", BiText("Basic Sediment and Water—the volume % of water and solids in crude oil.", "خام تیل میں موجود پانی اور تلچھٹ کی فیصد مقدار۔")),
        DictionaryTerm("d5", "Cavitation", "کیویٹیشن", "Mechanical", BiText("Formation and violent collapse of vapor bubbles in a centrifugal pump when NPSHa < NPSHr, damaging impellers.", "کم سکشن پریشر کی وجہ سے پمپ میں بلبلوں کا بننا اور پھٹنا جو امپیلر کو نقصان پہنچاتا ہے۔")),
        DictionaryTerm("d6", "Christmas Tree", "کرسمس ٹری", "Production", BiText("Surface assembly of master, wing, swab valves and choke on a producing well.", "پیداواری کنویں پر نصب والوز کا مجموعہ۔")),
        DictionaryTerm("d7", "Cracking (FCC)", "کریکنگ", "Refinery", BiText("Breaking large heavy hydrocarbon molecules into lighter high-value gasoline and olefins.", "بڑے ہائیڈرو کاربن مالیکیولز کو توڑ کر پیٹرول اور گیس میں بدلنا۔")),
        DictionaryTerm("d8", "EMW (Equivalent Mud Weight)", "EMW", "Drilling", BiText("Effective mud density felt downhole combining hydrostatic head and annular friction pressure (ECD).", "کنویں کی تہہ پر محسوس ہونے والا کل موثر مڈ وزن۔")),
        DictionaryTerm("d9", "ESD (Emergency Shutdown)", "ایمرجنسی شٹ ڈاؤن", "HSE", BiText("Automated fail-safe system isolating plant energy and hydrocarbon inventories during emergencies.", "خطرے کے وقت پلانٹ کو خودکار طور پر بند کرنے والا حفاظتی نظام۔")),
        DictionaryTerm("d10", "Flash Point", "فلیش پوائنٹ", "Laboratory", BiText("Lowest temperature at which a liquid emits enough vapor to ignite momentarily near an ignition source.", "کم سے کم درجہ حرارت جس پر تیل کے بخارات چنگاری سے بھڑک اٹھیں۔")),
        DictionaryTerm("d11", "GOR (Gas-Oil Ratio)", "گیس آئل ریشو", "Production", BiText("Volume of produced natural gas (scf) per barrel of stock-tank crude oil (STB).", "فی بیرل تیل کے ساتھ نکلنے والی گیس کا تناسب۔")),
        DictionaryTerm("d12", "H2S (Sour Gas)", "ہائیڈروجن سلفائیڈ", "HSE", BiText("Colorless, heavier-than-air, extremely toxic flammable gas smelling like rotten eggs at low ppm.", "سڑے ہوئے انڈوں جیسی بو والی انتہائی زہریلی گیس جو سونگھنے کی حس مفلوج کر دیتی ہے۔")),
        DictionaryTerm("d13", "Hydrostatic Pressure", "ہائیڈروسٹیٹک پریشر", "Drilling", BiText("Pressure exerted by a vertical column of fluid: P = 0.052 × MW × TVD.", "سیال کے عمودی ستون کا دباؤ۔")),
        DictionaryTerm("d14", "Kick", "کِک (Kick)", "Drilling", BiText("Unwanted entry of formation gas, oil, or water into the wellbore during drilling.", "ڈرلنگ کے دوران زیرِ زمین سے گیس یا پانی کا کنویں میں داخل ہو جانا۔")),
        DictionaryTerm("d15", "LEL (Lower Explosive Limit)", "ایل ای ایل", "HSE", BiText("Minimum concentration of flammable gas in air capable of igniting (e.g., Methane LEL = 5%).", "ہوا میں آتش گیر گیس کی کم سے کم مقدار جو آگ پکڑ سکے۔")),
        DictionaryTerm("d16", "LOTO (Lockout/Tagout)", "لاک آؤٹ / ٹیگ آؤٹ", "HSE", BiText("Safety isolation procedure physically locking energy breakers/valves during maintenance.", "مرمت کے دوران مشین یا بجلی کو تالا لگا کر محفوظ بنانے کا طریقہ۔")),
        DictionaryTerm("d17", "NPSH", "این پی ایس ایچ", "Mechanical", BiText("Net Positive Suction Head—margin above liquid vapor pressure at pump impeller inlet.", "پمپ انلیٹ پر بخارات بننے سے بچاؤ کے لیے ضروری دباؤ۔")),
        DictionaryTerm("d18", "Pigging", "پگنگ (Pigging)", "Pipelines", BiText("Sending mechanical scrapers or smart sensors through pipelines to clean or inspect walls.", "پائپ لائن کی صفائی یا معائنے کے لیے پگ بھیجنے کا عمل۔")),
        DictionaryTerm("d19", "PSV (Pressure Safety Valve)", "پریشر سیفٹی والو", "HSE", BiText("Spring-loaded relief valve opening automatically to protect pressure vessels from overpressure.", "ویسل کو زیادہ دباؤ سے پھٹنے سے بچانے والا خودکار والو۔")),
        DictionaryTerm("d20", "Sweet vs Sour Crude", "سوئیٹ بمقابلہ ساؤر کروڈ", "Refinery", BiText("Sweet crude contains < 0.5 wt% sulfur; Sour crude contains > 0.5% sulfur requiring extra desulfurization.", "کم سلفر والا تیل سوئیٹ اور زیادہ سلفر والا ساؤر کہلاتا ہے۔")),
        DictionaryTerm("d21", "WOB (Weight on Bit)", "ویٹ آن بٹ", "Drilling", BiText("Downward axial force applied to the drill bit via heavy drill collars.", "ڈرل بٹ پر ڈالا جانے والا عمودی وزن۔")),
        DictionaryTerm("d22", "Workover", "ورک اوور", "Completion", BiText("Major remedial intervention on a producing well to restore tubing, zone isolation, or artificial lift.", "پیداواری کنویں کی مرمت اور پیداوار بحال کرنے کا بڑا آپریشن۔"))
    )

    val flashcards: List<FlashcardItem> = listOf(
        FlashcardItem("fc1", "Drilling", BiText("What is the Hydrostatic Pressure formula in oilfield units?", "آئل فیلڈ یونٹس میں ہائیڈروسٹیٹک پریشر کا فارمولا کیا ہے؟"), BiText("P (psi) = 0.052 × Mud Weight (ppg) × TVD (ft)", "P = 0.052 × MW (ppg) × TVD (ft)")),
        FlashcardItem("fc2", "Refinery", BiText("What defines 'Sweet' vs 'Sour' crude oil?", "سوئیٹ اور ساؤر خام تیل میں کیا فرق ہے؟"), BiText("Sweet crude has < 0.5% sulfur; Sour crude has > 0.5% sulfur (often with H2S).", "سوئیٹ میں سلفر 0.5% سے کم اور ساؤر میں 0.5% سے زیادہ ہوتا ہے۔")),
        FlashcardItem("fc3", "Mechanical", BiText("Why does Pump Cavitation occur?", "پمپ میں کیویٹیشن کیوں ہوتی ہے؟"), BiText("When available NPSHa drops below required NPSHr, causing liquid to flash to vapor bubbles and implode on the impeller.", "جب سکشن پریشر (NPSHa) مطلوبہ حد سے کم ہو جائے تو بلبلے بن کر امپیلر سے ٹکراتے ہیں۔")),
        FlashcardItem("fc4", "HSE", BiText("At what H2S concentration does human sense of smell fail?", "کتنے ppm پر H2S انسان کے سونگھنے کی حس ختم کر دیتی ہے؟"), BiText("Around 100 ppm (Olfactory fatigue). Never rely on smell—always trust your H2S personal monitor!", "تقریباً 100 ppm پر؛ اس لیے ہمیشہ H2S ڈیٹیکٹر پر بھروسہ کریں۔")),
        FlashcardItem("fc5", "Production", BiText("What does a Weir Plate inside a 3-Phase Separator do?", "تھری فیز سیپریٹر میں ویئر پلیٹ کا کیا کام ہے؟"), BiText("It acts as an internal dam separating settled water from lighter crude oil overflowing into the oil compartment.", "یہ پانی کو نیچے روک کر ہلکے تیل کو اوپر سے الگ چیمبر میں جانے دیتی ہے۔")),
        FlashcardItem("fc6", "Properties", BiText("Water has an API Gravity of ___°.", "پانی کی API گریویٹی کتنی ہوتی ہے؟"), BiText("10° API (SG = 1.0). Crude > 31.1° is Light; < 22.3° is Heavy.", "10 ڈگری API (پانی کی کثافت 1.0 کے برابر)۔")),
        FlashcardItem("fc7", "Well Control", BiText("Which BOP component can seal around ANY pipe size or open hole?", "کون سا BOP ہر سائز کے پائپ یا خالی سوراخ کو سیل کر سکتا ہے؟"), BiText("The Annular Preventer (rubber elastomer packing element).", "اینولر پریوینٹر (Annular BOP)۔")),
        FlashcardItem("fc8", "Pipelines", BiText("Why should a Gate Valve NEVER be used for flow throttling?", "گیٹ والو کو بہاؤ کم زیادہ کرنے کے لیے کیوں استعمال نہیں کرنا چاہیے؟"), BiText("High-velocity turbulence erodes the bottom gate edge and seats (wire-drawing), ruining shutoff.", "تیز بہاؤ سے گیٹ اور سیٹس کٹ جاتے ہیں اور والو بند ہونا بند ہو جاتا ہے۔"))
    )

    val quizQuestions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = "q1",
            topic = BiText("Drilling", "ڈرلنگ"),
            question = BiText(
                "Calculate hydrostatic pressure at 10,000 ft TVD with 12.0 ppg mud weight:",
                "10,000 فٹ TVD اور 12.0 ppg مڈ ویٹ پر ہائیڈروسٹیٹک پریشر کتنا ہوگا؟"
            ),
            options = listOf(
                BiText("5,200 psi", "5,200 psi"),
                BiText("6,240 psi", "6,240 psi"),
                BiText("4,800 psi", "4,800 psi"),
                BiText("7,500 psi", "7,500 psi")
            ),
            correctIndex = 1,
            explanation = BiText("P = 0.052 × 12.0 × 10,000 = 6,240 psi.", "فارمولا: 0.052 × 12.0 × 10,000 = 6,240 psi۔")
        ),
        QuizQuestion(
            id = "q2",
            topic = BiText("HSE & Safety", "ایچ ایس ای"),
            question = BiText(
                "What is the standard workplace low-level H2S gas alarm setpoint?",
                "زیادہ تر آئل فیلڈز میں H2S گیس کا پہلا (Low) الارم کتنے ppm پر بجتا ہے؟"
            ),
            options = listOf(
                BiText("10 ppm", "10 ppm"),
                BiText("100 ppm", "100 ppm"),
                BiText("500 ppm", "500 ppm"),
                BiText("1,000 ppm", "1,000 ppm")
            ),
            correctIndex = 0,
            explanation = BiText("Low alarm is set at 10 ppm (TLV-TWA / STEL threshold); 100 ppm is immediately dangerous to life and health (IDLH).", "پہلا الارم 10 ppm پر بجتا ہے؛ 100 ppm جان لیوا حد (IDLH) ہے۔")
        ),
        QuizQuestion(
            id = "q3",
            topic = BiText("Mechanical", "مکینیکل"),
            question = BiText(
                "Which device smooths pressure spikes on a Triplex Mud Pump discharge?",
                "ٹرائی پلیکس مڈ پمپ کے ڈسچارج پر پریشر کے جھٹکے کون کم کرتا ہے؟"
            ),
            options = listOf(
                BiText("Shale Shaker", "شیل شیکر"),
                BiText("Mist Extractor", "مسٹ ایکسٹریکٹر"),
                BiText("Nitrogen Pulsation Dampener", "نائٹروجن پلسیشن ڈیمپنر"),
                BiText("Weir Plate", "ویئر پلیٹ")
            ),
            correctIndex = 2,
            explanation = BiText("A nitrogen-precharged bladder pulsation dampener absorbs reciprocating discharge surges.", "نائٹروجن سے بھرا پلسیشن ڈیمپنر پمپ کے جھٹکوں کو جذب کرتا ہے۔")
        ),
        QuizQuestion(
            id = "q4",
            topic = BiText("Refinery", "ریفائنری"),
            question = BiText(
                "Why is crude oil desalted before entering the Atmospheric Distillation Tower?",
                "ڈسٹیلیشن ٹاور سے پہلے خام تیل کی ڈی سالٹنگ کیوں کی جاتی ہے؟"
            ),
            options = listOf(
                BiText("To increase octane rating", "آکٹین بڑھانے کے لیے"),
                BiText("To remove chlorides that form corrosive HCl acid", "نمکیات نکالنے کے لیے جو تیزابی HCl زنگ پیدا کرتے ہیں"),
                BiText("To add sulfur to diesel", "ڈیزل میں سلفر شامل کرنے کے لیے"),
                BiText("To lower API gravity", "API گریویٹی کم کرنے کے لیے")
            ),
            correctIndex = 1,
            explanation = BiText("Magnesium and calcium chlorides hydrolyze in the furnace to form hydrochloric acid, severely corroding overhead condensers.", "نمکیات گرم ہو کر ہائیڈروکلورک ایسڈ بناتے ہیں جو ٹاور کے اوپری حصے کو شدید زنگ لگاتے ہیں۔")
        ),
        QuizQuestion(
            id = "q5",
            topic = BiText("Production", "پروڈکشن"),
            question = BiText(
                "Which Christmas Tree valve provides vertical wireline/slickline access to the wellbore?",
                "کرسمس ٹری کا کون سا والو اوپر سے وائر لائن ٹولز اتارنے کے لیے کھولا جاتا ہے؟"
            ),
            options = listOf(
                BiText("Flow Wing Valve", "فلو وِنگ والو"),
                BiText("Kill Wing Valve", "کِل وِنگ والو"),
                BiText("Swab Valve", "سواب والو"),
                BiText("Choke Bean", "چوک بین")
            ),
            correctIndex = 2,
            explanation = BiText("The top Swab Valve sits directly above the tee/cross allowing wireline lubricator rig-up.", "سب سے اوپر لگا سواب والو وائر لائن آلات اندر اتارنے کا راستہ دیتا ہے۔")
        ),
        QuizQuestion(
            id = "q6",
            topic = BiText("Refinery", "ریفائنری"),
            question = BiText(
                "A light crude oil has an API Gravity of:",
                "لائٹ کروڈ آئل (Light Crude) کی API گریویٹی کتنی ہوتی ہے؟"
            ),
            options = listOf(
                BiText("Below 10° API", "10 ڈگری سے کم"),
                BiText("10° to 22.3° API", "10 سے 22.3 ڈگری"),
                BiText("Above 31.1° API", "31.1 ڈگری سے زیادہ"),
                BiText("Exactly 5° API", "صرف 5 ڈگری")
            ),
            correctIndex = 2,
            explanation = BiText("Per API classification: Light crude > 31.1° API; Medium 22.3–31.1°; Heavy 10–22.3°.", "31.1° API سے اوپر کا خام تیل Light Crude کہلاتا ہے۔")
        )
    )

    val interviewQuestions: List<InterviewQuestion> = listOf(
        InterviewQuestion(
            id = "iq1",
            role = BiText("Drilling Engineer / Toolpusher", "ڈرلنگ انجینئر"),
            question = BiText("What is the difference between SIDPP and SICP during a well shut-in?", "کنواں بند (Shut-in) کرتے وقت SIDPP اور SICP میں کیا فرق ہے؟"),
            answer = BiText(
                "SIDPP (Shut-In Drill Pipe Pressure) reads pressure through clean mud inside the drill pipe—used to calculate true formation pressure and required kill mud weight. SICP (Shut-In Casing Pressure) reads annulus pressure, which is higher if lighter gas/oil kick fluid entered the annulus.",
                "SIDPP ڈرل پائپ کے اندر صاف مڈ کے ذریعے اصل فارمیشن پریشر بتاتا ہے جس سے کِل مڈ کا وزن نکالا جاتا ہے، جبکہ SICP اینولس کا دباؤ دکھاتا ہے جہاں گیس داخل ہونے کی وجہ سے پریشر زیادہ ہوتا ہے۔"
            )
        ),
        InterviewQuestion(
            id = "iq2",
            role = BiText("Rotating Equipment / Mechanical Engineer", "مکینیکل انجینئر"),
            question = BiText("How do you stop cavitation in a Centrifugal Pump without changing the pump?", "پمپ بدلے بغیر سینٹری فیوگل پمپ میں کیویٹیشن کیسے روکی جا سکتی ہے؟"),
            answer = BiText(
                "Increase NPSHa by raising suction vessel level/pressure, cooling the pumped liquid (reducing vapor pressure), cleaning clogged suction strainers, opening suction isolation valves fully, or throttling discharge slightly to reduce flow rate (lowering NPSHr).",
                "ٹینک کا لیول یا پریشر بڑھا کر، سکشن اسٹرینر صاف کر کے، مائع کا درجہ حرارت کم کر کے یا ڈسچارج کو ہلکا کم کر کے NPSHa کو NPSHr سے زیادہ کیا جاتا ہے۔"
            )
        ),
        InterviewQuestion(
            id = "iq3",
            role = BiText("Process / Refinery Engineer", "پروسیس انجینئر"),
            question = BiText("Why is reflux essential in a Fractional Distillation Column?", "ڈسٹیلیشن ٹاور میں ریفلکس (Reflux) کیوں ضروری ہے؟"),
            answer = BiText(
                "Liquid reflux returned to the top tray provides the downward-flowing cold liquid phase required for mass transfer with rising hot vapors, controlling top product purity (end-point) and overhead temperature.",
                "اوپر سے ٹھنڈا ریفلکس واپس بھیجنے سے ٹرے پر بخارات اور مائع کا ملاپ ہوتا ہے، جو ٹاور کا درجہ حرارت اور پیٹرول کی خالصیت کنٹرول کرتا ہے۔"
            )
        ),
        InterviewQuestion(
            id = "iq4",
            role = BiText("HSE Officer / Field Operator", "ایچ ایس ای آفیسر"),
            question = BiText("Explain the difference between LEL and UEL in hydrocarbon gas safety.", "ہائیڈرو کاربن سیفٹی میں LEL اور UEL میں کیا فرق ہے؟"),
            answer = BiText(
                "Below LEL (Lower Explosive Limit), the gas mixture is too lean to burn; above UEL (Upper Explosive Limit), it is too rich (insufficient oxygen). Between LEL and UEL is the flammable/explosive range. Hot work requires < 1% LEL (0% preferred).",
                "LEL سے کم پر گیس کم ہونے کی وجہ سے آگ نہیں لگتی اور UEL سے اوپر آکسیجن کم ہونے سے آگ نہیں پکڑتی؛ دونوں کے درمیان دھماکہ خیز حد ہوتی ہے۔ ہاٹ ورک کے لیے LEL صفر ہونا ضروری ہے۔"
            )
        )
    )

    val hseTopics: List<HseTopic> = listOf(
        HseTopic(
            id = "hse_ppe",
            title = BiText("Personal Protective Equipment (PPE)", "ذاتی حفاظتی سامان (PPE)"),
            severityLabel = BiText("Mandatory Zone 1/2", "لازمی حفاظتی اصول"),
            overview = BiText(
                "Minimum hydrocarbon plant PPE acts as the final physical barrier against impact, flash fire, toxic gas, noise, and chemical splash.",
                "تیل و گیس پلانٹس میں آگ، زہریلی گیس، شور اور چوٹ سے بچاؤ کے لیے معیاری PPE پہننا ہر فرد پر لازم ہے۔"
            ),
            checklist = listOf(
                BiText("ANSI Z89.1 Hard Hat with chinstrap at height", "معیاری سیفٹی ہیلمٹ"),
                BiText("NFPA 2112 Flame-Resistant (FR) Coveralls", "آگ سے بچانے والے FR کورآلز"),
                BiText("ANSI Z87.1 Side-shield safety glasses / goggles", "سیفٹی چشمہ"),
                BiText("Steel-toe / Composite-toe oil-resistant boots", "اسٹیل ٹو سیفٹی بوٹس"),
                BiText("Personal calibrated H2S clip monitor in breathing zone", "کالر پر نصب H2S گیس ڈیٹیکٹر"),
                BiText("Double hearing protection in >85 dBA compressor/rig areas", "زیادہ شور والے علاقوں میں ایئر مفس")
            ),
            criticalLimits = listOf(
                BiText("Noise Action Level", "شور کی حد") to "85 dBA (8-hr TWA)",
                BiText("FR Arc/Flash Standard", "کپڑوں کا معیار") to "NFPA 2112 / EN ISO 11612"
            )
        ),
        HseTopic(
            id = "hse_h2s",
            title = BiText("H2S & Toxic Gas Emergency Response", "H2S زہریلی گیس ایمرجنسی"),
            severityLabel = BiText("LIFE CRITICAL", "انتہائی خطرناک"),
            overview = BiText(
                "Hydrogen Sulfide (H2S) is heavier than air, settles in low pits/cellars, paralyzes breathing at high ppm, and deadens smell at 100 ppm.",
                "H2S ہوا سے بھاری زہریلی گیس ہے جو گڑھوں میں جمع ہوتی ہے اور 100 ppm پر سونگھنے کی حس ختم کر کے سانس روک سکتی ہے۔"
            ),
            checklist = listOf(
                BiText("Check wind sock direction continuously on site.", "سائٹ پر ہوا کی سمت (Wind Sock) پر نظر رکھیں۔"),
                BiText("On H2S alarm: Hold breath, don escape hood/SCBA, evacuate crosswind/upwind.", "الارم پر سانس روک کر SCBA پہنیں اور ہوا کے مخالف/کراس ونڈ سمت بھاگیں۔"),
                BiText("NEVER attempt rescue in H2S without full positive-pressure SCBA.", "بغیر SCBA پہنے کسی متاثرہ شخص کو بچانے نہ جائیں ورنہ آپ بھی بے ہوش ہو جائیں گے۔")
            ),
            criticalLimits = listOf(
                BiText("Low Alarm (TWA)", "پہلا الارم") to "10 ppm",
                BiText("High Alarm", "ہائی الارم") to "15–20 ppm",
                BiText("IDLH (Lethal)", "جان لیوا حد") to "100 ppm"
            )
        ),
        HseTopic(
            id = "hse_fire",
            title = BiText("Hydrocarbon Fire Safety & Extinguishers", "آئل اینڈ گیس فائر سیفٹی"),
            severityLabel = BiText("Class B / C Hazard", "آتش زدگی سے بچاؤ"),
            overview = BiText(
                "Hydrocarbon fires (Class B) and pressurized gas jet fires require fuel isolation first, followed by foam, dry chemical (ABC/PKP), or CO2—never direct water streams on pooled oil!",
                "تیل کی آگ (Class B) پر کبھی سیدھا پانی نہ ڈالیں ورنہ تیل پھیل جائے گا؛ پہلے والو بند کریں اور فوم یا ڈرائی کیمیکل استعمال کریں۔"
            ),
            checklist = listOf(
                BiText("Class A (Solids): Water / Foam", "کلاس A (لکڑی/کاغذ): پانی یا فوم"),
                BiText("Class B (Crude/Gasoline/Diesel): AFFF Foam / Dry Chemical", "کلاس B (تیل/پیٹرول): فوم یا ڈرائی پاؤڈر"),
                BiText("Class C (Live Electrical / VFD): CO2 / Clean Agent", "کلاس C (بجلی): CO2 گیس")
            ),
            criticalLimits = listOf(
                BiText("Hot Work LEL Limit", "ہاٹ ورک LEL حد") to "0% LEL (Strict)",
                BiText("PASS Method", "آگ بجھانے کا اصول") to "Pull, Aim, Squeeze, Sweep"
            )
        ),
        HseTopic(
            id = "hse_ptw",
            title = BiText("Permit-to-Work (PTW), LOTO & Confined Space", "پرمٹ ٹو ورک، لاک آؤٹ اور کنفائنڈ اسپیس"),
            severityLabel = BiText("Zero Energy Isolation", "حفاظتی آئسولیشن"),
            overview = BiText(
                "Every non-routine maintenance task requires a signed PTW, physical Lockout/Tagout (LOTO), positive spade/blind isolation, and atmospheric gas testing before entry.",
                "ہر مرمت سے پہلے PTW پرمٹ، بجلی اور والوز پر LOTO تالا اور اندر جانے سے پہلے گیس ٹیسٹ لازمی ہے۔"
            ),
            checklist = listOf(
                BiText("Confirm O2 between 19.5% and 23.5% before vessel entry.", "آکسیجن کی مقدار 19.5% تا 23.5% یقینی بنائیں۔"),
                BiText("Verify 0% LEL and < 10 ppm H2S / < 25 ppm CO.", "LEL صفر اور زہریلی گیسیں مقررہ حد سے کم چیک کریں۔"),
                BiText("Station a trained Hole Watch attendant outside with radio.", "باہر ایک تربیت یافتہ ہول واچ گارڈ تعینات کریں۔")
            ),
            criticalLimits = listOf(
                BiText("Safe Oxygen Range", "محفوظ آکسیجن") to "19.5% – 23.5%",
                BiText("Confined Space LEL", "LEL حد") to "0% LEL"
            )
        )
    )

    val dailyFacts: List<DailyFact> = listOf(
        DailyFact(
            "df1",
            BiText("1 Barrel of Crude Oil = 42 US Gallons", "خام تیل کا 1 بیرل = 42 گیلن"),
            BiText(
                "One 42-gallon (159-liter) barrel of crude oil yields roughly 19 gallons of gasoline, 12 gallons of diesel, and 4 gallons of jet fuel—plus petrochemicals!",
                "خام تیل کے ایک 159 لیٹر بیرل سے ریفائنری میں تقریباً 19 گیلن پیٹرول، 12 گیلن ڈیزل اور 4 گیلن جیٹ فیول تیار ہوتا ہے۔"
            ),
            BiText("Refining Fact", "ریفائنری حقیقت")
        ),
        DailyFact(
            "df2",
            BiText("Why 0.052 in Hydrostatic Pressure?", "ہائیڈروسٹیٹک فارمولے میں 0.052 کہاں سے آیا؟"),
            BiText(
                "Because 1 gallon = 231 in³ and 1 foot = 12 in: 12 / 231 = 0.051948 ≈ 0.052 psi/(ppg·ft)!",
                "12 انچ کو 231 مکعب انچ فی گیلن سے تقسیم کرنے پر 0.051948 یعنی 0.052 آتا ہے!"
            ),
            BiText("Drilling Math", "ڈرلنگ ریاضی")
        ),
        DailyFact(
            "df3",
            BiText("Deepest Oil & Gas Boreholes Exceed 12 km", "دنیا کے گہرے ترین کنویں 12 کلومیٹر سے بھی زیادہ ہیں"),
            BiText(
                "Modern extended-reach offshore wells exceed 40,000 ft (12.2 km) Measured Depth using Rotary Steerable Systems and PDC diamond bits.",
                "جدید روٹری اسٹیئرایبل سسٹمز اور PDC بٹس کی مدد سے 40,000 فٹ سے زیادہ لمبے کنویں کھودے جا چکے ہیں۔"
            ),
            BiText("Drilling Record", "ڈرلنگ ریکارڈ")
        ),
        DailyFact(
            "df4",
            BiText("H2S Paralyzes Smell Above 100 ppm", "100 ppm پر H2S سونگھنے کی حس ختم کر دیتی ہے"),
            BiText(
                "At low concentrations H2S smells like rotten eggs, but above 100 ppm it deadens the olfactory nerve instantly—making electronic detectors mandatory.",
                "کم مقدار میں H2S سڑے انڈے جیسی بو دیتی ہے لیکن 100 ppm پر ناک کی حس ختم ہو جاتی ہے، اس لیے گیس ڈیٹیکٹر لازمی ہے۔"
            ),
            BiText("HSE Fact", "سیفٹی حقیقت")
        )
    )

    val referenceTables: List<EngineeringRefTable> = listOf(
        EngineeringRefTable(
            id = "ref_api",
            title = BiText("API Gravity Crude Classification", "API گریویٹی کے مطابق خام تیل کی درجہ بندی"),
            headers = listOf("Crude Type", "°API Range", "Specific Gravity"),
            rows = listOf(
                listOf("Light Crude", "> 31.1°", "< 0.870"),
                listOf("Medium Crude", "22.3° – 31.1°", "0.870 – 0.920"),
                listOf("Heavy Crude", "10.0° – 22.3°", "0.920 – 1.000"),
                listOf("Extra Heavy / Bitumen", "< 10.0°", "> 1.000")
            )
        ),
        EngineeringRefTable(
            id = "ref_casing",
            title = BiText("Common API Casing & Tubing Sizes", "معیاری API کیسنگ اور ٹیوبنگ سائز"),
            headers = listOf("String Type", "OD (inches)", "Typical Function"),
            rows = listOf(
                listOf("Conductor", "20\" – 30\"", "Prevents surface washout"),
                listOf("Surface Casing", "13-3/8\"", "Protects aquifers & holds BOP"),
                listOf("Intermediate", "9-5/8\"", "Isolates abnormal pressure zones"),
                listOf("Production Casing", "7\" / 5-1/2\"", "Lines reservoir pay zone"),
                listOf("Production Tubing", "2-7/8\" – 4-1/2\"", "Conduit for produced oil/gas")
            )
        ),
        EngineeringRefTable(
            id = "ref_flanges",
            title = BiText("ASME B16.5 Flange Pressure Ratings (100°F)", "ASME فلینج پریشر کلاس ریٹنگز"),
            headers = listOf("Class", "Max Working (psi)", "Max Working (bar)"),
            rows = listOf(
                listOf("Class 150", "285 psi", "19.6 bar"),
                listOf("Class 300", "740 psi", "51.0 bar"),
                listOf("Class 600", "1,480 psi", "102.1 bar"),
                listOf("Class 900", "2,220 psi", "153.1 bar"),
                listOf("Class 1500", "3,705 psi", "255.3 bar")
            )
        )
    )
}
