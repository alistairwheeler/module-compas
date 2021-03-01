package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.COMPAS_NRCO.ComSAPHelper;
import com.simplicite.commons.COMPAS_NRCO.JSONToolHelper;
import org.json.*;
import java.io.*;

/**
 * Business object ComClient
 */
public class ComClient extends ObjectDB {
	private static final long serialVersionUID = 1L;

	/*	
	@Override
	public String getUserKeyLabel(String[] row) {
		String label = getFieldValue("comCliRaisonSociale");
		return label;
	}*/

	@Override
	public void postLoad() {
		// if Commercial or manager, only show AC where zone of responsable is same as
		// zone user
		Grant g = getGrant();
		if(g.hasResponsibility("COM_API")){
			setDefaultSearchSpec("1=1");
		}
		if (g.hasResponsibility("COM_COMMERCIAL")||g.hasResponsibility("COM_MANAGER") && !g.hasResponsibility("COM_SUPER_MANAGER") && (!g.hasResponsibility("DESIGNER") && !g.hasResponsibility("COM_API"))) {
			ObjectDB user = g.getTmpObject("ComUser");
			user.select(String.valueOf(g.getUserId()));
			String userZone = user.getFieldValue("comUserZoneLibelle");
			setDefaultSearchSpec("t.cli_zone =" + "'" + userZone + "'");
		}
		if (g.hasResponsibility("DESIGNER")||g.hasResponsibility("COM_API")) {
			setDefaultSearchSpec("1=1");
		}
		// if ADV, show everything

	}

	@Override
	public void initCreate() {
		Grant g = getGrant();
		getFieldArea("ComClient-2").setVisible(false);
		
		// vu avec alistaire
		// getField("comCliSAPid").setVisibility(ObjectField.VIS_HIDDEN);
		ObjectDB userObj = g.getTmpObject("ComUser");
		
		if (g.hasResponsibility("COM_API")) {
			List<ObjectField> fields = getFields();
			for (ObjectField field : fields) {
				field.setUpdatable(ObjectField.UPD_ALWAYS);
			}
		}
		else{
			userObj.select(String.valueOf(getGrant().getUserId()));
			setFieldValue("comCliZone", userObj.getFieldValue("comUserZoneLibelle"));
		}
		
	}

	@Override
	public void initUpdate() {
		
		Grant g = getGrant();
		
		getFieldArea("ComClient-2").setVisible(true);
		
		// bug d'affichage vu avec alistaire
		// getField("comCliSAPid").setVisibility(ObjectField.VIS_FORM);

		// if ("PRO".equals(getFieldValue("comCliNature"))) {
		//	getField("comCliSAPid").setVisibility(ObjectField.VIS_HIDDEN);
		//}
		
		if (g.hasResponsibility("COM_API")) {
			List<ObjectField> fields = getFields();
			for (ObjectField field : fields) {
				field.setUpdatable(ObjectField.UPD_ALWAYS);
			}
		}

	}

	@Override
	public void initRefSelect(ObjectDB parent) {
		if(parent != null){
			if (parent.getName().startsWith("ComAC")) {
				if (!parent.getField("comACContactId").isEmpty())
					setSearchSpec("t.row_id in (select com_contact_client_id from com_contact where contact_id='"
							+ parent.getFieldValue("comACContactId") + "')");
				else
					setSearchSpec("t.CLI_NATURE = 'CLI'");
			}
		}
	
	}

	@Override
	public String preDelete() {
	
		Grant g = getGrant();
		
		if (!g.hasResponsibility("DESIGNER") && !g.hasResponsibility("COM_API")) {
			AppLog.info(getClass(), "method", "IDIOT", getGrant());
			if (!Tool.isEmpty(getFieldValue("comCliSAPid"))) {
				return Message.formatError("ERROR_CODE", "CLIENT ENREGISTRÉ DANS SAP", "code SAP");
			}
		}
		// return Message.formatInfo("INFO_CODE", "Message", "fieldName");
		// return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
		return null;	
		
	}

	@Override
	public String preSave() {

		Grant g = getGrant();
		GMapTool gmap = new GMapTool(g);
		ObjectDB adresse = g.getTmpObject("ComAdresse");
		String adresseRowId = g.simpleQuery(
				"select row_id from com_adresse where adr_type='PRIN' AND com_adresse_client_id=" + getRowId());
		synchronized (adresse) {
			if (adresse.select(adresseRowId)) {
				setFieldValue("comCliGeoCoords", adresse.getFieldValue("comAdrCoords"));
				AppLog.info(getClass(), "method", adresse.getFieldValue("comAdrCoords"), getGrant());
				setFieldValue("comCliGeoLibelle", adresse.getFieldValue("comAdrLibelle"));
				setFieldValue("comCliGeoLigne1", adresse.getFieldValue("comAdrLigne1"));
				setFieldValue("comCliGeoVille", adresse.getFieldValue("comAdrVille"));
			}
		}

		// return Message.formatInfo("INFO_CODE", "Message", "fieldName");
		// return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
		// return Message.formatError("ERROR_CODE", "Message", "fieldName");
		return null;
	}
	
	public String cliToSAP(){
		
		//has 1 principal adresse
		//has 1 principal contact
		Grant g = getGrant();
		ComSAPHelper sap = new ComSAPHelper(g);
		String adressCount = g.simpleQuery(
				"select count(*) from com_adresse where adr_type='PRIN' AND com_adresse_client_id=" + getRowId());
		String contactCount = g.simpleQuery(
				"select count(*) from com_contact where CONTACT_PRINCIPAL='1' AND COM_CONTACT_CLIENT_ID=" + getRowId());
		if("0".equals(adressCount)){
			return  Message.formatError("ERROR_CODE", "Veuillez renseigner une adresse principale", "fieldName");
		}
		else if("0".equals(contactCount)){
			return  Message.formatError("ERROR_CODE", "Veuillez renseigner un contact principal", "fieldName");
		}
		else{
			TreeView treeview = g.getTreeView("ComClientTree");
			try{
				String reponse = JSONToolHelper.getObjectAsJsonTreeview(this, getRowId(), treeview);
				JSONObject jsonRep = new JSONObject(reponse);
				AppLog.info(getClass(), "method", jsonRep.toString(), getGrant());
				//try {
					sap.prospectToSap(jsonRep);
						//setFieldValue("comCliNature", "CLI");
					return  Message.formatSimpleInfo("Prospect envoyé à SAP avec succès");
					
				//} catch (IOException e) {
				//	AppLog.error(getClass(), "remonterPNRSap", "ERROR", e, g);
				//}
			}
			catch(Exception e){
				AppLog.info(this.getClass(), "getJSON", e.getMessage(), g);
			}
			return Message.formatError("ERROR_CODE", "Echec de l'envoi vers SAP", "fieldName");
		}
	}

}