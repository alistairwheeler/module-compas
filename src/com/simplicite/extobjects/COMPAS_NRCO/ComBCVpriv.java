package com.simplicite.extobjects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.*;
import com.simplicite.webapp.web.JQueryWebPage;
import com.simplicite.commons.COMPAS_NRCO.JSONToolHelper;

/**
 * External object ComBCVpriv
 */
public class ComBCVpriv extends ExternalObject {
	private static final long serialVersionUID = 1L;

	/**
	 * Display method
	 * @param params Request parameters
	 */
	@Override
	public Object display(Parameters params) {
		
		JQueryWebPage wp = new JQueryWebPage(params.getRoot(), getDisplay());
		wp.appendAjax();
		wp.appendMustache();
		wp.appendCSSInclude(HTMLTool.getResourceCSSURL(this, "STYLES"));
		wp.appendJSInclude(HTMLTool.getResourceJSURL(this, "SCRIPT"));
		wp.append(HTMLTool.getResourceHTMLContent(this, "HTML"));
		
		setDecoration(false);
		setPublic(true);
	
		try{
			
			ObjectDB ac = getGrant().getTmpObject("ComACVPriv");
			ac.resetValues();
			ac.resetFilters();

			AppLog.info(getClass(), "display", "===== EXT OBJ FOUND AC WITH ID "+params.getParameter("id")+" ========", getGrant());

			
			ac.setFieldFilter("comACid", params.getParameter("id"));
			List<String[]> rows = ac.search();
			if(!Tool.isEmpty(rows)){
				for(String[] row: rows){
					
					AppLog.info(getClass(), "display", "===== EXT OBJ FOUND AC WITH ID "+params.getParameter("id")+" ========", getGrant());
					
					ac.setValues(row, false);
					
					TreeView treeview = getGrant().getTreeView("ComACVPR");
			
					String reponse = JSONToolHelper.getObjectAsJsonTreeview(getGrant().getTmpObject("ComACVPriv"),  params.getParameter("row_id"), treeview);
					
					JSONObject jsonRep = new JSONObject(reponse);
					
					JSONArray arrayCommandes = new JSONArray();
					ObjectDB com = getGrant().getTmpObject("ComCommandeJournaux");
					
					com.resetFilters();
					com.setFieldFilter("comComACid", params.getParameter("id"));
					
					AppLog.info(getClass(), "comComACid", "param : "+params.getParameter("id"), getGrant());

					
					List<String[]> commandes = com.search();
					Integer quantite = 0;
					for(String[] rowC : commandes){
						com.setValues(rowC, false);
						JSONObject jCs = new JSONObject();
						String date = com.getFieldValue("comComDateParution");
						String[] splitDate = date.split("-");
						String formatedDate = splitDate[2] + "." + splitDate[1] + "." + splitDate[0];
						
						jCs.put("dateParution", formatedDate);
						jCs.put("edition", com.getFieldValue("comComEdition"));
						
						quantite+=Integer.valueOf(Tool.isEmpty(com.getFieldValue("comComQuantite")) ? "0" : com.getFieldValue("comComQuantite"));
						
						arrayCommandes.put(jCs);
					}
					
					jsonRep.put("commandes", arrayCommandes);
					
					com.resetValues();
					com.setValues(commandes.get(0));
					JSONObject jCom = new JSONObject();
					
					jCom.put("qte", quantite);
					jCom.put("fichier", com.getFieldDisplayValue("comComFichier"));

					AppLog.info(getClass(), "method","comComSiFichier "+ com.getFieldDisplayValue("comComSiFichier"), getGrant());
				
				
					jCom.put("sup", com.getFieldDisplayValue("comComsupplement"));
					
					JSONObject vprivItem = jsonRep.getJSONObject("item");
					
					if("EPO".equals(com.getFieldValue("comComTypeLivraison"))){
						jCom.put("type", "Abonnés");
						
						String l1 = "Sans fichier";
						
						if(  com.getFieldDisplayValue("comComFichier") != null && !  com.getFieldDisplayValue("comComFichier").equals ("") ){
							l1 = com.getFieldDisplayValue("comComFichier");
							
						}
						
						if( com.getFieldDisplayValue("comComSiFichier") != null && !com.getFieldDisplayValue("comComSiFichier").equals("")  ){
							l1 += " - "+com.getFieldDisplayValue("comComSiFichier");
						}
						
						jCom.put("line1", l1 );
			
						
						jCom.put("line2", "1".equals(com.getFieldValue("comComEncart")) ? "Encart" : "Sans encart");
						jCom.put("adresse", vprivItem.opt("comACAdresseId__comAdrLigne1")+
						" "+vprivItem.opt("comACAdresseId__comAdrCP")+
						" "+vprivItem.opt("comACAdresseId__comAdrVille"));
						jCom.put("poste", "Poste");
						
					}
					else{
						jCom.put("type", "Paquets");
						jCom.put("line1", "1".equals(com.getFieldValue("comComEncart")) ? "Encart" : "");
						jCom.put("line2", "SFP".equals(com.getFieldValue("comComTypeLivraison")) ? "Sous film" : "");
						jCom.put("line3", "1".equals(com.getFieldValue("comComTvMag")) ? "Avec TVM" : "");
						jCom.put("line4", "1".equals(com.getFieldValue("comComTvMag")) ? "Avec PA" : "");
						jCom.put("adresse", com.getFieldDisplayValue("comComAdresse"));
						jCom.put("poste", "Routeur");
					}
					
					JSONObject facturation = new JSONObject();
					
					AppLog.info(getClass(), "method", vprivItem.optString("comPNRadrAdresse"), getGrant());
					
					if(!"".equals(vprivItem.optString("comPNRadrAdresse"))){
						facturation.put("nom", vprivItem.optString("comPNRadrNomPrenom"));
						facturation.put("organisme", vprivItem.optString("comPNRadrOrga"));
						facturation.put("siret", vprivItem.optString("comPNRadrSiret"));
						facturation.put("adresse", vprivItem.optString("comPNRadrAdresse"));
						facturation.put("complement", vprivItem.optString("comPNRadrComp"));
						facturation.put("cp", vprivItem.optString("comPNRadrCPville"));
						facturation.put("tel", vprivItem.optString("comPNRadrtel"));
						facturation.put("email", vprivItem.optString("comACContactId__comContactEmail"));
					}
					else{
						facturation.put("nom", vprivItem.optString("comACContactId__comContactNom", " ") + " " + vprivItem.optString("comACContactId__comContactPrenom"));
						facturation.put("organisme", vprivItem.optString("comACClientId__comCliRaisonSociale"));
						facturation.put("siret", vprivItem.optString("comACClientId__comCliSIRET"));
						facturation.put("adresse", vprivItem.optString("comACAdresseId__comAdrLigne1", " "));
						//facturation.put("complement", vprivItem.opt("comPNRadrComp"));
						facturation.put("cp", vprivItem.optString("comACAdresseId__comAdrCP"," ") + " " + vprivItem.optString("comACAdresseId__comAdrVille", " "));
						facturation.put("tel", vprivItem.optString("comACContactId__comContactTel", " "));
						facturation.put("email", vprivItem.optString("comACContactId__comContactEmail", " "));
					}
					
					facturation.put("dateFacturation", Tool.isEmpty(ac.getFieldValue("comPNRdateFacturation")) ? "Fin de mois" : ac.getFieldDisplayValue("comPNRdateFacturation"));
					AppLog.info(getClass(), "getFieldDisplayValue of comPNRenvoiFacture", ac.getFieldDisplayValue("comPNRenvoiFacture"), getGrant());
					facturation.put("envoi", ac.getFieldDisplayValue("comPNRenvoiFacture"));
					facturation.put("delai", Tool.isEmpty(ac.getFieldValue("comPNRdelaiReglement")) ? "A réception de facture" : ac.getFieldDisplayValue("comPNRdelaiReglement"));
					
					if(!Tool.isEmpty(ac.getFieldValue("comPBdateOpe"))){
						String[] splitDate = ac.getFieldValue("comPBdateOpe").split("-");
						String formatedDate = splitDate[2] + "." + splitDate[1] + "." + splitDate[0];
						jsonRep.put("dateOpe", formatedDate);
					}
					
					
					jsonRep.put("facturation", facturation);
					jCom.put("txtEncart", com.getFieldValue("comComsiEncart"));
					jsonRep.put("commandeData", jCom);
					jsonRep.put("logo", HTMLTool.getResourceImageURL(this, "LOGO"));
					
					Double totalPresta = vprivItem.optDouble("comPNRfraisLocAdr", 0) +vprivItem.optDouble("comPNRfraisTraitFich", 0) +vprivItem.optDouble("comPNRfraisDistribStreet", 0) +
													vprivItem.optDouble("comPNRfraisLocCar", 0) +
													vprivItem.optDouble("comPNRfraisAnmin", 0) +
												vprivItem.optDouble("comPNRfraisAutre", 0);
												
					JSONObject frais = new JSONObject();
					
					if((vprivItem.optDouble("comPNRfraisLocAdr"))==Double.NaN){
						AppLog.info(getClass(), "method", "Cool NAN bro", getGrant());
					}
					else{
						AppLog.info(getClass(), "method", String.format("%.2f",vprivItem.optDouble("comPNRfraisLocAdr", 0)) + " Not Cool NAN", getGrant());
					}
					
					
					
					frais.put("fraisLocAdr", (String.format("%.2f",vprivItem.optDouble("comPNRfraisLocAdr", 0))).replace(".", ","));
					frais.put("comPNRfraisTraitFich", (String.format("%.2f",vprivItem.optDouble("comPNRfraisTraitFich", 0))).replace(".", ","));
					frais.put("comPNRfraisDistribStreet", (String.format("%.2f",vprivItem.optDouble("comPNRfraisDistribStreet", 0))).replace(".", ","));
					frais.put("comPNRfraisLocCar", (String.format("%.2f",vprivItem.optDouble("comPNRfraisLocCar", 0))).replace(".", ","));
					frais.put("comPNRfraisAnmin", (String.format("%.2f",vprivItem.optDouble("comPNRfraisAnmin", 0))).replace(".", ","));
					frais.put("comPNRfraisAutre", (String.format("%.2f",vprivItem.optDouble("comPNRfraisAutre", 0))).replace(".", ","));
					
					//(String.format("%.2f",vprivItem.optDouble("comPNRfraisAnmin"))).repalce(".", ","))
					
					frais.put("comPNRtotalRemise", (String.format("%.2f",vprivItem.optDouble("comPNRtotalRemise", 0))).replace(".", ","));
					frais.put("comPNRprixTtc", (String.format("%.2f", vprivItem.optDouble("comPNRprixTtc", 0))).replace(".", ","));
					frais.put("comPNRtauxRemise", (String.valueOf(vprivItem.optInt("comPNRtauxRemise", 0))).replace(".", ","));
					
					jsonRep.put("frais", frais);
					
					Double totalJournaux = vprivItem.optDouble("comPNRtotalRemise");
					
					Double total = (totalPresta * 1.20) + totalJournaux;
					
					double totalPrestaArrondi = (double) Math.round(totalPresta * 100) / 100;
					double totalArrondi = (double) Math.round(total * 100) / 100;
					
					
					jsonRep.put("totalHTprestaCom", String.format("%.2f",totalPresta).replace(".",","));
					
					JSONObject recap = new JSONObject();
					recap.put("tP", String.format("%.2f",totalPrestaArrondi * 1.20).replace(".",","));
					recap.put("tJ", String.format("%.2f",totalJournaux).replace(".",","));
					recap.put("total", String.format("%.2f",totalArrondi).replace(".",","));
					
					jsonRep.put("recapTTC", recap);

					AppLog.info(getClass(), "display", jsonRep.toString(), getGrant());
					wp.appendJS("var data = " + jsonRep + ";");
				}
			}
			
			wp.setReady("ComBCVpriv.render('"+wp.getRoot()+"');");
			return wp.toString();
			
			//return sendJavaScript("ComBCVpriv.render('"+jsonRep+"');");
		} catch (Exception e) {
			AppLog.error(getClass(), "display", null, e, getGrant());
			return e.getMessage();
		}
	}
}