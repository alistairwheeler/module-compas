package com.simplicite.commons.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.Tool;
import com.simplicite.util.tools.*;
import java.lang.Exception;
import java.io.IOException;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import static java.nio.charset.StandardCharsets.*;
import org.apache.commons.codec.binary.Base64;

import java.net.http.*;

import java.net.http.HttpResponse.*;
import java.net.URI;
/**
* Shared code jsreportHelper
*/
public class JsreportHelper implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    private String baseUrl;
    
    public JsreportHelper(String baseUrl){
        this.baseUrl = baseUrl;
    }
    
    public JsreportHelper(){
        this.baseUrl = System.getenv("JSREPORT_BASE_URL");
    }
    
    public String report(String templateId, JSONObject data)  {
        
        
        JSONObject templatePayload = new JSONObject(); 
        templatePayload.accumulate("shortid",templateId);
        
        JSONObject optionsPayload = new JSONObject(); 
        optionsPayload.accumulate("timeout",60000);
        
        JSONObject jsreportPayload = new JSONObject();
        jsreportPayload.accumulate("template",templatePayload);
        jsreportPayload.accumulate("options",optionsPayload);
        jsreportPayload.accumulate("data",data);
        
        
        try {
            
            HttpRequest requetePost = HttpRequest.newBuilder()
                .uri(URI.create(this.baseUrl+"/api/report"))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsreportPayload.toString()))
                .build();
            
            HttpClient httpClient = HttpClient.newHttpClient();
            
            
            HttpResponse response;
            
            response = httpClient.send(requetePost, BodyHandlers.ofString());
            
            
            return  response.body().toString();
            
        } catch (IOException | InterruptedException e) {
            
            
            return e.toString();
        }
        
    }

    
}