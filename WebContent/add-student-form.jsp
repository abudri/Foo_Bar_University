<!DOCTYPE>

<!--  Video 93, Section 13, Add Student form: https://hexaware.udemy.com/jsp-tutorial/learn/v4/t/lecture/4616914?start=0
 -->
<html>

<head>
	<title>Add Student</title>
	
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
		<h3>Add Student</h3>
		
		<form action="StudentControllerServlet" method="GET"> <!--  send data to the StudentControllerServlet using method GET, calls doGet() method on our Student Controller servlet -->
			<input type="hidden" name="command" value="ADD" />
			<table>
				<tbody>
					<tr>
						<td><label>First Name:</label></td>
						<td><input type="text" name="firstName" /></td>
					</tr>
					<tr>
						<td><label>Last Name:</label></td>
						<td><input type="text" name="lastName" /></td>
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" /></td>
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