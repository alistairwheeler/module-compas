package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.apache.commons.lang3.StringUtils;
import java.text.SimpleDateFormat;

/**
 * Business object ComUserObjectifs
 */
public class ComUserObjectifs extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		Grant g = getGrant();
		if (g.hasResponsibility("COM_API")) {
			setSearchSpec("1=1");
		}
		if (g.hasResponsibility("COM_COMMERCIAL")) {
			setSearchSpec("USEROBJ_USERID=" + g.getUserId());
			
			List<ObjectField> fields = getFields();
			for(ObjectField field : fields){
				if(field.getName().endsWith("Obj")){
					field.setUpdatable(false);
				} 
			}
			
		}
		
		if (g.hasResponsibility("COM_MANAGER") && !g.hasResponsibility("DESIGNER") && !g.hasResponsibility("COM_SUPER_MANAGER") ) {
			String userZone = g.simpleQuery("select user_zoneid from m_user where row_id='" + g.getUserId() + "'");
			setSearchSpec("t.USEROBJ_USERID in (select row_id from m_user where USER_ZONEID ='"+userZone+"')");
		}
	}

	@Override
	public void initList(ObjectDB parent) {
		
		Grant g = getGrant();
		
		int dbVendor = g.getDBVendor();
		boolean postgre = dbVendor == Globals.DB_POSTGRESQL ? true : false;
		String userZone = g.simpleQuery("select z.USERZONE_LIBELLE from m_user u right JOIN com_userzone z on u.USER_ZONEID = z.row_id WHERE u.row_id='"+g.getUserId()+"'");
		
		ObjectDB objectif = g.getTmpObject("ComUserObjectifs");
		List<String[]> rslts = objectif.search();
		if (g.hasResponsibility("COM_COMMERCIAL")) {
			getField("usr_first_name").setVisibility(ObjectField.VIS_FORM);
			getField("usr_last_name").setVisibility(ObjectField.VIS_FORM);
		}
		
		
		AppLog.info(getClass(), "contains", "======= ZONE = "+userZone, getGrant());
		
		if(!userZone.contains("86/79")){
			//display packCP ventes magasin
			//display CP vente privileges
			getField("comObjVprivCPRes").setVisibility(ObjectField.VIS_HIDDEN);
			getField("comObjVmagCPRes").setVisibility(ObjectField.VIS_HIDDEN);
			getField("comObjVprivCPObj").setVisibility(ObjectField.VIS_HIDDEN);
			getField("comObjVmagCPObj").setVisibility(ObjectField.VIS_HIDDEN);
		} else {
			
			getField("comObjVprivCPRes").setVisibility(ObjectField.VIS_BOTH);
			getField("comObjVmagCPRes").setVisibility(ObjectField.VIS_BOTH);
			getField("comObjVprivCPObj").setVisibility(ObjectField.VIS_BOTH);
			getField("comObjVmagCPObj").setVisibility(ObjectField.VIS_BOTH);
		}
		
		
		
		for (String[] item : rslts) {
			objectif.setValues(item);
			String login = objectif.getFieldValue("usr_login");
			String mois = objectif.getFieldValue("comUOmois");
			String comRowId = g.simpleQuery("select row_id from m_user where usr_login='" + login + "'");
			
			String nbAppelMonth = postgre ? 
									g.simpleQuery("select count(*) from com_actioncommerciale where date_part('month', AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='APP' ") : 
									g.simpleQuery("select count(*) from com_actioncommerciale where MONTH(AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='APP'");
			String nbVisitesMonth = postgre ? 
									g.simpleQuery("select count(*) from com_actioncommerciale where date_part('month', AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='VIS' AND AC_DATEPREV<=CURRENT_DATE") : 
									g.simpleQuery("select count(*) from com_actioncommerciale where MONTH(AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='VIS'");
			
			String querryRDV = "select count(*) from com_actioncommerciale where date_part('month', AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='RDV' AND AC_DATEPREV<=CURRENT_DATE";
			String nbRDVMonth = postgre ? 
									g.simpleQuery(querryRDV) : 
									g.simpleQuery("select count(*) from com_actioncommerciale where MONTH(AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='RDV'");
			String nbOPVMonth = postgre ? 
									g.simpleQuery("select "+
											"count(distinct(ac.row_id)) "+
										"from "+
											"com_actioncommerciale ac "+
										"left join "+
											"com_edition ed "+
											"on ac.row_id=ed.ed_acid "+
										"where "+
											"COM_ACUSER_ID="+comRowId+" "+
											"AND "+
											"AC_TYPE='OPV' "+
											"and "+
											"date_part('month', ed.ed_datedebut)="+mois+
											"AND "+
											"ac.ac_sapid is not null " + // pas de sapid => pas de remonté
											"AND "+
											"ed.ed_support='NR' "+ // comptabiliser uniquement les NR ( les CP sont dupliquées )
											"AND "+
											"ac.ac_signaturecli is NULL" // ne pas comptabiliser les mutations 
									) : 
									g.simpleQuery("select count(*) from com_actioncommerciale where MONTH(AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='OPV'");
			String nbMerMonth = postgre ? 
									g.simpleQuery("select count(*) from com_actioncommerciale where date_part('month', AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='MER' AND AC_DATEPREV<=CURRENT_DATE") : 
									g.simpleQuery("select count(*) from com_actioncommerciale where MONTH(AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='MER'");	
			String nbGMSMonth = postgre ? 
									g.simpleQuery("select count(*) from com_actioncommerciale where date_part('month', AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='PGM' AND AC_DATEPREV<=CURRENT_DATE") : 
									g.simpleQuery("select count(*) from com_actioncommerciale where MONTH(AC_DATEPREV)="
									+ mois + " AND COM_ACUSER_ID=" + comRowId + " AND AC_TYPE='PGM'");						


			Integer nbAppel = StringUtils.isNotBlank(nbAppelMonth) ? Integer.parseInt(nbAppelMonth) : 0;
			Integer nbVis = StringUtils.isNotBlank(nbVisitesMonth) ? Integer.parseInt(nbVisitesMonth) : 0;
			Integer nbRDV = StringUtils.isNotBlank(nbRDVMonth) ? Integer.parseInt(nbRDVMonth) : 0;
			Integer nbCreation = StringUtils.isNotBlank(nbOPVMonth) ? Integer.parseInt(nbOPVMonth) : 0;
			Integer nbMer = StringUtils.isNotBlank(nbMerMonth) ? Integer.parseInt(nbMerMonth) : 0;
			Integer nbGMS = StringUtils.isNotBlank(nbGMSMonth) ? Integer.parseInt(nbGMSMonth) : 0;
			
			Integer nbActions = nbAppel + nbVis + nbRDV;
			Integer nbReseau = nbCreation;
			Integer nbAnim = nbMer + nbGMS;
			
			if(nbRDV > 0){
				AppLog.info( getClass(), "InitList", "Login : "+login+" month "+ mois +" acMonth : "+nbRDVMonth, getGrant());
			

				AppLog.info( getClass(), "InitList", querryRDV , getGrant());

			}	
		
		
			AppLog.info(getClass(), "toto", nbVisitesMonth , getGrant() );
			
			objectif.setFieldValue("comObjACRes", nbActions);
			objectif.setFieldValue("comObjAppRes", nbAppel);
			objectif.setFieldValue("comObjVisRes", nbVis);
			objectif.setFieldValue("comObjRdvRes", nbRDV);
			
			objectif.setFieldValue("comObjRESRes", nbReseau);
			objectif.setFieldValue("comObjCreaRes", nbCreation);
			
			objectif.setFieldValue("comObjANIMRes", nbAnim);
			objectif.setFieldValue("comObjMerchRes", nbMer);
			objectif.setFieldValue("comObjGmsRes", nbGMS);
			
			
			// VENTES PRIV & PACKS BROCANTE
			List<String[]> rowIdsOfCommandeVpriv = postgre ? g.query("select cj.row_id from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
							+ " and date_part('month', ac.pb_dateope)="
							+ mois + " and ac.row_id = cj.com_acid AND (ac.ac_type='PBR' OR ac.ac_type='VPR')and cj.COM_EDITION!='CP' AND ac.ac_sapid is not null") : g.query("select cj.row_id from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
							+ " and MONTH(ac.pb_dateope)="
							+ mois + " and ac.row_id = cj.com_acid AND (ac.ac_type='PBR' OR ac.ac_type='VPR') and cj.COM_EDITION!='CP'");
			
		    //Début VBO 1214	
			/* 				
			String nbCPVpriv = postgre ? g.simpleQuery("select cj.com_quantite from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
							+ " and date_part('month', ac.pb_dateope)="
							+ mois + " and ac.row_id = cj.com_acid AND (ac.ac_type='PBR' OR ac.ac_type='VPR') and (cj.COM_EDITION='CP') AND ac.ac_sapid is not null ") : g.simpleQuery("select cj.com_quantite from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
							+ " and MONTH(ac.pb_dateope)="
							+ mois + " and ac.row_id = cj.com_acid AND (ac.ac_type='PBR' OR ac.ac_type='VPR') and (cj.COM_EDITION='CP')");
			*/
			
		
	       String nbCPVpriv1 = postgre ? g.simpleQuery("select SUM(cj.com_quantite) from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
							+ " and date_part('month', ac.pb_dateope)="
							+ mois + " and ac.row_id = cj.com_acid AND (ac.ac_type='PBR') and (cj.COM_EDITION='CP') AND ac.ac_sapid is not null ") : 
							g.simpleQuery("select cj.com_quantite from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
							+ " and MONTH(ac.pb_dateope)="
							+ mois + " and ac.row_id = cj.com_acid AND (ac.ac_type='PBR') and (cj.COM_EDITION='CP')");
	
	
	       String nbCPVpriv2 = postgre ? g.simpleQuery("select SUM(cj.com_quantite) from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
							+ " and date_part('month', ac.pb_dateope)="
							+ mois + " and ac.row_id = cj.com_acid AND (ac.ac_type='VPR') and (cj.COM_EDITION='CP') AND ac.ac_sapid is not null ") : 
							g.simpleQuery("select cj.com_quantite from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
							+ " and MONTH(ac.pb_dateope)="
							+ mois + " and ac.row_id = cj.com_acid AND (ac.ac_type='VPR') and (cj.COM_EDITION='CP')");
							
			Integer InbCPVpriv1 =StringUtils.isNotBlank(nbCPVpriv1) ? Integer.parseInt(nbCPVpriv1) : 0;	
			Integer InbCPVpriv2 =StringUtils.isNotBlank(nbCPVpriv2) ? Integer.parseInt(nbCPVpriv2) : 0;
			
	        Integer nbCPVpriv = InbCPVpriv1 + InbCPVpriv2;
	        //VBO 1210 Ventes privilèges CP : comObjVmagCPRes remplace comObjVprivCPRes
			objectif.setFieldValue("comObjVmagCPRes", String.valueOf(nbCPVpriv));
	       //Fin VBO 1214						
			
			
			
			Integer comSemaineVpriv = getCommandes(rowIdsOfCommandeVpriv)[0];
			Integer comDimancheVpriv = getCommandes(rowIdsOfCommandeVpriv)[1];
			objectif.setFieldValue("comObjVprivPnrRes", String.valueOf(comSemaineVpriv));
			objectif.setFieldValue("comObjVprivPnrdRes", String.valueOf(comDimancheVpriv));
			//VBO 1210 Ventes privilèges CP : comObjVmagCPRes remplace comObjVprivCPRes
			//VBO 1214 objectif.setFieldValue("comObjVmagCPRes", StringUtils.isNotBlank(nbCPVpriv) ? Integer.parseInt(nbCPVpriv) : 0);
					
			Integer nbComPriv = comSemaineVpriv + comDimancheVpriv;
			objectif.setFieldValue("comObjectifsVentesPrivRes", String.valueOf(nbComPriv));		
			
			
			
			// PACKS NR
			List<String[]> rowIdsOfCommandeMag = postgre ? g.query("select cj.row_id from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
					+ " and date_part('month', ac.pnr_datedebut)="	+ mois + " and ac.row_id = cj.com_acid AND ac.ac_type='PNR' and cj.COM_EDITION!='CP' AND ac.ac_sapid is not null "):
					g.query("select cj.row_id from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
					+ " and MONTH(ac.pnr_datedebut)="	+ mois + " and ac.row_id = cj.com_acid AND ac.ac_type='PNR' and cj.COM_EDITION!='CP'");
			
			String nbCPMag = postgre ? g.simpleQuery(
				"select SUM(cj.com_quantite) "+
				"from com_actioncommerciale ac, com_commandejournaux cj "+
				"where ac.com_acuser_id = " + comRowId+ 
				" and date_part('month', ac.pnr_datedebut)="	+ mois + 
				" and ac.row_id = cj.com_acid "+
				"AND ac.ac_type='PNR' "+
				"and (cj.COM_EDITION='CP') "+
				"AND ac.ac_sapid is not null")
				:
					g.simpleQuery("select cj.com_quantite from com_actioncommerciale ac, com_commandejournaux cj where ac.com_acuser_id = " + comRowId
					+ " and MONTH(ac.pnr_datedebut)="	+ mois + " and ac.row_id = cj.com_acid AND ac.ac_type='PNR' and (cj.COM_EDITION='CP')");
			
			Integer comSemaineMag = getCommandes(rowIdsOfCommandeMag)[0];
			Integer comDimancheMag = getCommandes(rowIdsOfCommandeMag)[1];
			objectif.setFieldValue("comObjVmagPnrRes", String.valueOf(comSemaineMag));
			objectif.setFieldValue("comObjVmagPnrdRes", String.valueOf(comDimancheMag));
			//VBO 1210 Packs CP (ventes magasin) : comObjVprivCPRes remplace comObjVmagCPRes
			objectif.setFieldValue("comObjVprivCPRes", StringUtils.isNotBlank(nbCPMag) ? Integer.parseInt(nbCPMag) : 0);
					
			Integer nbComMag = comSemaineMag + comDimancheMag;
			objectif.setFieldValue("comObjectifsVentesMagRes", String.valueOf(nbComMag));		
								
			AppLog.info(getClass(), "initList", "i'm Here", getGrant());
			
			
			objectif.save();
			
		}

			
	}
	
	public Integer[] getCommandes(List<String[]> rowIds){
			
			Integer comSemaine = 0;
			Integer comDimanche = 0;
			
			for(String[] rowIdTab : rowIds){
				for(String rowId : rowIdTab){
					ObjectDB commande = getGrant().getTmpObject("ComCommandeJournaux");
					commande.select(rowId);
					try{
						Date date = new SimpleDateFormat("yyyy-MM-dd").parse(commande.getFieldValue("comComDateParution"));
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
						switch(dayOfWeek){
							case 1:
								comDimanche += Integer.parseInt(commande.getFieldValue("comComQuantite"));
								break;
							case 2:
								comSemaine += Integer.parseInt(commande.getFieldValue("comComQuantite"));
								break;
							case 3:
								comSemaine += Integer.parseInt(commande.getFieldValue("comComQuantite"));
								break;
							case 4:
								comSemaine += Integer.parseInt(commande.getFieldValue("comComQuantite"));
								break;
							case 5:
								comSemaine += Integer.parseInt(commande.getFieldValue("comComQuantite"));
								break;
							case 6:
								comSemaine += Integer.parseInt(commande.getFieldValue("comComQuantite"));
								break;
							case 7:
								comSemaine += Integer.parseInt(commande.getFieldValue("comComQuantite"));
								break;
							default:
								AppLog.info(getClass(), "initList", "Something went wrong", getGrant());
						}
					}
					catch(Exception e){
						AppLog.fatal(getClass(), "initList", "message", e, getGrant());
					}
				}
			}
			Integer[] commandes = new Integer[2];
			commandes[0] = comSemaine;
			commandes[1] = comDimanche;
			
			return commandes;
			
	}
	

	@Override
	public String getStyle(ObjectField f, java.lang.String[] row) {

		if (getContext().isCreate()) {
			return "";
		} else {
			Grant g = getGrant();
			ObjectDB objectifs = g.getTmpObject("ComUserObjectifs");
			objectifs.setValues(row);
			
			Integer resAC = StringUtils.isNotBlank(objectifs.getFieldValue("comObjACRes")) ? Integer.parseInt(objectifs.getFieldValue("comObjACRes")) : 0;
			Integer objAC = StringUtils.isNotBlank(objectifs.getFieldValue("comObjACObj")) ? Integer.parseInt(objectifs.getFieldValue("comObjACObj")) : 0;
			
			/*Integer resApp = Integer.parseInt(objectifs.getFieldValue("comObjAppRes"));
			Integer resVis = Integer.parseInt(objectifs.getFieldValue("comObjVisRes"));
			Integer resRdv = Integer.parseInt(objectifs.getFieldValue("comObjRdvRes"));*/
			
			Integer resRes = StringUtils.isNotBlank(objectifs.getFieldValue("comObjRESRes")) ? Integer.parseInt(objectifs.getFieldValue("comObjRESRes")) : 0;
			Integer objRes = StringUtils.isNotBlank(objectifs.getFieldValue("comObjRESObj")) ? Integer.parseInt(objectifs.getFieldValue("comObjRESObj")) : 0;
			
			Integer resAnim = StringUtils.isNotBlank(objectifs.getFieldValue("comObjANIMRes")) ? Integer.parseInt(objectifs.getFieldValue("comObjANIMRes")) : 0;
			Integer objAnim = StringUtils.isNotBlank(objectifs.getFieldValue("comObjANIMObj")) ? Integer.parseInt(objectifs.getFieldValue("comObjANIMObj")) : 0;
			
			Integer resVMag = StringUtils.isNotBlank(objectifs.getFieldValue("comObjectifsVentesMagRes")) ? Integer.parseInt(objectifs.getFieldValue("comObjectifsVentesMagRes")) : 0;
			Integer objVMag = StringUtils.isNotBlank(objectifs.getFieldValue("comObjectifsVentesMagObj")) ? Integer.parseInt(objectifs.getFieldValue("comObjectifsVentesMagObj")) : 0;
			
			Integer resVpriv = StringUtils.isNotBlank(objectifs.getFieldValue("comObjectifsVentesPrivRes")) ? Integer.parseInt(objectifs.getFieldValue("comObjectifsVentesPrivRes")) : 0;
			Integer objVpriv = StringUtils.isNotBlank(objectifs.getFieldValue("comObjectifsVentesPrivObj")) ? Integer.parseInt(objectifs.getFieldValue("comObjectifsVentesPrivObj")) : 0;
			
			/*if("comObjACRes".equals(f.getName())){
				return compareValues(resAC, objAC);
			}
			if("comObjRESRes".equals(f.getName())){
				return compareValues(resRes, objRes);
			}
			if("comObjANIMRes".equals(f.getName())){
				return compareValues(resAnim, objAnim);
			}
			if("comObjectifsVentesMagRes".equals(f.getName())){
				return compareValues(resVMag, objVMag);
			}
			if("comObjectifsVentesPrivRes".equals(f.getName())){
				return compareValues(resVpriv, objVpriv);
			}*/
			
			List<ObjectField> fields = objectifs.getFields();
			for(ObjectField field : fields){
				String fieldName = field.getName();
				if(fieldName.contains("Res") && fieldName.equals(f.getName())){
					String corresObjField = fieldName.replace("Res", "Obj");
					return compareValues(getIntValue(objectifs, fieldName), getIntValue(objectifs, corresObjField));
				}
			}
			
			
		}
		return "";
	}
	
	@Override
	public String preSave() {
		
		
		Grant g = getGrant();
		
		AppLog.info(getClass(), "contains", "======= this "+this.getOldValues(this).toString(), g);
		
		

		
		//return Message.formatInfo("INFO_CODE", "Message", "fieldName");
		//return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
		//return Message.formatError("ERROR_CODE", "Message", "fieldName");
		return null;
	}
	
	public String compareValues(Integer res, Integer obj){
		if(res < obj){
			return "redbg";
		}
		else
			return "greenbg";
	}
	
	public Integer getIntValue(ObjectDB obj, String fieldName){
		 return StringUtils.isNotBlank(obj.getFieldValue(fieldName)) ? Integer.parseInt(obj.getFieldValue(fieldName)) : 0;		
	}
	
	
}