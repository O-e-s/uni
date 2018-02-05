package workshop;

public class WorkshopReview {
	protected int RScore;
	protected String RReview;
	protected static String[] ROutputs = new String[]{"*","**","***","****","*****"};

	public WorkshopReview() {
		RScore = 0;
		RReview = "No Review";
	}

	public WorkshopReview(int rScore, String rReview) {
		RScore = rScore;
		RReview = rReview;
	}
	public int getRScore() {
		return RScore;
	}
	public void setRScore(int rScore) {
		RScore = rScore;
	}
	public String getRReview() {
		return RReview;
	}
	public void setRReview(String rReview) {
		RReview = rReview;
	}

	@Override
	public String toString() {
		return "Score = " + ROutputs[RScore] + "\n Review: " + RReview + "\n";
	}

}
