package net.bioclipse.ds.ames.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.ds.Activator;
import net.bioclipse.ds.business.IDSManager;
import net.bioclipse.ds.model.DSException;
import net.bioclipse.ds.model.ITestResult;
import net.bioclipse.ds.model.result.ExternalMoleculeMatch;
import net.bioclipse.ds.signatures.business.ISignaturesManager;
import net.bioclipse.ds.tests.BaseDSModelsTest;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.junit.Test;

/**
 * 
 * @author ola
 */
public class TestAmesPredictions extends BaseDSModelsTest{

	String exactModel = "ames.lookup.exact";
	String nnModel = "ames.lookup.nearest";
	String alertsModel = "ames.smarts";


	@Test
	public void testAmesExacMatches() throws BioclipseException, DSException, MalformedURLException, IOException, CoreException, URISyntaxException{

		//Assert some hits
		assertExactMatchSMILES("O=C(OCCN(C)C)C=C", exactModel, ITestResult.POSITIVE);
		assertExactMatchSMILES("O=C=NC1=CC=CC(N=C=O)=C1C", exactModel, ITestResult.POSITIVE);

		//Assert some non-hits
		assertExactMatchSMILES("CCC", exactModel, ITestResult.NEGATIVE);

		//Assert some neg files
		assertExactMatch(loadMolecule("/data/bursiNegs1.mol"), exactModel, ITestResult.NEGATIVE);
		assertExactMatch(loadMolecule("/data/bursiNegs2.mol"), exactModel, ITestResult.NEGATIVE);
		assertExactMatch(loadMolecule("/data/bursiNegs3.mol"), exactModel, ITestResult.NEGATIVE);

		//Assert some pos files
		assertExactMatch(loadMolecule("/data/bursiPos1.mol"), exactModel, ITestResult.POSITIVE);
		assertExactMatch(loadMolecule("/data/bursiPos2.mol"), exactModel, ITestResult.POSITIVE);
		assertExactMatch(loadMolecule("/data/bursiPos3.mol"), exactModel, ITestResult.POSITIVE);


	}


	@Test
	public void testAmesNN() throws BioclipseException, DSException, URISyntaxException, MalformedURLException, IOException, CoreException{

		assertNN(loadMolecule("/data/bursiPos3.mol"), nnModel, 1);
		assertNN(loadMolecule("/data/sampleBursiHitsCmpds3.mol"), nnModel, 2);

	}


	@Test
	public void testAmesStructuralAlerts() throws BioclipseException, DSException, URISyntaxException, MalformedURLException, IOException, CoreException{

		//Some alerts
		assertStructuralAlert(loadMolecule("/data/sampleBursiHitsCmpds2.mol"), alertsModel, "Alkyl nitrite");
		assertStructuralAlert(loadMolecule("/data/sampleBursiHitsCmpds3.mol"), alertsModel, "Aromatic nitroso");
		assertStructuralAlert(loadMolecule("/data/sampleBursiHitsCmpds3.mol"), alertsModel, "Nitrosamine");

		//Some none-alerts
		assertStructuralAlert(loadMolecule("/data/bursiPos1.mol"), alertsModel, null);

	}



}
