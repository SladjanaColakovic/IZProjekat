import { TestBed } from '@angular/core/testing';

import { SuggestionComponentService } from './suggestion-component.service';

describe('SuggestionComponentService', () => {
  let service: SuggestionComponentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SuggestionComponentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
