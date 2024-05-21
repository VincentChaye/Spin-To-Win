import { Component } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  constructor(public PLAYERINFO: PlayoutComponent) {
    // Définir PLAYERINFO.pageCharger à 1 lorsque le composant est chargé
    this.PLAYERINFO.pageCharger = 0;
  }
  
  selectedToken: { value: number, color: string } | null = null;
  cellTokens: { [key: string]: { count: number, color: string, originalContent: string, originalColor: string } } = {};
  credit: number = 100;
  actionHistory: { cellId: string, previousCount: number, previousColor: string, tokenValue: number }[] = [];

  creditOp(value: number) {
    this.credit = this.credit + value;
  }

  onTokenClick(value: number, color: string) {
    this.selectedToken = { value, color };
  }

  onCellClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (this.selectedToken && target.tagName === 'TD' && target.id) {
      const cellId = target.id;

      // Initialize the cell data if not already initialized
      if (!this.cellTokens[cellId]) {
        this.cellTokens[cellId] = { 
          count: 0, 
          color: this.selectedToken.color, 
          originalContent: target.innerText,
          originalColor: target.style.backgroundColor || '' 
        };
      }
      
      // Save current state for undo functionality
      this.actionHistory.push({ 
        cellId, 
        previousCount: this.cellTokens[cellId].count, 
        previousColor: this.cellTokens[cellId].color,
        tokenValue: this.selectedToken.value 
      });
      
      // Update the cell data with the selected token value
      this.cellTokens[cellId].count += this.selectedToken.value;
      this.cellTokens[cellId].color = this.selectedToken.color;
      this.creditOp(-this.selectedToken.value);

      // Update the display of the cell
      this.updateCellDisplay(cellId);
    }
  }

  updateCellDisplay(cellId: string) {
    const cell = document.getElementById(cellId);
    if (cell) {
      const cellData = this.cellTokens[cellId];
      
      // Change cell color based on the value
      if (cellData.count >= 50) {
        cell.style.backgroundColor = 'blue';
      } else if (cellData.count >= 20) {
        cell.style.backgroundColor = 'lightblue';
      } else if (cellData.count >= 10) {
        cell.style.backgroundColor = 'purple';
      } else if (cellData.count >= 5) {
        cell.style.backgroundColor = 'red';
      } else if (cellData.count >= 2) {
        cell.style.backgroundColor = 'orange';
      } else if (cellData.count >= 1) {
        cell.style.backgroundColor = 'yellow';
      } else {
        cell.style.backgroundColor = cellData.originalColor;
        cell.innerText = cellData.originalContent;
      }
      
      // Create or update a span element for the token value
      let tokenSpan = cell.querySelector('span.token-value') as HTMLElement;
      if (!tokenSpan && cellData.count > 0) {
        tokenSpan = document.createElement('span');
        tokenSpan.className = 'token-value';
        cell.appendChild(tokenSpan);
      }
      if (tokenSpan) {
        tokenSpan.innerText = cellData.count > 0 ? `  ${cellData.count}€` : '';
      }
    }
  }

  onRemoveAllTokens() {
    Object.keys(this.cellTokens).forEach(cellId => {
      const cellData = this.cellTokens[cellId];
      this.creditOp(cellData.count); // Add the count back to credit
      cellData.count = 0;
      const cell = document.getElementById(cellId);
      if (cell) {
        cell.style.backgroundColor = cellData.originalColor;
        cell.innerText = cellData.originalContent;
      }
    });
    this.cellTokens = {};
    this.actionHistory = [];
  }

  onUndoLastAction() {
    const lastAction = this.actionHistory.pop();
    if (lastAction) {
      const { cellId, previousCount, previousColor, tokenValue } = lastAction;
      const cellData = this.cellTokens[cellId];
      cellData.count = previousCount;
      cellData.color = previousColor;
      this.updateCellDisplay(cellId);
      this.creditOp(tokenValue); // Add the token value back to credit
    }
  }
}
