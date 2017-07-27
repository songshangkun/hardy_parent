package org.hardy.netty.tcp2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import io.netty.channel.socket.SocketChannel;

public class SocketChannelRespo {
	 
	private static SocketChannelRespo respo ;
        /**
         *  超时时间间隔，一般是心跳时间的几倍
         */
	 private static long orderIterval = 1000*60;
	/**
	 * 记录连接系统的全部socketChannels
	 */
	  private Map<String, SocketChannelUnit> socketChannels = new HashMap<>();
	  
	  private SocketChannelRespo(long orderIterval){
		  SocketChannelRespo.orderIterval = orderIterval;
		        if(SocketChannelRespo.orderIterval>0){
		        	Timer timer = new Timer();
			        timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							 removeTimeOut();
						}
					}, orderIterval, orderIterval);
		        }
	  }
	  
	  public static SocketChannelRespo getInstance(long orderIterval){
		      if(respo==null) respo = new SocketChannelRespo(orderIterval);
		      return respo;
	  }
	  public static SocketChannelRespo getInstance(){
	      if(respo==null) respo = new SocketChannelRespo(SocketChannelRespo.orderIterval);
	      return respo;
  }
	  
	  public SocketChannel getSocketChannelByKey(String key){
		  if(key==null|| socketChannels.get(key)==null)return null;
		  else{
		       return socketChannels.get(key).socketChannel;
		  }
	  }
	  
	  public SocketChannel active(String key){
		  if(key==null|| socketChannels.get(key)==null) return null;
		  else{
			  SocketChannel socketChannel = socketChannels.get(key).socketChannel;
			  socketChannels.get(key).count++;
		       return socketChannel;
		  }
		  
 }
	  
	  public void addSocketChannel(String key,SocketChannel socketChannel){
		  SocketChannelUnit socketChannelUnit = new SocketChannelUnit();
		  socketChannelUnit.socketChannel = socketChannel;
		  socketChannelUnit.count = 0;
		   this.socketChannels.put(key,socketChannelUnit);
	  }
	  
	  public void removeTimeOut(){  
		    	 Map<String, SocketChannelUnit> socketChannels_remove = new HashMap<>();
				    Iterator<Entry<String, SocketChannelUnit>> ites = socketChannels.entrySet().iterator();
				    while(ites.hasNext()){
				    	Entry<String, SocketChannelUnit> entry =  ites.next();
				    	SocketChannelUnit socketChannelUnit =entry.getValue();
				    	
				    	     if(socketChannelUnit.count==0){
				    	    	 socketChannelUnit.socketChannel.close();
				    	    	 socketChannels_remove.put(entry.getKey(), socketChannelUnit);
				    	     }
				    	    	 
				    	     else socketChannelUnit.count = 0;
				    	   
				    }
				    socketChannels.entrySet().removeAll(socketChannels_remove.entrySet());
	  }
	  
	  
	  
	  private class SocketChannelUnit{
		      SocketChannel socketChannel ;
		      int count;
	  }
	  
	  public boolean containt(String key){
		   return this.socketChannels.containsKey(key);
	  }
	  
	  public void remove(String key){
		    this.socketChannels.remove(key);
	  }
	  
	  public int size(){
		  return this.socketChannels.size();
	  }
	  public boolean isEmpty(){
		   return this.socketChannels.isEmpty();
	  }
	  
	  public Set<Entry<String, SocketChannelUnit>> entrySet(){
		    return this.socketChannels.entrySet();
	  }
	  public List<SocketChannel> listSocketChannel(){
		  List<SocketChannel> result = new ArrayList<>();
		  for(SocketChannelUnit eu :this.socketChannels.values()){
			  result.add(eu.socketChannel);
		  }
		    return result;
	  }
	   
}
