package commands.custom.print;

import static leetcodehelper.Main.problemsList;

import commands.Command;
import commands.enums.Checkables;
import commands.enums.Handleables;

public class PrintProblemsCommand extends Command {
	
	public PrintProblemsCommand() {
		// TODO Auto-generated constructor stub
		super(tokens -> {
			if (problemsList == null) {
				System.out.println("the problems list is currently null.");
				return;
			}
			problemsList.forEach(System.out::println);
		}, Checkables.IS_EMPTY, Handleables.CONSUME);
	}

}
