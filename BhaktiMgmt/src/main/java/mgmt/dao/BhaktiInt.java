package mgmt.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import mgmt.entities.Bhakti;
import mgmt.entities.Book;

public interface BhaktiInt {

	public void createSchema() throws ClassNotFoundException, SQLException;

	public void createPagesTable()throws ClassNotFoundException, SQLException;
	
	public void createBooksTable()throws ClassNotFoundException, SQLException;


//	insert a page to the database
	public int addPage(int bookId, int pageNo, Date lastFetchedAt)throws ClassNotFoundException, SQLException;
	
	public int addBook(String bookName)throws ClassNotFoundException, SQLException;
	

	public int updateLastFetchedAndCount(int bookId, int pageNo)throws ClassNotFoundException, SQLException;
	
	public Book getBookById(int bookId)throws ClassNotFoundException, SQLException;
	
    public Bhakti getEntryByBookIdAndPageNo(int bookId, int pageNo) throws ClassNotFoundException, SQLException;

	
//	delete a pageNo
	public void deletePageNo(int pageNo)throws ClassNotFoundException, SQLException;

	public void updateEntry(int sNo)throws ClassNotFoundException, SQLException;
	
//	get all pages
	public List<Bhakti> getAllEntries()throws ClassNotFoundException, SQLException;
	
	public List<Book> getAllBooks() throws ClassNotFoundException, SQLException;

	public List<Bhakti> getTopEntriesByBook(int bookId)throws ClassNotFoundException, SQLException;
	
	public List<Bhakti> sortEntries(List<Bhakti> bhaktiList, String sortBy, int limit)throws ClassNotFoundException, SQLException;

	

	

//	get bhakti by pageNo
//	add this when you add actual bhakti to the project. Not just pageNo
//	public void getPageNo(int pageNo);

}
