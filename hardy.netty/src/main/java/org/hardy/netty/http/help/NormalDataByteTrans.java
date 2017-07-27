package org.hardy.netty.http.help;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * 一般的对象转换方法
 * <br>支持一般字符对象，byte对象数组
 * <br>String CharSequence byte[] ByteBuf --->输出为ByteBuf
 * @author ssk
 *
 */
public class NormalDataByteTrans implements ResponseHelper.DataByteTrans{
	/**
	 * 转换的具体方法
	 * <br>只支持String CharSequence byte[] ByteBuf
	 */
	@Override
	public ByteBuf dataToByteBuf(Object object) { 
		ByteBuf buf = null;
	    if(object instanceof String){  
	      	  buf = Unpooled.copiedBuffer(object.toString(), CharsetUtil.UTF_8);
	    }else if(object instanceof CharSequence){
			CharSequence charSequence = (CharSequence)object;
			  buf = Unpooled.copiedBuffer(charSequence, CharsetUtil.UTF_8);
		}else  if(object instanceof byte[]){
			byte[] bytes = (byte[])object;
			  buf = Unpooled.copiedBuffer(bytes);
		}else if(object instanceof ByteBuf){
			ByteBuf bytes = (ByteBuf)object;
			buf = Unpooled.copiedBuffer(bytes);
		}else{
			throw new RuntimeException("support parmas must be String CharSequence byte[] ByteBuf");
		}
	    return buf;
	}

}
