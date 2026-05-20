import { Component, OnInit, inject, signal } from '@angular/core';
import { TicketManagement } from './ticket-management.model';
import { TicketManagementService } from './ticket-management.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Account } from '../../core/auth/account.model';
import { AccountService } from '../../core/auth/account.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PagerModule, PageEventArgs } from '@syncfusion/ej2-angular-grids';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'jhi-ticket-management',
  imports: [NgFor, RouterLink, PagerModule, CommonModule],
  templateUrl: './ticket-management.component.html',
  styleUrl: './ticket-management.component.scss',
})
export class TicketManagementComponent implements OnInit {
  account = signal<Account | null>(null);
  private readonly accountService = inject(AccountService);
  private readonly destroy$ = new Subject<void>();

  constructor(private ticketManagementService: TicketManagementService) {}

  progressLoader: boolean = true;
  ticketManagements: TicketManagement[] = [];
  pagedData: TicketManagement[] = [];

  pageSize = 10;
  totalRecords = 0;

  login: any;
  statetype: string = '';
  infoMsg: string = '';
  cusShortName: any;

  ngOnInit() {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => this.account.set(account));

    this.login = this.account()?.login;

    this.loadCusShortName();
  }

  loadCusShortName() {
    this.ticketManagementService.findCusShortName(this.login).subscribe({
      next: (res: HttpResponse<string>) => {
        this.cusShortName = res.body;
        this.statetype = 'open'; // initial statetype: open
        this.loadTickets();
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  loadTickets() {
    this.ticketManagementService.find(this.cusShortName, this.statetype).subscribe({
      next: (res: HttpResponse<TicketManagement[]>) => {
        if (res.body !== null) {
          this.ticketManagements = res.body;
        }

        this.totalRecords = this.ticketManagements.length;
        this.updatePagedData(1);
        this.progressLoader = false;
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  pageChanged(event: PageEventArgs) {
    const currentPage: any = event.currentPage ?? 1;
    const start = (currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.pagedData = this.ticketManagements.slice(start, end);

    this.updatePagedData(currentPage);
  }

  private updatePagedData(currentPage: any): void {
    const start = (currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.pagedData = this.ticketManagements.slice(start, end);
  }

  onChange(selectedValue: Event) {
    this.progressLoader = true;
    const target = selectedValue.target as HTMLSelectElement;
    this.statetype = target.value;
    this.loadTickets();
  }
}
