import { Observable } from "rxjs/Observable";
export interface Filter<T> {
    isActive(): boolean;
    accepts(item: T): boolean;
    changes: Observable<any>;
}
