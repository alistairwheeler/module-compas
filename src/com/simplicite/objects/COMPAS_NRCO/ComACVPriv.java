package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.*;
import com.simplicite.commons.COMPAS_NRCO.ComSAPHelper;
import com.simplicite.commons.COMPAS_NRCO.JSONToolHelper;
import com.simplicite.commons.COMPAS_NRCO.ComMailTool;

/**
 * Business object ComACVPriv
 */
public class ComACVPriv extends ComActionCommerciale {
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		super.postLoad();
		
		FieldArea area8 = getFieldArea("ComActionCommerciale-8");
		FieldArea area9 = getFieldArea("ComActionCommerciale-9");
		FieldArea area10 = getFieldArea("ComActionCommerciale-10");
		FieldArea area11 = getFieldArea("ComActionCommerciale-11");
		FieldArea area12 = getFieldArea("ComActionCommerciale-12");
		FieldArea area13 = getFieldArea("ComActionCommerciale-13");
		FieldArea area15 = getFieldArea("ComActionCommerciale-15");
		FieldArea area16 = getFieldArea("ComActionCommerciale-16");
		FieldArea area17 = getFieldArea("ComActionCommerciale-17");

		List<FieldArea> fieldsToHide = Arrays.asList(area15, area16, area17, area8, area9, area10, area11, area12,
				area13);
		hideFields(fieldsToHide);

	}
	
	@Override
	public void initList(ObjectDB parent) {
		
		super.initList(parent);
		if(isChildOf("ComClient") || isHomeInstance()){
			setSearchSpec(getDefaultSearchSpec());
		}
		else{
			setSearchSpec(getDefaultSearchSpec() + " and ac_type='VPR'");
		}
	
	}

	public void hideFields(List<FieldArea> fieldAreas) {
		for (int j = 0; j < fieldAreas.size(); j++) {
			List<ObjectField> fieldOfArea = fieldAreas.get(j).getFields();
			for (int i = 0; i < fieldOfArea.size(); i++) {
				fieldOfArea.get(i).setVisibility(ObjectField.VIS_FORBIDDEN);
			}
		}
	}

	@Override
	public void initCreate() {
		setFieldValue("comACtype", "VPR");
		super.initCreate();
	}

	@Override
	public void initUpdate() {
		FieldArea area8 = getFieldArea("ComActionCommerciale-8");
		FieldArea area9 = getFieldArea("ComActionCommerciale-9");
		FieldArea area10 = getFieldArea("ComActionCommerciale-10");
		FieldArea area11 = getFieldArea("ComActionCommerciale-11");
		FieldArea area12 = getFieldArea("ComActionCommerciale-12");
		FieldArea area19 = getFieldArea("ComActionCommerciale-19");
		FieldArea area13 = getFieldArea("ComActionCommerciale-13");
		FieldArea area15 = getFieldArea("ComActionCommerciale-15");
		FieldArea area16 = getFieldArea("ComActionCommerciale-16");
		FieldArea area17 = getFieldArea("ComActionCommerciale-17");
		FieldArea area18 = getFieldArea("ComActionCommerciale-18");
		FieldArea area20 = getFieldArea("ComActionCommerciale-20");
		FieldArea area25 = getFieldArea("ComActionCommerciale-25");

		area19.setVisible(true);
		area15.setVisible(false);
		area16.setVisible(false);
		area17.setVisible(false);
		area18.setVisible(true);
		area20.setVisible(true);
		area25.setVisible(true);
		area8.setVisible(false);
		area9.setVisible(false);
		area10.setVisible(false);
		area11.setVisible(false);
		area12.setVisible(false);
		area13.setVisible(false);

		super.initUpdate();
	}

	public List<String> remonterVPrivSap() {
		
		Grant g = getGrant();
				
		
		List<String> msgs = new ArrayList<String>();
		ComSAPHelper sapHelper = new ComSAPHelper(g);

		try{
			this.verifContact();
			this.verifAddr();
		}catch(Exception e){
			msgs.add( e.getMessage() );
			return msgs;
		}


		if( getFieldValue("comACsapID") != null && ! getFieldValue("comACsapID").equals("") ){
			msgs.add("L'action commerciale a déja été envoyée à SAP");	
			return msgs;
		}


		ObjectDB commandeJournaux = g.getTmpObject("ComCommandeJournaux");
		commandeJournaux.resetFilters();
		commandeJournaux.setFieldFilter("comACid", getFieldValue("comACid"));
		List<String[]> rows = commandeJournaux.search();
		if (rows.isEmpty()) {
			msgs.add(Message.formatError("ERREUR", "Il n'y a aucune commande de journaux associée", "erreur"));
			return msgs;
		}

		// msgs.add(Message.formatInfo("INFO_CODE", "Message", "fieldName"));
		// msgs.add(Message.formatWarning("WARNING_CODE", "Message", "fieldName"));
		
		List<String> emptyFields = new ArrayList<String>();

		ObjectDB commande = g.getTmpObject("ComCommandeJournaux");
		JSONObject data = new JSONObject();
		JSONArray commandesArray = new JSONArray();

		ObjectField id = getField("comAClibelle");
		ObjectField pbrnom = getField("comPBnom");
		ObjectField taux = getField("comPNRtauxRemise");
		ObjectField date = getField("comPNRdateFacturation");
		ObjectField delai = getField("comPNRdelaiReglement");
		List<ObjectField> fieldsToCheck = Arrays.asList(id, taux, date, delai, pbrnom);
		emptyFields.addAll(sapHelper.checkFields(fieldsToCheck));
		
		String dateMail5 = "";
		
		/*data.put("id", id.getValue());
		data.put("pbrnom", pbrnom.getValue());
		data.put("taux", taux.getValue());
		data.put("date", date.getDisplayValue());
		data.put("delai", delai.getDisplayValue());*/
		for (String[] commandesRow : rows) {

			commande.setValues(commandesRow);
			JSONObject commandeObj = new JSONObject();

	

			ObjectField dateParu = commande.getField("comComDateParution");
			
			if( dateMail5.equals("") ){
				dateMail5 = commande.getFieldValue("comComDateParution");
			}
			
			ObjectField edition = commande.getField("comComEdition");
			ObjectField quantite = commande.getField("comComQuantite");
			ObjectField typeLivraison = commande.getField("comComTypeLivraison");

			List<ObjectField> fieldsComamndeToCheck = Arrays.asList(dateParu, edition, quantite, typeLivraison);
			emptyFields.addAll(sapHelper.checkFields(fieldsComamndeToCheck));

			/*commandeObj.put("date", dateParu.getValue());
			commandeObj.put("edition", edition.getDisplayValue());
			commandeObj.put("quantite", quantite.getValue());
			commandeObj.put("typeLivraison", typeLivraison.getDisplayValue());
			commandesArray.put(commandeObj);*/
		}

		//data.put("commandes", commandesArray);

		if (emptyFields.isEmpty()) {
			sapHelper.vprivToSap(data);
			TreeView treeview = g.getTreeView("ComACVPR");
			try{
				String reponse = JSONToolHelper.getObjectAsJsonTreeview(this, getRowId(), treeview);
				JSONObject jsonRep = new JSONObject(reponse);
				AppLog.info(getClass(), "method", jsonRep.toString(), getGrant());
				
				sendEmailNotif(this, getGrant(),dateMail5);
				
				
				sapHelper.vprivToSap(jsonRep);
				
				
				
			}
			catch(Exception e){
				AppLog.info(this.getClass(), "getJSON", e.getMessage(), g);
			}
			msgs.add(Message.formatSimpleInfo("Action Commerciale envoyée à SAP avec succès"));
		} else if (!emptyFields.isEmpty()) {
			msgs.add(Message.formatError("ECHEC",
					"Merci de renseigner les champs suivants: " + String.join(", ", emptyFields), "fieldName"));
		}

		return msgs;
	}
	
	public void sendEmailNotif(ObjectDB obj, Grant g, String dateMail5 ){
		
		
		String directUrl = g.getSystemParam("DIRECT_URL");
		String publicUrl = directUrl.substring(0, directUrl.length() -3 );
		String urlBc = publicUrl + "/ext/ComBCVpriv?id="+getFieldValue("comACid");
		
		// mail #5
		
		String bodyMail5 = 
			"Bonjour,<br/>"+
			"Ci joint le(s) document(s) pour création d'une nouvelle vente privilège.<br/>"+
			"Pensez à valider les données dans la ZVT90<br />"+
			"<a href="+urlBc+" target=\"_blank\">Lien du bon de commande</a><br/>"+
			"Commentaire :<br/>"+this.getFieldValue("comACcommentaire");
			
		String[] dateSplit = dateMail5.split("-");

		String titleMail5 = "Info nouvelle vente privilège " +dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0] + " CP : " + this.getVilleCp();


		ObjectDB pj = g.getTmpObject("ComPieceJointe");
		ArrayList<String> pathDoc = new ArrayList<String>();
		
		pj.resetFilters();
		pj.resetValues();
		
		pj.setFieldFilter("comPJaCId", obj.getRowId());
		
		List<String[]> rslts = pj.search();
		
		List<Mail.MailAttach> pjs = new ArrayList<Mail.MailAttach>(); 
		
		if(!Tool.isEmpty(rslts)){
			for(String[] row : rslts){
				pj.setValues(row, false);
				ObjectField f = pj.getField("comPJfichier");
				
				if(!Tool.isEmpty(f.getValue())){
					
					DocumentDB doc = f.getDocument(g);
					try{
						java.io.File docFile = doc.getFile();
						Mail.MailAttach mA = new Mail.MailAttach(docFile);
						pjs.add(mA);
					}
					catch(Exception e ){
						AppLog.info(getClass(), "sendEmailNotif", e.getMessage(), getGrant());
					}
				}
			}
		}
		
		(new ComMailTool()).sendEmailJsreport(titleMail5,bodyMail5,"adv", pjs, g );

		

	}
	
	public Object bonDeCommande(){
		AppLog.info(getClass(), "bonDeCommande", "id : "+getFieldValue("comACid"), getGrant());
		return HTMLTool.redirectStatement("/ui/ext/ComBCVpriv?id="+getFieldValue("comACid"));
	}
	
	

}