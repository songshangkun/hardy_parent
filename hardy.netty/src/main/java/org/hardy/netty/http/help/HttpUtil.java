package org.hardy.netty.http.help;



import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMessage;

public class HttpUtil {
	public static boolean isTransferEncodingChunked(HttpMessage message) {
        return message.headers().contains(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED, true);
    }
	
	 
	
}
