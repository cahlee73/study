import java.sql.SQLException;

import springbook.user.dao.NUserDao;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class Driver {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new NUserDao();
		
		User user = new User();
		user.setId("ctrl0703");
		user.setName("이창희");
		user.setPassword("32Armyband");
		
		dao.add(user);
		
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		
		System.out.println(user2.getId() + " 조회 성공");
	}

}
