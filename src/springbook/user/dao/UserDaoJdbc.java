package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserDaoJdbc implements UserDao {
	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			return user;
		};
	};

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void add(final User user) {
		this.jdbcTemplate.update(
				"INSERT INTO users (id, name, password, level, login, recommend) VALUES (?, ?, ?, ?, ?, ?)",
				user.getId(), user.getName(), user.getPassword(),
				user.getLevel().intValue(), user.getLogin(), user.getRecommend());
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE ID = ?",
				new Object[] {id}, userMapper);
	}
	
	public void deleteAll() {
		this.jdbcTemplate.update("DELETE FROM users");
	}

	public int getCount() {
		return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users");
	}

	public List<User> getAll() {
		return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id", this.userMapper );
	}

	public void update(User user) {
		this.jdbcTemplate.update("UPDATE users SET"
				+ " name = ?, password = ?, level = ?, login = ?, recommend = ?"
				+ " where id = ?",
				user.getName(), user.getPassword(), user.getLevel().intValue(),
				user.getLogin(), user.getRecommend(), user.getId());
	}
}
