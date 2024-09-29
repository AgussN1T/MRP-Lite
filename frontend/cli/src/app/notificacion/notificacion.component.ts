import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from './notificacion.service';


@Component({
  selector: 'app-notificacion',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="notification" class="alert alert-success alert-dismissible fade show" role="alert">
      {{ notification }}
      <button type="button" class="btn-close" aria-label="Close" (click)="clearNotification()"></button>
    </div>
  `,
  styles: [`
    .alert {
      position: fixed;
      top: 20px;
      right: 20px;
      z-index: 1050;
    }
  `]
})
export class NotificacionComponent {
  notification: string | null = null;

  constructor(private notificacionService: NotificationService) {
    this.notificacionService.notification$.subscribe(message => {
      this.notification = message;
    });
  }

  clearNotification(): void {
    this.notificacionService.clearNotification();
  }
}
