
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- JSTL core tag library import -->


<!DOCTYPE html>

<html>
<head>
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<!--  link to our style.css file in `css` folder -->
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Foo Bar University</h2>
		</div>
	</div>


	<div id="container">
		<div id="content">

			<!--  Add Student button, Section 13, Lecture 92, https://hexaware.udemy.com/jsp-tutorial/learn/v4/t/lecture/4616910?start=2 -->
			<input type="button" value="Add Student"
				onclick="window.location.href='add-student-form.jsp'; return false;"
				class="add-student-button" />
			<!--  tell system what to do on click, show form for adding student if button clicked -->

			<!--  adding search feature, not sure if i have to make button myself -->
			<form action=StudentControllerServlet method="GET">
				<input type="hidden" name="command" value="SEARCH" /> Search
				Student: <input type="text" name="theSearchName" /> <input
					type="submit" value="Search" class="add-student-button" />
			</form>

			<table>
				<tr>
					<th>First Name</th>
					<!--  table headers -->
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
					<!--  Section 14, Lecture 97, for updatind student -->
				</tr>

				<c:forEach var="tempStudent" items="${STUDENT_LIST}">


					<!--  setup link for each student to pre-populate their existing info from the DB into the update form -->
					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD" />
						<!--  load info into DB -->
						<c:param name="studentId" value="${tempStudent.id}" />
					</c:url>

					<!--  setup link to delete a student, `"deleteLink"` is a variable to be used later on in our page -->
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE" />
						<!--  DELETE info into DB -->
						<c:param name="studentId" value="${tempStudent.id}" />
					</c:url>

					<!-- getting each student info and putting them in row using JSP expressions below --!>
					<tr>
						<td> ${tempStudent.firstName} </td>  <!--  note that this is JSP expression language, `.firstName` is using instance variable capitalization I guess, and it calls getFirstName getter method as well -->
					<td>${tempStudent.lastName}</td>
					<td>${tempStudent.email}</td>
					<td><a href="${tempLink}">Update</a> | <a href="${deleteLink}"
						onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">
							Delete</a> <!--  above is javascript, the `onclick` part, and saying if NOT confirming you want to delete student, then `return false` and then that link won't process the rest of delete command -->

					</td>
					<!--  Section 14, Video 97, update student -->
					</tr>
				</c:forEach>

			</table>
		</div>
		<p>
			<a href="StudentControllerServlet"> Back to Student List</a>
			<!--  a link at bottom of page taking us back to full Students list after doing a search. I added this on my own as part of Section 19, Lecture 117, which didn't have a return to list of students feature after a student was found by search -->
		</p>
	</div>
</body>

</html>