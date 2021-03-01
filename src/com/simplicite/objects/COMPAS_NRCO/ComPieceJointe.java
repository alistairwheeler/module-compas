package com.simplicite.objects.COMPAS_NRCO;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object ComPieceJointe
 */
public class ComPieceJointe extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public String getUserKeyLabel(String[] row) {
		String label;
		if (!Tool.isEmpty(getFieldValue("comPJnom"))) {
			label = getFieldValue("comPJnom");
		} else
			label = "Fichier_" + getFieldValue("comPJID");

		return label;
	}

}