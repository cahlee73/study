package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao {
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://ctrl0703.iptime.org:3306/spring", "root", "32Armyband");
		
		PreparedStatement ps = c.prepareStatement(
				"INSERT INTO users (id, name, password) VALUES (?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection(
				"jdbc:mysql://ctrl0703.iptime.org:3306/spring", "root", "32Armyband");
		
		PreparedStatement ps = c.prepareStatement(
				"SELECT * FROM users WHERE ID = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		c.close();
		
		return user;
	}
}
