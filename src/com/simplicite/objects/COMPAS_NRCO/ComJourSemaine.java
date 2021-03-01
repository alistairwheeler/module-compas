package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComJourSemaine
 */
public class ComJourSemaine extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public String getUserKeyLabel(String[] row) {
		String label = getFieldValue("comJSnom");
		return label;
	}

	@Override
	public boolean isUpdateEnable(String[] row) {
		Grant g = getGrant();
		// if user is commercial update not available if zone is != zone user
		if (g.hasResponsibility("COM_COMMERCIAL") && !g.hasResponsibility("COM_MANAGER")
				&& !g.hasResponsibility("COM_ADV")) {
			ObjectDB ac = getGrant().getTmpObject("ComActionCommerciale");
			ac.select(getFieldValue("comJSACid"));
			String respId = ac.getFieldValue("comACUserId");
			if (!Integer.toString(g.getUserId()).equals(respId)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isCreateEnable() {
		Grant g = getGrant();
		// if user is commercial update not available if zone is != zone user
		if (g.hasResponsibility("COM_COMMERCIAL") && !g.hasResponsibility("COM_MANAGER")
				&& !g.hasResponsibility("COM_ADV")) {
			if (isPanelOf("ComACPNR")) {
				String resp = getParentObject().getFieldValue("comACUserId");
				if (Integer.toString(g.getUserId()).equals(resp)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isListEditable() {
		Grant g = getGrant();
		// if user is commercial update not available if zone is != zone user
		if (g.hasResponsibility("COM_COMMERCIAL") && !g.hasResponsibility("COM_MANAGER")
				&& !g.hasResponsibility("COM_ADV")) {
			if (isPanelOf("ComACPNR")) {
				String resp = getParentObject().getFieldValue("comACUserId");
				if (Integer.toString(g.getUserId()).equals(resp)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

}