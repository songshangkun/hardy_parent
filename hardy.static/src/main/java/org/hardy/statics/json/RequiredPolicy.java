package org.hardy.statics.json;


/**
 *
 */
public enum RequiredPolicy {
    /**
     * 基本属性和String,Date
     */
    ESSENTIAL,
    /**
     * 全部对象
     */
    ALL,
    /**
     * 标注@ToJson
     */
    ANNOTATION,
    /**
      拥有GET方法
     */
    GET
}
