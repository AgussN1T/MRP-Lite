import { Component } from '@angular/core';
import { RobotCommunicationService } from '../guia/robot-communication.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  template: `
    <div class="px-3 py-3 pt-md-5 pb-md-4 md-auto text-center">
      <h1 class="display-4">MRP-Lite</h1>
      <p class="lead">El gantt contraataca</p>
    </div>
  `,
  styles: ``
})
export class HomeComponent {

  constructor(private robotCommunicationService: RobotCommunicationService){}

  ngOnInit(): void {
    this.sendMessageToRobot()
    
  }
  sendMessageToRobot() {
    this.robotCommunicationService.sendMessage("Bienvenido a MRP-Lite!");
  }
}