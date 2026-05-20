import { AfterViewInit, Component, ElementRef, OnInit, inject, signal, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import SharedModule from 'app/shared/shared.module';
import { LoginService, JWTToken } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-login',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export default class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', { static: false }) usernameRef?: ElementRef;

  authenticationError = signal(false);
  mfaRequired = signal(false);
  username = '';

  loginForm = new FormGroup({
    username: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required] }),
    rememberMe: new FormControl(false, { nonNullable: true }),
  });

  mfaForm = new FormGroup({
    code: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.pattern(/^\d{6}$/)],
    }),
  });

  private readonly accountService = inject(AccountService);
  private readonly loginService = inject(LoginService);
  private readonly router = inject(Router);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  ngAfterViewInit(): void {
    this.usernameRef?.nativeElement.focus();
  }
  cancelMfa(): void {
    this.mfaRequired.set(false);
    this.mfaForm.reset();
    this.authenticationError.set(false);
    this.usernameRef?.nativeElement.focus();
  }

  login(): void {
    this.username = this.loginForm.get('username')!.value;
    this.loginService.requestLogin(this.loginForm.getRawValue()).subscribe({
      next: res => {
        this.authenticationError.set(false);
        if ('status' in res && res.status === 'MFA_REQUIRED') {
          this.mfaRequired.set(true);
        } else if ('id_token' in res && res.id_token) {
          this.loginService.handleToken(res.id_token);
          this.router.navigate(['']);
        }
      },
      error: () => this.authenticationError.set(true),
    });
  }

  verifyMfa(): void {
    const code = this.mfaForm.get('code')!.value;
    const rememberMe = this.loginForm.get('rememberMe')!.value;

    this.loginService.verifyMfa({ username: this.username, code }).subscribe({
      next: (res: JWTToken) => {
        this.accountService['stateStorageService'].storeAuthenticationToken(res.id_token, this.loginForm.get('rememberMe')!.value);
        this.accountService.identity(true).subscribe(account => {
          this.mfaRequired.set(false);
          this.router.navigate(['']);
        });
      },
      error: () => this.authenticationError.set(true),
    });
  }
}
