package com.app.model.response;

import lombok.*;
import java.util.*;
import static com.app.model.response.OperationResponse.*;

//@Data //for getters and setters
public class PageResponse extends OperationResponse {
  @Getter @Setter private boolean  first;
  @Getter @Setter private boolean  last;
  @Getter @Setter private int currentPageNumber;
  @Getter @Setter private int itemsInPage;
  @Getter @Setter private int pageSize;
  @Getter @Setter private int totalPages;
  @Getter @Setter private long totalItems;
  private List items;

  public void setPageStats(){
  }

  public void setPageTotal(int count, boolean setDefaultMessage){
    //this.items             = list;
    this.first             = true;
    this.last              = true;
    this.itemsInPage       = count;
    this.totalItems        = count;
    this.totalPages        = 1;
    this.pageSize          = count;
    if (setDefaultMessage == true){
      this.setOperationStatus(ResponseStatusEnum.SUCCESS);
      this.setOperationMessage("Total " + count + " items ");
    }
  }

}
