import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

@SuppressWarnings("serial")
public class SheffieSteals extends HttpServlet {
	
	static String text = "";
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter out = res.getWriter();
		
		text = req.getParameter("text");

		if (text != "") {
			addPostToDatabase(text);
			res.sendRedirect(req.getContextPath());
		} else {
			res.sendRedirect(req.getContextPath());
			out.println("No text submitted");
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		
        String button = req.getParameter("button");

        if ("thumbs_up".equals(button)) {
			thumbs_up(Integer.parseInt(req.getParameter("id")));
        } else if ("thumbs_down".equals(button)) {
			thumbs_down(Integer.parseInt(req.getParameter("id")));
        }
        res.setStatus(HttpServletResponse.SC_OK);
		res.setHeader("Content-Language", "en");
		res.sendRedirect(req.getContextPath());
		return;

    }
	
	public void addPostToDatabase(String text) {
		System.out.println("START addPostToDatabase");
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sheffiesteals", "root", "jzhzfpc6");
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO posts(text) VALUES (\""+text+"\")");
			
			
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		System.out.println("END addPostToDatabase");
	}
	
	public void thumbs_up(int id) {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sheffiesteals", "root", "jzhzfpc6");
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT thumbs_up FROM posts WHERE id = "+id+";");
			
			int num_thumbs_up = 0;
			while (resultSet.next()) {
				num_thumbs_up = resultSet.getInt("thumbs_up") + 1;
			}
			
			statement.executeUpdate("UPDATE posts SET thumbs_up = "+num_thumbs_up+" where id = "+id+";");
			
		} catch(Exception exc) {exc.printStackTrace();}
	}
	
	public void thumbs_down(int id) {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sheffiesteals", "root", "jzhzfpc6");
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT thumbs_down FROM posts WHERE id = "+id+";");
			
			int num_thumbs_down = 0;
			while (resultSet.next()) {
				num_thumbs_down = resultSet.getInt("thumbs_down") + 1;
			}
			
			statement.executeUpdate("UPDATE posts SET thumbs_down = "+num_thumbs_down+" where id = "+id+";");
			
		} catch(Exception exc) {exc.printStackTrace();}
	}
	
}