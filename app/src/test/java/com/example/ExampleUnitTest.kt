package com.example

import com.example.data.local.SeedData
import com.example.ui.components.JAVA_KEYWORDS
import com.example.ui.components.getUserLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testSeedDataCurriculumIntegrity() {
        val lessons = SeedData.initialLessons
        assertFalse("Lessons should not be empty", lessons.isEmpty())
        assertTrue("Should have at least 10 comprehensive lessons", lessons.size >= 10)

        // Verify each lesson has non-empty code, expected output, and Bengali theory
        for (lesson in lessons) {
            assertNotNull(lesson.id)
            assertTrue(lesson.title.isNotBlank())
            assertTrue(lesson.titleBn.isNotBlank())
            assertTrue(lesson.codeExample.isNotBlank())
            assertTrue(lesson.expectedOutput.isNotBlank())
            assertTrue(lesson.theoryContentBn.isNotBlank())
            assertTrue(lesson.estimatedMinutes > 0)
            assertTrue(lesson.xpReward > 0)
        }
    }

    @Test
    fun testUserLevelProgression() {
        val (level1Bn, _) = getUserLevel(50)
        assertEquals("Java শিক্ষানবিস", level1Bn)

        val (level2Bn, _) = getUserLevel(300)
        assertEquals("জাভা কোডার", level2Bn)

        val (level3Bn, _) = getUserLevel(800)
        assertEquals("OOP স্পেশালিস্ট", level3Bn)

        val (level4Bn, _) = getUserLevel(1500)
        assertEquals("জাভা ডেভেলপার", level4Bn)

        val (level5Bn, _) = getUserLevel(2500)
        assertEquals("জাভা মাস্টার 🏆", level5Bn)
    }

    @Test
    fun testJavaSyntaxKeywords() {
        assertTrue(JAVA_KEYWORDS.contains("class"))
        assertTrue(JAVA_KEYWORDS.contains("public"))
        assertTrue(JAVA_KEYWORDS.contains("static"))
        assertTrue(JAVA_KEYWORDS.contains("extends"))
        assertTrue(JAVA_KEYWORDS.contains("implements"))
        assertTrue(JAVA_KEYWORDS.contains("interface"))
    }

    @Test
    fun testQuizzesAndChallenges() {
        val quizzes = SeedData.sampleQuizzes
        assertFalse(quizzes.isEmpty())
        for (q in quizzes) {
            assertTrue(q.options.size >= 3)
            assertTrue(q.correctIndex in q.options.indices)
            assertTrue(q.explanationBn.isNotBlank())
        }

        val challenges = SeedData.sampleChallenges
        assertFalse(challenges.isEmpty())
        for (ch in challenges) {
            assertTrue(ch.starterCode.isNotBlank())
            assertTrue(ch.expectedOutput.isNotBlank())
        }
    }
}
