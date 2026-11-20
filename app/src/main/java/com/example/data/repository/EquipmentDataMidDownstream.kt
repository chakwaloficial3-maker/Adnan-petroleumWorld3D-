package com.example.data.repository

import com.example.model.BiText
import com.example.model.DiagramType
import com.example.model.EquipmentComponent
import com.example.model.EquipmentItem
import com.example.model.EquipmentSpec
import com.example.model.Model3DType

object EquipmentDataMidDownstream {
    val items: List<EquipmentItem> = listOf(
        // 9. Gas Processing
        EquipmentItem(
            id = "eq_amine_contactor",
            categoryId = "cat_gas_processing",
            name = BiText("Amine Gas Sweetening Contactor", "امائن گیس سویٹننگ کونٹیکٹر"),
            shortTagline = BiText("Removes toxic H2S and CO2 from sour natural gas", "کچی گیس سے زہریلی H2S اور CO2 گیس الگ کرنے والا ٹاور"),
            has3dModel = true,
            model3dType = Model3DType.DISTILLATION_COLUMN,
            diagramType = DiagramType.DISTILLATION_COLUMN,
            definition = BiText(
                "A vertical trayed or packed absorption tower where sour natural gas flows upward counter-currently against lean aqueous amine solution (MDEA/DEA) to strip acid gases.",
                "عمودی ٹاور جس میں کڑوی گیس (Sour Gas) نیچے سے اوپر جاتی ہے اور اوپر سے آنے والا امائن محلول اس میں سے H2S اور CO2 جذب کر لیتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Meets pipeline sales-gas specifications (< 4 ppm H2S, < 2% CO2) and prevents sulfide stress cracking and pipeline corrosion.",
                "گیس کو پائپ لائن معیار (< 4 ppm H2S) پر لانا اور پائپوں کو زنگ لگنے سے بچانا۔"
            ),
            workingPrinciple = BiText(
                "High-pressure exothermic chemical absorption occurs at 30–50°C inside the contactor; rich amine leaves the bottom to a low-pressure regenerator (stripper) where heat reverses the reaction.",
                "ٹاور میں ٹھنڈا لین امائن تیزابیت والی گیس جذب کرتا ہے، پھر رچ امائن کو ری جنریٹر میں گرم کر کے دوبارہ صاف امائن بنا لیا جاتا ہے۔"
            ),
            construction = BiText(
                "ASME pressure tower fitted with 20–24 stainless steel valve trays or structured packing, chimney tray, knock-out section, and mist eliminator pad.",
                "اسٹیل کا بلند پریشر ٹاور جس میں 20 تا 24 اسٹین لیس اسٹیل ٹریز اور مسٹ ایلیمینیٹر پیڈ نصب ہوتے ہیں۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("dc_shell", BiText("Absorber Tower Shell", "ایبساربر ٹاور شیل"), "SA-516 PWHT Steel", BiText("Post-weld heat treated to resist amine stress corrosion.", "امائن سنکنرن سے بچاؤ کے لیے خصوصی اسٹیل شیل۔")),
                EquipmentComponent("dc_trays", BiText("Absorption Trays", "امائن ٹریز"), "316L Stainless", BiText("Maximizes gas-liquid contact surface area.", "گیس اور امائن کے ملاپ کا رقبہ بڑھاتی ہیں۔")),
                EquipmentComponent("dc_overhead", BiText("Sweet Gas Outlet", "سوئیٹ گیس آؤٹ لیٹ"), "Carbon Steel", BiText("Delivers treated gas to TEG dehydration unit.", "صاف گیس کو ڈی ہائیڈریشن یونٹ میں بھیجتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("MEA / DEA General Acid Gas Removal", "MEA اور DEA یونٹ"),
                BiText("MDEA Selective H2S Removal", "MDEA سلیکٹو سویٹننگ")
            ),
            applications = listOf(
                BiText("Natural gas processing plants & sour gas fields", "نیچرل گیس پروسیسنگ پلانٹس"),
                BiText("Refinery fuel gas & Claus sulfur tail-gas treating", "ریفائنری فیول گیس صفائی")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Operating Pressure", "پریشر"), "600 – 1,200 psig"),
                EquipmentSpec(BiText("Outlet H2S Spec", "آؤٹ لیٹ H2S معیار"), "< 4 ppmv"),
                EquipmentSpec(BiText("Amine Concentration", "امائن تناسب"), "35 – 50 wt% MDEA")
            ),
            maintenanceConcepts = listOf(
                BiText("Monitor heat-stable salts (HSS) and replace carbon filters before foaming occurs.", "ہیٹ اسٹیبل سالٹس چیک کریں اور کاربن فلٹر وقت پر تبدیل کریں۔")
            ),
            commonProblems = listOf(
                BiText("Amine foaming caused by condensed hydrocarbons or solids.", "ہائیڈرو کاربن ملنے سے جھاگ بننا (Foaming)۔")
            ),
            safetyPrecautions = listOf(
                BiText("Strict H2S respiratory protection (SCBA) required when sampling rich amine or opening vessels.", "رچ امائن سیمپلنگ کے وقت H2S سے بچاؤ کے لیے SCBA پہنیں۔")
            ),
            relatedIds = listOf("eq_distillation_column", "eq_compressor", "eq_h2s_detector"),
            references = listOf("GPSA Engineering Data Book Section 21", "API RP 945 Amine Cracking Avoidance")
        ),

        // 10. Pipelines and Transportation
        EquipmentItem(
            id = "eq_pig_launcher",
            categoryId = "cat_pipelines",
            name = BiText("Pipeline Pig Launcher & Receiver", "پائپ لائن پگ لانچر اور ریسیور"),
            shortTagline = BiText("Inspection & cleaning scraper trap station", "پائپ لائن کی صفائی اور معائنہ کرنے والا پگ اسٹیشن"),
            has3dModel = false,
            diagramType = DiagramType.PIPELINE_PIGGING,
            definition = BiText(
                "Pressurized barrel chambers installed at pipeline terminals to safely insert and retrieve cleaning pigs, batching spheres, or intelligent ILI inspection tools without stopping flow.",
                "پائپ لائن کے شروع اور آخر میں نصب پریشر چیمبرز جن کے ذریعے صفائی یا معائنہ کرنے والے پگز (Pigs) کو بہاؤ بند کیے بغیر پائپ میں بھیجا جاتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Removes wax, condensate, scale, and water pockets while enabling smart magnetic-flux (MFL) wall-thickness corrosion inspection.",
                "پائپ لائن کے اندر جمی موم، پانی اور زنگ صاف کرنا اور اسمارٹ پگ سے پائپ کی موٹائی چیک کرنا۔"
            ),
            workingPrinciple = BiText(
                "With trap isolated and depressurized, the pig is loaded into the oversized barrel; isolation and kicker valves equalize pressure and propel the pig using pipeline product flow.",
                "پریشر ختم کر کے پگ اندر رکھا جاتا ہے، پھر کِکر والو کھول کر پائپ لائن کے بہاؤ کے دباؤ سے پگ کو آگے روانہ کر دیا جاتا ہے۔"
            ),
            construction = BiText(
                "Oversized cylindrical barrel, eccentric reducer, quick-opening safety closure door with pressure interlock, kicker line, bypass line, and pig-passage signaller.",
                "چوڑا اسٹیل بیرل، ریڈیوسر، کوئیک اوپننگ لاک ڈور، کِکر لائن اور پگ سگنلر۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("pig_closure", BiText("Quick-Opening Closure Door", "کوئیک اوپننگ کلوزر ڈور"), "Forged Steel + Interlock", BiText("Safety-interlocked hinged door preventing opening under pressure.", "پریشر موجود ہونے پر دروازہ کھلنے سے روکنے والا حفاظتی لاک۔")),
                EquipmentComponent("pig_signaller", BiText("Pig Passage Signaller", "پگ سگنلر"), "Mechanical / Magnetic", BiText("Trips visual flag/switch when pig passes trap.", "پگ کے گزرنے کا فوری سگنل دیتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Foam & Cup Cleaning Pigs (Wax/Water removal)", "صفائی والے فوم اور کپ پگز"),
                BiText("Smart Intelligent ILI Pigs (MFL / Ultrasonic inspection)", "اسمارٹ انٹیلیجنٹ پگز")
            ),
            applications = listOf(
                BiText("Cross-country crude oil & natural gas transmission trunklines", "طویل فاصلے کی تیل اور گیس پائپ لائنز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Design Standard", "ڈیزائن معیار"), "ASME B31.4 / B31.8"),
                EquipmentSpec(BiText("Trap Diameter", "بیرل قطر"), "Line NPS + 2\" to 4\"")
            ),
            maintenanceConcepts = listOf(
                BiText("Lubricate closure door O-ring seals and verify pressure-bleed interlock before every launch.", "ہر لانچ سے پہلے کلوزر ڈور سیل اور سیفٹی انٹرلاک چیک کریں۔")
            ),
            commonProblems = listOf(
                BiText("Stuck pig at tight bends or partially closed gate valves.", "مُڑنے والی جگہ یا آدھے بند والو میں پگ کا پھنس جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Never stand directly in front of the closure door; always verify zero pressure on two independent gauges.", "دروازہ کھولنے سے پہلے دو الگ گیجز پر صفر پریشر کنفرم کریں اور سامنے کھڑے نہ ہوں۔")
            ),
            relatedIds = listOf("eq_gate_valve", "eq_centrifugal_pump"),
            references = listOf("ASME B31.8 Gas Transmission Piping", "API 1163 In-Line Inspection")
        ),

        // 11. Storage Tanks and Terminals
        EquipmentItem(
            id = "eq_floating_roof_tank",
            categoryId = "cat_storage",
            name = BiText("External Floating Roof Storage Tank", "فلوٹنگ روف اسٹوریج ٹینک"),
            shortTagline = BiText("API 650 bulk crude tank minimizing vapor loss", "خام تیل ذخیرہ کرنے والا ٹینک جو بخارات کے ضیاع کو روکتا ہے"),
            has3dModel = false,
            diagramType = DiagramType.STORAGE_TANK_FLOATING_ROOF,
            definition = BiText(
                "A large cylindrical atmospheric steel storage tank whose roof floats directly on the liquid surface of crude oil or gasoline via buoyancy pontoons.",
                "بڑا اسٹیل ٹینک جس کی چھت براہِ راست تیل کی سطح پر تیرتی رہتی ہے تاکہ اوپر گیس بننے کے لیے خالی جگہ نہ رہے۔"
            ),
            purposeAndFunction = BiText(
                "Eliminates vapor space above volatile liquids, slashing evaporative VOC hydrocarbon losses and minimizing fire/explosion hazards.",
                "تیل کے بخارات بن کر اڑنے کو روکنا اور آگ لگنے کے خطرے کو کم سے کم کرنا۔"
            ),
            workingPrinciple = BiText(
                "As crude is pumped in or out, the pontoon roof rises and falls smoothly along guide poles while dual rim seals wipe the inner shell wall vapor-tight.",
                "جیسے جیسے تیل بھرا یا نکالا جاتا ہے، چھت تیل کے ساتھ اوپر نیچے جاتی ہے اور کناروں کی سیلز بخارات کو باہر نکلنے سے روکتی ہیں۔"
            ),
            construction = BiText(
                "Welded steel bottom plate, stepped-thickness cylindrical shell courses, pontoon floating roof with primary/secondary rim seals, roof drain hose, rolling ladder, and foam dam.",
                "اسٹیل بیس پلیٹ، ویلڈڈ شیل، پونٹون فلوٹنگ چھت، رِم سیلز، رولنگ سیڑھی اور فوم فائر ڈیم۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("tk_pontoons", BiText("Pontoon Floating Deck", "پونٹون تیرتی چھت"), "Carbon Steel", BiText("Provides buoyancy and thermal insulation.", "چھت کو تیل پر تیرائے رکھتی ہے۔")),
                EquipmentComponent("tk_rim_seal", BiText("Primary & Secondary Rim Seals", "رِم سیلز"), "Viton / Urethane", BiText("Seals gap between moving roof and tank shell.", "چھت اور دیوار کے درمیان خلا کو سیل کرتی ہیں۔")),
                EquipmentComponent("tk_drain", BiText("Articulated Roof Drain", "روف ڈرین پائپ"), "Swivel Pipe", BiText("Safely drains rainwater from floating deck.", "بارش کا پانی محفوظ طریقے سے باہر نکالتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("External Floating Roof Tank (EFRT)", "ایکسٹرنل فلوٹنگ روف ٹینک"),
                BiText("Internal Floating Roof Tank (IFRT – geodesic dome cover)", "انٹرنل فلوٹنگ روف ٹینک")
            ),
            applications = listOf(
                BiText("Crude oil tank farms & refinery export terminals", "ریفائنری اور آئل ٹرمینل اسٹوریج فارمز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Design Standard", "معیار"), "API Std 650"),
                EquipmentSpec(BiText("Capacity Range", "گنجائش"), "50,000 – 1,000,000 bbl")
            ),
            maintenanceConcepts = listOf(
                BiText("Inspect rim seals, roof legs, and grounding shunts per API 653.", "API 653 کے مطابق رِم سیلز اور گراؤنڈنگ شَنٹس کا معائنہ کریں۔")
            ),
            commonProblems = listOf(
                BiText("Sunk floating roof from clogged rainwater drain.", "بارش کا پانی جمع ہونے سے چھت کا ڈوب جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Verify lightning-protection bypass shunts and never land roof on legs unnecessarily (creates explosive vapor space).", "آسمانی بجلی سے بچاؤ کے شَنٹس چیک رکھیں اور چھت کو غیر ضروری طور پر نیچے نہ ٹکائیں۔")
            ),
            relatedIds = listOf("eq_centrifugal_pump", "eq_pressure_gauge"),
            references = listOf("API Standard 650 Welded Tanks for Oil Storage", "API 653 Tank Inspection")
        ),

        // 12. Petroleum Refinery
        EquipmentItem(
            id = "eq_distillation_column",
            categoryId = "cat_refinery",
            name = BiText("Atmospheric Crude Distillation Column", "ایٹماسفیرک کروڈ ڈسٹیلیشن ٹاور"),
            shortTagline = BiText("Primary refinery tower separating crude into fuel fractions", "خام تیل کو پیٹرول، کیروسین اور ڈیزل میں تقسیم کرنے والا مرکزی ٹاور"),
            has3dModel = true,
            model3dType = Model3DType.DISTILLATION_COLUMN,
            diagramType = DiagramType.DISTILLATION_COLUMN,
            isPopular = true,
            definition = BiText(
                "The heart of a petroleum refinery—a multi-tray fractional distillation tower separating desalted, preheated crude oil into boiling-range cuts.",
                "ریفائنری کا سب سے اہم یونٹ جو گرم خام تیل کو ابلنے کے درجہ حرارت کے فرق سے مختلف ایندھنوں میں تقسیم کرتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Separates crude continuously into LPG/fuel gas, light & heavy naphtha, kerosene/jet fuel, light/heavy diesel, and atmospheric residue.",
                "خام تیل سے ایل پی جی، نیفتھا، مٹی کا تیل، ڈیزل اور فرنس آئل الگ الگ کرنا۔"
            ),
            workingPrinciple = BiText(
                "Preheated crude (~365°C) enters the lower flash zone. Light hydrocarbons vaporize and rise through valve trays, cooling and condensing at their respective boiling heights while heavy residue exits the bottom.",
                "گرم خام تیل فلیش زون میں داخل ہوتا ہے؛ ہلکے اجزاء بھاپ بن کر اوپر اٹھتے ہیں اور اوپر جاتے ہوئے ٹھنڈے ہو کر الگ الگ پلیٹوں پر مائع بن کر نکل آتے ہیں۔"
            ),
            construction = BiText(
                "Cladded steel column (40–60 m tall) containing 30–50 valve/sieve trays, pumparound heat-removal loops, side-strippers, overhead condenser, and reflux drum.",
                "40 تا 60 میٹر بلند اسٹیل ٹاور جس میں درجنوں ٹریز، سائیڈ اسٹرپرز، اوور ہیڈ کنڈینسر اور ریفلکس ڈرم شامل ہوتے ہیں۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("dc_shell", BiText("Fractionating Tower Shell", "ٹاور شیل"), "SA-516 + 410S Clad", BiText("Resists high-temperature sulfur/naphthenic corrosion.", "زیادہ حرارت اور سلفر زنگ کو برداشت کرتا ہے۔")),
                EquipmentComponent("dc_trays", BiText("Fractionation Trays", "ڈسٹیلیشن ٹریز"), "410S / 316L SS", BiText("Provides vapor-liquid equilibrium stages.", "بخارات اور مائع کے تبادلے کے مراحل فراہم کرتی ہیں۔")),
                EquipmentComponent("dc_overhead", BiText("Overhead Condenser & Reflux", "اوور ہیڈ کنڈینسر"), "Carbon Steel", BiText("Cools top vapors to produce naphtha & reflux.", "اوپری بخارات کو ٹھنڈا کر کے پیٹرول بناتا ہے۔")),
                EquipmentComponent("dc_side_draws", BiText("Side-Draw Cut Nozzles", "سائیڈ ڈرا آؤٹ لیٹس"), "ASME Flanges", BiText("Withdraws kerosene, jet fuel, and diesel fractions.", "کیروسین اور ڈیزل نکالنے کے راستے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Atmospheric Distillation Unit (CDU – ~1.2–2.5 bar)", "ایٹماسفیرک یونٹ (CDU)"),
                BiText("Vacuum Distillation Unit (VDU – ~25–50 mmHg for heavy gasoil)", "ویکیوم ڈسٹیلیشن یونٹ (VDU)")
            ),
            applications = listOf(
                BiText("All fuel & petrochemical refineries worldwide", "دنیا کی تمام آئل ریفائنریز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Flash Zone Temp", "فلیش زون درجہ حرارت"), "350 – 375 °C"),
                EquipmentSpec(BiText("Top Temp", "اوپری درجہ حرارت"), "105 – 125 °C"),
                EquipmentSpec(BiText("Capacity", "گنجائش"), "50,000 – 400,000 BPD")
            ),
            maintenanceConcepts = listOf(
                BiText("Control overhead dewpoint corrosion via neutralizer & filming amine injection.", "اوپری حصے میں تیزابی زنگ روکنے کے لیے نیوٹرلائزر انجیکشن برقرار رکھیں۔")
            ),
            commonProblems = listOf(
                BiText("Tray flooding or weeping caused by excessive vapor/liquid traffic.", "زیادہ بخارات کی وجہ سے ٹری فلڈنگ ہو جانا۔"),
                BiText("Ammonium chloride salt fouling on top trays.", "نمک جمنے سے اوپری ٹریز کا بند ہونا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Auto-ignition hazard if hot residue (>260°C) leaks to air; ensure flange integrity.", "گرم تیل کے لیکج پر فوری آگ بھڑکنے کا خطرہ، فلینج سیلنگ چیک رکھیں۔")
            ),
            relatedIds = listOf("eq_heat_exchanger", "eq_fcc_unit", "eq_petroleum_products"),
            references = listOf("Gary, Handwerk & Kaiser: Petroleum Refining Technology", "API 571 Damage Mechanisms")
        ),

        // 13. Refinery Units and Processes
        EquipmentItem(
            id = "eq_fcc_unit",
            categoryId = "cat_refinery_units",
            name = BiText("Fluid Catalytic Cracking (FCC) Unit", "فلوئڈ کیٹالیٹک کریکنگ (FCC) یونٹ"),
            shortTagline = BiText("Converts heavy gas oil into high-octane gasoline", "بھاری تیل کو توڑ کر زیادہ آکٹین والا پیٹرول بنانے والا یونٹ"),
            has3dModel = false,
            diagramType = DiagramType.DISTILLATION_COLUMN,
            definition = BiText(
                "A continuous high-temperature conversion process using fluidized zeolite catalyst powder at ~530°C to crack heavy vacuum gas oil into high-octane gasoline, LPG olefins, and light cycle oil.",
                "ایسا ریفائنری یونٹ جو باریک کیٹالسٹ پاؤڈر اور 530°C حرارت کے ذریعے بھاری گیس آئل کے بڑے مالیکیولز کو توڑ کر اعلیٰ کوالٹی کا پیٹرول بناتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Upgrades low-value heavy refinery bottoms/VGO into high-margin motor gasoline and petrochemical propylene.",
                "سستے بھاری تیل کو مہنگے اور اعلیٰ معیار کے پیٹرول میں تبدیل کرنا۔"
            ),
            workingPrinciple = BiText(
                "Hot regenerated catalyst contacts atomized oil feed at the base of a vertical Riser Reactor, cracking molecules in 2–3 seconds. Spent catalyst covered in coke flows to the Regenerator where air burns off coke at ~700°C.",
                "گرم کیٹالسٹ رائزر میں تیل کے مالیکیولز کو 2-3 سیکنڈ میں توڑ دیتا ہے، پھر ری جنریٹر میں ہوا کے ذریعے کیٹالسٹ پر جمی کاربن جلا کر اسے دوبارہ فعال کیا جاتا ہے۔"
            ),
            construction = BiText(
                "Coupled dual-vessel system comprising Riser Reactor with cyclones, Steam Stripper, Slide Valves, and Fluidized Bed Regenerator with Air Blower.",
                "رائزر ری ایکٹر، سائیکلونز، سلائیڈ والوز اور بڑے ری جنریٹر ویسل پر مشتمل دوہرا نظام۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("fcc_riser", BiText("Riser Reactor & Feed Nozzles", "رائزر ری ایکٹر"), "Refractory-Lined Steel", BiText("Primary plug-flow cracking zone.", "کریکنگ کا بنیادی زون۔")),
                EquipmentComponent("fcc_regen", BiText("Catalyst Regenerator", "کیٹالسٹ ری جنریٹر"), "Refractory Alloy", BiText("Burns off coke and supplies endothermic cracking heat.", "کیٹالسٹ صاف کر کے حرارت مہیا کرتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Side-by-Side FCC Configuration", "سائیڈ بائی سائیڈ FCC"),
                BiText("Stacked / Orthoflow FCC", "اسٹیکڈ FCC")
            ),
            applications = listOf(
                BiText("High-octane gasoline & propylene manufacturing", "زیادہ آکٹین پیٹرول اور پروپیلین کی تیاری")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Reactor Temp", "ری ایکٹر درجہ حرارت"), "510 – 550 °C"),
                EquipmentSpec(BiText("Regenerator Temp", "ری جنریٹر درجہ حرارت"), "680 – 730 °C")
            ),
            maintenanceConcepts = listOf(
                BiText("Inspect refractory lining and cyclone diplegs for catalyst erosion during turnarounds.", "ریفریکٹری استر اور سائیکلون کا کٹاؤ چیک کریں۔")
            ),
            commonProblems = listOf(
                BiText("Afterburning in regenerator dilute phase.", "ری جنریٹر کے اوپری حصے میں غیر ضروری حرارت بڑھنا۔"),
                BiText("Slide valve erosion causing catalyst circulation loss.", "سلائیڈ والو کٹ جانے سے کیٹالسٹ بہاؤ میں خلل۔")
            ),
            safetyPrecautions = listOf(
                BiText("Maintain positive differential pressure across slide valves to prevent hydrocarbon/air reversal.", "سلائیڈ والوز پر دباؤ کا فرق برقرار رکھیں ورنہ گیس اور ہوا مل کر دھماکہ کر سکتے ہیں۔")
            ),
            relatedIds = listOf("eq_distillation_column", "eq_petroleum_products"),
            references = listOf("Sadeghbeigi: Fluid Catalytic Cracking Handbook")
        ),

        // 14. Petroleum Products
        EquipmentItem(
            id = "eq_petroleum_products",
            categoryId = "cat_products",
            name = BiText("Refined Petroleum Fuels & Products", "ریفائنڈ پیٹرولیم مصنوعات"),
            shortTagline = BiText("LPG, Gasoline, Jet-A1, Diesel, Lubes & Bitumen spectrum", "ایل پی جی، پیٹرول، جیٹ فیول، ڈیزل اور بٹومین کی درجہ بندی"),
            has3dModel = false,
            diagramType = DiagramType.DISTILLATION_COLUMN,
            definition = BiText(
                "Hydrocarbon fractions separated and upgraded from crude oil, characterized by carbon chain length, boiling range, octane/cetane number, flash point, and viscosity.",
                "خام تیل سے حاصل شدہ مختلف ہائیڈرو کاربن ایندھن اور مصنوعات جن کی پہچان ان کے بوائلنگ رینج، آکٹین/سیٹین نمبر اور فلیش پوائنٹ سے ہوتی ہے۔"
            ),
            purposeAndFunction = BiText(
                "Powers global transport (road, aviation, marine), industrial boilers, power generation, asphalt paving, and petrochemical feedstocks.",
                "ٹرانسپورٹ، ہوا بازی، بجلی کی پیداوار، سڑکوں کی تعمیر اور کیمیائی صنعت کو ایندھن اور خام مال فراہم کرنا۔"
            ),
            workingPrinciple = BiText(
                "Shorter carbon chains (C3–C4 LPG, C5–C10 Gasoline) ignite rapidly via spark ignition (high Octane); longer chains (C12–C20 Diesel) auto-ignite under compression (high Cetane).",
                "چھوٹے کاربن مالیکیولز (C5-C10 پیٹرول) ہلکے اور جلدی بھڑکنے والے ہوتے ہیں جبکہ لمبے مالیکیولز (C12-C20 ڈیزل) دباؤ کے تحت چلتے ہیں۔"
            ),
            construction = BiText(
                "Blended finished fuels containing anti-knock, detergent, cetane improver, anti-oxidant, and static-dissipator additives.",
                "مختلف ریفائنری اسٹریمز کو مخصوص معیار کے مطابق بلینڈ کر کے تیار کردہ ایندھن۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("prod_gasoline", BiText("Motor Gasoline (C5–C10)", "موٹر گیسولین / پیٹرول"), "RON 91–98", BiText("Spark-ignition fuel blended for high knock resistance.", "آکٹین نمبر کے مطابق انجن ناکنگ روکنے والا ایندھن۔")),
                EquipmentComponent("prod_jet", BiText("Jet-A1 Aviation Fuel (C10–C14)", "جیٹ اے-1 ایوی ایشن فیول"), "Freeze < -47°C", BiText("Ultra-clean kerosene cut for turbine aircraft.", "ہوائی جہازوں کے لیے انتہائی صاف کیروسین ایندھن۔")),
                EquipmentComponent("prod_diesel", BiText("Ultra-Low Sulfur Diesel (C12–C20)", "الٹرا لو سلفر ڈیزل"), "Cetane > 51, S < 10 ppm", BiText("Compression-ignition fuel for heavy transport.", "بھاری گاڑیوں اور جنریٹرز کا ایندھن۔"))
            ),
            typesAndClassification = listOf(
                BiText("Light Ends: LPG (C3/C4) & Naphtha (C5–C9)", "ہلکے اجزاء: ایل پی جی اور نیفتھا"),
                BiText("Middle Distillates: Kerosene/Jet-A1 & Diesel", "درمیانے اجزاء: مٹی کا تیل اور ڈیزل"),
                BiText("Heavy Residue: Fuel Oil, Base Lubes & Bitumen", "بھاری اجزاء: فرنس آئل، موبل آئل اور تارکول")
            ),
            applications = listOf(
                BiText("Automotive, Aviation, Marine & Road Construction", "گاڑیاں، ہوائی جہاز، بحری جہاز اور سڑکیں")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Gasoline Boiling Range", "پیٹرول بوائلنگ رینج"), "35 – 200 °C"),
                EquipmentSpec(BiText("Jet-A1 Flash Point", "جیٹ فیول فلیش پوائنٹ"), "Min 38 °C"),
                EquipmentSpec(BiText("Diesel Flash Point", "ڈیزل فلیش پوائنٹ"), "Min 55 °C")
            ),
            maintenanceConcepts = listOf(
                BiText("Drain water bottoms from product storage tanks daily to prevent microbial growth.", "اسٹوریج ٹینکوں سے روزانہ پانی نکالیں تاکہ ایندھن میں بیکٹیریا نہ پھیلیں۔")
            ),
            commonProblems = listOf(
                BiText("Wax crystallization (Cloud Point) in cold-weather diesel.", "سرد موسم میں ڈیزل کا جم جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Ground and bond all fuel loading arms to eliminate static-spark ignition.", "فیول لوڈنگ کے دوران اسٹیٹک چنگاری سے بچنے کے لیے ارتھنگ/بانڈنگ لازمی کریں۔")
            ),
            relatedIds = listOf("eq_distillation_column", "eq_api_hydrometer"),
            references = listOf("ASTM D975 Diesel Standard", "ASTM D1655 Aviation Turbine Fuel")
        )
    )
}
