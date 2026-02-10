import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Створення початкового елемента Create
        Create creator = new Create(1.5);
        creator.setName("CREATOR");
        creator.setDistribution("exp");

        // Створення трьох процесів
        Process process1 = new Process(2, 2);
        process1.setName("PROCESSOR1");
        process1.setDistribution("exp");
        process1.setMaxqueue(5);

        Process process2 = new Process(4.0, 3);
        process2.setName("PROCESSOR2");
        process2.setDistribution("exp");
        process2.setMaxqueue(5);

        Process process3 = new Process(3.0, 1);
        process3.setName("PROCESSOR3");
        process3.setDistribution("exp");
        process3.setMaxqueue(5);

        // Завершальний елемент Dispose
        Dispose dispose = new Dispose();
        dispose.setName("DISPOSE");

        // Зв'язування елементів у ланцюжок
        creator.setNextElement(process1);

        process1.addNextElement(process2, 0.7);
        process1.addNextElement(process3, 0.3);

        process2.addNextElement(process3, 0.8);
        process2.addNextElement(dispose, 0.2);

        process3.addNextElement(dispose, 0.6);
        process3.addNextElement(process2, 0.4);

        // Додавання елементів до списку моделі
        ArrayList<Element> list = new ArrayList<>();
        list.add(creator);
        list.add(process1);
        list.add(process2);
        list.add(process3);
        list.add(dispose);

        // Ініціалізація та запуск моделі
        Model model = new Model(list);
        model.simulate(1000.0);
    }
}