package com.example.engine3d

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MeshData(
    val vertices: FloatArray, // interleaved: x, y, z, nx, ny, nz (6 floats per vertex)
    val vertexCount: Int
) {
    val buffer: FloatBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
        .apply {
            put(vertices)
            position(0)
        }
}

object MeshGeometry {
    private class Builder {
        private val data = ArrayList<Float>(1024)

        fun addTri(
            ax: Float, ay: Float, az: Float, anx: Float, any: Float, anz: Float,
            bx: Float, by: Float, bz: Float, bnx: Float, bny: Float, bnz: Float,
            cx: Float, cy: Float, cz: Float, cnx: Float, cny: Float, cnz: Float
        ) {
            data.add(ax); data.add(ay); data.add(az); data.add(anx); data.add(any); data.add(anz)
            data.add(bx); data.add(by); data.add(bz); data.add(bnx); data.add(bny); data.add(bnz)
            data.add(cx); data.add(cy); data.add(cz); data.add(cnx); data.add(cny); data.add(cnz)
        }

        fun addFlatTri(
            ax: Float, ay: Float, az: Float,
            bx: Float, by: Float, bz: Float,
            cx: Float, cy: Float, cz: Float
        ) {
            val ux = bx - ax; val uy = by - ay; val uz = bz - az
            val vx = cx - ax; val vy = cy - ay; val vz = cz - az
            var nx = uy * vz - uz * vy
            var ny = uz * vx - ux * vz
            var nz = ux * vy - uy * vx
            val len = sqrt(nx * nx + ny * ny + nz * nz).coerceAtLeast(1e-5f)
            nx /= len; ny /= len; nz /= len
            addTri(ax, ay, az, nx, ny, nz, bx, by, bz, nx, ny, nz, cx, cy, cz, nx, ny, nz)
        }

        fun addQuad(
            ax: Float, ay: Float, az: Float, anx: Float, any: Float, anz: Float,
            bx: Float, by: Float, bz: Float, bnx: Float, bny: Float, bnz: Float,
            cx: Float, cy: Float, cz: Float, cnx: Float, cny: Float, cnz: Float,
            dx: Float, dy: Float, dz: Float, dnx: Float, dny: Float, dnz: Float
        ) {
            addTri(ax, ay, az, anx, any, anz, bx, by, bz, bnx, bny, bnz, cx, cy, cz, cnx, cny, cnz)
            addTri(ax, ay, az, anx, any, anz, cx, cy, cz, cnx, cny, cnz, dx, dy, dz, dnx, dny, dnz)
        }

        fun addFlatQuad(
            ax: Float, ay: Float, az: Float,
            bx: Float, by: Float, bz: Float,
            cx: Float, cy: Float, cz: Float,
            dx: Float, dy: Float, dz: Float
        ) {
            addFlatTri(ax, ay, az, bx, by, bz, cx, cy, cz)
            addFlatTri(ax, ay, az, cx, cy, cz, dx, dy, dz)
        }

        fun build(): MeshData {
            val arr = data.toFloatArray()
            return MeshData(arr, arr.size / 6)
        }
    }

    fun box(
        cx: Float = 0f, cy: Float = 0f, cz: Float = 0f,
        sx: Float, sy: Float, sz: Float
    ): MeshData {
        val b = Builder()
        val hx = sx * 0.5f; val hy = sy * 0.5f; val hz = sz * 0.5f
        val x0 = cx - hx; val x1 = cx + hx
        val y0 = cy - hy; val y1 = cy + hy
        val z0 = cz - hz; val z1 = cz + hz
        // Front (+Z)
        b.addFlatQuad(x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1)
        // Back (-Z)
        b.addFlatQuad(x1, y0, z0, x0, y0, z0, x0, y1, z0, x1, y1, z0)
        // Right (+X)
        b.addFlatQuad(x1, y0, z1, x1, y0, z0, x1, y1, z0, x1, y1, z1)
        // Left (-X)
        b.addFlatQuad(x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0)
        // Top (+Y)
        b.addFlatQuad(x0, y1, z1, x1, y1, z1, x1, y1, z0, x0, y1, z0)
        // Bottom (-Y)
        b.addFlatQuad(x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, y0, z1)
        return b.build()
    }

    /**
     * Cylinder along Y, X, or Z axis ('X', 'Y', or 'Z').
     */
    fun cylinder(
        cx: Float = 0f, cy: Float = 0f, cz: Float = 0f,
        radiusTop: Float, radiusBottom: Float,
        length: Float,
        axis: Char = 'Y',
        segments: Int = 24,
        capped: Boolean = true
    ): MeshData {
        val b = Builder()
        val half = length * 0.5f
        for (i in 0 until segments) {
            val a0 = (2.0 * PI * i / segments).toFloat()
            val a1 = (2.0 * PI * (i + 1) / segments).toFloat()
            val c0 = cos(a0); val s0 = sin(a0)
            val c1 = cos(a1); val s1 = sin(a1)

            when (axis) {
                'Y' -> {
                    val x0b = cx + radiusBottom * c0; val z0b = cz + radiusBottom * s0
                    val x1b = cx + radiusBottom * c1; val z1b = cz + radiusBottom * s1
                    val x0t = cx + radiusTop * c0; val z0t = cz + radiusTop * s0
                    val x1t = cx + radiusTop * c1; val z1t = cz + radiusTop * s1
                    val y0 = cy - half; val y1 = cy + half
                    b.addQuad(
                        x0b, y0, z0b, c0, 0f, s0,
                        x1b, y0, z1b, c1, 0f, s1,
                        x1t, y1, z1t, c1, 0f, s1,
                        x0t, y1, z0t, c0, 0f, s0
                    )
                    if (capped) {
                        if (radiusTop > 0.001f) b.addTri(cx, y1, cz, 0f, 1f, 0f, x0t, y1, z0t, 0f, 1f, 0f, x1t, y1, z1t, 0f, 1f, 0f)
                        if (radiusBottom > 0.001f) b.addTri(cx, y0, cz, 0f, -1f, 0f, x1b, y0, z1b, 0f, -1f, 0f, x0b, y0, z0b, 0f, -1f, 0f)
                    }
                }
                'X' -> {
                    val y0b = cy + radiusBottom * c0; val z0b = cz + radiusBottom * s0
                    val y1b = cy + radiusBottom * c1; val z1b = cz + radiusBottom * s1
                    val y0t = cy + radiusTop * c0; val z0t = cz + radiusTop * s0
                    val y1t = cy + radiusTop * c1; val z1t = cz + radiusTop * s1
                    val x0 = cx - half; val x1 = cx + half
                    b.addQuad(
                        x0, y0b, z0b, 0f, c0, s0,
                        x0, y1b, z1b, 0f, c1, s1,
                        x1, y1t, z1t, 0f, c1, s1,
                        x1, y0t, z0t, 0f, c0, s0
                    )
                    if (capped) {
                        if (radiusTop > 0.001f) b.addTri(x1, cy, cz, 1f, 0f, 0f, x1, y1t, z1t, 1f, 0f, 0f, x1, y0t, z0t, 1f, 0f, 0f)
                        if (radiusBottom > 0.001f) b.addTri(x0, cy, cz, -1f, 0f, 0f, x0, y0b, z0b, -1f, 0f, 0f, x0, y1b, z1b, -1f, 0f, 0f)
                    }
                }
                else -> { // 'Z'
                    val x0b = cx + radiusBottom * c0; val y0b = cy + radiusBottom * s0
                    val x1b = cx + radiusBottom * c1; val y1b = cy + radiusBottom * s1
                    val x0t = cx + radiusTop * c0; val y0t = cy + radiusTop * s0
                    val x1t = cx + radiusTop * c1; val y1t = cy + radiusTop * s1
                    val z0 = cz - half; val z1 = cz + half
                    b.addQuad(
                        x0b, y0b, z0, c0, s0, 0f,
                        x1b, y1b, z0, c1, s1, 0f,
                        x1t, y1t, z1, c1, s1, 0f,
                        x0t, y0t, z1, c0, s0, 0f
                    )
                    if (capped) {
                        if (radiusTop > 0.001f) b.addTri(cx, cy, z1, 0f, 0f, 1f, x0t, y0t, z1, 0f, 0f, 1f, x1t, y1t, z1, 0f, 0f, 1f)
                        if (radiusBottom > 0.001f) b.addTri(cx, cy, z0, 0f, 0f, -1f, x1b, y1b, z0, 0f, 0f, -1f, x0b, y0b, z0, 0f, 0f, -1f)
                    }
                }
            }
        }
        return b.build()
    }

    fun sphere(
        cx: Float = 0f, cy: Float = 0f, cz: Float = 0f,
        radius: Float,
        stacks: Int = 12,
        slices: Int = 20
    ): MeshData {
        val b = Builder()
        for (i in 0 until stacks) {
            val phi0 = (PI * i / stacks).toFloat()
            val phi1 = (PI * (i + 1) / stacks).toFloat()
            val y0 = cos(phi0); val r0 = sin(phi0)
            val y1 = cos(phi1); val r1 = sin(phi1)
            for (j in 0 until slices) {
                val t0 = (2.0 * PI * j / slices).toFloat()
                val t1 = (2.0 * PI * (j + 1) / slices).toFloat()
                val c0 = cos(t0); val s0 = sin(t0)
                val c1 = cos(t1); val s1 = sin(t1)

                val nx00 = r0 * c0; val ny00 = y0; val nz00 = r0 * s0
                val nx01 = r0 * c1; val ny01 = y0; val nz01 = r0 * s1
                val nx10 = r1 * c0; val ny10 = y1; val nz10 = r1 * s0
                val nx11 = r1 * c1; val ny11 = y1; val nz11 = r1 * s1

                b.addQuad(
                    cx + radius * nx00, cy + radius * ny00, cz + radius * nz00, nx00, ny00, nz00,
                    cx + radius * nx10, cy + radius * ny10, cz + radius * nz10, nx10, ny10, nz10,
                    cx + radius * nx11, cy + radius * ny11, cz + radius * nz11, nx11, ny11, nz11,
                    cx + radius * nx01, cy + radius * ny01, cz + radius * nz01, nx01, ny01, nz01
                )
            }
        }
        return b.build()
    }

    fun impeller(
        cx: Float = 0f, cy: Float = 0f, cz: Float = 0f,
        radius: Float = 0.42f,
        thickness: Float = 0.14f,
        vanes: Int = 6
    ): MeshData {
        val b = Builder()
        // Hub disc
        val hub = cylinder(cx, cy, cz, radius * 0.35f, radius * 0.35f, thickness * 1.4f, axis = 'Z', segments = 20)
        appendRaw(b, hub)
        // Back shroud
        val shroud = cylinder(cx, cy, cz - thickness * 0.35f, radius, radius, 0.04f, axis = 'Z', segments = 24)
        appendRaw(b, shroud)
        // Curved/angled radial vanes
        for (i in 0 until vanes) {
            val angle = (2.0 * PI * i / vanes).toFloat()
            val c = cos(angle); val s = sin(angle)
            val tx = -s * 0.025f; val ty = c * 0.025f
            val rIn = radius * 0.32f; val rOut = radius * 0.96f
            val z0 = cz - thickness * 0.3f; val z1 = cz + thickness * 0.45f
            val xIn = cx + rIn * c; val yIn = cy + rIn * s
            val angleOut = angle + 0.35f
            val xOut = cx + rOut * cos(angleOut); val yOut = cy + rOut * sin(angleOut)
            b.addFlatQuad(
                xIn - tx, yIn - ty, z0,
                xOut - tx, yOut - ty, z0,
                xOut - tx, yOut - ty, z1,
                xIn - tx, yIn - ty, z1
            )
            b.addFlatQuad(
                xOut + tx, yOut + ty, z0,
                xIn + tx, yIn + ty, z0,
                xIn + tx, yIn + ty, z1,
                xOut + tx, yOut + ty, z1
            )
            b.addFlatQuad(
                xIn - tx, yIn - ty, z1,
                xOut - tx, yOut - ty, z1,
                xOut + tx, yOut + ty, z1,
                xIn + tx, yIn + ty, z1
            )
        }
        return b.build()
    }

    fun latticeMast(
        cx: Float = 0f, baseY: Float = -0.6f, cz: Float = 0f,
        bottomHalf: Float = 0.42f, topHalf: Float = 0.16f,
        height: Float = 2.0f, tiers: Int = 5
    ): MeshData {
        val b = Builder()
        val tierH = height / tiers
        val postThick = 0.032f
        for (t in 0 until tiers) {
            val f0 = t.toFloat() / tiers
            val f1 = (t + 1).toFloat() / tiers
            val w0 = bottomHalf + (topHalf - bottomHalf) * f0
            val w1 = bottomHalf + (topHalf - bottomHalf) * f1
            val y0 = baseY + t * tierH
            val y1 = baseY + (t + 1) * tierH
            val corners0 = arrayOf(-w0 to -w0, w0 to -w0, w0 to w0, -w0 to w0)
            val corners1 = arrayOf(-w1 to -w1, w1 to -w1, w1 to w1, -w1 to w1)
            for (c in 0 until 4) {
                val (ax, az) = corners0[c]
                val (bx, bz) = corners1[c]
                addStrut(b, cx + ax, y0, cz + az, cx + bx, y1, cz + bz, postThick)
                val next = (c + 1) % 4
                val (nx0, nz0) = corners0[next]
                val (nx1, nz1) = corners1[next]
                // Horizontal brace
                addStrut(b, cx + bx, y1, cz + bz, cx + nx1, y1, cz + nz1, postThick * 0.7f)
                // Diagonal X brace
                addStrut(b, cx + ax, y0, cz + az, cx + nx1, y1, cz + nz1, postThick * 0.55f)
            }
        }
        return b.build()
    }

    private fun addStrut(
        b: Builder,
        x0: Float, y0: Float, z0: Float,
        x1: Float, y1: Float, z1: Float,
        r: Float
    ) {
        val dx = x1 - x0; val dy = y1 - y0; val dz = z1 - z0
        val mx = (x0 + x1) * 0.5f; val my = (y0 + y1) * 0.5f; val mz = (z0 + z1) * 0.5f
        // Simple 4-sided prism along vector
        val len = sqrt(dx * dx + dy * dy + dz * dz).coerceAtLeast(1e-4f)
        val ux = -dz / len; val uz = dx / len
        val px = if (abs(ux) + abs(uz) < 0.01f) r else ux * r
        val pz = if (abs(ux) + abs(uz) < 0.01f) 0f else uz * r
        b.addFlatQuad(x0 - px, y0, z0 - pz, x0 + px, y0, z0 + pz, x1 + px, y1, z1 + pz, x1 - px, y1, z1 - pz)
        b.addFlatQuad(x0 + px, y0, z0 + pz, x0 - px, y0 - r, z0 - pz, x1 - px, y1 - r, z1 - pz, x1 + px, y1, z1 + pz)
        b.addFlatQuad(x0 - px, y0 - r, z0 - pz, x0 - px, y0, z0 - pz, x1 - px, y1, z1 - pz, x1 - px, y1 - r, z1 - pz)
    }

    private fun abs(v: Float): Float = if (v < 0f) -v else v

    fun combine(vararg meshes: MeshData): MeshData {
        var total = 0
        for (m in meshes) total += m.vertices.size
        val out = FloatArray(total)
        var offset = 0
        for (m in meshes) {
            m.vertices.copyInto(out, offset)
            offset += m.vertices.size
        }
        return MeshData(out, total / 6)
    }

    private fun appendRaw(b: Builder, mesh: MeshData) {
        val v = mesh.vertices
        var i = 0
        while (i + 17 < v.size) {
            b.addTri(
                v[i], v[i + 1], v[i + 2], v[i + 3], v[i + 4], v[i + 5],
                v[i + 6], v[i + 7], v[i + 8], v[i + 9], v[i + 10], v[i + 11],
                v[i + 12], v[i + 13], v[i + 14], v[i + 15], v[i + 16], v[i + 17]
            )
            i += 18
        }
    }
}
