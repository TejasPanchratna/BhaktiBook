package mgmt.utilities;

public class Utils {
	
	public final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	public final static String DB_BASE = "jdbc:mysql://localhost:3306";
	public final static String DB_URL = "jdbc:mysql://localhost:3306/bhakti";
	public final static String USERNAME = "root";
	public final static String PASSWORD = "12345678";
	
	public final static String CREATE_SCHEMA = "CREATE SCHEMA bhakti;";
	
	
	/*
					CREATE TABLE bhakti.books (
			bookId INT NOT NULL AUTO_INCREMENT,
			bookName VARCHAR(50) NOT NULL,
			PRIMARY KEY (bookId)
			);

*/
	public final static String CREATE_BOOKS_TABLE = "CREATE TABLE bhakti.books ("
			+ "    bookId INT NOT NULL AUTO_INCREMENT,"
			+ "    bookName VARCHAR(50) NOT NULL,"
			+ "    PRIMARY KEY (bookId)"
			+ ");";
	
	
	/*
				  		CREATE TABLE pages (
		    sNo INT NOT NULL AUTO_INCREMENT,
		    bookId INT NOT NULL,
		    pageNo INT NOT NULL,
		    lastFetchedAt DATE NOT NULL,
		    count INT DEFAULT 0,
		    PRIMARY KEY (sNo),
		    UNIQUE (bookId, pageNo),
		    FOREIGN KEY (bookId) REFERENCES books(bookId)
			);

	*/
	public final static String CREATE_PAGES_TABLE = "CREATE TABLE pages (\n"
			+ "    sNo INT NOT NULL AUTO_INCREMENT,\n"
			+ "    bookId INT NOT NULL,\n"
			+ "    pageNo INT NOT NULL,\n"
			+ "    lastFetchedAt DATE NOT NULL,\n"
			+ "    count INT DEFAULT 0,\n"
			+ "    PRIMARY KEY (sNo),\n"
			+ "    UNIQUE (bookId, pageNo),\n"
			+ "    FOREIGN KEY (bookId) REFERENCES books(bookId)\n"
			+ ");";
	
	public final static String INSERT_BOOK = "INSERT INTO books(bookName) VALUES (?);";
	
	public final static String GET_BOOK_BY_ID = "SELECT * FROM books WHERE bookId = ?;";
	
	public final static String INSERT_PAGENO = 
		    "INSERT INTO pages (bookId, pageNo, lastFetchedAt) VALUES (?, ?, ?) " +
		    "ON DUPLICATE KEY UPDATE lastFetchedAt = VALUES(lastFetchedAt), count = count + 1;";

	
	public final static String GET_ENTRY_BY_PAGENO_AND_BOOKID = "SELECT p.sNo, p.bookId, b.bookName, p.pageNo, p.lastFetchedAt, p.count " +
            "FROM pages p " +
            "JOIN books b ON p.bookId = b.bookId " +
            "WHERE p.bookId = ? AND p.pageNo = ?";
	
	public final static String GET_ENTRY_BY_BOOKID = 
		    "SELECT p.sNo, p.bookId, b.bookName, p.pageNo, p.lastFetchedAt, p.count " +
		    "FROM pages p " +
		    "JOIN books b ON p.bookId = b.bookId " +
		    "WHERE p.bookId = ? " +
		    "ORDER BY p.count ASC, p.lastFetchedAt ASC " +
		    "LIMIT 60";
	
	public static final String FETCH_ALL_BOOKS = "SELECT * FROM books";
	
    public final static String UPDATE_LAST_FETCHED_AND_COUNT = "UPDATE pages SET lastFetchedAt = NOW(), count = count + 1 WHERE sNo = ?";
	
    public static final String FETCH_ALL = 
    	    "SELECT p.sNo, p.bookId, b.bookName, p.pageNo, p.lastFetchedAt, p.count " +
    	    "FROM pages p " +
    	    "JOIN books b ON p.bookId = b.bookId " +
    	    "ORDER BY p.sNo";

}

