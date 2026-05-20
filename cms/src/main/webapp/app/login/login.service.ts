import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { Account } from 'app/core/auth/account.model';
import { Login } from './login.model';
export interface JWTToken {
  id_token: string;
}
@Injectable({ providedIn: 'root' })
export class LoginService {
  private readonly router = inject(Router);
  private readonly http = inject(HttpClient);
  private readonly accountService = inject(AccountService);
  private readonly appConfig = inject(ApplicationConfigService);

  requestLogin(credentials: Login): Observable<JWTToken | { status: 'MFA_REQUIRED' }> {
    return this.http.post<JWTToken | { status: 'MFA_REQUIRED' }>(this.appConfig.getEndpointFor('/api/authenticate'), credentials).pipe(
      map(res => {
        // If JWT returned, handle it
        if ('id_token' in res) {
          this.handleToken(res.id_token);
        }
        return res;
      }),
    );
  }

  verifyMfa(payload: { username: string; code: string }): Observable<JWTToken> {
    return this.http.post<JWTToken>(this.appConfig.getEndpointFor('/api/mfa-verify'), payload);
  }

  public handleToken(idToken: string): Account {
    localStorage.setItem('jhi-authenticationToken', idToken);
    const payloadDecoded: any = JSON.parse(atob(idToken.split('.')[1]));
    const account: Account = {
      login: payloadDecoded.sub || '',
      email: payloadDecoded.email || '',
      activated: true,
      authorities: payloadDecoded.auth ? payloadDecoded.auth.split(',') : [],
      langKey: 'en',
      imageUrl: null,
      firstName: payloadDecoded.given_name || '',
      lastName: payloadDecoded.family_name || '',
    };

    this.accountService.authenticate(account);
    return account;
  }

  logout(): void {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.accountService['stateStorageService'].clearAuthenticationToken();
    this.accountService.authenticate(null);
    this.router.navigate(['/login']);
  }
}
