import { Injectable } from '@angular/core';
import { Observable, lastValueFrom } from 'rxjs';
import { HttpClient } from '@angular/common/http';

const base_url: string = 'http://localhost:8080';

@Injectable({
  providedIn: 'root',
})
export class LoanDecision {
  constructor(private http: HttpClient) { }

  public post({
    endpoint,
    data = {},
    queryParams = {},
  }: {
    endpoint: string;
    data?: any;
    queryParams?: any;
  }): Promise<any> {
    return this.request({ endpoint, method: 'POST', data, queryParams });
  }

  public async request({
    endpoint,
    data = {},
    queryParams = {},
  }: {
    endpoint: string;
    method?: string;
    data?: object;
    queryParams?: any;
  }): Promise<any> {

    const url = base_url + endpoint;

    const requestOptions = {
      params: queryParams,
    };


    let req: Observable<any>;

    req = this.http.post(url, data, {
      ...requestOptions,
      observe: 'response',
    });

    if (!req) {
      throw new Error(`error calling ${url} with method $POST`);
    }

    return await lastValueFrom(req).then((res) => {
      return res.body;
    });
  }
}
