package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.COMPAS_NRCO.ComSAPHelper;
import com.simplicite.commons.COMPAS_NRCO.ComMailTool;
import com.simplicite.commons.COMPAS_NRCO.JSONToolHelper;
import org.json.*;
import java.io.*;

/**
 * Business object ComACPNR
 */
public class ComACPNR extends ComActionCommerciale {
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		super.postLoad();
		//setSearchSpec(getDefaultSearchSpec() + " and ac_type='PNR'");

		FieldArea area8 = getFieldArea("ComActionCommerciale-8");
		FieldArea area9 = getFieldArea("ComActionCommerciale-9");
		FieldArea area10 = getFieldArea("ComActionCommerciale-10");
		FieldArea area11 = getFieldArea("ComActionCommerciale-11");
		FieldArea area12 = getFieldArea("ComActionCommerciale-12");
		FieldArea area13 = getFieldArea("ComActionCommerciale-13");
		FieldArea area25 = getFieldArea("ComActionCommerciale-25");

		List<FieldArea> fieldsToHide = Arrays.asList(area25, area8, area9, area10, area11, area12, area13);
		hideFields(fieldsToHide);

	}
	
	@Override
	public void initList(ObjectDB parent) {
		
		super.initList(parent);
		if(isChildOf("ComClient") || isHomeInstance()){
			setSearchSpec(getDefaultSearchSpec());
		}
		else{
			setSearchSpec(getDefaultSearchSpec() + " and ac_type='PNR'");
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
		setFieldValue("comACtype", "PNR");
		super.initCreate();
	}

	@Override
	public String postCreate() {
		super.postCreate();
		Grant g = getGrant();
		String acId = getFieldValue("comACid");
		if ("PNR".equals(getFieldValue("comACtype"))) {
			for (int i = 1; i < 7; i++) {
				try {
					ObjectDB dotation = g.getTmpObject("ComDotation");
					dotation.setFieldValue("comDotLotNum", i);
					dotation.setFieldValue("comDotACid", acId);
					new BusinessObjectTool(dotation).create();
				} catch (Exception e) {
					AppLog.error(getClass(), "postCreate", "Error creating ComDotation", e, g);
				}
			}
		}

		return null;
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
		area15.setVisible(true);
		area16.setVisible(true);
		area17.setVisible(true);
		area18.setVisible(true);
		area20.setVisible(true);
		area25.setVisible(false);
		area8.setVisible(false);
		area9.setVisible(false);
		area10.setVisible(false);
		area11.setVisible(false);
		area12.setVisible(false);
		area13.setVisible(false);

		super.initUpdate();
	}

	public List<String> remonterPNRSap() {
		
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
		ObjectField taux = getField("comPNRtauxRemise");
		ObjectField date = getField("comPNRdateFacturation");
		ObjectField delai = getField("comPNRdelaiReglement");
		List<ObjectField> fieldsToCheck = Arrays.asList(id, taux, date, delai);
		emptyFields.addAll(sapHelper.checkFields(fieldsToCheck));

		data.put("typeAC", getFieldDisplayValue("comACtype"));
		data.put("id", id.getValue());
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
			//sapHelper.vprivToSap(data);
			TreeView treeview = g.getTreeView("ComACPNR");
			try{
				String reponse = JSONToolHelper.getObjectAsJsonTreeview(this, getRowId(), treeview);
				JSONObject jsonRep = new JSONObject(reponse);
				JSONObject jsonItem = jsonRep.getJSONObject("item");
				sapHelper.pnrToSap(jsonRep);
				
				ObjectDB client = g.getTmpObject("ComClient");
				client.resetFilters();
				client.setFieldFilter("comCliID", getFieldValue("comACClientId"));
				List<String[]> cliRows = client.search();
				if (cliRows.isEmpty()) {
					msgs.add(Message.formatError("", "Il n'y a pas de client associée", ""));
					return msgs;
				}
				String cliRS="";
				JSONArray clientsArray = new JSONArray();
				for (String[] cliRow : cliRows) {
					client.setValues(cliRow);
					cliRS=client.getFieldValue("comCliRaisonSociale");

					if( client.getFieldValue("comCliType").equals("DEP") ){
					// EMAIL 3
					
						String[] dateSplit = this.getFieldValue("comPNRdateDebut").split("-");
						
						String title = "Info nouveau pack NR "+dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0]+ " CP : " + this.getVilleCp();
						
						String body = "Bonjour,<br />Création d'un nouveau pack. <br />Pensez à valider les données dans la ZVT90. <br />Commentaire :<br />"+this.getFieldValue("comACcommentaire");
	
						(new ComMailTool()).sendEmailJsreport(title,body,"adv", g );
						
					}else if( client.getFieldValue("comCliType").equals("DIF") ){
					// EMAIL 9
					
					
						
						String[] dateSplit = this.getFieldValue("comPNRdateDebut").split("-");
						
						String title = "Info nouveau pack NR "+ client.getFieldValue("comCliRaisonSociale") +" "+dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
						
						String body = "Bonjour, Ci dessous les informations de création d'un nouveau pack.,<br />"+
							this.formatCommandeJournaux()+
							"<br /> Adresse du diffuseur : <br />"+
							client.getFieldValue("comCliRaisonSociale")+"<br />"+
							jsonItem.getString("comACAdresseId__comAdrLigne1")+"<br />"+
							jsonItem.getString("comACAdresseId__comAdrCP")+ " " + jsonItem.getString("comACAdresseId__comAdrVille") +  "<br />"+
							"<br /> Commentaire :<br />"+
							this.getFieldValue("comACcommentaire");
							
						(new ComMailTool()).sendEmailJsreport(title,body,this.getMailDepositaire(client), g );

					}
					
				}
				
				// MAIL 4
				
				AppLog.info(getClass(), "Mail4 ",this.getFieldValue("comPNRintitule"),  getGrant());
				
				String title = "Info nouveau pack NR "+formatDateSap(this.getFieldValue("comPNRdateDebut"));
				
				String body = "Bonjour,<br />Ci joint le(s) document(s) pour création d'un nouveau pack.<br /><br />"+
				
					cliRS+"<br />"+ 
					jsonItem.getString("comACAdresseId__comAdrLigne1")+"<br />"+ 
					jsonItem.getString("comACAdresseId__comAdrCP")+" "+jsonItem.getString("comACAdresseId__comAdrVille")+"<br />"+ 
					
				
				
					"Informations : "+this.getFieldValue("comPNRintitule")+"<br />"+ 
					"Début de l'opération : "+ formatDateSap(this.getFieldValue("comPNRdateDebut"))+ " à Date de fin de l'opération : "+ formatDateSap(this.getFieldValue("comPNRdateFin"))+"<br />"+ 
					"Bon à découper : Edition : "+ this.getFieldValue("comPNRbonEdition")+"<br />"+ 
					"Bon à découper : Rubrique : "+ this.getFieldValue("comPNRbonRubrique")+"<br />"+ 
					"Bon à découper : Date : "+ formatDateSap(this.getFieldValue("comPNRbonDate"))+"<br />"+ 
					"Date du tirage au sort : "+ formatDateSap(this.getFieldValue("comPNRdateTirage"))+"<br />"+ 
					"Date de la remise des prix : "+ formatDateSap(this.getFieldValue("comPNRdateRemise"))+"<br />"+ 
					"Date de l'annonce rédactionnelle : "+ formatDateSap(this.getFieldValue("comPNRdateAnnonce"))+"<br /><br />"+ 
					
					"Matériel :<br />"+
					"Nombre d'affichettes opération : "+ this.getFieldValue("comPNRnbAffOpe")+"<br />"+ 
					"Nombre de flyers bulletin : "+ this.getFieldValue("comPNRnbFlyersBulletin")+"<br />"+ 
					"Nombre d'urnes : "+ this.getFieldValue("comPNRnbUrnes")+"<br />"+ 
					"Nombre d'affichettes \"Ce journal vous est offert\" : "+ this.getFieldValue("comPNRnbAffOffert")+"<br />"+ 
					"Nombre de flyers \"Ce journal vous est offert\" : "+ this.getFieldValue("comPNRnbFlyOffert")+"<br />"+ 
					"Commentaires : "+ this.getFieldValue("comPNRcommentaire")+"<br />"+ 
					
					"<br />Dotations : <br/>"+this.formatDotations()+
					
					"<br />Commande de journaux : "+this.formatCommandeJournaux();


					AppLog.info(getClass(), "Mail4 ",this.getFieldValue("comPNRintitule"),  getGrant());

				(new ComMailTool()).sendEmailJsreport(title,body,"promotion", g );


				
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
	
	private String formatCommandeJournaux(){
		String res = "";
		ObjectDB commandeJournaux = getGrant().getTmpObject("ComCommandeJournaux");
		commandeJournaux.resetFilters();
		commandeJournaux.setFieldFilter("comACid", getRowId());

		List<String[]> rows = commandeJournaux.search();
		
		int index = 1;
		
		for (String[] row : rows) {
			commandeJournaux.setValues(row);
			
			res += "<br/> Commande "+index+"<br/>";
			res += "Date parution : "+   formatDateSap(commandeJournaux.getFieldValue("comComDateParution")) +"<br/>";
			res += "Edition : "+  commandeJournaux.getFieldValue("comComEdition") +"<br/>";
			res += "Quantité : "+  commandeJournaux.getFieldValue("comComQuantite") +"<br/>";
			res += "Type de livraison : "+  commandeJournaux.getFieldDisplayValue("comComTypeLivraison") +"<br/>";
			res += "Encart : "+  (commandeJournaux.getFieldValue("comComEncart").equals("1") ? "Oui" : "Non") +"<br/>";
			if( commandeJournaux.getFieldValue("comComsiEncart") != null && !commandeJournaux.getFieldValue("comComsiEncart").equals("")  ){
				res += "Texte encart : "+  commandeJournaux.getFieldValue("comComsiEncart")+"<br/>";
			}
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
	
	private String formatDotations(){
	    String res = "";
	    ObjectDB doation = getGrant().getTmpObject("ComDotation");
	    doation.resetFilters();
	    doation.setFieldFilter("comDotACid", getRowId());
	    List<String[]> rows = doation.search();
	    
	    
	    for (String[] row : rows) {
	        doation.setValues(row);
	        
	        res += "Lot n° : "+   doation.getFieldValue("comDotLotNum") +"<br/>";
	        res += "Nom du lot :" +  doation.getFieldValue("comDotLotNom") +"<br/>";
	        res += "Prix public (TTC) : "+  doation.getFieldValue("comDotLotPrix") +"<br/>";
	    }
	    return res;
	}
	
	private String getMailDepositaire(ObjectDB diffuseur) throws Exception{

		Grant g = getGrant();
		
		ObjectDB comCliCli = g.getTmpObject("ComClientClient");
		comCliCli.resetFilters();
		comCliCli.setFieldFilter("comClientDepositaireId", diffuseur.getFieldValue("comCliID"));
		List<String[]> comCliCliRows = comCliCli.search();
		
		for (String[] comCliCliRow : comCliCliRows) {
		    comCliCli.setValues(comCliCliRow);
		    
			ObjectDB depositaire = g.getTmpObject("ComContact");
			depositaire.resetFilters();
			depositaire.setFieldFilter("comContactClientId", comCliCli.getFieldValue("comClientDiffuseurId.comCliID"));
			List<String[]> depositaireRows = depositaire.search();
	
			for (String[] depositaireRow : depositaireRows) {
				depositaire.setValues(depositaireRow);
				return depositaire.getFieldValue("comContactEmail");				
			}
			
			
		}
		throw new Exception("Aucun contact trouvé");
	}
	
	public String formatDateSap(String date){
		try{
			String[] dateSplit = date.split("-");
			return dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
		}catch (Exception e){
			return date;
		}
	}
	
	public Object accordPartnariat(){
		return HTMLTool.redirectStatement("/ui/ext/ComPRNAccordPartenariat?id="+getRowId());
	}
	
	
	

}