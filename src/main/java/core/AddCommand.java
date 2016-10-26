package core;

import java.util.Arrays;
import java.util.logging.Logger;

import models.Task;
//@@author A0130164W
public class AddCommand extends BaseCommand {
    
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());
    
    private static final String helpString = "add <task>";

    public AddCommand(StorageBackend storage) {
        super(storage);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getHelpString() {
        return helpString;
    }

    @Override
    public void execute(String[] args) throws CommandException {
        if (args.length == 0) {
            throw new CommandException("empty arguments");
        }
        //System.out.println(args.length);
        try {
            String desc = String.join(" ", Arrays.copyOfRange(args, 0, args.length));
            Task t = new Task(desc);
            logger.info("trying to add task: " + desc);
            storage.addTask(t);
        } catch (StorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new CommandException(e.getMessage());
        }
    }

}
