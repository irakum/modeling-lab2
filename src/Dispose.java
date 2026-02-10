public class Dispose extends Element {

    public Dispose() {
        super();
    }

    @Override
    public void inAct() {
        if (super.getState() == 1) {
            this.updateBusyTime();
        }
        super.outAct();
    }

    @Override
    public void outAct() {

    }

    @Override
    public void printResult() {
        System.out.println(getName() + " quantity = " + getQuantity());
    }
}
