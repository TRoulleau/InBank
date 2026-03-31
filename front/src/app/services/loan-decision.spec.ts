import { TestBed } from '@angular/core/testing';

import { LoanDecision } from './loan-decision';

describe('LoanDecision', () => {
  let service: LoanDecision;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanDecision);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
