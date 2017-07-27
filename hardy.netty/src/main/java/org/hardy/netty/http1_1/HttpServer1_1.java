package org.hardy.netty.http1_1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.multipart.DiskFileUpload;

import java.util.Map;

import org.hardy.netty.http.AutoWaveRequestMapping;
import org.hardy.netty.http.HttpConfig;
import org.hardy.netty.http.ServletContainer;
import org.hardy.netty.http.ServletContainer.UrlMethodInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * http主服务
 * @author 09
 *
 */
public class HttpServer1_1 {
     
	private static final Logger logger = LoggerFactory.getLogger(HttpServer1_1.class);
	
	/**
	 * 服务配置文件
	 */
	private HttpConfig config;
	
	public HttpServer1_1(HttpConfig config){
		this.config = config;
	}
	public HttpServer1_1(){
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
	                   ch.pipeline().addLast(new HttpRequestDecoder())   
	                   		.addLast(new HttpResponseEncoder())   
	                   		.addLast(new HttpContentCompressor())
	                        .addLast(new HttpServer1_1handler(HttpServer1_1.this.config));
	                }
	            });
	             
			ChannelFuture future = bootstrap.bind(this.config.getPort()).sync();
			
			logger.info("Netty-http server listening on port " + this.config.getPort());
			
			future.channel().closeFuture().sync();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
//	static {
//        DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file
//                                                         // on exit (in normal
//                                                         // exit)
//        DiskFileUpload.baseDirectory = null; // system temp directory
//        DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on
//                                                        // exit (in normal exit)
//        DiskAttribute.baseDirectory = null; // system temp directory
//    }
	
	public static void main(String[] args) throws Exception {
		HttpConfig config = new HttpConfig();
		 new AutoWaveRequestMapping(new String[]{"com.song.util.netty.test"});
		 Map<String, UrlMethodInfo> map = ServletContainer.getServletMap();
		 for(String s:map.keySet()){
			 System.out.println(s); 
		 }
		 System.out.println(DiskFileUpload.baseDirectory);	
		config.setPort(8888);
		new HttpServer1_1(config).run();
	}
	@Override
	public String toString() {
		return "HttpUploadServer [config=" + config + "]";
	}
}
