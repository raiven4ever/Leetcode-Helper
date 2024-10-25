package commands.custom.print.util;

import java.util.List;
import java.util.function.ToIntFunction;

import utils.objects.Problem;
import utils.objects.Problem.Tag;

public class ProblemsTable {

	private List<Problem> problemsList;

	public ProblemsTable(List<Problem> problemsList) {
		this.problemsList = problemsList;
	}
	
	public String buildTable() {
		StringBuilder builder = new StringBuilder();
		int acRateWidth = "Acceptance Rate".length();
		int paidOnlyWidth = "Paid".length();
		int titleWidth = "Title".length();
		int frontendQuestionIdWidth = "ID".length();
		int difficultyWidth = "Difficulty".length();
		int topicTagsWidth = "Tags".length();
		for (Problem problem : problemsList) {
			acRateWidth = Math.max(acRateWidth, Double.toString(problem.getAcRate()).length());
			paidOnlyWidth = Math.max(paidOnlyWidth, Boolean.toString(problem.isPaidOnly()).length());
			titleWidth = Math.max(titleWidth, problem.getTitle().length());
			frontendQuestionIdWidth = Math.max(frontendQuestionIdWidth, Integer.toString(problem.getFrontendQuestionId()).length());
			difficultyWidth = Math.max(difficultyWidth, problem.getDifficulty().length());
			String string = problem.getTopicTags().toString();
			String substring = string.substring(string.indexOf('[') + 1, string.indexOf(']'));
			topicTagsWidth = Math.max(topicTagsWidth, substring.length());
		}
		String format = "| %-" + frontendQuestionIdWidth + "s " +
                "| %-" + titleWidth + "s " +
                "| %-" + difficultyWidth + "s " +
                "| %-" + paidOnlyWidth + "s " +
                "| %-" + acRateWidth + "s " +
                "| %-" + topicTagsWidth + "s |\n";

		builder.append(String.format(format,
		        "ID",
		        "Title",
		        "Difficulty",
		        "Paid",
		        "Acceptance Rate",
		        "Tags"));

		int totalWidth = frontendQuestionIdWidth + titleWidth + difficultyWidth + paidOnlyWidth + acRateWidth + topicTagsWidth + 19; // 19 accounts for extra characters in the format (| and spaces)
		builder.append("-".repeat(totalWidth) + "\n");

		for (Problem problem : problemsList) {
		    String topicTagsString = problem.getTopicTags().toString();
		    String topicTagsSubstring = topicTagsString.substring(topicTagsString.indexOf('[') + 1, topicTagsString.indexOf(']'));
		
		    builder.append(String.format(format,
		            problem.getFrontendQuestionId(),
		            problem.getTitle(),
		            problem.getDifficulty(),
		            problem.isPaidOnly(),
		            problem.getAcRate(),
		            topicTagsSubstring));
		}

		return builder.toString();
	}

}
