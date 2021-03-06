//@@author A0108515L
package core;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import controllers.TaskController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import models.Task;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class TaskRuby extends Application {
    
    private static final Logger logger = Logger.getLogger(TaskRuby.class.getName());
    
    public TaskRuby() {
        /*
         * TODO
         * test item list
         */

        logger.info("main class starting up");
        storage = new DatabaseStorage("");
        parser = new Parser(this);
        commandList = new HashMap<String, BaseCommand>();
        
        commandList.put("add", new AddCommand(storage, this));
        commandList.put("delete", new DeleteCommand(storage, this));
        commandList.put("exit", new ExitCommand(storage));

        commandList.put("clear", new ClearCommand(storage));
        commandList.put("hide", new HideCommand(storage, this));
        commandList.put("list", new ListCommand(storage, this));
        commandList.put("undo", new UndoCommand(storage, this));
        commandList.put("help", new HelpCommand(storage));
        commandList.put("update", new UpdateCommand(storage, this));
        commandList.put("edit", new EditCommand(storage, this));
        commandList.put("find", new FindCommand(storage, this));
        commandList.put("load", new LoadTestData(storage, this));
        lastCommand = "";

        try {
            System.out.println(storage.getNextAvailableIdentifier());
        } catch (StorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        testTasks = FXCollections.observableArrayList(

                );
    }
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Task> testTasks;
    private DatabaseStorage storage;
    private Parser parser;
    private String lastCommand;
    
    private boolean isVisible = true;
    
    public void toggleVisible() {
        this.isVisible = !this.isVisible;
    }
    
    public boolean getTaskListVisibility() {
        return this.isVisible;
    }
    
    public Parser getParser() {
        return parser;
    }
    
    public DatabaseStorage getStorage() {
        return storage;
    }
    
    private HashMap<String, BaseCommand> commandList;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }
    
    public ObservableList<Task> getTasks() {
        return testTasks;
    }
    
    public HashMap<String, BaseCommand> getAvailableCommands() {
        return commandList;
    }
    
    public String getLastCommand() {
    	return lastCommand;
    }
    
    public void setLastCommand(String input) {
    	logger.info("last commadn: " + input);
    	lastCommand = input;
    }

    //@@author A0118894N
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TaskRuby");
        initRootLayout();
        initTaskOverviewLayout();
    }
    
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaskRuby.class.getClassLoader().getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void initTaskOverviewLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TaskRuby.class.getClassLoader().getResource("TaskRubyIdea.fxml"));
            AnchorPane taskOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(taskOverview);
            
            TaskController controller = loader.getController();
            controller.setMain(this);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
