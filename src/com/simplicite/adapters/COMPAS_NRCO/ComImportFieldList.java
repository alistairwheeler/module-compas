package com.simplicite.adapters.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.integration.*;
import com.simplicite.util.AppLogEvent;
import com.simplicite.util.AppLog;
import com.simplicite.util.Message;
import com.simplicite.util.ObjectDB;
import com.simplicite.util.ObjectField;
import com.simplicite.util.Tool;
import com.simplicite.util.integration.LineBasedAdapter;
import com.simplicite.util.tools.CSVTool;
import com.simplicite.util.DocumentDB;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileInputStream;

import com.simplicite.util.tools.*;

// -----------------------------------------------------------------------------------------------------------
// Note: you should consider using one of com.simplicite.util.integration sub classes instead of SimpleAdapter
// -----------------------------------------------------------------------------------------------------------

/**
 * Adapter ComImportFieldList
 */
public class ComImportFieldList extends LineBasedAdapter {
	private static final long serialVersionUID = 1L;


	/**
	 * Process method
	 */
	@Override
	public void process() throws InterruptedException {
		try {
			
		} catch (Exception e) {
			AppLog.error(getClass(), "process", null, e, getGrant());
		}
	}
	
	@Override
	public String processLine(long lineNumber, String line){
		processNRCOline(lineNumber, line);		
		return null;
	}
	
	private void processNRCOline(long lineNumber, String line){
		CSVTool csvTool = null;	
		csvTool = new CSVTool(';','"');
		try{
			String[] v = csvTool.parse(line);
			AppLog.info(getClass(), "processNRCOline", v[0], getGrant());
		}
		catch(Exception e){
			AppLog.error(getClass(), "process", null, e, getGrant());
		}
	}
}