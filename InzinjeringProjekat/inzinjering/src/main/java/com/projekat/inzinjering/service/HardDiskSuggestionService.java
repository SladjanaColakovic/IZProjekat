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

import com.projekat.inzinjering.dto.HardDiskDTO;
import com.projekat.inzinjering.dto.ProcessorDTO;

@Service
public class HardDiskSuggestionService {

Model model = ModelFactory.createDefaultModel();
	
	public List<HardDiskDTO> getCompatibleHDD(String motherboard, String computer){
		
		List<HardDiskDTO> compatibleHardDisks = new ArrayList<HardDiskDTO>();
		try {
			InputStream is = TypeReference.class.getResourceAsStream("/ontologija.owl");
	        RDFDataMgr.read(model,is,Lang.TURTLE); 
			List<String> sata = findSata3(motherboard);
			boolean sata3 = hasSata3(sata);
			List<String> m2 = findM2Slots(motherboard);
			System.out.println(m2);
			if(sata3) {
				compatibleHardDisks.addAll(findSataCompatibleHD(sata3, computer));
			}
			if(!m2.isEmpty()) {
				compatibleHardDisks.addAll(findM2CompatibleHD(m2));
			}
			return compatibleHardDisks;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return compatibleHardDisks;
		
	}
	
	
	private List<HardDiskDTO> findM2CompatibleHD(List<String> m2) {
		
		System.out.println("S S D M 2");
		List<HardDiskDTO> compatibleHD = new ArrayList<HardDiskDTO>();
		List<String> m2ssds = findM2SSD();
		System.out.println(m2ssds);
		
		for(String m : m2) {
			List<String> pcies = findPCIe(m);     // pcie1 pcie1.1
			System.out.println(pcies);
			for(String pcie : pcies) {
				int version = pcieVersion(pcie);   // 3 4
				System.out.println(version);
				int lanes = pcieLanes(pcie);       // 4
				System.out.println(lanes);
				
				for(String m2ssd : m2ssds) {
					String m2interface = ssdInterface(m2ssd);
					int ssdversion = pcieVersion(m2interface);
					System.out.println(ssdversion);
					int ssdlanes = pcieLanes(m2interface);
					System.out.println(ssdlanes);
					
					if(version == ssdversion && lanes == ssdlanes) {
						System.out.println(" JFDSNN DSKFSD");
						HardDiskDTO hardDisk = new HardDiskDTO();
						String format = formFactor(m2ssd);
						String hdd_interface = ssdInterface(m2ssd);
						String capacity = ssdCapacity(m2ssd);
						String speed = ssdSpeed(m2ssd);
						boolean nvme = hasNVMe(m2ssd);
						hardDisk.setFormat(format);
						hardDisk.setHdd_interface("PCIe" + version + ".0" + "x" + lanes);
						hardDisk.setCapacity(capacity.replace("_", " "));
						hardDisk.setSpeed(speed.replace("_", " "));
						hardDisk.setHasNvme(nvme);
						hardDisk.setType("SSD");
						compatibleHD.add(hardDisk);
						for(HardDiskDTO c : compatibleHD) {
							System.out.println(c.getFormat() + " " + c.getCapacity() + " " + c.getSpeed());
						}
					}
				}
			}
		
			
		}
		
		return compatibleHD;
	}
	
	private boolean hasSata3(List<String> sata) {
		
		if(!sata.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	private List<HardDiskDTO> findSataCompatibleHD(boolean sata3, String computer) {
		
		List<HardDiskDTO> compatibleHD = new ArrayList<>();
		
		if(sata3) {
			List<String> mSata = findmSATA();
			System.out.println(mSata);
			System.out.println("S a t a ");
			if(!mSata.isEmpty()) {
				for(String ms : mSata) {
					HardDiskDTO hardDisk = new HardDiskDTO();
					String format = formFactor(ms);
					String hdd_interface = ssdInterface(ms);
					String capacity = ssdCapacity(ms);
					String speed = ssdSpeed(ms);
					boolean nvme = hasNVMe(ms);
					hardDisk.setFormat(format);
					hardDisk.setHdd_interface(hdd_interface.replace("_", " "));
					hardDisk.setCapacity(capacity.replace("_", " "));
					hardDisk.setSpeed(speed.replace("_", " "));
					hardDisk.setHasNvme(nvme);
					hardDisk.setType("SSD");
					compatibleHD.add(hardDisk);
					for(HardDiskDTO c : compatibleHD) {
						System.out.println(c.getFormat() + " " + c.getCapacity() + " " + c.getSpeed());
					}
				}
			}
			
			if(computer.equals("PC")) {
				List<String> hdds = findHDDs();
				System.out.println("S A T A  PC ");
				if(!hdds.isEmpty()) {
					for(String hdd : hdds) {
						HardDiskDTO hardDisk = new HardDiskDTO();
						String format = format(hdd);
						String hdd_interface = hddInterface(hdd);
						String capacity = hddCapacity(hdd);
						String speed = hddSpeed(hdd);
						boolean nvme = hasNVMe(hdd);
						hardDisk.setFormat(format);
						hardDisk.setHdd_interface(hdd_interface.replace("_", " "));
						hardDisk.setCapacity(capacity.replace("_", " "));
						hardDisk.setSpeed(speed.replace("_", " "));
						hardDisk.setHasNvme(nvme);
						hardDisk.setType("HDD");
						compatibleHD.add(hardDisk);
						for(HardDiskDTO c : compatibleHD) {
							System.out.println(c.getFormat() + " " + c.getCapacity() + " " + c.getSpeed());
						}
					}
				} else {
					List<String> hdds25 = findHDDs();
					
					if(!hdds25.isEmpty()) {
						for(String hdd : hdds25) {
							HardDiskDTO hardDisk = new HardDiskDTO();
							String format = format(hdd);
							String hdd_interface = hddInterface(hdd);
							String capacity = hddCapacity(hdd);
							String speed = hddSpeed(hdd);
							boolean nvme = hasNVMe(hdd);
							hardDisk.setFormat(format);
							hardDisk.setHdd_interface(hdd_interface.replace("_", " "));
							hardDisk.setCapacity(capacity);
							hardDisk.setSpeed(speed);
							hardDisk.setHasNvme(nvme);
							hardDisk.setType("HDD");
							compatibleHD.add(hardDisk);
							for(HardDiskDTO c : compatibleHD) {
								System.out.println(c.getFormat() + " " + c.getCapacity() + " " + c.getSpeed());
							}
						}
					}
				}
			}
		}
		
		return compatibleHD;
		
	}
	
	private boolean hasNVMe(String hd) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?hd "
                +"WHERE "
                + "{"
                +"base:"
                + hd
                + " base:hasNVMeProtocol "
                +"?hd ."
                +" } ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Literal l = solution.getLiteral("hd");
                return l.getBoolean();
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return false;		
	}
	
	private String hddSpeed(String hdd) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?speed "
                +"WHERE "
                + "{"
                +"base:"
                + hdd
                + " base:hasHDDSpeed "
                +"?speed . "
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("speed");
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
	
	
	private String ssdSpeed(String ssd) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?speed "
                +"WHERE "
                + "{"
                +"base:"
                + ssd
                + " base:hasSSDSpeed "
                +"?speed . "
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("speed");
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
	
	private String ssdCapacity(String ssd) {
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
                + ssd
                + " base:hasSSDCapacity "
                +"?capacity . "
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("capacity");
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
	
	private String hddCapacity(String hdd) {
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
                + hdd
                + " base:hasHDDCapacity "
                +"?capacity . "
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("capacity");
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
	
	private String ssdInterface(String ssd) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?interface "
                +"WHERE "
                + "{"
                +"base:"
                + ssd
                + " base:hasSSDInterface "
                +"?interface . "
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("interface");
                return r.getLocalName();
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String hddInterface(String hdd) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?interface "
                +"WHERE "
                + "{"
                +"base:"
                + hdd
                + " base:hasHDDInterface "
                +"?interface . "
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("interface");
                return r.getLocalName();
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String formFactor(String ssd) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?formFactor "
                +"WHERE "
                + "{"
                +"base:"
                + ssd
                + " base:hasFormFactor "
                +"?formFactor . "
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("formFactor");
                return r.getLocalName();
                
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return "";		
	}
	
	private String format(String hdd) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?format "
                +"WHERE "
                + "{"
                +"base:"
                + hdd
                + " base:hasHDDSize "
                +"?format . "
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("format");
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
	
	private List<String> findPCIeSlots(String motherboard) {
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
                +"base:"
                + motherboard
                + " base:hasPCIe "
                +"?pcie"
                +" .} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("pcie");
                String pcie = r.getLocalName();
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
	
	private List<String> findSata3(String motherboard) {
		List<String> slots = new ArrayList<>();
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
                +"base:"
                + motherboard
                + " base:hasSATA "
                +"?slot"
                +" ."
                + " ?slot"
                + " rdf:type"
                + " base:SATA_III "
                + ". } ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("slot");
                String slot = r.getLocalName();
                slots.add(slot);    
                
            }
            return slots;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return slots;	
	}
	
	private List<String> findM2Slots(String motherboard) {
		List<String> slots = new ArrayList<>();
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
                +"base:"
                + motherboard
                + " base:hasM.2Slot "
                +"?slot"
                +" .} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("slot");
                String slot = r.getLocalName();
                slots.add(slot);    
                
            }
            return slots;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return slots;	
	}
	
	private List<String> findM2SSD() {
		List<String> ssds = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?ssd "
                +"WHERE "
                + "{"
                +" ?ssd"
                + " base:hasFormFactor "
                +" base:M.2"
                +" . } ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("ssd");
                String ssd = r.getLocalName();
                ssds.add(ssd);    
            }
            return ssds;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return ssds;	
	}
	
	private int pcieVersion(String pcie) {
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
                + " base:PCIeVersion "
                +"?version ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Literal l = solution.getLiteral("version");
                return l.getInt();
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return 0;		
	}
	
	private int pcieLanes(String pcie) {
        String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?lanes "
                +"WHERE "
                + "{"
                +"base:"
                + pcie
                + " base:PCIeLanes "
                +"?lanes ."
                +"} ";
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Literal l = solution.getLiteral("lanes");
                return l.getInt();
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return 0;		
	}
	
	private List<String> findPCIe(String m2) {
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
                +"base:"
                + m2
                + " base:supports "
                +"?pcie"
                +" . "
                + "?pcie "
                + "rdf:type "
                + "base:PCIe_ssd"
                + " . } "
                ;
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("pcie");
                String pcie = r.getLocalName();
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
	
	private List<String> findSSDs() {
		List<String> ssds = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?ssd "
                +"WHERE "
                + "{"
                +" ?ssd "
                + "rdf:type "
                + "base:SSD"
                + " . } "
                ;
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("ssd");
                String ssd = r.getLocalName();
                ssds.add(ssd);    
            }
            return ssds;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return ssds;	
	}
	
	private List<String> findmSATA() {
		List<String> mSatas = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?mSata "
                +"WHERE "
                + "{"
                +" ?mSata "
                + "base:hasFormFactor "
                + "base:mSATA"
                + " . } "
                ;
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("mSata");
                String mSata = r.getLocalName();
                mSatas.add(mSata);    
            }
            return mSatas;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return mSatas;	
	}
	
	private List<String> findHDDs() {
		List<String> hdds = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?hdd "
                +"WHERE "
                + "{"
                +" ?hdd "
                + "rdf:type "
                + "base:HDD"
                + " . } "
                ;
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("hdd");
                String hdd = r.getLocalName();
                hdds.add(hdd);    
                
            }
            return hdds;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return hdds;	
	}
	
	private List<String> find25HDDs() {
		List<String> hdds = new ArrayList<>();
		String queryString = 
        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
        		+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX base: <http://www.semanticweb.org/hp/ontologies/2022/3/untitled-ontology-7#>"
                + "PREFIX iz: <https://raw.githubusercontent.com/SladjanaColakovic/IZProjekat/instance-s/ontologija_instance.owl#>"
                + "SELECT ?hdd "
                +"WHERE "
                + "{"
                +" ?hdd "
                + "rdf:type "
                + "base:HDD"
                + " . "
                + " ?hdd"
                + " base:hasHDDSize"
                + " base:2.5inch "
                + ". } "
                ;
        Query query = QueryFactory.create(queryString);
        System.out.println(query);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource r = solution.getResource("hdd");
                String hdd = r.getLocalName();
                hdds.add(hdd);    
                
            }
            return hdds;
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            qexec.close();
        }       
        return hdds;	
	}
	
}
