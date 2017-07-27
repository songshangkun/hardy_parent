package org.hardy.texthelper;

import java.util.LinkedList;
/**
 * 一个可在其他类中使用,创建字符串序列的实现类
 * @author Administrator
 *
 */
public class SequenceString implements Runnable{
    
	private LinkedList<StringBuilder> strings = new LinkedList<StringBuilder>();
	
	/**
	 * 处理内部字符串接口
	 * @author songshangkun
	 *
	 */
	public static interface TraiterString{
		void traiter(StringBuilder sb);
	}
	
	private TraiterString ts = null;
	int i = 0;
	@Override
	public void run() {
	 while(true){
			synchronized (strings) {
				if(strings.isEmpty()){
					try {
						strings.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			synchronized (strings) {
			   this.ts.traiter(strings.removeFirst());;
			}
		}
		
	}
    
	public void addStrings(String s){
		synchronized (strings) {
			strings.add(new StringBuilder(s));
			strings.notify();
		}
	}
	
	public void addStrings(StringBuilder s){
		synchronized (strings) {
			strings.add(s);
			strings.notify();
		}
	}
	
	public TraiterString getTs() {
		return ts;
	}

	public void setTs(TraiterString ts) {
		this.ts = ts;
	}
	
	
}
