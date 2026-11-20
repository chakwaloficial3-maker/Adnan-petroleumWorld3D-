package com.example.engine3d

import com.example.model.BiText
import com.example.model.Model3DType

data class Part3D(
    val componentId: String,
    val label: BiText,
    val description: BiText,
    val material: String,
    val mesh: MeshData,
    val colorRgba: FloatArray,
    val isOuterShell: Boolean,
    val explodeVector: FloatArray,
    val centerLocal: FloatArray,
    val boundingRadius: Float
)

data class ProjectedLabel3D(
    val componentId: String,
    val label: BiText,
    val screenX: Float,
    val screenY: Float,
    val depth: Float,
    val isSelected: Boolean
)

enum class View3DMode {
    EXTERNAL,
    INTERNAL,
    EXPLODED,
    CROSS_SECTION
}

object MachinerySceneBuilder {

    private fun rgba(hex: Long): FloatArray {
        val a = ((hex ushr 24) and 0xFF) / 255f
        val r = ((hex ushr 16) and 0xFF) / 255f
        val g = ((hex ushr 8) and 0xFF) / 255f
        val b = (hex and 0xFF) / 255f
        return floatArrayOf(r, g, b, if (a == 0f) 1f else a)
    }

    fun buildScene(type: Model3DType): List<Part3D> = when (type) {
        Model3DType.CENTRIFUGAL_PUMP -> buildCentrifugalPump()
        Model3DType.MUD_PUMP -> buildMudPump()
        Model3DType.SEPARATOR_3PHASE -> buildThreePhaseSeparator()
        Model3DType.CHRISTMAS_TREE -> buildChristmasTree()
        Model3DType.DRILLING_RIG -> buildDrillingRig()
        Model3DType.GATE_VALVE -> buildGateValve()
        Model3DType.DISTILLATION_COLUMN -> buildDistillationColumn()
        Model3DType.RECIPROCATING_COMPRESSOR -> buildCompressor()
        Model3DType.BOP_STACK -> buildBopStack()
    }

    private fun buildCentrifugalPump(): List<Part3D> = listOf(
        Part3D(
            componentId = "cp_volute",
            label = BiText("Volute Casing", "وولیوٹ کیسنگ (Volute Casing)"),
            description = BiText(
                "Spiral pressure casing that collects fluid from the impeller and converts kinetic energy into static discharge pressure.",
                "سپرال پریشر کیسنگ جو امپیلر سے نکلنے والے مائع کی رفتار کو پریشر میں تبدیل کرتی ہے۔"
            ),
            material = "Cast Carbon Steel (ASTM A216 WCB)",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0.1f, 0f, 0.72f, 0.72f, 0.42f, axis = 'Z', segments = 28),
                MeshGeometry.cylinder(0f, 0.1f, 0.25f, 0.56f, 0.72f, 0.10f, axis = 'Z', segments = 28)
            ),
            colorRgba = rgba(0xFF1F6FEB),
            isOuterShell = true,
            explodeVector = floatArrayOf(0f, 0f, 0.85f),
            centerLocal = floatArrayOf(0f, 0.1f, 0f),
            boundingRadius = 0.75f
        ),
        Part3D(
            componentId = "cp_impeller",
            label = BiText("Radial Impeller", "ریڈیئل امپیلر (Impeller)"),
            description = BiText(
                "Rotating curved-vane rotor transferring mechanical energy from the shaft to the pumped hydrocarbon or water.",
                "گھومنے والا وین والا پرزہ جو شافٹ کی مکینیکل توانائی کو مائع میں منتقل کر کے دباؤ پیدا کرتا ہے۔"
            ),
            material = "Duplex Stainless Steel (CD4MCu)",
            mesh = MeshGeometry.impeller(0f, 0.1f, 0.02f, radius = 0.46f, thickness = 0.18f, vanes = 7),
            colorRgba = rgba(0xFFF5B041),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0f, 0.35f),
            centerLocal = floatArrayOf(0f, 0.1f, 0.02f),
            boundingRadius = 0.48f
        ),
        Part3D(
            componentId = "cp_suction",
            label = BiText("Suction Nozzle", "سکشن نوزل (Suction Flange)"),
            description = BiText(
                "Axial inlet port guiding low-pressure fluid smoothly into the impeller eye while minimizing NPSH loss.",
                "انلیٹ پائپ جو کم دباؤ والے مائع کو امپیلر کے مرکز (Eye) تک ہموار طریقے سے پہنچاتا ہے۔"
            ),
            material = "Forged Steel Flange (ASME B16.5)",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0.1f, 0.55f, 0.22f, 0.22f, 0.55f, axis = 'Z'),
                MeshGeometry.cylinder(0f, 0.1f, 0.82f, 0.34f, 0.34f, 0.08f, axis = 'Z')
            ),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0f, 1.25f),
            centerLocal = floatArrayOf(0f, 0.1f, 0.65f),
            boundingRadius = 0.35f
        ),
        Part3D(
            componentId = "cp_discharge",
            label = BiText("Discharge Nozzle", "ڈسچارج نوزل (Discharge Outlet)"),
            description = BiText(
                "Tangential vertical outlet delivering high-pressure fluid into downstream process piping.",
                "اوپری آؤٹ لیٹ جو ہائی پریشر مائع کو پائپ لائن یا پروسیس یونٹ میں بھیجتا ہے۔"
            ),
            material = "Forged Steel (Class 300/600)",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0.32f, 0.85f, 0f, 0.18f, 0.20f, 0.55f, axis = 'Y'),
                MeshGeometry.cylinder(0.32f, 1.12f, 0f, 0.29f, 0.29f, 0.08f, axis = 'Y')
            ),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.25f, 0.9f, 0f),
            centerLocal = floatArrayOf(0.32f, 0.95f, 0f),
            boundingRadius = 0.32f
        ),
        Part3D(
            componentId = "cp_seal",
            label = BiText("Mechanical Seal", "مکینیکل سیل (Mechanical Seal)"),
            description = BiText(
                "Precision rotating/stationary seal faces preventing hazardous hydrocarbon leakage along the pump shaft.",
                "شافٹ کے گرد لیکج روکنے والی خاص سیل جو خطرناک تیل یا گیس کو باہر نکلنے سے بچاتی ہے۔"
            ),
            material = "Silicon Carbide / Carbon Graphite",
            mesh = MeshGeometry.cylinder(0f, 0.1f, -0.34f, 0.22f, 0.22f, 0.18f, axis = 'Z'),
            colorRgba = rgba(0xFF2ECC71),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0f, -0.45f),
            centerLocal = floatArrayOf(0f, 0.1f, -0.34f),
            boundingRadius = 0.24f
        ),
        Part3D(
            componentId = "cp_shaft",
            label = BiText("Drive Shaft & Bearings", "ڈرائیو شافٹ اور بیرنگز"),
            description = BiText(
                "Hardened alloy steel shaft supported by radial and thrust bearings inside the oil-lubricated bearing housing.",
                "مضبوط اسٹیل شافٹ جو موٹر کی طاقت امپیلر تک منتقل کرتا ہے اور بیرنگ ہاؤسنگ میں چلتا ہے۔"
            ),
            material = "4140 Alloy Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0.1f, -0.65f, 0.10f, 0.10f, 1.25f, axis = 'Z'),
                MeshGeometry.cylinder(0f, 0.1f, -0.78f, 0.28f, 0.28f, 0.48f, axis = 'Z')
            ),
            colorRgba = rgba(0xFFB0C4DE),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0f, -0.95f),
            centerLocal = floatArrayOf(0f, 0.1f, -0.78f),
            boundingRadius = 0.42f
        ),
        Part3D(
            componentId = "cp_baseplate",
            label = BiText("Skid Baseplate", "بیس پلیٹ (Skid Frame)"),
            description = BiText(
                "Heavy structural steel base aligning pump and driver coupling while damping operating vibration.",
                "بھاری اسٹیل فریم جو پمپ اور موٹر کو سیدھ میں رکھتا ہے اور وائبریشن کم کرتا ہے۔"
            ),
            material = "Structural Carbon Steel",
            mesh = MeshGeometry.box(0f, -0.68f, -0.22f, 1.25f, 0.16f, 1.85f),
            colorRgba = rgba(0xFF3A4E6C),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, -0.55f, 0f),
            centerLocal = floatArrayOf(0f, -0.68f, -0.22f),
            boundingRadius = 0.85f
        )
    )

    private fun buildMudPump(): List<Part3D> = listOf(
        Part3D(
            componentId = "mp_power_end",
            label = BiText("Power End Crankcase", "پاور اینڈ کرینک کیس"),
            description = BiText(
                "Houses crankshaft, bull gear, pinion, and crossheads converting rotary motor input into reciprocating linear motion.",
                "اس حصے میں کرینک شافٹ اور گیئرز ہوتے ہیں جو موٹر کی گھومنے والی حرکت کو آگے پیچھے حرکت میں بدلتے ہیں۔"
            ),
            material = "Fabricated Alloy Steel",
            mesh = MeshGeometry.box(-0.55f, 0f, 0f, 1.05f, 0.85f, 1.25f),
            colorRgba = rgba(0xFF1F6FEB),
            isOuterShell = true,
            explodeVector = floatArrayOf(-0.6f, 0f, 0f),
            centerLocal = floatArrayOf(-0.55f, 0f, 0f),
            boundingRadius = 0.72f
        ),
        Part3D(
            componentId = "mp_fluid_cylinders",
            label = BiText("Triplex Fluid Cylinders", "ٹرائی پلیکس فلوئڈ سلنڈرز"),
            description = BiText(
                "Three forged steel modules containing replaceable chrome liners and suction/discharge check valves.",
                "تین مضبوط سلنڈر جن میں تبدیل ہونے والے لائنرز اور ہائی پریشر والوز نصب ہوتے ہیں۔"
            ),
            material = "Forged 4135 Chrome-Moly Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0.42f, -0.05f, -0.38f, 0.17f, 0.17f, 0.72f, axis = 'X'),
                MeshGeometry.cylinder(0.42f, -0.05f, 0f, 0.17f, 0.17f, 0.72f, axis = 'X'),
                MeshGeometry.cylinder(0.42f, -0.05f, 0.38f, 0.17f, 0.17f, 0.72f, axis = 'X')
            ),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = true,
            explodeVector = floatArrayOf(0.55f, 0f, 0f),
            centerLocal = floatArrayOf(0.42f, -0.05f, 0f),
            boundingRadius = 0.58f
        ),
        Part3D(
            componentId = "mp_pistons",
            label = BiText("Pistons & Pony Rods", "پسٹن اور راڈز (Pistons)"),
            description = BiText(
                "Three synchronized elastomer-bonded pistons reciprocating 120° out of phase inside liners up to 7,500 psi.",
                "تین پسٹن جو 120 ڈگری کے فرق سے حرکت کرتے ہیں تاکہ ڈرلنگ مڈ کو مسلسل ہائی پریشر پر پمپ کیا جا سکے۔"
            ),
            material = "Urethane / Forged Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0.16f, -0.05f, -0.38f, 0.10f, 0.10f, 0.78f, axis = 'X'),
                MeshGeometry.cylinder(0.16f, -0.05f, 0f, 0.10f, 0.10f, 0.78f, axis = 'X'),
                MeshGeometry.cylinder(0.16f, -0.05f, 0.38f, 0.10f, 0.10f, 0.78f, axis = 'X')
            ),
            colorRgba = rgba(0xFFF5B041),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.15f, 0.35f, 0f),
            centerLocal = floatArrayOf(0.16f, -0.05f, 0f),
            boundingRadius = 0.48f
        ),
        Part3D(
            componentId = "mp_dampener",
            label = BiText("Pulsation Dampener", "پلسیشن ڈیمپنر (Pulsation Dampener)"),
            description = BiText(
                "Nitrogen-precharged bladder accumulator smoothing discharge pressure spikes to protect standpipe and MWD tools.",
                "نائٹروجن گیس سے بھرا گنبد نما آلہ جو مڈ پریشر کے جھٹکوں کو ہموار بناتا ہے۔"
            ),
            material = "Forged Pressure Vessel + N2 Bladder",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0.68f, 0.65f, 0f, 0.24f, 0.24f, 0.55f, axis = 'Y'),
                MeshGeometry.sphere(0.68f, 0.92f, 0f, 0.24f)
            ),
            colorRgba = rgba(0xFFFFD166),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.35f, 0.85f, 0f),
            centerLocal = floatArrayOf(0.68f, 0.75f, 0f),
            boundingRadius = 0.36f
        ),
        Part3D(
            componentId = "mp_manifold",
            label = BiText("Discharge Manifold", "ڈسچارج مینی فولڈ"),
            description = BiText(
                "High-pressure header collecting output from all three fluid cylinders into the rig standpipe line.",
                "ہائی پریشر پائپ جو تینوں سلنڈرز سے مڈ جمع کر کے اسٹینڈ پائپ میں بھیجتا ہے۔"
            ),
            material = "API 5,000/7,500 psi Forged Steel",
            mesh = MeshGeometry.cylinder(0.75f, 0.25f, 0f, 0.13f, 0.13f, 1.15f, axis = 'Z'),
            colorRgba = rgba(0xFF2ECC71),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.9f, 0.35f, 0f),
            centerLocal = floatArrayOf(0.75f, 0.25f, 0f),
            boundingRadius = 0.52f
        )
    )

    private fun buildThreePhaseSeparator(): List<Part3D> = listOf(
        Part3D(
            componentId = "sep_shell",
            label = BiText("Pressure Vessel Shell", "پریشر ویسل شیل (Shell)"),
            description = BiText(
                "ASME Section VIII horizontal cylindrical drum providing residence time for gravity separation of gas, oil, and water.",
                "مضبوط اسٹیل ڈرم جہاں کششِ ثقل کی مدد سے گیس، خام تیل اور پانی ایک دوسرے سے الگ ہوتے ہیں۔"
            ),
            material = "SA-516 Gr. 70 Carbon Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0f, 0f, 0.58f, 0.58f, 1.75f, axis = 'X', segments = 28),
                MeshGeometry.sphere(-0.875f, 0f, 0f, 0.57f),
                MeshGeometry.sphere(0.875f, 0f, 0f, 0.57f)
            ),
            colorRgba = rgba(0xFF1F6FEB),
            isOuterShell = true,
            explodeVector = floatArrayOf(0f, 0.75f, 0f),
            centerLocal = floatArrayOf(0f, 0f, 0f),
            boundingRadius = 1.1f
        ),
        Part3D(
            componentId = "sep_inlet_diverter",
            label = BiText("Inlet Diverter", "انلیٹ ڈائیورٹر (Inlet Diverter)"),
            description = BiText(
                "Deflector baffle at the wellstream inlet that abruptly changes momentum to separate bulk gas from liquid droplets.",
                "داخل ہونے والے تیل اور گیس کے بہاؤ کا رخ موڑ کر گیس کو مائع سے فوری الگ کرنے والی پلیٹ۔"
            ),
            material = "316L Stainless Steel",
            mesh = MeshGeometry.box(-0.62f, 0.12f, 0f, 0.08f, 0.52f, 0.65f),
            colorRgba = rgba(0xFFF5B041),
            isOuterShell = false,
            explodeVector = floatArrayOf(-0.65f, 0.25f, 0.35f),
            centerLocal = floatArrayOf(-0.62f, 0.12f, 0f),
            boundingRadius = 0.38f
        ),
        Part3D(
            componentId = "sep_weir",
            label = BiText("Retention Weir Plate", "ویئر پلیٹ (Weir Plate)"),
            description = BiText(
                "Internal dam maintaining oil-water interface level so lighter oil overflows into the oil compartment while water settles.",
                "اندرونی دیوار جو پانی کو نیچے روک کر صاف تیل کو اوپر سے دوسرے چیمبر میں جانے دیتی ہے۔"
            ),
            material = "Carbon Steel / Epoxy Coated",
            mesh = MeshGeometry.box(0.28f, -0.20f, 0f, 0.07f, 0.52f, 0.85f),
            colorRgba = rgba(0xFF2ECC71),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.2f, -0.35f, 0.45f),
            centerLocal = floatArrayOf(0.28f, -0.20f, 0f),
            boundingRadius = 0.42f
        ),
        Part3D(
            componentId = "sep_mist_extractor",
            label = BiText("Mist Extractor Pad", "مسٹ ایکسٹریکٹر (Mist Pad)"),
            description = BiText(
                "Knitted wire-mesh coalescer at the gas outlet capturing tiny entrained liquid droplets before gas leaves the vessel.",
                "جالی دار فلٹر جو گیس کے ساتھ جانے والے تیل کے باریک قطروں کو روک کر گیس کو خشک کرتا ہے۔"
            ),
            material = "316 SS Wire Mesh",
            mesh = MeshGeometry.box(0.66f, 0.34f, 0f, 0.28f, 0.14f, 0.46f),
            colorRgba = rgba(0xFFFFD166),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.55f, 0.45f, 0f),
            centerLocal = floatArrayOf(0.66f, 0.34f, 0f),
            boundingRadius = 0.30f
        ),
        Part3D(
            componentId = "sep_nozzles",
            label = BiText("Gas / Oil / Water Nozzles", "گیس، تیل اور پانی کی نوزلز"),
            description = BiText(
                "Dedicated top gas outlet, bottom oil & water outlets, and safety relief valve (PSV) connection.",
                "اوپر گیس نکلنے کی نوزل، نیچے تیل اور پانی کے آؤٹ لیٹس، اور سیفٹی ریلیف والو کنکشن۔"
            ),
            material = "ASME Forged Flanges",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(-0.78f, 0.65f, 0f, 0.12f, 0.12f, 0.35f, axis = 'Y'),
                MeshGeometry.cylinder(0.66f, 0.68f, 0f, 0.14f, 0.14f, 0.38f, axis = 'Y'),
                MeshGeometry.cylinder(-0.20f, -0.68f, 0f, 0.11f, 0.11f, 0.32f, axis = 'Y'),
                MeshGeometry.cylinder(0.62f, -0.68f, 0f, 0.11f, 0.11f, 0.32f, axis = 'Y')
            ),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0.95f, 0f),
            centerLocal = floatArrayOf(0.66f, 0.68f, 0f),
            boundingRadius = 0.55f
        ),
        Part3D(
            componentId = "sep_saddles",
            label = BiText("Support Saddles", "سپورٹ سیڈلز (Saddles)"),
            description = BiText(
                "Reinforced steel cradle supports anchoring the vessel to concrete foundations while allowing thermal expansion.",
                "مضبوط اسٹیل پائے جو پورے پریشر ویسل کا وزن کنکریٹ فاؤنڈیشن پر سنبھالتے ہیں۔"
            ),
            material = "Structural Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.box(-0.55f, -0.65f, 0f, 0.22f, 0.36f, 0.85f),
                MeshGeometry.box(0.55f, -0.65f, 0f, 0.22f, 0.36f, 0.85f)
            ),
            colorRgba = rgba(0xFF3A4E6C),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, -0.65f, 0f),
            centerLocal = floatArrayOf(0f, -0.65f, 0f),
            boundingRadius = 0.65f
        )
    )

    private fun buildChristmasTree(): List<Part3D> = listOf(
        Part3D(
            componentId = "xt_wellhead",
            label = BiText("Tubing & Casing Head", "کیسنگ اور ٹیوبنگ ہیڈ"),
            description = BiText(
                "Lower forged spool assembly suspending casing and production tubing strings while sealing the annulus.",
                "نچلا مضبوط حصہ جو کنویں کی کیسنگ اور پروڈکشن ٹیوبنگ کا وزن سہارا دیتا ہے اور پریشر سیل کرتا ہے۔"
            ),
            material = "API 6A Forged Alloy Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, -0.82f, 0f, 0.42f, 0.48f, 0.45f, axis = 'Y'),
                MeshGeometry.cylinder(0f, -0.58f, 0f, 0.48f, 0.48f, 0.09f, axis = 'Y')
            ),
            colorRgba = rgba(0xFF3A4E6C),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, -0.65f, 0f),
            centerLocal = floatArrayOf(0f, -0.75f, 0f),
            boundingRadius = 0.48f
        ),
        Part3D(
            componentId = "xt_master_valves",
            label = BiText("Lower & Upper Master Valves", "ماسٹر گیٹ والوز (Master Valves)"),
            description = BiText(
                "Primary vertical full-bore gate valves capable of shutting in total well production during emergencies or maintenance.",
                "بنیادی والوز جو ایمرجنسی یا مرمت کے وقت پورے کنویں کی پیداوار کو مکمل بند کر دیتے ہیں۔"
            ),
            material = "AISI 4130 / Inconel 625 Clad",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, -0.15f, 0f, 0.22f, 0.22f, 0.72f, axis = 'Y'),
                MeshGeometry.box(0f, -0.28f, 0f, 0.56f, 0.22f, 0.34f),
                MeshGeometry.box(0f, 0.04f, 0f, 0.56f, 0.22f, 0.34f),
                MeshGeometry.cylinder(0.45f, -0.28f, 0f, 0.16f, 0.16f, 0.05f, axis = 'X'),
                MeshGeometry.cylinder(0.45f, 0.04f, 0f, 0.16f, 0.16f, 0.05f, axis = 'X')
            ),
            colorRgba = rgba(0xFF1F6FEB),
            isOuterShell = true,
            explodeVector = floatArrayOf(0f, -0.15f, 0f),
            centerLocal = floatArrayOf(0f, -0.15f, 0f),
            boundingRadius = 0.48f
        ),
        Part3D(
            componentId = "xt_wing_valves",
            label = BiText("Flow & Kill Wing Valves", "ونگ والوز (Wing Valves)"),
            description = BiText(
                "Horizontal side valves routing produced hydrocarbons to the flowline (right) and kill fluid injection (left).",
                "دائیں بائیں لگے والوز جو تیل/گیس کو فلو لائن میں بھیجنے یا کنویں کو کنٹرول کرنے کے لیے استعمال ہوتے ہیں۔"
            ),
            material = "API 6A PSL-3 Forged Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0.38f, 0f, 0.24f, 0.24f, 0.28f, axis = 'Y'),
                MeshGeometry.cylinder(0.48f, 0.38f, 0f, 0.14f, 0.14f, 0.75f, axis = 'X'),
                MeshGeometry.cylinder(-0.48f, 0.38f, 0f, 0.14f, 0.14f, 0.75f, axis = 'X'),
                MeshGeometry.cylinder(0.55f, 0.38f, 0.24f, 0.15f, 0.15f, 0.05f, axis = 'Z')
            ),
            colorRgba = rgba(0xFFF5B041),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.55f, 0.25f, 0f),
            centerLocal = floatArrayOf(0.42f, 0.38f, 0f),
            boundingRadius = 0.55f
        ),
        Part3D(
            componentId = "xt_choke",
            label = BiText("Production Choke", "پروڈکشن چوک (Choke Assembly)"),
            description = BiText(
                "Adjustable tungsten-carbide orifice controlling wellhead flowing pressure and production flow rate.",
                "ایڈجسٹ ایبل آلہ جو کنویں سے نکلنے والے تیل اور گیس کے دباؤ اور رفتار کو کنٹرول کرتا ہے۔"
            ),
            material = "Tungsten Carbide Trim",
            mesh = MeshGeometry.combine(
                MeshGeometry.box(0.88f, 0.38f, 0f, 0.26f, 0.26f, 0.26f),
                MeshGeometry.cylinder(0.88f, 0.18f, 0f, 0.11f, 0.11f, 0.32f, axis = 'Y')
            ),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.95f, 0.25f, 0f),
            centerLocal = floatArrayOf(0.88f, 0.32f, 0f),
            boundingRadius = 0.28f
        ),
        Part3D(
            componentId = "xt_swab_cap",
            label = BiText("Swab Valve & Tree Cap", "سواب والو اور ٹری کیپ"),
            description = BiText(
                "Topmost vertical access valve allowing wireline, slickline, or coiled tubing tools to enter the live well.",
                "سب سے اوپری والو جس کے ذریعے وائر لائن یا ٹیسٹنگ ٹولز کنویں کے اندر اتارے جاتے ہیں۔"
            ),
            material = "Forged Steel + Pressure Gauge",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0.72f, 0f, 0.18f, 0.20f, 0.42f, axis = 'Y'),
                MeshGeometry.cylinder(0f, 1.02f, 0f, 0.14f, 0.22f, 0.22f, axis = 'Y'),
                MeshGeometry.sphere(0f, 1.18f, 0f, 0.09f)
            ),
            colorRgba = rgba(0xFF2ECC71),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0.85f, 0f),
            centerLocal = floatArrayOf(0f, 0.85f, 0f),
            boundingRadius = 0.32f
        )
    )

    private fun buildDrillingRig(): List<Part3D> = listOf(
        Part3D(
            componentId = "rig_substructure",
            label = BiText("Substructure & Rig Floor", "سب اسٹرکچر اور رگ فلور"),
            description = BiText(
                "Elevated steel platform supporting the derrick, rotary table, pipe setback load, and providing clearance for the BOP stack.",
                "اونچا اسٹیل پلیٹ فارم جو ڈیرک، روٹری ٹیبل اور ڈرل پائپوں کا وزن اٹھاتا ہے اور نیچے BOP کے لیے جگہ بناتا ہے۔"
            ),
            material = "ASTM A572 Gr. 50 Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.box(0f, -0.78f, 0f, 1.55f, 0.28f, 1.35f),
                MeshGeometry.box(-0.55f, -1.02f, 0f, 0.35f, 0.24f, 1.25f),
                MeshGeometry.box(0.55f, -1.02f, 0f, 0.35f, 0.24f, 1.25f)
            ),
            colorRgba = rgba(0xFF3A4E6C),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, -0.6f, 0f),
            centerLocal = floatArrayOf(0f, -0.82f, 0f),
            boundingRadius = 0.9f
        ),
        Part3D(
            componentId = "rig_derrick",
            label = BiText("Lattice Derrick Mast", "ڈیرک ماسٹ (Steel Derrick)"),
            description = BiText(
                "Tall structural steel lattice tower providing vertical hoisting height and hook load capacity up to 1,500,000 lbs.",
                "بلند جالی دار اسٹیل ٹاور جو ڈرل اسٹرنگ کو اوپر اٹھانے اور نیچے اتارنے کے لیے بنیادی سہارا فراہم کرتا ہے۔"
            ),
            material = "High-Strength Structural Steel",
            mesh = MeshGeometry.latticeMast(0f, -0.64f, 0f, bottomHalf = 0.42f, topHalf = 0.16f, height = 1.95f, tiers = 5),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = true,
            explodeVector = floatArrayOf(0f, 0.35f, 0f),
            centerLocal = floatArrayOf(0f, 0.35f, 0f),
            boundingRadius = 0.95f
        ),
        Part3D(
            componentId = "rig_crown",
            label = BiText("Crown Block & Traveling Block", "کراؤن بلاک اور ٹریولنگ بلاک"),
            description = BiText(
                "Fixed top sheaves (Crown) and moving pulley block (Traveling) reeved with steel drilling line to multiply hoisting force.",
                "اوپر لگا فکسڈ پلّی نظام (Crown) اور نیچے اوپر چلنے والا بلاک جو بھاری پائپوں کو آسانی سے اٹھاتا ہے۔"
            ),
            material = "Forged Alloy Sheaves",
            mesh = MeshGeometry.combine(
                MeshGeometry.box(0f, 1.35f, 0f, 0.42f, 0.14f, 0.42f),
                MeshGeometry.box(0f, 0.65f, 0f, 0.24f, 0.28f, 0.20f)
            ),
            colorRgba = rgba(0xFFF5B041),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0.85f, 0f),
            centerLocal = floatArrayOf(0f, 0.98f, 0f),
            boundingRadius = 0.42f
        ),
        Part3D(
            componentId = "rig_top_drive",
            label = BiText("Top Drive & Drill String", "ٹاپ ڈرائیو اور ڈرل اسٹرنگ"),
            description = BiText(
                "Electric/hydraulic motor suspended below the traveling block that rotates the drill string while circulating drilling mud.",
                "طاقتور موٹر جو ٹریولنگ بلاک کے نیچے لٹک کر ڈرل پائپ کو گھماتی ہے اور ساتھ ہی مڈ سرکولیٹ کرتی ہے۔"
            ),
            material = "AC Variable-Frequency Motor + API S-135 Pipe",
            mesh = MeshGeometry.combine(
                MeshGeometry.box(0f, 0.32f, 0f, 0.28f, 0.24f, 0.28f),
                MeshGeometry.cylinder(0f, -0.25f, 0f, 0.045f, 0.045f, 1.05f, axis = 'Y')
            ),
            colorRgba = rgba(0xFFFFD166),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0f, 0.65f),
            centerLocal = floatArrayOf(0f, 0.15f, 0f),
            boundingRadius = 0.48f
        ),
        Part3D(
            componentId = "rig_drawworks",
            label = BiText("Drawworks Hoist Drum", "ڈرا ورکس (Drawworks)"),
            description = BiText(
                "Heavy-duty winch spooling steel drilling line with regenerative electric and disc braking systems.",
                "بھاری ونچ مشین جو اسٹیل کیبل کو لپیٹ کر ڈرل اسٹرنگ کو اوپر نیچے کرتی اور بریک لگاتی ہے۔"
            ),
            material = "Cast Steel Drum + Disc Brakes",
            mesh = MeshGeometry.combine(
                MeshGeometry.box(-0.56f, -0.52f, -0.28f, 0.42f, 0.30f, 0.46f),
                MeshGeometry.cylinder(-0.56f, -0.52f, -0.28f, 0.16f, 0.16f, 0.50f, axis = 'Z')
            ),
            colorRgba = rgba(0xFF1F6FEB),
            isOuterShell = false,
            explodeVector = floatArrayOf(-0.65f, -0.25f, -0.45f),
            centerLocal = floatArrayOf(-0.56f, -0.52f, -0.28f),
            boundingRadius = 0.36f
        )
    )

    private fun buildGateValve(): List<Part3D> = listOf(
        Part3D(
            componentId = "gv_body",
            label = BiText("Flanged Valve Body", "فلینجڈ والو باڈی"),
            description = BiText(
                "Main pressure-retaining housing with ASME raised-face flanges bolted directly into oil & gas process pipelines.",
                "والو کا بیرونی مضبوط ڈھانچہ جو پائپ لائن کے اندر بہاؤ کے دباؤ کو برداشت کرتا ہے۔"
            ),
            material = "ASTM A216 WCB / SS 316",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, -0.25f, 0f, 0.36f, 0.36f, 1.35f, axis = 'X'),
                MeshGeometry.cylinder(-0.68f, -0.25f, 0f, 0.56f, 0.56f, 0.10f, axis = 'X'),
                MeshGeometry.cylinder(0.68f, -0.25f, 0f, 0.56f, 0.56f, 0.10f, axis = 'X'),
                MeshGeometry.cylinder(0f, 0.08f, 0f, 0.32f, 0.36f, 0.55f, axis = 'Y')
            ),
            colorRgba = rgba(0xFF1F6FEB),
            isOuterShell = true,
            explodeVector = floatArrayOf(0f, -0.55f, 0f),
            centerLocal = floatArrayOf(0f, -0.22f, 0f),
            boundingRadius = 0.72f
        ),
        Part3D(
            componentId = "gv_gate",
            label = BiText("Wedge Gate & Seat Rings", "گیٹ ڈسک اور سیٹ رنگز"),
            description = BiText(
                "Precision-machined solid wedge sliding perpendicular to flow between Stellite-faced seat rings for bubble-tight shutoff.",
                "اندرونی مضبوط پٹّی (Gate) جو اوپر نیچے حرکت کر کے تیل یا گیس کے بہاؤ کو مکمل روک دیتی ہے۔"
            ),
            material = "13Cr Stainless / Stellite 6",
            mesh = MeshGeometry.combine(
                MeshGeometry.box(0f, -0.22f, 0f, 0.12f, 0.54f, 0.52f),
                MeshGeometry.cylinder(-0.18f, -0.25f, 0f, 0.32f, 0.32f, 0.06f, axis = 'X'),
                MeshGeometry.cylinder(0.18f, -0.25f, 0f, 0.32f, 0.32f, 0.06f, axis = 'X')
            ),
            colorRgba = rgba(0xFFF5B041),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0.15f, 0.65f),
            centerLocal = floatArrayOf(0f, -0.22f, 0f),
            boundingRadius = 0.36f
        ),
        Part3D(
            componentId = "gv_bonnet",
            label = BiText("Bolted Bonnet & Packing", "بولٹڈ بونٹ اور پیکنگ"),
            description = BiText(
                "Upper pressure cover housing graphite stem packing rings to prevent fugitive emissions around the moving stem.",
                "اوپری ڈھکن اور گریفائٹ پیکنگ جو والو کی ڈنڈی (Stem) کے گرد لیکج روکتی ہے۔"
            ),
            material = "Forged Steel + Braided Graphite",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0.42f, 0f, 0.42f, 0.42f, 0.10f, axis = 'Y'),
                MeshGeometry.cylinder(0f, 0.65f, 0f, 0.18f, 0.24f, 0.40f, axis = 'Y')
            ),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0.45f, 0f),
            centerLocal = floatArrayOf(0f, 0.55f, 0f),
            boundingRadius = 0.38f
        ),
        Part3D(
            componentId = "gv_stem_wheel",
            label = BiText("Rising Stem & Handwheel", "رائزنگ اسٹیم اور ہینڈ وہیل"),
            description = BiText(
                "Threaded OS&Y (Outside Screw and Yoke) stem rising visibly through the handwheel to show open/closed valve position.",
                "چوڑی دار راڈ اور وہیل جسے گھما کر والو کھولا یا بند کیا جاتا ہے اور کھلنے کی حالت باہر سے نظر آتی ہے۔"
            ),
            material = "ASTM A182 F6a / Ductile Iron",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0.65f, 0f, 0.055f, 0.055f, 1.15f, axis = 'Y'),
                MeshGeometry.cylinder(0f, 1.05f, 0f, 0.48f, 0.48f, 0.06f, axis = 'Y')
            ),
            colorRgba = rgba(0xFF2ECC71),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, 0.95f, 0f),
            centerLocal = floatArrayOf(0f, 1.05f, 0f),
            boundingRadius = 0.48f
        )
    )

    private fun buildDistillationColumn(): List<Part3D> = listOf(
        Part3D(
            componentId = "dc_shell",
            label = BiText("Fractionating Tower Shell", "ڈسٹیلیشن ٹاور شیل"),
            description = BiText(
                "Tall vertical cylindrical column operating under temperature gradient from 370°C at flash zone to 110°C at top.",
                "بلند عمودی ٹاور جہاں درجہ حرارت کے فرق سے خام تیل کو پیٹرول، ڈیزل اور کیروسین میں الگ کیا جاتا ہے۔"
            ),
            material = "SA-516 Carbon Steel + 410S Cladding",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0.05f, 0f, 0.48f, 0.52f, 2.05f, axis = 'Y', segments = 28),
                MeshGeometry.sphere(0f, 1.07f, 0f, 0.47f),
                MeshGeometry.cylinder(0f, -1.08f, 0f, 0.55f, 0.58f, 0.28f, axis = 'Y')
            ),
            colorRgba = rgba(0xFF1F6FEB),
            isOuterShell = true,
            explodeVector = floatArrayOf(-0.65f, 0f, 0f),
            centerLocal = floatArrayOf(0f, 0.05f, 0f),
            boundingRadius = 1.15f
        ),
        Part3D(
            componentId = "dc_trays",
            label = BiText("Fractionation Trays", "ڈسٹیلیشن ٹریز (Valve/Sieve Trays)"),
            description = BiText(
                "Stacked horizontal perforated plates forcing rising hydrocarbon vapors to bubble through descending liquid reflux.",
                "اندرونی پلیٹیں (Trays) جہاں اوپر اٹھتی بخارات اور نیچے آتے مائع کا ملاپ ہو کر خالص اجزاء الگ ہوتے ہیں۔"
            ),
            material = "316L / 410S Stainless Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, -0.55f, 0f, 0.42f, 0.42f, 0.05f, axis = 'Y'),
                MeshGeometry.cylinder(0f, -0.20f, 0f, 0.42f, 0.42f, 0.05f, axis = 'Y'),
                MeshGeometry.cylinder(0f, 0.15f, 0f, 0.42f, 0.42f, 0.05f, axis = 'Y'),
                MeshGeometry.cylinder(0f, 0.50f, 0f, 0.42f, 0.42f, 0.05f, axis = 'Y'),
                MeshGeometry.cylinder(0f, 0.82f, 0f, 0.40f, 0.40f, 0.05f, axis = 'Y')
            ),
            colorRgba = rgba(0xFFF5B041),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.55f, 0f, 0f),
            centerLocal = floatArrayOf(0f, 0.15f, 0f),
            boundingRadius = 0.65f
        ),
        Part3D(
            componentId = "dc_overhead",
            label = BiText("Overhead Vapor & Reflux", "اوور ہیڈ ویپر اور ریفلکس"),
            description = BiText(
                "Carries light naphtha & LPG vapors to the overhead condenser and returns cooled reflux to control top temperature.",
                "اوپری پائپ جو ہلکے بخارات کو کنڈینسر تک لے جاتا ہے اور ٹاور کو ٹھنڈا رکھنے کے لیے ریفلکس واپس بھیجتا ہے۔"
            ),
            material = "Carbon Steel Process Piping",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 1.56f, 0f, 0.11f, 0.11f, 0.34f, axis = 'Y'),
                MeshGeometry.cylinder(0.32f, 1.68f, 0f, 0.11f, 0.11f, 0.68f, axis = 'X'),
                MeshGeometry.cylinder(0.62f, 1.38f, 0f, 0.11f, 0.11f, 0.65f, axis = 'Y')
            ),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.45f, 0.75f, 0f),
            centerLocal = floatArrayOf(0.32f, 1.55f, 0f),
            boundingRadius = 0.45f
        ),
        Part3D(
            componentId = "dc_side_draws",
            label = BiText("Side-Draw Product Nozzles", "سائیڈ ڈرا آؤٹ لیٹس (Kerosene/Diesel)"),
            description = BiText(
                "Extract intermediate boiling fractions—heavy naphtha, kerosene/jet fuel, and diesel—at specific tray elevations.",
                "درمیانی نوزلز جہاں سے کیروسین، جیٹ فیول اور ڈیزل الگ الگ اونچائی سے نکالے جاتے ہیں۔"
            ),
            material = "ASME Class 300 Flanges",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0.62f, 0.48f, 0f, 0.08f, 0.08f, 0.34f, axis = 'X'),
                MeshGeometry.cylinder(0.62f, -0.05f, 0f, 0.08f, 0.08f, 0.34f, axis = 'X'),
                MeshGeometry.cylinder(-0.62f, -0.68f, 0f, 0.11f, 0.11f, 0.38f, axis = 'X')
            ),
            colorRgba = rgba(0xFF2ECC71),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.85f, 0f, 0f),
            centerLocal = floatArrayOf(0.62f, 0.22f, 0f),
            boundingRadius = 0.42f
        )
    )

    private fun buildCompressor(): List<Part3D> = listOf(
        Part3D(
            componentId = "comp_frame",
            label = BiText("Crankcase & Distance Piece", "کرینک کیس اور ڈسٹنس پیس"),
            description = BiText(
                "Houses crankshaft, connecting rods, and crossheads isolating lubricated crankcase from process gas cylinder.",
                "مین باڈی جس میں کرینک شافٹ اور کراس ہیڈ ہوتے ہیں اور گیس سلنڈر کو لبریکیٹنگ آئل سے الگ رکھتی ہے۔"
            ),
            material = "Heavy Ductile Cast Iron",
            mesh = MeshGeometry.combine(
                MeshGeometry.box(-0.58f, -0.15f, 0f, 0.95f, 0.72f, 0.85f),
                MeshGeometry.cylinder(-0.02f, -0.10f, 0f, 0.22f, 0.22f, 0.35f, axis = 'X')
            ),
            colorRgba = rgba(0xFF3A4E6C),
            isOuterShell = false,
            explodeVector = floatArrayOf(-0.65f, 0f, 0f),
            centerLocal = floatArrayOf(-0.55f, -0.15f, 0f),
            boundingRadius = 0.65f
        ),
        Part3D(
            componentId = "comp_cylinder",
            label = BiText("Compression Cylinder", "کمپریشن سلنڈر"),
            description = BiText(
                "Water-cooled double-acting cylinder compressing natural gas across suction and discharge plate valves.",
                "واٹر کولڈ سلنڈر جو قدرتی گیس کو دبا کر ہائی پریشر پر پہنچاتا ہے۔"
            ),
            material = "Forged Steel / Cast Iron Liner",
            mesh = MeshGeometry.cylinder(0.48f, -0.10f, 0f, 0.36f, 0.36f, 0.82f, axis = 'X'),
            colorRgba = rgba(0xFF1F6FEB),
            isOuterShell = true,
            explodeVector = floatArrayOf(0.55f, 0f, 0f),
            centerLocal = floatArrayOf(0.48f, -0.10f, 0f),
            boundingRadius = 0.52f
        ),
        Part3D(
            componentId = "comp_piston",
            label = BiText("Piston & Rider Rings", "پسٹن اور رائڈر رنگز"),
            description = BiText(
                "Reciprocating piston fitted with PTFE compression rings that seal high-pressure gas inside the bore.",
                "آگے پیچھے چلنے والا پسٹن جس پر گیس لیکج روکنے والے خاص رنگز لگے ہوتے ہیں۔"
            ),
            material = "Aluminum / Cast Iron + PTFE Rings",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0.25f, -0.10f, 0f, 0.07f, 0.07f, 0.72f, axis = 'X'),
                MeshGeometry.cylinder(0.52f, -0.10f, 0f, 0.26f, 0.26f, 0.22f, axis = 'X')
            ),
            colorRgba = rgba(0xFFF5B041),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.2f, 0.45f, 0.35f),
            centerLocal = floatArrayOf(0.50f, -0.10f, 0f),
            boundingRadius = 0.36f
        ),
        Part3D(
            componentId = "comp_valves",
            label = BiText("Suction & Discharge Bottles", "سکشن اور ڈسچارج بوٹلز"),
            description = BiText(
                "Acoustic pulsation bottles above and below the cylinder protecting piping from reciprocating pressure waves.",
                "سلنڈر کے اوپر اور نیچے لگے چیمبر جو گیس کے دباؤ کی لہروں کو متوازن رکھتے ہیں۔"
            ),
            material = "ASME Pressure Piping",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0.48f, 0.46f, 0f, 0.20f, 0.20f, 0.75f, axis = 'Z'),
                MeshGeometry.cylinder(0.48f, -0.66f, 0f, 0.20f, 0.20f, 0.75f, axis = 'Z')
            ),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.45f, 0.75f, 0f),
            centerLocal = floatArrayOf(0.48f, 0.46f, 0f),
            boundingRadius = 0.45f
        ),
        Part3D(
            componentId = "comp_flywheel",
            label = BiText("Inertia Flywheel", "فلائی وہیل (Flywheel)"),
            description = BiText(
                "Heavy balanced wheel storing rotational kinetic energy to smooth torque fluctuations across compression strokes.",
                "بھاری گول پہیہ جو انجن یا موٹر کی گردش کو ہموار اور مستحکم رکھتا ہے۔"
            ),
            material = "Balanced Cast Iron",
            mesh = MeshGeometry.cylinder(-1.12f, -0.15f, 0f, 0.56f, 0.56f, 0.14f, axis = 'X'),
            colorRgba = rgba(0xFF2ECC71),
            isOuterShell = false,
            explodeVector = floatArrayOf(-1.1f, 0f, 0f),
            centerLocal = floatArrayOf(-1.12f, -0.15f, 0f),
            boundingRadius = 0.56f
        )
    )

    private fun buildBopStack(): List<Part3D> = listOf(
        Part3D(
            componentId = "bop_annular",
            label = BiText("Annular BOP Preventer", "اینولر بی او پی (Annular BOP)"),
            description = BiText(
                "Topmost hydraulic elastomer packing element able to seal around any drill pipe, kelly, casing, or even open hole.",
                "سب سے اوپری حفاظتی والو جس کا ربڑ پیکنگ عنصر ہر سائز کے پائپ یا خالی سوراخ کو بھی سیل کر سکتا ہے۔"
            ),
            material = "Forged Steel + Reinforced Nitrile Element",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, 0.78f, 0f, 0.46f, 0.48f, 0.52f, axis = 'Y'),
                MeshGeometry.cylinder(0f, 1.06f, 0f, 0.34f, 0.44f, 0.16f, axis = 'Y')
            ),
            colorRgba = rgba(0xFFFF5252),
            isOuterShell = true,
            explodeVector = floatArrayOf(0f, 0.85f, 0f),
            centerLocal = floatArrayOf(0f, 0.82f, 0f),
            boundingRadius = 0.50f
        ),
        Part3D(
            componentId = "bop_pipe_rams",
            label = BiText("Double Pipe & Shear Rams", "پائپ اور شیئر ریمز (Ram Preventers)"),
            description = BiText(
                "Opposing hydraulic steel rams that close tightly around drill pipe or shear the pipe during severe well kicks.",
                "طاقتور ہائیڈرولک بلاکس جو کِک کی صورت میں پائپ کے گرد بند ہو جاتے ہیں یا ایمرجنسی میں پائپ کو کاٹ کر کنواں بند کر دیتے ہیں۔"
            ),
            material = "API 16A 10,000/15,000 psi Alloy Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.box(0f, 0.16f, 0f, 1.32f, 0.32f, 0.52f),
                MeshGeometry.box(0f, -0.26f, 0f, 1.32f, 0.32f, 0.52f),
                MeshGeometry.cylinder(0f, -0.05f, 0f, 0.32f, 0.32f, 0.85f, axis = 'Y')
            ),
            colorRgba = rgba(0xFFF5B041),
            isOuterShell = false,
            explodeVector = floatArrayOf(0.55f, 0f, 0f),
            centerLocal = floatArrayOf(0f, -0.05f, 0f),
            boundingRadius = 0.70f
        ),
        Part3D(
            componentId = "bop_spool",
            label = BiText("Drilling Spool & Choke/Kill Lines", "ڈرلنگ اسپول اور چوک/کِل لائنز"),
            description = BiText(
                "Side-outlet spool routing high-pressure kick fluids to the choke manifold and heavy kill mud into the wellbore.",
                "درمیانی حصہ جہاں سے کِک فلوئڈ کو چوک مینی فولڈ تک بھیجا اور بھاری کِل مڈ کنویں میں داخل کیا جاتا ہے۔"
            ),
            material = "API 16A Forged Steel",
            mesh = MeshGeometry.combine(
                MeshGeometry.cylinder(0f, -0.62f, 0f, 0.34f, 0.34f, 0.32f, axis = 'Y'),
                MeshGeometry.cylinder(0.48f, -0.62f, 0f, 0.11f, 0.11f, 0.64f, axis = 'X'),
                MeshGeometry.cylinder(-0.48f, -0.62f, 0f, 0.11f, 0.11f, 0.64f, axis = 'X')
            ),
            colorRgba = rgba(0xFF00D4FF),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, -0.55f, 0f),
            centerLocal = floatArrayOf(0f, -0.62f, 0f),
            boundingRadius = 0.52f
        ),
        Part3D(
            componentId = "bop_wellhead_flange",
            label = BiText("Wellhead Connector Flange", "ویل ہیڈ کنیکٹر فلینج"),
            description = BiText(
                "Heavy bottom flange locking the entire BOP stack securely onto the surface casing wellhead.",
                "نچلا مضبوط فلینج جو پورے بی او پی اسٹیک کو کنویں کے ویل ہیڈ سے جوڑتا ہے۔"
            ),
            material = "Forged Alloy Steel",
            mesh = MeshGeometry.cylinder(0f, -0.92f, 0f, 0.52f, 0.52f, 0.18f, axis = 'Y'),
            colorRgba = rgba(0xFF3A4E6C),
            isOuterShell = false,
            explodeVector = floatArrayOf(0f, -0.95f, 0f),
            centerLocal = floatArrayOf(0f, -0.92f, 0f),
            boundingRadius = 0.52f
        )
    )
}
