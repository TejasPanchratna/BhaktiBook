<%@ page import="mgmt.dao.BhaktiInt, mgmt.dao.BhaktiImpl, mgmt.entities.Bhakti, mgmt.entities.Book, java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bhakti Suggestions</title>
    <link rel="stylesheet" href="css/style.css">
    <script>
        // Auto-submit form on page load if bookId is not present
        window.onload = function() {
            const urlParams = new URLSearchParams(window.location.search);
            if (!urlParams.has('bookId')) {
                document.getElementById('bookForm').submit();
            }
        };
    </script>
</head>
<body>
    <div class="container">
        <h1>Bhakti Suggestions</h1>
        <form id="bookForm" method="get">
            <label for="book">Select Book:</label>
            <select id="book" name="bookId" onchange="this.form.submit()">
                <% 
                    BhaktiInt bhaktiDao = new BhaktiImpl();
                    List<Book> books = bhaktiDao.getAllBooks();
                    String selectedBookId = request.getParameter("bookId");
                    for(Book book : books) { 
                        boolean isSelected = selectedBookId != null && selectedBookId.equals(String.valueOf(book.getBookId()));
                %>
                    <option value="<%= book.getBookId() %>" <%= isSelected ? "selected" : "" %>><%= book.getBookName() %></option>
                <% } %>
            </select>
        </form>

        <%
            List<Bhakti> bhaktiList = null;
            if (selectedBookId != null && !selectedBookId.isEmpty()) {
                int bookId = Integer.parseInt(selectedBookId);
                bhaktiList = bhaktiDao.getTopEntriesByBook(bookId);
            }
        %>

        <% if (bhaktiList != null && !bhaktiList.isEmpty()) { %>
        <table>
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
                <% for (Bhakti b : bhaktiList) { %>
                <tr>
                    <td><%= b.getsNo() %></td>
                    <td><%= b.getBookName() %></td>
                    <td><%= b.getPageNo() %></td>
                    <td><%= b.getLastFetchedAt() %></td>
                    <td><%= b.getCount() %></td>
                    <%-- <td>
                        <form method="post">
                            <input type="hidden" name="sNo" value="<%= b.getsNo() %>">
                            <button type="submit" name="update">Update</button>
                        </form>
                    </td> --%>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } %>
        <a href="index.html" class="btn">Back to Home</a>
    </div>

    <%
        if (request.getMethod().equalsIgnoreCase("POST") && request.getParameter("update") != null) {
            int sNo = Integer.parseInt(request.getParameter("sNo"));
            bhaktiDao.updateEntry(sNo);
            response.sendRedirect("bhakti_suggestion.jsp?bookId=" + selectedBookId);
        }
    %>
</body>
</html>
