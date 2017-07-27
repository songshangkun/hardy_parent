package org.hardy.netty.http.upload;

import org.hardy.netty.http.HttpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

 
/**
 * 专门用于上传的服务,这个服务只要确定服务器地址和端口就可以上传
 * <br>具体的url和参数都不会被处理
 * @author songshangkun
 *
 */
public class HttpUploadServer {
    
	private static final Logger logger = LoggerFactory.getLogger(HttpUploadServer.class);
	/**
	 * 服务配置文件
	 */
	private HttpConfig config;
	
	public HttpUploadServer(HttpConfig config){
		this.config = config;
	}
	public HttpUploadServer(){
		this(new HttpConfig());
	}
	
	public void run(int port) throws Exception{
		this.config.setPort(port);
		run();
	}
	/**
	 * run方法运行服务
	 * @throws Exception
	 */
	public void run() throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
	                @Override
	                public void initChannel(SocketChannel ch) throws Exception {
	                   ch.pipeline().addLast("codec",new HttpServerCodec())  //或者使用HttpRequestDecoder & HttpResponseEncoder
	                   		.addLast("aggregator",new HttpObjectAggregator(HttpUploadServer.this.config.getAggregator()))  //在处理 POST消息体时需要加上
	                   		.addLast("handler",new HttpUploadServerhandler(HttpUploadServer.this.config));  //业务handler
	                }
	            })
	            .option(ChannelOption.SO_BACKLOG, 1024)
	            .childOption(ChannelOption.SO_KEEPALIVE, true)
	            .childOption(ChannelOption.TCP_NODELAY, true);
			ChannelFuture future = bootstrap.bind(this.config.getPort()).sync();
			
			logger.info("Netty-http server listening on port " + this.config.getPort());
			
			future.channel().closeFuture().sync();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
 
	@Override
	public String toString() {
		return "HttpUploadServer [config=" + config + "]";
	}
	
	
}
