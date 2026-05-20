import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { PagerModule, PageEventArgs } from '@syncfusion/ej2-angular-grids';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { OrderService } from './order.service';
import { Order } from './order.model';

@Component({
  selector: 'jhi-order',
  imports: [CommonModule, PagerModule, FormsModule, RouterModule],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss',
})
export class OrderComponent implements OnInit {
  constructor(private orderService: OrderService) {}

  infoMsg: string = '';
  progressLoader: boolean = true;
  orders: Order[] = [];
  pagedData: Order[] = [];
  searchTerm: string = '';
  allData: Order[] = [];

  pageSize = 10;
  totalRecords = 0;

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders() {
    this.orderService.find().subscribe({
      next: (res: HttpResponse<Order[]>) => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);

        if (res.body !== null) {
          this.orders = res.body;
          this.allData = res.body;
          this.progressLoader = false;
        }

        this.totalRecords = this.orders.length;
        this.updatePagedData(1);
      },
      error: (err: HttpErrorResponse) => (this.infoMsg = err.message),
      complete: () => console.log('Request completed'),
    });
  }

  pageChanged(event: PageEventArgs) {
    const currentPage: any = event.currentPage ?? 1;
    const start = (currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.pagedData = this.orders.slice(start, end);

    this.updatePagedData(currentPage);
  }

  private updatePagedData(currentPage: any): void {
    const start = (currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.pagedData = this.orders.slice(start, end);
  }

  sortColumn: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  sort(column: string) {
    if (this.sortColumn === column) {
      // Toggle direction
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }

    this.pagedData = this.sortArray(this.pagedData, this.sortColumn, this.sortDirection);
  }

  sortArray(data: any[], column: string, direction: 'asc' | 'desc'): any[] {
    return data.sort((a, b) => {
      const valueA = a[column];
      const valueB = b[column];

      if (valueA == null) return 1;
      if (valueB == null) return -1;

      let comparison = 0;
      if (typeof valueA === 'string') {
        comparison = valueA.localeCompare(valueB);
      } else {
        comparison = valueA > valueB ? 1 : valueA < valueB ? -1 : 0;
      }

      return direction === 'asc' ? comparison : -comparison;
    });
  }

  doSearch() {
    if (!this.searchTerm) {
      // If search box is empty, show all data
      this.pagedData = [...this.allData];
      return;
    }

    const term = this.searchTerm.toLowerCase();

    // Filter the array
    this.pagedData = this.allData.filter(
      order =>
        order.entityName?.toLowerCase().includes(term) ||
        order.status?.toLowerCase().includes(term) ||
        order.name?.toLowerCase().includes(term),
    );

    if (this.sortColumn) {
      this.pagedData = this.sortArray(this.pagedData, this.sortColumn, this.sortDirection);
    }
  }

  resetSearch() {
    this.searchTerm = '';
    this.pagedData = [...this.allData];
  }
}
