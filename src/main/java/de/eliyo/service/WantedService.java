package de.eliyo.service;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import de.eliyo.entity.Wanted;

@Stateless
public class WantedService {

	
	public List<Wanted> getAllWantedForInterval(int minutes) throws Exception {
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		ResultSet resultSet = stmt.executeQuery("select * from wanted where found=false and interval=" + minutes + " order by url");
		
		con.close();
		return mappElements(resultSet);
	}
	
	private List<Wanted> mappElements(ResultSet resultSet) throws Exception {
		List<Wanted> searches = new ArrayList<Wanted>();
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
		URI dbUri = new URI(System.getenv("HEROKU_POSTGRESQL_BROWN_URL"));
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
		return DriverManager.getConnection(dbUrl, username, password);
	}
}
