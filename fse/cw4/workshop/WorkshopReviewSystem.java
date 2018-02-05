package workshop;

import java.util.*;

public class WorkshopReviewSystem {

	protected static ArrayList<WorkshopPaper> AllPapers;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//////////////
		//example test data
		//////////////
		AllPapers = new ArrayList<WorkshopPaper>();

		WorkshopPaper p1 = new WorkshopPaper("Paper 1 is great");
		p1.addReview(new WorkshopReview(4,"This paper is pretty good."));
		p1.addReview(new WorkshopReview(3,"This paper is good for the workshop."));
		p1.addReview(new WorkshopReview(2, "This paper is pretty mediocre."));

		AllPapers.add(p1);

		WorkshopPaper p2 = new WorkshopPaper("Paper 2 is my best work");
		p2.addReview(new WorkshopReview(2,"This can hardly be his best work"));
		p2.addReview(new WorkshopReview(1,"Ive read better articles in Hello Magazine"));
		p2.addReview(new WorkshopReview(1,"So painful to read."));

		AllPapers.add(p2);

		//PrintPaperOverview();
		//PrintAPaper(0);
		//PrintAPaper(1);

		System.out.println("What do you want to do?\n O = Overview, P = Add Paper, R = Add Review, [num] = Detail of that paper, X = exit");
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()){
			String s = in.next();
			try{
				if (s.equals("O")) {
					PrintPaperOverview();
				} else if (s.equals("P")){
					AddPaper(in);
				} else if (s.equals("R")) {
					AddReview(in);
				} else if (s.equals("X")) {
					System.out.println("Goodbye!");
					break;
				} else if (Integer.parseInt(s) != -1 ) {
					PrintAPaper(Integer.parseInt(s)-1);
				} else {
					System.out.println("Command not recognised");
				}
			} catch (Exception e) {
				System.out.println("Something went wrong: " + e.toString() + "\n");

			}
			System.out.println("What do you want to do?\n O = Overview, P = Add Paper, R = Add Review, [num] = Detail of that paper, X = exit");
		}
		in.close();

	}

	protected static void AddPaper(Scanner in) {
		System.out.println("What is the title of the paper?");
		in.nextLine(); // to remove read-in bug
		String title = in.nextLine();
		AllPapers.add(new WorkshopPaper(title));
		System.out.println("[Paper added]");
	}

	protected static void AddReview(Scanner in) {
		System.out.println("Which paper do you want to add a review to?");
		int x = in.nextInt();

    // Fix for test 5 - William da Silva psywv
    if ( x < 1 || x > AllPapers.size() ) {
      System.out.println( "Invalid paper selected, please select another" );
      AddReview( in );
      return;
    } else if ( AllPapers.get(x).PReviews[2] != null ) {
      // Fix for test 6 - William da Silva psywv
      System.out.println( "That paper already has 3 reviews, please select another" );
      AddReview( in );
      return;
    }

		System.out.println("What score do you give it?");
		int score = in.nextInt();
		System.out.println("Please enter your review:");
		in.nextLine(); //to remove read-in bug
		String review = in.nextLine();
		WorkshopPaper wp = AllPapers.get(x-1);

		System.out.println("[Review added to Paper " + x + "]");
	}

	protected static void PrintPaperOverview(){
    // Fix for test 1 - William da Silva psywv
    if ( AllPapers.size() < 1 ) {
      System.out.println( "No papers found" );
      return;
    }

		for (int x = 0; x < AllPapers.size(); x++) {
			WorkshopPaper wp = AllPapers.get(x);
			System.out.println((x+1) + ") " + wp.getPTitle()+ " - " + wp.getAverageScore());
		}
	}

	protected static void PrintAPaper(int paperID) {
    if ( paperID < 0 || paperID > AllPapers.size() -1 ) {
      throw new ArrayIndexOutOfBoundsException();
    }

		WorkshopPaper wp = AllPapers.get(paperID);
		System.out.print("\nPaper " + (paperID+1) + " - " + wp.toString());
	}

}
