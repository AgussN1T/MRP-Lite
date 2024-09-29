import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RobotCommunicationService } from './robot-communication.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-robot-icon',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="robot-icon" (click)="showDialogueBox()">🤖</div>
    <div class="dialogue-box" *ngIf="showDialog">
    <p>{{message}}</p>
    </div>
  `,
  styles: `.robot-icon {
    position: fixed;
    bottom: 20px;
    right: 20px;
    font-size: 30px;
    cursor: pointer;
  }
  
  .robot-icon:hover {
    font-size: 35px;
  }

  .dialogue-box {
  position: fixed;
  bottom: 70px;
  right: 20px; 
  background-color: #fff;
  border: 1px solid #000;
  padding: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  z-index: 2
}
  `
})
export class RobotIconComponent {

  showDialog: boolean = true;
  message: string = '';
  subscription: Subscription;
  
  constructor(private robotCommunication: RobotCommunicationService) {
    this.subscription = this.robotCommunication.message$.subscribe(message => {
      this.message = message;
    });
  }  
  
  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  showDialogueBox() {
    this.showDialog = !this.showDialog;
  }

}