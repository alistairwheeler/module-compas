package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComMotifAchat
 */
public class ComMotifAchat extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public String getUserKeyLabel(String[] row) {
		String label = getFieldValue("comMAlibelle");
		return label;
	}

}