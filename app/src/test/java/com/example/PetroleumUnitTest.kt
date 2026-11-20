package com.example

import com.example.data.repository.PetroleumContentRepository
import com.example.engine3d.MachinerySceneBuilder
import com.example.model.Model3DType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PetroleumUnitTest {
    private val repo = PetroleumContentRepository()

    @Test
    fun allTwentyCategoriesPresent() {
        assertEquals(20, repo.categories.size)
        val numbers = repo.categories.map { it.number }.sorted()
        assertEquals((1..20).toList(), numbers)
    }

    @Test
    fun allNine3DModelsBuildValidMeshes() {
        Model3DType.entries.forEach { modelType ->
            val parts = MachinerySceneBuilder.buildScene(modelType)
            assertTrue("Expected parts for $modelType", parts.isNotEmpty())
            parts.forEach { part ->
                assertTrue("Expected vertices for ${part.componentId}", part.mesh.vertexCount > 0)
                assertTrue("Expected positive radius for ${part.componentId}", part.boundingRadius > 0f)
            }
        }
    }

    @Test
    fun bilingualSearchMatchesEnglishAndUrdu() {
        val enMatches = repo.searchEquipment("Centrifugal")
        assertTrue(enMatches.any { it.id == "eq_centrifugal_pump" })

        val urMatches = repo.searchEquipment("مڈ پمپ")
        assertTrue(urMatches.any { it.id == "eq_mud_pump" })

        val componentMatches = repo.searchEquipment("Weir Plate")
        assertTrue(componentMatches.any { it.id == "eq_separator_3phase" })
    }
}
