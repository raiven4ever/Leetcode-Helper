package commands.custom.print;

import static leetcodehelper.Main.problemsList;

import commands.Command;
import commands.custom.print.util.ProblemsTable;
import commands.enums.Checkables;
import commands.enums.Handleables;

public class PrintProblemsCommand extends Command {
	
	public PrintProblemsCommand() {
		/* TODO:
		 * make it so that problems are displayed in a table [done]
		 * */
		super(tokens -> {
			if (problemsList == null) {
				System.out.println("the problems list is currently null.");
				return;
			}
			ProblemsTable problemsTable = new ProblemsTable(problemsList);
			System.out.println(problemsTable.buildTable());  
		}, Checkables.IS_EMPTY, Handleables.CONSUME);
	}

}
