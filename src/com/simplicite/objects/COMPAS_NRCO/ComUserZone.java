package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComUserZone
 */
public class ComUserZone extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public String getUserKeyLabel(String[] row) {
		String label = getFieldValue("comUserZoneLibelle");
		return label;
	}

}