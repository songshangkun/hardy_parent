package org.hardy.netty.websocket.webchat.simplText;


import org.hardy.netty.websocket.webchat.WebChatConfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebsocketServer {
	   
	  private WebChatConfig config ;
	  
	  public  WebsocketServer(WebChatConfig config){
		    this.config = config;
	  }
	  
	  public  WebsocketServer(){
		  config = new  WebChatConfig();
	  }
	  
       public void run(){
    	       EventLoopGroup bossGroup = new NioEventLoopGroup();
    	       EventLoopGroup workerGroup = new NioEventLoopGroup();
    	       try {
				ServerBootstrap bootstrap = new ServerBootstrap();
				bootstrap.group(bossGroup,workerGroup);
				bootstrap.channel(NioServerSocketChannel.class);
				bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						  ChannelPipeline pipeline = ch.pipeline();
						   pipeline.addLast("http-codec",new HttpServerCodec());
						   pipeline.addLast("aggregator",new HttpObjectAggregator(65536));
						   pipeline.addLast("http-chunked",new ChunkedWriteHandler());
						   pipeline.addLast("handler",new WebSocketServerHandler(WebsocketServer.this.config));
					}
				});
				Channel channel = bootstrap.bind(this.config.getPort()).sync().channel();
				System.out.println("start websocket server..... localhost:"+this.config.getPort()+System.lineSeparator()+"websocket:"+this.config.getWebSocketAddres());
				channel.closeFuture().sync();
			} catch (Exception e) {
				  bossGroup.shutdownGracefully();
				  workerGroup.shutdownGracefully();
			}
       }


	public static void main(String[] args) {
    	   WebsocketServer wServer = new WebsocketServer();
    	   wServer.run();
	}
}
