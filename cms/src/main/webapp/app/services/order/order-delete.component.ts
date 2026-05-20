import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { OrderService } from './order.service';
import { Order } from './order.model';

@Component({
  selector: 'jhi-order-delete',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './order-delete.component.html',
  styleUrl: './order.component.scss',
})
export class OrderDeleteComponent implements OnInit {
  constructor(
    private orderService: OrderService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  infoMsg: string = '';
  orderId: string = '';
  order: any;
  readonly imageType: string = 'data:image/PNG;base64,';

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.orderId = params.get('id')!;
    });

    this.orderService.findId(this.orderId).subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.order = res.body;
        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

  public delete() {
    this.orderService.delete(this.orderId).subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        this.router.navigate(['/services/order']);
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

  showImage = false;

  openImage() {
    this.showImage = true;
  }

  closeImage() {
    this.showImage = false;
  }
}
