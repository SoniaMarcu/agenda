package agenda.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import agenda.model.base.Activity;
import agenda.model.repository.classes.RepositoryActivityMock;
import agenda.model.repository.interfaces.RepositoryActivity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddActivityTest {
	private Activity act;
	private RepositoryActivity rep;
	
	@Before
	public void setUp() throws Exception {
		rep = new RepositoryActivityMock();
	}
	
	@Test
	public void testCase1()
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try {
			act = new Activity("name1", 
					df.parse("03/20/2013 12:00"), 
					df.parse("03/20/2013 13:00"),
					null,
					"Lunch break");
			rep.addActivity(act);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertTrue(1 == rep.count());
	}
	
	@Test
	public void testCase2()
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try{
			for (Activity a : rep.getActivities())
				rep.removeActivity(a);
			
			act = new Activity("name1",
					df.parse("03/20/2013 12:00"), 
					df.parse("03/20/2013 13:00"),
					null,
					"Lunch break");
			rep.addActivity(act);
			
			act = new Activity("name1",
					df.parse("03/21/2013 12:00"), 
					df.parse("03/21/2013 13:00"),
					null,
					"Lunch break");
			rep.addActivity(act);
		}
		catch(Exception ignore){
		}

		int c = rep.count();
		assertEquals(2, c);
		for (Activity a : rep.getActivities())
			rep.removeActivity(a);
	}
	
	@Test
	public void testCase3()
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try{
			for (Activity a : rep.getActivities())
				rep.removeActivity(a);
			
			act = new Activity("name1",
					df.parse("03/20/2013 12:00"), 
					df.parse("03/20/2013 13:00"),
					null,
					"Lunch break");
			rep.addActivity(act);
			
			act = new Activity("name1",
					df.parse("03/20/2013 12:30"), 
					df.parse("03/20/2013 13:30"),
					null,
					"Lunch break");
			assertFalse(rep.addActivity(act));
		}
		catch(Exception ignore) {
		}
		assertEquals(1, rep.count());
		rep.saveActivities();
		for (Activity a : rep.getActivities())
			rep.removeActivity(a);
	}
	
	@Test
	public void testCase4()
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try{
			for (Activity a : rep.getActivities())
				rep.removeActivity(a);
			
			act = new Activity("name1",
					df.parse("03/20/2013 12:00"), 
					df.parse("03/20/2013 13:00"),
					null,
					"Lunch break");
			rep.addActivity(act);
			
			act = new Activity("name1",
					df.parse("03/20/2013 13:30"), 
					df.parse("03/20/2013 14:00"),
					null,
					"Curs");
			rep.addActivity(act);
			
			act = new Activity("name1",
					df.parse("03/20/2013 13:30"), 
					df.parse("03/20/2013 14:30"),
					null,
					"Lunch break");
			assertFalse(rep.addActivity(act));			
		}
		catch(Exception e){}	
		assertTrue( 2 == rep.count());
		for (Activity a : rep.getActivities())
			rep.removeActivity(a);
	}
	
	@Test
	public void testCase5()
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		try{
			for (Activity a : rep.getActivities())
				rep.removeActivity(a);
			
			act = new Activity("name1",
					df.parse("03/20/2013 12:00"), 
					df.parse("03/20/2013 13:00"),
					null,
					"Lunch break");
			rep.addActivity(act);
			
			assertFalse(rep.addActivity(act));			
		}
		catch(Exception e){}	
		assertTrue( 1 == rep.count());
		for (Activity a : rep.getActivities())
			rep.removeActivity(a);
	}
}
