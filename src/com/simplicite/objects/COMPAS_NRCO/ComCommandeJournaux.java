package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComCommandeJournaux
 */
public class ComCommandeJournaux extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public String getUserKeyLabel(String[] row) {
		String label = getFieldDisplayValue("comComEdition");
		return label;
	}

	@Override
	public boolean isUpdateEnable(String[] row) {
		Grant g = getGrant();
		// if user is commercial update not available if zone is != zone user
		if (g.hasResponsibility("COM_COMMERCIAL") && !g.hasResponsibility("COM_MANAGER")
				&& !g.hasResponsibility("COM_ADV")) {
			ObjectDB ac = getGrant().getTmpObject("ComActionCommerciale");
			ac.select(getFieldValue("comComACid"));
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
			if (isPanelOf("ComACPNR") || isPanelOf("ComACPBR") || isPanelOf("ComACVPR")) {
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
			if (isPanelOf("ComACPNR") || isPanelOf("ComACPBR") || isPanelOf("ComACVPR")) {
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
	public void initCreate() {
		Grant g = getGrant();
		ObjectDB parent = getParentObject();
		if ("ComACVentePriv".equals(parent.getName())) {
			getFieldArea("ComCommandeJournaux-1").setVisible(false);
		}
		if ("ComACPNR".equals(parent.getName())) {
			getFieldArea("ComCommandeJournaux-3").setVisible(false);
		}
	}

	@Override
	public void initUpdate() {
		Grant g = getGrant();
		/*
		 * if(Tool.isEmpty(getFieldValue("comPNRidentifiant")))
		 * getFieldArea("ComCommandeJournaux-1").setVisible(false);
		 * if(Tool.isEmpty(getFieldValue("comComVPid")))
		 * getFieldArea("ComCommandeJournaux-3").setVisible(false);
		 */
	}

}