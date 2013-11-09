<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/start.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/start.menu.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.3.css" type="text/css">
<style type="text/css">
input[type=text] {
	background: white;
	border: 1px solid #DDD;
	border-radius: 5px;
	box-shadow: 0 0 5px #DDD inset;
	color: #666;
	float: left;
	padding: 5px 10px;
	width: 400px;
	outline: none;
	margin: 3px;
}

textarea {
	background: white;
	border: 1px solid #DDD;
	border-radius: 5px;
	box-shadow: 0 0 5px #DDD inset;
	color: #666;
	float: left;
	padding: 5px 10px;
	width: 480px;
	outline: none;
	margin: 3px;
}

div.option:hover{
	cursor: default;
	drop-shadow: none;
}

#formulario {
    border: 2px solid rgb(0, 175, 80);
    border-radius: 5px;
    width: 540px;
    margin-left: auto;
    margin-right: auto;
    padding: 8px;
}
</style>
<script src="/js/jquery-latest.js"></script>
<script src="/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript">
 function validate(form){
	 if(document.getElementById("n").value != "" && 
			 document.getElementById("ct").value !="" &&
			 document.getElementById("c").value !="")
		 return true;
	 return false;
 }
 
 function hookup(){
	 var msg = document.getElementById("mess");
	 if(msg != null)
	 	setTimeout(function(){msg.style.display = "none";}, 3000);
 }
 $(document).ready(function()
		    {
		      $("#catalogtable").tablesorter({
		        sortList: [[0,0]]
		      });
		    }
		  );
</script>
<title>Productos</title>
</head>
<body onload="hookup();">


<%
    UserService userService = UserServiceFactory.getUserService();
%>
	<div id='cssmenu'>
		<ul>
		   <li class='has-sub'><a href='#'><span>${sessionScope.nickname}</span></a>
		      <ul>
		         <li><a href="/"><span>Inicio</span></a></li>
		         <li><a href="<%= userService.createLogoutURL("/") %>"><span>Salir</span></a></li>
		      </ul>
		   </li>
		</ul>
	</div>
	
	<div id="headerlogo"><span style="vertical-align: sub;">STOCK-IN&nbsp;</span>
		<img src="/img/logo.png" width="30" height="30" border="0" style="float: right; margin-top: 3px; margin-right: 2px;">
	</div><br />
<br /><br />

<c:if test="${not empty message}">
	<div class="message" id="mess">
		<div class="cell">
			${message}
		</div>
	</div>
	<br />
</c:if>

<div class="card servicios" style="position: absolute;">
	<img alt="servicios" src="${pageContext.request.contextPath}/img/servicios-icon.png"><br />
	<span>Servicios</span>
</div>

<form action="services.mvc" method="post" id="formulario">
<div class="table">
	<div class="row">
		<div class="cell" style="text-align: right;">
			<b>Catálogo&nbsp;</b>
		</div>
		<div class="cell">
			<div class="styled-select">
				<select name="catalogo" id="n">
					<c:forEach var="cat" items="${cats}">
						<option value="${cat}">${cat}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="cell" style="text-align: right;">
			<b>Servicio&nbsp;</b>
		</div>
		<div class="cell">
			<input type="text" placeholder="Nombre del producto" size="40" name="producto" id="ct" />
		</div>
	</div>
</div>
<br />
<div class="table">
	<div class="row">
		<div class="cell">
			<b>Descripción</b>
		</div>
	</div>
	<div class="row">
		<div class="cell">
			<textarea name="content" placeholder="Descripción de tu producto" rows="3" id="c" cols="60"></textarea>
		</div>
	</div>
	<div class="row">
		<div class="cell" style="text-align: right;">
			<input type="submit" value="Guardar" onclick="return validate(this);" />
		</div>
	</div>
</div>
</form>


<br/> <br/>

<c:if test="${not empty products}">
	 <p align="center"><b>Servicios en cat&aacute;logo</b></p>
        <table id="catalogtable" class="tablesorter ws_data_table" align="center" width="750">
	        <thead>
		        <tr>
		           <th>
		            <b>Catálogo</b>
		            </th>
		            <th>
		            <b>Servicio</b>
		            </th>
		            <th>
		           <b>Descripción</b>
		           </th>
		        </tr>
		    </thead>
		    <tbody>
	        	<c:forEach var="product" items="${products}">
		        	<c:set var="ctl" value="${product}"/>
			        <%
			        			Entity e = (Entity)pageContext.getAttribute("ctl");
			        			
					        	pageContext.setAttribute("catalogue_name",
					        			e.getProperty("catalog"));
					        	
					            pageContext.setAttribute("catalogue_category",
					                                     e.getProperty("cname"));
					            
					            pageContext.setAttribute("catalogue_description",
					                    e.getProperty("description"));
					%>
		            <tr>
		           <td>
		            	${fn:escapeXml(catalogue_name)}
		            </td>
		            <td>
		           		${fn:escapeXml(catalogue_category)}
		            </td>
		            <td>
		           		${fn:escapeXml(catalogue_description)}
		           </td>
		           </tr>
		     </c:forEach>
	        </tbody>
        </table>
        
</c:if>


</body>
</html>