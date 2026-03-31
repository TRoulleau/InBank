import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoanDecision } from '../../services/loan-decision';

@Component({
  selector: 'app-score-calculator',
  imports: [CommonModule, FormsModule],
  templateUrl: './score-calculator.html',
  styleUrl: './score-calculator.css',
})
export class ScoreCalculator {
  errorMessage: string = '';
  result: string | null = null;
  personalCode: string = '';
  loanAmount: string = '';
  loanPeriod: string = '';

  constructor(private api: LoanDecision, private cdr: ChangeDetectorRef) {}

  refresh() {
    window.location.reload();
  }

  calculateScore(): void {
    this.errorMessage = '';
    this.result = null;


    if (!this.personalCode || !this.loanAmount || !this.loanPeriod) {
      this.errorMessage = 'Please fill in all fields';
      this.result = null;
      this.cdr.detectChanges();
      return;
    }

    this.api.post({endpoint: '/score', data: {
      personalCode: this.personalCode,
      loanAmount: this.loanAmount,
      loanPeriod: this.loanPeriod
    }})
      .then(response => {
        this.errorMessage = '';
        this.result = response.score || response.result || JSON.stringify(response);
        this.cdr.detectChanges();
      })
      .catch(error => {
        // If response is a 404, the personal code was not found
        if (error.status === 404) {
          this.errorMessage = 'Personal code not found';
        } else {
          this.errorMessage = 'Error calculating score';
        }
        this.result = null;
        this.cdr.detectChanges();
        console.error('Score calculation failed:', error);
      });
  }
}
