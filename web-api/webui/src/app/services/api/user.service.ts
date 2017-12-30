import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { TranslateService } from './translate.service';
import { ApiRequestService } from './api-request.service';
import { HttpParams} from "@angular/common/http";
@Injectable()
export class UserService {

    constructor(
        private apiRequest: ApiRequestService,
        private translate:TranslateService
    ) {}

    getCustomers(from?:number, size?:number): Observable<any> {
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('from', typeof from === "number"? from.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");
        params = params.append('only-customers', "true");

        let customerListSubject = new Subject<any>(); // Will use this subject to emit data that we want

        this.apiRequest.get('users',params)
            .subscribe(jsonResp => {
                let items = jsonResp.items.map(function(v, i, a){
                    let newRow = Object.assign({}, v, {
                        address: `${v.address1}, <br/> ${v.city}, ${v.state} ${v.postal} <br/> ${v.country}`
                    });
                    return newRow;
                });

                let returnObj = Object.assign({},jsonResp,{
                    items:items
                })
                customerListSubject.next(returnObj); // incidentList is a Subject and emits an event thats being listened to by the components
            });

        return customerListSubject;
    }
}
