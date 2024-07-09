<%@ page import="mgmt.dao.*, mgmt.entities.*, java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fetch PageNo</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%
    BhaktiInt dao = new BhaktiImpl();
    List<Book> books = dao.getAllBooks();
    Bhakti fetchedEntry = null;
    String message = null;

    // Check if the request contains the parameters for fetching
    if (request.getParameter("book_id") != null && request.getParameter("pageNo") != null) {
        int bookId = Integer.parseInt(request.getParameter("book_id"));
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        fetchedEntry = dao.getEntryByBookIdAndPageNo(bookId, pageNo);
        if (fetchedEntry == null) {
            message = "No entry found for the given Book ID and Page Number.";
        }
    }
%>

<div class="container">
    <h1>Enter PageNo and BookName</h1>
    <form action="" method="get">
        <label>Book:</label>
        <select name="book_id">
            <% for (Book b : books) { %>
                <option value="<%= b.getBookId() %>"> <%= b.getBookName() %></option>
            <% } %>
        </select> <br>

        <label for="fetchPageNo">Page No:</label>
        <input type="number" id="fetchPageNo" name="pageNo" required><br>

        <button type="submit">Show</button><br>
    </form>
    <br><a href="index.html" class="btn">Back to Home</a>

    <% if (message != null) { %>
        <p><%= message %></p>
    <% } else if (fetchedEntry != null) { %>
        <table>
            <caption>Entry Details</caption>
            <thead>
                <tr>
                    <th>Sno.</th>
                    <th>Book Name</th>
                    <th>Page No</th>
                    <th>Last Fetched At</th>
                    <th>Count</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><%= fetchedEntry.getsNo() %></td>
                    <td><%= fetchedEntry.getBookName() %></td>
                    <td><%= fetchedEntry.getPageNo() %></td>
                    <td><%= fetchedEntry.getLastFetchedAt() %></td>
                    <td><%= fetchedEntry.getCount() %></td>
                </tr>
            </tbody>
        </table>
    <% } %>
</div>
</body>
</html>
