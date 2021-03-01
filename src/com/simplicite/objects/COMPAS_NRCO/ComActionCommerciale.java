package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import java.text.SimpleDateFormat;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.*;

/**
 * Business object ComActionCommerciale
 */
public class ComActionCommerciale extends ObjectDB {
	
	private static final long serialVersionUID = 1L;
	
	
	public void verifContact() throws Exception{
		
		if(this.getFieldValue("comACContactId.comContactNom").equals("") || this.getFieldValue("comACContactId.comContactNom").equals(null) ){
			throw new Exception("Le nom du contact est invalide");
		}   
		
		//if(this.getFieldValue("comACContactId.comContactPrenom").equals("") || this.getFieldValue("comACContactId.comContactPrenom").equals(null) ){
		//	throw new Exception("Le prenom du contact est invalide");
		//}   
	}
	
	public void verifAddr() throws Exception{
		
		if(this.getFieldValue("comACAdresseId.comAdrCP").equals("") || this.getFieldValue("comACAdresseId.comAdrCP").equals(null) ){
			throw new Exception("Le code postal de l'adresse est invalide");
		}   
				
		if(this.getFieldValue("comACAdresseId.comAdrVille").equals("") || this.getFieldValue("comACAdresseId.comAdrVille").equals(null) ){
			throw new Exception("La ville est invalide");
		}  
				
		if(this.getFieldValue("comACAdresseId.comAdrLigne1").equals("") || this.getFieldValue("comACAdresseId.comAdrLigne1").equals(null) ){
			throw new Exception("L'adresse est invalide");
		}  
	}
	
	public String getVilleCp(){
		return this.getFieldValue("comACAdresseId.comAdrCP")+" ("+this.getFieldValue("comACAdresseId.comAdrVille")+")";
	}
	
	

	@Override
	public void postLoad() {
	    // if Commercial or manager, only show AC where zone of responsable is same as
	    // zone user
	    Grant g = getGrant();
	    if (g.hasResponsibility("COM_API") || g.hasResponsibility("COM_PUBLIC")) {
	        setDefaultSearchSpec("1=1");
	    }
	    else{
	    	
	    	String userZone = getUserZone(g.getUserUniqueId());
	    	
	    	if(!"".equals(userZone)){
	    		setDefaultSearchSpec("t.ac_zonelibelle =" + "'" + userZone + "'");
	    	}
	    	else{
	    		AppLog.info("===== L'UTILISATEUR N'A PAS DE ZONE ASSOCIEE =====", getGrant());
	    		setDefaultSearchSpec("1=0");
	    	}
	    }
	    /*if () && !g.hasResponsibility("DESIGNER") && !g.hasResponsibility("COM_SUPER_MANAGER") ) {
	        
	        String userZone = getUserZone(g.getUserUniqueId());
	        
	        setDefaultSearchSpec("t.ac_zonelibelle =" + "'" + userZone + "'");
	        //AWH 06/01/2020 il n'y a pas de home instance ici
	        if (isHomeInstance()) {
	        	
	            setSearchSpec(getDefaultSearchSpec() + " and COM_ACUSER_ID=" + g.getUserId());
	            
	        }
	        else{
	            setSearchSpec(getDefaultSearchSpec());
	        }
	    }*/
	    
	    // if ADV, show everything
	}
	
	private String getUserZone(String userId){
		
		ObjectDB user = getGrant().getTmpObject("ComUser");
		user.select(userId);
		String userZone = user.getFieldValue("comUserZoneLibelle");
		
		return userZone;
	}

	@Override
	public String getUserKeyLabel(String[] row) {
		String label = getFieldValue("comAClibelle");
		return label;
	}

	@Override
	public boolean isDeleteEnable(String[] row) {
		return Tool.isEmpty(getFieldValue("comACsapID"));
	}
	
	@Override
	public void initList(ObjectDB parent) {
		
		AppLog.info(getDefaultSearchSpec(), getGrant());
		Grant g = getGrant();
		
		if(isHomeInstance()){
			getFieldArea("ComActionCommerciale-27").setVisible(true);
			
			// 1365, à surveiller
			setFieldOrder("comACdatePrev",1);
			for(int i = 1; i < 26; i++){
				FieldArea fA = getFieldArea("ComActionCommerciale-"+i);
				if(!Tool.isEmpty(fA)){
					fA.setVisible(false);
				}
			}
			
			if (g.hasResponsibility("COM_COMMERCIAL")) {
				setSearchSpec(getDefaultSearchSpec() + " and COM_ACUSER_ID=" + g.getUserId() + " and (current_Date <= ac_dateprev and ac_dateprev < current_date + 15)");
			}
			
			
			AppLog.info(getClass(), "init todo list", getSearchSpec().toString(), getGrant() );
		}
		else {
			getFieldArea("ComActionCommerciale-27").setVisible(false);
			
			//setSearchSpec("t.ac_zonelibelle =" + "'" + getUserZone(g.getUserUniqueId()) + "'");
			
			setSearchSpec(getDefaultSearchSpec());
		}
	
		
	}

	@Override
	public void initCreate() {

		Grant g = getGrant();

		if (g.hasResponsibility("COM_API")) {
			List<ObjectField> fields = getFields();
			for (ObjectField field : fields) {
				field.setUpdatable(ObjectField.UPD_ALWAYS);
			}
		}
		else{
			
			getField("comACtype").setUpdatable(true);
			
			
			getFieldArea("ComActionCommerciale-19").setVisible(false);
			getFieldArea("ComActionCommerciale-27").setVisible(false);
			//setFieldValue("comACUserId", getGrant().getUserId());
	
			ObjectDB userObj = g.getTmpObject("ComUser");
			userObj.select(String.valueOf(getGrant().getUserId()));
			setFieldValue("comACzoneLibelle", userObj.getFieldValue("comUserZoneLibelle"));
	
			if (isChildOf("ComClient")) {
				//getField("comACtype").setUpdatable(true);
				ObjectDB parent = getParentObject();
				// get adresse principale get contact principal
				String clientID = parent.getFieldValue("comCliID");
				ObjectDB adresse = g.getTmpObject("ComAdresse");
				adresse.resetFilters();
				adresse.setFieldFilter("comAdresseClientId.comCliID", clientID);
				adresse.setFieldFilter("comAdrType", "PRIN");
	
				ObjectDB contact = g.getTmpObject("ComContact");
				contact.resetFilters();
				contact.setFieldFilter("comContactClientId.comCliID", clientID);
				contact.setFieldFilter("comContactPrincipal", "1");
	
				try {
					List<String[]> rows = new BusinessObjectTool(adresse).search();
					for (int i = 0; i < rows.size(); i++) {
						String[] adress = rows.get(i);
						adresse.setValues(adress);
						String adressID = adresse.getFieldValue("comAdrId");
						AppLog.info(getClass(), "initCreate", adressID, g);
						setFieldValue("comACAdresseId", adressID);
					}
					List<String[]> rowsC = new BusinessObjectTool(contact).search();
					for (int i = 0; i < rowsC.size(); i++) {
						String[] cont = rowsC.get(i);
						contact.setValues(cont);
						String contactID = contact.getFieldValue("comContactID");
						AppLog.info(getClass(), "initCreate", contactID, g);
						setFieldValue("comACContactId", contactID);
					}
				} catch (Exception e) {
					AppLog.error(getClass(), "", null, e, getGrant());
				}
	
			}
			if (isChildOf("ComAdresse")) {
				//getField("comACtype").setUpdatable(true);
				ObjectDB parent = getParentObject();
				// get client et contact princiapl
				String clientID = parent.getFieldValue("comAdresseClientId.comCliID");
				ObjectDB contact = g.getTmpObject("ComContact");
				contact.resetFilters();
				contact.setFieldFilter("comContactClientId.comCliID", clientID);
				contact.setFieldFilter("comContactPrincipal", "1");
				try {
					List<String[]> rowsC = new BusinessObjectTool(contact).search();
					for (int i = 0; i < rowsC.size(); i++) {
						String[] cont = rowsC.get(i);
						contact.setValues(cont);
						String contactID = contact.getFieldValue("comContactID");
						AppLog.info(getClass(), "initCreate", contactID, g);
						setFieldValue("comACContactId", contactID);
					}
				} catch (Exception e) {
					AppLog.error(getClass(), "", null, e, getGrant());
				}
				setFieldValue("comACClientId", clientID);
	
			}
			if (isChildOf("ComContact")) {
				ObjectDB parent = getParentObject();
				//getField("comACtype").setUpdatable(true);
				String clientID = parent.getFieldValue("comContactClientId");
				setFieldValue("comACClientId", clientID);
				ObjectDB adresse = g.getTmpObject("ComAdresse");
				adresse.resetFilters();
				adresse.setFieldFilter("comAdresseClientId.comCliID", clientID);
				adresse.setFieldFilter("comAdrType", "PRIN");
				try {
					List<String[]> rows = new BusinessObjectTool(adresse).search();
					for (int i = 0; i < rows.size(); i++) {
						String[] adress = rows.get(i);
						adresse.setValues(adress);
						String adressID = adresse.getFieldValue("comAdrId");
						AppLog.info(getClass(), "initCreate", adressID, g);
						setFieldValue("comACAdresseId", adressID);
					}
				} catch (Exception e) {
					AppLog.error(getClass(), "", null, e, getGrant());
				}
			}
			
		}
		
	
	}
	
	@Override
	public void initCopy() {
		setFieldValue("comACid", getRowId());
		setFieldValue("comACsapID", null);
	}

	@Override
	public boolean isUpdateEnable(String[] row) {
		Grant g = getGrant();
		ObjectDB ac = g.getTmpObject("ComActionCommerciale");
		// if user is commercial update not available if zone is != zone user
		if (g.hasResponsibility("COM_COMMERCIAL") && !g.hasResponsibility("COM_MANAGER")
				&& !g.hasResponsibility("COM_ADV")) {
			String respId = getFieldValue("comACUserId");
			if (!Integer.toString(g.getUserId()).equals(respId)) {
				// getField("comAClibelle").setUpdatable(false);
				return false;
			}
		}
		// getField("comAClibelle").setUpdatable(true);
		return true;
	}

	@Override
	public void initUpdate() {
		// if user is manager update is enabled
		// if user is admin update is enabled
		getField("comACtype").setUpdatable(false);
		
		Grant g = getGrant();

		if (g.hasResponsibility("COM_API")) {
			List<ObjectField> fields = getFields();
			for (ObjectField field : fields) {
				field.setUpdatable(ObjectField.UPD_ALWAYS);
			}
		}
		
	}

	@Override
	public String preCreate() {
		ObjectDB parent = getParentObject();
		/*
		 * if("ComClient".equals(parent.getName())) setFieldValue("comACClientId",
		 * parent.getFieldValue("comContactClientId"));
		 */
		if(!getGrant().hasResponsibility("COM_API")){
			setFieldValue("comACUserId", getGrant().getUserId());
		}
		return null;
	}

	@Override
	public String preSave() {
		
		if(getGrant().hasResponsibility("COM_API")){
				setFieldValue("comACzoneLibelle", getFieldValue("comCliZone"));
		}
		
		else{
			
			AppLog.info(getClass(), "preSave", "|toto|", getGrant());

			Grant g = getGrant();
			ObjectDB userObj = g.getTmpObject("ComUser");
			userObj.select(getFieldValue("comACUserId"));
			setFieldValue("comACzoneLibelle", userObj.getFieldValue("comUserZoneLibelle"));
	
			ObjectField libelleField = getField("comAClibelle");
			ObjectField locationField = getField("comACAdresseId.comAdrLigne1");
			ObjectField descriptionField = getField("comACcommentaire");
			ObjectField startDateField = getField("comACdatePrev");
			ObjectField startTimeField = getField("comACheurePrev");
			ObjectField endDateField = getField("comACdateFin");
			ObjectField endTimeField = getField("comACheureFinPrev");
	
			if ("1".equals(getFieldValue("comACajouterAgenda")) && Tool.isEmpty(getFieldValue("comACEventId"))) {
	
				// cover update and delete
				try {
					com.google.api.services.calendar.Calendar service = GoogleAPITool.getCalendarService(getGrant());
	
					com.google.api.services.calendar.model.Event event = createEvent(libelleField, locationField,
							descriptionField, startDateField, startTimeField, endDateField, endTimeField);
	
					String calendarId = "primary";
					event = service.events().insert(calendarId, event).execute();
					setFieldValue("comACEventId", event.getId());
				} catch (Exception e) {
					AppLog.error(getClass(), "preSave", e.getMessage(), e, getGrant());
				}
			}
	
			if ("1".equals(getFieldValue("comACajouterAgenda")) && !Tool.isEmpty(getFieldValue("comACEventId"))) {
				// check if date and time have changed
				// check if description has changed
				if (libelleField.hasChanged() || locationField.hasChanged() || descriptionField.hasChanged()
						|| startDateField.hasChanged() || startTimeField.hasChanged() || endDateField.hasChanged()
						|| endTimeField.hasChanged()) {
					try {
						com.google.api.services.calendar.Calendar service = GoogleAPITool.getCalendarService(getGrant());
						// Retrieve the event from the API
	
						com.google.api.services.calendar.model.Event eventToUpdate = updateEvent(
								getFieldValue("comACEventId"), libelleField, locationField, descriptionField,
								startDateField, startTimeField, endDateField, endTimeField);
						// Update the event
						com.google.api.services.calendar.model.Event updatedEvent = service.events()
								.update("primary", eventToUpdate.getId(), eventToUpdate).execute();
					} catch (Exception e) {
						AppLog.error(getClass(), "postSave", e.getMessage(), e, getGrant());
					}
				}
			}
	
			if ("0".equals(getFieldValue("comACajouterAgenda")) && !Tool.isEmpty(getFieldValue("comACEventId"))) {
				try {
					com.google.api.services.calendar.Calendar service = GoogleAPITool.getCalendarService(getGrant());
					service.events().delete("primary", getFieldValue("comACEventId")).execute();
					setFieldValue("comACEventId", null);
				} catch (Exception e) {
					AppLog.error(getClass(), "postSave", e.getMessage(), e, getGrant());
				}
	
			}
				
		}
		
		if(Tool.isEmpty(getFieldValue("comACkey"))){
			setFieldValue("comACkey", Tool.randomUUID());
			//setFieldValue("comACkey", Tool.randomString(80));
		}
		
		return null;
	}
	

	

	@Override
	public List<String> preValidate() {
		
		
		List<String> msgs = new ArrayList<String>();
		
		String type = this.getFieldValue("comACtype");
		
		AppLog.info(getClass(), "preValidate", "|"+type+"|", getGrant());

		
		if(type.equals("VIS")  || type.equals("APP") || type.equals("RDV") || type.equals("PGM") || type.equals("MER") ){
			
			if( this.getFieldValue("comACheurePrev").equals(null) || this.getFieldValue("comACheurePrev").equals("") ){
				msgs.add(Message.formatError("ERROR_CODE", "Veuillez saisir une heure de début", "comACheurePrev"));
			}
			if( this.getFieldValue("comACdatePrev").equals(null) || this.getFieldValue("comACdatePrev").equals("") ){
				msgs.add(Message.formatError("ERROR_CODE", "Veuillez saisir une date de début", "comACdatePrev"));
			}
			

		}

		
		if (!Tool.isEmpty(getFieldValue("comPNRprixTtc"))) {
			Double prix = Double.parseDouble(getFieldValue("comPNRprixTtc"));
			Double remise = Double.parseDouble(getFieldValue("comPNRtauxRemise"));
			Double val = prix - prix * remise / 100;

			AppLog.info(getClass(), "method", Double.toString(val), getGrant());

			setFieldValue("comPNRtotalRemise", val);
		}
		return msgs;
	}

	@Override
	public String[] getTargetObject(String rowId, String[] row) {
		if (rowId.equals(ObjectField.DEFAULT_ROW_ID))
			return null;
		if (row == null && select(rowId))
			row = getValues();
		String target = null;
		if (row != null) {
			String type = row[getFieldIndex("comACtype")];
			
			if (type.equals("APP"))
				target = "ComACAppel";
			else if (type.equals("RDV"))
				target = "ComACRDV";
			else if (type.equals("VIS"))
				target = "ComACVisite";
			else if (type.equals("OPV"))
				target = "ComACOPV";
			else if (type.equals("PNR"))
				target = "ComACPNR";
			else if (type.equals("PBR"))
				target = "ComACPBR";
			else if (type.equals("PGM"))
				target = "ComACGMS";
			else if (type.equals("VPR"))
				target = "ComACVPriv";
			else if (type.equals("MER"))
				target = "ComACMer";
			else if (type.equals("VAU"))
				target = "ComACAutre";

		}
		
		if (target == null)
			return null; // no redirection
		String t[] = new String[3];
		t[0] = target; // target object
		t[1] = "the_ajax_" + target; // main target instance
		t[2] = rowId; // target id
		
		return t;
	}

	public com.google.api.services.calendar.model.Event updateEvent(String eventId, ObjectField libelle,
			ObjectField location, ObjectField description, ObjectField startDate, ObjectField startTime,
			ObjectField endDate, ObjectField endTime) {
		try {
			com.google.api.services.calendar.Calendar service = GoogleAPITool.getCalendarService(getGrant());
			com.google.api.services.calendar.model.Event event = service.events()
					.get("primary", getFieldValue("comACEventId")).execute();

			event.setSummary(libelle.getValue()).setLocation(location.getValue()).setDescription(description.getValue())
					.setStart(formatEventDateTime(startDate, startTime)).setEnd(formatEventDateTime(endDate, endTime));

			return event;
		} catch (Exception e) {
			AppLog.error(getClass(), "postSave", e.getMessage(), e, getGrant());
		}

		return null;
	}

	public com.google.api.services.calendar.model.Event createEvent(ObjectField libelle, ObjectField location,
			ObjectField description, ObjectField startDate, ObjectField startTime, ObjectField endDate,
			ObjectField endTime) {
		try {
			com.google.api.services.calendar.model.Event event = new com.google.api.services.calendar.model.Event();
			event.setSummary(libelle.getValue()).setLocation(location.getValue())
					.setDescription(description.getValue());
			event.setStart(formatEventDateTime(startDate, startTime));
			event.setEnd(formatEventDateTime(endDate, endTime));
			return event;
		} catch (Exception e) {
			AppLog.error(getClass(), "postSave", e.getMessage(), e, getGrant());
		}
		return null;
	}

	public com.google.api.services.calendar.model.EventDateTime formatEventDateTime(ObjectField date,
			ObjectField time) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = date.getValue() + " " + time.getValue();
			Date dateFormatted = format.parse(dateString);
			com.google.api.client.util.DateTime dateTimeFromatted = new com.google.api.client.util.DateTime(
					dateFormatted, TimeZone.getTimeZone("Europe/Paris"));
			com.google.api.services.calendar.model.EventDateTime eventDateTimeFormatted = new com.google.api.services.calendar.model.EventDateTime()
					.setDateTime(dateTimeFromatted).setTimeZone("Europe/Paris");
			return eventDateTimeFormatted;
		} catch (Exception e) {
			AppLog.error(getClass(), "postSave", e.getMessage(), e, getGrant());
		}
		return null;
	}


}