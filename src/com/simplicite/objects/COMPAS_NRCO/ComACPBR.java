package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.COMPAS_NRCO.JSONToolHelper;
import org.json.*;
import com.simplicite.commons.COMPAS_NRCO.ComSAPHelper;
import com.simplicite.commons.COMPAS_NRCO.ComMailTool;
import java.net.URL;

/**
 * Business object ComACPBR
 */
public class ComACPBR extends ComActionCommerciale {
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		super.postLoad();
		//setSearchSpec(getDefaultSearchSpec() + " and ac_type='PBR'");

		FieldArea area8 = getFieldArea("ComActionCommerciale-8");
		FieldArea area9 = getFieldArea("ComActionCommerciale-9");
		FieldArea area10 = getFieldArea("ComActionCommerciale-10");
		FieldArea area11 = getFieldArea("ComActionCommerciale-11");
		FieldArea area12 = getFieldArea("ComActionCommerciale-12");
		FieldArea area13 = getFieldArea("ComActionCommerciale-13");
		FieldArea area15 = getFieldArea("ComActionCommerciale-15");
		FieldArea area17 = getFieldArea("ComActionCommerciale-17");
		
		List<FieldArea> fieldsToHide = Arrays.asList(area8, area9, area10, area11, area12, area13, area15, area17);
		hideFields(fieldsToHide);

	}

	@Override
	public void initList(ObjectDB parent) {
		
		super.initList(parent);
		if(isChildOf("ComClient") || isHomeInstance()){
			setSearchSpec(getDefaultSearchSpec());
		}
		else{
			setSearchSpec(getDefaultSearchSpec() + " and ac_type='PBR'");
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
		setFieldValue("comACtype", "PBR");
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
		area16.setVisible(true);
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

	public List<String> remonterPBRSap() {
		
		Grant g = getGrant();
		
		List<String> msgs = new ArrayList<String>();
		
		try{
			this.verifContact();
			this.verifAddr();
		}catch(Exception e){
			msgs.add( e.getMessage() );
			return msgs;
		}

		ComSAPHelper sapHelper = new ComSAPHelper(g);

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
		List<ObjectField> fieldsToCheck = Arrays.asList(id, taux, date, delai);
		emptyFields.addAll(sapHelper.checkFields(fieldsToCheck));

		data.put("typeAC", getFieldDisplayValue("comACtype"));
		data.put("id", id.getValue());
		data.put("pbrnom", pbrnom.getValue());
		data.put("taux", taux.getValue());
		data.put("date", date.getDisplayValue());
		data.put("delai", delai.getDisplayValue());
		for (String[] commandesRow : rows) {

			commande.setValues(commandesRow);
			JSONObject commandeObj = new JSONObject();

			ObjectField dateParu = commande.getField("comComDateParution");
			ObjectField edition = commande.getField("comComEdition");
			ObjectField quantite = commande.getField("comComQuantite");
			ObjectField typeLivraison = commande.getField("comComTypeLivraison");

			List<ObjectField> fieldsComamndeToCheck = Arrays.asList(dateParu, edition, quantite, typeLivraison);
			emptyFields.addAll(sapHelper.checkFields(fieldsComamndeToCheck));

			commandeObj.put("date", dateParu.getValue());
			commandeObj.put("edition", edition.getDisplayValue());
			commandeObj.put("quantite", quantite.getValue());
			commandeObj.put("typeLivraison", typeLivraison.getDisplayValue());
			commandesArray.put(commandeObj);
		}

		data.put("commandes", commandesArray);

		if (emptyFields.isEmpty()) {
			sapHelper.vprivToSap(data);
			TreeView treeview = g.getTreeView("ComACPBR");
			try{
				String reponse = JSONToolHelper.getObjectAsJsonTreeview(this, getRowId(), treeview);
				JSONObject jsonRep = new JSONObject(reponse);
				sapHelper.pbrToSap(jsonRep);
				
				sendEmailNotif(this, g);
				
			}
			catch(Exception e){
				//AppLog.info(this.getClass(), "getJSON", e.getMessage(), g);
			}
			msgs.add(Message.formatSimpleInfo("Action Commerciale envoyée à SAP avec succès"));
		} else if (!emptyFields.isEmpty()) {
			msgs.add(Message.formatError("ECHEC",
					"Merci de renseigner les champs suivants: " + String.join(", ", emptyFields), "fieldName"));
		}

		return msgs;
	}
	
	
	public void sendEmailNotif(ObjectDB obj, Grant g){
		
		
	

		
		String id = obj.getFieldValue("comACkey");
		String directUrl = g.getSystemParam("DIRECT_URL");
		String publicUrl = directUrl.substring(0, directUrl.length() -3 );
		String urlBc = publicUrl + "/ext/ComBCPbr?id="+getFieldValue("comACid");
	
		// mail #6
		
		String bodyMail6 = 
			"Bonjour,<br/>"+
			"Ci joint le(s) document(s) pour création d'un nouveau pack brocante.<br/>"+
			"Pensez à valider les données dans la ZVT90<br />"+
			"<a href="+urlBc+" target=\"_blank\">Lien du bon de commande</a><br/>"+
			"Commentaire :<br/>"+obj.getFieldValue("comACcommentaire");



		String[] dateSplit = this.getFieldValue("comPBdateOpe").split("-");

		String titleMail6 = "Info nouveau pack brocante " +dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0]+ " CP : " + this.getVilleCp();
		


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
		
		(new ComMailTool()).sendEmailJsreport(titleMail6,bodyMail6,"adv", pjs, g );



		// mail  #7
		
		String bodyMail7 = 
			"Bonjour,<br/>Ci-dessous les informations pour création d'un nouveau pack brocante.<br/>"+
			"<br/>"+
			"<br/>"+
			"Informations "+getFieldValue("comPBnom")+" <br/>"+
			"Date de l'opération : "+formatDateSap(getFieldValue("comPBdateOpe"))+" <br/>"+
			"<br/>"+
			"<br/>"+
			"Matériel : <br/>"+
			"Nombre d'affichettes opération : "+getFieldValue("comPNRnbAffOpe")+" <br/>"+
			"Nombre de flyers bulletin : "+getFieldValue("comPNRnbFlyersBulletin")+" <br/>"+
			"Nombre d'urnes : "+getFieldValue("comPNRnbUrnes")+" <br/>"+
			"Nombre d'affichettes \"Ce journal vous est offert\" : "+getFieldValue("comPNRnbAffOffert")+" <br/>"+
			"Nombre de flyers \"Ce journal vous est offert\" : "+getFieldValue("comPNRnbFlyOffert")+" <br/>"+
			"Commentaires : "+getFieldValue("comPNRcommentaire")+" <br/>"+
			"<br />Commande de journaux : "+this.formatCommandeJournaux();;

		String titleMail7 = "Info nouveau pack brocante " +dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
		


		
		(new ComMailTool()).sendEmailJsreport(titleMail7,bodyMail7,"promotion", g );



		
	}
	
		
	private String formatCommandeJournaux(){
		String res = "";
		ObjectDB commandeJournaux = getGrant().getTmpObject("ComCommandeJournaux");
		commandeJournaux.resetFilters();
		commandeJournaux.setFieldFilter("comACid", getRowId());
			    AppLog.info(getClass(), "formatDotations","here "+getRowId(),  getGrant());
		List<String[]> rows = commandeJournaux.search();
		
		int index = 1;
		
		for (String[] row : rows) {
			commandeJournaux.setValues(row);

			res += "Date parution : "+   formatDateSap(commandeJournaux.getFieldValue("comComDateParution")) +"<br/>";
			res += "Edition : "+  commandeJournaux.getFieldValue("comComEdition") +"<br/>";
			res += "Quantité : "+  commandeJournaux.getFieldValue("comComQuantite") +"<br/>";
			res += "Type de livraison : "+  commandeJournaux.getFieldDisplayValue("comComTypeLivraison") +"<br/>";
			res += "Encart : "+  commandeJournaux.getFieldValue("comComEncart") +"<br/>";
			res += "TV mag : "+  commandeJournaux.getFieldValue("comComTvMag") +"<br/>";
			res += "Supplément : "+  commandeJournaux.getFieldValue("comComsupplement") +"<br/>";
			if( commandeJournaux.getFieldValue("comComRoutage") != null && !commandeJournaux.getFieldValue("comComRoutage").equals("")  ){
				res += "Routage : "+  commandeJournaux.getFieldValue("comComRoutage") +"<br/>";
			}
			res += "Fichier adresse : "+  commandeJournaux.getFieldDisplayValue("comComFichier") +"<br/>";
			res += "Délai de livraison : "+  commandeJournaux.getFieldDisplayValue("comComDelaiLivraison") +"<br/>";
			if( commandeJournaux.getFieldValue("comComAdresse") != null && !commandeJournaux.getFieldValue("comComAdresse").equals("")  ){
				res += "Lieu de livraison : "+  commandeJournaux.getFieldValue("comComAdresse") +"<br/>";
			}
			res += "Observations : "+  commandeJournaux.getFieldValue("comComObs") +"<br/>";
						
						
			index ++;
		}
		return res;
	}
	
	public String formatDateSap(String date){
		try{
			String[] dateSplit = date.split("-");
			return dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
		}catch (Exception e){
			return date;
		}
	}
	
	public Object bonDeCommande(){
		AppLog.info(getClass(), "bonDeCommande", "id : "+getFieldValue("comACid"), getGrant());
		return HTMLTool.redirectStatement("/ui/ext/ComBCPbr?id="+getFieldValue("comACid"));
	}
	

}