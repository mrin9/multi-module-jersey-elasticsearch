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
        calculatePageStats(totalItems, pageSize);
        currentPageNumber = 1;
        this.setSuccessMessage("Total " + totalItems + " items ");
    }

    public PageResponse(int totalItems, int pageSize, int currentPageNumber) {
        calculatePageStats(totalItems, pageSize);
        this.currentPageNumber = (currentPageNumber > 0 && currentPageNumber <= totalPages)? currentPageNumber : 1;
        this.setSuccessMessage("Total " + totalItems + " items ");
    }

    private void calculatePageStats(int totalItems, int pageSize){
        if (totalItems>0 && pageSize > 0){
            this.totalItems = totalItems;
            this.pageSize =  pageSize;
            this.totalPages = (int)(totalItems/pageSize) + (totalItems%pageSize==0?0:1);
            this.currentPageNumber = 1;
        }
    }
}
