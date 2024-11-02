package commands.enums;

import commands.Command;

public enum Commands{
	PARENT(new Command(null, Checkables.IS_NOT_EMPTY, Handleables.CONSUME)),
	NULL(new Command(null, null, null));
	
	private Command command;

	Commands(Command command) {
		this.command = command;
	}

	public Command get() {
		return command;
	}
	
}
