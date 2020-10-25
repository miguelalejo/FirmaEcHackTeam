import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { SignRequest } from '../model/sign-request';
import { SignResponse } from '../model/sign-response';

@Injectable({
  providedIn: 'root'
})
export class SignService {

  constructor(private httpCliente: HttpClient) { }

  ping(): Observable<SignResponse> {
    return this.httpCliente.get<SignResponse>(`${environment.endpoint}/servicio-firma/ping`);
  }

  sing(signRequest: SignRequest): Observable<SignResponse> {
    return this.httpCliente.post<SignResponse>(`${environment.endpoint}/servicio-firma/firmar/word`, signRequest);
  }

}
