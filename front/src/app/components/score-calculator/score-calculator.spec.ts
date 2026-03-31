import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScoreCalculator } from './score-calculator';

describe('ScoreCalculator', () => {
  let component: ScoreCalculator;
  let fixture: ComponentFixture<ScoreCalculator>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScoreCalculator]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScoreCalculator);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
