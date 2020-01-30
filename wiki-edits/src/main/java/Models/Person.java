package Models;
/**
 * Model built to parse the kafka message to an object with valuable fields.
 */
public class Person {

    private Integer _id;
    private String _name;
    private String _lastName;

    public Person (Integer  id, String name, String lastName){
        this._id = id;
        this._name = name;
        this._lastName = lastName;
    }

    public Integer getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public String getLastName(){
        return _lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + _id  +
                ", name = '" + _name + '\'' +
                ", last_name = '" + _lastName + '\'' +
                '}';
    }
}
