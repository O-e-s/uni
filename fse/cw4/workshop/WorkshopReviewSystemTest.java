package workshop;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.*;

public class WorkshopReviewSystemTest {

	WorkshopReviewSystem s1;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private ByteArrayInputStream inContent;
  private InputStream stdin = System.in;
  private PrintStream stdout = System.out;

  @Before
  public void setup() {
    WorkshopReviewSystem.AllPapers = new ArrayList<WorkshopPaper>();
    System.setOut( new PrintStream( outContent ) );
  }

  @After
  public void cleanUpStreams() {
    System.setOut( null );
    System.setIn( null );
  }

  @Test
  public void testAddReview() {
    WorkshopReviewSystem.AllPapers.add( new WorkshopPaper() );

    System.setIn( new ByteArrayInputStream( "1\n5\nGreat amazing wonderful\n".getBytes() ) );
    WorkshopReviewSystem.AddReview( new Scanner( System.in ) );
    assertEquals( "Which paper do you want to add a review to?" +System.lineSeparator()
      +"What score do you give it?"+ System.lineSeparator()
      +"Please enter your review:"+ System.lineSeparator()
      +"[Review added to Paper 1]"+ System.lineSeparator(),
      outContent.toString() );

    WorkshopPaper p = WorkshopReviewSystem.AllPapers.get(0);
    int i = 0;
    for ( WorkshopReview r : p.PReviews ) {
      if ( r != null ) i++;
    }
    assertEquals( 1, i );
    assertEquals( 5.0, p.PReviews[0].RScore, .1 );
    assertEquals( "Great amazing wonderful", p.PReviews[0].RReview );

    outContent.reset();
    System.setIn( new ByteArrayInputStream( "2\n1\n5\nGreat amazing wonderful\n".getBytes() ) );
    WorkshopReviewSystem.AddReview( new Scanner( System.in ) );
    assertEquals( "Which paper do you want to add a review to?" +System.lineSeparator()
      +"Invalid paper selected, please select another"+ System.lineSeparator()
      +"Which paper do you want to add a review to?" +System.lineSeparator()
      +"What score do you give it?"+ System.lineSeparator()
      +"Please enter your review:"+ System.lineSeparator()
      +"[Review added to Paper 1]"+ System.lineSeparator(),
      outContent.toString() );

    outContent.reset();
    p.addReview( new WorkshopReview() );
    WorkshopReviewSystem.AllPapers.add( new WorkshopPaper() );
    System.setIn( new ByteArrayInputStream( "1\n2\n5\nGreat amazing wonderful\n".getBytes() ) );
    WorkshopReviewSystem.AddReview( new Scanner( System.in ) );
    assertEquals( "Which paper do you want to add a review to?" +System.lineSeparator()
      +"That paper already has 3 reviews, please select another"+ System.lineSeparator()
      +"Which paper do you want to add a review to?" +System.lineSeparator()
      +"What score do you give it?"+ System.lineSeparator()
      +"Please enter your review:"+ System.lineSeparator()
      +"[Review added to Paper 1]"+ System.lineSeparator(),
      outContent.toString() );
  }

  @Test
  public void testPrintPaperOverview() {

    WorkshopReviewSystem.PrintPaperOverview();
    assertEquals( "No papers found\n", outContent.toString() );

    outContent.reset();
    WorkshopPaper p1 = new WorkshopPaper();
    WorkshopReviewSystem.AllPapers.add( p1 );
    WorkshopReviewSystem.PrintPaperOverview();
    assertEquals( "1) " +p1.PTitle +" - 0.0"+ System.lineSeparator(),
      outContent.toString() );
  }

  @Test
  public void testPrintAPaper() {
    for ( int i : new int[]{1, -1} ) {
      try {
        WorkshopReviewSystem.PrintAPaper( i );
        throw new Exception();

      } catch( Exception e ) {
        assertEquals( ArrayIndexOutOfBoundsException.class, e.getClass() );
      }

    }

    try {
      WorkshopReviewSystem.PrintAPaper( 1 );
      throw new Exception();

    } catch( Exception e ) {
      assertEquals( ArrayIndexOutOfBoundsException.class, e.getClass() );
    }

    outContent.reset();
    WorkshopPaper p1 = new WorkshopPaper();
    WorkshopReviewSystem.AllPapers.add( p1 );
    WorkshopReviewSystem.PrintAPaper( 0 );
    assertEquals( "\nPaper 1 - "+ p1.toString(), outContent.toString() );

  }

}
