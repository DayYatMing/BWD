import { Component, OnInit, signal, inject } from '@angular/core';
import { TicketManagement } from './ticket-management.model';
import { TicketManagementService } from './ticket-management.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subject } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Account } from '../../core/auth/account.model';
import { AccountService } from '../../core/auth/account.service';
import { takeUntil } from 'rxjs/operators';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';

@Component({
  selector: 'jhi-ticket-management-create',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './ticket-management-create.component.html',
})
export class TicketManagementCreateComponent implements OnInit {
  account = signal<Account | null>(null);
  public ticketManagement: TicketManagement = new TicketManagement();

  infoMsg: string = '';
  public display: boolean | undefined;

  login: string | null | undefined;
  customerMail: string | undefined;
  cusShortName: any;

  private readonly accountService = inject(AccountService);
  private readonly destroy$ = new Subject<void>();

  constructor(
    private ticketManagementService: TicketManagementService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => this.account.set(account));

    this.customerMail = this.account()?.email;
    this.login = this.account()?.login;
    this.loadCusShortName();
  }

  loadCusShortName() {
    this.ticketManagementService.findCusShortName(this.login).subscribe({
      next: (res: HttpResponse<string>) => {
        this.cusShortName = res.body;
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  public new() {
    this.ticketManagementService.create(this.ticketManagement, this.cusShortName, this.customerMail).subscribe({
      next: (res: HttpResponse<TicketManagement>) => {
        this.router.navigate(['/entities/ticket-management']);
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }
}
