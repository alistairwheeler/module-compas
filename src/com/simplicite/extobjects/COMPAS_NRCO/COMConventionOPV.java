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

/**
 * External object COMConventionOPV
 */
public class COMConventionOPV extends ExternalObject {
	private static final long serialVersionUID = 1L;



	/**
	 * Display method
	 * @param params Request parameters
	 */
	@Override
	public Object display(Parameters params) {
		
		AppLog.info(getClass(), "display", "1", getGrant());
		
		setPublic(true);
		
		JQueryWebPage wp = new JQueryWebPage(params.getRoot(), getDisplay());
        wp.appendJSInclude(HTMLTool.getResourceJSURL( new ComConventionDiffExt() , "SCRIPT"));
        
        		AppLog.info(getClass(), "display", "2", getGrant());

        
		/* url a passer en param systeme */
		String jsReportUrl = getGrant().getSystemParam("NRCO_JSREPORT_URL");
		String templateConventionShortID = (new JSONObject(getGrant().getSystemParam("NRCO_JSREPORT_TPL_SHORTID"))).getString("conventionOPV");

				AppLog.info(getClass(), "display", "3", getGrant());

		TreeView treeview = getGrant().getTreeView("ComFicheClient");
		
		String ficheClient = JSONToolHelper.getObjectAsJsonTreeview(getGrant().getTmpObject("ComACOPV"), params.getParameter("id"), treeview);
		
		JSONObject payload = new JSONObject(ficheClient);
		
		AppLog.info(getClass(), "display", payload.toString(), getGrant());
		
		JsreportHelper jsreport = new JsreportHelper(jsReportUrl);
				AppLog.info(getClass(), "display", "4", getGrant());

		
		
		AppLog.info(getClass(), "display", payload.toString(), getGrant());
		
		String res = jsreport.report(templateConventionShortID, payload);
				AppLog.info(getClass(), "display", "5", getGrant());

		wp.appendHTML(res);
		
		setDecoration(false);
        setPublic(true);
		
				AppLog.info(getClass(), "display", "6", getGrant());

		return wp.toString();
		
	}
}