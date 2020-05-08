package agenda.integration;

import agenda.exceptions.InvalidFormatException;
import agenda.model.base.Activity;
import agenda.model.base.Contact;
import agenda.model.repository.classes.RepositoryActivityFile;
import agenda.model.repository.classes.RepositoryActivityMock;
import agenda.model.repository.classes.RepositoryContactFile;
import agenda.model.repository.classes.RepositoryContactMock;
import agenda.model.repository.interfaces.RepositoryActivity;
import agenda.model.repository.interfaces.RepositoryContact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class BigBangIntegration {

    private Contact con;
    private RepositoryContact contactRepMock;
    private Activity act;
    private RepositoryActivity activityRepMock;
    private RepositoryActivity activityRep;
    private RepositoryContact contactRep;

    @Before
    public void setUp() throws Exception {
        contactRepMock = new RepositoryContactMock();
        activityRepMock = new RepositoryActivityMock();

        contactRep = new RepositoryContactFile();
        activityRep = new RepositoryActivityFile(contactRep);
    }

    @Test
    public void addContactUnitTest() {

        for (Contact c : contactRepMock.getContacts())
            contactRepMock.removeContact(c);

        try {
            con = new Contact("name", "address1", "+071122334455");
            contactRepMock.addContact(con);
        } catch (InvalidFormatException e) {
            assert false;
        }
        int n = contactRepMock.count();
        if (n == 1)
            if (con.equals(contactRepMock.getContacts().get(0))) assertTrue(true);
            else assert false;
        else assert false;
    }

    @Test
    public void addActivityUnitTest() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            act = new Activity("name1",
                    df.parse("03/20/2013 12:00"),
                    df.parse("03/20/2013 13:00"),
                    null,
                    "Lunch break");
            activityRepMock.addActivity(act);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(1, activityRepMock.count());
    }

    @Test
    public void generateReportUnitTest() {
        for (Activity act : activityRepMock.getActivities())
            activityRepMock.removeActivity(act);

        Calendar c = Calendar.getInstance();
        c.set(2013, 3 - 1, 20, 12, 00);
        Date start = c.getTime();

        c.set(2013, 3 - 1, 20, 12, 30);
        Date end = c.getTime();

        Activity act = new Activity("name1", start, end,
                new LinkedList<Contact>(), "description2");

        activityRepMock.addActivity(act);

        c.set(2013, 3 - 1, 20);

        List<Activity> result = activityRepMock.activitiesOnDate("name1", c.getTime());
        assertEquals(1, result.size());
    }

    @Test
    public void bigBangIntegrationTest() {
        for (Activity a : activityRep.getActivities())
            activityRep.removeActivity(a);

        for (Contact c : contactRep.getContacts())
            contactRep.removeContact(c);

        boolean part1 = false, part2 = false;
        int n = contactRep.count();

        try {
            Contact c = new Contact("name", "address1", "+071122334455");
            contactRep.addContact(c);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        if (n + 1 == contactRep.count())
            part1 = true;
        Activity act = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            act = new Activity("name1", df.parse("03/20/2013 12:00"),
                    df.parse("03/20/2013 13:00"), null, "Lunch break");
            activityRep.addActivity(act);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (activityRep.getActivities().get(0).equals(act) && activityRep.count() == 1)
            part2 = true;

        Calendar c = Calendar.getInstance();
        c.set(2013, 3 - 1, 20);

        List<Activity> result = activityRep.activitiesOnDate("name1", c.getTime());
        assertTrue(result.size() == 1 && result.get(0).equals(act) && part1
                && part2);
    }

}


