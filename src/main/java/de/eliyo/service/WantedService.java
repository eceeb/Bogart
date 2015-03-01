package de.eliyo.service;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import de.eliyo.entity.Wanted;

@Stateless
public class WantedService {

	private Connection con;
	private List<Wanted> searches = new ArrayList<Wanted>(); 
	
	public List<Wanted> getAllWantedForInterval(int minutes) throws Exception {

		searches.clear();
		Statement stmt = getConnection().createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from wanted where found=false and interval=" + minutes + " order by url");

		return mappElements(resultSet);
	}
	
	private List<Wanted> mappElements(ResultSet resultSet) throws Exception {
		
		while (resultSet.next()) {
			String url = resultSet.getString("url");
			String searchString = resultSet.getString("seek");
			String from = resultSet.getString("email");
			searches.add(new Wanted(url, searchString, from));
		}
		return searches;
	}

	public void markAsFound(Wanted w) throws Exception {
		
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		String s = "update wanted set found=true where email='"	+ w.getEmail()
				+ "' and seek='" + w.getSeek() + "' and url='" + w.getUrl() + "'";
		
		statement.executeUpdate(s);
	}

	private Connection getConnection() throws Exception {
		try {
			if (con != null && !con.isClosed())
				return con;
		} catch (SQLException e) {
			System.err.println("error db connection trying to get a new one...");
			System.err.println(e.getStackTrace());
		}
		URI dbUri = new URI(System.getenv("HEROKU_POSTGRESQL_BROWN_URL"));
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
		con = DriverManager.getConnection(dbUrl, username, password);
		return con;
	}
}
