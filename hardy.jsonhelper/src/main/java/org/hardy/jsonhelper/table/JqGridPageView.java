package org.hardy.jsonhelper.table;
import java.util.List;

 
 
/**
 * JqGridPageView 
 * page页数从1开始
 * rows数默认12
 * @author 宋尚堃
 *
 * @param <E>
 */
public class JqGridPageView<E> {
	/** list data * */
	private List<E> rows;
	/** total page * */
	private long total = 1;
	/** count per page * */
	private int maxResults = 12;
	/** current page * */
	private int currentPage = 0;
	/** total record qty * */
	private long records;

	public JqGridPageView(){}
	
	/**
	 * 不写泛型的new
	 * @return
	 */
	public static <E> JqGridPageView<E> newJqGridPageView(){
		return new JqGridPageView<E>();
	}

	public JqGridPageView(int maxResults, int currentPage) {
		this.maxResults = maxResults;
		this.currentPage = currentPage;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
		setTotal(this.records % this.maxResults == 0 ? this.records / this.maxResults : this.records / this.maxResults + 1);
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getFirstResult() {
		return (this.currentPage - 1) * this.maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
