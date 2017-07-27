package org.hardy.netty.privateprotocol;

import java.util.HashMap;
import java.util.Map;

public final class Header {
       private int crcCode = 0xabef0101; //校验
       private int length; //消息长度 
       private long sessionId;//会话id
       private byte type;//消息长度 
       private byte priority; //优先级
       private Map<String, Object> attachment = new HashMap<>();
	public final int getCrcCode() {
		return crcCode;
	}
	public final void setCrcCode(int crcCode) {
		this.crcCode = crcCode;
	}
	public final int getLength() {
		return length;
	}
	public final void setLength(int length) {
		this.length = length;
	}
	public final long getSessionId() {
		return sessionId;
	}
	public final void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public final byte getType() {
		return type;
	}
	public final void setType(byte type) {
		this.type = type;
	}
	 
	public final byte getPriority() {
		return priority;
	}
	public final void setPriority(byte priority) {
		this.priority = priority;
	}
	public final Map<String, Object> getAttachment() {
		return attachment;
	}
	public final void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}
	@Override
	public String toString() {
		return "Header [crcCode=" + crcCode + ", length=" + length + ", sessionId=" + sessionId + ", type=" + type
				+ ", priority=" + priority + ", attachment=" + attachment + "]";
	}
       
       
       
}
