package org.neueda.quiz.service;

import org.neueda.quiz.constants.TinyUrlTableEnum;
import org.neueda.quiz.dao.TinyUrlDao;
import org.neueda.quiz.exception.TinyUrlNotFoundException;
import org.neueda.quiz.utils.Base64Util;
import org.neueda.quiz.utils.Sha256Utils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.EnumMap;
import java.util.Map;

import static org.neueda.quiz.constants.TinyUrlTableEnum.*;

@Service
public class TinyUrlService {

    private final TinyUrlDao tinyUrlDao;

    public TinyUrlService(TinyUrlDao tinyUrlDao) {
        this.tinyUrlDao = tinyUrlDao;
    }

    public String getOriginalUrl(String tinyUrlPath) throws TinyUrlNotFoundException {
        final long longId = Base64Util.decodeBase64(tinyUrlPath);
        final Map<TinyUrlTableEnum, String> recordRow = tinyUrlDao.getRecordById(String.valueOf(longId), FULL_URL);
        if (recordRow.isEmpty() || StringUtils.isEmpty(recordRow.get(FULL_URL))) {
            throw new TinyUrlNotFoundException("Was not found the tiny Url with path " + tinyUrlPath);
        }
        return recordRow.get(FULL_URL);
    }

    public String createTinyUrlPath(String fullUrl) {
        final long longId = Sha256Utils.encode(fullUrl);
        final String tinyUrlPath = Base64Util.encodeBase64(longId);
        final Map<TinyUrlTableEnum, String> rowMap = new EnumMap<>(TinyUrlTableEnum.class);
        rowMap.put(ID, String.valueOf(longId));
        rowMap.put(FULL_URL, fullUrl);
        rowMap.put(TINY_URL_PATH, tinyUrlPath);
        tinyUrlDao.insertRow(rowMap);
        return tinyUrlPath;
    }


}
