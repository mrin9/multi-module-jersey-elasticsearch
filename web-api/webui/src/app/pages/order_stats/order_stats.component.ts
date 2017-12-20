import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/api/order.service';
import { ProductService } from '../../services/api/product.service';
import { Router } from '@angular/router';
import { NgxChartsModule} from '@swimlane/ngx-charts';
import 'rxjs/add/operator/mergeMap';


@Component({
	selector: 's-order_stats-pg',
	templateUrl: './order_stats.component.html',
    styleUrls: [ './order_stats.scss'],
})

export class OrderStatsComponent implements OnInit {
    view: any[] = [460, 180];
    ordersByStatusData : any[] = [];
    ordersByPaymentData: any[] = [];
    ordersByCountryData: any[] = [];
    colorScheme = {
        domain: ['#007cbb', '#61c673', '#ff8e28', '#ef2e2e']
    };
    barColorScheme = {
        domain: ['#007cbb']
    }

    constructor(private router: Router, private orderService: OrderService, private productService: ProductService) { }

    ngOnInit() {
        var me = this;
        this.getPageData()
    }

    getPageData() {
        var me = this;

        /**
         * This is an Example of sequencing RxJS observable using mergeMap
         * (We are sequencing the API calls as the H2 DB used by the backend is failing to serve multiple request at once)
         */
        me.orderService.getOrderCountStats("orderStatus")
        .mergeMap(function(statusData) {
            me.ordersByStatusData = statusData.items;
            console.log("Received order-Count by Status");
            return me.orderService.getOrderCountStats("paymentType");
        }).mergeMap( function(payTypeData) {
            me.ordersByPaymentData = payTypeData.items;
            console.log("Received order-count by Payment Type");
            return me.productService.getTotalSalesByProduct()
        }).subscribe(function(countryData){
            me.ordersByCountryData = countryData.items;
            console.log("Received Total sales by product");
        });
    }


}
