package Models;

public class Code {

    private Integer _id;
    private String _status;

    public Code (Integer  id, String status){
        this._id = id;
        this._status = status;
    }

    public Integer getId(){
        return _id;
    }

    public String getStatus(){
        return _status;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + _id  +
                ", status = '" + _status + '\'' +
                '}';
    }
}
