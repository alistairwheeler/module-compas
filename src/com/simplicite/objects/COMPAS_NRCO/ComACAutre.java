package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComACAutre
 */
public class ComACAutre extends ComActionCommerciale {
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
			setSearchSpec(getDefaultSearchSpec() + " and ac_type='VAU'");
		}
	}
	
	@Override
	public void initCreate() {
		setFieldValue("comACtype", "VAU");
		super.initCreate();
	}

	@Override
	public void initUpdate() {
		FieldArea area19 = getFieldArea("ComActionCommerciale-19");
		area19.setVisible(false);
		super.initUpdate();
	}

}