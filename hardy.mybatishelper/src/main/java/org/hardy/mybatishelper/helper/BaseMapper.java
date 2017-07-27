package org.hardy.mybatishelper.helper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.hardy.mybatishelper.sqlTranslation.DinaSqlProvider;
import org.hardy.mybatishelper.sqlTranslation.param.SqlParams;
import org.hardy.sqlhelper.enums.LinkedType;
import org.hardy.sqlhelper.enums.OperatorType;



/**
 * mybatis的抽象mapper
 * @author song
 *
 */
public interface BaseMapper {
  
	 
	/**
	 * 直接执行sql语句
	 * <br>使用方法1:mapper.b_selectSql(new SqlParams("select * from test_jdbc"))
	 * <br>使用方法2:mapper.b_selectSql(new SqlParams("select * from test_jdbc where id = #{id}").add("id", 1))
	 * <br>使用方法3:mapper.b_selectSql(new SqlParams("select * from test_jdbc where id = #{id} and depName=#{name}"),new KeyValue("name","vvbvbv").$("id", 5)))
	 * @param sql  SqlParams对象
	 * @return
	 */
	@SelectProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.excuteSQL)
	public List<Map<String,Object>> b_selectSql(SqlParams sql);
	/**
	 * 直接执行增删改的语句
	 * <br>使用方法1:mapper.b_excuteSql(new SqlParams("update  test_jdbc set depName='okkk' where id = 8"));
	 * <br>使用方法2:mapper.b_excuteSql(new SqlParams("update  test_jdbc set depName=#{name} where id = #{id}").add("name", "test11aA").add("id", 8));
	 *	<br>使用方法3:mapper.b_excuteSql(new SqlParams("update  test_jdbc set depName=#{name} where id = #{id}",new KeyValue("name","vvbvbv").$("id", 5)));
	 * @param sql SqlParams对象
	 */
	@UpdateProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.excuteSQL)
	public void b_excuteSql(SqlParams sql);
		 
	
	@InsertProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.insertObj)
	public void b_insert(Object object);
	/**
	 * 根据对象id删除
	 * <br>TestJdbc testJdbc = new TestJdbc();
	 * <br>testJdbc.setId(14l);
	 *	<br>mapper.b_deleteObjectById(testJdbc);
	 * @param object 使用@Table注解的实体类
	 */
	@DeleteProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.deleteObjectById)
	public void b_deleteObjectById(Object object);
	/**
	 * 删除某个类的id符合要求的对象
	 * <br>mapper.b_deleteSQLById(new SqlParams(TestJdbc.class,5));
	 * <br>mapper.b_deleteSQLById(new SqlParams(TestJdbc.class).ID(1));
	 * @param sqlParams SqlParams对象
	 */
	@DeleteProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.deleteSQLById)
	public void b_deleteSQLById(SqlParams sqlParams);
		/**
		 * 删除一样条件的对象
		 * <br>Dep dep = new Dep();dep.setName("test11aA");
		 * <br>TestJdbc testJdbc = new TestJdbc();
		 * <br>testJdbc.setDepName(dep);
		 * <br>testJdbc.settInteger(107);
		 * <br>mapper.b_deleteObject(testJdbc);
		 * @param obj 使用@Table注解的实体类
		 */
	@DeleteProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.deleteObject)
	public void b_deleteObject(Object obj);
	/**
	 * 根据要求删除对象
	 *  <br>Dep dep = new Dep();dep.setName("%==0");
	 * 	 <br>TestJdbc testJdbc = new TestJdbc();
	 * 	 <br>testJdbc.setDepName(dep);
	 * 	 <br>testJdbc.settInteger(107);
	 * 	 <br>mapper.b_deleteSqlParams(new SqlParams(testJdbc, LinkedType.OR, OperatorType.LIKE));
	 *  <br> 或者:new SqlParams(testJdbc).LINKED(LinkedType.OR).OPERATOR(OperatorType.EQ)
	 * @param sqlParams
	 */
	@DeleteProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.deleteSqlParams)
	public void b_deleteSqlParams(SqlParams sqlParams);
	/**
	 * 根据对象id修改对象
	 * <br>Dep dep = new Dep();dep.setName("newName");
	 *	 <br>TestJdbc testJdbc = new TestJdbc();
	 *	 <br>testJdbc.setId(13L);
	 *	 <br>testJdbc.setDepName(dep);
	 *	 <br>testJdbc.settInteger(999);
	 *	 <br>mapper.b_updateObjectById(testJdbc);
	 * @param obj
	 */
	@UpdateProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.updateObjectById)
	public void b_updateObjectById(Object obj);
	/**
	 *    根据sqlparams对象修改
	    *	 <br> Dep dep = new Dep();dep.setName("2newName");
	    *	 <br>	 TestJdbc target = new TestJdbc();
		*	 <br> target.setId(113L);
		*	 <br> target.setDepName(dep);
		*	 <br> target.settInteger(1999);
		 
		*	 <br> TestJdbc condition = new TestJdbc();
		*	 <br> Dep dep2 = new Dep();dep2.setName("ff2");
		*	 <br> condition.setId(23L);
		*	 <br> condition.setDepName(dep2);
		*	 <br> condition.settInteger(4441);
		*  <br>mapper.b_updateSqlParams(new SqlParams(target, condition).LINKED(LinkedType.AND).OPERATOR(OperatorType.EQ));
	     *  <br>或者:new SqlParams(target, "id",3);
	 * @param sqlParams
	 */
	@UpdateProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.updateSqlParams)
	public void b_updateSqlParams(SqlParams sqlParams);
	/**
	 * 根据map对象修改
	 *  <br> Map<String, Object> target = new HashMap<>();
	 *  <br>	Map<String, Object> condition = new HashMap<>();
	 *  <br>	target.put("tInteger", 1);
	 *  <br>	target.put("tDouble", 2.0d);
	 *  <br>	target.put("depName", "修改工地");
	 *  <br>	condition.put("depName", "测试名称19");
	 *  <br>	condition.put("tInteger", 119);
	 *  <br>	mapper.b_updateSqlParamsMap(new SqlParams(TestJdbc.class,target,condition));
	 * new SqlParams(TestJdbc.class,map_target,map_condition).TARGET("depName","newName").CONDITION_MAP("depName","oldName");
	 * @param sqlParams
	 */
	@UpdateProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.updateSqlParamsMap)
	public void b_updateSqlParamsMap(SqlParams sqlParams);
	/**
	 * 搜索相同的对象结果
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.selectSameObject)
	public List<Map<String,Object>> b_selectSameObject(Object obj);
	/**
	 * 指通过id搜索,注解影响搜索结果
	 * @param obj
	 * @return
	 */
	@SelectProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.selectObjectById)
	public List<Map<String,Object>> b_selectObjectById(Object obj);
	
	/**
	 * 根据对象搜索对象
	 * mapper.b_selectSqlParams(new SqlParams(tJdbc, LinkedType.OR, OperatorType.EQ));
	 * @param sqlParams
	 * @return
	 */
	@SelectProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.selectSqlParams)
	public List<Map<String,Object>> b_selectSqlParams(SqlParams sqlParams);
	/**
	 * 根据类和id搜索
	 * mapper.b_selectSqlById(new SqlParams(TestJdbc.class).ID(4))
	 * @param sqlParams
	 * @return
	 */
	@SelectProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.selectSqlById)
	public List<Map<String,Object>> b_selectSqlById(SqlParams sqlParams);
	/**
	 * 根据SQL接口查找
	 * @param sqlParams
	 * @return
	 */
	@SelectProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.excuteInterface)
	public List<Map<String,Object>> b_selectInterface(SqlParams sqlParams);
	/**
	 * 根据SQL执行
	 * @param sqlParams
	 * @return
	 */
	@UpdateProvider(type=DinaSqlProvider.class,method=DinaSqlProvider.excuteInterface)
	public void b_excuteInterfacee(SqlParams sqlParams);
	
}
