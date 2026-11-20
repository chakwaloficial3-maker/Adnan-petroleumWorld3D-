package com.example.data.repository

import com.example.R
import com.example.model.BiText
import com.example.model.PetroleumCategory

object CategoriesData {
    val allCategories: List<PetroleumCategory> = listOf(
        PetroleumCategory(
            id = "cat_geology",
            number = 1,
            title = BiText("Petroleum Geology & Exploration", "پیٹرولیم جیولوجی اور تلاش (Exploration)"),
            subtitle = BiText("Source rocks, traps, seismic surveys & logging", "چٹانیں، آئل ٹریپس، سیسمک سروے اور لاگنگ"),
            sectorTag = BiText("Upstream", "اپ اسٹریم"),
            heroImageRes = R.drawable.img_hero_rig,
            accentColorHex = 0xFF00D4FF
        ),
        PetroleumCategory(
            id = "cat_drilling_eng",
            number = 2,
            title = BiText("Drilling Engineering", "ڈرلنگ انجینئرنگ"),
            subtitle = BiText("Well planning, hydraulics, mud weights & directional drilling", "ویل پلاننگ، ڈرلنگ مڈ اور ڈائریکشنل ڈرلنگ"),
            sectorTag = BiText("Upstream", "اپ اسٹریم"),
            heroImageRes = R.drawable.img_hero_rig,
            accentColorHex = 0xFFF5B041
        ),
        PetroleumCategory(
            id = "cat_drilling_rigs",
            number = 3,
            title = BiText("Drilling Rigs & Rig Systems", "ڈرلنگ رگز اور رگ سسٹمز"),
            subtitle = BiText("Hoisting, rotating, circulating, power & well control", "ہوسٹنگ، روٹیٹنگ، سرکولیشن اور ویل کنٹرول"),
            sectorTag = BiText("Upstream", "اپ اسٹریم"),
            heroImageRes = R.drawable.img_hero_rig,
            accentColorHex = 0xFF1E88E5
        ),
        PetroleumCategory(
            id = "cat_rig_machinery",
            number = 4,
            title = BiText("Rig Machinery & Equipment", "رگ مشینری اور آلات"),
            subtitle = BiText("Mud pumps, drawworks, top drive, shale shakers & BOP", "مڈ پمپ، ڈرا ورکس، ٹاپ ڈرائیو اور بی او پی"),
            sectorTag = BiText("Upstream", "اپ اسٹریم"),
            heroImageRes = R.drawable.img_hero_rig,
            accentColorHex = 0xFF00D4FF
        ),
        PetroleumCategory(
            id = "cat_drill_string",
            number = 5,
            title = BiText("Drill String & Downhole Tools", "ڈرل اسٹرنگ اور ڈاؤن ہول ٹولز"),
            subtitle = BiText("Drill pipe, HWDP, drill collars, PDC & tricone bits", "ڈرل پائپ، کالر، اسٹیبلائزر اور PDC بٹس"),
            sectorTag = BiText("Upstream", "اپ اسٹریم"),
            accentColorHex = 0xFFFFD166
        ),
        PetroleumCategory(
            id = "cat_completion",
            number = 6,
            title = BiText("Well Completion & Workover", "ویل کمپلیشن اور ورک اوور"),
            subtitle = BiText("Casing, cementing, perforating, packers & Christmas trees", "کیسنگ، سیمنٹنگ، پیکرز اور کرسمس ٹری"),
            sectorTag = BiText("Upstream", "اپ اسٹریم"),
            heroImageRes = R.drawable.img_wellhead_production,
            accentColorHex = 0xFF2ECC71
        ),
        PetroleumCategory(
            id = "cat_production",
            number = 7,
            title = BiText("Oil & Gas Production", "تیل اور گیس کی پیداوار (Production)"),
            subtitle = BiText("Separators, manifolds, choke valves & surface facilities", "سیپریٹرز، مینی فولڈز اور سرفیس فسیلیٹیز"),
            sectorTag = BiText("Upstream", "اپ اسٹریم"),
            heroImageRes = R.drawable.img_wellhead_production,
            accentColorHex = 0xFF00D4FF
        ),
        PetroleumCategory(
            id = "cat_artificial_lift",
            number = 8,
            title = BiText("Artificial Lift Systems", "آرٹیفیشل لفٹ سسٹمز"),
            subtitle = BiText("ESP, sucker rod pumps, gas lift & progressive cavity pumps", "ESP پمپ، بیم پمپ اور گیس لفٹ"),
            sectorTag = BiText("Upstream", "اپ اسٹریم"),
            heroImageRes = R.drawable.img_wellhead_production,
            accentColorHex = 0xFFF5B041
        ),
        PetroleumCategory(
            id = "cat_gas_processing",
            number = 9,
            title = BiText("Gas Processing", "گیس پروسیسنگ"),
            subtitle = BiText("Amine sweetening, TEG dehydration & NGL recovery", "امائن سویٹننگ، ڈی ہائیڈریشن اور NGL ریکوری"),
            sectorTag = BiText("Midstream", "مڈ اسٹریم"),
            heroImageRes = R.drawable.img_refinery_complex,
            accentColorHex = 0xFF00D4FF
        ),
        PetroleumCategory(
            id = "cat_pipelines",
            number = 10,
            title = BiText("Pipelines & Transportation", "پائپ لائنز اور ٹرانسپورٹیشن"),
            subtitle = BiText("Pigging launchers, booster stations, cathodic protection", "پگ لانچر، بوسٹر پمپس اور کیتھوڈک پروٹیکشن"),
            sectorTag = BiText("Midstream", "مڈ اسٹریم"),
            heroImageRes = R.drawable.img_wellhead_production,
            accentColorHex = 0xFF1E88E5
        ),
        PetroleumCategory(
            id = "cat_storage",
            number = 11,
            title = BiText("Storage Tanks & Terminals", "اسٹوریج ٹینکس اور ٹرمینلز"),
            subtitle = BiText("Floating roof tanks, bullet vessels & custody metering", "فلوٹنگ روف ٹینکس، بلٹ ویسلز اور میٹرنگ"),
            sectorTag = BiText("Midstream", "مڈ اسٹریم"),
            heroImageRes = R.drawable.img_refinery_complex,
            accentColorHex = 0xFFFFD166
        ),
        PetroleumCategory(
            id = "cat_refinery",
            number = 12,
            title = BiText("Petroleum Refinery", "پیٹرولیم ریفائنری"),
            subtitle = BiText("Crude desalting, distillation towers & fired heaters", "ڈی سالٹنگ، ڈسٹیلیشن ٹاورز اور فرنسز"),
            sectorTag = BiText("Downstream", "ڈاؤن اسٹریم"),
            heroImageRes = R.drawable.img_refinery_complex,
            accentColorHex = 0xFFF5B041
        ),
        PetroleumCategory(
            id = "cat_refinery_units",
            number = 13,
            title = BiText("Refinery Units & Processes", "ریفائنری یونٹس اور پروسیسز"),
            subtitle = BiText("FCC, hydrocracking, catalytic reforming & sulfur recovery", "FCC، ہائیڈرو کریکنگ، ریفارمنگ اور سلفر یونٹ"),
            sectorTag = BiText("Downstream", "ڈاؤن اسٹریم"),
            heroImageRes = R.drawable.img_refinery_complex,
            accentColorHex = 0xFF00D4FF
        ),
        PetroleumCategory(
            id = "cat_products",
            number = 14,
            title = BiText("Petroleum Products", "پیٹرولیم مصنوعات (Products)"),
            subtitle = BiText("LPG, gasoline, jet fuel, diesel, bitumen & lubes", "ایل پی جی، پیٹرول، جیٹ فیول، ڈیزل اور بٹومین"),
            sectorTag = BiText("Downstream", "ڈاؤن اسٹریم"),
            heroImageRes = R.drawable.img_refinery_complex,
            accentColorHex = 0xFF2ECC71
        ),
        PetroleumCategory(
            id = "cat_instrumentation",
            number = 15,
            title = BiText("Instrumentation & Control", "انسٹرومینٹیشن اور کنٹرول"),
            subtitle = BiText("Control valves, pressure gauges, orifice meters & DCS", "کنٹرول والوز، پریشر گیجز، اوریفائس میٹر اور DCS"),
            sectorTag = BiText("Engineering", "انجینئرنگ"),
            heroImageRes = R.drawable.img_hse_safety,
            accentColorHex = 0xFF00D4FF
        ),
        PetroleumCategory(
            id = "cat_electrical",
            number = 16,
            title = BiText("Electrical Equipment", "الیکٹریکل آلات"),
            subtitle = BiText("Ex-d motors, VFD drives, transformers & switchgear", "ایکسپلوشن پروف موٹرز، VFD اور سوئچ گیئر"),
            sectorTag = BiText("Engineering", "انجینئرنگ"),
            heroImageRes = R.drawable.img_hse_safety,
            accentColorHex = 0xFFFFD166
        ),
        PetroleumCategory(
            id = "cat_mechanical",
            number = 17,
            title = BiText("Mechanical Equipment", "مکینیکل آلات"),
            subtitle = BiText("Centrifugal pumps, compressors, valves & heat exchangers", "سینٹری فیوگل پمپس، کمپریسرز، والوز اور ہیٹ ایکسچینجرز"),
            sectorTag = BiText("Engineering", "انجینئرنگ"),
            heroImageRes = R.drawable.img_wellhead_production,
            accentColorHex = 0xFF1E88E5
        ),
        PetroleumCategory(
            id = "cat_maintenance",
            number = 18,
            title = BiText("Maintenance & Troubleshooting", "مینٹیننس اور ٹربل شوٹنگ"),
            subtitle = BiText("Vibration analysis, laser alignment & mechanical seals", "وائبریشن انالیسس، الائنمنٹ اور مکینیکل سیلز"),
            sectorTag = BiText("Reliability", "مینٹیننس"),
            heroImageRes = R.drawable.img_hse_safety,
            accentColorHex = 0xFFF5B041
        ),
        PetroleumCategory(
            id = "cat_hse",
            number = 19,
            title = BiText("HSE & Industrial Safety", "ایچ ایس ای اور انڈسٹریل سیفٹی"),
            subtitle = BiText("H2S detectors, SCBA, PSV relief valves & fire systems", "H2S ڈیٹیکٹرز، SCBA، سیفٹی والوز اور فائر سسٹم"),
            sectorTag = BiText("Safety", "سیفٹی"),
            heroImageRes = R.drawable.img_hse_safety,
            accentColorHex = 0xFFFF5252
        ),
        PetroleumCategory(
            id = "cat_lab",
            number = 20,
            title = BiText("Petroleum Laboratory & Testing", "پیٹرولیم لیبارٹری اور ٹیسٹنگ"),
            subtitle = BiText("API hydrometers, flash point, viscosity & BS&W centrifuge", "API گریویٹی، فلیش پوائنٹ، وسکاسٹی اور BS&W ٹیسٹ"),
            sectorTag = BiText("Laboratory", "لیبارٹری"),
            heroImageRes = R.drawable.img_hse_safety,
            accentColorHex = 0xFF2ECC71
        )
    )
}
