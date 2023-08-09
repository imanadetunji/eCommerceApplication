package com.udacity.examples.Testing;

import org.junit.*;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HelperTest {

    @Test
    public void test() {
        assertEquals(3,3);
    }

    @Test
    public void verifyGetCount() {
        List<String> empNames = Arrays.asList("iman", "udacity");
        final long actual = Helper.getCount(empNames);
        assertEquals(2, actual);
    }

    @Ignore
    @Test
    public void verifyGetStats() {
        List<Integer> yrsOfExperience = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        List<Integer> expectedList = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        IntSummaryStatistics statistics = Helper.getStats(yrsOfExperience);
        assertEquals(19, statistics.getMax());
        assertEquals(expectedList, yrsOfExperience);
    }

    @Test
    public void compareArrays() {
        int[] years = {10, 14, 2};
        int[] expectedYears = {10, 14, 2};
        assertArrayEquals(expectedYears, years);
    }

    @Test
    public void verifyGetMergedList() {
        List<String> empNames = Arrays.asList("sareeta", "", "john","");
        String mergedString = Helper.getMergedList(empNames);
        assertEquals("sareeta, john", mergedString);
    }
    @Before
    public void init() {
        System.out.println("runs before each method");
    }
    @BeforeClass
    public static void setup() {
        System.out.println("runs before each class");
    }
    @After
    public void initEnd() {
        System.out.println("runs after each method");
    }
    @AfterClass
    public static void teardown() {
        System.out.println("runs after each class");
    }
}
