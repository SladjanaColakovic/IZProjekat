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

import com.projekat.inzinjering.dto.ProcessorDTO;

@Service
public class ProcessorSuggestionService {
	
	Model model = ModelFactory.createDefaultModel();
	
	public List<ProcessorDTO> getCompatibleProcessors(String motherboard){
		
		List<ProcessorDTO> processors = new ArrayList<>();
		try {
			InputStream is = TypeReference.class.getResourceAsStream("/ontologija.owl");
	        RDFDataMgr.read(model,is,Lang.TURTLE); 
	        String socket = findProcessorSocket(motherboard);
	        String chipset = findMotherboardChipset(motherboard);
	        List<String> socketCompatibleProcessors = new ArrayList<>();
	        List<String> chipsetCompatibleProcessors = new ArrayList<>();
	        List<String> compatibleProcessors = new ArrayList<>();
	        socketCompatibleProcessors = socketCompatibleProcessors(socket);
	        chipsetCompatibleProcessors = chipsetCompatibleProcessors(chipset);
	        compatibleProcessors = chipsetAndSocketCompatibility(chipsetCompatibleProcessors, socketCompatibleProcessors);
	        System.out.println(chipsetCompatibleProcessors);
	        System.out.println(socketCompatibleProcessors);
	        System.out.println(compatibleProcessors);
	        for(String p: compatibleProcessors) {
	        	ProcessorDTO processor = new ProcessorDTO();
	        	String frequency = processorFrequency(p);
	        	String boostFrequency = processorBoostFrequency(p);
	        	String tdp =  processorTDP(p);
	        	int cores = processorCores(p);
	        	int threads = processorThreads(p);
	        	String l1Cache = l1Cache(p);
	        	String l2Cache = l2Cache(p);
	        	String l3Cache = l3Cache(p);
	        	String l1Capacity = cacheCapacity(l1Cache);
	        	String l2Capacity = cacheCapacity(l2Cache);
	        	String l3Capacity = cacheCapacity(l3Cache);
	        	processor.setFrequency(frequency);
	        	processor.setBoostFrequency(boostFrequency);
	        	processor.setName(p.replace("_", " "));
	        	processor.setTDP(tdp);
	        	processor.setCores(cores);
	        	processor.setThreads(threads);
	        	processor.setL1Cache(l1Capacity.replace("_", ""));
	        	processor.setL2Cache(l2Capacity.replace("_", ""));
	        	processor.setL3Cache(l3Capacity.replace("_", ""));
	        	System.out.println(frequency);
	        	System.out.println(boostFrequency);
	        	System.out.println(tdp);
	        	System.out.println(cores);
	        	System.out.println(threads);
	        	System.out.println(l1Cache);
	        	System.out.println(l2Cache);
	        	System.out.println(l3Cache);
	        	System.out.println(l1Capacity);
	        	System.out.println(l2Capacity);
	        	System.out.println(l3Capacity);
	        	processors.add(processor);
	        }
	        return processors;
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return processors;
		
	}
	
	private String findProcessorSocket(String motherboard) {
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT  ?socket "
                +"WHERE "
                + "{ "
                +"base:"
                + motherboard
                + " base:hasProcessorSocket "
                +"?socket"
                +" .} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("socket");
                String socket = r.getLocalName();
                System.out.println(socket);
                return socket;
            }
            
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        return "";
	}
	
	private String findMotherboardChipset(String motherboard) {
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT  ?chipset "
                +"WHERE "
                + "{ "
                +"base:"
                + motherboard
                + " base:hasChipset "
                +"?chipset"
                +" .} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("chipset");
                String chipset = r.getLocalName();
                System.out.println(chipset);
                return chipset;
            }
            
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }
        return "";
	}
	
	private List<String> socketCompatibleProcessors(String socket) {
		List<String> processors = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?processor "
                +"WHERE "
                + "{"
                +"base:"
                + socket
                + " base:socketCompatibility "
                +"?processor"
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
                processors.add(processor);    
                
            }
            return processors;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return processors;	
	}
	
	
	private List<String> chipsetCompatibleProcessors(String chipset) {
		List<String> processors = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?processor "
                +"WHERE "
                + "{"
                +"base:"
                + chipset
                + " base:chipsetCompatibility "
                +"?processor"
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
                processors.add(processor);    
                
            }
            return processors;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return processors;	
	}
	
	private List<String> chipsetAndSocketCompatibility(List<String> chipsetCompatible, List<String> socketCompatible) {
		List<String> processors = new ArrayList<>();
		
		for(String cC: chipsetCompatible) {
			for(String sC : socketCompatible) {
				if(sC.equals(cC)) {
					processors.add(sC);
				}
			}
		}
        return processors;	
	}
	
	private String processorFrequency(String processor) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?frequency "
                +"WHERE "
                + "{"
                +"base:"
                + processor
                + " base:hasFrequency "
                +"?frequency ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("frequency");
                String[] array = r.toString().split("#");
                String frequency = array[1];
                return frequency.replace("_", "");
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String processorTDP(String processor) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?tdp "
                +"WHERE "
                + "{"
                +"base:"
                + processor
                + " base:hasTDP "
                +"?tdp ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("tdp");
                String tdp = r.toString().split("#")[1];
                return tdp;
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String processorBoostFrequency(String processor) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?boostFrequency "
                +"WHERE "
                + "{"
                +"base:"
                + processor
                + " base:hasBoostFrequency "
                +"?boostFrequency ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("boostFrequency");
                String[] array = r.toString().split("#");
                String boostFrequency = array[1];
                return boostFrequency.replace("_", "");
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	public int processorCores(String processor) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?cores "
                +"WHERE "
                + "{"
                +"base:"
                + processor
                + " base:numberOfCores "
                +"?cores ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Literal l = solution.getLiteral("cores");
                return l.getInt();
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return 0;		
	}
	
	public int processorThreads(String processor) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?threads "
                +"WHERE "
                + "{"
                +"base:"
                + processor
                + " base:numberOfThreads "
                +"?threads ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Literal l = solution.getLiteral("threads");
                return l.getInt();
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return 0;		
	}
	
	private String l1Cache(String processor) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?l1Cache "
                +"WHERE "
                + "{"
                +"base:"
                + processor
                + " base:hasL1Cache "
                +"?l1Cache ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("l1Cache");
                return r.getLocalName();
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String l2Cache(String processor) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?l2Cache "
                +"WHERE "
                + "{"
                +"base:"
                + processor
                + " base:hasL2Cache "
                +"?l2Cache ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("l2Cache");
                return r.getLocalName();
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String l3Cache(String processor) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?l3Cache "
                +"WHERE "
                + "{"
                +"base:"
                + processor
                + " base:hasL3Cache "
                +"?l3Cache ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("l3Cache");
                return r.getLocalName();
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String cacheCapacity(String cache) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?capacity "
                +"WHERE "
                + "{"
                +"base:"
                + cache
                + " base:hasCacheCapacity "
                +"?capacity ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("capacity");
                return r.toString().split("#")[1];
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}

}
