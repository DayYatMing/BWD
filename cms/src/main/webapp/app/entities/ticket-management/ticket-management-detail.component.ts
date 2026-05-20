import { Component, OnInit, signal, inject } from '@angular/core';
import { ArticleArr, TicketManagement } from './ticket-management.model';
import { ActivatedRoute } from '@angular/router';
import { TicketManagementService } from './ticket-management.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { NgFor } from '@angular/common';
import { Subject } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Account } from '../../core/auth/account.model';
import { AccountService } from '../../core/auth/account.service';
import { takeUntil } from 'rxjs/operators';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { PagerModule, PageEventArgs } from '@syncfusion/ej2-angular-grids';
import { Entity } from '../../services/entity/entity.model';

@Component({
  selector: 'jhi-ticket-management-detail',
  imports: [NgFor, CommonModule, FormsModule, RouterLink, PagerModule],
  templateUrl: './ticket-management-detail.component.html',
  styleUrl: './ticket-management.component.scss',
})
export class TicketManagementDetailComponent implements OnInit {
  account = signal<Account | null>(null);
  public ticketManagement: TicketManagement = new TicketManagement();
  public articleVal: ArticleArr;
  pagedArticles: ArticleArr[] = [];
  pageSize = 5;
  totalArticles = 0;

  infoMsg: string = '';
  progressLoader: boolean = true;
  ticketId: string = '';
  public display: boolean | undefined;
  public numLimit: number = 200;

  login: string | null | undefined;
  customerMail: string | undefined;
  customerName: string | null | undefined;
  cusShortName: any;

  showUpdateComponent = true;

  private readonly accountService = inject(AccountService);
  private readonly destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private ticketManagementService: TicketManagementService,
  ) {
    this.articleVal = new ArticleArr();
  }

  ngOnInit() {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => this.account.set(account));

    this.login = this.account()?.login;
    this.loadCusShortName();

    this.customerMail = this.account()?.email;
    this.customerName = this.account()?.firstName;

    this.route.paramMap.subscribe(params => {
      this.ticketId = params.get('id')!;
    });

    this.loadTicket(this.ticketId);
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

  loadTicket(id: string) {
    this.ticketManagementService.findId(id).subscribe({
      next: (res: HttpResponse<TicketManagement>) => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);

        if (res.body !== null) {
          this.ticketManagement = res.body;
        }

        this.initArticlesPagination();
        this.progressLoader = false;
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  initArticlesPagination(): void {
    const articles = this.ticketManagement?.Article ?? [];
    this.totalArticles = articles.length;
    this.updatePagedArticles(1);
  }

  articlesPageChanged(event: PageEventArgs): void {
    const currentPage: any = event.currentPage ?? 1;
    this.updatePagedArticles(currentPage);
  }

  private updatePagedArticles(currentPage: number): void {
    const start = (currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;

    const articles = (this.ticketManagement?.Article ?? []).slice().reverse();
    this.pagedArticles = articles.slice(start, end);
  }

  public readMore(article: ArticleArr) {
    this.display = true;
    this.articleVal = article;
  }

  public clear() {
    this.display = false;
  }

  save(): void {
    this.ticketManagementService.update(this.ticketManagement, this.cusShortName, this.customerMail).subscribe({
      next: (res: HttpResponse<TicketManagement>) => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);

        this.showUpdateComponent = true;
        this.loadTicket(this.ticketId);
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  toggleComponents(): void {
    this.showUpdateComponent = !this.showUpdateComponent;
  }

  protected readonly ArticleArr = ArticleArr;
}
