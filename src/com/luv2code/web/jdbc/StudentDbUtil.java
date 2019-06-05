// From Section 11, Video 85 or so, https://hexaware.udemy.com/jsp-tutorial/learn/v4/t/lecture/4616834?start=13

package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource; // setup a reference to a datasoruce, I guess that will be our DB

	public StudentDbUtil(DataSource theDataSource) {
		this.dataSource = theDataSource;
	}

	public List<Student> getStudents() throws Exception { // get a list of students from the database and then return it
		List<Student> students = new ArrayList<>();

		Connection myConn = null; // jdbc object setup
		Statement myStmt = null; // jdbc object setup
		ResultSet myRs = null; // jdbc object setup

		try {
			// get a connection to DB
			myConn = dataSource.getConnection(); // gives me a connection to the database, to the connection pool that
													// is
			// create a SQL statement
			String sql = "SELECT * FROM student ORDER BY last_name"; // note the table name is `student` here, i had
																		// issues when it was spelled `students`, argh

			myStmt = myConn.createStatement(); // create a sql statement object
			// execute a query
			myRs = myStmt.executeQuery(sql); // EXECUTE THE QUERY, pasing in the sql string we made earlier. gives
												// result set
			// process result set, go through loop, perform some oeprations and build our
			// list
			while (myRs.next()) { // looping trough result set, perform some operations
				// retrieve data from a result set row
				int id = myRs.getInt("id"); // `id` is the actual column name in the DB
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				// create a new student object based on that info, using the info we just
				// grabbed above to create a new Student object instance
				Student tempStudent = new Student(id, firstName, lastName, email);

				// add it to the list of students
				students.add(tempStudent); // adding th

			}
			return students; // return list of students to whoever called for that list
		}

		finally { // close JDBC objects
			close(myConn, myStmt, myRs);

		}

	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close(); // doesn't really close the DB connection, but puts that connection back in DB
								// connection pool for use by another client
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	public void addStudent(Student theStudent) throws Exception { // method for adding a student into the database
		// does nothign now, video 94. used in our servlet file. add actual code in
		// video 95
		// create SQL statement for insert
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get our DB connection
			myConn = dataSource.getConnection();
			// create SQL for the insert
			String sql_statement = "insert into student " + "(first_name, last_name, email) " + "values (?, ?, ?)";
			myStmt = myConn.prepareStatement(sql_statement);
			// set up SQL parameters values for the student
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			// execute the SQL insert
			myStmt.execute();

		} finally {
			// clean up my JDBC objects
			close(myConn, myStmt, null); // null value here is in place of Result Set, since we don't have one, we just
											// put `null`
		}

	}

	public Student getStudent(String theStudentId) throws Exception { // Video 98, prepopuatling the student form part 1
		Student theStudent = null;

		Connection myConn = null; // JDBC object
		PreparedStatement myStmt = null; // JDBC object
		ResultSet myRs = null; // JDBC object
		int studentId;

		try {
			// convert student ID to an int
			studentId = Integer.parseInt(theStudentId); // here theStudentId being the argument/parameter passed into
														// this getStudents() method we are makign
			// get connection to a Database
			myConn = dataSource.getConnection();
			// create a SQL statement to get the selected student
			String sql = "SELECT * FROM student WHERE id=?"; // the `?` will be filled in further below
			// create prepared statement based on that sql
			myStmt = myConn.prepareStatement(sql);
			// set parameters so it knows which student id to get (this fills in the `?`
			// part further up?)
			myStmt.setInt(1, studentId);
			// execute statement query and get back a result set
			myRs = myStmt.executeQuery();
			// once i have result set, need to process that result set and retrieve data
			// from that actual result set row, for a given student
			if (myRs.next()) { // basically if we have more rows to process, next(), go through it that is
				String firstName = myRs.getString("first_name"); // remember, `first_name`, `last_name`, `email` are the
																	// actual column names from the DB
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				// use the studentId during construction... using the above info, we can createa
				// new student object
				theStudent = new Student(studentId, firstName, lastName, email); // student Id was passed as an argument
																					// into our method we are making
																					// here, the other arguments we
																					// grabbed from DB based on the
																					// given Id
			} else {
				throw new Exception("Could not find student ID: " + studentId);
			}
		} finally {
			// clean up the JDBC objects
			close(myConn, myStmt, myRs);
		}

		return theStudent;
	}

	public void updateStudent(Student theStudent) throws Exception {
		// does nothing yet, video 100 is where we created this. 101 video is the one write actual code of this method

		Connection myConn = null; // create our JDBC objects
		PreparedStatement myStmt = null; // create our JDBC objects

		try {
			// get DB connection
			myConn = dataSource.getConnection();
			// create a SQL update statement
			String sql = "UPDATE student SET first_name=?, last_name=?, email=? WHERE id=?"; // the question marks are
																								// placeholders for
																								// those prepared
																								// statements, where ID
																								// is what is passed in
			// preapare that statement
			myStmt = myConn.prepareStatement(sql);
			// set the params for that statement. params start at position 1, and go from
			// left to right. this is going to fill in the `?`s in the string`sql` statement
			// just above, by grabbing the first, last names, email, and ID of given
			// student, theStudent
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			// execute the statement that will actually update the DB with the student's new
			// info
			myStmt.execute();
		}
		finally {
			close(myConn, myStmt, null); // null is for having no result set.

		}
	}

	public void deleteStudent(String theStudentId) throws Exception {  // Section 15, Lecture 104
		// setup JDBC objects
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert student id to an integer from it's current String form
			int studentId = Integer.parseInt(theStudentId);  // we convert the String argument `theStudentId` passed into this method and convert it to integer `studentId`
			// connect to our database
			myConn = dataSource.getConnection();
			// create the sql statement to delete the student
			String sql = "DELETE FROM student WHERE id=?";
			// prepare that sql statement
			myStmt = myConn.prepareStatement(sql);
			// set the parameters 
			myStmt.setInt(1, studentId);
			// execute the sql statement which actually performs the DELETE on the database
			myStmt.execute();
		}
		finally {
			// clean up our JDBC objects we used/created before above in this method
			close(myConn, myStmt, null);  // no result set so null for the last argument
		}
		
	}

	public List<Student> searchStudents(String theSearchName) throws Exception { // Section 19, Lecture 117, add search feature
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		// int studentId;  // note this line was in the solution code, but doesn't appear to be in use in this method
		
		try {
			myConn = dataSource.getConnection(); // connect to the database
			// only search by name if theSearchName is not empty, meaning they didn't just hit return in an empty text search box
			if (theSearchName != null && theSearchName.trim().length() > 0) {
				String sql = "SELECT * FROM student WHERE lower(first_name) like ? or lower(last_name) like ?";
				myStmt = myConn.prepareStatement(sql);	// create the prepared statement
				// set parameters
				String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%"; // i guess the "%" is like a wild card, anything before or after?
				myStmt.setString(1, theSearchNameLike);
				myStmt.setString(2, theSearchNameLike);
			}
			else {
				String sql = "SELECT * FROM student ORDER BY last_name"; // sql for getting all the students, since search string was null or empty(length was 0)
				myStmt = myConn.prepareStatement(sql);
			}
			
			myRs = myStmt.executeQuery(); // execute the query statement
			
			// retrieve data from result set rows
			while (myRs.next()) {
				// retrieve data from the result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// create a student object based upon above retrieved data
				Student tempStudent = new Student(id, firstName, lastName, email);
				
				students.add(tempStudent); // add it to the list of students
			}
			return students;
			
		}
		finally { // clean up the jdbc objects
			close(myConn, myStmt, myRs);	
		}
	}

}
