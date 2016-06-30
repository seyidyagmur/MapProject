package demo.chat.com.myfirstinternapp;

/**
 * Created by engineering on 14.06.2016.
 */
public class StringWithTag {
    public String name;
    public String id;
    public float x;
    public float y;
    public StringWithTag(String namePart, String idPart,float x,float y) {
        name = namePart;
        id = idPart;
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return name;
    }
}