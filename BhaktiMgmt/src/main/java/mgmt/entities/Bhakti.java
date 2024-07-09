package mgmt.entities;

import java.sql.Date;

public class Bhakti {

	private int sNo;
	private int bookId;
	private String bookName;
	private int pageNo;
	private Date lastFetchedAt;
	private int count;

	public Bhakti(int bookId, String bookName, int pageNo, Date lastFetchedAt, int count) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.pageNo = pageNo;
		this.lastFetchedAt = lastFetchedAt;
		this.count = count;
	}

	public Bhakti(int sNo, int bookId, String bookName, int pageNo, Date lastFetchedAt, int count) {
		super();
		this.sNo = sNo;
		this.bookId = bookId;
		this.bookName = bookName;
		this.pageNo = pageNo;
		this.lastFetchedAt = lastFetchedAt;
		this.count = count;
	}

	public Bhakti(int sNo, String bookName, int pageNo, Date lastFetchedAt) {
		super();
		this.sNo = sNo;
		this.bookName = bookName;
		this.pageNo = pageNo;
		this.lastFetchedAt = lastFetchedAt;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Bhakti() {
		super();
	}

	public Date getLastFetchedAt() {
		return lastFetchedAt;
	}

	public void setLastFetchedAt(Date lastFetchedAt) {
		this.lastFetchedAt = lastFetchedAt;
	}

	public Bhakti(String bookName, int pageNo) {
		super();
		this.bookName = bookName;
		this.pageNo = pageNo;
	}

	public int getsNo() {
		return sNo;
	}

	public void setsNo(int sNo) {
		this.sNo = sNo;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@Override
	public String toString() {
		return "\nBhakti [sNo=" + sNo + ", bookId=" + bookId + ", bookName=" + bookName + ", pageNo=" + pageNo
				+ ", lastFetchedAt=" + lastFetchedAt + ", count=" + count + "]";
	}

	

}
