package com.simplicite.commons.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import org.json.JSONObject;
import org.json.JSONArray;

import com.simplicite.commons.COMPAS_NRCO.JsreportHelper;

/**
 * Shared code ComMailTool
 */
public class ComMailTool {
	private static final long serialVersionUID = 1L;
	
	private Mail mail;
	protected Grant g;
	private List<String> to;
	private List<String> cc;
	private List<String> bcc;
	private String from;
	private String replyTo;
	private String subject;
	private String content;
	private List<Mail.MailImage> img;
	private List<Mail.MailAttach> attach;
	private String[] files;

	public ComMailTool(){
		g = Grant.getSystemAdmin();
		mail = new Mail(g);
		to = new ArrayList<>();
		cc = new ArrayList<>();
		bcc = new ArrayList<>();
		subject = "Subject";
		from = g.getParameter("EMAIL_DEFAULT_SENDER", "");
		replyTo = null;
		img = new ArrayList<>();
		attach = new ArrayList<>();
		setContent("...");
		files = null;
	}

	final public void addRcpt(String mail){
		to.add(mail);
	}

	final public void addRcpt(String[] mails){
		for(String mail : mails)
			addRcpt(mail);
	}

	final public void addCc(String mail){
		cc.add(mail);
	}

	final public void addCc(String[] mails){
		for(String mail : mails)
			addCc(mail);
	}

	final public void addBcc(String mail){
		bcc.add(mail);
	}

	final public void addBcc(String[] mails){
		for(String mail : mails)
			addBcc(mail);
	}

	final public void setReplyTo(String reply) {
		if (!Tool.isEmpty(reply))
			replyTo = reply;
	}

	final public void addAttach(ObjectDB inst, ObjectField field){
		AppLog.info(getClass(), "addAttach","AAAAAAATCH == " ,g);
		Mail.MailAttach attch = mail.documentAttach(inst, field);
		AppLog.info(getClass(), "addAttach","AAAAAAATCH == " +attch.getFileName(),g);
		attach.add(attch);
	}
	
	final public void addAttach(Mail.MailAttach attch){
		attach.add(attch);
	}
	
	final public void setAttach(List<Mail.MailAttach> attach){
		this.attach = attach ;
	}

	final public String addImage(Mail.MailImage image) {
		img.add(image);
		return image.getRefId();
	}

	final public void setSubject(String sjt){
		subject = sjt;
	}

	final public void setFrom(String mail) {
		if (!Tool.isEmpty(from))
			from = mail;
	}

	final public Mail getMail() {
		return mail;
	}

	public void setContent(String c) {
		if(c!=null){
			String template = HTMLTool.getResourceHTMLContent(g, "COM_MAIL_TEMPLATE");
			String css = HTMLTool.getResourceCSSContent(g, "COM_MAIL_STYLES");
			template = template.replace("[CSS]", css);
			template = template.replace("[BODY]", c);
			content = template;
		}
	}
	
	public void addFiles(String[] pathDocArray){
		files = pathDocArray;
	}
	
	
	public void sendEmailJsreport(String titre, String body, String mailRCPTSIndex, List<Mail.MailAttach> pjs, Grant grant){
		this.setAttach(pjs);
		this.sendEmailJsreport( titre, body, mailRCPTSIndex, grant);
	}
	
	
	
	public void sendEmailJsreport(String titre, String body, String mailRCPTSIndex, Grant grant){
		
		String jsReportUrl = grant.getSystemParam("NRCO_JSREPORT_URL");
		String templatShortId = (new JSONObject(grant.getSystemParam("NRCO_JSREPORT_TPL_SHORTID"))).getString("mailTpl");
		

		
		JSONObject jsonRep = new JSONObject();
		
    	jsonRep.put("body", body);
    	


    	jsonRep.put("user",  grant.getFirstName() + " " + grant.getLastName());
    	
    	this.addCc(grant.getEmail());
		
		JsreportHelper jsreport = new JsreportHelper(jsReportUrl);
		String res = jsreport.report(templatShortId, jsonRep);

		JSONObject rcptsObject = new JSONObject(grant.getSystemParam("NRCO_MAIL_RCPTS"));
		
	
		JSONArray rcptsArray = rcptsObject.optJSONArray(mailRCPTSIndex);
		if(rcptsArray != null ){
			for (int i = 0; i < rcptsArray.length(); i++) {
				AppLog.info(getClass(), "senMailJSreport ",rcptsArray.getString(i),  grant);
				this.addRcpt(rcptsArray.getString(i));

			}
		}else{
			String rcpts = rcptsObject.optString(mailRCPTSIndex);
				AppLog.info(getClass(), "sendEmailJsreport", "JsReport mail receiver |"+rcpts+"|",g);
			if( rcpts != null && !rcpts.equals("") ){
			    this.addRcpt(rcpts);
			}else{
			   	this.addRcpt(mailRCPTSIndex);
			}
		}
	
		
		this.setSubject(titre) ;
		this.setContent(res);
		this.send();
		
		AppLog.info(getClass(), "sendEmailJsreport", "JsReport mail sended to "+this.to.toString()+" with template "+templatShortId, g);
		
	}

	final public void send(){
		String[] rcpt = to.toArray(new String[to.size()]);
		String[] copie = cc.size()>0 ? cc.toArray(new String[cc.size()]) : null;
		String[] bcopie = bcc.size()>0 ? bcc.toArray(new String[bcc.size()]) : null;
 		Mail.MailAttach[] attachements = attach.size()>0 ? attach.toArray(new Mail.MailAttach[attach.size()]) : null;
		Mail.MailImage[] images = img.size()>0 ? img.toArray(new Mail.MailImage[img.size()]) : null;
		
		mail.sendWithAttach(rcpt, from, replyTo, copie, bcopie, subject, content, Mail.MAIL_MIME_TYPE_HTML, null, attachements, images);
		//mail.sendWithAttach(rcpt, from, copie, bcopie, subject, content, Mail.MAIL_MIME_TYPE_HTML, files, null);
	}
	
	
}