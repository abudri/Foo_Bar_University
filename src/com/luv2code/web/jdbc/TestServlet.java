// we use this servlet for our database connection.  remember, this is the Controller in the Model-View-Controller

package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// define datasource/connection pool for Resource Injection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource; // handle/refererence to our connection pool

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step 1. setup the printwriter  // Section 10, Video 81
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");	// set content type, keeping it simple withouth HTML right now(video 81)
		// step 2. get a connection to the database
		Connection myConn = null; // setup connection to DB, SQL object, set to null initially, will assign in later stesp
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();  // get connection to database from the connection pool, `dataSource` is synonymous with connection pool
			// step 3. create a SQL statement
			String sql = "SELECT * FROM student"; // setup a little SQL statement here. note his SQL keywords are lowercase, I'm trying mine upper case
			myStmt = myConn.createStatement(); 
			// step 4. execute the SQL statement
			myRs = myStmt.executeQuery(sql); // execute our query now, 'sql', assign result to my result set myRs
			// step 5. process the result set
			while (myRs.next()) {  // while I still have rows to process, go to the next one, .next()
				String email = myRs.getString("email"); // just getting emails here, getting emails from that given column name, "emails" in the DB table with that column name
				out.println(email); // sends the data back out to the browser, so a simple page will print these out right now
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}

}
