package utils.objects;

import java.util.List;
import java.util.Objects;

public class Problem {
	private double acRate;
	private boolean paidOnly;
	private String title;
	private int frontendQuestionId;
	private String difficulty;
	private List<Tag> topicTags;
	
	public static class Tag implements Comparable<Tag>{
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
			return name;
		}
		
		@Override
		public boolean equals(Object obj) {
		    if (this == obj) return true;
		    if (obj == null || !(obj instanceof Tag)) return false;
		    Tag other = (Tag) obj;
		    return Objects.equals(name, other.name) && Objects.equals(slug, other.slug);
		}
		
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return Objects.hash(name, slug);
		}

		@Override
		public int compareTo(Tag o) {
			// TODO Auto-generated method stub
			return name.compareToIgnoreCase(o.name);
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

	public int getFrontendQuestionId() {
		return frontendQuestionId;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public List<Tag> getTopicTags() {
		return topicTags;
	}

	public void setAcRate(double acRate) {
		this.acRate = acRate;
	}

	public void setPaidOnly(boolean paidOnly) {
		this.paidOnly = paidOnly;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setFrontendQuestionId(int frontendQuestionId) {
		this.frontendQuestionId = frontendQuestionId;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public void setTopicTags(List<Tag> topicTags) {
		this.topicTags = topicTags;
	}

	@Override
	public String toString() {
		return "Problem [acRate=" + acRate + ", paidOnly=" + paidOnly + ", title=" + title + ", frontendQuestionId="
				+ frontendQuestionId + ", difficulty=" + difficulty + ", topicTags=" + topicTags + "]";
	}

}
