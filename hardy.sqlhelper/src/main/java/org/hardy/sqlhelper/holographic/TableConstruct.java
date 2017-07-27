package org.hardy.sqlhelper.holographic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hardy.reflex.RefletClazzUtil;
import org.hardy.sqlhelper.annotation.structure.COLUMN;
import org.hardy.sqlhelper.annotation.structure.ID;
import org.hardy.sqlhelper.annotation.structure.Table;
import org.hardy.sqlhelper.enums.DataType;
import org.hardy.sqlhelper.enums.LazyType;
import org.hardy.sqlhelper.holographic.translation.inf.MapperType;
import org.hardy.statics.exception.NotSupportException;
import org.hardy.util.annotation.AnnotationTools;
import org.hardy.util.decide.NullOREmpty;
import org.hardy.util.extention.BidirectionalMap;

/**
 * 记录表的主体结构,在项目启动前加载,整个运行周期中不会变化
 * @author 09
 *
 */
public class TableConstruct {
    /**
     * 对应实体类
     */
	private Class<?> clazz;
	/**
     * 对应表名称
     */
	private String tableName;
	 /**
	  * id是否自增长
	  */
	private boolean idAutoIncrement = false;
	/**
	 * 对应的ID
	 */
	private PropColumn id;
	/**
	 * 对应的除id外的列
	 */
	private PropColumn[] columns;
	/**
	 * 记录属性名称和表列名对应关系的map
	 */
	private BidirectionalMap<String, String> nameMap ;

	/**
	 * 构造方法将类直接解析成TableConstruct结构
	 * @param clazz
	 */
	public TableConstruct(Class<?> clazz) {
		//记录标记@Deprecated的属性，在全部继承中不会记录该属性
	      Set<String> innerSupremer = new HashSet<>();
		//1-获取全部注解
		Table table = AnnotationTools.findClassAnnotation(clazz, Table.class);
		if(table==null)throw new NotSupportException("not support @Table");
		Map<Field, ID> idmap = findFieldAnnotation(clazz, ID.class,innerSupremer);
		if(idmap.size()>1) throw new NotSupportException("not support id num > 1");
		Map<Field, COLUMN> colmap = findFieldAnnotation(clazz, COLUMN.class,innerSupremer);
		this.setTable(table, clazz);
		if(!idmap.isEmpty()){
			Map.Entry<Field, ID> me = idmap.entrySet().iterator().next();
			Field idField = me.getKey();
			ID id = me.getValue();
			this.setId(id, idField);
		}
		this.columns = new PropColumn[colmap.size()];
		int index = 0;
		for(Map.Entry<Field, COLUMN> me :colmap.entrySet()){
			this.setPropColumn(me.getValue(), me.getKey(), index);
			index++;
		}
		nameMap = this.getMapPropCol();
	}
	/**
	 * 设置Table
	 * @param table
	 * @param clazz
	 */
	public void setTable(Table table,Class<?> clazz) {
		if(!table.name().trim().equals(""))
		this.tableName = table.name();
		else this.tableName = clazz.getSimpleName().toUpperCase();
		this.setClazz(clazz);
	}
	/**
	 * 设置ID
	 * @param id
	 * @param clazz
	 */
	public void setId(ID id,Field field) {
		if(!id.name().trim().equals(""))
			this.id = new PropColumn(field.getName(), field.getType(), id.name(), id.type(),
					NullOREmpty.returnStringValue(id.joinPropValue()),true,true,id.lazy(),
					NullOREmpty.returnStringValue(id.mapperType()),false)	;
		else 
			this.id = new PropColumn(field.getName(), field.getType(), field.getName(), id.type(), 
					NullOREmpty.returnStringValue(id.joinPropValue()),true,true,id.lazy(),
					NullOREmpty.returnStringValue(id.mapperType()),false);	
		if(id.automatic()) this.setIdAutoIncrement(true); 
		if(MapperType.class.isAssignableFrom(field.getType()))
			this.id.setMapper(true);
		
	}
	/**
	 * 设置COLUMN
	 * @param column
	 * @param field
	 * @param index 数组序列
	 */
	public void setPropColumn(COLUMN column,Field field,int index) {
		 PropColumn pc = null;
		if(!column.name().trim().equals(""))
			pc = new PropColumn(field.getName(), field.getType(), column.name(), column.type(),
					NullOREmpty.returnStringValue(column.joinPropValue()),false,false,column.lazy(),
					NullOREmpty.returnStringValue(column.mapperType()),false);
		else
			pc = new PropColumn(field.getName(), field.getType(), field.getName(), column.type(),
					NullOREmpty.returnStringValue(column.joinPropValue()),false,false,column.lazy(),
					NullOREmpty.returnStringValue(column.mapperType()),false);
		if(column.unique())pc.setUnique(true);
		if(column.notnull())pc.setNotNull(true);
		if(MapperType.class.isAssignableFrom(field.getType()))
			pc.setMapper(true);
	    this.columns[index] = pc;
	}

	public String getTableName() {
		return tableName;
	}
	
	

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public PropColumn getId() {
		return id;
	}
	
 
	public boolean isIdAutoIncrement() {
		return idAutoIncrement;
	}

	public void setIdAutoIncrement(boolean idAutoIncrement) {
		this.idAutoIncrement = idAutoIncrement;
	}

	 
	public void setId(PropColumn id) {
		this.id = id;
	}


	public PropColumn[] getColumns() {
		return columns;
	}


	public void setColumns(PropColumn[] columns) {
		this.columns = columns;
	}


	public Class<?> getClazz() {
		return clazz;
	}


	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	 

 
	@Override
	public String toString() {
		return "TableConstruct [clazz=" + clazz + ", tableName=" + tableName
				+ ", idAutoIncrement=" + idAutoIncrement + ", id=" + id
				+ ", columns=" + Arrays.toString(columns) + "]";
	}




	/**
	 * 记录属性名称和表数据的对应关系类,
	 * 注意该对象的hash值只有propName属性名称
	 * @author 09
	 *
	 */
	public static class PropColumn{
		/**
		 * 属性名称
		 */
		private String propName;
		/**
		 * 表的列名称
		 */
		private String columnName;
		/**
		 * 数据库字段的类型
		 */
		private DataType dataType;
		/**
		 * 属性字段的类型
		 */
		private Class<?> propType;
		/**
		 * 特殊映射类型
		 */
		private String mapperType;
		/**
		 *是否不为空,默认可以为空 
		 */
		private boolean notNull;
		/**
		 *是否唯一,默认不唯一
		 */
		private boolean unique;
		/**
		 * 懒加载类型
		 */
		private LazyType lazy;
		
		private boolean isMapper = false;
		
		/**
		 * 如果是复杂对象属性,数据库保存对象的某个子属性的属性值,
		 * 可依次递归获取
		 */
		private String joinPropValue;
	 
		/**
		 * 
		 * @param propName 属性名称
		 * @param propType 属性类型
		 * @param columnName 数据库列名称
		 * @param dataType 数据库对应类型
		 * @param joinPropValue 是否记录复合属性对象的子属性
		 * @param LazyType 如何懒加载对象
		 */
		public PropColumn(String propName,Class<?> propType,
				String columnName,DataType dataType, 
				String joinPropValue,boolean notNull,boolean unique
				,LazyType lazy,String mapperType,boolean isMapper) {
			super();
			this.propName = propName;
			this.columnName = columnName;
			this.dataType = dataType;
			this.propType = propType;
			this.joinPropValue = joinPropValue;
			this.notNull = notNull;
			this.unique = unique;
			this.lazy = lazy;
			this.mapperType = mapperType;
			this.isMapper = isMapper;
		}
		public PropColumn() {
			super();
			// TODO Auto-generated constructor stub
		}
		

		public boolean isMapper() {
			return isMapper;
		}
		public void setMapper(boolean isMapper) {
			this.isMapper = isMapper;
		}
		public String getMapperType() {
			return mapperType;
		}
		public void setMapperType(String mapperType) {
			this.mapperType = mapperType;
		}
		public boolean isNotNull() {
			return notNull;
		}
		public void setNotNull(boolean notNull) {
			this.notNull = notNull;
		}
		public boolean isUnique() {
			return unique;
		}
		public void setUnique(boolean unique) {
			this.unique = unique;
		}
		public String getPropName() {
			return propName;
		}
		public void setPropName(String propName) {
			this.propName = propName;
		}
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		
		public String getJoinPropValue() {
			return joinPropValue;
		}
		public void setJoinPropValue(String joinPropValue) {
			this.joinPropValue = joinPropValue;
		}
		public DataType getDataType() {
			return dataType;
		}
		public void setDataType(DataType dataType) {
			this.dataType = dataType;
		}
		public Class<?> getPropType() {
			return propType;
		}
		public void setPropType(Class<?> propType) {
			this.propType = propType;
		}
		
		
		public LazyType getLazy() {
			return lazy;
		}
		public void setLazy(LazyType lazy) {
			this.lazy = lazy;
		}
		
		@Override
		public String toString() {
			return "PropColumn [propName=" + propName + ", columnName=" + columnName + ", dataType=" + dataType
					+ ", propType=" + propType + ", mapperType=" + mapperType + ", notNull=" + notNull + ", unique="
					+ unique + ", lazy=" + lazy + ", joinPropValue=" + joinPropValue + "]";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((propName == null) ? 0 : propName.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PropColumn other = (PropColumn) obj;
			if (propName == null) {
				if (other.propName != null)
					return false;
			} else if (!propName.equals(other.propName))
				return false;
			return true;
		}
		
	} 
	
	/**
	 *  找到class类上的某个属性上的Annotation
	 * @param clazz
	 * @param ann
	 * @param eliminate 要排除的属性名称
	 * @return
	 */
	private static <T extends Annotation>Map<Field, T> findFieldAnnotation(
			Class<?> clazz,Class<T> ann,
			Set<String> eliminate) {
		Map<Field, T> result = new HashMap<Field, T>();
		Set<Class<?>> set = RefletClazzUtil.allSupperClass(clazz);	
		for(Class<?> c:set){
			if(c.isAnnotationPresent(Table.class)){
				for (Field field : c.getDeclaredFields()) {
					if(field.isAnnotationPresent(ann)){
						if(field.isAnnotationPresent(Deprecated.class)) eliminate.add(field.getName());
						if(!eliminate.contains(field.getName())){
							result.put(field, (T)field.getAnnotation(ann));
							eliminate.add(field.getName());
						} 
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 生成数据库字段名与属性名对照字符串
	 * @return
	 */
	public String columnPropNames(){
		 StringBuilder sb = new StringBuilder();
		 if(this.id!=null)sb.append(this.id.getColumnName()).append(" ").append(this.id.getPropName()).append(",");
		 if(this.columns!=null){
			 for(PropColumn pc:columns){
				 sb.append(pc.getColumnName()).append(" ").append(pc.getPropName()).append(",");
			 }
		 }
		 sb.deleteCharAt(sb.lastIndexOf(","));
		 return sb.toString();
	}
	/**
	 * 通过属性名称找列名
	 * @param prop
	 * @return
	 */
	public  String getColNameByProp(String prop){
		 if(this.getId()!=null&&this.getId().getPropName().equals(prop))
			 return this.id.getColumnName();
		 for(PropColumn pc:this.columns){
			 //System.out.println(pc.propName+"  "+prop);
			 if(pc.propName.equals(prop)){
				 return pc.getColumnName();
			 }
		 }
		 return null;
	}
	/**
	 * 	通过列名找属性名
	 */

	public String getPropNameByCol(String col){
		 if(this.getId()!=null&&this.getId().getColumnName().equals(col))
			 return this.id.getPropName();
		 for(PropColumn pc:this.columns){
			 if(pc.columnName.equals(col)){
				 return pc.getPropName();
			 }
		 }
		 return null;
	}
	
	/**
	 * 通过数据库字段名称找到PropColumn
	 * @param col
	 * @return
	 */
	public PropColumn getPropColumnByCol(String col){
		 if(this.id.columnName.equalsIgnoreCase(col))return this.id;
		 else {
			 for(PropColumn pc:this.columns){
				 if(pc.getColumnName().equalsIgnoreCase(col)){
					  return pc;
				 }
			 }
		 }
		 return null;
	}
	/**
	 * 通过属性名称找到PropColumn
	 * @param prop
	 * @return
	 */
	public PropColumn getPropColumnByProp(String prop){
		if(this.id.propName.equalsIgnoreCase(prop))return this.id;
		 else {
			 for(PropColumn pc:this.columns){
				 if(pc.getPropName().equalsIgnoreCase(prop)){
					  return pc;
				 }
			 }
		 }
		 return null;
	}
	
	/**
	 * 得到包括id的全部属性名称
	 * @return
	 */
	public String[] getAllPropNames(){
		Set<String> set = this.getNameMap().keySet();
		String[] result = new String[set.size()];
		 return set.toArray(result);
	}
	/**
	 * 得到包括id的全部列名称
	 * @return
	 */
	public String[] getAllColNames(){
		Collection<String> set = this.getNameMap().values();
		String[] result = new String[set.size()];
		 return set.toArray(result); 
	}
	
	/**
	 * 获取map保存的属性和列名称对应的关系,
	 * map.key 属性名称  map.value 表列名称
	 * @return
	 */
	private BidirectionalMap<String,String> getMapPropCol(){
		 BidirectionalMap<String, String> map = new BidirectionalMap<String, String>();
		 if(this.id!=null){
			 map.put(id.getPropName(), id.getColumnName());
		 }
		 for(PropColumn pc:this.columns){
			 map.put(pc.getPropName(), pc.getColumnName());
		 }
		 return map;
	}
	/**
	 * 获取map保存的属性和列名称对应的关系
	 * @return
	 */
	public BidirectionalMap<String, String> getNameMap() {
		return nameMap;
	}
	
	  
}
