package workshop;

public class WorkshopPaper {
	protected String PTitle;
	protected WorkshopReview[] PReviews;
	protected static String[] ROutputs = new String[]{"","*","**","***","****","*****"};

	public WorkshopPaper() {
		PTitle = "New Paper";
		PReviews = new WorkshopReview[3];
		PReviews[0] = null;
		PReviews[1] = null;
		PReviews[2] = null;
	}
	public WorkshopPaper(String pTitle) {
		setPTitle( pTitle );
		//^calls setPTitle to perform validation. A.B. psyae2
		PReviews = new WorkshopReview[3];
		PReviews[0] = null;
		PReviews[1] = null;
		PReviews[2] = null;
	}

	public String getPTitle() {
		return PTitle;
	}

	public void setPTitle(String pTitle) {
		// Validation fix by Aydin Erdem psyae2

		if (pTitle.equals(""))
			pTitle = "New Paper";
		else {
		PTitle = pTitle;
		}
		// ^^If string is empty with spaces it is
		// replaced with a default title. A.B.(authored by) psyae2
	}

	public void addReview(WorkshopReview nReview) {
		if (PReviews[0] == null)
			PReviews[0] = nReview;
		else {
			if (PReviews[1] == null)
				PReviews[1] = nReview;
			else {
				if (PReviews[2] == null)
					PReviews[2] = nReview;
				else {
					System.out.println("Three reviews alreadly stored, cannot add anymore\n");
          throw new ArrayIndexOutOfBoundsException();
//				Handle case where user tries to add an extra review when there are alreadly three
				}
			}
		}

	}

	public float getAverageScore(){
		float AvgScore = 0;
		int numReviews = 0;
		if (PReviews[0] != null){
			AvgScore += PReviews[0].getRScore();
			numReviews++;
		}
		if (PReviews[1] != null){
			AvgScore += PReviews[1].getRScore();
			numReviews++;
		}
		if (PReviews[2] != null){
			AvgScore += PReviews[2].getRScore();
			//^PReviews[1] changed to PReviews[2] meaning
			//the average value is now correctly calculated
			//A.B. Aydin Erdem psyae2
			numReviews++;
		}
		if (numReviews == 0)
			return 0;
		//^prevents division by zero in a situation where there
		//are no reviews stored. A.B. Aydin Erdem psyae2
		AvgScore = AvgScore/numReviews;
		return AvgScore;
	}

	public String toString(){
		String myoutput = "";
		myoutput = "Average Score = " + ROutputs[Math.round(getAverageScore())] + "\n\n";

		for ( int i = 0 ; i < 3 ; i ++ ) {
      if ( PReviews[i] == null ) continue;
			myoutput += "Review " + i + ":\n" + PReviews[i].toString() + "\n";
		}
		//^Check if objects are null before trying to print their values
		//A.B. Aydin Erdem psyae2 and William Silva
		return myoutput;
	}

}
