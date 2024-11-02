package commands.custom.request.problems;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import commands.Command;
import commands.enums.Handleables;
import leetcodehelper.Main;
import utils.objects.Problem;
import utils.objects.Problem.Tag;

public class RequestProblemsCommand extends Command {
	
	public RequestProblemsCommand() {
		// TODO Auto-generated constructor stub
		super(tokens -> {
			int limit = 100000;
			if (tokens.size() > 1) {
				System.out.println("argument must be no more than one");
				return;
			}
			if (tokens.size() > 0)
				try {
					limit = Integer.valueOf(tokens.peek());
					if (limit < 0)
						throw new IllegalArgumentException();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					System.out.println("argument must be an integer");
					return;
				} catch (IllegalArgumentException e){
					System.out.println("argument must be a positive integer");
					return;
				}
			ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
			executorService.scheduleWithFixedDelay(new LoadingRunnable(), 0, 500, TimeUnit.MILLISECONDS);
			String query = getQuery(limit);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest httpRequest = HttpRequest.newBuilder()
					.uri(URI.create("https://leetcode.com/graphql/"))
					.header("Content-Type", "application/json")
	                .header("Accept", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(query.replaceAll("\r\n", ""), StandardCharsets.UTF_8))
					.build();
			
			HttpResponse<String> httpResponse = null;
			try {
				httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
				
				Gson gson = new Gson();
				Type type = new TypeToken<List<Problem>>() {}.getType();
				String response = httpResponse.body();
				
				List<Problem> fromJson = gson.fromJson(response.substring(
						response.indexOf('['), response.lastIndexOf(']')+1), type);
				fromJson.forEach(problem -> {
					if (problem.getTopicTags().isEmpty())
						problem.setTopicTags(List.of(new Tag("Untagged", "untagged")));
					if (problem.isPaidOnly())
						problem.setTopicTags(List.of(new Tag("Paid", "paid")));
				});
				
				Main.setProblemsList(fromJson);
				System.out.println("problems list obtained successfully");
				
				Main.setTags(new HashSet<Tag>());
				Main.getProblemsList().forEach(problem -> problem.getTopicTags().forEach(tag -> Main.getTags().add(tag)));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				if (e instanceof ConnectException)
					System.out.println("the user must have an internet connection");
				else
					e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				System.out.println("status code: " + httpResponse.statusCode());
				executorService.shutdown();
			}
		}, null, Handleables.CONSUME);
		
	}

	private static String getQuery(int limit) {
		String query = "{\r\n"
				+ "  \"query\": \"\r\n"
				+ "    query problemsetQuestionList($categorySlug: String, $limit: Int, $skip: Int, $filters: QuestionListFilterInput) {\r\n"
				+ "      problemsetQuestionList: questionList(\r\n"
				+ "        categorySlug: $categorySlug\r\n"
				+ "        limit: $limit\r\n"
				+ "        skip: $skip\r\n"
				+ "        filters: $filters\r\n"
				+ "      ) {\r\n"
				+ "        total: totalNum\r\n"
				+ "        questions: data {\r\n"
				+ "          acRate\r\n"
				+ "          difficulty\r\n"
				+ "          frontendQuestionId: questionFrontendId\r\n"
				+ "          paidOnly: isPaidOnly\r\n"
				+ "          title\r\n"
				+ "          titleSlug\r\n"
				+ "          topicTags {\r\n"
				+ "            name\r\n"
				+ "            slug\r\n"
				+ "          }\r\n"
				+ "        }\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  \",\r\n"
				+ "  \"variables\": {\r\n"
				+ "    \"categorySlug\": \"all-code-essentials\",\r\n"
				+ "    \"skip\": 0,\r\n"
				+ "    \"limit\": "+limit+",\r\n"
				+ "    \"filters\": {\r\n"
//					+ "      \"paidOnly\": false\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"operationName\": \"problemsetQuestionList\"\r\n"
				+ "}\r\n"
				+ "";
		return query;
	}
	
}
