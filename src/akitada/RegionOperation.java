package akitada;

import java.io.Serializable;

/**
 * Created by akitada on 18/08/01.
 */
public class RegionOperation implements Serializable {
    private String operation = null;
    private String targetRegion = null;

    public RegionOperation(){
        System.out.println("---called RegionOperation()");
    }

    public RegionOperation(String operation, String targetRegion){
        System.out.println("---called RegionOperation(string,string)");
        this.operation = operation;
        this.targetRegion = targetRegion;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTargetRegion() {
        return targetRegion;
    }

    public void setTargetRegion(String targetRegion) {
        this.targetRegion = targetRegion;
    }

    public String toString() {
        return new String("[RegionOperation(operation = " + operation + "/targetRegion = " + targetRegion +")]");
    }

}