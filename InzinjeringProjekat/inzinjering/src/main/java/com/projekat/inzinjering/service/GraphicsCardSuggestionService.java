package com.projekat.inzinjering.service;

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
import org.springframework.asm.TypeReference;
import org.springframework.stereotype.Service;

import com.projekat.inzinjering.dto.GraphicsCardDTO;

@Service
public class GraphicsCardSuggestionService {

Model model = ModelFactory.createDefaultModel();
	
	public List<GraphicsCardDTO> getCompatibleGC(String motherboard) {
		List<GraphicsCardDTO> compatibleGC = new ArrayList<>();
		try {
			InputStream is = TypeReference.class.getResourceAsStream("/ontologija.owl");
	        RDFDataMgr.read(model,is,Lang.TURTLE);
			List<String> gcs = findGCs();
			System.out.println(gcs);
			for(String gc : gcs) {
				GraphicsCardDTO graphicsCard = new GraphicsCardDTO();
				String slot = findGCSlot(gc);
				System.out.println(slot);
				String version = pcieVersion(slot);
				String type = pcieType(slot);
				boolean compatible = compatibleSlot(version, type, motherboard);
				if(compatible) {
					graphicsCard = graphicsCard(gc);
					compatibleGC.add(graphicsCard);	
				}
			}
			return compatibleGC;					
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return compatibleGC;
	}
	
	private GraphicsCardDTO graphicsCard(String gc) {
		GraphicsCardDTO card = new GraphicsCardDTO();
	
		card.setName(gc.replace("_", " "));
		card.setMemory(gcMemory(gc).replace("_", " "));
		card.setMemoryType(gcMemoryType(gc));
			
		return card;
	}
	
	
	private boolean compatibleSlot(String version, String type, String motherboard) {
		List<String> pcies = findPCIes(motherboard);
		System.out.println(pcies);
		for(String pcie : pcies) {
			if(pcieVersion(pcie).equals(version) && pcieType(pcie).equals(type))  {
				return true;
			}
		}
		return false;
	}

	private String gcMemoryType(String gc) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?memoryType "
                +"WHERE "
                + "{ "
                +"base:"
                + gc
                + " base:discreteMemoryType "
                +"?memoryType ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
            	QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("memoryType");
                String result = r.toString().split("#")[1];
                
                return result;
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String gcMemory(String gc) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?memory "
                +"WHERE "
                + "{"
                +"base:"
                + gc
                + " base:discreteMemory "
                +"?memory ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
            	QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("memory");
                String result = r.toString().split("#")[1];
                
                return result;
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String pcieVersion(String pcie) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?version "
                +"WHERE "
                + "{"
                +"base:"
                + pcie
                + " base:version "
                +"?version ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
            	QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("version");
                String result = r.toString().split("#")[1];
                
                return result;
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String pcieType(String pcie) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?pcieType "
                +"WHERE "
                + "{"
                +"base:"
                + pcie
                + " rdf:type "
                +"?pcieType ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("pcieType");
                String result = r.toString().split("#")[1];
                
                return result;
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private List<String> findGCs() {
		List<String> gcs = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?gc "
                +"WHERE "
                + "{"
                +" ?gc "
                + "rdf:type "
                + "base:DiscreteGC"
                + " . } "
                ;
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("gc");
                String gc = r.getLocalName();
                gcs.add(gc);    
                
            }
            return gcs;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return gcs;	
	}
	
	private String findGCSlot(String gc) {
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?slot "
                +"WHERE "
                + "{"
                +" base:"
                + gc
                + " base:discreteGCSlot"
                + " ?slot"
                +" . } ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("slot");
                return r.getLocalName();  
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";				
	}
	
	
	private List<String> findPCIes(String motherboard) {
		List<String> pcies = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?pcie "
                +"WHERE "
                + "{"
                +" base:"
                + motherboard
                + " base:hasPCIe "
                +"?pcie"
                + " . } "
                ;
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            System.out.println(results);
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("pcie");
                String pcie = r.getLocalName();
                System.out.println(pcie);

                pcies.add(pcie);       
            }
            return pcies;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return pcies;	
	}
}
