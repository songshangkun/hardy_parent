package org.hardy.util.io.raf.special1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 这给类是使用RandomAccessFile的特别定制类1
 * 功能
 * 1:设置文件读取的开头和结尾,读取>=开始,<结尾
 * 2:设置读取缓冲区大小,
 * 3:设置在缓冲区读完后的接口
 * @author 09
 *
 */
public class RafSp1{
	 /**
	  * 随机读取文件对象
	  */
	 private RandomAccessFile raf;
	 /**
	  * 当前的读取位置,此处的currentIndex是提供给客户端使用的
	  * 当一个RafFile调用多次不同的RafSp1对象时,因为RafSp1的开始结束坐标改变，所以要重置currentIndex。
	  * 同一个RafSp1传递对象是会自动调整currentIndex对象
	  */
	 private long currentIndex;
     /**
      * 文件读取时的状态
      */
	 private ConfigMessage configMessage;
	 /**
	  * 缓冲区都满后,是否进行下一步操作接口
	  */
	 private CacheEnougth cacheEnougth;
	 /**
	  * RafSp1对象工作状态,false:空闲,true:运行
	  */
	 private boolean workState = false;
	 /**
	  * 构造方法
	  * @param configMessage
	  * @throws FileNotFoundException
	  */
	 public RafSp1(ConfigMessage configMessage) throws FileNotFoundException{
		 this.raf = new RandomAccessFile(configMessage.getFile(),"r");
		 this.currentIndex = configMessage.getStart();
		 this.configMessage = configMessage;
	 }
	 /**
	  * 按缓冲区传输文件块,当缓冲区都满后调用cacheEnougth接口方法
	  * @param autoClose  是否自动关闭内置的RandomAccessFile流对象
	  * @return
	  * @throws IOException
	  */
	 public long translate(boolean autoClose) throws IOException{
		 System.out.println("client:"+this.configMessage);
		  workState = true;
		try{
		 boolean flag = true;
		 //如果到文件末尾,byte数组长度超出文件末尾，可以采用byte数组长度从新定义或者文件记录对象的byte长度改变
		 //无论采用哪一种都要与server端对应
		 byte[] byts = new byte[this.configMessage.getByteLength()];
		 int i = 0;
		 while(i!=-1&&this.currentIndex>=this.configMessage.getStart()
				 &&this.currentIndex<this.configMessage.getEnd()){
		     this.raf.seek(this.currentIndex);
			 i = this.raf.read(byts, 0, byts.length);
			 if(i!=-1){
				   this.configMessage.setCurrentIndex(this.currentIndex); 
				   flag = cacheEnougth.transferData(configMessage, byts);
				   if(flag){
					   this.currentIndex = i+this.currentIndex;
					   this.getConfigMessage().setCurrentIndex(this.currentIndex); 
					   if(this.currentIndex+this.configMessage.getByteLength()>this.configMessage.getEnd()){
							 this.configMessage.setByteLength((int)(this.configMessage.getEnd()-this.currentIndex));
							 byts = new byte[this.configMessage.getByteLength()];
						 } 
				   }else break  ;
			 }
		 }
		 return this.currentIndex;
		}finally{
			workState = false;
			if(autoClose&&this.raf!=null)this.raf.close();
		}
   
	 }
	 
	 /**
	  * 关闭内置的RandomAccessFile流对象
	  */
	 public void close(){
		if(this.raf!=null)
			try {
				this.raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	 }

	public ConfigMessage getConfigMessage() {
		return configMessage;
	}

	public void setConfigMessage(ConfigMessage configMessage) {
		this.configMessage = configMessage;
	}

	public CacheEnougth getCacheEnougth() {
		return cacheEnougth;
	}

	public void setCacheEnougth(CacheEnougth cacheEnougth) {
		this.cacheEnougth = cacheEnougth;
	}
	public boolean isWorkState() {
		return workState;
	}
	@Override
	public String toString() {
		return "RafSp1 [raf=" + raf + ", currentIndex=" + currentIndex
				+ ", configMessage=" + configMessage + ", cacheEnougth="
				+ cacheEnougth + ", workState=" + workState + "]";
	}
	public long getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(long currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	
 
}
