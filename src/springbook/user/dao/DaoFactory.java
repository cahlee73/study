package springbook.user.dao;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

	public UserDao userDao() {
		return new UserDao(connectionMaker());
	}
	
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}

}
