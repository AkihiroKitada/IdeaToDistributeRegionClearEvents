package akitada;

import java.io.Serializable;

/**
 * Created by akitada on 15/09/11.
 */
public class MyPDXObject implements Serializable {
    private String name = null;
    private String date = null;

    public MyPDXObject(){
        System.out.println("---called MyPDXObject()");
    }

    public MyPDXObject(String name, String date){
        System.out.println("---called MyPDXObject(string,string)");
        this.name = name;
        this.date = date;
    }

    public String getName(){
        return this.name;
    }


    public String getDate(){
        return this.date;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return new String("[MyPDXObject(name = " + name + "/date = "
                + date+")]");
    }

}