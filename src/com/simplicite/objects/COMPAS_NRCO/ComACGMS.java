package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.COMPAS_NRCO.ComMailTool;
import java.text.SimpleDateFormat;  
import java.util.Date;  

/**
 * Business object ComACGMS
 */
public class ComACGMS extends ComActionCommerciale {
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		super.postLoad();
		//
	}

	@Override
	public void initList(ObjectDB parent) {
		
		super.initList(parent);
		if(isChildOf("ComClient") || isHomeInstance()){
			setSearchSpec(getDefaultSearchSpec());
		}
		else{
			setSearchSpec(getDefaultSearchSpec() + " and ac_type='PGM'");
		}
	}

	@Override
	public void initCreate() {
		setFieldValue("comACtype", "PGM");
		super.initCreate();
	}

	@Override
	public void initUpdate() {
		FieldArea area19 = getFieldArea("ComActionCommerciale-19");
		area19.setVisible(false);
		super.initUpdate();
	}

	public void newPackMail() {

		Grant g = getGrant();

	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  

		String title = "Info nouveau pack GMS "+formatter.format(new Date());
		String body = "Bonjour,<br />Création d'un nouveau pack GMS<br />Commentaire : <br />"+this.getFieldValue("comACcommentaire");

		(new ComMailTool()).sendEmailJsreport(title,body,"promotion", g );

	}

	
	@Override
	public List<String> preValidate() {
		List<String> msgs =  super.preValidate();
		
		if( this.getFieldValue("comACheurePrev") == null || this.getFieldValue("comACheurePrev").equals("") ){
			msgs.add(Message.formatError("ERROR_CODE", "Veuillez saisir une heure de début", "comACheurePrev"));
		}
		if( this.getFieldValue("comACdatePrev") == null || this.getFieldValue("comACdatePrev").equals("") ){
			msgs.add(Message.formatError("ERROR_CODE", "Veuillez saisir une date de début", "comACdatePrev"));
		}
		
		return msgs;
	}

}