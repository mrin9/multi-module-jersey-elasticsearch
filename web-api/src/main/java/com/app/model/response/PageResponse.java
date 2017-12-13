package com.app.model.response;

import lombok.*;

@Data
public class PageResponse extends BaseResponse {
    private int totalItems;
    private int pageSize;
    private int totalPages;
    private int currentPageNumber;
    private int itemsInPage;  // for last page ItemsInPage and PageSize can be different

    public PageResponse() {}
    
    public PageResponse(int totalItems, int pageSize) {
        setPageStats(totalItems, pageSize);
        currentPageNumber = 1;
    }

    public PageResponse(int totalItems, int pageSize, int currentPageNumber) {
        setPageStats(totalItems, pageSize);
        this.currentPageNumber = (currentPageNumber > 0 && currentPageNumber <= totalPages)? currentPageNumber : 1;
    }

    public void setPageStats(int totalItems, int pageSize){
        if (totalItems>0 && pageSize > 0){
            this.totalItems = totalItems;
            this.pageSize =  pageSize;
            this.totalPages = (int)(totalItems/pageSize) + (totalItems%pageSize==0?0:1);
            this.currentPageNumber = 1;
            this.setSuccessMessage("Total " + this.totalItems + " items ");
        }
    }
    
    public void setPageStats(int totalItems, int pageSize, int from, int itemsInPage){
        if (totalItems>0 && pageSize > 0 && from <= totalItems){
            this.totalItems = totalItems;
            this.pageSize =  pageSize;
            this.totalPages = (int)(totalItems/pageSize)  + (totalItems%pageSize==0?0:1);
            if (from == 0){
                this.currentPageNumber = 1;
            }
            else{
                this.currentPageNumber = (int)(from/pageSize) + (from%pageSize==0?0:1);
            }
            this.itemsInPage = itemsInPage;
            this.setSuccessMessage("Total " + this.totalItems + " items ");
        }
    }
    
    
    
}
