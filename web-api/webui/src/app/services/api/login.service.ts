import { Injectable, Inject } from '@angular/core';
import { Router } from '@angular/router';

import { Observable,Subject } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { UserInfoService, LoginInfoInStorage} from '../user-info.service';
import { ApiRequestService } from './api-request.service';

export interface LoginRequestParam{
    username:string;
    password:string;
}

@Injectable()
export class LoginService {

    public landingPage:string = "/home/dashboard/order";
    constructor(
        private router:Router,
        private userInfoService: UserInfoService,
        private apiRequest: ApiRequestService
    ) {}


    getToken(username:string, password:string): Observable<any> {
        let me = this;

        let bodyData:LoginRequestParam = {
            "username": username,
            "password": password,
        }
        let loginDataSubject:Subject<any> = new Subject<any>(); // Will use this subject to emit data that we want after ajax login attempt
        let loginInfoReturn:LoginInfoInStorage; // Object that we want to send back to Login Page

        this.apiRequest.post('login', bodyData)
            .subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.msgType === "SUCCESS"){
                    //Create a success object that we want to send back to login page
                    loginInfoReturn = {
                        "success"    : true,
                        "message"    : jsonResp.msg,
                        "landingPage": this.landingPage,
                        "user"       : {
                            "userId"     : jsonResp.item.username,
                            "email"      : jsonResp.item.emailAddresss,
                            "displayName": jsonResp.item.username,
                            "token"      : jsonResp.item.token,
                        }
                    };

                    // store username and jwt token in session storage to keep user logged in between page refreshes
                    this.userInfoService.storeUserInfo(JSON.stringify(loginInfoReturn.user));
                }
                else {
                    //Create a faliure object that we want to send back to login page
                    loginInfoReturn = {
                        "success":false,
                        "message":jsonResp.msg,
                        "landingPage":"/login"
                    };
                }
                loginDataSubject.next(loginInfoReturn);
            });

            return loginDataSubject;
    }

    logout(navigatetoLogout=true): void {
        // clear token remove user from local storage to log user out
        this.userInfoService.removeUserInfo();
        if(navigatetoLogout){
            this.router.navigate(["logout"]);
        }
    }
}
