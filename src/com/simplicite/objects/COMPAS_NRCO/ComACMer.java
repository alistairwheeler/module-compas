package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComACMer
 */
public class ComACMer extends ComActionCommerciale {
	private static final long serialVersionUID = 1L;

	@Override
	public void postLoad() {
		super.postLoad();
		//setSearchSpec(getDefaultSearchSpec() + " and ac_type='MER'");
	}

	@Override
	public void initList(ObjectDB parent) {
		
		super.initList(parent);
		if(isChildOf("ComClient") || isHomeInstance()){
			setSearchSpec(getDefaultSearchSpec());
		}
		else{
			setSearchSpec(getDefaultSearchSpec() + " and ac_type='MER'");
		}
	}

	@Override
	public void initCreate() {
		setFieldValue("comACtype", "MER");
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