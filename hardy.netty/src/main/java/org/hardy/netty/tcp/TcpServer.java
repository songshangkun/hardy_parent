package org.hardy.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
 
/**
 * 这个server是一个TCP服务，可以使用spring配置成bean启动
 * 接受远程的string字符串调用服务相关的spring方法并返回
 * json或者对象，解码使用LineBasedFrameDecoder和DelimiterBasedFrameDecoder
 * @author sundyn
 *
 */
public class TcpServer {

	public static final String LineBasedFrameDecoder = "LineBasedFrameDecoder";
	
    public static final String DelimiterBasedFrameDecoder = "DelimiterBasedFrameDecoder";
	
	public static final String JavaSerialize = "JavaSerialize";
	
	public static final String Marshalling = "Marshalling";
	
	private int port = 8080;
	
	private String host = "localhost";
	
	private String decoderType = LineBasedFrameDecoder;
 
	private int exceptionSize = 1024;
	
	private int OPE_SO_BACKLOG = 1024;
	
	private String delim = "#";
	
	private MyServerHandler handler ;
	/**
	 * 执行内部handler的处理对象接口
	 */
	private ExcuteServer excuteServer;
	
	private int maxObjectSize = 1024;
	/**
	 * 在发送后关闭连接
	 */
	private boolean afterSendCloseCtx = false;
	
	
	/**
	 * 是否在发送后关闭连接
	 */
	public boolean isAfterSendCloseCtx() {
		return afterSendCloseCtx;
	}
    /**
     * 是否在发送后关闭连接,只对excuteServer接口的isFisPreexcute()=false时有效
     * @param afterSendCloseCtx
     */
	public void setAfterSendCloseCtx(boolean afterSendCloseCtx) {
		this.afterSendCloseCtx = afterSendCloseCtx;
	}

	public ExcuteServer getExcuteServer() {
		return excuteServer;
	}

	public void setExcuteServer(ExcuteServer excuteServer) {
		this.excuteServer = excuteServer;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDecoderType() {
		return decoderType;
	}

	public void setDecoderType(String decoderType) {
		this.decoderType = decoderType;
	}

	public int getExceptionSize() {
		return exceptionSize;
	}

	public void setExceptionSize(int exceptionSize) {
		this.exceptionSize = exceptionSize;
	}

	public String getDelim() {
		return delim;
	}

	public void setDelim(String delim) {
		this.delim = delim;
	}

	 

	public int getOPE_SO_BACKLOG() {
		return OPE_SO_BACKLOG;
	}
	public void setOPE_SO_BACKLOG(int oPE_SO_BACKLOG) {
		OPE_SO_BACKLOG = oPE_SO_BACKLOG;
	}
	public MyServerHandler getHandler() {
		return handler;
	}
	public void setHandler(MyServerHandler handler) {
		this.handler = handler;
	}
	public int getMaxObjectSize() {
		return maxObjectSize;
	}
	public void setMaxObjectSize(int maxObjectSize) {
		this.maxObjectSize = maxObjectSize;
	}
	/**
	 * 绑定端口
	 * @throws InterruptedException
	 */
	public void bind() throws InterruptedException{		 
		 EventLoopGroup bossGroup = new NioEventLoopGroup();		 
		 EventLoopGroup workGroup = new NioEventLoopGroup();      
		 try{			 
			 ServerBootstrap b = new ServerBootstrap();
			 b.group(bossGroup, workGroup) 
			 .channel(NioServerSocketChannel.class) 
			.option(ChannelOption.SO_BACKLOG, OPE_SO_BACKLOG) 
			 .childHandler(new ChildChannelHandler()); 
			 ChannelFuture f = b.bind(port).sync(); 
			 f.channel().closeFuture().sync(); 
		 }finally{
			 bossGroup.shutdownGracefully();  
			 workGroup.shutdownGracefully();
		 }
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception {
			if(TcpServer.LineBasedFrameDecoder.equalsIgnoreCase(decoderType)){
				socketChannel.pipeline().addLast(new LineBasedFrameDecoder(exceptionSize)); //添加行分割解码器
				socketChannel.pipeline().addLast(new StringDecoder()); //添加转换字符串编码器
			}
			if(TcpServer.DelimiterBasedFrameDecoder.equalsIgnoreCase(decoderType)){
				ByteBuf delimiter = Unpooled.copiedBuffer(delim.getBytes());
				socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(exceptionSize, delimiter));
				socketChannel.pipeline().addLast(new StringDecoder());  
			}
			if(TcpServer.JavaSerialize.equalsIgnoreCase(decoderType)){	 
				socketChannel.pipeline().addLast(new ObjectDecoder(maxObjectSize*1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				socketChannel.pipeline().addLast(new ObjectEncoder()); 
			}
			if(TcpServer.Marshalling.equalsIgnoreCase(decoderType)){ 
				socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder(maxObjectSize));
				socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());  
			}
			handler = new MyServerHandler(TcpServer.this);
			/**
			 * TimeServerHandler是继承ChannelHandlerAdapter的一个子类
			 */
			socketChannel.pipeline().addLast(handler); //添加自定义的TimeServerHandler类
		}
		
	}
	
	 
}
