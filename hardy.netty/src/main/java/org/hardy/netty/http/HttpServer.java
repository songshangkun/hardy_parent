package org.hardy.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
/**
 * 自定义HTTP服务器
 * <br>注意,在使用上传时如果使用默认的处理接口,
 * <br>在接口中不使用FileUpload对象对接受的文件做定制处理
 * <br>那么传递过小的文件时会出现无法上传或找不到上传的文件这种情况
 * <br>测试大小至少要106K以上
 * @author songshangkun
 *
 */
public class HttpServer {
	    
	    private ChannelFuture channelFuture;
	    
	    private HttpConfig config; 
	    
	    public HttpServer(HttpConfig config){
	    	this.config = config; 
	    }
	    
	    public HttpServer(){
	    	this(new HttpConfig()); 
	    }
	    
	    public void run() throws InterruptedException{
			run(this.config.getHost(), this.config.getPort());
	    }
	    public void run(int port) throws InterruptedException{
	    	this.config.setPort(port);
	    	run(this.config.getHost(), port);
	    }
	    
	    public void run(String host,int port) throws InterruptedException{
	    	this.config.setHost(host);
	    	this.config.setPort(port);
	    	  EventLoopGroup bossGroup = new NioEventLoopGroup();
	    	  EventLoopGroup workGroup = new NioEventLoopGroup();
	     try{
	    	  ServerBootstrap bootStrap = new ServerBootstrap();
	    	  bootStrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
	    	  .childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast("http-decoder",new HttpRequestDecoder()); //http reqeust 消息解码
					socketChannel.pipeline().addLast("http-encoder",new HttpResponseEncoder()); //http response 编码
					if(HttpServer.this.config.getAggregator()>0)
					socketChannel.pipeline().addLast("http-aggregator",new HttpObjectAggregator(HttpServer.this.config.getAggregator())); //将多个Http消息合成一个FullHttpRequest/FullHttpResponse
					socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler()); //大文件传输
					if(HttpServer.this.config.getAggregator()>0)
					socketChannel.pipeline().addLast("handler",new HttpServerHandler(HttpServer.this.config));
					else
					socketChannel.pipeline().addLast("handler",new HttpRequestServerHandler(HttpServer.this.config));
				}
			});
				 channelFuture = bootStrap.bind(host, port).sync() ;
				 channelFuture.channel().closeFuture().sync();
	     }finally{
	    	 bossGroup.shutdownGracefully();
	    	 workGroup.shutdownGracefully();
	     }
	    }

		public HttpConfig getConfig() {
			return config;
		}

		public void setConfig(HttpConfig config) {
			this.config = config;
		}	    
	    

		
		
		
		 
		
}
