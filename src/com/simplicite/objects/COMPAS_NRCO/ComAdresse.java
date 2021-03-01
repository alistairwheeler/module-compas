package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComAdresse
 */
public class ComAdresse extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public String getUserKeyLabel(String[] row) {
		String label = getFieldValue("comAdrLibelle");
		return label;
	}

	@Override
	public void postLoad() {
		Grant g = getGrant();
		if ((g.hasResponsibility("COM_COMMERCIAL") || g.hasResponsibility("COM_MANAGER"))
				&& !g.hasResponsibility("DESIGNER") && !g.hasResponsibility("COM_SUPER_MANAGER") && !g.hasResponsibility("COM_API") ) {
			ObjectDB user = g.getTmpObject("ComUser");
			user.select(String.valueOf(g.getUserId()));
			String userZone = user.getFieldValue("comUserZoneLibelle");
			setSearchSpec(
					"t.row_id in (select adr.row_id from com_client cli right join com_adresse adr on adr.COM_ADRESSE_CLIENT_ID=cli.row_id where cli.cli_zone='"
							+ userZone + "')");
		} else if (g.hasResponsibility("DESIGNER")||g.hasResponsibility("COM_API"))
			setSearchSpec("1=1");
	}

	@Override
	public void initRefSelect(ObjectDB parent) {
		if(parent != null){
			if (parent.getName().startsWith("ComAC")) {
				if (!parent.getField("comACClientId").isEmpty())
					setSearchSpec(
							"t.com_adresse_client_id=" + parent.getFieldValue("comACClientId") + " AND adr_type='PRIN'");
				if (!parent.getField("comACContactId").isEmpty() && parent.getField("comACClientId").isEmpty())
					setSearchSpec("t.row_id in (select com_adresse_client_id from com_adresse where contact_id="
							+ parent.getFieldValue("comACContactId") + ")");
			}
		}
	}

	@Override
	public List<String> preValidate() {
		List<String> msgs = new ArrayList<String>();
		Grant g = getGrant();
		String rS = getFieldValue("comCliRaisonSociale");
		String rowId = getRowId();
		String clientId = getFieldValue("comAdresseClientId");
		String libele = getFieldValue("comAdrLibelle");
		if (!Tool.isEmpty(rS)) {
			ObjectDB adresse = g.getTmpObject("ComAdresse");
			adresse.resetFilters();
			adresse.setFieldFilter("comAdresseClientId", clientId);
			try {
				List<String[]> rows = new BusinessObjectTool(adresse).search();
				String type = getFieldValue("comAdrType");
				if ("PRIN".equals(type)) {
					for (int i = 0; i < rows.size(); i++) {
						String[] adress = rows.get(i);
						adresse.setValues(adress);
						String addressType = adresse.getFieldValue("comAdrType");
						String rowIdSearch = adresse.getRowId();
						String addressLibele = adresse.getFieldValue("comAdrLibelle");
						if ("PRIN".equals(addressType)) {
							if (!rowId.equals(rowIdSearch)) {
								msgs.add(Message.formatError("Erreur d'enregistrement'",
										"Il existe déjà une adresse principale pour ce client", "Type"));
							}
						}
					}
				}
			} catch (Exception e) {
				AppLog.error(getClass(), "", null, e, getGrant());
			}
		}
		return msgs;
	}

}