import { Component, OnInit, inject, signal } from '@angular/core';
import { TicketManagement } from './ticket-management.model';
import { TicketManagementService } from './ticket-management.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Account } from '../../core/auth/account.model';
import { AccountService } from '../../core/auth/account.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { CircularChart3DAllModule } from '@syncfusion/ej2-angular-charts';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Customer } from '../../services/customer/customer.model';

@Component({
  selector: 'jhi-ticket-management-ratio',
  imports: [CircularChart3DAllModule, CommonModule, RouterModule],
  templateUrl: './ticket-management-ratio.component.html',
  styleUrl: './ticket-management.component.scss',
  providers: [CircularChart3DAllModule],
})
export class TicketManagementRatioComponent implements OnInit {
  account = signal<Account | null>(null);
  private readonly accountService = inject(AccountService);
  private readonly destroy$ = new Subject<void>();

  constructor(private ticketManagementService: TicketManagementService) {}

  ticketManagement: TicketManagement | any;

  login: any;
  infoMsg: string = '';
  cusShortName: any;
  progressLoader: boolean = true;

  public dataSource?: Object[];
  public legendSettings?: Object;
  public dataLabel?: Object;
  public tilt?: number;
  public pointColorMapping?: string;
  public enableAnimation?: boolean;
  public innerRadius?: string;

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
        this.cusShortName = res.body || '';

        this.loadRatio();
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  loadRatio() {
    this.ticketManagementService.findRatio(this.cusShortName).subscribe({
      next: (res: HttpResponse<TicketManagement>) => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        this.loadGraph(res.body, res.headers);
        this.progressLoader = false;
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  private loadGraph(data: any, headers: any): void {
    this.ticketManagement = data;

    this.dataSource = [
      { x: 'Ticket(s) Open: ' + this.ticketManagement.open, y: this.ticketManagement.open, fill: '#ff4973', text: 'Open Ticket(s)' },
      {
        x: 'Ticket(s) Closed: ' + this.ticketManagement.closed,
        y: this.ticketManagement.closed,
        fill: '#f3caad',
        text: 'Closed Ticket(s)',
      },
    ];
    this.dataLabel = {
      visible: true,
      name: 'x',
      position: 'Outside',
      font: {
        fontWeight: '600',
      },
      connectorStyle: { length: '50px' },
    };
    this.pointColorMapping = 'fill';
    this.legendSettings = { visible: true, position: 'Bottom', alignment: 'Near' };
    this.tilt = -45;
    this.enableAnimation = true;
    this.innerRadius = '40%';
  }
}
