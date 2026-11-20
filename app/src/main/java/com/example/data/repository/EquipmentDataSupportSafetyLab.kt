package com.example.data.repository

import com.example.model.BiText
import com.example.model.DiagramType
import com.example.model.EquipmentComponent
import com.example.model.EquipmentItem
import com.example.model.EquipmentSpec
import com.example.model.Model3DType

object EquipmentDataSupportSafetyLab {
    val items: List<EquipmentItem> = listOf(
        // 15. Instrumentation and Control
        EquipmentItem(
            id = "eq_control_valve",
            categoryId = "cat_instrumentation",
            name = BiText("Pneumatic Globe Control Valve", "نیومیٹک کنٹرول والو"),
            shortTagline = BiText("Automated 4–20 mA / HART flow & pressure regulator", "آٹومیٹک کنٹرول والو جو پریشر اور بہاؤ کو کنٹرول کرتا ہے"),
            has3dModel = false,
            diagramType = DiagramType.INSTRUMENTATION_PID,
            definition = BiText(
                "A final control element that modulates fluid flow rate, pressure, level, or temperature continuously in response to a 4–20 mA / HART signal from the plant DCS/PLC.",
                "آٹومیشن سسٹم (DCS/PLC) سے آنے والے سگنل کے مطابق پائپ لائن میں بہاؤ، پریشر یا لیول کو خودکار طریقے سے کم یا زیادہ کرنے والا والو۔"
            ),
            purposeAndFunction = BiText(
                "Maintains process variables strictly at setpoint inside separators, distillation columns, and compressor surge loops.",
                "سیپریٹر اور ریفائنری یونٹس میں پریشر اور لیول کو مقررہ حد پر برقرار رکھنا۔"
            ),
            workingPrinciple = BiText(
                "A smart digital valve positioner converts a 4–20 mA signal into pneumatic instrument air pressure onto a spring-diaphragm actuator, lifting or lowering a contoured plug inside the valve seat.",
                "ڈیجیٹل پوزیشنر برقی سگنل کو ہوا کے دباؤ میں بدل کر ڈایافرام کو حرکت دیتا ہے، جس سے اندرونی پلگ اوپر نیچے ہو کر بہاؤ کنٹرول کرتا ہے۔"
            ),
            construction = BiText(
                "Globe valve body, characterized cage/plug trim, stainless steel stem, PTFE/graphite packing, smart HART positioner, and fail-safe spring-diaphragm actuator.",
                "گلوب والو باڈی، پلگ ٹرم، پیکنگ، اسمارٹ ہارٹ پوزیشنر اور اسپرنگ ڈایافرام ایکچویٹر۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("cv_actuator", BiText("Spring-Diaphragm Actuator", "ڈایافرام ایکچویٹر"), "Cast Aluminum / Steel", BiText("Provides Fail-Open or Fail-Close safety action on air loss.", "ہوا بند ہونے پر والو کو محفوظ حالت (Fail-Safe) میں لاتا ہے۔")),
                EquipmentComponent("cv_positioner", BiText("Smart HART Positioner", "اسمارٹ پوزیشنر"), "Microprocessor I/P", BiText("Ensures exact stem travel vs controller setpoint.", "والو کی پوزیشن کو سگنل کے عین مطابق رکھتا ہے۔")),
                EquipmentComponent("cv_trim", BiText("Contoured Plug & Cage Trim", "پلگ اور کیج ٹرم"), "Hardened 440C / Stellite", BiText("Controls flow characteristic (Equal % or Linear).", "بہاؤ کی رفتار کا تناسب طے کرتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Globe Control Valve (Precision throttling)", "گلوب کنٹرول والو"),
                BiText("Segmented Ball / V-Notch & Butterfly Control Valve", "وی-ناچ بال اور بٹر فلائی کنٹرول والو")
            ),
            applications = listOf(
                BiText("Separator level/pressure control & compressor anti-surge", "سیپریٹر لیول کنٹرول اور اینٹی سرج سسٹم")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Control Signal", "سگنل"), "4–20 mA DC + HART 7"),
                EquipmentSpec(BiText("Instrument Air", "ایئر سپلائی"), "20 – 60 psig dry clean air"),
                EquipmentSpec(BiText("Shutoff Class", "سیلنگ کلاس"), "ANSI/FCI 70-2 Class IV / VI")
            ),
            maintenanceConcepts = listOf(
                BiText("Run partial-stroke valve signature diagnostics via HART communicator before turnarounds.", "ہارٹ کمیونیکیٹر سے والو کی اسٹرکچرل ہیلتھ چیک کریں۔")
            ),
            commonProblems = listOf(
                BiText("Cavitation/flashing noise eroding valve plug under high pressure drop.", "کیویٹیشن سے پلگ اور سیٹ کا کٹ جانا۔"),
                BiText("Sticky stem packing causing control loop hunting/oscillation.", "پیکنگ سخت ہونے سے والو کا جھٹکوں سے چلنا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Confirm Fail-Safe action (FO vs FC) and isolate bypass valve carefully before placing manual.", "مینوئل بائی پاس کھولنے سے پہلے والو کی Fail-Safe حالت ضرور چیک کریں۔")
            ),
            relatedIds = listOf("eq_pressure_gauge", "eq_gate_valve", "eq_separator_3phase"),
            references = listOf("ISA-75 Control Valve Standards", "Fisher Control Valve Handbook")
        ),

        EquipmentItem(
            id = "eq_pressure_gauge",
            categoryId = "cat_instrumentation",
            name = BiText("Bourdon Tube Pressure Gauge (Small Component)", "بورڈن ٹیوب پریشر گیج"),
            shortTagline = BiText("Local mechanical pressure dial indicator", "پائپ لائن پر دباؤ ناپنے والا مقامی ڈائل گیج"),
            has3dModel = false,
            diagramType = DiagramType.INSTRUMENTATION_PID,
            isSmallComponent = true,
            definition = BiText(
                "A rugged mechanical pressure-measuring instrument using a C-shaped curved Bourdon tube that uncoils proportionally under internal fluid pressure.",
                "مضبوط مکینیکل آلہ جو اندر موجود مڑی ہوئی بورڈن ٹیوب کے ذریعے پائپ لائن کا دباؤ ڈائل پر دکھاتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Provides instantaneous, power-free local pressure verification on wellheads, pump discharges, and vessels.",
                "بغیر بجلی کے پمپ، سیپریٹر اور ویل ہیڈ پر فوری پریشر کی تصدیق فراہم کرنا۔"
            ),
            workingPrinciple = BiText(
                "Pressurized fluid enters the oval-cross-section C-tube; internal pressure tries to make the cross-section circular, straightening the curve and rotating the pointer via a gear sector.",
                "جب دباؤ ٹیوب میں داخل ہوتا ہے تو مڑی ہوئی ٹیوب سیدھی ہونے کی کوشش کرتی ہے جس سے گیئر کے ذریعے سوئی گھوم کر ریڈنگ دکھاتی ہے۔"
            ),
            construction = BiText(
                "316 SS wetted Bourdon tube and socket, glycerin-filled stainless case (for vibration damping), blow-out back safety disc, and laminated safety glass.",
                "اسٹین لیس اسٹیل باڈی، وائبریشن روکنے کے لیے گلیسرین سے بھرا کیس، اور سیفٹی گلاس۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("pg_tube", BiText("C-Type Bourdon Tube", "بورڈن ٹیوب"), "316L SS / Monel", BiText("Elastic sensing element deflecting under pressure.", "پریشر کے مطابق پھیلنے والی بنیادی ٹیوب۔")),
                EquipmentComponent("pg_movement", BiText("Sector Gear & Hair Spring", "سیکٹر گیئر"), "Stainless Steel", BiText("Amplifies micro-deflection into 270° dial sweep.", "باریک حرکت کو سوئی کے پورے چکر میں بدلتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Dry Bourdon Gauge", "خشک پریشر گیج"),
                BiText("Glycerin-Filled Vibration-Proof Gauge", "گلیسرین بھرا گیج (پمپوں کے لیے)")
            ),
            applications = listOf(
                BiText("Pump suction/discharge, separators, wellheads & compressors", "پمپس، سیپریٹرز اور ویل ہیڈز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Accuracy Class", "درستگی"), "ASME B40.100 Grade 2A (±0.5%)"),
                EquipmentSpec(BiText("Ranges", "پریشر رینج"), "0–15 to 0–30,000 psi")
            ),
            maintenanceConcepts = listOf(
                BiText("Select range so normal operating pressure sits at 40–70% of full scale.", "گیج کی رینج ایسی منتخب کریں کہ عام دباؤ درمیانے حصے میں رہے۔")
            ),
            commonProblems = listOf(
                BiText("Zero shift or broken gear teeth from water-hammer pulsation.", "پریشر کے جھٹکوں سے سوئی کا صفر سے ہٹ جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Always install a block-and-bleed needle valve below the gauge for safe replacement.", "گیج تبدیل کرنے کے لیے نیچے ہمیشہ آئسولیشن اور بلیڈ والو لگائیں۔")
            ),
            relatedIds = listOf("eq_control_valve", "eq_christmas_tree"),
            references = listOf("ASME B40.100 Pressure Gauges")
        ),

        // 16. Electrical Equipment
        EquipmentItem(
            id = "eq_vfd_motor",
            categoryId = "cat_electrical",
            name = BiText("Explosion-Proof (Ex-d) Motor & VFD", "ایکسپلوشن پروف (Ex-d) موٹر اور VFD"),
            shortTagline = BiText("Hazardous-area Zone 1 flameproof industrial prime mover", "خطرناک گیس والے علاقوں میں محفوظ طریقے سے چلنے والی الیکٹرک موٹر"),
            has3dModel = false,
            diagramType = DiagramType.ELECTRICAL_EXPLOSION_PROOF,
            definition = BiText(
                "A heavy-duty three-phase squirrel-cage induction motor certified for explosive gas atmospheres (Class I Div 1 / Zone 1 Ex-d), paired with a Variable Frequency Drive (VFD).",
                "خاص فلیم پروف (Flameproof) تھری فیز موٹر جو آتش گیر گیس کے ماحول میں اندرونی چنگاری کو باہر نکلنے سے روکتی ہے اور VFD سے رفتار کنٹرول کرتی ہے۔"
            ),
            purposeAndFunction = BiText(
                "Drives centrifugal pumps, fin-fan coolers, and rig drawworks safely in hydrocarbon vapor zones while saving energy via speed modulation.",
                "تیل و گیس پلانٹس میں پمپ اور پنکھے محفوظ طریقے سے چلانا اور بجلی کی بچت کرنا۔"
            ),
            workingPrinciple = BiText(
                "VFD rectifies AC to DC and synthesizes variable-frequency PWM AC to control motor RPM (N = 120f/P). Ex-d flamepaths cool any internal spark gas below auto-ignition temperature before exiting the housing.",
                "VFD فریکوئنسی بدل کر موٹر کی رفتار کنٹرول کرتا ہے جبکہ Ex-d باڈی کے باریک جوڑ کسی بھی اندرونی چنگاری کو ٹھنڈا کر کے باہر آگ لگنے سے روکتے ہیں۔"
            ),
            construction = BiText(
                "Ribbed cast-iron flameproof enclosure, precision machined flamepath flanges, certified Ex-e/Ex-d terminal box, Class F/H stator windings with RTDs, and insulated bearings.",
                "مضبوط کاسٹ آئرن باڈی، فلیم پاتھ جوڑ، Ex-d ٹرمینل باکس اور درجہ حرارت ناپنے والے RTD سینسرز۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("em_enclosure", BiText("Flameproof Cast Enclosure", "فلیم پروف باڈی"), "Cast Iron GG25", BiText("Withstands internal explosion without propagating flame outside.", "اندرونی دھماکے کو باہر پھیلنے سے روکتی ہے۔")),
                EquipmentComponent("em_winding", BiText("Stator Winding + PT100 RTD", "اسٹیٹر وائنڈنگ اور سینسر"), "Copper Class H", BiText("Monitors winding temperature continuously.", "کوائل کے درجہ حرارت کی نگرانی کرتی ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Ex-d Flameproof (Zone 1)", "Ex-d فلیم پروف موٹر"),
                BiText("Ex-e Increased Safety / Ex-p Pressurized (Zone 1/2)", "Ex-e اور Ex-p موٹرز")
            ),
            applications = listOf(
                BiText("Refinery process pumps, tank mixers & gas compression skids", "ریفائنری پمپس اور گیس کمپریسر اسکیڈز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("ATEX / IECEx Rating", "سیفٹی ریٹنگ"), "II 2G Ex db IIB/IIC T4 Gb"),
                EquipmentSpec(BiText("Ingress Protection", "آئی پی ریٹنگ"), "IP55 / IP66")
            ),
            maintenanceConcepts = listOf(
                BiText("Never paint or scratch machined flamepath gaps; check shaft grounding brushes on VFD motors.", "فلیم پاتھ جوڑوں پر پینٹ یا خراش نہ لگنے دیں اور وائنڈنگ میگر ٹیسٹ کریں۔")
            ),
            commonProblems = listOf(
                BiText("Bearing fluting caused by VFD high-frequency shaft currents.", "VFD کرنٹ کی وجہ سے بیرنگز کا خراب ہونا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Never open Ex-d terminal box in a live hazardous zone without Hot Work Permit & gas test.", "گیس زون میں ہاٹ ورک پرمٹ اور گیس ٹیسٹ کے بغیر ٹرمینل باکس نہ کھولیں۔")
            ),
            relatedIds = listOf("eq_centrifugal_pump", "eq_esp_lift"),
            references = listOf("IEC 60079 Explosive Atmospheres", "IEEE 841 Petroleum & Chemical Duty Motors")
        ),

        // 17. Mechanical Equipment
        EquipmentItem(
            id = "eq_centrifugal_pump",
            categoryId = "cat_mechanical",
            name = BiText("API 610 Centrifugal Process Pump", "API 610 سینٹری فیوگل پروسیس پمپ"),
            shortTagline = BiText("Heavy-duty refinery & pipeline fluid transfer workhorse", "ریفائنری اور پائپ لائنز میں مائع منتقل کرنے والا سب سے عام پمپ"),
            has3dModel = true,
            model3dType = Model3DType.CENTRIFUGAL_PUMP,
            diagramType = DiagramType.CENTRIFUGAL_PUMP_FLOW,
            isPopular = true,
            definition = BiText(
                "A rotodynamic pump that converts rotational mechanical energy from a driver into hydrodynamic fluid velocity and static pressure via a rotating impeller inside a volute casing.",
                "روٹری مشین جو گھومتے ہوئے امپیلر کے ذریعے سینٹری فیوگل فورس پیدا کر کے تیل یا پانی کو تیز رفتاری اور دباؤ کے ساتھ آگے بھیجتی ہے۔"
            ),
            purposeAndFunction = BiText(
                "Transfers crude oil, naphtha, cooling water, boiler feedwater, and refined fuels continuously across refinery units and shipping pipelines.",
                "ریفائنری یونٹس اور ٹرمینلز میں خام تیل اور دیگر مصنوعات کو مسلسل پمپ کرنا۔"
            ),
            workingPrinciple = BiText(
                "Fluid enters the eye of the spinning impeller axially; centrifugal force flings fluid radially outward into the expanding spiral volute casing, converting velocity head into high discharge pressure.",
                "مائع امپیلر کے مرکز میں داخل ہوتا ہے، گھومتے وینز اسے باہر پھینکتے ہیں جہاں وولیوٹ کیسنگ رفتار کو دباؤ میں بدل دیتی ہے۔"
            ),
            construction = BiText(
                "Centerline-supported heavy steel volute casing, dynamically balanced closed impeller, 4140 shaft, API 682 cartridge mechanical seal, oil-bath bearing housing, and steel baseplate.",
                "مضبوط وولیوٹ کیسنگ، بیلنسڈ امپیلر، شافٹ، API 682 مکینیکل سیل، بیرنگ ہاؤسنگ اور بیس پلیٹ۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("cp_volute", BiText("Volute Casing", "وولیوٹ کیسنگ"), "ASTM A216 WCB", BiText("Diffusion chamber converting velocity into pressure.", "رفتار کو پریشر میں بدلنے والا خول۔")),
                EquipmentComponent("cp_impeller", BiText("Radial Impeller", "ریڈیئل امپیلر"), "CD4MCu Duplex SS", BiText("Transfers rotational energy to fluid.", "مائع کو حرکت اور دباؤ دینے والا روٹر۔")),
                EquipmentComponent("cp_suction", BiText("Suction Nozzle", "سکشن نوزل"), "ASME Flange", BiText("Feeds fluid smoothly to impeller eye.", "کم دباؤ مائع اندر لاتا ہے۔")),
                EquipmentComponent("cp_discharge", BiText("Discharge Nozzle", "ڈسچارج نوزل"), "ASME Flange", BiText("Delivers pressurized flow to piping.", "ہائی پریشر مائع باہر بھیجتا ہے۔")),
                EquipmentComponent("cp_seal", BiText("Mechanical Seal", "مکینیکل سیل"), "SiC / Carbon", BiText("Prevents flammable hydrocarbon leakage.", "شافٹ کے گرد لیکج روکتی ہے۔")),
                EquipmentComponent("cp_shaft", BiText("Shaft & Bearings", "شافٹ اور بیرنگز"), "4140 Alloy Steel", BiText("Transmits torque and supports axial thrust.", "طاقت منتقل کرتا اور لوڈ اٹھاتا ہے۔")),
                EquipmentComponent("cp_baseplate", BiText("Skid Baseplate", "بیس پلیٹ"), "Structural Steel", BiText("Maintains motor-pump alignment.", "پمپ اور موٹر کو سیدھ میں رکھتی ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("OH2 Overhung Centerline-Mounted (Hot refinery services)", "OH2 اوور ہنگ پمپ"),
                BiText("BB1 / BB2 Between-Bearings (Pipeline & high-head)", "BB1/BB2 ہائی پریشر پمپ"),
                BiText("VS1 / VS4 Vertical Sump & Barrel Pumps", "VS عمودی پمپس")
            ),
            applications = listOf(
                BiText("Crude charge, reflux, product transfer & pipeline boosting", "کروڈ چارج، ریفلکس اور پائپ لائن بوسٹنگ")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Design Standard", "معیار"), "API Std 610 (ISO 13709)"),
                EquipmentSpec(BiText("Flow Range", "بہاؤ کی حد"), "10 – 50,000+ GPM"),
                EquipmentSpec(BiText("Seal Standard", "سیل معیار"), "API 682 Plan 11 / 52 / 53A")
            ),
            maintenanceConcepts = listOf(
                BiText("Laser-align coupling within 0.05 mm and monitor bearing housing vibration (< 3.0 mm/s RMS).", "لیزر سے الائنمنٹ درست رکھیں اور وائبریشن 3.0 mm/s سے کم رکھیں۔")
            ),
            commonProblems = listOf(
                BiText("Cavitation (rattling gravel sound) when NPSHa < NPSHr.", "کیویٹیشن (پتھر ٹکرانے جیسی آواز) جب سکشن پریشر کم ہو۔"),
                BiText("Mechanical seal face dry-running or thermal cracking.", "مکینیکل سیل کا خشک چل کر خراب ہونا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Never run dead-headed (closed discharge valve) for more than seconds—fluid flashes and casing can explode.", "ڈسچارج والو مکمل بند رکھ کر پمپ نہ چلائیں، اندر مائع ابل کر دھماکہ کر سکتا ہے۔")
            ),
            relatedIds = listOf("eq_mechanical_seal", "eq_gate_valve", "eq_vfd_motor"),
            references = listOf("API Standard 610 Centrifugal Pumps", "Karassik: Pump Handbook")
        ),

        EquipmentItem(
            id = "eq_gate_valve",
            categoryId = "cat_mechanical",
            name = BiText("OS&Y Flanged Gate Valve", "او ایس اینڈ وائی گیٹ والو"),
            shortTagline = BiText("Full-bore positive pipeline isolation valve", "پائپ لائن میں بہاؤ کو مکمل کھولنے یا بند کرنے والا والو"),
            has3dModel = true,
            model3dType = Model3DType.GATE_VALVE,
            diagramType = DiagramType.VALVE_CROSS_SECTION,
            isPopular = true,
            definition = BiText(
                "A linear-motion isolation valve using a flat or wedge-shaped metal gate sliding perpendicular to the flow path to provide unobstructed full-bore flow or tight shutoff.",
                "ایسا آئسولیشن والو جس میں ایک مضبوط دھاتی گیٹ بہاؤ کے راستے کے آر پار اوپر نیچے حرکت کر کے پائپ کو مکمل کھولتا یا بند کرتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Provides zero-restriction full-bore isolation (minimal pressure drop, allows pipeline pigs) for maintenance, tank manifolds, and pump suction/discharge blocks.",
                "مرمت، پمپ آئسولیشن اور پائپ لائن پگنگ کے دوران کم سے کم پریشر لاس کے ساتھ مکمل بندش فراہم کرنا۔"
            ),
            workingPrinciple = BiText(
                "Turning the handwheel rotates a yoke sleeve, raising or lowering the threaded OS&Y stem and attached wedge gate between two angled hard-faced seat rings.",
                "ہینڈ وہیل گھمانے سے چوڑی دار اسٹیم اوپر اٹھتا یا نیچے جاتا ہے اور گیٹ کو دو سیٹ رنگز کے درمیان بند یا کھلا کر دیتا ہے۔"
            ),
            construction = BiText(
                "Bolted-bonnet API 600 carbon/stainless steel body, flexible or solid wedge gate, Stellite-welded seats, rising stem, braided graphite packing, and handwheel.",
                "API 600 فلینجڈ باڈی، ویج گیٹ، اسٹیلٹ سیٹس، رائزنگ اسٹیم اور گریفائٹ پیکنگ۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("gv_body", BiText("Flanged Valve Body", "والو باڈی"), "ASTM A216 WCB", BiText("Bolts inline between ASME raised-face flanges.", "پائپ لائن کے فلینجز کے درمیان فٹ ہوتا ہے۔")),
                EquipmentComponent("gv_gate", BiText("Wedge Gate & Seats", "گیٹ اور سیٹ رنگز"), "13Cr / Stellite 6", BiText("Creates metal-to-metal line shutoff.", "مکمل لیک پروف بندش فراہم کرتے ہیں۔")),
                EquipmentComponent("gv_bonnet", BiText("Bolted Bonnet & Packing", "بونٹ اور پیکنگ"), "Steel + Graphite", BiText("Seals stem against atmospheric fugitive leaks.", "اسٹیم کے گرد گیس یا تیل نکلنے سے روکتا ہے۔")),
                EquipmentComponent("gv_stem_wheel", BiText("Rising Stem & Handwheel", "رائزنگ اسٹیم اور وہیل"), "A182 F6a", BiText("Visual open/close position indicator.", "باہر سے والو کھلا یا بند ہونے کی نشانی دکھاتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Rising Stem OS&Y (Process piping standard)", "رائزنگ اسٹیم OS&Y"),
                BiText("Through-Conduit Slab Gate (Piggable trunklines)", "تھرو کنڈوئٹ سلیب گیٹ (API 6D)")
            ),
            applications = listOf(
                BiText("Pump isolation, tank farms, refinery manifolds & wellheads", "پمپ آئسولیشن، ٹینک فارمز اور مینی فولڈز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Design Standard", "معیار"), "API 600 / API 6D"),
                EquipmentSpec(BiText("Pressure Classes", "کلاسز"), "ASME Class 150 – 2500")
            ),
            maintenanceConcepts = listOf(
                BiText("Never use a gate valve for flow throttling—high-velocity fluid erodes the bottom edge of the gate (wire drawing).", "گیٹ والو کو کبھی بہاؤ کم زیادہ کرنے (Throttling) کے لیے آدھا کھلا نہ چھوڑیں ورنہ گیٹ کٹ جاتا ہے۔")
            ),
            commonProblems = listOf(
                BiText("Seat passing due to scale trapped under wedge.", "کچرا پھنسنے سے والو کا مکمل بند نہ ہونا۔"),
                BiText("Thermal binding when closed hot and cooled down.", "گرم حالت میں بند کرنے کے بعد ٹھنڈا ہونے پر جام ہو جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Verify double-block-and-bleed isolation before breaking any downstream flange.", "آگے کا پائپ کھولنے سے پہلے ڈبل بلاک اینڈ بلیڈ کنفرم کریں۔")
            ),
            relatedIds = listOf("eq_control_valve", "eq_christmas_tree", "eq_centrifugal_pump"),
            references = listOf("API Standard 600 Steel Gate Valves", "API 6D Pipeline Valves")
        ),

        EquipmentItem(
            id = "eq_compressor",
            categoryId = "cat_mechanical",
            name = BiText("Reciprocating Gas Compressor", "ریسیپروکیٹنگ گیس کمپریسر"),
            shortTagline = BiText("High-pressure natural gas & refinery hydrogen booster", "قدرتی گیس اور ہائیڈروجن کا دباؤ بڑھانے والی طاقتور مشین"),
            has3dModel = true,
            model3dType = Model3DType.RECIPROCATING_COMPRESSOR,
            diagramType = DiagramType.COMPRESSOR_CYCLE,
            definition = BiText(
                "A positive displacement gas compressor using piston(s) reciprocating inside water-cooled cylinders to elevate gas pressure across high compression ratios.",
                "پازیٹو ڈسپلیسمنٹ مشین جو سلنڈر کے اندر پسٹن کی آگے پیچھے حرکت سے گیس کو دبا کر انتہائی ہائی پریشر پر پہنچاتی ہے۔"
            ),
            purposeAndFunction = BiText(
                "Boosts natural gas for pipeline transmission, gas-lift injection, CNG stations, and refinery hydrotreater hydrogen recycle.",
                "پائپ لائن ٹرانسمیشن، گیس لفٹ اور ریفائنری ہائیڈروٹریٹرز کے لیے گیس کا دباؤ بڑھانا۔"
            ),
            workingPrinciple = BiText(
                "On the backward stroke, cylinder pressure drops below suction line pressure, opening spring-loaded suction plate valves. On forward stroke, gas compresses adiabatically until discharge valves open into acoustic pulsation bottles.",
                "پسٹن پیچھے ہٹنے پر سکشن والو سے گیس اندر آتی ہے اور آگے بڑھنے پر دباؤ بڑھ کر ڈسچارج والو سے باہر نکلتی ہے۔"
            ),
            construction = BiText(
                "Heavy crankcase with crankshaft/crossheads, distance piece (isolates lube oil from gas), finned/jacketed cylinders, PTFE piston rings, plate valves, and pulsation bottles.",
                "کرینک کیس، ڈسٹنس پیس، گیس سلنڈر، PTFE رنگز والا پسٹن، پلیٹ والوز اور پلسیشن بوٹلز۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("comp_frame", BiText("Crankcase & Distance Piece", "کرینک کیس اور ڈسٹنس پیس"), "Ductile Cast Iron", BiText("Isolates flammable process gas from lubricated crankcase.", "گیس کو کرینک کیس کے آئل سے الگ رکھتا ہے۔")),
                EquipmentComponent("comp_cylinder", BiText("Compression Cylinder", "کمپریشن سلنڈر"), "Forged Steel", BiText("Double-acting chamber compressing gas on both strokes.", "دونوں اطراف سے گیس کمپریس کرتا ہے۔")),
                EquipmentComponent("comp_piston", BiText("Piston & PTFE Rings", "پسٹن اور رنگز"), "Al / Cast Iron + PTFE", BiText("Seals high-pressure differential across piston.", "پسٹن کے گرد گیس لیکج روکتے ہیں۔")),
                EquipmentComponent("comp_valves", BiText("Pulsation Bottles", "پلسیشن بوٹلز"), "ASME Steel", BiText("Dampens acoustic gas pressure waves.", "گیس کے دباؤ کی لہروں کو کم کرتی ہیں۔")),
                EquipmentComponent("comp_flywheel", BiText("Inertia Flywheel", "فلائی وہیل"), "Cast Iron", BiText("Smooths rotational torque.", "گردش کو ہموار رکھتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Balanced-Opposed Multi-Throw Compressor (API 618)", "بیلنسڈ اپوزڈ API 618 کمپریسر"),
                BiText("High-Speed Separable Gas Engine Compressor (API 11P)", "ہائی اسپیڈ فیلڈ کمپریسر")
            ),
            applications = listOf(
                BiText("Gas gathering, reinjection, gas lift & hydrocracking", "گیس گیدرنگ، ری انجیکشن اور ہائیڈرو کریکنگ")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Design Standard", "معیار"), "API Standard 618"),
                EquipmentSpec(BiText("Discharge Pressure", "ڈسچارج پریشر"), "Up to 6,000+ psig"),
                EquipmentSpec(BiText("Max Discharge Temp", "زیادہ سے زیادہ حرارت"), "< 150 °C per stage")
            ),
            maintenanceConcepts = listOf(
                BiText("Monitor valve cover temperatures via IR—a hot suction valve cap signals a leaking plate valve.", "انفراریڈ سے والو کور کا درجہ حرارت چیک کریں؛ زیادہ گرم کور والو لیکج کی نشانی ہے۔")
            ),
            commonProblems = listOf(
                BiText("Liquid slugging entering cylinder (can bend connecting rods).", "سلنڈر میں مائع آ جانے سے شدید مکینیکل نقصان۔"),
                BiText("Broken valve springs/plates reducing capacity.", "والو پلیٹ ٹوٹنے سے گیس کا دباؤ گر جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Purge cylinders with Nitrogen before startup and ensure suction scrubber high-level trip works.", "چلانے سے پہلے نائٹروجن پرج کریں اور سکشن اسکرَبر ہائی لیول ٹرپ چیک کریں۔")
            ),
            relatedIds = listOf("eq_separator_3phase", "eq_heat_exchanger", "eq_h2s_detector"),
            references = listOf("API Standard 618 Reciprocating Compressors")
        ),

        EquipmentItem(
            id = "eq_heat_exchanger",
            categoryId = "cat_mechanical",
            name = BiText("Shell & Tube Heat Exchanger", "شیل اینڈ ٹیوب ہیٹ ایکسچینجر"),
            shortTagline = BiText("TEMA thermal energy recovery & cooling unit", "دو سیالوں کے درمیان حرارت کا تبادلہ کرنے والا یونٹ"),
            has3dModel = false,
            diagramType = DiagramType.HEAT_EXCHANGER_SHELL_TUBE,
            definition = BiText(
                "A rugged pressure vessel housing a bundle of parallel tubes inside a cylindrical shell so hot and cold streams exchange thermal energy across metal tube walls without mixing.",
                "مضبوط اسٹیل شیل جس کے اندر سینکڑوں متوازی ٹیوبیں ہوتی ہیں تاکہ گرم اور ٹھنڈے سیال آپس میں ملے بغیر حرارت کا تبادلہ کر سکیں۔"
            ),
            purposeAndFunction = BiText(
                "Preheats cold crude oil using hot refinery product streams (saving furnace fuel) and cools overhead vapors or lube oil.",
                "خام تیل کو گرم کر کے فرنس ایندھن بچانا اور گرم مصنوعات کو ٹھنڈا کرنا۔"
            ),
            workingPrinciple = BiText(
                "One fluid flows inside the tube bundle while the second fluid snakes across the outside of the tubes guided by segmental baffles, maximizing turbulent convective heat transfer (Q = U·A·ΔTlm).",
                "ایک سیال ٹیوبوں کے اندر اور دوسرا ٹیوبوں کے باہر بفلز کی مدد سے گزرتا ہے، جس سے حرارت تیز رفتاری سے منتقل ہوتی ہے۔"
            ),
            construction = BiText(
                "Channel head with pass partition, stationary/floating tubesheets, seamless U-tube or straight tube bundle, segmental baffles, and outer shell.",
                "چینل ہیڈ، ٹیوب شیٹ، ٹیوب بنڈل، سیگمنٹل بفلز اور بیرونی شیل۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("hx_bundle", BiText("Tube Bundle & Tubesheet", "ٹیوب بنڈل اور ٹیوب شیٹ"), "Admiralty Brass / SS / Ti", BiText("Primary heat transfer surface separating fluids.", "حرارت منتقل کرنے والی مرکزی سطح۔")),
                EquipmentComponent("hx_baffles", BiText("Segmental Baffles", "سیگمنٹل بفلز"), "Carbon Steel", BiText("Directs shell-side cross-flow and supports tubes.", "شیل سائیڈ بہاؤ کو ٹیوبوں پر سے گزارتے ہیں۔"))
            ),
            typesAndClassification = listOf(
                BiText("TEMA AES Floating Head (Easy mechanical cleaning)", "TEMA AES فلوٹنگ ہیڈ"),
                BiText("TEMA BEU U-Tube (High thermal expansion tolerance)", "TEMA BEU یو-ٹیوب")
            ),
            applications = listOf(
                BiText("Crude preheat trains, overhead condensers & reboilers", "کروڈ پری ہیٹ ٹرینز، کنڈینسرز اور ری بوائلرز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Design Codes", "معیار"), "ASME Sec VIII + TEMA R / API 660"),
                EquipmentSpec(BiText("Pressure Rating", "پریشر"), "Up to 3,000+ psi")
            ),
            maintenanceConcepts = listOf(
                BiText("Track fouling resistance via pressure drop (ΔP) and approach temperature; hydro-jet tubes during shutdown.", "پریشر ڈراپ بڑھنے پر ہائی پریشر واٹر جیٹ سے ٹیوبوں کی صفائی کریں۔")
            ),
            commonProblems = listOf(
                BiText("Tube-side fouling / scaling reducing heat duty.", "ٹیوبوں میں کچرا یا اسکیل جمنے سے ہیٹ ٹرانسفر کم ہونا۔"),
                BiText("Tube corrosion pinholes causing cross-contamination.", "ٹیوب میں سوراخ ہونے سے دونوں سیالوں کا آپس میں مل جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Thermal expansion hazard: never lock in both sides of a cold exchanger while hot side is flowing.", "دونوں سائیڈز کو بیک وقت بند کر کے گرم نہ کریں، تھرمل پریشر ویسل پھاڑ سکتا ہے۔")
            ),
            relatedIds = listOf("eq_distillation_column", "eq_centrifugal_pump"),
            references = listOf("TEMA Standards 10th Edition", "API Standard 660")
        ),

        // 18. Maintenance and Troubleshooting
        EquipmentItem(
            id = "eq_mechanical_seal",
            categoryId = "cat_maintenance",
            name = BiText("API 682 Cartridge Mechanical Seal (Small Component)", "کارٹریج مکینیکل سیل"),
            shortTagline = BiText("Precision micron-flat rotating shaft seal preventing leaks", "پمپ شافٹ پر لیکج روکنے والا انتہائی باریک اور اہم پرزہ"),
            has3dModel = false,
            diagramType = DiagramType.CENTRIFUGAL_PUMP_FLOW,
            isSmallComponent = true,
            definition = BiText(
                "A pre-assembled cartridge sealing device mounted around a rotating pump shaft where a spring-loaded stationary ring runs against a rotating ring separated by a 1-micron lubricating fluid film.",
                "پمپ شافٹ کے گرد لگنے والی کارٹریج سیل جس میں دو انتہائی ہموار رنگز آپس میں مل کر تیل یا گیس کو باہر نکلنے سے روکتے ہیں۔"
            ),
            purposeAndFunction = BiText(
                "Eliminates hazardous hydrocarbon emissions, fire risks, and continuous drip loss compared to traditional braided gland packing.",
                "آتش گیر ہائیڈرو کاربن کے اخراج اور آگ لگنے کے خطرات کو روکنا۔"
            ),
            workingPrinciple = BiText(
                "Hydraulic product pressure plus wave/coil springs hold Silicon Carbide and Carbon Graphite faces flat within 2 light-bands (~0.6 microns), forming a hydrodynamic vapor/liquid barrier.",
                "اسپرنگ اور سیال کے دباؤ سے سلیکون کاربائیڈ اور کاربن گریفائٹ کے چہرے بالکل جڑے رہتے ہیں اور مائیکرون فلم سے لیکج رکتی ہے۔"
            ),
            construction = BiText(
                "316SS shaft sleeve, gland plate with flush/quench/drain ports, rotating & stationary seal faces, elastomer O-rings (Viton/Kalrez), and setting clips.",
                "اسٹین لیس اسٹیل سلیو، گلینڈ پلیٹ، روٹیٹنگ اور فکسڈ سیل فیسز، اور او-رنگز۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("ms_faces", BiText("Primary Seal Faces", "پرائمری سیل فیسز"), "SiC vs Carbon Graphite", BiText("Optical-flat mating faces creating dynamic seal.", "لیکج روکنے والی بنیادی ہموار سطحیں۔")),
                EquipmentComponent("ms_orings", BiText("Secondary O-Rings", "او-رنگز"), "FKM / FFKM Kalrez", BiText("Prevents static bypass along shaft sleeve.", "سلیو کے نیچے سے لیکج روکتے ہیں۔"))
            ),
            typesAndClassification = listOf(
                BiText("Single Seal with Plan 11 Flush (Clean hydrocarbons)", "سنگل سیل (Plan 11)"),
                BiText("Dual Pressurized Seal with Plan 53A Barrier Fluid (Toxic/H2S/LPG)", "ڈبل سیل (Plan 53A خطرناک گیسوں کے لیے)")
            ),
            applications = listOf(
                BiText("Refinery pumps, pipeline boosters & chemical mixers", "ریفائنری پمپس اور مکسرز")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Design Standard", "معیار"), "API Standard 682"),
                EquipmentSpec(BiText("Face Flatness", "سطح کی ہمواری"), "< 0.6 microns (2 Helium light bands)")
            ),
            maintenanceConcepts = listOf(
                BiText("Remove cartridge spacer clips ONLY after tightening gland bolts and coupling shaft.", "سیل کے اسپیسر کلپس ہمیشہ آخر میں بولٹ ٹائٹ کرنے کے بعد اتاریں۔")
            ),
            commonProblems = listOf(
                BiText("Dry-running blistered carbon face when pump loses prime.", "پمپ خشک چلنے سے سیل فیس کا جل جانا۔"),
                BiText("Shaft misalignment causing fretting wear under O-rings.", "الائنمنٹ خرابی سے سیل کا جلد لیک ہو جانا۔")
            ),
            safetyPrecautions = listOf(
                BiText("Do not touch lapped seal faces with bare fingers; clean with lint-free isopropyl wipe.", "سیل کے چمکدار چہروں کو ننگے ہاتھوں سے نہ چھوئیں۔")
            ),
            relatedIds = listOf("eq_centrifugal_pump", "eq_vfd_motor"),
            references = listOf("API Standard 682 Shaft Sealing Systems")
        ),

        // 19. HSE and Industrial Safety
        EquipmentItem(
            id = "eq_h2s_detector",
            categoryId = "cat_hse",
            name = BiText("Personal & Fixed H2S Gas Detector", "H2S گیس ڈیٹیکٹر اور حفاظتی نظام"),
            shortTagline = BiText("Life-saving sour gas electrochemical warning monitor", "زہریلی H2S گیس کا فوری الرٹ دینے والا حفاظتی آلہ"),
            has3dModel = false,
            diagramType = DiagramType.ELECTRICAL_EXPLOSION_PROOF,
            isSmallComponent = true,
            definition = BiText(
                "An intrinsically safe (Ex-ia) electrochemical gas sensing monitor worn in the breathing zone or mounted on process skids to detect lethal Hydrogen Sulfide (H2S) and Lower Explosive Limit (LEL) gases.",
                "ایسا حفاظتی سینسر جو ہوا میں زہریلی ہائیڈروجن سلفائیڈ (H2S) یا دھماکہ خیز گیس کی مقدار کو فوری ناپ کر الارم بجاتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Alerts personnel within seconds because H2S paralyzes the human sense of smell (olfactory fatigue) at 100 ppm and causes rapid collapse above 500 ppm.",
                "چونکہ 100 ppm پر H2S سونگھنے کی حس ختم کر دیتی ہے، یہ آلہ فوری آواز، روشنی اور وائبریشن سے خبردار کر کے جان بچاتا ہے۔"
            ),
            workingPrinciple = BiText(
                "H2S diffuses through a hydrophobic membrane into an electrochemical cell, oxidizing at the sensing electrode and producing microamp current proportional to gas concentration in ppm.",
                "گیس میمبرین سے گزر کر الیکٹرو کیمیکل سیل میں جاتی ہے جہاں کیمیائی عمل سے بننے والا کرنٹ ppm ریڈنگ ظاہر کرتا ہے۔"
            ),
            construction = BiText(
                "Anti-static rugged polycarbonate housing, sintered sensor filter, 95 dB horn, bright LED strobes, vibrator, LCD screen, and alligator clip.",
                "مضبوط اینٹی اسٹیٹک کیسنگ، سینسر فلٹر، 95 ڈیسی بل سائرن، فلیش لائٹ اور LCD اسکرین۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("h2s_cell", BiText("Electrochemical Sensor Cell", "الیکٹرو کیمیکل سینسر"), "Electrolyte + Au/Pt", BiText("Detects 0–100+ ppm H2S rapidly (T90 < 15s).", "سیکنڈوں میں H2S کی مقدار ناپتا ہے۔")),
                EquipmentComponent("h2s_alarm", BiText("Triple Alarm Module", "تہرے الارم سسٹم"), "95 dB Piezo + LED", BiText("Audible, visual, and tactile vibration alerts.", "تیز آواز، روشنی اور وائبریشن الارم دیتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Single-Gas Personal H2S Clip Monitor", "پرسنل سنگل گیس مانیٹر"),
                BiText("4-Gas Confined Space Monitor (O2, LEL, H2S, CO)", "4-گیس مانیٹر")
            ),
            applications = listOf(
                BiText("Sour drilling rigs, tank gauging, amine units & confined space entry", "ڈرلنگ رگز، ٹینک گیجنگ اور کنفائنڈ اسپیس")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Low / High Alarm", "لو / ہائی الارم"), "10 ppm (Low) / 15–20 ppm (High)"),
                EquipmentSpec(BiText("IDLH Lethal Limit", "جان لیوا حد"), "100 ppm"),
                EquipmentSpec(BiText("Certification", "سرٹیفیکیشن"), "ATEX Ex ia IIC T4")
            ),
            maintenanceConcepts = listOf(
                BiText("Perform daily bump test with certified calibration gas before entering sour zones.", "سائٹ پر جانے سے پہلے روزانہ بمپ ٹیسٹ (Bump Test) ضرور کریں۔")
            ),
            commonProblems = listOf(
                BiText("Sensor poisoning from silicone vapors or expired electrolyte.", "سلیکون بخارات یا پرانے سیل کی وجہ سے سینسر کا کام چھوڑنا۔")
            ),
            safetyPrecautions = listOf(
                BiText("If alarm sounds, hold breath immediately, don emergency escape hood/SCBA, and walk crosswind/upwind to muster point.", "الارم بجتے ہی سانس روک کر ہوا کے مخالف سمت (Upwind) محفوظ جگہ پر جائیں۔")
            ),
            relatedIds = listOf("eq_amine_contactor", "eq_bop_stack"),
            references = listOf("OSHA 29 CFR 1910.1000", "API RP 49 Drilling Safety for H2S")
        ),

        // 20. Petroleum Laboratory and Testing
        EquipmentItem(
            id = "eq_api_hydrometer",
            categoryId = "cat_lab",
            name = BiText("API Gravity Hydrometer & Cylinder", "API گریویٹی ہائیڈرو میٹر"),
            shortTagline = BiText("ASTM D1298 standard crude oil density & quality tester", "خام تیل کے ہلکے یا بھاری پن (API Gravity) کا لیبارٹری ٹیسٹ"),
            has3dModel = false,
            diagramType = DiagramType.LAB_TESTING_HYDROMETER,
            isSmallComponent = true,
            definition = BiText(
                "A precision-calibrated borosilicate glass float instrument (ASTM D1298) used to measure the relative density and API Gravity of crude oil and petroleum products.",
                "شیشے کا معیاری لیبارٹری آلہ (ASTM D1298) جو خام تیل کی کثافت اور API گریویٹی ناپ کر تیل کا معیار اور قیمت طے کرتا ہے۔"
            ),
            purposeAndFunction = BiText(
                "Determines whether crude oil is Light (>31.1° API), Medium (22.3–31.1° API), Heavy (10–22.3° API), or Extra Heavy (<10° API) for custody transfer and refinery yield planning.",
                "خام تیل کی خرید و فروخت اور ریفائنری میں اس کی کوالٹی (Light/Medium/Heavy) معلوم کرنا۔"
            ),
            workingPrinciple = BiText(
                "Based on Archimedes' buoyancy principle: the weighted glass bulb sinks deeper in lighter (higher API) oil and floats higher in denser (lower API) oil; reading is temperature-corrected to 60°F (15.56°C).",
                "ارشمیدس کے اصول کے تحت ہلکے تیل میں آلہ زیادہ ڈوبتا ہے اور بھاری تیل میں اوپر تیرتا ہے؛ ریڈنگ کو 60°F درجہ حرارت پر ایڈجسٹ کیا جاتا ہے۔"
            ),
            construction = BiText(
                "Weighted lead/steel-shot glass bulb, slender graduated stem calibrated in °API (e.g., 19–31° or 29–41°), clear glass hydrometer jar, and ASTM thermometer.",
                "وزنی شیشے کا بلب، API درجوں والا باریک اسکیل اور شفاف شیشے کا سلنڈر۔"
            ),
            mainComponents = listOf(
                EquipmentComponent("lab_bulb", BiText("Weighted Glass Float", "وزنی گلاس بلب"), "Borosilicate Glass", BiText("Maintains vertical upright flotation in oil.", "تیل میں آلے کو بالکل سیدھا تیراتا ہے۔")),
                EquipmentComponent("lab_stem", BiText("Calibrated °API Scale", "API اسکیل"), "Paper Scale in Stem", BiText("Direct readout in 0.1° API increments.", "0.1 ڈگری کی درستگی تک ریڈنگ دیتا ہے۔"))
            ),
            typesAndClassification = listOf(
                BiText("Plain ASTM API Hydrometer", "سادہ API ہائیڈرو میٹر"),
                BiText("Thermohydrometer (Built-in thermometer)", "تھرمو ہائیڈرو میٹر")
            ),
            applications = listOf(
                BiText("Refinery QA/QC labs, tank custody transfer & well testing", "ریفائنری لیبارٹری اور ٹینک کسٹڈی ٹرانسفر")
            ),
            specifications = listOf(
                EquipmentSpec(BiText("Formula", "فارمولا"), "°API = (141.5 / SG@60°F) − 131.5"),
                EquipmentSpec(BiText("Standard", "معیار"), "ASTM D1298 / API MPMS Ch. 9.1")
            ),
            maintenanceConcepts = listOf(
                BiText("Clean with solvent naphtha and verify zero meniscus air bubbles before reading.", "استعمال کے بعد نپتھا سے صاف کریں اور ریڈنگ لیتے وقت بلبلے ہٹا دیں۔")
            ),
            commonProblems = listOf(
                BiText("Failing to correct observed API to 60°F via ASTM Table 5A/5B.", "60°F پر درجہ حرارت کی تصحیح (Correction) نہ کرنے سے غلط حساب۔")
            ),
            safetyPrecautions = listOf(
                BiText("Perform volatile crude testing inside a fume hood away from open flames.", "ہوا دار فیوم ہُڈ میں ٹیسٹ کریں کیونکہ خام تیل کے بخارات آتش گیر ہوتے ہیں۔")
            ),
            relatedIds = listOf("eq_petroleum_products", "eq_floating_roof_tank"),
            references = listOf("ASTM D1298 Standard Test Method for Density/API Gravity")
        )
    )
}
