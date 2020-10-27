package com.northbund.vblog.common;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lk
 **/

/**
 * 分页bean
 */
@Data
public class PageBean<T>{
    /**
     *当前页
     */
    private Integer currentPage = 1;
    /**
     *每页显示的总条数
     */
    private Integer pageSize = 10;
    /**
     *总条数
     */
    private Integer totalNum;
    /**
     *是否有下一页
     */
    private Integer isMore;
    /**
     *总页数
     */
    private Integer totalPage;
    /**
     * 开始索引
     */
    private Integer startIndex;
    /**
     *分页结果
     */
    private List<T> result;

    public PageBean() {

    }

    public PageBean(Integer currentPage, Integer pageSize, Integer totalNum) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = (this.totalNum+this.pageSize-1)/this.pageSize;
        this.startIndex = (this.currentPage-1)*this.pageSize;
        this.isMore = this.currentPage >= this.totalPage?0:1;
    }

    public PageBean(List<T> list) {
        if(null == list || list.size() == 0){
            new PageBean();
        }
        if (list instanceof Page) {
            Page page = (Page) list;
            this.result=page;
            this.currentPage = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.totalNum = Integer.parseInt(page.getTotal()+"");
            this.totalPage = (this.totalNum+this.pageSize-1)/this.pageSize;
            this.startIndex = (this.currentPage-1)*this.pageSize;
            this.isMore = this.currentPage >= this.totalPage?0:1;
        }

    }


}