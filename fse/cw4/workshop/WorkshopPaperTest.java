package workshop;

import static org.junit.Assert.*;
import org.junit.*;

public class WorkshopPaperTest {

  WorkshopPaper p1;

  @Before
  public void setup() {
     WorkshopPaper p1 = new WorkshopPaper();
  }
  public void testDefConstructor() {
    // 1
    assertTrue( p1.PTitle.equals( "New Paper" ) );
  }

  @Test
  public void testConstructor() {
    // 2
    assertFalse( p1.PTitle.equals( "" ) );
    // TODO array checking
    // 3
    WorkshopPaper p2 = new WorkshopPaper( "Investigation" );
    assertTrue( p1.PTitle.equals( "Investigation" ) );
  }

  @Test
  public void testGetPTitle() {
    // 4
    assertTrue( p1.getPTitle().equals( "Investigation" ) );
  }

  @Test
  public void testSetPTitle() {
    // 5
    p1.setPTitle( "Investigation" );
    assertTrue( p1.PTitle.equals( "Investigation" ) );

    // 6
    WorkshopPaper p2 = new WorkshopPaper();
    p2.setPTitle( "" );
    assertFalse( p2.PTitle.equals( "" ) );
  }

  @Test
  public void testAddReview() {

    WorkshopReview r = new WorkshopReview( 1, "Bob Jones" );
    for ( int i=0; i < 3; i++ ) {
      p1.addReview( r );
    }

    boolean thrown = false;
    try {
      p1.addReview( r );
    } catch( Exception e ) {
      thrown = true;
    }
    assertTrue( thrown );

    WorkshopPaper p2 = new WorkshopPaper();
    p2.addReview( r );

    int count = 0;
    for ( Object obj : p2.PReviews ) {
      if ( obj != null ) count++;
    }
    assertEquals( count, 1 );

  }

  @Test
  public void testGetAverageScore() {

    boolean thrown = false;
    try {
      p1.getAverageScore();
    } catch( Exception e ) {
      thrown = true;
    }
    assertTrue( thrown );

    WorkshopReview r = new WorkshopReview( 5, "Bob Jones" );
    p1.addReview( r );
    assertEquals( 5, p1.getAverageScore(), .1 );

    r = new WorkshopReview( 4, "Bob Jones" );
    p1.addReview( r );
    assertEquals( 4.5, p1.getAverageScore(), .1 );

    r = new WorkshopReview( 3, "Bob Jones" );
    p1.addReview( r );
    assertEquals( 4, p1.getAverageScore(), .1 );

  }

  @Test
  public void testToString() {
    assertEquals( "'New Paper'\nNo reviews", p1.toString() );

    WorkshopReview r = new WorkshopReview( 5, "Bob Jones" );
    for ( int i = 0; i < 3; i++ ) {
      p1.addReview( r );
    }

    assertEquals( "'New Paper'\nAverage Score = *****\n\nReview 1:", p1.toString() );


  }
}
