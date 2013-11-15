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
        <%
        for (Entity catalogue : catalogues) {
        	pageContext.setAttribute("catalogue_name",
                    catalogue.getProperty("cname"));
        	
            pageContext.setAttribute("catalogue_category",
                                     catalogue.getProperty("category"));
            
            pageContext.setAttribute("catalogue_description",
                    catalogue.getProperty("description"));
            
            
            
            
            
           
            
            if (catalogue.getProperty("user") == null) {
                %>
                <p>An anonymous person wrote:</p>
                <%
            } else {
                pageContext.setAttribute("catalogue_user",
                                         catalogue.getProperty("user"));
                %>
                <p><b>${fn:escapeXml(catalogue_user.nickname)}</b> wrote:</p>
                <%
            }
            %>
           <b>Cat&aacute;logo:</b> <blockquote>${fn:escapeXml(catalogue_name)}</blockquote>
            
           <b>Categor&iacute;a</b> <blockquote>${fn:escapeXml(catalogue_category)}</blockquote>
            
           <b>Descripci&oacute;n</b><blockquote>${fn:escapeXml(catalogue_description)}</blockquote>
            <%
        }
    }
%>


  </body>
</html>