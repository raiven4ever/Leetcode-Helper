package commands.custom;

import commands.Command;
import commands.enums.Checkables;
import commands.enums.Handleables;
import leetcodehelper.Main;

public class ExitCommand extends Command {

	public ExitCommand() {
		// TODO Auto-generated constructor stub
		super(tokens -> {
			Main.isRunning = false;
			System.out.println("exiting program...");
		}, Checkables.IS_EMPTY, Handleables.CONSUME);
	}
}
