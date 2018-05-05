package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;

public class UserDao {
	private JdbcContext jdbcContext;
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	
	public void add(final User user) throws ClassNotFoundException, SQLException {
		this.jdbcContext.executeSql(
				"INSERT INTO users (id, name, password) VALUES (?, ?, ?)",
				user.getId(),
				user.getName(),
				user.getPassword());
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"SELECT * FROM users WHERE ID = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		User user = null;
		if(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		
		if(user == null) throw new EmptyResultDataAccessException(1);
		
		rs.close();
		ps.close();
		c.close();
		
		return user;
	}
	
	public void deleteAll() throws ClassNotFoundException, SQLException {
		this.jdbcContext.executeSql("DELETE FROM users");
	}

	public int getCount() throws ClassNotFoundException, SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			c = dataSource.getConnection();
			ps = c.prepareStatement("SELECT COUNT(*) FROM users");
			rs = ps.executeQuery();
			rs.next();
			
			result = rs.getInt(1);
		} catch(SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch(SQLException e) {
					
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch(SQLException e) {
					
				}
			}
			if (c != null) {
				try {
					c.close();
				} catch(SQLException e) {
					
				}
			}
		} 
		
		return result;
	}
}
