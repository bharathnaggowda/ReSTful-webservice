<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" layout:decorator="layout/sitelayout">
<head>
<title th:text="#{app.name}">List of Users</title>
<link rel="stylesheet" th:href="@{/webjars/bootstrap/3.1.1/css/bootstrap.min.css}" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
<link rel="stylesheet" th:href="@{/webjars/bootstrap/3.1.1/css/bootstrap-theme.css}" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.css" />
<link rel="stylesheet" th:href="@{/css/application.css}" href="../../static/css/application.css" />


</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-xs-12">
				<h1 class="well well-small">User Details</h1>
			</div>
		</div>
		<div>
		<label for="message" class="col-xs-2">${message}</label>
		</div>
		<div layout:fragment="content">
			<div class="row">
				<div class="col-xs-12">
					<form action="/SpringMVC/homepage/" method="POST" class="form-horizontal">
					
					
					<div class="form-group">
							<label for="name" class="col-xs-2">Id</label>
							<div class="col-xs-6">
								<input class="form-control" placeholder="Id" name="id" value="${id}"/>
							</div>
							
						</div>
						<div class="form-group">
							<label for="name" class="col-xs-2">Firstname</label>
							<div class="col-xs-6">
								<input class="form-control" id="name" placeholder="Firstname" name="firstname" value="${firstname}"/>
							</div>
							
						</div>
						<div class="form-group">
							<label for="name" class="col-xs-2">Lastname</label>
							<div class="col-xs-6">
								<input class="form-control" placeholder="Lastname" name="lastname" value="${lastname}"/>
							</div>
							</div>
						<div class="form-group">
							<label for="name" class="col-xs-2">Email</label>
							<div class="col-xs-6">
								<input class="form-control" placeholder="Email" name="email" value="${email}"/>
							</div>
							
						</div>
						<div class="form-group">
							<label for="name" class="col-xs-2">Address</label>
							<div class="col-xs-6">
								<input class="form-control" placeholder="Address" name="address" value="${address}"/>
							</div>
							
						</div>
						<div class="form-group">
							<label for="name" class="col-xs-2">Organization</label>
							<div class="col-xs-6">
								<input class="form-control" placeholder="Organization" name="organization" value="${organization}"/>
							</div>
							
						</div>
						<div class="form-group">
							<label for="address" class="col-xs-2">AboutMyself</label>
							<div class="col-xs-6">
								<input class="form-control" placeholder="AboutMyself" name="aboutMyself" value="${aboutMyself}"/>
							</div>
									
						</div>	
						
							
						
						<button type="submit" name="cre" class="btn btn-default">Create</button>
													
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>