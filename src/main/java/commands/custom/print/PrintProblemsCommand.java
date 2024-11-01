package commands.custom.print;

import java.util.List;

import commands.Command;
import commands.custom.print.util.ProblemsTable;
import commands.enums.Checkables;
import commands.enums.Handleables;
import leetcodehelper.Main;
import utils.objects.Problem;

public class PrintProblemsCommand extends Command {
	
	public PrintProblemsCommand() {
		/* TODO:
		 * make it so that problems are displayed in a table [done]
		 * */
		super(tokens -> {
			List<Problem> problemsList = Main.getProblemsList();
			ProblemsTable problemsTable = new ProblemsTable(problemsList);
			System.out.println(problemsTable.buildTable());  
		}, Checkables.IS_EMPTY, Handleables.CONSUME);
	}

}
