import java.util.HashMap;
import java.util.List;

class Professor {
    private HashMap<DetailOfRobot, Integer> robots = new HashMap<>();
    private String name;
    Professor(String name){
        this.name = name;
        for (DetailOfRobot detail: DetailOfRobot.values()){
            robots.put(detail, 0);
        }
    }

    public void sendServant(List<DetailOfRobot> dump) {
        DetailOfRobot detail = null;
        if (!dump.isEmpty()) {
            while (detail == null) {
                detail = Main.getDetail();
                if (!dump.contains(detail)){
                    detail = null;
                }
            }
            dump.remove(detail);
            robots.put(detail, robots.get(detail) + 1);
            System.out.println("Professor " + name + " took " + detail);
        }
    }

    public HashMap<DetailOfRobot, Integer> getRobots() {
        return robots;
    }
    public String getName() {
        return name;
    }
}
