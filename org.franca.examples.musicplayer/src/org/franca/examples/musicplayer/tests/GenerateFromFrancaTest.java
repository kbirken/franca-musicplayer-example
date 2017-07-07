/*******************************************************************************
 * Copyright (c) 2017 itemis AG (http://www.itemis.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.franca.examples.musicplayer.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.franca.core.dsl.FrancaIDLInjectorProvider;
import org.franca.core.dsl.FrancaPersistenceManager;
import org.franca.core.franca.FModel;
import org.franca.generators.FrancaGenerators;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Testcase for the Franca=>Websocket generator.
 * 
 * @author Klaus Birken
 */
@RunWith(XtextRunner.class)
@InjectWith(FrancaIDLInjectorProvider.class)
public class GenerateFromFrancaTest {

	final static String SERVER_GEN_DIR = "server_nodejs/gen";
	final static String CLIENT_GEN_DIR = "client/gen";

	@Inject
	FrancaPersistenceManager loader;
	
	@Test
	public void test01() {
		genAndSave("org/example/Musicplayer.fidl");
	}


	private void genAndSave (String filename) {
		// load example Franca IDL interface
    	URI root = URI.createURI("classpath:/");
    	URI loc = URI.createFileURI(filename);
    	FModel fmodel = loader.loadModel(loc, root);
		assertNotNull(fmodel);
		//System.out.println("Franca IDL: package '" + fmodel.getName() + "'");
		
		// create HTML documentation from Franca model
		assertTrue(FrancaGenerators.instance().genWebsocket(fmodel,
				SERVER_GEN_DIR, CLIENT_GEN_DIR));
	}

}
