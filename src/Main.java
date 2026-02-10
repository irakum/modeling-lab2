import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Create creator = new Create(1);
        creator.setName("CREATOR");
        creator.setDistribution("exp");

        Process process1 = new Process(1, 1);
        process1.setName("PROCESSOR1");
        process1.setDistribution("exp");
        process1.setMaxqueue(5);

        Process process2 = new Process(1, 1);
        process2.setName("PROCESSOR2");
        process2.setDistribution("exp");
        process2.setMaxqueue(5);

        Process process3 = new Process(1, 1);
        process3.setName("PROCESSOR3");
        process3.setDistribution("exp");
        process3.setMaxqueue(2);

        Dispose dispose = new Dispose();
        dispose.setName("DISPOSE");

        creator.setNextElement(process1);

        process1.addNextElement(process2, 1);
        process1.addNextElement(process3, 0);

        process2.addNextElement(process3, 1);
        process2.addNextElement(dispose, 0);

        process3.addNextElement(dispose, 1);
        process3.addNextElement(process2, 0);

        ArrayList<Element> list = new ArrayList<>();
        list.add(creator);
        list.add(process1);
        list.add(process2);
        list.add(process3);
        list.add(dispose);

        Model model = new Model(list);
        model.simulate(1000.0);
    }
}