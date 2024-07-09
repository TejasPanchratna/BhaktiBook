package mgmt.controller;

import mgmt.dao.BhaktiInt;
import mgmt.entities.Book;
import mgmt.dao.BhaktiImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/insertPage")
public class AddPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("book_id"));
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        String dateStr = request.getParameter("date");
        
        // Store form data in session attributes
        request.getSession().setAttribute("book_id", bookId);
        request.getSession().setAttribute("pageNo", pageNo);
        request.getSession().setAttribute("date", dateStr);
        
        // Parse the date string to SQL Date
        Date lastFetchedAt = null;
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            lastFetchedAt = new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BhaktiInt bhaktiDao = new BhaktiImpl();
        Book book;
        String message;
        try {
            book = bhaktiDao.getBookById(bookId);
            String bookName = book.getBookName();
            
            int result = bhaktiDao.addPage(bookId, pageNo, lastFetchedAt);
            
            if (result > 0) {
                message = "Page " + pageNo + " added to " + bookName;
            } else {
                message = "Couldn't add Page.";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            message = "Error occurred while adding page.";
        }
        
        // Set message attribute in session
        request.getSession().setAttribute("message", message);
        response.sendRedirect("add_page.jsp");
    }
}
