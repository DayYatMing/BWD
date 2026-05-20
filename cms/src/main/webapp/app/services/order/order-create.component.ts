import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { OrderService } from './order.service';
import { Order } from './order.model';
import { EntityService } from '../entity/entity.service';
import { Entity } from '../entity/entity.model';

@Component({
  selector: 'jhi-order-create',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './order-create.component.html',
})
export class OrderCreateComponent implements OnInit {
  constructor(
    private orderService: OrderService,
    private entityService: EntityService,
    private router: Router,
  ) {}

  infoMsg: string = '';
  order: Order = new Order();
  entities: Entity[] = [];
  selectedFile: File | null = null;

  ngOnInit() {
    this.loadEntity();
  }

  public new() {
    const formData = new FormData();
    formData.append('entityId', (this.order.entityId ?? 1).toString());
    formData.append('name', this.order.name ?? '');
    formData.append('status', this.order.status ?? '');
    formData.append('duration', (this.order.duration ?? 0).toString());
    if (this.selectedFile) formData.append('circuit', this.selectedFile);

    this.orderService.create(formData).subscribe({
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

  loadEntity() {
    this.entityService.find().subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.entities = res.body;
        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

  onEntitySelected(name: string) {
    const entity = this.entities.find(e => e.name === name);
    if (entity) {
      this.order.entityId = entity.id;
    }
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;
    this.selectedFile = input.files[0];
  }
}
