import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserDaoTest {	
	@Autowired
	private UserDao dao;
	
	private User user1;
	private User user2;
	private User user3;

	@Before
	public void setUp() {		
		user1 = new User("ctrl0703", "이창희", "32Armyband");
		user2 = new User("si1254", "윤나라", "1q2w3e");
		user3 = new User("bunjin", "박범진", "springno3");
	}

	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
		
		User userget1 = dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		User userget2 = dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
		
		User userget3 = dao.get(user3.getId());
		assertThat(userget3.getName(), is(user3.getName()));
		assertThat(userget3.getPassword(), is(user3.getPassword()));
	}
	
	@Test
	public void count() throws ClassNotFoundException, SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}

	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws ClassNotFoundException, SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
	}
}
