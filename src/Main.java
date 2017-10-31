import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static List<DetailOfRobot> dump = new ArrayList<>();
    private static final Object LOCK = new Object();
    public static long time;

    public static void main(String[] args) throws InterruptedException, IllegalThreadStateException {
        for (int i = 0; i < 20; i++) {
            dump.add(getDetail());
        }
        System.out.println(dump);
        Professor professor1 = new Professor("Jon");
        Professor professor2 = new Professor("Konor");

        for (int i = 0; i < 100; i++) {
            time = System.currentTimeMillis();
            System.out.println("Day " + (i + 1));
            Thread thread1 = new Thread(new Servant(professor1));
            Thread thread2 = new Thread(new Servant(professor2));
            Thread thread3 = new Thread(new Dump());
            thread1.start();
            thread2.start();
            thread3.start();
            try {
                thread1.join();
                thread2.join();
                thread3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(dump);
            if (100 -(System.currentTimeMillis()) - time > 0) {
                Thread.sleep(100 - (System.currentTimeMillis() - time));
            }
            System.out.println();
        }

        System.out.println("Professor " + professor1.getName() + " made " + howMuchRobots(professor1) + "robot(s).");
        System.out.println("Professor " + professor2.getName() + " made " + howMuchRobots(professor2) + "robot(s).");
        if (howMuchRobots(professor1) > howMuchRobots(professor2)){
            System.out.println("Professor " + professor1.getName() + " won.");
        } else {
            if (howMuchRobots(professor1) < howMuchRobots(professor2)){
                System.out.println("Professor " + professor2.getName() + " won.");
            } else {
                System.out.println("Draw.");
            }
        }

    }

    public static class Servant implements Runnable {
        Professor professor;
        public Servant(Professor professor) {
            this.professor = professor;
        }

        @Override
        public void run() {
            try {
                int x = new Random().nextInt(5);
                System.out.println("Professor " + professor.getName() + " needs " + x + " detail(s).");
                for (int i = 0; i < x; i++) {
                    synchronized (LOCK) {
                        professor.sendServant(dump);
                    }
                    Thread.sleep(5);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Dump implements Runnable {
        @Override
        public void run() {
            try {
                int number = new Random().nextInt(5);
                for (int i = 0; i < number; i++) {
                    synchronized (LOCK) {
                        DetailOfRobot detail = getDetail();
                        dump.add(detail);
                        System.out.println(detail + " was added to the dump.");
                    }
                    Thread.sleep(5);
                }
            } catch(InterruptedException e) {
            e.printStackTrace();
            }
        }
    }

    static DetailOfRobot getDetail(){
        switch (new Random().nextInt(9)){
            case 0:
                return DetailOfRobot.HEAD;
            case 1:
                return DetailOfRobot.BODY;
            case 2:
                return DetailOfRobot.LEFT_HAND;
            case 3:
                return DetailOfRobot.LEFT_LEG;
            case 4:
                return DetailOfRobot.RIGHT_HAND;
            case 5:
                return DetailOfRobot.RIGHT_LEG;
            case 6:
                return DetailOfRobot.HDD;
            case 7:
                return DetailOfRobot.RAM;
            case 8:
                return DetailOfRobot.CPU;
        }
        return null;
    }

    public static int  howMuchRobots (Professor professor){
        int i = professor.getRobots().get(getDetail());
        for (Integer x : professor.getRobots().values()) {
            if (i > x){
                i = x;
            }
        }
        return i;
    }
}
