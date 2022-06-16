package com.projekat.inzinjering.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.springframework.asm.TypeReference;
import org.springframework.stereotype.Service;

@Service
public class ComponentSuggestionService {
	
	private static final String SELECT_URL = "http://localhost:3030/inz/sparql";
	
	Model model = ModelFactory.createDefaultModel();
	public void ramSuggestion() {
			
		try {
			InputStream is = TypeReference.class.getResourceAsStream("/ontologija.owl");
	        RDFDataMgr.read(model,is,Lang.TURTLE);
	       
	       
	        int slotNum = ramSlots(model);
	        if(slotNum != 0) {
	        	int maxRam = maxRamCapacity(model);
	        	System.out.println(maxRam);
	        }
			is.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	private int ramSlots(Model mod) {
		String motherboard = "Gigabyte_H410M_S2H_V3_G10";
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?slotNum "
                +"WHERE "
                + "{"
                +"base:"
                + motherboard
                + " base:numberOfRamSlots "
                +"?slotNum ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Literal l = solution.getLiteral("slotNum");
                return l.getInt();
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        
        return 0;
		
	}
	
	private int maxRamCapacity(Model mod) {
	
		String processor = "AMD_Ryzen_5_5600X_Hexa_Core";
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?maxRam "
                +"WHERE "
                + "{"
                +"base:"
                + processor
                + " base:providesMaxRAMCapacity "
                +"?maxRam ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("maxRam");
                String resource = r.toString();
                String[] array = resource.split("#");
                String[] array1 = array[1].split("_");
                return Integer.parseInt(array1[0]);
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        
        return 0;
		
	}
}
