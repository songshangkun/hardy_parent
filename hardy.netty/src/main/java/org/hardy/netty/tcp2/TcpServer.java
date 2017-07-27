package org.hardy.netty.tcp2;



import org.hardy.netty.tcp.MarshallingCodeCFactory;
import org.hardy.netty.tcp2.handler.TcpServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;



public class TcpServer {
	
	private Configuration configuration ;
	
	  public TcpServer()  {
		   this.configuration = new Configuration();
	  }
      public TcpServer(Configuration config){
    	      this.configuration = config;
      }	  
	  public void  bind() throws InterruptedException{
	      	 EventLoopGroup bossGroup = new NioEventLoopGroup();		 
			 EventLoopGroup workGroup = new NioEventLoopGroup(); 
			 ServerBootstrap bootstrap=new ServerBootstrap();
		     try{   
			 bootstrap.group(bossGroup,workGroup)
		        .channel(NioServerSocketChannel.class) 
		        .option(ChannelOption.SO_BACKLOG, this.configuration.SO_BACKLOG())
		        .childOption(ChannelOption.SO_KEEPALIVE, true) 
		        .childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline p = ch.pipeline();
						if(Configuration.JavaSerialize.equalsIgnoreCase(TcpServer.this.configuration.getDecoderType())){	 
							p.addLast(new ObjectDecoder(TcpServer.this.configuration.getMaxObjectSize()*1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
							p.addLast(new ObjectEncoder()); 
						}
						if(Configuration.Marshalling.equalsIgnoreCase(TcpServer.this.configuration.getDecoderType())){ 
							p.addLast(MarshallingCodeCFactory.buildMarshallingDecoder(TcpServer.this.configuration.getMaxObjectSize()));
							p.addLast(MarshallingCodeCFactory.buildMarshallingEncoder());  
						}
		                p.addLast(new TcpServerHandler(TcpServer.this.configuration));
					}
				});
		        ChannelFuture future = bootstrap.bind(this.configuration.getPort()).sync(); 
		        if(future.isSuccess()){
		            System.out.println("server start---------------");
		        }
		        future.channel().closeFuture().sync();  
		     }finally{
				 bossGroup.shutdownGracefully();  
				 workGroup.shutdownGracefully();
			 }
	    }

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
