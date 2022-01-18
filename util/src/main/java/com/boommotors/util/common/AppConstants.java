package com.boommotors.util.common;

import org.springframework.stereotype.Component;

/**
 *
 * @author rjanumpally
 */
@Component
public interface AppConstants {

    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "30";

    int MAX_PAGE_SIZE = 50;
    String DB_SCHEMA = "LS";
    String[] SERVER_TO_TCU_ACTION_CODES = {"101", "102", "103", "104", "105", "106", "107", "108", "109"};
}
