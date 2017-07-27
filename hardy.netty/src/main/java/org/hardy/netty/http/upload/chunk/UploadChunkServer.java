//package com.song.util.netty.http.upload.chunk;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.song.util.netty.http.HttpConfig;
//import com.song.util.netty.http.upload.FileUploadRename;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.HttpContentCompressor;
//import io.netty.handler.codec.http.HttpRequestDecoder;
//import io.netty.handler.codec.http.HttpResponseEncoder;
//import io.netty.handler.codec.http.multipart.FileUpload;
//
//public class UploadChunkServer {
//	
//	private static final Logger logger = LoggerFactory.getLogger(UploadChunkServer.class);
//	
//	/**
//	 * 服务配置文件
//	 */
//	private HttpConfig config;
//	
//	public UploadChunkServer(HttpConfig config){
//		this.config = config;
//	}
//	public UploadChunkServer(){
//		this(new HttpConfig());
//	}
//	
//	public void run(int port) throws Exception{
//		this.config.setPort(port);
//		run();
//	}
//	/**
//	 * run方法运行服务
//	 * @throws Exception
//	 */
//	public void run() throws Exception{
//		EventLoopGroup bossGroup = new NioEventLoopGroup();
//		EventLoopGroup workerGroup = new NioEventLoopGroup();
//		try{
//			ServerBootstrap bootstrap = new ServerBootstrap();
//			bootstrap.group(bossGroup, workerGroup)
//				.channel(NioServerSocketChannel.class)
//				.childHandler(new ChannelInitializer<SocketChannel>() {
//	                @Override
//	                public void initChannel(SocketChannel ch) throws Exception {
//	                   ch.pipeline().addLast(new HttpRequestDecoder())   
//	                   		.addLast(new HttpResponseEncoder())   
//	                   		.addLast(new HttpContentCompressor())
//	                        .addLast(new UploadChunkServerHandler());
//	                }
//	            });
//	             
//			ChannelFuture future = bootstrap.bind(this.config.getPort()).sync();
//			
//			logger.info("Netty-http server listening on port " + this.config.getPort());
//			
//			future.channel().closeFuture().sync();
//		}finally{
//			bossGroup.shutdownGracefully();
//			workerGroup.shutdownGracefully();
//		}
//	}
//	
//	public static void main(String[] args) throws Exception {
//		HttpConfig config = new HttpConfig();
//		config.setUploadTempleDirectory("d:\\666\\");
//		config.setPort(8888);
//		new UploadChunkServer(config).run();
//	}
//	@Override
//	public String toString() {
//		return "HttpUploadServer [config=" + config + "]";
//	}
//}
