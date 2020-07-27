package org.neueda.quiz.constants;

public enum TinyUrlTableEnum {

    ID("id"), TINY_URL_PATH("tiny_url_path"), FULL_URL("full_url");

    private final String columnName;

    TinyUrlTableEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
