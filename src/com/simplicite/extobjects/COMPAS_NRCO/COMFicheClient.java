package com.simplicite.extobjects.COMPAS_NRCO;

import java.net.http.*;

import java.net.http.HttpResponse.*;
import com.simplicite.extobjects.COMPAS_NRCO.ComConventionDiffExt;
import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.webapp.web.JQueryWebPage;
import com.simplicite.commons.COMPAS_NRCO.JSONToolHelper;
import com.simplicite.commons.COMPAS_NRCO.JsreportHelper;
import com.simplicite.objects.COMPAS_NRCO.ComACOPV;
import com.simplicite.util.AppLog;


import org.json.JSONObject;
import org.json.JSONArray;

/**
 * External object COMFicheClient
 */
public class COMFicheClient extends ExternalObject {
	private static final long serialVersionUID = 1L;

	/**
	 * Display method
	 * @param params Request parameters
	 */
	@Override
	public Object display(Parameters params) {
		
		
		this.setPublic(true);
		
        JQueryWebPage wp = new JQueryWebPage(params.getRoot(), getDisplay());
        wp.appendJSInclude(HTMLTool.getResourceJSURL( new ComConventionDiffExt() , "SCRIPT"));
        
		String jsReportUrl = getGrant().getSystemParam("NRCO_JSREPORT_URL");
		String templateFicheClientShortID = (new JSONObject(getGrant().getSystemParam("NRCO_JSREPORT_TPL_SHORTID"))).getString("ficheClient");

		
		TreeView treeview = getGrant().getTreeView("ComACOPV");
        String reponse = JSONToolHelper.getObjectAsJsonTreeview(getGrant().getTmpObject("ComACOPV"), params.getParameter("id"), treeview);
		JSONObject jsonRep = new JSONObject(reponse);


		AppLog.info(getClass(), "method", reponse, getGrant());

		
	    ObjectDB ac = getGrant().getTmpObject("ComACOPV");
        ac.resetValues();
        ac.resetFilters();
        // le filtre
        ac.setFieldFilter("comACid", params.getParameter("id"));
        // le search qui retourne des string
		List<String[]> rows = ac.search();
		
		// loooooool 
		if(!Tool.isEmpty(rows)){
			for(String[] row: rows){
				// on peuple notre objet
				ac.setValues(row, false);
				// on récupere notre champ
				jsonRep.put("typo", Tool.isEmpty(ac.getFieldValue("comPVtypologie")) ? "" : ac.getFieldDisplayValue("comPVtypologie"));

			}
			
		}
		AppLog.info(getClass(), "method", jsonRep.toString(), getGrant());
		
		JsreportHelper jsreport = new JsreportHelper(jsReportUrl);
		
		String res = jsreport.report(templateFicheClientShortID, jsonRep);
		
		wp.appendHTML(res);
		
		setDecoration(false);
        setPublic(true);
		
		return wp.toString();

	}
}