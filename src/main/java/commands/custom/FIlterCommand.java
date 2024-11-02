package commands.custom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import commands.Command;
import commands.custom.print.util.ProblemsTable;
import commands.enums.Checkables;
import commands.enums.Handleables;
import leetcodehelper.Main;
import utils.objects.Problem;
import utils.objects.Problem.Tag;

public class FIlterCommand extends Command {
	
	private HashMap<String, Command> pseudosubordinates;
	private Set<Tag> excludedSet;
	private Set<Tag> includedSet;
	
	public FIlterCommand() {
		// TODO Auto-generated constructor stub
		super(null, Checkables.IS_NOT_EMPTY, Handleables.CONSUME);
	}

	public FIlterCommand setFilter() {
		// TODO Auto-generated method stub
		setExecutable(tokens -> {
			//ingredients;
			pseudosubordinates = new HashMap<String, Command>();
			excludedSet = new HashSet<Tag>();
			includedSet = new HashSet<Tag>();
			
			//setup;
			buildSubordinates();
			Set<Tag> tags = Main.getTags();
			tags.stream().forEach(tag -> excludedSet.add(tag));
			
			//return conditions
			if (tags.isEmpty())
				return;
			if (getSubordinates().containsKey(tokens.peek()))//if direct subordinate is called
				return;
			
			//handle the tag filters
			while (true) {
				if (!hasTagFilters(tokens))
					break;
				String slug = tokens.poll();
				Command command = pseudosubordinates.get(slug);
				if (command == null) {
					System.out.println(String.format("no such tag %s exists", slug));
					continue;
				}
				command.run(tokens);
			}
			
			//handle the case where no option is mentioned
			if (tokens.isEmpty())
				tokens.add(">print-table");
			
			//handle the options
			while (true) {
				if (tokens.isEmpty())
					break;
				String token = tokens.poll();
				switch (token) {
				case ">print-table":{
					List<Problem> list = Main.getProblemsList()
						.stream()
						.filter(problem -> !problem.getTopicTags()
								.stream()
								.anyMatch(tag -> excludedSet.contains(tag)))
						.filter(problem -> problem.getTopicTags().containsAll(includedSet))
						.toList();
					ProblemsTable table = new ProblemsTable(list);
					System.out.println(table.buildTable());
				}break;
				default:{
					System.out.println(String.format("no such option %s exists", token));
				}
				}
			}

		});
		return this;
	}

	private boolean hasTagFilters(Queue<String> tokens) {
		// TODO Auto-generated method stub
		return tokens.stream().anyMatch(token -> token.startsWith("+") || token.startsWith("-") || token.startsWith("?"));
	}

	private void buildSubordinates() {
		//add unofficial subordinates
		Set<Tag> tags = Main.getTags();
		tags.stream().forEach(tag -> {
			String slug = tag.getSlug();
			
			pseudosubordinates.put("-"+slug, new Command(tokens -> {
				excludedSet.add(tag);
			}, null, Handleables.PRESERVE));
			
			pseudosubordinates.put("+"+slug, new Command(tokens -> {
				if (excludedSet.contains(tag))
					excludedSet.remove(tag);
				includedSet.add(tag);
			}, null, Handleables.PRESERVE));
			
			pseudosubordinates.put("?"+slug, new Command(tokens -> {
				excludedSet.remove(tag);
			}, null, Handleables.PRESERVE));
		});;
		
		pseudosubordinates.put("-all", new Command(tokens -> {
			excludedSet.addAll(tags);
		}, null, Handleables.PRESERVE));
		
		pseudosubordinates.put("+all", new Command(tokens -> {
			excludedSet.removeAll(tags);
			includedSet.addAll(tags);
		}, null, Handleables.PRESERVE));
		
		pseudosubordinates.put("?all", new Command(tokens -> {
			excludedSet.removeAll(tags);
		}, null, Handleables.PRESERVE));
		
		//the only direct subordinate of this command
		addSubordinate("help", new Command(tokens -> {
			Main
			.getTags()
			.stream()
			.sorted((tag1, tag2) -> {
				long count1 = Main.getProblemsList()
					.stream()
					.filter(problem -> problem.getTopicTags().contains(tag1))
					.count();
				
				long count2 = Main.getProblemsList()
					.stream()
					.filter(problem -> problem.getTopicTags().contains(tag2))
					.count();
				
				return Long.compare(count1, count2);
			})
			.forEach(tag -> {
				long count = Main.getProblemsList()
					.stream()
					.filter(problem -> problem.getTopicTags().contains(tag))
					.count();
				System.out.println(String.format("%s - present in %d %s", tag.getSlug(), count, (count > 1 ? "problems" : "problem")));
			});
		}, Checkables.IS_EMPTY, Handleables.CONSUME));
	}

}
