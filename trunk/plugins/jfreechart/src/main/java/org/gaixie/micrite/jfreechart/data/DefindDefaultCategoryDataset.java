package org.gaixie.micrite.jfreechart.data;

public class DefindDefaultCategoryDataset {
    private String value;
    private String rowKey;
    private String columnKey;
    public DefindDefaultCategoryDataset(String value ,String rowKey,String columnKey){
        this.value = value;
        this.rowKey = rowKey;
        this.columnKey = columnKey;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getRowKey() {
        return rowKey;
    }
    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }
    public String getColumnKey() {
        return columnKey;
    }
    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }
    
}
