package thread.sync;

public class ModifyAmountThread extends Thread{

  private CommonCalculate calc;
  private boolean addFlag;

  public ModifyAmountThread(CommonCalculate calc, boolean addFlag) {
    this.calc = calc;
    this.addFlag = addFlag;
  }

  @Override
  public void run() {
    for (int i = 0; i < 100000000; i++) {
      if (addFlag) {
        calc.plus(1);
      } else {
        calc.minus(1);
      }
    }
  }
}
