package org.hardy.netty.tcp;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public class MarshallingCodeCFactory {
       
	/**
	 * 创建Marshalling解码器
	 * @param maxObjectSize
	 * @return
	 */
	public static MarshallingDecoder buildMarshallingDecoder(int maxObjectSize){
		final MarshallerFactory  marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		MarshallingDecoder decoder = new MarshallingDecoder(provider, maxObjectSize);
		return decoder;
	}
	
	public static MarshallingEncoder buildMarshallingEncoder(){
		final MarshallerFactory  marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		return encoder;
	}
		  /** 
	     * 创建Jboss Marshaller 
	     * @throws IOException 
	     */  
	    public static Marshaller buildMarshalling() throws IOException {  
	        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");  
	        final MarshallingConfiguration configuration = new MarshallingConfiguration();  
	        configuration.setVersion(5);  
	        Marshaller marshaller = marshallerFactory.createMarshaller(configuration);  
	        return marshaller;  
	    }  
	  
	    /** 
	     * 创建Jboss Unmarshaller 
	     * @throws IOException 
	     */  
	    public static Unmarshaller buildUnMarshalling() throws IOException {  
	        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");  
	        final MarshallingConfiguration configuration = new MarshallingConfiguration();  
	        configuration.setVersion(5);  
	        final Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);  
	        return unmarshaller;  
	    }
	 
}
