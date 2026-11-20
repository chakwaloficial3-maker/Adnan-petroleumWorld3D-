package com.example.data.repository

import com.example.model.BiText
import com.example.model.DiagramType
import com.example.model.EquipmentComponent
import com.example.model.EquipmentItem
import com.example.model.EquipmentSpec
import com.example.model.Model3DType

object EquipmentDataUpstream {
    val items: List<EquipmentItem> = listOf(
        // 1. Geology
        EquipmentItem(
            id = "eq_seismic_geophone",
            categoryId = "cat_geology",
            name = BiText("Seismic Exploration & Geophone Array", "سیسمک ایکسپلوریشن اور جیوفون ایرے"),
            shortTagline = BiText("Subsurface acoustic imaging for hydrocarbon traps", "زیرِ زمین تیل اور گیس کے ذخائر کی صوتی تصویر کشی"),
            has3dModel = false,
            diagramType = DiagramType.GEOLOGY_ANTICLINE_TRAP,
            definition = BiText(
                "A geophysical exploration system using acoustic waves from Vibroseis trucks or airguns and sensitive Geophone receivers to map underground rock strata and structural traps.",
                "ایک جیو فزیکل نظام جو زیرِ زمین چٹانوں کی تہوں اور آئل ٹریپس کا نقشہ بنانے کے لیے صوتی لہریں (Acoustic Waves) اور جیوفون سینسرز استعمال کرتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Identifies anticlines, fault traps, salt domes, and stratigraphic pinch-outs prior to committing expensive exploratory drilling.",
                "مہنگی کھدائی شروع کرنے سے پہلے زیرِ زمین اینٹی کلائن اور فالٹ ٹریپس میں تیل یا گیس کے امکانات کی نشاندہی کرنا۔"
            ),
            workingPrinciple = BiText(
                "Acoustic waves travel downward and reflect off boundaries with contrasting acoustic impedance (density × velocity). Geophones convert ground velocity into electrical voltage recorded as 2D/3D seismic sections.",
                "صوتی لہریں زمین کے اندر جا کر مختلف چٹانوں سے ٹکرا کر واپس آتی ہیں، جنہیں جیوفون برقی سگنل میں بدل کر 3D سب سرفیس نقشہ بناتے ہیں۔"
            ),
            construction = BiText(
                "A rugged waterproof housing containing a suspended electromagnetic coil around a permanent magnet anchored to the ground via a steel spike.",
                "مضبوط واٹر پروف خول جس کے اندر مستقل مقناطیس کے گرد متحرک کوائل اور زمین میں گاڑنے کے لیے اسٹیل اسپائک ہوتی ہے۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("geo_coil", BiText("Moving Coil & Magnet", "موونگ کوائل اور مقناطیس"), "Copper / Alnico", BiText("Generates voltage proportional to ground particle velocity.", "زمین کی لرزش کو برقی وولٹیج میں بدلتا ہے۔")),
                EquipmentComponent("geo_vibroseis", BiText("Vibroseis Source Truck", "وائبروسیس سورس ٹرک"), "Hydraulic Shaker", BiText("Injects controlled swept-frequency acoustic waves.", "کنٹرول شدہ صوتی لہریں زمین میں بھیجتا ہے۔")),
                EquipmentComponent("geo_recorder", BiText("Seismic Acquisition Unit", "سیسمک ریکارڈر"), "24-bit ADC", BiText("Digitizes and timestamps reflections across thousands of channels.", "ہزاروں چینلز کا ڈیٹا ڈیجیٹل شکل میں محفوظ کرتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("2D Land Seismic Lines (Regional reconnaissance)", "2D لینڈ سیسمک (ابتدائی سروے)"),
                BiText("3D High-Resolution Seismic Cube (Field development)", "3D سیسمک کیوب (تفصیلی نقشہ)"),
                BiText("4D Time-Lapse Marine Streamer (Reservoir monitoring)", "4D میرین سیسمک (ذخائر کی نگرانی)")
            ),
            applications = listOf(
                BiText("Locating anticlinal hydrocarbon reservoirs", "اینٹی کلائن آئل ٹریپس تلاش کرنا"),
                BiText("Direct Hydrocarbon Indicators (Bright Spots)", "گیس زونز کی براہِ راست نشاندہی")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Natural Frequency", "قدرتی فریکوئنسی"), "10 Hz – 14 Hz"),
                EquipmentSpec(BiText("Sensitivity", "حساسیت"), "28.8 – 85 V/m/s"),
                EquipmentSpec(BiText("Survey Depth", "سروے کی گہرائی"), "Up to 10,000 m")
            ),
            maintenanceConcepts = listOf(
                BiText("Verify geophone coil resistance and tilt angle before deployment.", "لگانے سے پہلے کوائل کی مزاحمت اور جھکاؤ چیک کریں۔")
            ),
            commonProblems = listOf(
                BiText("Ground roll surface noise masking deep reflections.", "سطحی شور کی وجہ سے گہرائی کے سگنل کمزور ہونا۔"),
                BiText("Poor geophone-to-soil coupling in loose desert sand.", "ریتیلی زمین میں سینسر کی کمزور گرفت۔")
            ),
            safetyPrecautions = listOf(
                BiText("Maintain exclusion zones around seismic vibrator plates and marine airguns.", "وائبریٹر پلیٹس کے قریب محفوظ فاصلہ برقرار رکھیں۔")
            ),
            relatedIds = listOf("eq_drilling_rig", "eq_pdc_bit"),
            references = listOf("SEG Exploration Geophysics Standards", "Sheriff: Encyclopedic Dictionary of Applied Geophysics")
        ),

        // 2. Drilling Engineering
        EquipmentItem(
            id = "eq_mud_engineering",
            categoryId = "cat_drilling_eng",
            name = BiText("Drilling Fluid & Mud Mixing System", "ڈرلنگ فلوئڈ اور مڈ مکسنگ سسٹم"),
            shortTagline = BiText("Primary well control & cuttings transport medium", "ویل کنٹرول اور کٹنگز باہر لانے کا بنیادی نظام"),
            has3dModel = false,
            diagramType = DiagramType.ROTARY_RIG_CIRCULATION,
            definition = BiText(
                "Engineered water-based, oil-based, or synthetic drilling fluid circulated down the drill string to maintain hydrostatic overbalance, cool the bit, and lift rock cuttings.",
                "خاص کیمیکل محلول (Water/Oil-Based Mud) جو ہائیڈروسٹیٹک دباؤ برقرار رکھنے، بٹ کو ٹھنڈا کرنے اور کٹی ہوئی چٹانیں باہر لانے کے لیے استعمال ہوتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Prevents formation fluid influx (kicks) via hydrostatic pressure (0.052 × MW × TVD), stabilizes wellbore walls, and suspends cuttings when circulation stops.",
                "کنویں کے اندر فارمیشن پریشر کو قابو میں رکھ کر کِک یا بلو آؤٹ سے بچانا اور کنویں کی دیواروں کو گرنے سے روکنا۔"
            ),
            workingPrinciple = BiText(
                "Mud pumps push mud from active pits down the standpipe, through drill pipe and bit nozzles, and back up the annulus to shale shakers and desanders.",
                "مڈ پمپ مڈ کو پائپ کے ذریعے بٹ تک بھیجتے ہیں اور کنویں کے خالی حصے (Annulus) سے کٹنگز سمیت شیل شیکر پر واپس لاتے ہیں۔"
            ),
            construction = BiText(
                "Active mud tanks equipped with mud agitators, centrifugal mixing hoppers, shale shakers, degassers, desanders, and mud balance testing kits.",
                "اسٹیل مڈ ٹینکس جن میں ایجیٹیٹرز، مکسنگ ہوپر، شیل شیکر، ڈی گیسر اور ڈی سینڈر نصب ہوتے ہیں۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("mud_hopper", BiText("Venturi Mixing Hopper", "وینچوری مکسنگ ہوپر"), "Carbon Steel", BiText("Shears and blends barite, bentonite, and polymers.", "بیرائٹ اور بینٹونائٹ کو مڈ میں حل کرتا ہے۔")),
                EquipmentComponent("mud_shaker", BiText("Shale Shaker Screens", "شیل شیکر اسکرینز"), "Stainless Wire Mesh", BiText("Primary solids control removing coarse drill cuttings.", "بڑی چٹانی کٹنگز کو مڈ سے چھان کر الگ کرتا ہے۔")),
                EquipmentComponent("mud_degasser", BiText("Vacuum Degasser", "ویکیوم ڈی گیسر"), "Vacuum Chamber", BiText("Strips entrained methane/H2S gas from returning mud.", "واپس آنے والی مڈ سے پھنسی ہوئی گیس نکالتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Water-Based Mud (WBM) – Bentonite & Polymer", "واٹر بیسڈ مڈ (WBM)"),
                BiText("Oil-Based Mud (OBM) – High-temp/shale inhibition", "آئل بیسڈ مڈ (OBM)"),
                BiText("Synthetic-Based Mud (SBM) – Offshore environmental compliance", "سنتھیٹک بیسڈ مڈ (SBM)")
            ),
            applications = listOf(
                BiText("HPHT deep exploratory wells", "ہائی پریشر ہائی ٹمپریچر (HPHT) کنویں"),
                BiText("Horizontal & extended-reach shale drilling", "افقی اور ڈائریکشنل ڈرلنگ")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Mud Weight Range", "مڈ ویٹ کی حد"), "8.5 – 20.5 ppg"),
                EquipmentSpec(BiText("Funnel Viscosity", "فنل وسکاسٹی"), "38 – 65 sec/qt"),
                EquipmentSpec(BiText("API Fluid Loss", "فلوئڈ لاس"), "< 5.0 mL / 30 min")
            ),
            maintenanceConcepts = listOf(
                BiText("Check mud weight and Marsh funnel viscosity every 30 minutes at the flowline.", "ہر 30 منٹ بعد مڈ بیلنس سے وزن اور فنل وسکاسٹی چیک کریں۔")
            ),
            commonProblems = listOf(
                BiText("Lost circulation into fractured formations.", "دراڑوں والی چٹانوں میں مڈ کا ضائع ہونا (Lost Circulation)۔"),
                BiText("Gas-cut mud reducing hydrostatic head.", "گیس ملنے سے مڈ کا وزن کم ہو جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Wear splash goggles and chemical gloves when handling caustic soda or OBM.", "کیمیکلز یا OBM استعمال کرتے وقت چشمہ اور دستانے ضرور پہنیں۔")
            ),
            relatedIds = listOf("eq_mud_pump", "eq_bop_stack", "eq_hydrocyclone"),
            references = listOf("API RP 13B-1 / 13B-2 Drilling Fluid Testing", "ASME Shale Shaker & Solids Control Handbook")
        ),

        // 3. Drilling Rigs and Rig Systems
        EquipmentItem(
            id = "eq_drilling_rig",
            categoryId = "cat_drilling_rigs",
            name = BiText("Rotary Drilling Rig Assembly", "روٹری ڈرلنگ رگ اسمبلی"),
            shortTagline = BiText("Integrated hoisting, rotating & circulating well construction plant", "مکمل ڈرلنگ پلانٹ جو کنویں کی کھدائی سرانجام دیتا ہے"),
            has3dModel = true,
            model3dType = Model3DType.DRILLING_RIG,
            diagramType = DiagramType.ROTARY_RIG_CIRCULATION,
            isPopular = true,
            definition = BiText(
                "A complete mechanical and power plant integrating five synchronized systems—Power, Hoisting, Rotating, Circulating, and Well Control—to drill oil and gas wells.",
                "ایک مکمل صنعتی نظام جو پانچ بنیادی سسٹمز (پاور، ہوسٹنگ، روٹیٹنگ، سرکولیٹنگ اور ویل کنٹرول) کو ملا کر تیل اور گیس کے کنویں کھودتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Applies controlled weight-on-bit (WOB) and rotary torque while circulating drilling fluid and safeguarding the wellbore against blowouts.",
                "بٹ پر وزن اور گردش فراہم کرنا، مڈ سرکولیٹ کرنا اور کنویں کو بلو آؤٹ سے محفوظ رکھنا۔"
            ),
            workingPrinciple = BiText(
                "Diesel-electric generators feed SCR/VFD drives powering the drawworks hoist, top drive rotation, and triplex mud pumps simultaneously.",
                "ڈیزل الیکٹرک جنریٹرز VFD کے ذریعے ڈرا ورکس، ٹاپ ڈرائیو اور مڈ پمپس کو بیک وقت طاقت فراہم کرتے ہیں۔"
            ),
            construction = BiText(
                "Elevated steel substructure supporting the rig floor and BOP clearance, topped by a lattice derrick mast holding the crown block, traveling block, and top drive.",
                "بلند اسٹیل سب اسٹرکچر اور اس پر نصب جالی دار ڈیرک ٹاور، کراؤن بلاک، ٹریولنگ بلاک اور ٹاپ ڈرائیو۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("rig_derrick", BiText("Lattice Derrick Mast", "ڈیرک ماسٹ"), "ASTM A572 Steel", BiText("Supports vertical hook load and drill pipe racking.", "پائپوں کا عمودی وزن اور کھدائی کا دباؤ برداشت کرتا ہے۔")),
                EquipmentComponent("rig_crown", BiText("Crown & Traveling Block", "کراؤن اور ٹریولنگ بلاک"), "Forged Alloy Sheaves", BiText("Block-and-tackle pulley system multiplying hoisting force.", "بھاری ڈرل اسٹرنگ کو اوپر نیچے کرنے والا پلّی سسٹم۔")),
                EquipmentComponent("rig_top_drive", BiText("Top Drive System", "ٹاپ ڈرائیو سسٹم"), "AC VFD Motor", BiText("Rotates drill string directly from the traveling block.", "پائپوں کو اوپر سے گھماتا اور مڈ پمپ کرتا ہے۔")),
                EquipmentComponent("rig_drawworks", BiText("Drawworks Hoist", "ڈرا ورکس ہوسٹ"), "Cast Steel Drum", BiText("Spools steel drilling line for tripping in and out.", "اسٹیل کیبل لپیٹ کر بوجھ اٹھاتا اور بریک لگاتا ہے۔")),
                EquipmentComponent("rig_substructure", BiText("Substructure & Floor", "سب اسٹرکچر اور فلور"), "Structural Steel", BiText("Provides working deck and BOP stack height clearance.", "عملے کے کام کی جگہ اور نیچے BOP کی تنصیب کے لیے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Land Rotary Rigs (AC-VFD / Mechanical)", "لینڈ روٹری رگ"),
                BiText("Jack-Up Offshore Rigs (Shallow water up to 400 ft)", "جیک اپ آف شور رگ"),
                BiText("Semi-Submersible & Drillships (Deepwater)", "سیمی سب مرسیبل اور ڈرل شپ")
            ),
            applications = listOf(
                BiText("Onshore & offshore exploration and development wells", "خشکی اور سمندر میں تیل و گیس کی کھدائی"),
                BiText("Geothermal & deep gas drilling", "گہرے گیس اور جیوتھرمل کنویں")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Hook Load Capacity", "ہُک لوڈ گنجائش"), "500 – 1,000 Short Tons"),
                EquipmentSpec(BiText("Drawworks Power", "ڈرا ورکس پاور"), "1,500 – 3,000 HP"),
                EquipmentSpec(BiText("Max Drilling Depth", "زیادہ سے زیادہ گہرائی"), "25,000 – 35,000 ft")
            ),
            maintenanceConcepts = listOf(
                BiText("Perform ton-mile cut-and-slip program on the drilling wire rope.", "ڈرلنگ کیبل پر ٹن مائل (Ton-Mile) حساب سے کٹ اینڈ سلپ کریں۔"),
                BiText("Daily MPI inspection of top drive bails, elevator links, and hook.", "ٹاپ ڈرائیو لنکس اور ایلیویٹرز کا روزانہ معائنہ۔")
            ),
            commonProblems = listOf(
                BiText("Stuck drill pipe due to differential sticking or pack-off.", "کنویں میں ڈرل پائپ کا پھنس جانا (Stuck Pipe)۔"),
                BiText("Crown-o-matic trip failure if limiter switches drift.", "کراؤن سیفٹی سوئچ کی خرابی۔")
            ),
            safetyPrecautions = listOf(
                BiText("Verify Crown-O-Matic and Floor-Saver electronic limits every shift.", "ہر شفٹ میں کراؤن سیفٹی لیمٹ چیک کریں۔"),
                BiText("Keep hands clear of rotary slips and hydraulic tongs (Red Zone).", "روٹری ٹیبل اور پاور ٹونگز کے ریڈ زون سے دور رہیں۔")
            ),
            relatedIds = listOf("eq_mud_pump", "eq_bop_stack", "eq_pdc_bit"),
            references = listOf("API Spec 4F Drilling & Well Servicing Structures", "IADC Drilling Manual")
        ),

        // 4. Rig Machinery & Equipment
        EquipmentItem(
            id = "eq_mud_pump",
            categoryId = "cat_rig_machinery",
            name = BiText("Triplex Reciprocating Mud Pump", "ٹرائی پلیکس مڈ پمپ"),
            shortTagline = BiText("High-pressure positive displacement rig heart", "رگ کا دل جو 7,500 psi تک مڈ پمپ کرتا ہے"),
            has3dModel = true,
            model3dType = Model3DType.MUD_PUMP,
            diagramType = DiagramType.MUD_PUMP_TRIPLEX,
            isPopular = true,
            definition = BiText(
                "A three-cylinder single-acting positive displacement reciprocating pump that delivers high-pressure drilling mud down the drill string.",
                "تین سلنڈر والا پازیٹو ڈسپلیسمنٹ پمپ جو انتہائی ہائی پریشر پر ڈرلنگ مڈ کو کنویں میں پمپ کرتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Generates hydraulic horsepower to clean rock cuttings under the bit jets, drive mud motors/MWD tools, and circulate cuttings to surface.",
                "بٹ کے نوزلز پر ہائیڈرولک طاقت پیدا کرنا، ڈاؤن ہول موٹر چلانا اور کٹنگز کو سطح تک لانا۔"
            ),
            workingPrinciple = BiText(
                "Three pistons phased 120° apart reciprocate inside chrome-iron liners. On suction stroke fluid enters via suction valve; on discharge stroke check valves open into a nitrogen-charged pulsation dampener.",
                "تین پسٹن 120 ڈگری کے فرق سے آگے پیچھے حرکت کرتے ہیں، جس سے مڈ مسلسل سکشن والو سے اندر آ کر ڈسچارج والو کے ذریعے نکلتی ہے۔"
            ),
            construction = BiText(
                "Divided into a heavy Power End (bull gear, crankshaft, crossheads) and replaceable modular Fluid End (cylinders, liners, pistons, valves) with a spherical pulsation dampener.",
                "دو حصوں پر مشتمل: پاور اینڈ (گیئرز اور کرینک شافٹ) اور فلوئڈ اینڈ (سلنڈرز، لائنرز، پسٹن اور پلسیشن ڈیمپنر)۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("mp_power_end", BiText("Power End Crankcase", "پاور اینڈ کرینک کیس"), "Alloy Steel", BiText("Converts rotary motor drive into linear crosshead stroke.", "موٹر کی گردش کو پسٹن کی سیدھی حرکت میں بدلتا ہے۔")),
                EquipmentComponent("mp_fluid_cylinders", BiText("Triplex Fluid Cylinders", "فلوئڈ سلنڈرز"), "Forged 4135 Steel", BiText("Holds liners and high-pressure suction/discharge valves.", "ہائی پریشر لائنرز اور والوز کو سنبھالتا ہے۔")),
                EquipmentComponent("mp_pistons", BiText("Pistons & Pony Rods", "پسٹن اور راڈز"), "Urethane / Steel", BiText("Displaces abrasive drilling fluid at up to 7,500 psi.", "مڈ کو انتہائی دباؤ کے ساتھ آگے دھکیلتا ہے۔")),
                EquipmentComponent("mp_dampener", BiText("Pulsation Dampener", "پلسیشن ڈیمپنر"), "N2 Bladder Vessel", BiText("Absorbs flow spikes to protect standpipe and sensors.", "پریشر کے جھٹکوں کو جذب کر کے بہاؤ ہموار بناتا ہے۔")),
                EquipmentComponent("mp_manifold", BiText("Discharge Manifold", "ڈسچارج مینی فولڈ"), "API 5000/7500", BiText("Merges flow from all 3 cylinders to rig standpipe.", "تینوں سلنڈرز کا بہاؤ اسٹینڈ پائپ میں بھیجتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Triplex Single-Acting (Industry standard, smoother flow)", "ٹرائی پلیکس سنگل ایکٹنگ (معیاری)"),
                BiText("Duplex Double-Acting (Legacy high-volume low-pressure)", "ڈوپلیکس ڈبل ایکٹنگ (قدیم قسم)")
            ),
            applications = listOf(
                BiText("Primary mud circulation on land & offshore rigs", "تمام ڈرلنگ رگز پر مڈ سرکولیشن"),
                BiText("Well killing & high-pressure cementing support", "ویل کِلنگ آپریشنز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Rated Power", "پاور ریٹنگ"), "1,000 – 2,200 HP"),
                EquipmentSpec(BiText("Max Working Pressure", "زیادہ سے زیادہ پریشر"), "5,000 – 7,500 psi"),
                EquipmentSpec(BiText("Volumetric Efficiency", "والیومیٹرک ایفیشنسی"), "95% – 97%")
            ),
            maintenanceConcepts = listOf(
                BiText("Maintain liner wash water spray continuously to cool pistons and flush sand.", "پسٹن ٹھنڈے رکھنے کے لیے لائنر واش سسٹم ہمیشہ چالو رکھیں۔"),
                BiText("Check N2 pre-charge pressure in pulsation dampener (typically 30–50% of working pressure).", "پلسیشن ڈیمپنر میں نائٹروجن پری چارج باقاعدگی سے چیک کریں۔")
            ),
            commonProblems = listOf(
                BiText("Liner & piston swab washout causing pressure drop.", "پسٹن یا لائنر کٹ جانے سے پمپ پریشر گر جانا۔"),
                BiText("Knocking sound from worn suction/discharge valve inserts or low supercharge pressure.", "والو خراب ہونے یا سپر چارج پریشر کم ہونے سے کھٹکھٹاہٹ کی آواز۔")
            ),
            safetyPrecautions = listOf(
                BiText("Never tighten fluid module studs or open valve covers while pump is pressurized.", "پمپ پر دباؤ موجود ہونے کی حالت میں والو کور ہرگز نہ کھولیں۔"),
                BiText("Verify reset relief valve shear pin rating before startup.", "اسٹارٹ کرنے سے پہلے سیفٹی ریلیف والو کی شیئر پِن چیک کریں۔")
            ),
            relatedIds = listOf("eq_drilling_rig", "eq_bop_stack", "eq_hydrocyclone"),
            references = listOf("API Spec 7K Drilling & Well Servicing Equipment", "National Oilwell Varco Triplex Pump Manual")
        ),

        EquipmentItem(
            id = "eq_bop_stack",
            categoryId = "cat_rig_machinery",
            name = BiText("Blowout Preventer (BOP) Stack", "بلو آؤٹ پریوینٹر (BOP) اسٹیک"),
            shortTagline = BiText("Last line of defense against uncontrolled well kicks", "کنویں کے بے قابو دباؤ (Blowout) کو روکنے والا سب سے اہم حفاظتی نظام"),
            has3dModel = true,
            model3dType = Model3DType.BOP_STACK,
            diagramType = DiagramType.BOP_WELL_CONTROL,
            isPopular = true,
            definition = BiText(
                "A high-pressure hydraulic valve assembly mounted directly atop the wellhead to seal, control, and monitor oil & gas wells during drilling kicks.",
                "ہائی پریشر ہائیڈرولک والوز کا مجموعہ جو ویل ہیڈ کے اوپر لگایا جاتا ہے تاکہ ڈرلنگ کے دوران کِک یا بلو آؤٹ کو فوری روکا جا سکے۔"
            ),
            purposeAndFunction = BiText(
                "Shuts in the annulus or open hole instantaneously when formation pressure exceeds mud hydrostatic pressure, allowing safe kick circulation.",
                "جب زیرِ زمین گیس یا تیل کا دباؤ مڈ کے دباؤ سے بڑھ جائے تو کنویں کو فوری سیل کر کے محفوظ کنٹرول فراہم کرنا۔"
            ),
            workingPrinciple = BiText(
                "Accumulator unit (Koomey) stores hydraulic fluid at 3,000 psi to actuate annular elastomer packing or opposing steel pipe/shear rams within seconds.",
                "کومے اکومولیٹر یونٹ 3,000 psi ہائیڈرولک دباؤ کے ذریعے چند سیکنڈ میں ربڑ پیکنگ یا فولادی ریمز کو بند کر دیتا ہے۔"
            ),
            construction = BiText(
                "Vertical stack combining an upper Annular Preventer, double/triple Ram Preventers (pipe rams & blind-shear rams), drilling spool with choke/kill lines, and wellhead flange.",
                "اوپر اینولر پریوینٹر، درمیان میں پائپ اور شیئر ریمز، سائیڈ پر چوک و کِل لائن اسپول اور نیچے ویل ہیڈ فلینج۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("bop_annular", BiText("Annular BOP Preventer", "اینولر بی او پی"), "Steel + Nitrile Element", BiText("Conforms to any pipe size or seals open hole.", "ہر سائز کے پائپ یا خالی سوراخ کو سیل کرتا ہے۔")),
                EquipmentComponent("bop_pipe_rams", BiText("Pipe & Blind-Shear Rams", "پائپ اور شیئر ریمز"), "API 16A Forged Steel", BiText("Seals specific pipe OD or shears drill pipe in emergencies.", "پائپ کے گرد سیل لگاتا ہے یا ایمرجنسی میں پائپ کاٹ دیتا ہے۔")),
                EquipmentComponent("bop_spool", BiText("Drilling Spool & Outlets", "ڈرلنگ اسپول"), "Forged Alloy", BiText("Connects choke manifold and kill lines.", "چوک اور کِل لائنز کو کنویں سے جوڑتا ہے۔")),
                EquipmentComponent("bop_wellhead_flange", BiText("Wellhead Connector", "ویل ہیڈ کنیکٹر"), "API 16A Flange", BiText("Locks stack onto surface casing head.", "پورے BOP کو کیسنگ ہیڈ پر مضبوطی سے جوڑتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Surface Land BOP Stack (5,000–15,000 psi)", "سرفیس لینڈ BOP اسٹیک"),
                BiText("Subsea Multiplex BOP Stack (Deepwater ROV operable)", "سب سی میرین BOP اسٹیک")
            ),
            applications = listOf(
                BiText("Primary well control during drilling & workover", "ڈرلنگ اور ورک اوور کے دوران ویل کنٹرول"),
                BiText("Stripping pipe under pressure", "پریشر کے تحت پائپ اتارنا یا نکالنا")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Pressure Rating", "پریشر ریٹنگ"), "5,000 / 10,000 / 15,000 psi"),
                EquipmentSpec(BiText("Closing Time (Ram)", "بند ہونے کا وقت"), "< 30 seconds"),
                EquipmentSpec(BiText("H2S Trim Standard", "سلفر سیفٹی معیار"), "NACE MR0175")
            ),
            maintenanceConcepts = listOf(
                BiText("Pressure test BOP every 14–21 days and after any seal repair per API Std 53.", "API Std 53 کے مطابق ہر 14 تا 21 دن بعد BOP کا پریشر ٹیسٹ کریں۔")
            ),
            commonProblems = listOf(
                BiText("Annular element fatigue from excessive stripping.", "بار بار پائپ گزارنے سے ربڑ ایلیمنٹ کا گھس جانا۔"),
                BiText("Hydraulic line leaks reducing accumulator closing pressure.", "ہائیڈرولک لیکج سے کلوزنگ پریشر کم ہونا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Never close blind-shear rams with pipe in hole unless emergency shear is authorized.", "ایمرجنسی اجازت کے بغیر شیئر ریمز بند نہ کریں۔"),
                BiText("Conduct weekly BOP drills with rig crew.", "ہر ہفتے عملے کے ساتھ BOP ایمرجنسی ڈرل کریں۔")
            ),
            relatedIds = listOf("eq_drilling_rig", "eq_christmas_tree", "eq_mud_pump"),
            references = listOf("API Standard 53 Well Control Equipment Systems", "API Spec 16A Drill-Through Equipment")
        ),

        EquipmentItem(
            id = "eq_hydrocyclone",
            categoryId = "cat_rig_machinery",
            name = BiText("Hydrocyclone Desander Cone (Small Component)", "ہائیڈروسائیکلون ڈی سینڈر کون"),
            shortTagline = BiText("Centrifugal fine-solids separator cone for drilling mud", "مڈ سے باریک ریت الگ کرنے والا کون نما پرزہ"),
            has3dModel = false,
            diagramType = DiagramType.ROTARY_RIG_CIRCULATION,
            isSmallComponent = true,
            definition = BiText(
                "A compact polyurethane conical separator using tangential centrifugal force—with no moving parts—to strip abrasive sand (45–75 microns) from drilling fluid.",
                "بغیر کسی متحرک پرزے کے پولی یوریتھین کون جو سینٹری فیوگل فورس سے مڈ میں سے باریک ریت الگ کرتی ہے۔"
            ),
            purposeAndFunction = BiText(
                "Protects expensive mud pump liners, valves, and downhole MWD turbines from abrasive sand erosion.",
                "مڈ پمپ کے لائنرز اور ڈاؤن ہول آلات کو ریت کی رگڑ سے بچانا۔"
            ),
            workingPrinciple = BiText(
                "Feed mud enters tangentially at 30–40 psi creating a high-G vortex. Heavy sand slides down the cone wall to the apex underflow while clean mud exits upward through the vortex finder.",
                "تیز رفتار بھنور کی وجہ سے بھاری ریت دیوار کے ساتھ نیچے نکلتی ہے اور صاف مڈ اوپر سے واپس ٹینک میں جاتی ہے۔"
            ),
            construction = BiText(
                "Abrasion-resistant polyurethane cone body with tangential feed inlet, upper vortex finder pipe, and replaceable rubber apex nozzle.",
                "رگڑ برداشت کرنے والی پولی یوریتھین باڈی، اوپری وورٹیکس فائنڈر اور نیچے تبدیل ہونے والا ایپکس نوزل۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("hc_body", BiText("Conical Polyurethane Shell", "کونیکل باڈی"), "Polyurethane", BiText("Resists abrasive sand erosion inside vortex.", "ریت کی رگڑ کو برداشت کرتی ہے۔")),
                EquipmentComponent("hc_apex", BiText("Apex Discharge Nozzle", "ایپکس نوزل"), "Rubber / Ceramic", BiText("Controls underflow solids rope/spray discharge.", "نیچے سے ریت کے اخراج کو کنٹرول کرتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Desander (8–12 inch cones, removes > 45 micron solids)", "ڈی سینڈر (8–12 انچ کون)"),
                BiText("Desilter (4 inch cones, removes 15–45 micron silt)", "ڈی سلٹر (4 انچ کون)")
            ),
            applications = listOf(
                BiText("Rig solids control package downstream of shale shakers", "شیل شیکر کے بعد رگ سالڈز کنٹرول")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Feed Head", "فیڈ پریشر"), "75 ft head (~32–40 psi)"),
                EquipmentSpec(BiText("Cut Point", "فلٹریشن سائز"), "40–60 microns")
            ),
            maintenanceConcepts = listOf(
                BiText("Inspect apex discharge shape: a hollow spray umbrella indicates proper operation; solid rope indicates plugging.", "ایپکس سے نکلنے والی دھار چھتری نما ہونی چاہیے؛ اگر رسی جیسی ہو تو اندر کچرا پھنسا ہے۔")
            ),
            commonProblems = listOf(
                BiText("Plugged apex from coarse shale pieces bypassing damaged shaker screens.", "پھٹی ہوئی شیکر اسکرین سے بڑے ٹکڑے آ کر کون بند کر دیتے ہیں۔")
            ),
            safetyPrecautions = listOf(
                BiText("Isolate centrifugal feed pump before disassembling Victaulic cone clamps.", "کون کھولنے سے پہلے فیڈ پمپ بند اور لاک آؤٹ کریں۔")
            ),
            relatedIds = listOf("eq_mud_pump", "eq_mud_engineering"),
            references = listOf("IADC Solids Control Manual")
        ),

        // 5. Drill String and Downhole Tools
        EquipmentItem(
            id = "eq_pdc_bit",
            categoryId = "cat_drill_string",
            name = BiText("PDC Drill Bit & Cutters", "پی ڈی سی ڈرل بٹ اور کٹرز (PDC Bit)"),
            shortTagline = BiText("Polycrystalline Diamond Compact shearing drill bit", "مصنوعی ہیرے کے کٹرز والی جدید ڈرل بٹ"),
            has3dModel = false,
            diagramType = DiagramType.ROTARY_RIG_CIRCULATION,
            isSmallComponent = true,
            definition = BiText(
                "A fixed-cutter rotary drill bit fitted with synthetic Polycrystalline Diamond Compact (PDC) cutters bonded to tungsten-carbide studs that shear rock continuously.",
                "بغیر رولر والی مضبوط ڈرل بٹ جس پر مصنوعی ہیرے (PDC) کے کٹرز لگے ہوتے ہیں جو چٹانوں کو کتر کر تیز رفتار کھدائی کرتے ہیں۔"
            ),
            purposeAndFunction = BiText(
                "Achieves high Rate of Penetration (ROP) and long bit runs in shale, sandstone, and carbonate formations without moving bearings that wear out.",
                "بغیر بیرنگ کے طویل گھنٹوں تک تیز رفتاری سے چٹانیں کاٹنا۔"
            ),
            workingPrinciple = BiText(
                "As the drill string rotates, angled PDC diamond cutters fail the rock in shear rather than crushing it, while replaceable tungsten-carbide nozzles jet mud to cool cutters and clear cuttings.",
                "گھومتے ہوئے ہیرے کے کٹرز چٹان کو چھیلتے ہیں اور نوزلز سے نکلنے والی مڈ کٹنگز کو فوری بہا لے جاتی ہے۔"
            ),
            construction = BiText(
                "One-piece matrix-body (tungsten carbide powder infiltrated with binder) or milled steel body with spiral blades, PDC cutters, gauge pads, and hydraulic junk slots.",
                "میٹرکس یا اسٹیل باڈی جس پر اسپائرل بلیڈز، PDC ڈائمنڈ کٹرز اور ہائیڈرولک نوزلز لگے ہوتے ہیں۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("bit_cutters", BiText("PDC Diamond Cutters", "ہیرے کے کٹرز"), "Synthetic Diamond + WC", BiText("Primary shearing elements cutting the rock formation.", "چٹان کاٹنے والے اصل تیز دھار حصے۔")),
                EquipmentComponent("bit_nozzles", BiText("Hydraulic Jet Nozzles", "جیٹ نوزلز"), "Tungsten Carbide", BiText("Accelerate drilling mud for bottom-hole cleaning.", "مڈ کی تیز دھار سے بٹ کو صاف اور ٹھنڈا رکھتے ہیں۔")),
                EquipmentComponent("bit_gauge", BiText("Gauge Protection Pads", "گیج پیڈز"), "Thermally Stable Diamond", BiText("Maintain exact borehole diameter.", "کنویں کا قطر بالکل درست برقرار رکھتے ہیں۔"))
            ),
            typesAndClassification = listOf(
                BiText("Matrix-Body PDC (Erosion-resistant for hard/abrasive rock)", "میٹرکس باڈی PDC بٹ"),
                BiText("Steel-Body PDC (High impact & deep junk slots for soft shale)", "اسٹیل باڈی PDC بٹ"),
                BiText("Roller-Cone Tricone Bit (Hard fractured chert/conglomerates)", "ٹرائی کون رولر بٹ")
            ),
            applications = listOf(
                BiText("Directional & horizontal rotary steerable drilling", "ڈائریکشنل اور افقی ڈرلنگ"),
                BiText("Long shale and limestone intervals", "شیل اور چونے کے پتھر کی طویل تہیں")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Common Sizes", "عام سائز"), "6\", 8.5\", 12.25\", 17.5\""),
                EquipmentSpec(BiText("Cutter Diameter", "کٹر سائز"), "13 mm – 19 mm"),
                EquipmentSpec(BiText("IADC Code Example", "IADC کوڈ"), "M423 / S323")
            ),
            maintenanceConcepts = listOf(
                BiText("Dull grade every bit per IADC 8-character system upon tripping out.", "باہر نکالنے پر IADC معیار کے مطابق بٹ کی گھساوٹ درج کریں۔")
            ),
            commonProblems = listOf(
                BiText("Bit balling in sticky reactive clays.", "چپچپی مٹی کی وجہ سے بٹ پر مٹی جم جانا (Bit Balling)۔"),
                BiText("Whirl vibration chipping diamond cutters.", "وائبریشن سے کٹرز کا ٹوٹ جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Always use bit breaker plate and hydraulic tongs; never strike carbide nozzles with hammers.", "بٹ کھولنے کے لیے ہمیشہ بٹ بریکر استعمال کریں، ہتھوڑا نہ ماریں۔")
            ),
            relatedIds = listOf("eq_drilling_rig", "eq_mud_engineering"),
            references = listOf("IADC Dull Grading System", "SPE Drilling Bit Hydraulics Guide")
        ),

        // 6. Well Completion and Workover
        EquipmentItem(
            id = "eq_christmas_tree",
            categoryId = "cat_completion",
            name = BiText("Christmas Tree & Wellhead", "کرسمس ٹری اور ویل ہیڈ اسمبلی"),
            shortTagline = BiText("Surface pressure-control valve tree on producing wells", "پیداواری کنویں کے اوپر لگا تیل و گیس کنٹرول کرنے والا والو سسٹم"),
            has3dModel = true,
            model3dType = Model3DType.CHRISTMAS_TREE,
            diagramType = DiagramType.CHRISTMAS_TREE_STACK,
            isPopular = true,
            definition = BiText(
                "An upright assembly of high-pressure gate valves, spools, chokes, and pressure gauges installed on top of a completed oil or gas well.",
                "مکمل ہونے والے کنویں کے منہ پر نصب ہائی پریشر والوز، چوک اور گیجز کا عمودی نظام جو شکل میں درخت جیسا دکھائی دیتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Controls hydrocarbon flow rate, provides safe well shut-in isolation, permits kill fluid injection, and grants vertical access for wireline/workover tools.",
                "تیل و گیس کی پیداوار کی رفتار کنٹرول کرنا، کنویں کو محفوظ طریقے سے بند کرنا اور وائر لائن ٹیسٹنگ کی سہولت دینا۔"
            ),
            workingPrinciple = BiText(
                "Well fluids rise through production tubing into the wellhead tubing spool, pass Lower and Upper Master Valves, turn horizontally through the Flow Wing Valve, and throttle across the Production Choke into flowlines.",
                "تیل و گیس نیچے ٹیوبنگ سے آ کر ماسٹر والوز سے گزرتا ہے اور وِنگ والو اور چوک کے ذریعے فلو لائن میں داخل ہوتا ہے۔"
            ),
            construction = BiText(
                "API 6A forged alloy steel monoblock or stacked flanged gate valves (Lower/Upper Master, Flow/Kill Wing, Swab) bolted atop casing and tubing heads.",
                "API 6A فورجڈ اسٹیل سے بنے فلینجڈ والوز جو کیسنگ ہیڈ اور ٹیوبنگ ہیڈ کے اوپر نصب ہوتے ہیں۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("xt_wellhead", BiText("Casing & Tubing Head", "کیسنگ اور ٹیوبنگ ہیڈ"), "API 6A Forged Steel", BiText("Suspends casing/tubing strings and seals annulus.", "کیسنگ اور ٹیوبنگ کا وزن اٹھاتا ہے۔")),
                EquipmentComponent("xt_master_valves", BiText("Lower & Upper Master Valves", "ماسٹر والوز"), "AISI 4130 + Inconel", BiText("Primary vertical shut-in valves; Upper is often hydraulic SSV.", "کنویں کو مکمل بند کرنے والے مرکزی والوز۔")),
                EquipmentComponent("xt_wing_valves", BiText("Flow & Kill Wing Valves", "ونگ والوز"), "API 6A PSL-3", BiText("Isolate surface flowline and kill-line ports.", "فلو لائن اور کِل لائن کو الگ کرتے ہیں۔")),
                EquipmentComponent("xt_choke", BiText("Production Choke", "پروڈکشن چوک"), "Tungsten Carbide", BiText("Regulates drawdown pressure and daily flow rate.", "دباؤ اور پیداوار کی مقدار کنٹرول کرتا ہے۔")),
                EquipmentComponent("xt_swab_cap", BiText("Swab Valve & Tree Cap", "سواب والو اور ٹری کیپ"), "Forged Steel", BiText("Provides top lubricator access for wireline tools.", "اوپر سے ٹیسٹنگ ٹولز اتارنے کا راستہ۔"))
            ),
            typesAndClassification = listOf(
                BiText("Conventional Stacked Spool Tree", "روایتی اسیکڈ اسپول ٹری"),
                BiText("Compact Monoblock / Horizontal Subsea Tree", "مونو بلاک اور سب سی ٹری")
            ),
            applications = listOf(
                BiText("Naturally flowing oil, gas & gas-condensate wells", "قدرتی دباؤ سے بہنے والے تیل و گیس کے کنویں"),
                BiText("Water & gas injection wells", "پانی یا گیس انجیکشن کے کنویں")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("API 6A Pressure Classes", "پریشر کلاسز"), "2,000 – 20,000 psi"),
                EquipmentSpec(BiText("Temperature Class", "درجہ حرارت کلاس"), "P-U (-29°C to 121°C)")
            ),
            maintenanceConcepts = listOf(
                BiText("Grease valve body cavities periodically and cycle manual gate valves quarterly.", "والو کیویٹی میں باقاعدہ گریسنگ اور والوز کی حرکت چیک کریں۔")
            ),
            commonProblems = listOf(
                BiText("Sand erosion on choke bean and wing valve seats.", "ریت کی رگڑ سے چوک بین کا کٹ جانا۔"),
                BiText("Annulus sustained casing pressure (SCP) from packer/seal leak.", "پیکر لیکج سے اینولس میں دباؤ بڑھنا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Always verify ESD (Emergency Shutdown) hydraulic actuator slam time.", "ایمرجنسی شٹ ڈاؤن (ESD) والو کے بند ہونے کا وقت چیک رکھیں۔")
            ),
            relatedIds = listOf("eq_separator_3phase", "eq_bop_stack", "eq_gate_valve"),
            references = listOf("API Spec 6A Wellhead & Christmas Tree Equipment")
        ),

        // 7. Oil and Gas Production
        EquipmentItem(
            id = "eq_separator_3phase",
            categoryId = "cat_production",
            name = BiText("Three-Phase Production Separator", "تھری فیز پروڈکشن سیپریٹر"),
            shortTagline = BiText("Separates raw wellstream into gas, crude oil & produced water", "خام تیل، گیس اور پانی کو ایک دوسرے سے الگ کرنے والا پریشر ویسل"),
            has3dModel = true,
            model3dType = Model3DType.SEPARATOR_3PHASE,
            diagramType = DiagramType.THREE_PHASE_SEPARATOR,
            isPopular = true,
            definition = BiText(
                "A horizontal or vertical ASME pressure vessel engineered to separate multiphase well fluids continuously into distinct gas, hydrocarbon liquid, and free water phases.",
                "ایسا پریشر ویسل جو کنویں سے نکلنے والے ملے جلے سیال کو گیس، خام تیل اور پانی کے تین الگ حصوں میں تقسیم کرتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Stabilizes crude oil vapor pressure, removes corrosive water before pipeline transport, and prevents liquid carryover into gas compressors.",
                "گیس کو مائع کے قطروں سے پاک کرنا، تیل سے نمکین پانی الگ کرنا اور پائپ لائن کی حفاظت کرنا۔"
            ),
            workingPrinciple = BiText(
                "Inlet stream hits an inlet diverter causing rapid momentum drop for primary gas breakout. Gravity settling separates dense water below oil across a retention weir plate, while gas exits upward through a wire-mesh mist extractor.",
                "انلیٹ ڈائیورٹر سے ٹکرا کر گیس اوپر اٹھتی ہے اور مسٹ پیڈ سے صاف ہو کر نکلتی ہے، جبکہ کششِ ثقل سے پانی نیچے اور تیل ویئر پلیٹ کے اوپر سے الگ ہو جاتا ہے۔"
            ),
            construction = BiText(
                "Cylindrical carbon-steel drum with hemispherical heads on steel saddles, housing inlet diverter, coalescing plates, weir dam, vortex breakers, mist extractor, level/pressure transmitters, and PSV.",
                "اسٹیل کا بڑا سلنڈر جس کے اندر ڈائیورٹر، ویئر پلیٹ، مسٹ ایکسٹریکٹر، لیول کنٹرولرز اور سیفٹی والو نصب ہوتے ہیں۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("sep_shell", BiText("Pressure Vessel Shell", "پریشر ویسل شیل"), "SA-516 Gr.70", BiText("Provides 1–5 min liquid retention time under pressure.", "الگ ہونے کے لیے ضروری وقت اور دباؤ فراہم کرتا ہے۔")),
                EquipmentComponent("sep_inlet_diverter", BiText("Inlet Diverter", "انلیٹ ڈائیورٹر"), "316L Stainless", BiText("Breaks momentum to knock out bulk liquid.", "داخل ہونے والے بہاؤ کا رخ موڑ کر گیس الگ کرتا ہے۔")),
                EquipmentComponent("sep_weir", BiText("Retention Weir Plate", "ویئر پلیٹ"), "Carbon Steel", BiText("Separates heavy water layer from overflowing crude oil.", "پانی اور تیل کی سطح کو الگ الگ رکھتی ہے۔")),
                EquipmentComponent("sep_mist_extractor", BiText("Mist Extractor Pad", "مسٹ ایکسٹریکٹر"), "316 SS Wire Mesh", BiText("Coalesces fine mist droplets (>10 microns) from gas.", "گیس سے باریک تیل کے قطرے پکڑتی ہے۔")),
                EquipmentComponent("sep_nozzles", BiText("Process Nozzles & PSV", "پروسیس نوزلز"), "ASME Flanges", BiText("Routes gas, oil, water, and emergency overpressure relief.", "گیس، تیل، پانی اور ایمرجنسی پریشر کے راستے۔")),
                EquipmentComponent("sep_saddles", BiText("Support Saddles", "سپورٹ سیڈلز"), "Structural Steel", BiText("Supports full hydrotest vessel weight.", "ویسل کا مکمل وزن سہارا دیتی ہیں۔"))
            ),
            typesAndClassification = listOf(
                BiText("Horizontal 3-Phase Separator (Best for high GOR & oil-water emulsions)", "افقی تھری فیز سیپریٹر"),
                BiText("Vertical Separator (Small footprint, handles sand/solids)", "عمودی سیپریٹر"),
                BiText("Spherical Test Separator", "گول ٹیسٹ سیپریٹر")
            ),
            applications = listOf(
                BiText("Central Processing Facilities (CPF) & GOSP trains", "آئل فیلڈ پروسیسنگ پلانٹس (CPF)"),
                BiText("Offshore production platforms", "آف شور پروڈکشن پلیٹ فارمز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Design Standard", "ڈیزائن معیار"), "ASME Sec VIII Div 1 / API 12J"),
                EquipmentSpec(BiText("Operating Pressure", "آپریٹنگ پریشر"), "50 – 1,440 psi"),
                EquipmentSpec(BiText("Retention Time", "رٹینشن ٹائم"), "2 – 5 minutes")
            ),
            maintenanceConcepts = listOf(
                BiText("Jet-wash bottom sand accumulation during scheduled turnarounds and inspect sacrificial zinc anodes.", "صفائی کے دوران تہہ میں جمی ریت نکالیں اور اینوڈز چیک کریں۔")
            ),
            commonProblems = listOf(
                BiText("Foaming crude causing high-level trip and liquid carryover.", "جھاگ بننے سے گیس لائن میں تیل چلے جانا (Carryover)۔"),
                BiText("Interface level sensor fouling in heavy emulsion.", "ایملشن کی وجہ سے لیول سینسر کا غلط ریڈنگ دینا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Confined space entry requires LOTO, gas testing (O2, LEL, H2S), and standby attendant.", "ویسل کے اندر جانے سے پہلے LOTO اور گیس ٹیسٹ لازمی ہے۔")
            ),
            relatedIds = listOf("eq_christmas_tree", "eq_centrifugal_pump", "eq_control_valve"),
            references = listOf("API Spec 12J Oil & Gas Separators", "Arnold & Stewart: Surface Production Operations Vol 1")
        ),

        // 8. Artificial Lift Systems
        EquipmentItem(
            id = "eq_esp_lift",
            categoryId = "cat_artificial_lift",
            name = BiText("Electric Submersible Pump (ESP)", "الیکٹرک سب مرسیبل پمپ (ESP)"),
            shortTagline = BiText("High-rate multistage downhole centrifugal artificial lift", "کنویں کی گہرائی سے تیل نکالنے والا ملٹی اسٹیج پمپ"),
            has3dModel = false,
            diagramType = DiagramType.CENTRIFUGAL_PUMP_FLOW,
            definition = BiText(
                "A slender downhole multistage centrifugal pumping system driven by a submerged three-phase induction motor attached to the bottom of the production tubing.",
                "کنویں کے اندر پروڈکشن ٹیوبنگ کے نچلے سرے پر نصب ملٹی اسٹیج سینٹری فیوگل پمپ جو سب مرجڈ تھری فیز موٹر سے چلتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Lifts high volumes of crude oil and water (500 to 60,000+ BPD) from depleted or low-pressure reservoirs that can no longer flow naturally.",
                "کم دباؤ والے کنووں سے روزانہ ہزاروں بیرل تیل اوپر لانے کے لیے استعمال ہوتا ہے۔"
            ),
            workingPrinciple = BiText(
                "A surface Variable Speed Drive (VSD) feeds high-voltage AC via armored downhole cable to the submerged motor; dozens of stacked impellers and diffusers add pressure stage-by-stage.",
                "سطح زمین سے VSD کے ذریعے کیبل سے نیچے موٹر کو بجلی دی جاتی ہے اور سینکڑوں امپیلر مرحلہ وار تیل کو اوپر دھکیلتے ہیں۔"
            ),
            construction = BiText(
                "Bottom-to-top assembly: Downhole Sensor, Induction Motor, Seal/Protector (equalizes oil pressure & takes thrust), Gas Separator/Intake, Multistage Pump, and Armored Power Cable.",
                "نیچے سے اوپر: سینسر، سب مرسیبل موٹر، سیل پروٹیکٹر، گیس سیپریٹر، ملٹی اسٹیج پمپ اور آرمرڈ پاور کیبل۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("esp_motor", BiText("Submersible Motor", "سب مرسیبل موٹر"), "Oil-Filled 3-Phase", BiText("High-torque slim motor cooled by flowing well fluid.", "تیل کے بہاؤ سے ٹھنڈی ہونے والی طاقتور موٹر۔")),
                EquipmentComponent("esp_protector", BiText("Seal / Protector Section", "سیل پروٹیکٹر"), "Elastomer Bag / Labyrinth", BiText("Blocks wellbore fluid from entering motor oil.", "کنویں کے پانی کو موٹر میں داخل ہونے سے روکتا ہے۔")),
                EquipmentComponent("esp_stages", BiText("Impeller-Diffuser Stages", "امپیلر اور ڈفیوزر اسٹیجز"), "Ni-Resist Cast Iron", BiText("Up to 300+ stages generating thousands of feet of head.", "ہر اسٹیج دباؤ میں اضافہ کرتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Conventional Tubing-Deployed ESP", "روایتی ٹیوبنگ ESP"),
                BiText("Permanent Magnet Motor (PMM) High-Efficiency ESP", "پرماننٹ میگنیٹ موٹر ESP")
            ),
            applications = listOf(
                BiText("High water-cut mature oilfields", "پرانے آئل فیلڈز جہاں پانی کی مقدار زیادہ ہو"),
                BiText("Offshore & deviated high-rate wells", "زیادہ پیداوار والے آف شور کنویں")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Flow Capacity", "پیداواری صلاحیت"), "200 – 60,000 BPD"),
                EquipmentSpec(BiText("Motor Voltage", "موٹر وولٹیج"), "400 – 5,000 V"),
                EquipmentSpec(BiText("Max Operating Temp", "زیادہ سے زیادہ درجہ حرارت"), "Up to 200°C (High-temp)")
            ),
            maintenanceConcepts = listOf(
                BiText("Monitor motor amps, intake pressure, and motor winding temperature continuously via surface VSD.", "VSD پر موٹر کرنٹ اور درجہ حرارت کی مسلسل نگرانی کریں۔")
            ),
            commonProblems = listOf(
                BiText("Gas lock when free gas exceeds 15–20% at pump intake.", "زیادہ گیس داخل ہونے سے پمپ کا گیس لاک ہو جانا۔"),
                BiText("Scale/asphaltene plugging or sand abrasion.", "اسکیل یا ریت سے اسٹیجز کا جام ہونا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Lock out medium-voltage VSD transformer and discharge capacitors before testing cable.", "کیبل ٹیسٹنگ سے پہلے ہائی وولٹیج VSD کو لاک آؤٹ اور ڈسچارج کریں۔")
            ),
            relatedIds = listOf("eq_centrifugal_pump", "eq_vfd_motor", "eq_separator_3phase"),
            references = listOf("API RP 11S Recommended Practice for ESP Installations")
        )
    )
}
