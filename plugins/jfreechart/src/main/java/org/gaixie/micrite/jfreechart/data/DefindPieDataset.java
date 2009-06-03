package org.gaixie.micrite.jfreechart.data;

public class DefindPieDataset {
    private String key;
    private String value;
    public DefindPieDataset(String key ,String value){
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
