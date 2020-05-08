package agenda.model.repository.classes;

import agenda.model.base.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class AddActivityBBT {

    @Test
    public void test_valid() {
        try {
            Contact contact = new Contact("name", "address", "0712345678");

            RepositoryContactFile repo = new RepositoryContactFile();

            repo.addContact(contact);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void test_invalid() {
        try {
            Contact contact = new Contact("", "", "");

            RepositoryContactFile repo = new RepositoryContactFile();

            repo.addContact(contact);
        } catch (Exception e) {
            assert true;
        }
    }
}
