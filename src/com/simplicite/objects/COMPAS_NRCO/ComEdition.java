package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComEdition
 */
public class ComEdition extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public String getUserKeyLabel(String[] row) {
		String label = null;
		if (!Tool.isEmpty(getFieldValue("comEdEditionNR")))
			label = getFieldDisplayValue("comEdEditionNR");
		if (!Tool.isEmpty(getFieldValue("comEdEditionCP")))
			label = getFieldDisplayValue("comEdEditionCP");
		return label;
	}

	@Override
	public void initUpdate() {
		// get Action commerciale
		// if label of action commerciale is not updatable
		// edition is not updatable
		String acid = getFieldValue("comEdACid");
		ObjectDB ac = getGrant().getTmpObject("ComActionCommerciale");
		if(ac.select(acid)){
			if (!ac.getField("comAClibelle").isUpdatable()) {
				setAllFieldsUpdatable(false);
				AppLog.info(getClass(), "initUpdate", "sorry", getGrant());
			}
		}
	}	

	@Override
	public boolean isUpdateEnable(String[] row) {
		Grant g = getGrant();
		// if user is commercial update not available if zone is != zone user
		if (g.hasResponsibility("COM_COMMERCIAL") && !g.hasResponsibility("COM_MANAGER")
				&& !g.hasResponsibility("COM_ADV")) {
			ObjectDB ac = getGrant().getTmpObject("ComActionCommerciale");
			ac.select(getFieldValue("comEdACid"));
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
			if (isPanelOf("ComACOPV")) {
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
			if (isPanelOf("ComACOPV")) {
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
	public String postSave() {
		
		boolean bigComission = false;
		
		
		ArrayList<String> comissions = new ArrayList<String>(); // Create an ArrayList object
		
		if( Integer.parseInt(this.getFieldValue("comEdLundi")) >= 500 ){
			bigComission = true;
			comissions.add("Lundi");
		}
		if( Integer.parseInt(this.getFieldValue("comEdMardi")) >= 500 ){
		    bigComission = true;
		    comissions.add("Mardi");
		}
		if( Integer.parseInt(this.getFieldValue("comEdMercredi")) >= 500 ){
		    bigComission = true;
		    comissions.add("Mercredi");
		}
		if( Integer.parseInt(this.getFieldValue("comEdJeudi")) >= 500 ){
		    bigComission = true;
		    comissions.add("Jeudi");
		}
		if( Integer.parseInt(this.getFieldValue("comEdVendredi")) >= 500 ){
		    bigComission = true;
		    comissions.add("Vendredi");
		}
		if( Integer.parseInt(this.getFieldValue("comEdSamedi")) >= 500 ){
		    bigComission = true;
		    comissions.add("Samedi");
		}
		if( Integer.parseInt(this.getFieldValue("comEdDimanche")) >= 500 ){
		    bigComission = true;
		    comissions.add("Dimanche");
		}
		
		if(bigComission){
			return Message.formatWarning("Attention", "Vous avez saisi une valeur supérieure à 500 pour le(s) jour(s) suivant(s) : "+comissions.toString(), "");
		}

		return null;
	}
	
}