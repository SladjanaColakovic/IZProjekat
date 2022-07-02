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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.inzinjering.dto.FuzzyDTO;

@Service
public class FuzzyService {
	
	@Autowired
	private ProcessorSuggestionService processorService;
	
	@Autowired
	private GraphicsCardSuggestionService GCService;
	
Model model = ModelFactory.createDefaultModel();
	
	public List<String> getProcessors(){
		List<String> result = new ArrayList<>();
		try {
			InputStream is = TypeReference.class.getResourceAsStream("/ontologija.owl");
	        RDFDataMgr.read(model,is,Lang.TURTLE);  
			String queryString = 
	        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
	                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
	        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
	                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
	                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
	                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
	                + "SELECT  ?processor "
	                +"WHERE "
	                + "{ "
	                +"?processor"
	                + " rdf:type "
	                +"base:Processor"
	                +" .} ";
	        Query query = QueryFactory.create(queryString);
	        System.out.println(query);
	        QueryExecution qexec = QueryExecutionFactory.create(query, model);
	        try {
	            ResultSet results = qexec.execSelect();
	            while (results.hasNext()) {
	                QuerySolution solution = results.nextSolution();
	                Resource r = solution.getResource("processor");
	                String processor = r.getLocalName();
	                System.out.println(processor);
	                result.add(processor);
	            }
	            return result;
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }finally {
	            qexec.close();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} 
			
		return result;
	}
	
	public List<String> getGCs(){
		List<String> result = new ArrayList<>();
		try {
			InputStream is = TypeReference.class.getResourceAsStream("/ontologija.owl");
	        RDFDataMgr.read(model,is,Lang.TURTLE);  
			String queryString = 
	        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
	                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
	        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
	                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
	                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
	                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
	                + "SELECT  ?gc ?type "
	                +"WHERE "
	                + "{ "
	                +"?gc"
	                + " rdf:type "
	                +"?type. "
	                + "?type rdfs:subClassOf base:GraphicsCard"
	                +" .} ";
	        Query query = QueryFactory.create(queryString);
	        System.out.println(query);
	        QueryExecution qexec = QueryExecutionFactory.create(query, model);
	        try {
	            ResultSet results = qexec.execSelect();
	            while (results.hasNext()) {
	                QuerySolution solution = results.nextSolution();
	                Resource r = solution.getResource("gc");
	                String gc = r.getLocalName();
	                System.out.println(gc);
	                result.add(gc);
	            }
	            return result;
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }finally {
	            qexec.close();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} 
			
		return result;
	}
	
	public boolean isDiscreteGC(String gc) {
		Boolean result = false;
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "ASK  "
                +"WHERE "
                + "{"
                +"base:"
                + gc
                + " rdf:type "
                +"base:DiscreteGC ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            result = qexec.execAsk();
            return result;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return result;		
	}
	
	public void fuzzyLogic(FuzzyDTO fuzzyDTO) {
		int cores = processorService.processorCores(fuzzyDTO.getProcessor());
		int threads = processorService.processorThreads(fuzzyDTO.getProcessor());
		boolean isDicreteGC = isDiscreteGC(fuzzyDTO.getGC());
		if(isDicreteGC) {
			String gc = GCService.gcMemory(fuzzyDTO.getGC());
			int gcMemoryCapacity = Integer.parseInt(gc.split("_")[0]);
		}
		int ramCapacity = Integer.parseInt(fuzzyDTO.getRAM().split(" ")[1]);
		int hdCapacity = Integer.parseInt(fuzzyDTO.getHD().split(" ")[1]);
		int os = getOS(fuzzyDTO.getOS());
		
	}
	
	private int getOS(String os) {
		
		switch (os) {
		case "Linux" : {
			return 1;
		}
		case "Windows XP" : {
			return 2;
		}
		case "Windows Vista" : {
			return 3;
		}
		case "Windows 7" : {
			return 4;
		}
		case "Windows 8" : {
			return 5;
		}
		case "Windows 10" : {
			return 6;
		}
		default:
			return 0;
		}
		
	}

}
