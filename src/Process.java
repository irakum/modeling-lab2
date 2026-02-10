import java.util.ArrayList;
import java.util.List;

public class Process extends Element {
    private int queue, maxqueue, failure;
    private double meanLoad;
    private double meanQueue;
    private int numDevices; // Кількість ідентичних пристроїв
    private int busyDevices; // Поточна кількість зайнятих пристроїв
    private List<Element> nextElements; // Список наступних блоків
    private List<Double> transitionProbabilities = new ArrayList<>();
    private int maxTransitions = 3000; // Ліміт кількості переходів для одного процеса
    private int currentTransitions = 0;

    public Process(double delay, int numDevices) {
        super(delay);
        this.queue = 0;
        this.maxqueue = Integer.MAX_VALUE;
        this.meanQueue = 0.0;
        this.numDevices = numDevices;
        this.busyDevices = 0;
        this.nextElements = new ArrayList<>();
    }
    @Override
    public void inAct() {
        currentTransitions++;
        if (currentTransitions > maxTransitions) {
            System.out.println(getName() + ": Maximum transitions reached. Sending to Dispose.");
            if (!nextElements.isEmpty()) {
                nextElements.getLast().inAct(); // Останній елемент - Dispose
            }
            return;
        }

        if (busyDevices < numDevices) { // Якщо є вільні пристрої
            busyDevices++;
            super.setTnext(super.getTcurr() + super.getDelay());
        } else {
            if (getQueue() < getMaxqueue()) {
                setQueue(getQueue() + 1);
            } else {
                failure++;
            }
        }
    }
    @Override
    public void outAct() {
        super.outAct();
        busyDevices--;

        if (queue > 0) {
            // Обробка наступного запиту з черги
            queue--;
            busyDevices++;
            super.setTnext(super.getTcurr() + super.getDelay());
        } else if (busyDevices > 0) {
            // Якщо є інші зайняті пристрої, зберігаємо мінімальне `tnext`
            double nextTime = Double.MAX_VALUE;
            for (int i = 0; i < busyDevices; i++) {
                nextTime = Math.min(nextTime, super.getTcurr() + super.getDelay());
            }
            super.setTnext(nextTime);
        } else {
            // Якщо всі пристрої вільні, tnext встановлюється в нескінченність
            super.setState(0);
            super.setTnext(Double.MAX_VALUE);
        }

        // Передача елемента до наступного процесу (якщо він є)
        if (!nextElements.isEmpty()) {
            Element next = selectNextElement();
            System.out.println(getName() + " sends request to " + next.getName());
            if (next != null) {
                next.inAct();
            }
        }
    }

    // Додає наступний блок до списку
    public void addNextElement(Element element, double probability) {
        this.nextElements.add(element);
        this.transitionProbabilities.add(probability);
    }

    // Вибір наступного елемента за ймовірностями
    private Element selectNextElement() {
        if (!nextElements.isEmpty() && !transitionProbabilities.isEmpty()) {
            double random = Math.random();
            double cumulativeProbability = 0.0;
            for (int i = 0; i < nextElements.size(); i++) {
                cumulativeProbability += transitionProbabilities.get(i);
                if (random <= cumulativeProbability) {
                    return nextElements.get(i);
                }
            }
        }
        return null;
    }

    public int getFailure() {
        return failure;
    }
    public int getQueue() {
        return queue;
    }
    public void setQueue(int queue) {
        this.queue = queue;
    }
    public int getMaxqueue() {
        return maxqueue;
    }
    public void setMaxqueue(int maxqueue) {
        this.maxqueue = maxqueue;
    }
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure = " + this.getFailure());
    }
    @Override
    public void doStatistics(double delta) {
        meanQueue = getMeanQueue() + queue * delta;
        super.setBusyTime(getBusyTime() + delta * busyDevices);
    }
    public double getMeanQueue() {
        return meanQueue;
    }
    public double getMeanLoad() {
        return this.meanLoad;
    }
    public int getNumDevices() {
        return numDevices;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }
}
