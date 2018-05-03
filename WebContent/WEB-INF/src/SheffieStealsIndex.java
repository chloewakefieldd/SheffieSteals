import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

@SuppressWarnings("serial")
public class SheffieStealsIndex extends HttpServlet {
	
	static ResultSet resultSet;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		readExistingPostsFromDatabase();
		
		res.setStatus(HttpServletResponse.SC_OK);
		res.setHeader("Content-Language", "en");
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"");
		out.println("\"http://www.w3.org/TR/html4/loose.dtd\">");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
		out.println("<title>SheffieSteals</title>");
		out.println("<link rel=\"stylesheet\" type=\'text/css\' href=\""+req.getContextPath()+"/styles.css\"></head>");
		out.println("<body>");
		

		

		// create new post form
		out.println("<form action=\"post_submitted\" method=\"get\" id = \"addPostForm\">");
		out.println("<div id=\"addFormContainer\">");
		out.println("<p>Add a deal!</p><input name=\"text\" type=\"text\" id=\"searchTextbox\">");
		out.println("</div><div>");
		out.println("<input type=\"submit\" value=\"Submit\" id=\"searchButton\" />");
		out.println("</div></form>");
		
		
		
		
		
		// output current database values
		try {
			if(resultSet.next()) {
				out.println("<div id = \"postsTableContainer\"><table id = \"postsTable\">");
				out.println("<tr>"
						+ "<th>id</th>"
						+ "<th>text</th>"
						+ "<th>thumbs_up</th>"
						+ "<th>thumbs_down</th>"
						+ "</tr>");
				do {
					
					out.println("<form action=\"post_submitted\" method=\"post\">");
					
					out.println("<tr>"
							+ "<td>"+resultSet.getString("id")+"<input type=\"hidden\" name=\"id\" value="+resultSet.getString("id")+" /></td>"
							+ "<td>"+resultSet.getString("text")+"</td>"
							+ "<td><button type=\"submit\" name=\"button\" value=\"thumbs_up\">like ("+resultSet.getString("thumbs_up")+")</button></td>"
							+ "<td><button type=\"submit\" name=\"button\" value=\"thumbs_down\">dislike ("+resultSet.getString("thumbs_down")+")</button></td>"
							+ "</tr>");
					
					out.println("</form>");
					
					
				} while(resultSet.next());
				out.println("</table></div>");
			} else {
				out.println("No posts yet!");
			}
		} catch (SQLException e) {e.printStackTrace();}
				
		
		out.println("</body></html>");
	}
	
	public void readExistingPostsFromDatabase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sheffiesteals", "root", "jzhzfpc6");
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			Statement statement = connection.createStatement();
			
			//seedFakePosts(statement);
			
			resultSet = statement.executeQuery("SELECT * FROM POSTS;");
			
		} catch(Exception e) {e.printStackTrace();}
	}

	private void seedFakePosts(Statement statement) {
		try {		
			statement.executeUpdate("INSERT INTO posts(text, thumbs_up, thumbs_down) VALUES ('first deal', 2, 3), ('second deal', 7, 11), ('third deal', 17, 19)");	
		} catch (SQLException e) {e.printStackTrace();}
	}
	
}