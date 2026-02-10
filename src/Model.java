import java.util.ArrayList;

public class Model {
    private ArrayList<Element> list = new ArrayList<>();
    double tnext, tcurr;
    int event;

    public Model(ArrayList<Element> elements) {
        list = elements;
        tnext = 0.0;
        event = 0;
        tcurr = tnext;
    }

    public void simulate(double time) {
        while (tcurr < time) {
            tnext = Double.MAX_VALUE;
            Element eventElement = null;

            for (Element e : list) {
                if (e.getTnext() < tnext) {
                    tnext = e.getTnext();
                    eventElement = e;
                }
            }

            // Якщо немає подій, завершити симуляцію
            if (eventElement == null || tnext == Double.MAX_VALUE) {
                System.out.println("No more events to process. Simulation ends.");
                break;
            }

            System.out.println("\nIt's time for event in " +
                    eventElement.getName() + ", time = " + tnext);
/*
            eventElement.doStatistics(tnext - tcurr);
            eventElement.setTcurr(tnext);
*/

            for (Element e : list) {
                e.doStatistics(tnext - tcurr);
                e.setTcurr(tnext);
            }



            tcurr = tnext;

            if (eventElement != null) {
                eventElement.outAct();
            }

            for (Element e : list) {
                if (e.getTnext() == tcurr) {
                    e.outAct();
                }
            }
            printInfo();
        }
        printResult();
    }

    public void printInfo() {
        for (Element e : list) {
            e.printInfo();
        }
    }
    public void printResult() {
        System.out.println("\n-------------RESULTS-------------");
        for (Element e : list) {
            e.printResult();
            if (e instanceof Process) {
                Process p = (Process) e;
                System.out.println("mean length of queue = " +
                        p.getMeanQueue() / tcurr
                        + "\nfailure probability = " +
                        Math.round((p.getFailure() / (double)
                                (p.getQuantity()+p.getFailure())) * 100 * 10000) / (double)10000 + "%;\n"
                       + "\tсереднє завантаження процесора: " + p.getBusyTime() / tcurr + ";");
            }
        }
    }
}
