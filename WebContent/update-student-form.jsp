<!DOCTYPE>

<!--  Video 99, Section 14, add-student-form.jsp Update Student - Prepopulating the HTML Form -->

<html>

<head>
	<title>Update Student</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">  <!--  specific stylesheet for Add Student form -->
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Foo Bar University</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update Student</h3>
		
		<form action="StudentControllerServlet" method="GET"> 		<!--  send data to the StudentControllerServlet using method GET, calls doGet() method on our Student Controller servlet -->
			<input type="hidden" name="command" value="UPDATE" /> 	<!--  sending update command to our StudentControllerServlet -->
			<input type="hidden" name="studentId" value="${THE_STUDENT.id}" />
			<table>
				<tbody>
					<tr>
						<td><label>First Name:</label></td>
						<td><input type="text" name="firstName"
								   value="${THE_STUDENT.firstName}" /></td>  <!--  prepopulates field with the student's first name -->
					</tr>
					<tr>
						<td><label>Last Name:</label></td>
						<td><input type="text" name="lastName"
								   value="${THE_STUDENT.lastName}" /></td> <!--  prepopulates the existing lastname in DB -->
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email"
								   value="${THE_STUDENT.email}" /></td>	<!--  prepopulates the existing email in DB -->
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td> <!--  we have a css style for Save button -->
					</tr>
				</tbody>
			
			</table>
			</form>
			
			<div style="clear: both;"></div>
			<p>
				<a href="StudentControllerServlet"> Back to Student List</a> <!--  a link at bottom of page taking us back to full Students list -->
			</p>
	</div>
</body>
</html>