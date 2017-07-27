package org.hardy.sqlhelper.holographic.translation.inf;

/**
 * 经过翻译后的语句和条件参数
 * @author song
 *
 */
public class Condition<T> {
	    /**
	     * SQL语句
	     */
	    protected String phrase;
	     /**
	      * 参数集合
	      */
	    protected T t;

		public String getPhrase() {
			return phrase;
		}

		public void setPhrase(String phrase) {
			this.phrase = phrase;
		}

		public T getT() {
			return t;
		}

		public void setT(T t) {
			this.t = t;
		}
	    
	    
}
