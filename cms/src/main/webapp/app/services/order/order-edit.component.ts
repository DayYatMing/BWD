import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { OrderService } from './order.service';
import { Order } from './order.model';

@Component({
  selector: 'jhi-orders-edit',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './order-edit.component.html',
  styleUrl: './order.component.scss',
})
export class OrderEditComponent implements OnInit {
  constructor(
    private orderService: OrderService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  infoMsg: string = '';
  orderId: string = '';
  order: any;
  selectedFile: File | null = null;
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

  public edit() {
    const formData = new FormData();
    formData.append('id', (this.order.id ?? 0).toString());
    formData.append('entityId', (this.order.entityId ?? 0).toString());
    formData.append('name', this.order.name ?? '');
    formData.append('status', this.order.status ?? '');
    formData.append('duration', (this.order.duration ?? 0).toString());
    if (this.selectedFile) formData.append('circuit', this.selectedFile);

    this.orderService.update(formData).subscribe({
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

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;
    this.selectedFile = input.files[0];
  }

  showImage = false;

  openImage() {
    this.showImage = true;
  }

  closeImage() {
    this.showImage = false;
  }
}
