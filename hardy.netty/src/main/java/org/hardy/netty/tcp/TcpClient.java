package org.hardy.netty.tcp;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class TcpClient {
	 
	 public static final String LineBasedFrameDecoder = "LineBasedFrameDecoder";
		
	 public static final String DelimiterBasedFrameDecoder = "DelimiterBasedFrameDecoder";
	 
	 public static final String JavaSerialize = "JavaSerialize";
	 
	 public static final String Marshalling = "Marshalling";
	
	 private String decoderType = LineBasedFrameDecoder;
	 
	 private ChannelHandlerAdapter myHandlerAdapter ;
	 /**
	  * 针对传输字符串,LineBasedFrameDecoder和DelimiterBasedFrameDecoder
	  */
	 private int exceptionSize = 1024;
		
	 private String delim = "#";
	 
	 private Object message;
			
	 private ExcuteClient excuteClient;
	 /**
	  * 针对传输对象Marshalling,JavaSerialize
	  */
	 private int maxObjectSize = 1024;
	 
	 private long timeout = 0l;
	 
	 private ReadTimeOut readTimeOut;
	 
	 private WriteTimeOut writeTimeOut;
	 
	 private AutorTimeOut autorTimeOut;
	 
	 private boolean closeAfterRead = false;
	 
	 private ChannelFuture channelFuture;
	 
	 private Bootstrap bootstrap;
	 
	 private String host = "localhost";
	 
	 private int port = 8080;
	 
	 public void connect() throws InterruptedException{
		     connect(this.host, this.port);
	 }
 
	public void connect(String host,int port) throws InterruptedException {
		this.myHandlerAdapter = new MyClientHandler(this);
		this.host = host; this.port = port;
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					if(TcpClient.LineBasedFrameDecoder.equalsIgnoreCase(TcpClient.this.decoderType)){
						socketChannel.pipeline().addLast(new io.netty.handler.codec.LineBasedFrameDecoder(exceptionSize));	
						socketChannel.pipeline().addLast(new StringDecoder());
					}
					if(TcpClient.DelimiterBasedFrameDecoder.equalsIgnoreCase(TcpClient.this.decoderType)){
						ByteBuf delimite = Unpooled.copiedBuffer(TcpClient.this.delim.getBytes());
						socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(exceptionSize,delimite));	
						socketChannel.pipeline().addLast(new StringDecoder());
					}
					if(TcpClient.JavaSerialize.equalsIgnoreCase(TcpClient.this.decoderType)){
						socketChannel.pipeline().addLast(new ObjectDecoder(maxObjectSize*1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));	
						socketChannel.pipeline().addLast(new ObjectEncoder());
					}
					if(TcpClient.Marshalling.equalsIgnoreCase(TcpClient.this.decoderType)){
						socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder(maxObjectSize));	
						socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
					}
					if(timeout>0)socketChannel.pipeline().addLast("idleStateHandler",new IdleStateHandler(timeout,timeout,timeout,TimeUnit.MILLISECONDS));
					socketChannel.pipeline().addLast(myHandlerAdapter);
				}
			});
			this.channelFuture = bootstrap.connect(host, port).sync();  	
			this.channelFuture.channel().closeFuture().sync();
		}finally{
			group.shutdownGracefully();
		}
	}
	 
	public ChannelFuture getChannelFuture(){
    	   return this.channelFuture;
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


	public ExcuteClient getExcuteClient() {
		return excuteClient;
	}

	public void setExcuteClient(ExcuteClient excuteClient) {
		this.excuteClient = excuteClient;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getTimeout() {
		return timeout;
	}

	public ReadTimeOut getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(ReadTimeOut readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public WriteTimeOut getWriteTimeOut() {
		return writeTimeOut;
	}

	public void setWriteTimeOut(WriteTimeOut writeTimeOut) {
		this.writeTimeOut = writeTimeOut;
	}

	public AutorTimeOut getAutorTimeOut() {
		return autorTimeOut;
	}

	public int getMaxObjectSize() {
		return maxObjectSize;
	}

	public void setMaxObjectSize(int maxObjectSize) {
		this.maxObjectSize = maxObjectSize;
	}

	public void setAutorTimeOut(AutorTimeOut autorTimeOut) {
		this.autorTimeOut = autorTimeOut;
	}
	
	public void setALLTimeOut(ReadWriteTimeOut timeOut) {
		this.readTimeOut = timeOut;
		this.writeTimeOut = timeOut;
		this.autorTimeOut = timeOut;
	}

	public boolean isCloseAfterRead() {
		return closeAfterRead;
	}

	public void setCloseAfterRead(boolean closeAfterRead) {
		this.closeAfterRead = closeAfterRead;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	public ChannelHandlerAdapter getMyHandlerAdapter() {
		return myHandlerAdapter;
	}


	public void setMyHandlerAdapter(ChannelHandlerAdapter myHandlerAdapter) {
		this.myHandlerAdapter = myHandlerAdapter;
	}
	
}
