package hardy.securite.handler.sqlhandler;

import hardy.securite.RefuseType;
import hardy.securite.exception.RefuseRequestException;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 默认的执行sql实现类,只支持一条sql语句
 * @author 09
 *
 */
public class DefaultExcuteSql implements ExcuteSQL,RefuseType{
	/**
	 * 从request里读取的参数名
	 */
    private String[] paramNames; 
	/**
	 * 内置jdbcTemplate对象
	 * 
	 */
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void excute(String name,String sql, HttpServletRequest request)
			throws RefuseRequestException {
		Object[] params = null;
		if(this.paramNames!=null&&this.paramNames.length>0){
			params = new Object[this.paramNames.length];
			for(int i=0;i<this.paramNames.length;i++){
				params[i] = request.getSession().getAttribute(this.paramNames[i]);
				if(params[i]==null) throw new RefuseRequestException(SESSION_ATTR, this.paramNames[i]+" in session is NULL");
			}
		}
		List<Map<String,Object>> list = null;
		if(params!=null)list = jdbcTemplate.queryForList(sql, params);
		else list = jdbcTemplate.queryForList(sql);
		System.out.println(list.toString());
		if(list.isEmpty()){
			throw  new RefuseRequestException(NOT_FIND, "搜索结果为空");
		}
	}

	public DataSource getDataSource() {
		return this.jdbcTemplate.getDataSource();
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String[] getParamNames() {
		return paramNames;
	}
	/**
	 * 设置从request里读取的参数名
	 * @param paramNames
	 */
	public void setParamNames(String[] paramNames) {
		this.paramNames = paramNames;
	}
	/**
	 * 设置从request里读取的参数名
	 * @param paramNames
	 */
	public void setParams(String paramNames) {
		this.paramNames = paramNames.split(",");
	}
	
	

	 
}
