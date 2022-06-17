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

import com.projekat.inzinjering.dto.BetterRAMDTO;
import com.projekat.inzinjering.dto.RAMSuggestionDTO;

@Service
public class ComponentSuggestionService {
	
	Model model = ModelFactory.createDefaultModel();
	public List<BetterRAMDTO> ramSuggestion(RAMSuggestionDTO ramSuggestion) {
			List<BetterRAMDTO> result = new ArrayList<>();
		try {
			InputStream is = TypeReference.class.getResourceAsStream("/ontologija.owl");
	        RDFDataMgr.read(model,is,Lang.TURTLE);   
	        int slotNum = ramSlots(ramSuggestion.getMotherboard());
	        if(slotNum != 0) {
	        	String pc = findPC(ramSuggestion.getMotherboard());
	        	String processor = findProcessor(pc);
	        	int maxRam = maxRamCapacity(processor);
	        	String ram = ram(ramSuggestion.getMotherboard());
	        	System.out.println(ram);
	        	int speed = ramSpeed(ram);
	        	int capacity = ramCapacity(ram);
	        	String layout = ramLayout(ram);
	        	String type = ramType(ram);
	        	List<String> rams = new ArrayList<>();
	        	List<String> betterRams = new ArrayList<>();
	        	rams = rams(type, ram);
	        	betterRams = betterRams(rams, speed, capacity, maxRam, slotNum);
	        	result = suggestBetterRams(betterRams);
	        	System.out.println("RAM slots: " + slotNum);
	        	System.out.println("Max RAM capacity: " + maxRam);
	        	System.out.println("RAM speed: " + speed);
	        	System.out.println("RAM capacity: " + capacity);
	        	System.out.println("Layout: "+ layout);
	        	System.out.println("RAM type: "+ type);
	        	System.out.println("RAMS "+ rams);
	        	System.out.println("Better RAMS "+ betterRams);
	        	return result;
	        		
	        }
			is.close();						
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public List<String> getMotherboards(){
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
	                + "SELECT  ?motherboard "
	                +"WHERE "
	                + "{ "
	                +"[]"
	                + " base:hasMotherboard "
	                +"?motherboard"
	                +" .} ";
	        Query query = QueryFactory.create(queryString);
	        System.out.println(query);
	        QueryExecution qexec = QueryExecutionFactory.create(query, model);
	        try {
	            ResultSet results = qexec.execSelect();
	            while (results.hasNext()) {
	                QuerySolution solution = results.nextSolution();
	                Resource r = solution.getResource("motherboard");
	                String motherboard = r.getLocalName();
	                System.out.println(motherboard);
	                result.add(motherboard);
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
	
	private String findPC(String motherboard) {
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?pc "
                +"WHERE "
                + "{"
                +"?pc"
                + " base:hasMotherboard "
                +"base:"
                + motherboard
                +" . } ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("pc");
                return r.getLocalName();  
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";				
	}
	
	private String findProcessor(String pc) {
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
                + "base:"
                + pc
                + " base:hasProcessor "
                + "?processor"
                +" . } ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("processor");
                return r.getLocalName();  
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";
		
	}
	
	private List<String> rams(String ramType, String currentRam) {
		List<String> rams = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?ram "
                +"WHERE "
                + "{"
                +"?ram"
                + " rdf:type "
                +"base:"
                + ramType
                +" .} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("ram");
                String ram = r.getLocalName();
                if(!ram.equals(currentRam)) {
                	rams.add(ram);    
                }
            }
            return rams;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return rams;	
	}
	
	private List<BetterRAMDTO> suggestBetterRams(List<String> rams) {
		List<BetterRAMDTO> betterRams = new ArrayList<>();
		
		for(String r: rams) {
			BetterRAMDTO ram = new BetterRAMDTO();
			int speed = ramSpeed(r);
			int capacity = ramCapacity(r);
			String layout = ramLayout(r);
			String manufacturer = ramManufacturer(r);
			String type = ramType(r);
			ram.setType(type);
			ram.setCapacity(capacity);
			ram.setSpeed(speed);
			ram.setManufacturer(manufacturer);
			ram.setLayout(layout);
			betterRams.add(ram);			
		}
		
        return betterRams;	
	}
	
	private List<String> betterRams(List<String> rams, int currentRamSpeed, int currentRamCapacity, int currentRamMaxCapacity, int currentRamSlots) {
		List<String> betterRams = new ArrayList<>();
		
		for(String r: rams) {
			
			int speed = ramSpeed(r);
			int capacity = ramCapacity(r);
			String layout = ramLayout(r);
			int usedLayout = Integer.parseInt(layout.split("x")[0]);
			if(speed > currentRamSpeed) {
				if(capacity >= currentRamCapacity) {
					if(capacity <= currentRamMaxCapacity) {
						if(usedLayout <= currentRamSlots) {
							betterRams.add(r);
						}
					}					
				}
			}else if(speed == currentRamSpeed){
				if(capacity > currentRamCapacity) {
					if(capacity <= currentRamMaxCapacity) {
						if(usedLayout <= currentRamSlots) {
							betterRams.add(r);
						}
					};
				}
			}
		}
		
        return betterRams;	
	}
	
	private String ramType(String ram) {		
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?ramType "
                +"WHERE "
                + "{"
                +"base:"
                + ram
                + " rdf:type "
                +"?ramType ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("ramType");
                String[] array = r.toString().split("#");
                if(array[1].startsWith("D")) {
                	return array[1];
                }
                return "";  
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	
	private String ramManufacturer(String ram) {		
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?ramManufacturer "
                +"WHERE "
                + "{"
                +"base:"
                + ram
                + " base:manufacturer "
                +"?ramManufacturer ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("ramManufacturer");
                return r.getLocalName();
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String ram(String motherboard) {		
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?ram "
                +"WHERE "
                + "{"
                +"base:"
                + motherboard
                + " base:hasRAM "
                +"?ram ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("ram");
                return r.getLocalName();   
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	
	private int ramSpeed(String ram) {		
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?ramSpeed "
                +"WHERE "
                + "{"
                +"base:"
                + ram
                + " base:hasSpeed "
                +"?ramSpeed ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("ramSpeed");
                String[] array = r.toString().split("#");
                String speed = array[1].split("_")[0];
                return Integer.parseInt(speed);   
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return 0;		
	}
	
	
	private String ramLayout(String ram) {		
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?ramLayout "
                +"WHERE "
                + "{"
                +"base:"
                + ram
                + " base:layout "
                +"?ramLayout ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("ramLayout");
                String[] array = r.toString().split("#");
                String layout = array[1].split("_")[0];
                return layout.replace("GB", "");   
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private int ramCapacity(String ram) {		
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?ramCapacity "
                +"WHERE "
                + "{"
                +"base:"
                + ram
                + " base:hasCapacity "
                +"?ramCapacity ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("ramCapacity");
                String[] array = r.toString().split("#");
                String capacity = array[1].split("_")[0];
                return Integer.parseInt(capacity);   
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return 0;		
	}

	
	private int ramSlots(String motherboard) {
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
	
	private int maxRamCapacity(String processor) {
	
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
