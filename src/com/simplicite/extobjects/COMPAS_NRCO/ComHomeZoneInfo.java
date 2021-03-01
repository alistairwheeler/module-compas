package com.simplicite.extobjects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.*;

/**
 * External object ComHomeZoneInfo
 */
public class ComHomeZoneInfo extends ExternalObject {
	private static final long serialVersionUID = 1L;

	/**
	 * Display method
	 * @param params Request parameters
	 */
	@Override
	public Object display(Parameters params) {
		try {
			Grant g = getGrant();
			ObjectDB userObj = g.getTmpObject("ComUser");
			appendJSInclude(HTMLTool.getResourceJSURL(this, "SCRIPT"));
			appendMustache();
			setDecoration(false);
			userObj.select(String.valueOf(getGrant().getUserId()));
			String zoneLibelle = userObj.getFieldValue("comUserZoneLibelle");
			JSONObject infos = new JSONObject();
			infos.put("libelle", zoneLibelle);
			return sendJavaScript("ComHomeZoneInfo.init('"+infos+"');");
		} catch (Exception e) {
			AppLog.error(getClass(), "display", null, e, getGrant());
			return e.getMessage();
		}
	}
}