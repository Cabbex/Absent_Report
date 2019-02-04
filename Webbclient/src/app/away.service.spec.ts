import { TestBed } from '@angular/core/testing';

import { AwayService } from './away.service';

describe('AwayService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AwayService = TestBed.get(AwayService);
    expect(service).toBeTruthy();
  });
});
