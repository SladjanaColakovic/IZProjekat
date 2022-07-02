import { TestBed } from '@angular/core/testing';

import { FuzzyLogicService } from './fuzzy-logic.service';

describe('FuzzyLogicService', () => {
  let service: FuzzyLogicService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuzzyLogicService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
