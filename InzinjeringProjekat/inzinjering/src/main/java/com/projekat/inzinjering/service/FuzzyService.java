package com.projekat.inzinjering.service;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projekat.inzinjering.dto.FuzzyDTO;
import com.projekat.inzinjering.dto.ResponseFuzzyDTO;
import java.lang.Math;

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
		boolean result;
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
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return false;		
	}
	
	public List<ResponseFuzzyDTO> fuzzyLogic(FuzzyDTO fuzzyDTO) {
		List<ResponseFuzzyDTO> result = new ArrayList<>();
		int cores = processorService.processorCores(fuzzyDTO.getProcessor());
		System.out.println(cores);
		double frequency = Double.parseDouble(processorService.processorFrequency(fuzzyDTO.getProcessor()).split(" ")[0]);
		System.out.println(frequency);
		boolean isDicreteGC = isDiscreteGC(fuzzyDTO.getGC());
		System.out.println(isDicreteGC);
		int gcMemoryCapacity = 0;
		if(isDicreteGC) {
			String gc = GCService.gcMemory(fuzzyDTO.getGC());
			System.out.println(gc);
			gcMemoryCapacity = Integer.parseInt(gc.split("_")[0]);
			System.out.println(gcMemoryCapacity);
		}
		
		System.out.println(fuzzyDTO.getRAM());
		int ramCapacity = Integer.parseInt(fuzzyDTO.getRAM().split(" ")[1]);
		int hdCapacity = Integer.parseInt(fuzzyDTO.getHD().split(" ")[1]);
		
		List<String> usages = new ArrayList<>();
		usages.add("home");
		usages.add("gaming");
		usages.add("video_editing");
		usages.add("crypto_mining");
		usages.add("business");
		System.out.println(gcMemoryCapacity);
		String path;
		
		try {
			path = TypeReference.class.getResource("/fuzzy.fcl").toURI().getPath();
			
			FIS fis = FIS.load(path, true);

	        if (fis == null) {
	            System.err.println("Can't load file");
	            System.exit(1);
	        }

	        fis.setVariable("cores", cores);
	        fis.setVariable("frequency", frequency);
	        fis.setVariable("gcMemory", gcMemoryCapacity);
	        fis.setVariable("ram", ramCapacity);
	        fis.setVariable("hd", hdCapacity);
	        
	        fis.evaluate();

	        for(String usage: usages) {
	        	 Variable analise = fis.getFunctionBlock("comparing").getVariable(usage);
	        	 ResponseFuzzyDTO r = new ResponseFuzzyDTO();
	        	 
	        	 r.setUsage(usage.replace("_", " "));
	        	 long factor = (long) Math.pow(10, 2);
	        	 double value = analise.getValue() * factor;
	        	 long tmp = Math.round(value);
	        	 r.setFuzzyValue((double) tmp / factor);
	        	 result.add(r);
	        	
	        }
	        return result;
	        
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return result;
	}
	
	

}
