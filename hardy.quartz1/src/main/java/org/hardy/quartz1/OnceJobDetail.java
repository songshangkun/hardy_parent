package org.hardy.quartz1;

import org.quartz.JobExecutionContext;
/**
 * 在OnceJob中运行的实际方法接口
 * @author song
 *
 */
public interface OnceJobDetail {
     /**
      * 实际运行的Job方法体
      * @param context
      */
	public void execute(JobExecutionContext context);
}
