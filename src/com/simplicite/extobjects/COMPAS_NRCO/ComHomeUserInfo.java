package com.simplicite.extobjects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.*;

/**
 * External object ComHomeUserInfo
 */
public class ComHomeUserInfo extends ExternalObject {
	private static final long serialVersionUID = 1L;

	/**
	 * Display method
	 * @param params Request parameters
	 */
	@Override
	public Object display(Parameters params) {
		try {
			AppLog.info(getClass(), "display", "display USERINFO", getGrant());
			Grant g = getGrant();
			ObjectDB userObj = g.getTmpObject("ComUser");
			appendJSInclude(HTMLTool.getResourceJSURL(this, "SCRIPT"));
			appendMustache();
			setDecoration(false);
			AppLog.info(getClass(), "display", String.valueOf(g.getUserId()), getGrant());
			userObj.select(String.valueOf(g.getUserId()));
			JSONObject infos = new JSONObject();
			infos.put("prenom", userObj.getFieldValue("usr_first_name"));
			AppLog.info(getClass(), "display", userObj.getFieldValue("usr_first_name"), getGrant());
			infos.put("nom", userObj.getFieldValue("usr_last_name"));
			return sendJavaScript("ComHomeUserInfo.init('"+infos+"');");
		} catch (Exception e) {
			AppLog.error(getClass(), "display", null, e, getGrant());
			return e.getMessage();
		}
	}
}