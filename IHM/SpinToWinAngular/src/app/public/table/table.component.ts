import { Component, ElementRef } from '@angular/core';
import { PlayoutComponent } from '../playout/playout.component';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent {
  selectedToken: { value: number, color: string } | null = null;
  cellTokens: { [key: string]: { count: number, color: string, originalContent: string, originalColor: string } } = {};
  credit: number = 100;
  actionHistory: { cellId: string, previousCount: number, previousColor: string, tokenValue: number }[] = [];
  previousSelectedTokenElement: HTMLElement | null = null;

  constructor(public PLAYERINFO: PlayoutComponent, private elRef: ElementRef) {
    this.PLAYERINFO.pageCharger = 0;
  }

  creditOp(value: number) {
    this.credit += value;
  }

  onTokenClick(value: number, color: string) {
    this.selectedToken = { value, color };
    console.log(`Selected token: ${value} of color ${color}`);

    // Reset the previous token's border
    if (this.previousSelectedTokenElement) {
      this.previousSelectedTokenElement.classList.remove('selected');
    }

    // Find the current token element and apply the golden border
    const tokenElements = this.elRef.nativeElement.querySelectorAll('.token');
    tokenElements.forEach((tokenElement: HTMLElement) => {
      if (parseInt(tokenElement.querySelector('text')?.textContent || '0', 10) === value) {
        tokenElement.classList.add('selected');
        this.previousSelectedTokenElement = tokenElement;
      }
    });
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
      
      // Log the updated tokens data to the console
      this.logTokensData();
    }
  }

  updateCellDisplay(cellId: string) {
    const cell = document.getElementById(cellId);
    if (cell) { // Vérifie si l'élément n'est pas nul
      const cellData = this.cellTokens[cellId];
      
      // Clear existing tokens
      cell.innerHTML = '';

      // Créer ou mettre à jour un élément span pour la valeur du jeton
      let tokenSpan = cell.querySelector('span.token-value') as HTMLElement;
      if (!tokenSpan) {
        tokenSpan = document.createElement('span');
        tokenSpan.className = 'token-value';
        cell.appendChild(tokenSpan);
      }
      if (tokenSpan) {
        tokenSpan.innerText = cellData.count > 0 ? `  (${cellData.count}€)` : '';
      }
      
      // Ajouter le jeton SVG sélectionné à la cellule
      if (cellData.count > 0) {
        const tokenSvg = this.createTokenSvg(this.selectedToken!.value, this.selectedToken!.color); // Utilisez l'opérateur non-null assertion (!)
        tokenSvg.classList.add('token-svg');  // Ajoutez la classe pour le positionnement
        cell.appendChild(tokenSvg);
      }
    }
  }

  createTokenSvg(value: number, color: string): SVGSVGElement {
    const svgNamespace = 'http://www.w3.org/2000/svg';
    const svg = document.createElementNS(svgNamespace, 'svg') as SVGSVGElement;
    svg.setAttribute('viewBox', '0 0 100 100');
    svg.setAttribute('class', 'token');

    const circle = document.createElementNS(svgNamespace, 'circle') as SVGCircleElement;
    circle.setAttribute('cx', '50');
    circle.setAttribute('cy', '50');
    circle.setAttribute('r', '45');
    circle.setAttribute('class', 'outer-circle');
    circle.setAttribute('fill', color);

    const innerCircle = document.createElementNS(svgNamespace, 'circle') as SVGCircleElement;
    innerCircle.setAttribute('cx', '50');
    innerCircle.setAttribute('cy', '50');
    innerCircle.setAttribute('r', '30');
    innerCircle.setAttribute('fill', color);

    const text = document.createElementNS(svgNamespace, 'text') as SVGTextElement;
    text.setAttribute('x', '50');
    text.setAttribute('y', '60');
    text.setAttribute('text-anchor', 'middle');
    text.setAttribute('class', 'chip-number');
    text.setAttribute('fill', 'black');
    text.textContent = `${this.getCellCount('value')}`;

    svg.appendChild(circle);
    svg.appendChild(text);

    return svg;
  }

  getCellCount(cellId: string): number {
    return this.cellTokens[cellId]?.count;
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
    this.logTokensData(); // Log the updated tokens data to the console
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
      this.logTokensData(); // Log the updated tokens data to the console
    }
  }

  logTokensData() {
    console.table(this.cellTokens);
  }
}
