package commands.custom.print;

import commands.Command;
import commands.enums.Checkables;
import commands.enums.Handleables;
import leetcodehelper.Main;

public class PrintTagsNameCMD extends Command {
	
	public PrintTagsNameCMD() {
		// TODO Auto-generated constructor stub
		super(tokens -> {
			Main.getTags().stream().map(tag -> tag.getName()).sorted().forEach(System.out::println);
		}, Checkables.IS_EMPTY, Handleables.CONSUME);
	}
}
