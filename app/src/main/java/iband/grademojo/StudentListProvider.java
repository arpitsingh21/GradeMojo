package iband.grademojo;

/**
 * Created by dell on 22-11-2017.
 */

public class StudentListProvider {
    String id,roll,name,gender;

    public StudentListProvider(String id, String roll, String name, String gender) {
        this.id = id;
        this.roll = roll;
        this.name = name;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
