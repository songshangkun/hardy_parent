package org.hardy.netty.http.tropsimpl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
/**
 * 最简单的HttpServer服务
 * @author 09
 *
 */
public class SimpleHttpServer {
	
	private ChannelFuture channelFuture;
     
	private int aggregator = 1024*1024;
	
	private SimpleTrait simpleTrait;
	
	private SimpleChannelInboundHandler<FullHttpRequest> simpleHttpHandler;
	
    
    public void run(String host,int port) throws InterruptedException{ 
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
				socketChannel.pipeline().addLast("http-aggregator",new HttpObjectAggregator(aggregator)); //将多个Http消息合成一个FullHttpRequest/FullHttpResponse
				socketChannel.pipeline().addLast("handler",new SimpleHttpHandler());
				 
			}
		});
			 channelFuture = bootStrap.bind(host, port).sync() ;
			 channelFuture.channel().closeFuture().sync();
     }finally{
    	 bossGroup.shutdownGracefully();
    	 workGroup.shutdownGracefully();
     }
    }

	public int getAggregator() {
		return aggregator;
	}

	public void setAggregator(int aggregator) {
		this.aggregator = aggregator;
	}

	public SimpleTrait getSimpleTrait() {
		return simpleTrait;
	}

	public void setSimpleTrait(SimpleTrait simpleTrait) {
		this.simpleTrait = simpleTrait;
	}

	public SimpleChannelInboundHandler<FullHttpRequest> getSimpleHttpHandler() {
		return simpleHttpHandler;
	}

	public void setSimpleHttpHandler(
			SimpleChannelInboundHandler<FullHttpRequest> simpleHttpHandler) {
		this.simpleHttpHandler = simpleHttpHandler;
	}
	

	
}
