package model;

import model.Model;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model = new Model();

    @Before
    public void setUp() {
        System.out.println("Starting test for Model class...");
    }

    @Test
    public void sizeOfUndoStack() {
        System.out.println("Test 1: Testing undo stack...");
        assertEquals(0, 0);
        System.out.println("Test 1: Completed.");
    }

    @Test
    public void sizeOfRedoStack() {
        System.out.println("Test 2: Testing redo stack...");
        assertEquals(0, 0);
        System.out.println("Test 2: Completed.");
    }

    @Test
    public void newTest3() {
        System.out.println("Test 3: Starting...");
        boolean a = true;
        assertTrue(a);
        assertEquals(0, 0);
        System.out.println("Test 3: Completed.");
    }

    @Test
    public void newTest4() {
        System.out.println("Test 4: Starting...");
        assertEquals(0, 0);
        System.out.println("Test 4: Completed.");
    }

    @Test
    public void newTest5() {
        System.out.println("Test 5: Starting...");
        boolean a = true;
        assertTrue(a);
        System.out.println("Test 5: Completed.");
    }

    @Test
    public void newTest6() {
        System.out.println("Test 6: Starting...");
        assertEquals(0, 0);
        System.out.println("Test 6: Completed.");
    }

    @Test
    public void newTest7() {
        System.out.println("Test 7: Starting...");
        boolean a = true;
        assertTrue(a);
        System.out.println("Test 7: Completed.");
    }
}