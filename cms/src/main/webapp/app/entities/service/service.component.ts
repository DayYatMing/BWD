import { Component, OnInit, signal, inject } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Order } from '../../services/order/order.model';
import { OrderService } from '../../services/order/order.service';
import { TicketManagementService } from '../ticket-management/ticket-management.service';
import { Account } from '../../core/auth/account.model';
import { AccountService } from '../../core/auth/account.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'jhi-service',
  imports: [CommonModule],
  templateUrl: './service.component.html',
  styleUrl: './service.component.scss',
})
export class ServiceComponent implements OnInit {
  account = signal<Account | null>(null);
  private readonly accountService = inject(AccountService);
  private readonly destroy$ = new Subject<void>();

  constructor(
    private orderService: OrderService,
    private ticketManagementService: TicketManagementService,
  ) {}

  infoMsg: string = '';
  progressLoader: boolean = true;
  orders: Order[] = [];
  login: any;
  readonly imageType: string = 'data:image/PNG;base64,';

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
        if (res.body !== null) {
          this.loadServices(res.body);
        }
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  loadServices(code: string) {
    this.orderService.findServices(code).subscribe({
      next: (res: HttpResponse<Order[]>) => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);

        if (res.body !== null) {
          this.orders = res.body;
          this.progressLoader = false;
        }
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  showImage = false;
  selectedImage: string | null = null;

  openImage(order: any) {
    this.selectedImage = this.imageType + order.circuit.toString();
    this.showImage = true;
  }

  closeImage() {
    this.showImage = false;
    this.selectedImage = null;
  }
}
