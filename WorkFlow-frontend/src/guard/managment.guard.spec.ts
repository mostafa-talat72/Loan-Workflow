import { TestBed } from '@angular/core/testing';

import { ManagmentGuard } from './managment.guard';

describe('ManagmentGuard', () => {
  let guard: ManagmentGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ManagmentGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
