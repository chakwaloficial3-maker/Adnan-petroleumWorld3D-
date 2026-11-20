package com.example.engine3d

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.example.model.Model3DType
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.abs
import kotlin.math.sqrt

class PetroleumGlRenderer(
    initialModel: Model3DType,
    private val onLabelsUpdated: (List<ProjectedLabel3D>) -> Unit,
    private val onPartPicked: (Part3D?) -> Unit
) : GLSurfaceView.Renderer {

    @Volatile var currentModelType: Model3DType = initialModel
        private set
    @Volatile private var parts: List<Part3D> = MachinerySceneBuilder.buildScene(initialModel)
    @Volatile private var pendingModelRebuild = false

    @Volatile var yawDeg: Float = 32f
    @Volatile var pitchDeg: Float = 22f
    @Volatile var cameraDistance: Float = 4.2f
    @Volatile var autoRotate: Boolean = true
    @Volatile var viewMode: View3DMode = View3DMode.EXTERNAL
    @Volatile var explodeAmount: Float = 0f
    @Volatile var crossSectionAmount: Float = 0.5f
    @Volatile var selectedComponentId: String? = null
    @Volatile var lowEndMode: Boolean = false

    private var smoothExplode = 0f
    private var viewportWidth = 1
    private var viewportHeight = 1

    private var programId = 0
    private var gridProgramId = 0
    private var gridBuffer: FloatBuffer? = null
    private var gridVertexCount = 0

    private val projMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val vpMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val mvpMatrix = FloatArray(16)
    private val normalMatrix = FloatArray(16)
    private val tempInv = FloatArray(16)

    private var frameCounter = 0

    fun setModel(model: Model3DType) {
        if (model != currentModelType) {
            currentModelType = model
            pendingModelRebuild = true
            selectedComponentId = null
        }
    }

    fun getParts(): List<Part3D> = parts

    fun resetCamera() {
        yawDeg = 32f
        pitchDeg = 22f
        cameraDistance = 4.2f
    }

    fun pickAt(touchX: Float, touchY: Float) {
        if (viewportWidth <= 0 || viewportHeight <= 0) return
        val ndcX = (2f * touchX) / viewportWidth - 1f
        val ndcY = 1f - (2f * touchY) / viewportHeight

        val invVp = FloatArray(16)
        if (!Matrix.invertM(invVp, 0, vpMatrix, 0)) return

        val nearNdc = floatArrayOf(ndcX, ndcY, -1f, 1f)
        val farNdc = floatArrayOf(ndcX, ndcY, 1f, 1f)
        val nearWorld = FloatArray(4)
        val farWorld = FloatArray(4)

        Matrix.multiplyMV(nearWorld, 0, invVp, 0, nearNdc, 0)
        Matrix.multiplyMV(farWorld, 0, invVp, 0, farNdc, 0)
        if (abs(nearWorld[3]) < 1e-5f || abs(farWorld[3]) < 1e-5f) return

        val ox = nearWorld[0] / nearWorld[3]
        val oy = nearWorld[1] / nearWorld[3]
        val oz = nearWorld[2] / nearWorld[3]

        var dx = (farWorld[0] / farWorld[3]) - ox
        var dy = (farWorld[1] / farWorld[3]) - oy
        var dz = (farWorld[2] / farWorld[3]) - oz
        val len = sqrt(dx * dx + dy * dy + dz * dz).coerceAtLeast(1e-5f)
        dx /= len; dy /= len; dz /= len

        var bestPart: Part3D? = null
        var bestT = Float.MAX_VALUE

        val activeExplode = if (viewMode == View3DMode.EXPLODED) explodeAmount.coerceAtLeast(0.7f) else smoothExplode
        for (part in parts) {
            if (viewMode == View3DMode.INTERNAL && part.isOuterShell) continue
            val cx = part.centerLocal[0] + part.explodeVector[0] * activeExplode
            val cy = part.centerLocal[1] + part.explodeVector[1] * activeExplode
            val cz = part.centerLocal[2] + part.explodeVector[2] * activeExplode
            val r = part.boundingRadius * 0.92f

            val mx = ox - cx
            val my = oy - cy
            val mz = oz - cz
            val b = mx * dx + my * dy + mz * dz
            val c = mx * mx + my * my + mz * mz - r * r
            val disc = b * b - c
            if (disc >= 0f) {
                val t = -b - sqrt(disc)
                val hitT = if (t > 0f) t else -b + sqrt(disc)
                if (hitT > 0f && hitT < bestT) {
                    bestT = hitT
                    bestPart = part
                }
            }
        }
        selectedComponentId = bestPart?.componentId
        onPartPicked(bestPart)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.024f, 0.051f, 0.102f, 1.0f)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        programId = createProgram(VERTEX_SHADER, FRAGMENT_SHADER)
        gridProgramId = createProgram(GRID_VERTEX_SHADER, GRID_FRAGMENT_SHADER)
        buildGrid()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        viewportWidth = width.coerceAtLeast(1)
        viewportHeight = height.coerceAtLeast(1)
        GLES20.glViewport(0, 0, viewportWidth, viewportHeight)
        val aspect = viewportWidth.toFloat() / viewportHeight.toFloat()
        Matrix.perspectiveM(projMatrix, 0, 42f, aspect, 0.3f, 35f)
    }

    override fun onDrawFrame(gl: GL10?) {
        if (pendingModelRebuild) {
            parts = MachinerySceneBuilder.buildScene(currentModelType)
            pendingModelRebuild = false
        }

        if (autoRotate) {
            yawDeg = (yawDeg + if (lowEndMode) 0.25f else 0.42f) % 360f
        }

        val targetExplode = when (viewMode) {
            View3DMode.EXPLODED -> explodeAmount.coerceAtLeast(0.15f)
            else -> 0f
        }
        smoothExplode += (targetExplode - smoothExplode) * 0.16f

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        // Camera orbit matrix
        val radYaw = Math.toRadians(yawDeg.toDouble())
        val radPitch = Math.toRadians(pitchDeg.coerceIn(-75f, 80f).toDouble())
        val cosP = kotlin.math.cos(radPitch).toFloat()
        val sinP = kotlin.math.sin(radPitch).toFloat()
        val sinY = kotlin.math.sin(radYaw).toFloat()
        val cosY = kotlin.math.cos(radYaw).toFloat()

        val camX = cameraDistance * cosP * sinY
        val camY = cameraDistance * sinP + 0.15f
        val camZ = cameraDistance * cosP * cosY

        Matrix.setLookAtM(viewMatrix, 0, camX, camY, camZ, 0f, 0.05f, 0f, 0f, 1f, 0f)
        Matrix.multiplyMM(vpMatrix, 0, projMatrix, 0, viewMatrix, 0)

        drawGrid()

        GLES20.glUseProgram(programId)
        val posHandle = GLES20.glGetAttribLocation(programId, "aPosition")
        val normHandle = GLES20.glGetAttribLocation(programId, "aNormal")
        val mvpHandle = GLES20.glGetUniformLocation(programId, "uMvpMatrix")
        val modelHandle = GLES20.glGetUniformLocation(programId, "uModelMatrix")
        val normMatHandle = GLES20.glGetUniformLocation(programId, "uNormalMatrix")
        val colorHandle = GLES20.glGetUniformLocation(programId, "uColor")
        val camPosHandle = GLES20.glGetUniformLocation(programId, "uCameraPos")
        val clipHandle = GLES20.glGetUniformLocation(programId, "uClipX")
        val highlightHandle = GLES20.glGetUniformLocation(programId, "uHighlight")

        GLES20.glUniform3f(camPosHandle, camX, camY, camZ)
        val clipX = if (viewMode == View3DMode.CROSS_SECTION) {
            1.15f - crossSectionAmount * 2.25f
        } else {
            999f
        }
        GLES20.glUniform1f(clipHandle, clipX)

        // Pass 1: Opaque parts
        GLES20.glDisable(GLES20.GL_BLEND)
        GLES20.glDepthMask(true)
        for (part in parts) {
            if (viewMode == View3DMode.INTERNAL && part.isOuterShell) continue
            drawPart(part, 1.0f, posHandle, normHandle, mvpHandle, modelHandle, normMatHandle, colorHandle, highlightHandle)
        }

        // Pass 2: Translucent outer shell in Internal mode
        if (viewMode == View3DMode.INTERNAL) {
            GLES20.glEnable(GLES20.GL_BLEND)
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
            GLES20.glDepthMask(false)
            for (part in parts) {
                if (part.isOuterShell) {
                    drawPart(part, 0.20f, posHandle, normHandle, mvpHandle, modelHandle, normMatHandle, colorHandle, highlightHandle)
                }
            }
            GLES20.glDepthMask(true)
            GLES20.glDisable(GLES20.GL_BLEND)
        }

        frameCounter++
        if (frameCounter % 2 == 0) {
            updateProjectedLabels()
        }
    }

    private fun drawPart(
        part: Part3D,
        alphaOverride: Float,
        posHandle: Int,
        normHandle: Int,
        mvpHandle: Int,
        modelHandle: Int,
        normMatHandle: Int,
        colorHandle: Int,
        highlightHandle: Int
    ) {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(
            modelMatrix, 0,
            part.explodeVector[0] * smoothExplode,
            part.explodeVector[1] * smoothExplode,
            part.explodeVector[2] * smoothExplode
        )
        Matrix.multiplyMM(mvpMatrix, 0, vpMatrix, 0, modelMatrix, 0)

        Matrix.invertM(tempInv, 0, modelMatrix, 0)
        Matrix.transposeM(normalMatrix, 0, tempInv, 0)

        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, mvpMatrix, 0)
        GLES20.glUniformMatrix4fv(modelHandle, 1, false, modelMatrix, 0)
        GLES20.glUniformMatrix4fv(normMatHandle, 1, false, normalMatrix, 0)

        val c = part.colorRgba
        GLES20.glUniform4f(colorHandle, c[0], c[1], c[2], alphaOverride)
        GLES20.glUniform1f(highlightHandle, if (part.componentId == selectedComponentId) 1.0f else 0.0f)

        val buf = part.mesh.buffer
        val stride = 6 * 4
        buf.position(0)
        GLES20.glEnableVertexAttribArray(posHandle)
        GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false, stride, buf)

        buf.position(3)
        GLES20.glEnableVertexAttribArray(normHandle)
        GLES20.glVertexAttribPointer(normHandle, 3, GLES20.GL_FLOAT, false, stride, buf)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, part.mesh.vertexCount)

        GLES20.glDisableVertexAttribArray(posHandle)
        GLES20.glDisableVertexAttribArray(normHandle)
    }

    private fun updateProjectedLabels() {
        val out = ArrayList<ProjectedLabel3D>(parts.size)
        val worldPos = FloatArray(4)
        val clipPos = FloatArray(4)
        for (part in parts) {
            worldPos[0] = part.centerLocal[0] + part.explodeVector[0] * smoothExplode
            worldPos[1] = part.centerLocal[1] + part.explodeVector[1] * smoothExplode
            worldPos[2] = part.centerLocal[2] + part.explodeVector[2] * smoothExplode
            worldPos[3] = 1f

            Matrix.multiplyMV(clipPos, 0, vpMatrix, 0, worldPos, 0)
            if (clipPos[3] > 0.1f) {
                val ndcX = clipPos[0] / clipPos[3]
                val ndcY = clipPos[1] / clipPos[3]
                val ndcZ = clipPos[2] / clipPos[3]
                if (ndcX in -0.95f..0.95f && ndcY in -0.92f..0.92f) {
                    val sx = (ndcX * 0.5f + 0.5f) * viewportWidth
                    val sy = (1f - (ndcY * 0.5f + 0.5f)) * viewportHeight
                    out.add(
                        ProjectedLabel3D(
                            componentId = part.componentId,
                            label = part.label,
                            screenX = sx,
                            screenY = sy,
                            depth = ndcZ,
                            isSelected = part.componentId == selectedComponentId
                        )
                    )
                }
            }
        }
        onLabelsUpdated(out)
    }

    private fun buildGrid() {
        val lines = ArrayList<Float>()
        val y = -1.18f
        val size = 2.6f
        val step = 0.52f
        var p = -size
        while (p <= size + 0.01f) {
            lines.add(p); lines.add(y); lines.add(-size)
            lines.add(p); lines.add(y); lines.add(size)
            lines.add(-size); lines.add(y); lines.add(p)
            lines.add(size); lines.add(y); lines.add(p)
            p += step
        }
        val arr = lines.toFloatArray()
        gridVertexCount = arr.size / 3
        gridBuffer = ByteBuffer.allocateDirect(arr.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .apply { put(arr); position(0) }
    }

    private fun drawGrid() {
        val buf = gridBuffer ?: return
        GLES20.glUseProgram(gridProgramId)
        val posHandle = GLES20.glGetAttribLocation(gridProgramId, "aPosition")
        val mvpHandle = GLES20.glGetUniformLocation(gridProgramId, "uMvpMatrix")
        val colorHandle = GLES20.glGetUniformLocation(gridProgramId, "uColor")

        GLES20.glUniformMatrix4fv(mvpHandle, 1, false, vpMatrix, 0)
        GLES20.glUniform4f(colorHandle, 0.0f, 0.65f, 0.90f, 0.24f)

        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
        buf.position(0)
        GLES20.glEnableVertexAttribArray(posHandle)
        GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false, 12, buf)
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, gridVertexCount)
        GLES20.glDisableVertexAttribArray(posHandle)
        GLES20.glDisable(GLES20.GL_BLEND)
    }

    private fun createProgram(vertexSrc: String, fragmentSrc: String): Int {
        val vs = compileShader(GLES20.GL_VERTEX_SHADER, vertexSrc)
        val fs = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentSrc)
        val prog = GLES20.glCreateProgram()
        GLES20.glAttachShader(prog, vs)
        GLES20.glAttachShader(prog, fs)
        GLES20.glLinkProgram(prog)
        return prog
    }

    private fun compileShader(type: Int, src: String): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, src)
        GLES20.glCompileShader(shader)
        return shader
    }

    companion object {
        private const val VERTEX_SHADER = """
            uniform mat4 uMvpMatrix;
            uniform mat4 uModelMatrix;
            uniform mat4 uNormalMatrix;
            attribute vec4 aPosition;
            attribute vec3 aNormal;
            varying vec3 vWorldPos;
            varying vec3 vNormal;
            void main() {
                vec4 world = uModelMatrix * aPosition;
                vWorldPos = world.xyz;
                vNormal = normalize((uNormalMatrix * vec4(aNormal, 0.0)).xyz);
                gl_Position = uMvpMatrix * aPosition;
            }
        """

        private const val FRAGMENT_SHADER = """
            precision mediump float;
            uniform vec4 uColor;
            uniform vec3 uCameraPos;
            uniform float uClipX;
            uniform float uHighlight;
            varying vec3 vWorldPos;
            varying vec3 vNormal;
            void main() {
                if (vWorldPos.x > uClipX) {
                    discard;
                }
                vec3 N = normalize(vNormal);
                if (!gl_FrontFacing) N = -N;
                vec3 L1 = normalize(vec3(0.6, 0.9, 0.7));
                vec3 L2 = normalize(vec3(-0.7, 0.3, -0.5));
                vec3 V = normalize(uCameraPos - vWorldPos);

                float diff1 = max(dot(N, L1), 0.0);
                float diff2 = max(dot(N, L2), 0.0) * 0.45;
                vec3 H = normalize(L1 + V);
                float spec = pow(max(dot(N, H), 0.0), 36.0) * 0.65;
                float rim = pow(1.0 - max(dot(N, V), 0.0), 2.5) * 0.35;

                vec3 base = uColor.rgb;
                vec3 lit = base * (0.28 + diff1 * 0.72 + diff2) + vec3(0.85, 0.95, 1.0) * spec + vec3(0.0, 0.83, 1.0) * rim;
                if (uHighlight > 0.5) {
                    lit = mix(lit, vec3(1.0, 0.82, 0.28), 0.38) + vec3(0.25, 0.18, 0.0);
                }
                gl_FragColor = vec4(lit, uColor.a);
            }
        """

        private const val GRID_VERTEX_SHADER = """
            uniform mat4 uMvpMatrix;
            attribute vec4 aPosition;
            void main() {
                gl_Position = uMvpMatrix * aPosition;
            }
        """

        private const val GRID_FRAGMENT_SHADER = """
            precision mediump float;
            uniform vec4 uColor;
            void main() {
                gl_FragColor = uColor;
            }
        """
    }
}
