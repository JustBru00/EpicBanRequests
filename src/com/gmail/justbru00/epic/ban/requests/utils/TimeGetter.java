package com.gmail.justbru00.epic.ban.requests.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeGetter {

	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}
