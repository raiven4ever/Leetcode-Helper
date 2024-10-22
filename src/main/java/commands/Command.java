package commands;

import java.util.Map;
import java.util.Queue;

public class Command {
	
	private Executable executable;
	private Checkable checkable;
	private Map<String, Command> subordinates;
	private Handleable handleable;
	
	public Command(Executable executable, Checkable checkable, Handleable handleable) {
		this.executable = executable;
		this.checkable = checkable;
		this.handleable = handleable;
	}
	
	public Command(Command command, Map<String, Command> subordinates) {
		this.executable = command.executable;
		this.checkable = command.checkable;
		this.handleable = command.handleable;
		this.subordinates = subordinates;
	}
	
	
	public void run(Queue<String> tokens) {
		if (!checkable.check(tokens))
			return;
		if (executable != null)
			executable.execute(tokens);
		if (handleable == null)
			return;
		String handle = handleable.handle(tokens);
		boolean hasSubordinates = subordinates != null && !subordinates.isEmpty();
		Command command = hasSubordinates ? subordinates.get(handle) : null;
		if (command != null)
			command.run(tokens);
		else if (hasSubordinates)
			System.out.printf("no command of name %s is found\r\n", handle);
	}

}
