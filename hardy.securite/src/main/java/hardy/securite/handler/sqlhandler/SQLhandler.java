package hardy.securite.handler.sqlhandler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hardy.securite.HardyAuthInterface;
import hardy.securite.exception.RefuseRequestException;

/**
 * 直接访问数据库获取认证信息
 * @author 09
 *
 */
public class SQLhandler implements HardyAuthInterface{
	/**
     * 执行顺序
     */
	private int order = 0;
	/**
	 * 默认名称
	 */
	private String name = this.getClass().getName();
	/**
	 * 默认开启
	 */
	private boolean open = true;
	
	/**
	 * 要执行的sql语句
	 */
	private Map<String,String> sqls = new HashMap<String, String>();
	/**
	 * 执行sql的接口
	 */
	private ExcuteSQL es ;
	
	@Override
	public void valid(HttpServletRequest request, HttpServletResponse response,Object handler) throws RefuseRequestException {
		   for(String name:sqls.keySet()){
			   es.excute(name,sqls.get(name), request);
		   }
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	@Override
	public String getName() {
		 
		return this.name;
	}

	@Override
	public boolean open() { 
		return this.open;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getSqls() {
		return sqls;
	}

	public void setSqls(Map<String, String> sqls) {
		this.sqls = sqls;
	}
    
	public void addSql(String name,String sql){
		this.sqls.put(name, sql);
	}

	public ExcuteSQL getEs() {
		return es;
	}
    /**
     * 设置ExcuteSQL接口
     * @param es
     */
	public void setEs(ExcuteSQL es) {
		this.es = es;
	}
	
	
}
