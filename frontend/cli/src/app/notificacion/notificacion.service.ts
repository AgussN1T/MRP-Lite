import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationSubject = new BehaviorSubject<string | null>(null);
  notification$ = this.notificationSubject.asObservable();

  showNotification(message: string): void {
    this.notificationSubject.next(message);
  }

  clearNotification(): void {
    this.notificationSubject.next(null);
  }
}