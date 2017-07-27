package org.hardy.sqlhelper.datatrans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NotImplementedException;
import org.hardy.reflex.SpringReflext;
import org.hardy.sqlhelper.datatrans.inf.DataTranslation;
import org.hardy.sqlhelper.enums.LazyType;
import org.hardy.sqlhelper.holographic.TableConstruct.PropColumn;
import org.hardy.sqlhelper.holographic.translation.inf.MapperType;
import org.hardy.sqlhelper.holographic.translation.jdbc.MapperTypeContext;
import org.hardy.statics.exception.NotSupportException;
import org.hardy.util.decide.NullOREmpty;
/**
 * 负责jdbc的数据库类型转换成java对象类型
 * @author songs
 *
 */
public class JdbcDataTransfer implements DataTranslation{
	 
	/**
	 * 时间解析接口,使用优先级最高
	 */
	private DatePatten datePatten;
	
	private MapperTypeContext mapperTypeContext;
	/**
	 * 时间转换格式,在没有DatePatten时会使用这个格式解析
	 */
	private String dateFormat = "yyyy-MM-dd HH:mm:ss"; 
	/**
	 * 全局是否懒加载,优先级低于对象属性标注的懒加载方式
	 */
	private LazyType lazy = LazyType.NONE;
	
	/**
	 * sqlhelper的基础类型的定义
	 */
	private  static  Class<?>[] essentiel = new Class<?>[]{int.class,long.class,float.class,double.class,byte.class,boolean.class,short.class,char.class,
		  Integer.class,Long.class,Float.class,Double.class,Byte.class,Boolean.class,Short.class,Character.class,String.class,Date.class};
	 
	public String getDateFormat() {
		return dateFormat;
	}


	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}



	public LazyType getLazy() {
		return lazy;
	}



	public void setLazy(LazyType lazy) {
		this.lazy = lazy;
	}



	public DatePatten getDatePatten() {
		return datePatten;
	}



	public void setDatePatten(DatePatten datePatten) {
		this.datePatten = datePatten;
	}



	@SuppressWarnings("unchecked")
	@Override
	public <T> T transler(Object bdData,PropColumn pc) throws ParseException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
		if(pc.getPropType().isAssignableFrom(bdData.getClass()))return (T)bdData;
		//首先转换8种基本数据类型,通过String方式转换
//		if(targetClass==int.class&&bdData.getClass() != int.class)  return (T)new Integer(bdData.toString());
//		else if(targetClass==float.class&&bdData.getClass() != float.class)  return (T)new Float(bdData.toString());
//		else if(targetClass==double.class&&bdData.getClass() != double.class)  return (T)new Double(bdData.toString());
//		else if(targetClass==byte.class&&bdData.getClass() != byte.class)  return (T)new Byte(bdData.toString());
//		else if(targetClass==boolean.class&&bdData.getClass() != boolean.class)  return (T)new Boolean(bdData.toString());
//		else if(targetClass==short.class&&bdData.getClass() != short.class)  return (T)new Short(bdData.toString());
//		else if(targetClass==char.class&&bdData.getClass() != char.class)  return (T)new Character(bdData.toString().charAt(0));
//		else if(targetClass==long.class&&bdData.getClass() != long.class)  return (T)new Long(bdData.toString());
		//转换8种基本数据封装类型,通过String方式转换
		else if(pc.getPropType()==Integer.class&&!(bdData instanceof Integer))  return (T)new Integer(bdData.toString());
		else if(pc.getPropType()==Float.class&&!(bdData instanceof Float))  return (T)new Float(bdData.toString());
		else if(pc.getPropType()==Double.class&&!(bdData instanceof Double))  return (T)new Double(bdData.toString());
		else if(pc.getPropType()==Byte.class&&!(bdData instanceof Byte))  {
			if(bdData.getClass().isArray()){
				return (T)new Byte(((Byte[])bdData)[0].toString());
			}
			else if(bdData.toString().equals("true"))return (T)new Byte("1");
			else if(bdData.toString().equals("false"))return (T)new Byte("0");
			else throw new NotImplementedException("对于其他byte类型未实现功能");
		}
		else if(pc.getPropType()==Boolean.class&&!(bdData instanceof Boolean)) return (T)new Boolean(bdData.toString());
		else if(pc.getPropType()==Short.class&&!(bdData instanceof Short))  return (T)new Short(bdData.toString());
		else if(pc.getPropType()==Character.class&&!(bdData instanceof Character))  return (T)new Character(bdData.toString().charAt(0));
		else if(pc.getPropType()==Long.class&&!(bdData instanceof Long))  return (T)new Long(bdData.toString());
		//转换String
		else if(pc.getPropType()==String.class&&!(bdData instanceof String)) return (T)bdData.toString();
		//转换Date
		else if(pc.getPropType()==Date.class&&!(bdData instanceof Date)){
			if(datePatten==null){
				datePatten = new DatePatten() {
					@Override
					public Date convertir(String dateString) throws ParseException {
						  SimpleDateFormat  sdf =   new SimpleDateFormat(JdbcDataTransfer.this.dateFormat) ;
						      Date date = sdf.parse(dateString); 
						      return new java.sql.Date(date.getTime());				
					}
				};
			}
			return (T)datePatten.convertir(bdData.toString());
		} 
		else if(!ArrayUtils.contains(essentiel, pc.getPropType())){ //对于其他的类型 很可能是自定义实体类,所以如果按照表关联记录的应都是关联键 
			if(pc.isMapper()){
				MapperType mType = (MapperType)pc.getPropType().newInstance();
				return (T) mType.retour(bdData);
			}
			if(!NullOREmpty.isEmptyString(pc.getMapperType())){
				 if(mapperTypeContext==null||mapperTypeContext.getMapperType(pc.getMapperType())==null) 
					 throw new NotSupportException("mapperTypeContext not found "+pc.getMapperType());
				return (T) this.mapperTypeContext.getMapperType(pc.getMapperType()).retour(bdData);
			}
			switch (pc.getLazy()) {
			case NONE:
				switch (this.lazy) {
				case NONE:
					return null;
				case SANS:
					return null;
				case SUELKEY:
					Object obj = pc.getPropType().newInstance();
					SpringReflext.setPropValue(obj, pc.getJoinPropValue(), bdData);//这里缺少对bdData的基本数据验证(太麻烦)
					return (T)obj;
				case LINKED:
					throw new NotImplementedException("未实现这种情况的解析");
				default:return null;
				}
			case SANS:
				return null;					
			case SUELKEY:
				Object obj = pc.getPropType().newInstance();
				SpringReflext.setPropValue(obj, pc.getJoinPropValue(), bdData);//这里缺少对bdData的基本数据验证(太麻烦)
				return (T)obj;
			case LINKED:
				throw new NotImplementedException("未实现这种情况的解析");
			default:return null;
			}
   
		}else{
			return (T) bdData;
		}
	}


	public MapperTypeContext getMapperTypeContext() {
		return mapperTypeContext;
	}


	public void setMapperTypeContext(MapperTypeContext mapperTypeContext) {
		this.mapperTypeContext = mapperTypeContext;
	}

 
}
