import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class OrderService {

    constructor(
        private apiRequest: ApiRequestService,
        private translate:TranslateService
    ) {}

    /**
     * Gets List of orders
     */
    getOrderInfo(from?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('from', typeof from === "number"? from.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");
        let orderListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('orders',params)
            .subscribe(jsonResp => {
                let returnObj = jsonResp.items.map(function(v, i, a){
                    let newRow = Object.assign({}, v, {
                        orderDate  : me.translate.getDateString(v.orderDate),
                        paidDate   : me.translate.getDateString(v.paidDate),
                        shippedDate: me.translate.getDateString(v.shippedDate)
                    });
                    return newRow;
                });
                orderListSubject.next(returnObj); // incidentList is a Subject and emits an event thats being listened to by the components
            });
        return orderListSubject;
    }

    /**
     * Gets Orders and Order Lines (Products in each order)
     */
    getOrderDetails(orderId:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let url:string = "";
        let orderDetailSubject = new Subject<any>(); // Will use this subject to emit data that we want
        if (orderId){
            url= "orders/"+orderId.toString();
            this.apiRequest.get(url)
                .subscribe(jsonResp => {
                    let returnObj = jsonResp.items.map(function(v, i, a){
                        let newRow = Object.assign({}, v, {
                            orderDate  : me.translate.getDateString(v.orderDate),
                            paidDate   : me.translate.getDateString(v.paidDate),
                            shippedDate: me.translate.getDateString(v.shippedDate)
                        });
                        return newRow;
                    });
                    orderDetailSubject.next(returnObj); // incidentList is a Subject and emits an event thats being listened to by the components
                });
        }
        else{
            let empty={orderId:'',orderLines:[]};
            orderDetailSubject.next(empty)
        }
        return orderDetailSubject;
    }


    getOrderCountStats(field:string): Observable<any> {
        return this.apiRequest.get('stats/order-count/by/' + field );
    }



}
