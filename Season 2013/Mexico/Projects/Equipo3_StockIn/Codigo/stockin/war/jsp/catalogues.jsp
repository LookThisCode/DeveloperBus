<%@page import="com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<style type="text/css">
/* @group Data Tables */

.ws_data_table {
  border-collapse: collapse;
  border-color: #537DB9;
  border-width: 1px;
  border-style: solid;
  margin-bottom: 15px;
}

.ws_data_table caption {
  text-align: left;
  font-weight: bold;
  margin-top: 10px;
}

.ws_data_table th {
  text-transform: inherit;
  white-space: nowrap;
  background-color: #3366CC;
  color: #FFFFFF;
  padding: 2px 10px 2px 10px;
  border-right: #FFFFFF;
  border-width: 1px;
  border-style: none dotted none none;
}

.ws_data_table td {
  padding: 5px 10px 5px 10px;
  border-right: #cccccc;
  border-width: 1px;
  border-style: none dotted none none;
  vertical-align: top;
}

.ws_data_table tr:nth-child(odd) {
  background-color: #FFFFFF;
}

.ws_data_table tr:nth-child(even) {
  background-color: #E6E6E6;
}
</style>
  <body>

<%
    String catalogueName = request.getParameter("catalogueName");
    
	System.out.println("Valor del catalogo "+catalogueName);
	
	
    pageContext.setAttribute("catalogueName", catalogueName);
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
      pageContext.setAttribute("user", user);
%>
<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } 
%>


<%
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key catalogueKey = KeyFactory.createKey("Catalogue",100);
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
    Query query = new Query("Catalogue").addSort("date", Query.SortDirection.DESCENDING);
    List<Entity> catalogues = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
    if (catalogues.isEmpty()) {
        %>
        <p>Catalogue '${fn:escapeXml(catalogueName)}' no tiene nuevos productos.</p>
        <%
    } else {
        %>
        <p>Productos en cat&aacute;logo '${fn:escapeXml(catalogueName)}'.</p>
        <div style="display: table;">
        <div style="display: table-row;">
           <div style="display: table-cell; border: 1px solid black;">
            <b>Nombre</b>
            </div>
            <div style="display: table-cell; border: 1px solid black;">
            <b>Categoría</b>
            </div>
            <div style="display: table-cell; border: 1px solid black;">
           <b>Descripción</b>
           </div>
           </div>
        <%
        for (Entity catalogue : catalogues) {
        	pageContext.setAttribute("catalogue_name",
                    catalogue.getProperty("cname"));
        	
            pageContext.setAttribute("catalogue_category",
                                     catalogue.getProperty("category"));
            
            pageContext.setAttribute("catalogue_description",
                    catalogue.getProperty("description"));
            
            
%>
            <div style="display: table-row;">
           <div style="display: table-cell; border: 1px solid black;">
            <blockquote>${fn:escapeXml(catalogue_name)}</blockquote>
            </div>
            <div style="display: table-cell; border: 1px solid black;">
            <blockquote>${fn:escapeXml(catalogue_category)}</blockquote>
            </div>
            <div style="display: table-cell; border: 1px solid black;">
           <blockquote>${fn:escapeXml(catalogue_description)}</blockquote>
           </div>
           </div>
            <%
        }
    }
%>
</div>

  </body>
</html>