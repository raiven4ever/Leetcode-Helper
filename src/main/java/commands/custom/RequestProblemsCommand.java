package commands.custom;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import commands.Command;
import commands.enums.Checkables;
import commands.enums.Handleables;
import leetcodehelper.Main;

public class RequestProblemsCommand extends Command {
	
	public RequestProblemsCommand() {
		// TODO Auto-generated constructor stub
		super(tokens -> {
			ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
			executorService.scheduleWithFixedDelay(new Runnable() {
				int count = 0;
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(count + (count++ == 1 ? " second has" : " seconds have") + " passed");
				}
			}, 0, 1, TimeUnit.SECONDS);
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
					+ "    \"limit\": 10000,\r\n"
					+ "    \"filters\": {\r\n"
//					+ "	\"paidOnly\": false\r\n"
					+ "    }\r\n"
					+ "  },\r\n"
					+ "  \"operationName\": \"problemsetQuestionList\"\r\n"
					+ "}\r\n"
					+ "";
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest httpRequest = HttpRequest.newBuilder()
					.uri(URI.create("https://leetcode.com/graphql/"))
					.header("Content-Type", "application/json")
	                .header("Accept", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(query.replaceAll("\r\n", ""), StandardCharsets.UTF_8))
					.build();
			try {
				HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
				System.out.println("Status code: " + httpResponse.statusCode());
				Main.problemsString = httpResponse.body();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				executorService.shutdown();
			}
		}, Checkables.IS_EMPTY, Handleables.CONSUME);
	}
	
}
