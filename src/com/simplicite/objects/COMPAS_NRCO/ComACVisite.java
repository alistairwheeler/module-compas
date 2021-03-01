package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComACVisite
 */
public class ComACVisite extends ComActionCommerciale {
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		super.postLoad();
		
	}
	
	@Override
	public void initList(ObjectDB parent) {
		
		super.initList(parent);
		if(isChildOf("ComClient") || isHomeInstance()){
			setSearchSpec(getDefaultSearchSpec());
		}
		else{
			setSearchSpec(getDefaultSearchSpec() + " and ac_type='VIS'");
		}
	
	}

	@Override
	public void initCreate() {
		setFieldValue("comACtype", "VIS");
		getField("comACtype").setUpdatable(false);
		super.initCreate();
	}

	@Override
	public void initUpdate() {
		FieldArea area19 = getFieldArea("ComActionCommerciale-19");

		area19.setVisible(false);

		super.initUpdate();
	}
	
	@Override
	public List<String> preValidate() {

		AppLog.info(getClass(), "preValidate heure ", this.getFieldValue("comACheurePrev"), getGrant() );
		AppLog.info(getClass(), "preValidate date ", this.getFieldValue("comACheurePrev"), getGrant() );


		List<String> msgs =  super.preValidate();

		AppLog.info(getClass(), "preValidate super","ok", getGrant() );
		

		if( this.getFieldValue("comACheurePrev") == null || this.getFieldValue("comACheurePrev").equals("") ){
			msgs.add(Message.formatError("ERROR_CODE", "Veuillez saisir une heure de début", "comACheurePrev"));
		}else{
			AppLog.info(getClass(), "preValidate heure ", this.getFieldValue("comACheurePrev"), getGrant() );
		}
		if( this.getFieldValue("comACdatePrev") == null || this.getFieldValue("comACdatePrev").equals("") ){
			msgs.add(Message.formatError("ERROR_CODE", "Veuillez saisir une date de début", "comACdatePrev"));
		}else{
			AppLog.info(getClass(), "preValidate date ", this.getFieldValue("comACdatePrev"), getGrant() );
		}
		
		return msgs;
	}
	
	@Override
	public String preSave() {
		
		//super.preSave();
		
		//return Message.formatInfo("INFO_CODE", "Message", "fieldName");
		//return Message.formatWarning("WARNING_CODE", "Message", "fieldName");
		//return Message.formatError("ERROR_CODE", "Message", "fieldName");
		return null;
	}

}