package org.hardy.netty.tcp2;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hardy.netty.tcp.MarshallingCodeCFactory;
import org.hardy.netty.tcp2.handler.TcpClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class TcpClient {
	
	Logger logger = Logger.getLogger(TcpClient.class);
 	
    private SocketChannel socketChannel;
    
    
    public TcpClient(ConfigClient config) throws InterruptedException {
        start(config);
    }
    
    public TcpClient() throws InterruptedException {
    	 	start(new ConfigClient());
    }
    
    private void start(final ConfigClient config) throws InterruptedException {
		EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(eventLoopGroup);
		 bootstrap.channel(NioSocketChannel.class);  
		 bootstrap.option(ChannelOption.TCP_NODELAY, true);
	        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);        
	        bootstrap.remoteAddress(config.getHost(),config.getPort());
	        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					if(Configuration.JavaSerialize.equalsIgnoreCase(config.getDecoderType())){
						ch.pipeline().addLast(new ObjectDecoder(config.getMaxObjectSize()*1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));	
						ch.pipeline().addLast(new ObjectEncoder());
					}
					if(Configuration.Marshalling.equalsIgnoreCase(config.getDecoderType())){
						ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder(config.getMaxObjectSize()));	
						ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
					}
					
					if(config.getR_timeOut()>0||config.getW_timeOut()>0||config.getBothTimeOut_r_w()>0){	
						ch.pipeline().addLast("idleStateHandler",new IdleStateHandler(config.getR_timeOut(),config.getW_timeOut(),config.getBothTimeOut_r_w(),TimeUnit.MILLISECONDS));
					}
					ch.pipeline().addLast(new TcpClientHandler(config));
				}
			});
	        ChannelFuture future =bootstrap.connect(config.getHost(),config.getPort()).sync();
	        if (future.isSuccess()) {
	        	   this.socketChannel = (SocketChannel)future.channel();
	        System.out.println("connect server  成功---------");
	        }
	        //此处不能阻塞，否则无法继续
//	        future.channel().closeFuture().sync();

	}
    /**
     * 通过该方法使客户端进入阻塞状态。
     * 已被持续发送信息
     */
    public void attend(){
        new Thread(new Runnable() {
			@Override
			public void run() { 
				try {
					TcpClient.this.socketChannel.closeFuture().sync();
				} catch (InterruptedException e) {
					logger.error("启动客户端进入阻塞异常", e.getCause());
					e.printStackTrace();
				}
				logger.info("启动服务进入阻塞");
			}
		}).start();
    }
    
    public void sendMessage(Message message){
        TcpClient.this.socketChannel.writeAndFlush(message);   
    }
      

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}
	
}
