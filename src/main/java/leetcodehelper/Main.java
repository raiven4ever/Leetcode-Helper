package leetcodehelper;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import commands.Command;
import commands.custom.RequestProblemsCommand;
import commands.custom.print.PrintCommand;
import commands.custom.print.PrintProblemsCommand;
import commands.custom.MainCommand;
import commands.enums.Checkables;
import commands.enums.Commands;
import commands.enums.Handleables;
import utils.MapBuilder;
import utils.TokenStringBuilder;
import utils.objects.Problem;

public class Main {
	public static boolean isRunning = true;
	public static List<Problem> problemsList;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Command main = new Command(new MainCommand(), new MapBuilder(new HashMap<String, Command>())
				.add("print", new PrintCommand().setPrint().setSubordinates(new MapBuilder(new HashMap<String, Command>())
						.add("problems", new PrintProblemsCommand())
						.build()))
				.add("exit", new Command(tokens -> isRunning = false, Checkables.IS_EMPTY, Handleables.CONSUME))
				.add("request", Commands.PARENT.get().setSubordinates(new MapBuilder(new HashMap<String, Command>())
						.add("problems", new RequestProblemsCommand())
						.build()))
				.build());
		Scanner scanner = new Scanner(System.in);
		while (isRunning) {
			System.out.print("leetcode helper: ");
			main.run(new ArrayDeque<String>(List.of(scanner.nextLine().split(" "))));
		}
		scanner.close();
	}

}
