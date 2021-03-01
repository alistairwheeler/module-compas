package com.simplicite.commons.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.*;
import java.net.*;
import java.io.*;

/**
 * Shared code ComSAPHelper
 */
public class ComSAPHelper implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Grant g;
	
	
	public ComSAPHelper(Grant userGrant){
		g = userGrant.getSystemAdmin();
	}
	
	public List<String>	checkFields(List<ObjectField> fieldsToSend){
		
		List<String> emptyFields = new ArrayList<String>();
		for(ObjectField field: fieldsToSend){
			if("".equals(field.getValue())){
				emptyFields.add(field.getDisplay());
			}
		}
		return emptyFields;
	}
	
	
	public void prospectToSap(JSONObject data){
		AppLog.info(getClass(), "prospectToSap", data.toString(), g);
		if(data.isEmpty()){
		}
		else{
			String endpoint = g.getSystemParam("ENDPOINT_SAP");
			
			try{
				RESTTool.post(data, endpoint);
			}
			catch(Exception e){
				AppLog.error(getClass(), "opvToSap", "Exception on sending to SAP", e, g);
			}
		}
		
	}
	
	public void pnrToSap(JSONObject data) throws IOException{
		
		String endpoint = g.getSystemParam("ENDPOINT_SAP");
		
		try{
			RESTTool.post(data, endpoint);
		}
		catch(Exception e){
			AppLog.error(getClass(), "opvToSap", "Exception on sending to SAP", e, g);
		}
	}
	
	public void vprivToSap(JSONObject data){
		String endpoint = g.getSystemParam("ENDPOINT_SAP");
		
		try{
			RESTTool.post(data, endpoint);
		}
		catch(Exception e){
			AppLog.error(getClass(), "opvToSap", "Exception on sending to SAP", e, g);
		}
	}
	
	public void pbrToSap(JSONObject data){
		String endpoint = g.getSystemParam("ENDPOINT_SAP");
		
		try{
			RESTTool.post(data, endpoint);
		}
		catch(Exception e){
			AppLog.error(getClass(), "opvToSap", "Exception on sending to SAP", e, g);
		}
	}
	
	public void opvToSap(JSONObject data) throws IOException{
		
		String endpoint = g.getSystemParam("ENDPOINT_SAP");
		
		try{
			RESTTool.post(data, endpoint);
			
		}
		catch(Exception e){
			AppLog.error(getClass(), "opvToSap", "Exception on sending to SAP", e, g);
		}
	}
	
}