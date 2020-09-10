package fabrica.production.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;

@Embeddable
public class DataSheet implements Comparable<DataSheet>, ValueObject {
    private byte[] vec;
    private String typeF;

    protected DataSheet(){

    }
    public DataSheet(byte[] vec, String typeF){
        this.vec = vec;
        this.typeF = typeF;
    }

    public byte[] obtainBytes(){
        return this.vec;
    }

    public String obtainFileFormat(){return this.typeF;}

    @Override
    public int compareTo(DataSheet o) {
        return this.toString().compareToIgnoreCase(o.toString());
    }


}
