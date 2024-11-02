package leetcodehelper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import commands.Command;
import commands.custom.ExitCommand;
import commands.custom.FIlterCommand;
import commands.custom.MainCommand;
import commands.custom.print.PrintCommand;
import commands.custom.print.PrintProblemsCommand;
import commands.custom.print.PrintTagsNameCMD;
import commands.custom.print.PrintTagsSlugCMD;
import commands.custom.request.problems.RequestProblemsCommand;
import commands.enums.Commands;
import utils.objects.Problem;
import utils.objects.Problem.Tag;

public class Main {
	public static boolean isRunning = true;
	private static List<Problem> problemsList;
	private static Set<Tag> tags;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Command main = new MainCommand()
				.addSubordinate("print", new PrintCommand().setPrint()
						.addSubordinate("problems", new PrintProblemsCommand())
						.addSubordinate("tags", Commands.PARENT.get()
								.addSubordinate("name", new PrintTagsNameCMD())
								.addSubordinate("slug", new PrintTagsSlugCMD())))
				.addSubordinate("request", Commands.PARENT.get()
						.addSubordinate("problems", new RequestProblemsCommand()))
				.addSubordinate("filter", new FIlterCommand().setFilter())
				.addSubordinate("exit", new ExitCommand());
		
		Scanner scanner = new Scanner(System.in);
		main.run("request problems");
		while (isRunning) {
			System.out.print("leetcode helper: ");
			main.run(scanner.nextLine());
		}
		scanner.close();
	}
	
	public static void setTags(Set<Tag> tags) {
		Main.tags = tags;
	}

	public static Set<Tag> getTags() {
		// TODO Auto-generated method stub
		boolean b = tags != null;
		if (!b)
			System.out.println("there are currently no tags in the program.");
		return b ? tags : new HashSet<Tag>();
	}
	
	public static void setProblems(List<Problem> problems) {
		setProblemsList(problems);
	}
	
	public static List<Problem> getProblemsList(){
		boolean b = problemsList != null;
		if (!b)
			System.out.println("there are currently no leetcode problems in the program.");
		return b ? problemsList : new ArrayList<Problem>();
	}

	public static void setProblemsList(List<Problem> problemsList) {
		Main.problemsList = problemsList;
	}


}
