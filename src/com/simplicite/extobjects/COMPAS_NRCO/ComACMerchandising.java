package com.simplicite.extobjects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * External object ComACMerchandising
 */
public class ComACMerchandising extends ExternalObject {
	private static final long serialVersionUID = 1L;

	/**
	 * Display method
	 * @param params Request parameters
	 */
	@Override
	public Object display(Parameters params) {
		try {
			return sendJavaScript("ComACMerchandising.render();");
		} catch (Exception e) {
			AppLog.error(getClass(), "display", null, e, getGrant());
			return e.getMessage();
		}
	}
}