package org.hardy.util.io.conversion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.hardy.util.io.conversion.inf.Conversion;

 

 
/**
 * 对流进行转换
 * 为简化操作,如果设置整个类的流对象不关闭则必须手动外部关闭.
 * 所有方法中的内部产生的流必须都关闭,对于无返回值的我们不能关闭
 * 参数流对象
 * @author ssk
 *
 */
public class ConversionImpl implements Conversion{
        /**
         * 对参数的流是否自动关闭
         * 默认为不关闭
         */
	    private boolean closeParamsSource = false;
	     
		@Override
		public void objToStream(Object obj, OutputStream os) {
			ObjectOutputStream oos = null;
			  try {
				 oos = new ObjectOutputStream(os);
				oos.writeObject(obj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(oos!=null){
					try {
						oos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				 
			}
			  
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T>T streamToObject(InputStream is) {
			Object obj = null;
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(is);
				obj = ois.readObject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(ois!=null){
					try {
						ois.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(closeParamsSource){
					if(is!=null){
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} 
			}
			return (T) obj;
		}

		@Override
		public void byteArrayToStream(byte[] bs, OutputStream os) {
			   try {
				os.write(bs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public byte[] streamToByteArray(InputStream is) {
			 byte[] data = null;
			 try
			     {
			        data = new byte[is.available()];
			        is.read(data);
			        is.close();
			      }
			       catch (IOException e)
			      {
			        e.printStackTrace();
			      }finally{
			    	  if(closeParamsSource&&is!=null){
			    		  try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	  }
			      }
			  
			      return data;
		}
		@Override
		 public byte[] streamToByteArray(InputStream in,int cache){
			 ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			 if(cache==0)cache = 1024;
			 byte[] data = new byte[cache];
			 int count = -1;
			 try {
				while ((count = in.read(data, 0, cache)) != -1) {
				     outStream.write(data, 0, count); //閲嶇偣  count
				  }
				data = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(outStream!=null){
					try {
						outStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(closeParamsSource&&in!=null){
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
 
			}
			 return outStream.toByteArray();
		 }

		@Override
		public InputStream byteArrayToStream(byte[] bs) {	 
			return new ByteArrayInputStream(bs);
		}
}
