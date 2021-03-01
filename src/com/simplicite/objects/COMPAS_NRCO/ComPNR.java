package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComPNR
 */
public class ComPNR extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public String preSave() {

		// if(getField("comPNRprixTtc").hasChanged()||getField("comPNRtauxRemise").hasChanged()){
		double ttc = Double.parseDouble(getFieldValue("comPNRprixTtc"));

		double remise = Double.parseDouble(getFieldValue("comPNRtauxRemise"));
		double prc = ttc * remise;
		double rem = prc / 100;
		double total = ttc - (ttc * (remise / 100));
		AppLog.info(getClass(), "preSave", Double.toString(rem), getGrant());
		setFieldValue("comPNRtotalRemise", total);
		// }

		// return Message.formatInfo("INFO_CODE", "Message", "fieldName");
		// return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
		// return Message.formatError("ERROR_CODE", "Message", "fieldName");
		return null;
	}

}