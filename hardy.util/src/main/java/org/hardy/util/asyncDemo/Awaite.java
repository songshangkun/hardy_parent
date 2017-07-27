package org.hardy.util.asyncDemo;


import java.util.LinkedList;
import java.util.List;

public class Awaite<T>  implements Linked<T>{
	
	private long currentIndex = 0;
      
	 private Fonction<T> function;
      
	  private List<T>  iterator = new LinkedList<>();
	  
	  public void setProduice(Fonction<T> function){
		  this.function = function;
	  }
	   public void produice(){ //System.out.println("调用 produice 方法 获取数据函数");

		   new Thread(new Runnable() {
			
			@Override
			public void run() {
				 synchronized (Awaite.this.function) { //System.out.println("执行 produice 的线程 方法 ");
					   try {  
						if(Awaite.this.function.getRange()[0]<=Awaite.this.function.getRange()[1]){
					    	 for(long index = Awaite.this.function.getRange()[0];index<=Awaite.this.function.getRange()[1];index++){
					    		 iterator.add(Awaite.this.function.produice(index)) ;  
					    		 synchronized (Awaite.this.iterator) {
					    			 Awaite.this.iterator.notifyAll();
								}
					    		 synchronized (Awaite.this) { 
					    			 Awaite.this.wait();
					    		 }
					    	 }
					    }else{
					    	for(long index = Awaite.this.function.getRange()[0];index>=Awaite.this.function.getRange()[1];index--){
					    		Awaite.this.function.wait();
					    		 iterator.add(Awaite.this.function.produice(index)) ;  
					    		  synchronized (Awaite.this.iterator) {
									    Awaite.this.iterator.notifyAll();
								   }
					    	 }
					    }
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   } 
				 
			}
		}).start();

	   }

	@Override
	public T next() {  //System.out.println("调用next方法 获取下一个数据");
		new Thread(new Runnable() {
			@Override
			public void run() {
				produice();
				synchronized (Awaite.this.iterator) {
					 try {
						 Awaite.this.iterator.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
				System.out.println("  iterator  被唤醒 ");
				synchronized (Awaite.this) { //System.out.println(" 唤醒输出结果..................... ");
					 Awaite.this.notifyAll(); 
				} 
				currentIndex ++;
			}
		}).start();
		synchronized (this) {// System.out.println("  等待输出结果................. ");
			  try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 synchronized (Awaite.this) {
			 Awaite.this.notifyAll();
		}
		return  iterator.get(iterator.size()-1);
	}

	@Override
	public T previous() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> iterator() {	 
		return this.iterator;
	}
	  
}
