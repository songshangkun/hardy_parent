package org.hardy.webhelper;

import java.util.Arrays;
import java.util.Collection;
/**
 * 类似于hibernate帮助类的Entity<T> 
 *  Entity是hibernate在查询对象时自动搜索数量,
 *  而PageData<T>的作用是为了在分页查询后,将数据呈现给前台页面
 * @author songshangkun
 *
 * @param <T>
 */
public class PageData<T> {
      /**
       * 总数据量
       */
      private int totalCount;
      /**
       * 每页数据量
       */
      private int pageSize;
      /**
       * 当前页码
       */
      private int currentPage;
      /**
       * 总页码
       */
      private int totalPage;
      /**
       * 快速链接页码
       */
      private int[] quickLink;
      /**
       * 快速链接一半的数量
       */
      private int quickLinkSize;
      /**
       * 数据
       */
      private Collection<T>  datas; 
      /**
       * 设置完参数后准备
       */
      public void prepar(){
           if(this.currentPage==0) throw new RuntimeException("currentPage is NULL");
           if(this.pageSize==0)throw new RuntimeException("pageSize is NULL");
           this.totalPage = (this.totalCount-1)/this.pageSize+1;
           if(this.quickLinkSize!=0){
                int left  = 1; int right = this.totalPage;
                if((this.currentPage-this.quickLinkSize)>0){
                       left = this.currentPage-this.quickLinkSize ;
                }
                if((this.currentPage+this.quickLinkSize<this.totalPage+1)){
                     right = this.currentPage+this.quickLinkSize;
                }
                this.quickLink = new int[right-left+1];
                for(int i=0;i<this.quickLink.length;i++){
                     this.quickLink[i] = left+i;
                }
                
            }
      }

    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    public Collection<T> getDatas() {
        return datas;
    }
    public void setDatas(Collection<T> datas) {
        this.datas = datas;
    }
    
    public void setQuickLinkSize(int quickLinkSize) {
        this.quickLinkSize = quickLinkSize;
    }
    
    public int[] getQuickLink() {
        return quickLink;
    }

   
    
    @Override
    public String toString() {
        return "PageData [totalCount=" + totalCount + ", pageSize=" + pageSize + ", currentPage=" + currentPage
                + ", totalPage=" + totalPage + ", quickLink=" + Arrays.toString(quickLink) + ", quickLinkSize="
                + quickLinkSize + ", datas=" + datas + "]";
    }

}


