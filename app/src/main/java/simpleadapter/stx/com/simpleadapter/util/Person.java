package simpleadapter.stx.com.simpleadapter.util;

/**
 * Created by chao on 2015/4/20.
 */

public class Person {
    public int _id;
    public String name;
    public String phone;
    public String info;
    public String sex;

    public Person() {
    }

    public Person(String name, String phone, String info, String sex) {
        this.name = name;
        this.phone = phone;
        this.info = info;
        this.sex = sex;
    }
}