package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComContact
 */
public class ComContact extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		Grant g = getGrant();
		if (g.hasResponsibility("COM_API")) {
			setDefaultSearchSpec("1=1");
		}
		if ((g.hasResponsibility("COM_COMMERCIAL") || g.hasResponsibility("COM_MANAGER")) && !g.hasResponsibility("DESIGNER") && !g.hasResponsibility("COM_SUPER_MANAGER") ) {
			ObjectDB user = g.getTmpObject("ComUser");
			user.select(String.valueOf(g.getUserId()));
			String userZone = user.getFieldValue("comUserZoneLibelle");
			setSearchSpec(
					"t.row_id in (select con.row_id from com_client cli right join com_Contact con on con.com_contact_client_id=cli.row_id where cli.cli_zone='"
							+ userZone + "')");
		}
	}
	
		
	@Override
	public void initRefSelect(ObjectDB parent) {
		if(parent != null){
			if (parent.getName().startsWith("ComAC")) {
				AppLog.info(getClass(), "method", parent.getFieldValue("comAClibelle"), getGrant());
				if (!parent.getField("comACClientId").isEmpty())
				{
					AppLog.info(getClass(), "method", "coucou", getGrant());
					setSearchSpec("t.com_contact_client_id=" + parent.getFieldValue("comACClientId"));
				}
					
			}
		}
	}

	@Override
	public String getUserKeyLabel(String[] row) {
		String label = getFieldValue("comContactPrenom") + " " + getFieldValue("comContactNom");
		return label;
	}

	@Override
	public List<String> preValidate() {
		List<String> msgs = new ArrayList<String>();

		// if for client there exists a principal -> WARNING
		Grant g = getGrant();
		String rS = getFieldValue("comCliRaisonSociale");
		String clientId = getFieldValue("comContactClientId");
		String id = getFieldValue("comContactID");
		if (!Tool.isEmpty(rS)) {
			ObjectDB contacts = g.getTmpObject("ComContact");
			contacts.resetFilters();
			contacts.setFieldFilter("comContactClientId", clientId);
			try {
				List<String[]> rows = new BusinessObjectTool(contacts).search();
				String principal = getFieldValue("comContactPrincipal");
				if ("1".equals(principal)) {
					AppLog.info(getClass(), "preValidate", "isPrincipal", g);
					for (int i = 0; i < rows.size(); i++) {
						String[] contact = rows.get(i);
						contacts.setValues(contact);
						String contactPrincipal = contacts.getFieldValue("comContactPrincipal");
						String idContact = contacts.getFieldValue("comContactID");
						if ("1".equals(contactPrincipal)) {
							AppLog.info(getClass(), "preValidate", idContact + " " + id, g);
							if (!idContact.equals(id)) {
								msgs.add(Message.formatError("Erreur d'enregistrement'",
										"Il existe déjà un contact principal pour ce client", "Type"));
							}
						}
					}
				}
			} catch (Exception e) {
				AppLog.error(getClass(), "", null, e, getGrant());
			}
		}

		// msgs.add(Message.formatInfo("INFO_CODE", "Message", "fieldName"));
		// msgs.add(Message.formatWarning("WARNING_CODE", "Message", "fieldName"));
		// msgs.add(Message.formatError("ERROR_CODE", "Message", "fieldName"));
		if(!g.hasResponsibility("COM_API")){
			if (Tool.isEmpty(getFieldValue("comContactEmail")) && Tool.isEmpty(getFieldValue("comContactTel")))
			msgs.add(Message.formatError("ERROR_CODE",
					"Veuillez saisir un numéro de téléphone ou un email pour ce contact", "fieldName"));
		}
		

		return msgs;
	}

}