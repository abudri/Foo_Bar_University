package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	private StudentDbUtil studentDbUtil; // reference to student DB Util, its a instance variable here(?), a "data member"
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource; // resource injection item, our connection pool, and we pass it in later
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our student DB util .. and pass in our data source object
		try {
			studentDbUtil = new StudentDbUtil(dataSource);  // assigning `studentDbUtil` on this line, after declaring it before, and passing in dataSource as an argument, creating our studentDbUtil
		}
		catch (Exception exc) {  // if any exceptions with creating the database Util, maybe the DB wasn't up and running or some permission problem, so through exception
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// list students in MVC fashion, so like get the data, set the attribute , then use request dispatcher and send it over to the JSP
		try {
			// read the command parameter
			String theCommand = request.getParameter("command");
			// if the command is empty, default to listing the students as an action
			if (theCommand == null) {
				theCommand = "LIST";  // if not given a command, default is list students
			}
			// route it - theCommand - to the appropriate piece of code
			switch (theCommand) {
			
			case "LIST": listStudents(request, response);
						 break;
			case "ADD": addStudent(request, response);
						break;
			case "LOAD": loadStudent(request, response);
						break;
			case "UPDATE": updateStudent(request, response); // Section 14, Lecture 100, making update to student
						break;
			case "DELETE": deleteStudent(request, response); // Section 15, Lecture 104, deleting a student
						break;
			case "SEARCH": searchStudents(request, response); // Section 19, Lecture 117, adding search function/feature
						break;
			default: listStudents(request, response); // default action in case they send over a bad command we don't understand		
						
			}
			
		listStudents(request, response);
		}
		catch (Exception exc) {
			throw new ServletException(exc);  // exception will appear in browser
		}
	}
		

	private void searchStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {  // Section 19, Lecture 117, Search Function
		// read searched name from from data
		String theSearchName = request.getParameter("theSearchName"); // remember, `"theSearchName"` is part of the html page info
		// search students from the DbUtil
		List <Student> students = studentDbUtil.searchStudents(theSearchName);
		// add students to the request
		request.setAttribute("STUDENT_LIST", students);
		// send the info to the .jsp page, the "view"
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request,  response);
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception { // this is called upon the the case statement above in the doGet() method
		// read student id from the form data
		String theStudentId = request.getParameter("studentId");
		// delete the student from the database
		studentDbUtil.deleteStudent(theStudentId);
		// send the user back to the list-students.jsp page with the updated page
		listStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception { // Section 14, Lecture 100, updating a student method
		// read student info from the form data
		int id = Integer.parseInt(request.getParameter("studentId")); // here `"studentId"` is that hidden form field in the update-student-form.jsp page
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		// create a new student object base on that form data
		Student theStudent = new Student(id, firstName, lastName, email);
		// perform the update on the database
		studentDbUtil.updateStudent(theStudent);
		// send the user back to the list-students.jsp page, "List Students" or whatever. created this listStudents() method long ago
		listStudents(request, response); 
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student id from form data
		String theStudentId = request.getParameter("studentId");
		// get student id from the database (db util)
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		// place that student in the request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		// send that data to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from the form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		// based on that info, create a new Student object/instance
		Student theStudent = new Student(firstName, lastName, email);
		// add that student to the database
		studentDbUtil.addStudent(theStudent);
		// send them back to the main page, get a list of all the students again to see the student we just added/created
		listStudents(request, response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception { // Video 86
		// get students from Db Util
		List<Student> students = studentDbUtil.getStudents();   // did this low So we did all the low level JDBC work and now we're simply calling that method and we're getting a list of objects.

		// add those students to the request object as an attribute
		request.setAttribute("STUDENT_LIST", students);
		
		// send it to the JSP (view) page using the request dispatcher
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
		
	}

}
