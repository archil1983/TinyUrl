package org.neueda.quiz.dao;

import org.neueda.quiz.constants.TinyUrlTableEnum;

import java.util.Map;

public interface TinyUrlDao {

    Map<TinyUrlTableEnum, String> getRecordById(String idValue, TinyUrlTableEnum... columns);

    void insertRow(Map<TinyUrlTableEnum, String> row);

}
