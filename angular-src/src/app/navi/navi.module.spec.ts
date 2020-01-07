import { NaviModule } from './navi.module';

describe('NaviModule', () => {
  let naviModule: NaviModule;

  beforeEach(() => {
    naviModule = new NaviModule();
  });

  it('should create an instance', () => {
    expect(naviModule).toBeTruthy();
  });
});
