//@@author A0130164W
import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.DatabaseStorage;
import core.StorageException;
import models.Task;

/**
 * 
 */

public class DatabaseStorageTest {
    
    DatabaseStorage storage;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        storage = new DatabaseStorage("testDB.db");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    	storage.deleteStorage();
    }
    
    @Test
    public void getNextIdentifierTest() {
        try {
            assertEquals("that the next id returned is always 1 for a new db",
                         storage.getNextAvailableIdentifier(), 1);
        } catch (StorageException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    
    @Test
    public void addTaskTest() {
        try {
            int nextIdentifier = storage.getNextAvailableIdentifier();
            assertEquals("next identifier is available", 1, nextIdentifier);
            Task t = new Task("add an undo and redo command");
            storage.addTask(t);
            assertEquals("next identifier incremented", ++nextIdentifier,
                         storage.getNextAvailableIdentifier());
            storage.addTask(new Task("add one more task"));
            assertEquals("next identifier incremented", ++nextIdentifier,
                    storage.getNextAvailableIdentifier());
            
        } catch (StorageException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void getTasksTest() {
        try {
            assertEquals("that the amount of tasks is 0", 0,
                         storage.getTasks().size());
            storage.addTask(new Task("some random task"));
            assertEquals("amount of tasks increased by 1", 1,
                         storage.getTasks().size());
        } catch (StorageException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void getTaskByIdTest() {
        String[] stubs = new String[10];
        try {
            for (int i = 0; i < 10; i++) {
                stubs[i] = UUID.randomUUID().toString();
                storage.addTask(new Task(stubs[i]));
            }
            
            for (int i = 0; i < 10; i++) {
                assertEquals("that each task is the same", stubs[i],
                             storage.getTaskById(i + 1).getTaskShortName());
            }
        } catch (StorageException e) {
            fail(e.getMessage());
        }
    }
    
    //@@author A0118894N
    @Test
    public void deleteTaskTest() {
        try {
            storage.addTask(new Task("some random task"));

            assertEquals("size is 1", storage.getTasks().size(), 1);
            storage.deleteTask(1);
            assertEquals("size is 0", storage.getTasks().size(), 0);
        } catch (StorageException e) {
            fail(e.getMessage());
        }
    }
    
    //@@author A0144017R
    @Test
    public void deleteAllTest() {
        try {
            for (int i = 0; i < 10; i++) {
                storage.addTask(new Task(UUID.randomUUID().toString()));
            }
            assertEquals("that 10 tasks were added", 10, storage.getTasks().size());
            storage.deleteAllTasks();
            assertEquals("that all 10 were deleted", 0, storage.getTasks().size());
            assertEquals("that the nextId was reset", 1, storage.getNextAvailableIdentifier());
        } catch (StorageException e) {
            fail(e.getMessage());
        }
    }

}
