public class Dispose extends Element {

    public Dispose() {
        super(); // Викликає конструктор Element
    }

    @Override
    public void inAct() {
        if (super.getState() == 1) { // Якщо пристрій зайнятий
            this.updateBusyTime(); // Оновлення часу завантаження
        }
        super.outAct(); // Фіксує кількість запитів, які досягли Dispose
    }

    @Override
    public void outAct() {
        // Нічого не робить, бо це кінцевий елемент
    }

    @Override
    public void printResult() {
        System.out.println(getName() + " quantity = " + getQuantity());
    }
}
