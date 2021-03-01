package com.simplicite.extobjects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.webapp.web.JQueryWebPage;
import org.json.*;
import com.simplicite.commons.COMPAS_NRCO.JSONToolHelper;
import com.simplicite.commons.COMPAS_NRCO.ComSAPHelper;

/**
 * External object ComConventionDiffExt
 */
public class ComConventionDiffExt extends ExternalObject {
    private static final long serialVersionUID = 1L;

    /**
     * Display method
     * @param params Request parameters
     */
    @Override
    public Object display(Parameters params) {
    	
    	setPublic(true);

        JQueryWebPage wp = new JQueryWebPage(params.getRoot(), getDisplay());
        wp.appendAjax();
        wp.appendMustache();
        wp.appendCSSInclude(HTMLTool.getResourceCSSURL(this, "STYLES"));
        wp.appendJSInclude(HTMLTool.getResourceJSURL(this, "SCRIPT"));
        //wp.appendCSSInclude("https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css");

        setDecoration(false);
        setPublic(true);

        try {


            ObjectDB ac = getGrant().getTmpObject("ComACOPV");
            ac.resetValues();
            ac.resetFilters();
            

		
            ac.setFieldFilter("comACkey", params.getParameter("id"));
            List < String[] > rows = ac.search();
            if (!Tool.isEmpty(rows)) {
                for (String[] row: rows) {

                    AppLog.info(getClass(), "display", "===== EXT OBJ FOUND AC ========", getGrant());

                    ac.setValues(row, false);

                    String rowIdOPV = params.getParameter("row_id");

                    TreeView treeview = getGrant().getTreeView("ComACOPV");

                    String reponse = JSONToolHelper.getObjectAsJsonTreeview(getGrant().getTmpObject("ComACOPV"), ac.getRowId(), treeview);

                    JSONObject jsonRep = new JSONObject(reponse);

                    JSONObject item = jsonRep.getJSONObject("item");

                    AppLog.info(getClass(), "method", reponse, getGrant());

                    boolean portageSemaine = item.getBoolean("comPVps");
                    boolean portageDimanche = item.getBoolean("comPVportageDim");

                    if (!portageSemaine && !portageDimanche) {
                        wp.append(HTMLTool.getResourceHTMLContent(this, "HTML_MAGASIN"));
                        //convention magasin uniquement
                    } else if (portageSemaine || portageDimanche) {
                        wp.append(HTMLTool.getResourceHTMLContent(this, "HTML_PORTAGE"));
                        //convention portage
                        if (portageDimanche) {
                            jsonRep.put("displayDimanche", "inline");
                        } else {
                            jsonRep.put("displayDimanche", "none");
                        }

                        Double comissionPort = item.optDouble("comPVfactCommPort", 0);
                        Double comissionDim = item.optDouble("comPVfactCommDim", 0);

                        jsonRep.put("comissionSemaine", comissionPort);
                        jsonRep.put("comissionDimanche", comissionDim);
                    }

                    JSONArray links = jsonRep.getJSONArray("links");

                    for (int i = 0; i < links.length(); i++) {
                        JSONObject link = links.getJSONObject(i);
                        if ("ComContact".equals(link.getString("object"))) {
                            JSONArray list = link.getJSONArray("list");
                            if(list.length()==0){
                            	break;
                            }
                            AppLog.info(getClass(), "display", list.toString(), getGrant());
                            
                            JSONObject list1 = list.getJSONObject(0);
                            JSONObject contactItem = list1.getJSONObject("item");
                            jsonRep.put("contact", contactItem.optString("comContactCivilite") + " " + contactItem.optString("comContactNom") + " " + contactItem.getString("comContactPrenom"));
                            break;
                        }
                        if ("ComEdition".equals(link.getString("object"))) {
                            JSONArray listEd = link.getJSONArray("list");
                            String dateEdition = null;
                            for (int j = 0; j < listEd.length(); j++) {
                                JSONObject ed = listEd.getJSONObject(j);
                                JSONObject edItem = ed.getJSONObject("item");
                                if ("PRI".equals(edItem.getString("comEdType"))) {
                                	if("CP".equals(edItem.getString("comEdSupport"))){
                                		jsonRep.put("IsCp", true);
                                	}
                                    dateEdition = edItem.getString("comEdDateDebut");
                                    break;
                                }
                            }
                            if (dateEdition == null) {
                                return "Merci de renseigner une édition principale";
                            } else {
                                String[] splitDate = dateEdition.split("-");
                                String formatedDate = splitDate[2] + "." + splitDate[1] + "." + splitDate[0];
                                jsonRep.put("date", formatedDate);
                            }



                        }
                    }


					String freq = "HEB".equals(ac.getFieldValue("comPVfactFreqFact")) ? "Hebdomadaire" : "Mensuelle";
					
                    jsonRep.put("frequenceFact", ac.getFieldDisplayValue("comPVfactFreqFact"));
                    jsonRep.put("displayHebdo", ("HEB".equals(ac.getFieldValue("comPVfactFreqFact")) ? "block" : "none"));
                    jsonRep.put("displayMensuel", ("MEN".equals(ac.getFieldValue("comPVfactFreqFact")) ? "block" : "none"));


                    if (!item.isNull("comACClientId__comCliRcs")) {
                        jsonRep.put("rcsMension", "inscrit au Registre du Commerce et des Sociétés : "+item.getString("comACClientId__comCliRcs"));
                      
                    }else {
						jsonRep.put("rcsMension", "");
                    }

                    wp.appendJS("var data = " + jsonRep + ";");

                }

            }
            wp.setReady("ComConventionDiffExt.render('" + wp.getRoot() + "');");
            return wp.toString();

        } catch (Exception e) {
            AppLog.error(getClass(), "display", null, e, getGrant());
            return e.getMessage();
        }
    }
}