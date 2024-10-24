package utils.objects;

import java.util.List;

public class Problem {
	private double acRate;
	private boolean paidOnly;
	private String title;
	private String frontendQuestionId;
	private String difficulty;
	private List<Tag> topicTags;
	
	public static class Tag{
		private String name;
		private String slug;
		
		public String getName() {
			return name;
		}
		
		public String getSlug() {
			return slug;
		}
		
		@Override
		public String toString() {
			return "Tag [name=" + name + ", slug=" + slug + "]";
		}
		
	}

	public double getAcRate() {
		return acRate;
	}

	public boolean isPaidOnly() {
		return paidOnly;
	}

	public String getTitle() {
		return title;
	}

	public String getFrontendQuestionId() {
		return frontendQuestionId;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public List<Tag> getTopicTags() {
		return topicTags;
	}

	@Override
	public String toString() {
		return "Problem [acRate=" + acRate + ", paidOnly=" + paidOnly + ", title=" + title + ", frontendQuestionId="
				+ frontendQuestionId + ", difficulty=" + difficulty + ", topicTags=" + topicTags + "]";
	}

}
