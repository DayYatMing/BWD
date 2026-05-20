import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Entity } from './entity.model';
import { EntityService } from './entity.service';

@Component({
  selector: 'jhi-entity-delete',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './entity-delete.component.html',
})
export class EntityDeleteComponent implements OnInit {
  constructor(
    private entityService: EntityService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  infoMsg: string = '';
  public entity: Entity = new Entity();
  entityId: string = '';

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.entityId = params.get('id')!;
    });

    this.entityService.findId(this.entityId).subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        if (res.body !== null) {
          this.entity = res.body;
        }
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }

  public delete() {
    this.entityService.delete(this.entity).subscribe({
      next: res => {
        console.log('Status:', res.status);
        console.log('Headers:', res.headers);
        this.router.navigate(['/services/entity']);
      },
      error: err => {
        this.infoMsg = err.message;
      },
    });
  }
}
