<%@ page import="mgmt.dao.BhaktiInt, mgmt.dao.BhaktiImpl, mgmt.entities.Bhakti, java.util.List, java.util.Date, java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>See All Entries</title>
    <link rel="stylesheet" href="css/styling1.css">
</head>
<body>

    <%
        BhaktiInt bhaktiInt = new BhaktiImpl();
        List<Bhakti> bhaktiList = bhaktiInt.getAllEntries();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    %>

    <div class="container">
        <h1>All Entries</h1>
        <table>
            <caption>Entries List</caption>
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
                    <td>
                        <%= b.getLastFetchedAt() != null ? dateFormat.format(b.getLastFetchedAt()) : "Not available" %>
                    </td>
                    <td><%= b.getCount() %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <a href="index.html">Back to Home</a>
    </div>
</body>
</html>
