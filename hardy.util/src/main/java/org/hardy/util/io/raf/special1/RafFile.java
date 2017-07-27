package org.hardy.util.io.raf.special1;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hardy.util.arrays.ArrayUtil;

/** 
 * 随机传输文件,这个类可规定线程数,规定分块大小.
 * @注释 rs1.setCurrentIndex(currentIndex);
  <br>因为RafSp1对象在一次数据块传输后byteLength会复位为0,所以这样会影响反复使用传输
  <br>解决1-创建新的RafSp1对象，并设置参数
  <br>2-对原有的RafSp1对象设置byteLength属性
  <br>因为分块读取文件时RafSp1对象中的configMessage对象的currentIndex会被同一对象的RafSp1上次线程读取的数据影响所以需要从置
  <br>解决1-在RafSp1对象中从置
  <br>2-在当前位置从置
 * @author 09
 *
 */
public class RafFile {
	 /**
	  * 要传输的文件
	  */
	 private File file;
	  
     /**
      * 传输文件的线程数量
      */
	 private int threadNum;
	 /**
	  * 每块数据的大小
	  */
	 private long blockSize;
	 /**
	  * 分块的临界值
	  */
	 private long[][] skips;
	 /**
	  * 数据块缓冲区大小
	  */
	 private int cacheSize;
	 /**
	  * 文件属性文件
	  */
	 private ConfigMessage cm;
	 /**
	  * 缓存区满后操作接口
	  */
	 private CacheEnougth cacheEnougth;
	 
	 /**
	  * 当前运行的线程数
	  */
	 private int currentThreadNum = 0;
	 
	 
	 
	 /**
	  * 构造一个分块传输文件的RafFile实体
	  * @param file  文件
	  * @param blockSize  分块大小,每个线程一次性传递数据大小
	  * @param threadNum  线程数量
	  * @param cacheSize  线程缓冲区大小
	  * @param savePath   保存文件的路径
	  * @param cacheEnougth 当缓冲区都满后调用的接口
	 * @throws IOException 
	  */
	 public RafFile(File file,long blockSize,int threadNum,int cacheSize,String savePath,CacheEnougth cacheEnougth) throws IOException{
		  this.file = file;
		  this.blockSize = blockSize;
		  this.cacheSize = cacheSize;
		  long blokNum = (this.file.length()-1)/this.blockSize+1;
		  long restSize = this.file.length()%this.blockSize;
		  skips = new long[(int) blokNum][2];
		  for(int i=0;i<skips.length;i++){
			  skips[i][0] = i*blockSize;
			  skips[i][1] = (i+1)*blockSize;
		  }
		  if(restSize!=0)skips[skips.length-1][1] = skips[skips.length-1][0]+restSize;
		  if(this.threadNum>this.skips.length)this.threadNum = this.skips.length;
		  else this.threadNum = threadNum;
		  this.cm = new ConfigMessage(this.file);
		  this.cm.setByteLength(this.cacheSize);
		  this.cm.setSavePath(savePath);
		  this.cacheEnougth = cacheEnougth;  
	 }
	 /**
	  * 开始传输
	 * @throws IOException 
	 * @throws InterruptedException 
	  */
	 public void transfer() throws IOException, InterruptedException{
		  final List<long[]> list = ArrayUtil.asList(skips);   
		  while(!list.isEmpty()){
			  Thread.sleep(1);
			  if(this.currentThreadNum<this.threadNum){
				   long[] ls = list.remove(0);
			       ConfigMessage cfm = cm.cloneNew();
			       cfm.setStart(ls[0]);
			       cfm.setEnd(ls[1]);
			       cfm.setByteLength(this.cacheSize);
			       final RafSp1 rs1 = new RafSp1(cfm);
			       rs1.setCacheEnougth(this.cacheEnougth);
			       System.out.println("currentThreadNum=="+currentThreadNum);
				  if(rs1!=null&&!rs1.isWorkState()){ 
					  ThreadRunnable runnable = new ThreadRunnable();
					  runnable.setRs1(rs1);
					  new Thread(runnable).start();
				  }
			  }
			     
		  } 
	 }
	 
	public File getFile() {
		return file;
	}
	 
	public int getThreadNum() {
		return threadNum;
	}
	public long getBlockSize() {
		return blockSize;
	}
	public long[][] getSkips() {
		return skips;
	}
	 
	
	public class ThreadRunnable implements Runnable{
        private RafSp1 rs1;
		@Override
		public void run() {
			try {
				 RafFile.this.currentThreadNum++;
				 long currentSkip = rs1.translate(false);
				 System.out.println("完成:"+currentSkip);
				 RafFile.this.currentThreadNum--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	 
		}
		public RafSp1 getRs1() {
			return rs1;
		}
		public void setRs1(RafSp1 rs1) {
			this.rs1 = rs1;
		}
		public int getCurrentCount(){
			return RafFile.this.currentThreadNum;
		}
		
	} 
 
}
