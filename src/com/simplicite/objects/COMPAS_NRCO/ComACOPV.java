package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.commons.COMPAS_NRCO.ComSAPHelper;
import com.simplicite.commons.COMPAS_NRCO.JSONToolHelper;
import com.simplicite.commons.COMPAS_NRCO.ComMailTool;
import org.json.*;
import java.io.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComACOPV
 */
public class ComACOPV extends ComActionCommerciale {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		
		super.postLoad();
		
		//setSearchSpec(getDefaultSearchSpec() + " and ac_type='OPV'");
	
	
		AppLog.info(getClass(), "postLoadACOPV", getInstanceName(), getGrant());
		
		FieldArea area15 = getFieldArea("ComActionCommerciale-15");
		FieldArea area16 = getFieldArea("ComActionCommerciale-16");
		FieldArea area17 = getFieldArea("ComActionCommerciale-17");
		FieldArea area18 = getFieldArea("ComActionCommerciale-18");
		FieldArea area20 = getFieldArea("ComActionCommerciale-20");
		FieldArea area21 = getFieldArea("ComActionCommerciale-21");
		FieldArea area22 = getFieldArea("ComActionCommerciale-22");
		FieldArea area23 = getFieldArea("ComActionCommerciale-23");
		FieldArea area24 = getFieldArea("ComActionCommerciale-24");
		FieldArea area25 = getFieldArea("ComActionCommerciale-25");

		List<FieldArea> fieldsToHide = Arrays.asList(area15, area16, area17, area18, area20, area21, area22, area23,
				area24, area25);
		hideFields(fieldsToHide);

	
	}

	@Override
	public void initList(ObjectDB parent) {
		
		super.initList(parent);
		if(isChildOf("ComClient") || isHomeInstance()){
			setSearchSpec(getDefaultSearchSpec());
		}
		else{
			setSearchSpec(getDefaultSearchSpec() + " and ac_type='OPV'");
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
		setFieldValue("comACtype", "OPV");
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

		area8.setVisible(true);
		area9.setVisible(true);
		area10.setVisible(true);
		area11.setVisible(true);
		area12.setVisible(true);
		area13.setVisible(true);
		area19.setVisible(true);
		area15.setVisible(false);
		area16.setVisible(false);
		area17.setVisible(false);
		area18.setVisible(false);
		area20.setVisible(false);
		area25.setVisible(false);

		super.initUpdate();
	}

	public String genererJoursSemaine() {

		Grant g = getGrant();

		List<String> jFermes = Arrays.asList(getFieldValue("comPVjoursFermeture").split(";"));

		List<String> joursDeLaSemaine = new ArrayList();
		joursDeLaSemaine.add("Lundi");
		joursDeLaSemaine.add("Mardi");
		joursDeLaSemaine.add("Mercredi");
		joursDeLaSemaine.add("Jeudi");
		joursDeLaSemaine.add("Vendredi");
		joursDeLaSemaine.add("Samedi");
		joursDeLaSemaine.add("Dimanche");

		String acID = getFieldValue("comACid");

		ObjectDB jours = g.getTmpObject("ComJourSemaine");
		jours.setFieldFilter("comJSACid", acID);
		List<String[]> rslts = jours.search();
		if (rslts.size() > 0) {
			AppLog.info(getClass(), "genererJoursSemaine", "========== ALREADY GENER ==========", g);
			return Message.formatError("ERREUR",
					"Vous avez déjà généré les jours pour ce point de vente, veuillez modifier les horaires directement dans la liste",
					"comPVheureOuvertureDef");
		}
		if (!Tool.isEmpty(getFieldValue("comPVheureOuvertureDef"))
				&& !Tool.isEmpty(getFieldValue("comPVheureFermetureDef"))) {
			if ("1".equals(getFieldValue("comPVfermetureMidi"))
					&& (Tool.isEmpty(getFieldValue("comPVheureFermetureDefAM"))
							|| Tool.isEmpty(getFieldValue("comPVheureOuvertureDefPM")))) {
				return Message.formatError("ERREUR", "Veuillez renseigner les horaires d'ouverture par défaut",
						"comPVheureOuvertureDef");
			} else {
				creerObjetJours(g, acID, joursDeLaSemaine, jFermes);
				return Message.formatInfo("OK", "Les horaires d'ouvertures on été pré-renseignées",
						"comPVheureOuvertureDef");
			}
		} else {
			return Message.formatError("ERREUR", "Veuillez renseigner les horaires d'ouverture par défaut",
					"comPVheureOuvertureDef");
		}

	}

	public void creerObjetJours(Grant g, String id, List<String> joursOuverts, List<String> joursFermes) {
		for (String jourOuvert : joursOuverts) {

			ObjectDB jSobj = g.getTmpObject("ComJourSemaine");
			jSobj.setFieldValue("comJSACid", id);
			jSobj.setFieldValue("comJSnom", jourOuvert);
			if (joursFermes.contains(jourOuvert)) {
				jSobj.setFieldValue("comJSouvert", false);
				jSobj.setFieldValue("comJSheureOuverture", null);
				if (!Tool.isEmpty(getFieldValue("comPVheureOuvertureDefPM"))
						&& !Tool.isEmpty(getFieldValue("comPVheureFermetureDefAM"))) {
					jSobj.setFieldValue("comJSheureFermetureMidi", null);
					jSobj.setFieldValue("comJSheureOuvertureMidi", null);
				}
				jSobj.setFieldValue("comJSheureFermeture", null);
			}

			else {
				jSobj.setFieldValue("comJSouvert", true);
				jSobj.setFieldValue("comJSheureOuverture", getFieldValue("comPVheureOuvertureDef"));
				if (!Tool.isEmpty(getFieldValue("comPVheureOuvertureDefPM"))
						&& !Tool.isEmpty(getFieldValue("comPVheureFermetureDefAM"))) {
					jSobj.setFieldValue("comJSheureFermetureMidi", getFieldValue("comPVheureFermetureDefAM"));
					jSobj.setFieldValue("comJSheureOuvertureMidi", getFieldValue("comPVheureOuvertureDefPM"));
				}
				jSobj.setFieldValue("comJSheureFermeture", getFieldValue("comPVheureFermetureDef"));
			}
			jSobj.create();
		}
	}

	public List<String> remonterOPVSap() {
		
		Grant g = getGrant();
		AppLog.info(getClass(), "remontesap", g.getFirstName(), getGrant());	
		AppLog.info(getClass(), "remonterOPVSap", getFieldValue("comACsapID"), g);
		List<String> msgs = new ArrayList<String>();
		
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
		
		ComSAPHelper sapHelper = new ComSAPHelper(g);
		List<String> emptyFields = new ArrayList<String>();
		JSONObject data = new JSONObject();

		/*ObjectField affichette = getField("comPVaffichette");
		ObjectField iban = getField("comPVfactIban");
		ObjectField nr = getField("comPVnr");
		ObjectField cp = getField("comPVcp");
		ObjectField ps = getField("comPVps");
		ObjectField pd = getField("comPVportageDim");
		ObjectField mal = getField("comMAlibelle");
		ObjectField pvtypo = getField("comPVtypologie");
		ObjectField pvtd = getField("comPVtypeDepositaire");
		ObjectField pvempl = getField("comPVemplacement");
		ObjectField pvsur = getField("comPVsurface");
		ObjectField pvfreq = getField("comPVfreq");
		ObjectField pvespdiff = getField("comPVespaceDif");
		ObjectField pvjf = getField("comPVjoursFermeture");
		ObjectField pvhm = getField("comPVheureMax");*/
		ObjectField pvtp = getField("comPVtypePaiement");
		ObjectField pvfff = getField("comPVfactFreqFact");
		//ObjectField pvfcm = getField("comPVfactCommMag");

		/*List<ObjectField> fieldsToSend = Arrays.asList(affichette, iban, nr, cp, ps, pd, mal, pvtypo, pvtd, pvempl,
				pvsur, pvfreq, pvespdiff, pvjf, pvhm, pvtp, pvfff, pvfcm);*/
				
		List<ObjectField> fieldsToSend = Arrays.asList(pvtp, pvfff);
				
		emptyFields.addAll(sapHelper.checkFields(fieldsToSend));
		// send return ?
		if (!emptyFields.isEmpty()) {
			msgs.add(Message.formatError("ERROR_CODE",
					"Merci de renseigner les champs suivants: " + String.join(", ", emptyFields), "fieldName"));
		}

		data.put("typeAC", getFieldDisplayValue("comACtype"));
		/*data.put("affichette", affichette.getValue());
		data.put("iban", iban.getValue());
		data.put("nr", nr.getBoolean());
		data.put("cp", cp.getBoolean());
		data.put("ps", ps.getBoolean());
		data.put("pd", pd.getBoolean());
		data.put("mal", mal.getDisplayValue());
		data.put("pvtypo", pvtypo.getDisplayValue());
		data.put("pvtd", pvtd.getDisplayValue());
		data.put("pvempl", pvempl.getDisplayValue());
		data.put("pvsur", pvsur.getDisplayValue());
		data.put("pvfreq", pvfreq.getDisplayValue());
		data.put("pvespdiff", pvespdiff.getBoolean());
		data.put("pvjf", pvjf.getDisplayValue());
		data.put("pvhm", pvhm.getDisplayValue());*/
		data.put("pvtp", pvtp.getDisplayValue());
		data.put("pvfff", pvfff.getDisplayValue());
		//data.put("pvfcm", pvfcm.getDisplayValue());

		ObjectDB edition = g.getTmpObject("ComEdition");
		edition.resetFilters();
		edition.setFieldFilter("comACid", getFieldValue("comACid"));
		List<String[]> rows = edition.search();
		if (rows.isEmpty()) {
			msgs.add(Message.formatError("", "Il n'y a pas d'édition associée", ""));
			return msgs;
		}

		JSONArray editionsArray = new JSONArray();
		for (String[] row : rows) {
			edition.setValues(row);
			JSONObject editionObject = new JSONObject();

			ObjectField type = edition.getField("comEdType");
			ObjectField support = edition.getField("comEdSupport");
			ObjectField editionNR = edition.getField("comEdEditionNR");
			ObjectField editionCP = edition.getField("comEdEditionCP");
			ObjectField offre = edition.getField("comEdOffre");
			ObjectField datedeb = edition.getField("comEdDateDebut");
			ObjectField edLundi = edition.getField("comEdLundi");
			ObjectField edMar = edition.getField("comEdMardi");
			ObjectField edMer = edition.getField("comEdMercredi");
			ObjectField edJeu = edition.getField("comEdJeudi");
			ObjectField edVen = edition.getField("comEdVendredi");
			ObjectField edSam = edition.getField("comEdSamedi");
			ObjectField edDim = edition.getField("comEdDimanche");

			List<ObjectField> fieldsComamndeToCheck = Arrays.asList(type, support, offre, datedeb, edLundi, edMar,
					edMer, edJeu, edVen, edSam, edDim);
			emptyFields.addAll(sapHelper.checkFields(fieldsComamndeToCheck));

			editionObject.put("type", type.getDisplayValue());
			editionObject.put("support", support.getDisplayValue());
			editionObject.put("editionNR", editionNR.getDisplayValue());
			editionObject.put("editionCP", editionCP.getDisplayValue());
			editionObject.put("offre", offre.getDisplayValue());
			editionObject.put("datedeb", datedeb.getValue());
			editionObject.put("edLundi", edLundi.getValue());
			editionObject.put("edMar", edMar.getValue());
			editionObject.put("edMer", edMer.getValue());
			editionObject.put("edJeu", edJeu.getValue());
			editionObject.put("edVen", edVen.getValue());
			editionObject.put("edSam", edSam.getValue());
			editionObject.put("edDim", edDim.getValue());

			editionsArray.put(editionObject);
		}

		data.put("editions", editionsArray);

		if (emptyFields.isEmpty()) {
			msgs.add(Message.formatSimpleInfo("Action Commerciale envoyée à SAP avec succès"));
				TreeView treeview = g.getTreeView("ComACOPV");
				try{
					String reponse = JSONToolHelper.getObjectAsJsonTreeview(this, getRowId(), treeview);
					JSONObject jsonRep = new JSONObject(reponse);
					AppLog.info(getClass(), "method", jsonRep.toString(), getGrant());
					sapHelper.opvToSap(jsonRep);
						
					sendEmailNotif(this, g);						
						
				}
				catch(Exception e){
					AppLog.info(this.getClass(), "getJSON", e.getMessage(), g);
				}

		} else {
			msgs.add(Message.formatError("ERROR_CODE",
					"Merci de renseigner les champs suivants: " + String.join(", ", emptyFields), "fieldName"));
		}
		// msgs.add(Message.formatInfo("INFO_CODE", "Message", "fieldName"));
		// msgs.add(Message.formatWarning("WARNING_CODE", "Message", "fieldName"));

		return msgs;
	}
	
	public Object conventionQuotidienne(){
		return HTMLTool.redirectStatement("/ui/ext/ComConventionDiffExt?id="+getFieldValue("comACkey"));
	}
	
	
	public Object ficheclient(){
		return HTMLTool.redirectStatement("/ui/ext/COMFicheClient?id="+getRowId());
	}
	
	
	public Object conventionOPV(){
		TreeView treeview = getGrant().getTreeView("ComFicheClient");
		
		String ficheClient = JSONToolHelper.getObjectAsJsonTreeview(getGrant().getTmpObject("ComACOPV"), getRowId(), treeview);
	
		AppLog.info(getClass(), "ficheclient",ficheClient, getGrant());
	
		return HTMLTool.redirectStatement("/ui/ext/COMConventionOPV?id="+getRowId());
	}
	
	
	public void sendEmailNotif(ObjectDB obj, Grant g){
		

				
		String id = getRowId();


		ObjectDB edition = g.getTmpObject("ComEdition");
		edition.resetFilters();
		edition.setFieldFilter("comACid", getFieldValue("comACid"));
		List<String[]> rows = edition.search();
		String datedeb ="";
		JSONArray editionsArray = new JSONArray();
		for (String[] row : rows) {
			edition.setValues(row);
			datedeb = formatDateSap(edition.getFieldValue("comEdDateDebut"));
		}
		
		String directUrl = g.getSystemParam("DIRECT_URL");
		String publicUrl = directUrl.substring(0, directUrl.length() -3 );

		String urlBc = publicUrl + "/ext/COMConventionOPV?id="+id;
		String urlBConvention = publicUrl + "/ext/ComConventionDiffExt?id="+getFieldValue("comACkey");
		String urlFichePV = publicUrl + "/ext/COMFicheClient?id="+id;
		
		// mail #1 
		
		
		
		String bodyMail1 = 
			"Bonjour,<br/>"+
			"<a href="+urlFichePV+" target=\"_blank\">Ci joint,</a>"+
			" une fiche de création point de vente.<br/>Pensez à valider les données dans la ZVT90<br/>"+
			"Commentaire :<br/>"+
			this.getFieldValue("comACcommentaire")+
			"<br/>" + this.getFieldValue("comACsignatureCli");
		

		String titleMail1 = "Info nouvelle création ou mutation " + datedeb + " CP : " + this.getVilleCp();

		(new ComMailTool()).sendEmailJsreport(titleMail1,bodyMail1,"adv", g );
		
		// mail  #2
		
		String bodyMail2 = 
			"Bonjour,<br/>"+
			"Ci joint, le(s) document(s) contractuel(s) d'un  nouveau point de vente : <br/><ul>"+
			"<li><a href="+urlBc+" target=\"_blank\">Mise à dispo PLV</a></li>"+
			"<li><a href="+urlBConvention+" target=\"_blank\">Convention</a></li></ul>"+
			"<br/>"+
			"pour signature Olivier Boisnard.";

		String titleMail2 = "Info nouvelle création ou mutation " +datedeb;
		
		(new ComMailTool()).sendEmailJsreport(titleMail2,bodyMail2,"promotion", g );

	}
	 
	public String formatDateSap(String date){
		try{
			String[] dateSplit = date.split("-");
			return dateSplit[2]+"/"+dateSplit[1]+"/"+dateSplit[0];
		}catch (Exception e){
			return date;
		}
	}
	



}