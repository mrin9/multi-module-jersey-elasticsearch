import { Component, OnInit,TemplateRef, ViewChild } from '@angular/core';
import { ProductService } from '../../services/api/product.service';
import { Router } from '@angular/router';

@Component({
	selector: 's-products-pg',
	templateUrl: './products.component.html',
    styleUrls: [ './products.scss'],
})

export class ProductsComponent implements OnInit {

    @ViewChild('productDiscontinuedTpl') productDiscontinuedTpl: TemplateRef<any>;

    //ngx-Datatable Variables
    columns:any[];
    rows:any[];


    constructor( private router: Router, private productService: ProductService) {}
    ngOnInit() {
        var me = this;
        me.getProductData();
        this.columns=[
            {prop:"productId"     , name: "Code"              , width:60  },
            {prop:"productName"   , name: "Name"              , width:210 },
            {prop:"listPrice"     , name: "List Price"        , width:100 },
            {prop:"productType"   , name: "Category"          , width:100 },
            {prop:"quantityOnHand", name: "Quantity Available", width:120 },
            {prop:"reorderLevel"  , name: "Reorder level"     , width:110 }
        ];

    }

    getProductData() {
        this.productService.getProducts().subscribe( (policyData) => {
            this.rows = policyData;
        });
    }


}
