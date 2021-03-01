package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.objects.System.User;

/**
 * Business object ComUser
 */
public class ComUser extends User {
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		Grant g = getGrant();
		if (g.hasResponsibility("COM_COMMERCIAL")) {
			setSearchSpec("t.row_id='" + g.getUserId() + "'");
		}
		
		if(g.hasResponsibility("COM_MANAGER") && !g.hasResponsibility("COM_SUPER_MANAGER") &&!g.hasResponsibility("ADMIN")){
			String userZone = g.simpleQuery("select user_zoneid from m_user where row_id='" + g.getUserId() + "'");
			setSearchSpec("t.USER_ZONEID = '"+userZone+"'");
		}
		
	}

	@Override
	public void initList(ObjectDB parent) {
		Grant g = getGrant();
		if (g.hasResponsibility("COM_MANAGER") && !g.hasResponsibility("COM_SUPER_MANAGER") && !g.hasResponsibility("ADMIN")) {
			ObjectDB user = g.getTmpObject("ComUser");
			user.select(String.valueOf(g.getUserId()));
			String zone = user.getFieldValue("comUserZoneLibelle");
			String zoneRowID = g.simpleQuery("select row_id from com_userzone where USERZONE_LIBELLE='" + zone + "'");
			AppLog.info(getClass(), "postLoad", zoneRowID, getGrant());
			setSearchSpec("t.USER_ZONEID='" + zoneRowID + "'");
		}
		
		ObjectDB test = g.getTmpObject("ComUser");
		List<String[]> liste = test.search();
		for(String[] obj : liste){
			test.setValues(obj);
			if(("3").equals(getFieldFilter("usr_active"))){
				getField("viw_name").setVisibility(ObjectField.VIS_HIDDEN);
			}
			else{
				getField("viw_name").setVisibility(ObjectField.VIS_BOTH);
			}
		}
		
	}

}