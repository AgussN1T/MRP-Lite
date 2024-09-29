import { Component } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SpreadsheetModule } from '@syncfusion/ej2-angular-spreadsheet';

@Component({
  selector: 'app-calculo',
  standalone: true,
  imports: [SpreadsheetModule],
  template: `
 <ejs-spreadsheet>
  
</ejs-spreadsheet>
  `,
  styles: `
  .app-calculo {

display: flex;

flex-direction: column;

align-items: center;

padding: 20px;

}


.ejs-spreadsheet {

width: 100%;

height: 500px;

border: 1px solid #ccc;

border-radius: 5px;

padding: 10px;

box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);

}


.ejs-spreadsheet.e-header-cell {

background-color: #f0f0f0;

border-bottom: 1px solid #ccc;

padding: 10px;

}


.ejs-spreadsheet.e-cell {

padding: 10px;

border-bottom: 1px solid #ccc;

}
  `
})
export class CalculoComponent {

}
