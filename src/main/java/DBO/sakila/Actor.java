package DBO.sakila;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Actor {
    private int actor_id;
    private String first_name;
    private String last_name;
    private Date last_update;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Actor(int actor_id, String first_name, String last_name, Date last_update) {
        this.actor_id = actor_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.last_update = last_update;
    }

    public int getActor_id() {
        return actor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Date getLast_update() {
        return last_update;
    }

    @Override
    public String toString() {
        return "ActorDBO{" +
                "actor_id=" + actor_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", last_update=" + last_update +
                '}';
    }
}
