package mgmt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import mgmt.entities.Bhakti;
import mgmt.entities.Book;
import mgmt.utilities.Utils;

import java.sql.*;

//import java.util.List;

public class BhaktiImpl implements BhaktiInt {

	Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(Utils.DRIVER);
		Connection con = DriverManager.getConnection(Utils.DB_URL, Utils.USERNAME, Utils.PASSWORD);

		return con;
	}

	@Override
	public void createSchema() throws ClassNotFoundException, SQLException {
		Class.forName(Utils.DRIVER);

		Connection con = DriverManager.getConnection(Utils.DB_BASE, Utils.USERNAME, Utils.PASSWORD);
		Statement stmt = con.createStatement();

		int result = stmt.executeUpdate(Utils.CREATE_SCHEMA);
		System.out.println(result);

	}

	@Override
	public void createPagesTable() throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		Statement stmt = con.createStatement();

		stmt.executeUpdate(Utils.CREATE_PAGES_TABLE);
	}
	
	@Override
	public void createBooksTable() throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		Statement stmt = con.createStatement();

		stmt.executeUpdate(Utils.CREATE_BOOKS_TABLE);
	}
	
	 @Override
	    public int addBook(String bookName) {
	        int bookId = -1; // Initialize bookId to -1 to indicate failure if not changed
	        try (Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USERNAME, Utils.PASSWORD);
	             PreparedStatement ps = conn.prepareStatement(Utils.INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {

	            ps.setString(1, bookName);
	            int rowsAffected = ps.executeUpdate();

	            if (rowsAffected > 0) {
	                // Retrieve the generated bookId
	                ResultSet rs = ps.getGeneratedKeys();
	                if (rs.next()) {
	                    bookId = rs.getInt(1);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return bookId; // Return the generated bookId or -1 if the operation failed
	    }

	    @Override
	    public int addPage(int bookId, int pageNo, Date lastFetchedAt) {
	        int rowsAffected = 0; // Initialize rowsAffected to 0 to indicate failure if not changed
	        try (Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USERNAME, Utils.PASSWORD);
	             PreparedStatement ps = conn.prepareStatement(Utils.INSERT_PAGENO)) {

	            ps.setInt(1, bookId);
	            ps.setInt(2, pageNo);
	            ps.setDate(3, lastFetchedAt);
	            rowsAffected = ps.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return rowsAffected; // Return the number of rows affected
	    }
	
	    public int updateLastFetchedAndCount(int bookId, int pageNo) {
	    	int r=0;
	        try (Connection conn = DriverManager.getConnection(Utils.DB_URL, Utils.USERNAME, Utils.PASSWORD);
	             PreparedStatement ps = conn.prepareStatement(Utils.UPDATE_LAST_FETCHED_AND_COUNT)) {

	            ps.setInt(1, bookId);
	            ps.setInt(2, pageNo);
	            r=ps.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			return r;
	    }

//	@Override
//	public int getLastPageNo() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

//	@Override
//	public int getRandomPageNo() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public void deletePageNo(int pageNo) {
	}
	
	

	@Override
	public List<Bhakti> getAllEntries() throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		ResultSet rSet = stmt.executeQuery(Utils.FETCH_ALL);
		
		List<Bhakti> bh = new ArrayList<Bhakti>();
		
		while(rSet.next()) {
			
			int sno = rSet.getInt("sNo");
			int bookId = rSet.getInt("bookId");
			String bookName = rSet.getString("bookName");
			int pageno = rSet.getInt("pageNo");
			Date lastfetchat = rSet.getDate("lastFetchedAt");
			int count = rSet.getInt("count");
			
			Bhakti bhakt = new Bhakti();
			bhakt.setsNo(sno);
			bhakt.setBookId(bookId);
			bhakt.setBookName(bookName);
			bhakt.setPageNo(pageno);
			bhakt.setLastFetchedAt(lastfetchat);
			bhakt.setCount(count);
			
			bh.add(bhakt);
		}
		    return bh;
	}
	
	@Override
	public List<Book> getAllBooks() throws ClassNotFoundException, SQLException {
	    Connection con = getConnection();
	    Statement stmt = con.createStatement();
	    ResultSet rSet = stmt.executeQuery(Utils.FETCH_ALL_BOOKS);
	    
	    List<Book> books = new ArrayList<>();
	    
	    while (rSet.next()) {
	        int bookId = rSet.getInt("bookId");
	        String bookName = rSet.getString("bookName");
	        
	        Book book = new Book(bookId, bookName);
	        books.add(book);
	    }
	    return books;
	}

	
	
	@Override
    public List<Bhakti> sortEntries(List<Bhakti> bhaktiList, String sortBy, int limit) {
        switch (sortBy) {
            case "bookName":
                bhaktiList.sort(Comparator.comparing(Bhakti::getBookName));
                break;
            case "pageNoAscending":
                bhaktiList.sort(Comparator.comparingInt(Bhakti::getPageNo));
                break;
            case "pageNoDescending":
                bhaktiList.sort(Comparator.comparingInt(Bhakti::getPageNo).reversed());
                break;
            case "count":
                bhaktiList.sort(Comparator.comparingInt(Bhakti::getCount));
                break;
            case "Dateoldest":
                bhaktiList.sort(Comparator.comparing(Bhakti::getLastFetchedAt));
                break;
            case "Datenewest":
                bhaktiList.sort(Comparator.comparing(Bhakti::getLastFetchedAt).reversed());
                break;
            default:
                throw new IllegalArgumentException("Invalid sort criteria: " + sortBy);
        }

        // Limiting the number of entries to 'limit'
        if (limit > 0 && limit <= bhaktiList.size()) {
            return bhaktiList.subList(0, limit);
        } else {
            return bhaktiList; // Return all entries if limit is invalid or exceeds list size
        }
    }

	@Override
	public Book getBookById(int bookId) throws ClassNotFoundException, SQLException {
	    Connection con = getConnection();
	    PreparedStatement stmt = con.prepareStatement(Utils.GET_BOOK_BY_ID);
	    stmt.setInt(1, bookId);
	    ResultSet rSet = stmt.executeQuery();

	    if (rSet.next()) {
	        Book book = new Book();
	        book.setBookId(rSet.getInt("bookId"));
	        book.setBookName(rSet.getString("bookName"));
	        return book;
	    } else {
	        throw new SQLException("Book not found for id: " + bookId);
	    }
	}

	@Override
	public Bhakti getEntryByBookIdAndPageNo(int bookId, int pageNo) throws ClassNotFoundException, SQLException {
		Bhakti bhakti = null;
//        String query = "SELECT p.sNo, p.bookId, b.bookName, p.pageNo, p.lastFetchedAt, p.count " +
//                       "FROM pages p " +
//                       "JOIN books b ON p.bookId = b.bookId " +
//                       "WHERE p.bookId = ? AND p.pageNo = ?";

		Connection con = getConnection();
	    PreparedStatement stmt = con.prepareStatement(Utils.GET_ENTRY_BY_PAGENO_AND_BOOKID);

            stmt.setInt(1, bookId);
            stmt.setInt(2, pageNo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    bhakti = new Bhakti();
                    bhakti.setsNo(rs.getInt("sNo"));
                    bhakti.setBookId(rs.getInt("bookId"));
                    bhakti.setBookName(rs.getString("bookName"));
                    bhakti.setPageNo(rs.getInt("pageNo"));
                    bhakti.setLastFetchedAt(rs.getDate("lastFetchedAt"));  // Assuming you changed this to Date
                    bhakti.setCount(rs.getInt("count"));
                }
            }

         catch (SQLException e) {
            e.printStackTrace();
        }

        return bhakti;

	}

	@Override
	public void updateEntry(int sNo) throws ClassNotFoundException, SQLException {
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(Utils.UPDATE_LAST_FETCHED_AND_COUNT)) {
	        stmt.setInt(1, sNo);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public List<Bhakti> getTopEntriesByBook(int bookId) throws ClassNotFoundException, SQLException {
		 List<Bhakti> bhaktiList = new ArrayList<>();
		    
		    try (Connection conn = getConnection();
		         PreparedStatement stmt = conn.prepareStatement(Utils.GET_ENTRY_BY_BOOKID)) {
		        stmt.setInt(1, bookId);
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            Bhakti bhakti = new Bhakti();
		            bhakti.setsNo(rs.getInt("sNo"));
		            bhakti.setBookId(rs.getInt("bookId"));
		            bhakti.setBookName(rs.getString("bookName"));
		            bhakti.setPageNo(rs.getInt("pageNo"));
		            bhakti.setLastFetchedAt(rs.getDate("lastFetchedAt"));
		            bhakti.setCount(rs.getInt("count"));
		            bhaktiList.add(bhakti);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return bhaktiList;
	}


	
	
//	 @Override
//	    public List<Bhakti> getOldestEntries() throws ClassNotFoundException, SQLException {
//	        List<Bhakti> bhaktiList = getAllEntries();
//	        bhaktiList.sort(Comparator.comparing(Bhakti::getLastFetchedAt));
//	        return bhaktiList;
//	    }

//	@Override
//	public void fetchAll() throws ClassNotFoundException, SQLException {
//		Connection con = getConnection();
//		Statement stmt = con.createStatement();
//		ResultSet rSet = stmt.executeQuery(Utils.FETCH_ALL);
//		
//		while(rSet.next()) {
//			
//			int sno = rSet.getInt("sNo");
//			String bhakti = rSet.getString("bookName");
//			int pageno = rSet.getInt("pageNo");
//			Timestamp lastfetchat = rSet.getTimestamp("lastFetchedAt");
//			
//			
//			
//		}
//		
//	}

}
