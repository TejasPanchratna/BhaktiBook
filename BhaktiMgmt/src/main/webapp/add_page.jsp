<%@ page import="mgmt.dao.*, mgmt.entities.*, java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submit New PageNo</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<%
    BhaktiInt dao = new BhaktiImpl(); 
    List<Book> book = dao.getAllBooks();
    Integer bookId = (Integer) session.getAttribute("book_id");
    Integer pageNo = (Integer) session.getAttribute("pageNo");
    String date = (String) session.getAttribute("date");
    String message = (String) session.getAttribute("message");
%>

<div class="container">
    <h1>Submit New PageNo to Database</h1>
    <form action="insertPage" method="get">
        <label for="book_id">Book:</label>
        <select name="book_id" id="book_id">
            <% for(Book b : book) {  %>
                <option value="<%= b.getBookId()%>" <%= (bookId != null && b.getBookId() == bookId) ? "selected" : "" %>> <%= b.getBookName() %></option>
            <% } %>
        </select> <br>
        
        <label for="Date">Date:</label>
        <input type="date" id="date" name="date" value="<%= date != null ? date : "" %>" required><br>
        
        <label for="pageNo">Page No:</label>
        <input type="number" id="pageNo" name="pageNo" value="<%= pageNo != null ? pageNo : "" %>" required><br>
        
        <button type="submit">Insert</button>
    </form>
    <br><a href="index.html" class="btn">Back to Home</a>

    <div id="message">
        <% if (message != null) { %>
            <%= message %>
            <%
                // Clear the message after displaying it
                session.removeAttribute("message");
            %>
        <% } %>
    </div>
</div>

</body>
</html>
