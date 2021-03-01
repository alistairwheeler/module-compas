package com.simplicite.commons.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Shared code JSONToolHelper
 */
public class JSONToolHelper implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Grant g;
	
	public JSONToolHelper(Grant userGrant){
		g = userGrant.getSystemAdmin();
	}
	
	public static String getObjectAsJsonTreeview(ObjectDB object, String rowId, TreeView tv){
		/*Parameters.ParamTreeView p = new Parameters.ParamTreeView();
		p.treeview = tv;
		p.depth = 5;*/
		try{
			//String jsonTree = JSONTool.get(object, rowId, ObjectDB.CONTEXT_LIST, false, null, null, true, false, null, false, false, null, p);
			String jsonTree = JSONTool.get(object, rowId, ObjectDB.CONTEXT_LIST, false, null, null, true, false, null, false, false, null, tv, null, 5);
			return jsonTree;
		}
		catch(Exception e){
			return null;
		}
	}
	
}