//package com.song.util.netty.http.upload.chunk;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.HttpContent;
//import io.netty.handler.codec.http.HttpObject;
//import io.netty.handler.codec.http.HttpRequest;
//import io.netty.handler.codec.http.HttpResponseStatus;
//import io.netty.handler.codec.http.LastHttpContent;
//import io.netty.handler.codec.http.multipart.Attribute;
//import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
//import io.netty.handler.codec.http.multipart.DiskAttribute;
//import io.netty.handler.codec.http.multipart.DiskFileUpload;
//import io.netty.handler.codec.http.multipart.FileUpload;
//import io.netty.handler.codec.http.multipart.HttpData;
//import io.netty.handler.codec.http.multipart.HttpDataFactory;
//import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
//import io.netty.handler.codec.http.multipart.InterfaceHttpData;
//import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.logging.Logger;
//
//import com.song.util.netty.http.help.ResponseHelper;
//
//public class UploadChunkServerHandler extends SimpleChannelInboundHandler<HttpObject> {
//	
//	 private static final Logger logger = Logger.getLogger(UploadChunkServerHandler.class.getName());
//
//
//
//	    private HttpData partialContent;
//
//	    private final StringBuilder responseContent = new StringBuilder();
//
//	    private static final HttpDataFactory factory =
//	            new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed
//
//	    private HttpPostRequestDecoder decoder;
//
//	    static {
//	        DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file
//	                                                         // on exit (in normal
//	                                                         // exit)
//	        DiskFileUpload.baseDirectory = null; // system temp directory
//	        DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on
//	                                                        // exit (in normal exit)
//	        DiskAttribute.baseDirectory = null; // system temp directory
//	    }
//
//	    @Override
//	    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//	        if (decoder != null) {
//	            decoder.cleanFiles();
//	        }
//	    }
//
//	    @Override
//	    public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
//	        if (msg instanceof HttpRequest) {
//	            HttpRequest request =  (HttpRequest) msg;
//	             decoder = new HttpPostRequestDecoder(factory, request);   
//	        }
//
//	        // check if the decoder was constructed before
//	        // if not it handles the form get
//	        if (decoder != null) {
//	            if (msg instanceof HttpContent) {
//	                // New chunk is received
//	                HttpContent chunk = (HttpContent) msg;
//	               
//	                    decoder.offer(chunk);
//	                
//	                responseContent.append('o');
//	                //*************************************
//	                //readHttpDataChunkByChunk();
//	                while (decoder.hasNext()) {
//		                InterfaceHttpData data = decoder.next();
//		                if (data != null) {
//		                    // check if current HttpData is a FileUpload and previously set as partial
//		                    if (partialContent == data) {
//		                        logger.info(" 100% (FinalSize: " + partialContent.length() + ")");
//		                        partialContent = null;
//		                    }
//		                    try {
//		                    	 if (data.getHttpDataType() == HttpDataType.Attribute) {
//		             	            Attribute attribute = (Attribute) data;
//		             	            String value = attribute.getValue();
//		             	            System.out.println("value=="+value);
//		             	           }else{   
//		             		            if (data.getHttpDataType() == HttpDataType.FileUpload) {
//		             		                FileUpload fileUpload = (FileUpload) data;
//		             		                if (fileUpload.isCompleted()) {
//		             		                	 try { System.out.println("aaaaaa");
//		             								fileUpload.renameTo(new File("d:\\666\\aaa.jpg"));
//		             							} catch (IOException e) {
//		             								// TODO Auto-generated catch block
//		             								e.printStackTrace();
//		             							}    
//		             		                } else {
//		             		                    responseContent.append("\tFile to be continued but should not!\r\n");
//		             		                }
//		             		            }
//		             		          
//		             	           }
//		                      //**********************************
//		                    } finally {
//		                        data.release();
//		                    }
//		                }
//		            }	     
//	                //*************************************
//	                // example  of reading only if at the end
//	                if (chunk instanceof LastHttpContent) {
//	                	ResponseHelper.sendError(ctx, HttpResponseStatus.OK);
//	        	        decoder.destroy();
//	        	        decoder = null;
//	                }
//	            }
//	        } else {
//	            //writeResponse(ctx.channel());
//	        	ResponseHelper.sendError(ctx, HttpResponseStatus.BAD_REQUEST);
//	        }
//	    }
//
//	   
//
//	    
//
////	    private void writeResponse(Channel channel) {
////	        // Convert the response content to a ChannelBuffer.
////	        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
////	        responseContent.setLength(0);
////
////	        // Decide whether to close the connection or not.
////	        boolean close = request.headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE, true)
////	                || request.protocolVersion().equals(HttpVersion.HTTP_1_0)
////	                && !request.headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE, true);
////
////	        // Build the response object.
////	        FullHttpResponse response = new DefaultFullHttpResponse(
////	                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
////	        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
////
////	        if (!close) {
////	            // There's no need to add 'Content-Length' header
////	            // if this is the last response.
////	            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
////	        }
////
////	        Set<Cookie> cookies;
////	        String value = request.headers().get(HttpHeaderNames.COOKIE).toString();
////	        if (value == null) {
////	            cookies = Collections.emptySet();
////	        } else {
////	            cookies = ServerCookieDecoder.decode(value);
////	        }
////	        if (!cookies.isEmpty()) {
////	            // Reset the cookies if necessary.
////	            for (Cookie cookie : cookies) {
////	                response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.encode(cookie));
////	            }
////	        }
////	        // Write the response.
////	        ChannelFuture future = channel.writeAndFlush(response);
////	        // Close the connection after the write operation is done if necessary.
////	        if (close) {
////	            future.addListener(ChannelFutureListener.CLOSE);
////	        }
////	    }
//
////	    private void writeMenu(ChannelHandlerContext ctx) {
////	        // print several HTML forms
////	        // Convert the response content to a ChannelBuffer.
////	        responseContent.setLength(0);
////
////	        // create Pseudo Menu
////	        responseContent.append("<html>");
////	        responseContent.append("<head>");
////	        responseContent.append("<title>Netty Test Form</title>\r\n");
////	        responseContent.append("</head>\r\n");
////	        responseContent.append("<body bgcolor=white><style>td{font-size: 12pt;}</style>");
////
////	        responseContent.append("<table border=\"0\">");
////	        responseContent.append("<tr>");
////	        responseContent.append("<td>");
////	        responseContent.append("<h1>Netty Test Form</h1>");
////	        responseContent.append("Choose one FORM");
////	        responseContent.append("</td>");
////	        responseContent.append("</tr>");
////	        responseContent.append("</table>\r\n");
////
////	        // GET
////	        responseContent.append("<CENTER>GET FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
////	        responseContent.append("<FORM ACTION=\"/formget\" METHOD=\"GET\">");
////	        responseContent.append("<input type=hidden name=getform value=\"GET\">");
////	        responseContent.append("<table border=\"0\">");
////	        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"info\" size=10></td></tr>");
////	        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"secondinfo\" size=20>");
////	        responseContent
////	                .append("<tr><td>Fill with value: <br> <textarea name=\"thirdinfo\" cols=40 rows=10></textarea>");
////	        responseContent.append("</td></tr>");
////	        responseContent.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
////	        responseContent.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
////	        responseContent.append("</table></FORM>\r\n");
////	        responseContent.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
////
////	        // POST
////	        responseContent.append("<CENTER>POST FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
////	        responseContent.append("<FORM ACTION=\"/formpost\" METHOD=\"POST\">");
////	        responseContent.append("<input type=hidden name=getform value=\"POST\">");
////	        responseContent.append("<table border=\"0\">");
////	        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"info\" size=10></td></tr>");
////	        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"secondinfo\" size=20>");
////	        responseContent
////	                .append("<tr><td>Fill with value: <br> <textarea name=\"thirdinfo\" cols=40 rows=10></textarea>");
////	        responseContent.append("<tr><td>Fill with file (only file name will be transmitted): <br> "
////	                + "<input type=file name=\"myfile\">");
////	        responseContent.append("</td></tr>");
////	        responseContent.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
////	        responseContent.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
////	        responseContent.append("</table></FORM>\r\n");
////	        responseContent.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
////
////	        // POST with enctype="multipart/form-data"
////	        responseContent.append("<CENTER>POST MULTIPART FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
////	        responseContent.append("<FORM ACTION=\"/formpostmultipart\" ENCTYPE=\"multipart/form-data\" METHOD=\"POST\">");
////	        responseContent.append("<input type=hidden name=getform value=\"POST\">");
////	        responseContent.append("<table border=\"0\">");
////	        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"info\" size=10></td></tr>");
////	        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"secondinfo\" size=20>");
////	        responseContent
////	                .append("<tr><td>Fill with value: <br> <textarea name=\"thirdinfo\" cols=40 rows=10></textarea>");
////	        responseContent.append("<tr><td>Fill with file: <br> <input type=file name=\"myfile\">");
////	        responseContent.append("</td></tr>");
////	        responseContent.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
////	        responseContent.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
////	        responseContent.append("</table></FORM>\r\n");
////	        responseContent.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
////
////	        responseContent.append("</body>");
////	        responseContent.append("</html>");
////
////	        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
////	        // Build the response object.
////	        FullHttpResponse response = new DefaultFullHttpResponse(
////	                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
////
////	        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
////	        response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
////
////	        // Write the response.
////	        ctx.channel().writeAndFlush(response);
////	    }
//
//	    @Override
//	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//	       // logger.log(Level.WARNING, responseContent.toString(), cause);
//	        ctx.channel().close();
//	    }
//
//		
//    
//}
