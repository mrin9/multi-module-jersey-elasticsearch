import { Component, OnInit,TemplateRef, ViewChild,HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/api/user.service';

@Component({
	selector: 's-customers-pg',
	templateUrl: './customers.component.html',
    styleUrls: [ './customers.scss'],
})

export class CustomersComponent implements OnInit {

    columns:any[];
    rows:any[];
    pageSize:number=5;
    from:number=0;
    isLastPageLoaded:boolean=false;
    isLoading:boolean=false;

    constructor(private router: Router, private userService: UserService) { }

    ngOnInit() {
        let me = this;
        me.getPageData();

        this.columns=[
            {prop:"userId"   , name: "ID"      , width:80  },
            {prop:"userName" , name: "Name"    , width:180 },
            {prop:"email"    , name: "Email"   , width:220 },
            {prop:"address"  , name: "Address" , width:220 },
        ];
    }

    getPageData(isAppend:boolean=false) {

        if (this.isLastPageLoaded===false){
            let me = this;
            me.isLoading=true;
            this.userService.getCustomers(this.from,this.pageSize).subscribe((data) => {
                me.isLastPageLoaded = (data.totalPages === data.currentPageNumber ? true:false);
                me.from = (data.currentPageNumber * data.pageSize);
                if (isAppend===true){
                    me.rows = me.rows.concat(data.items);
                }
                else{
                    me.rows = data.items;
                }
                me.isLoading=false;
            });
        }
    }

    onScroll() {
        console.log("bottom")
        if (this.isLoading===false){
            this.getPageData(true);
        }
	}

}
