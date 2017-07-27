package org.hardy.jsonhelper.table;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hardy.jsonhelper.coreface.OutInterface;

public class JsonTableExploer {
	
	public static OutInterface oif;
	
	public static <E extends Object> String toJqGrid(HttpServletResponse response, List<E> rows,int records,Integer currentPage,Integer maxResults){
		 if(currentPage==null)currentPage = 0;
		 if(maxResults==null) maxResults=10;
		 final JqGridPageView<E> jqpv = new JqGridPageView<>();
		 jqpv.setCurrentPage(currentPage);
		 jqpv.setMaxResults(maxResults);
		 jqpv.setRecords(records);
		 jqpv.setRows(rows);
		 return oif.toJSONObjectString(jqpv);
	 }
}
