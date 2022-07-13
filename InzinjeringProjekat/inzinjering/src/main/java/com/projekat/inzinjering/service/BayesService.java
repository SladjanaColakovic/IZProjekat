package com.projekat.inzinjering.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.asm.TypeReference;
import org.springframework.stereotype.Service;

import com.projekat.inzinjering.dto.BayesDTO;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.io.exception.LoadException;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

@Service
public class BayesService {

	public List<BayesDTO> bayes(String malfunction) {
		String path;
		List<BayesDTO> dtoList = new ArrayList<>(); 
		try {
			path = TypeReference.class.getResource("/" + malfunction + ".net").toURI().getPath();
			BaseIO io = new NetIO();
			try {
				ProbabilisticNetwork net = (ProbabilisticNetwork)io.load(new File(path));
				IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();
		        algorithm.setNetwork(net);
		        algorithm.run();
		        var malfunctionNode = (ProbabilisticNode)net.getNode(malfunction);
		        malfunctionNode.addFinding(0);
		        try {
		            net.updateEvidences();
		        } catch (Exception e) {
		            System.out.println(e.getMessage());                   
		        }
		        List<Node> nodeList = net.getNodes();
		        for (Node node: nodeList) {
		            System.out.println(node.getName());
		            for (int i = 0; i < node.getStatesSize(); i++) {
		                System.out.println(node.getStateAt(i) + ": " + ((ProbabilisticNode)node).getMarginalAt(i));
		            }
		        }
		        List<BayesDTO> tempList = new ArrayList<>();
		        for (Node node : nodeList) {
		            System.out.println(node.getName());
		            BayesDTO dto = new BayesDTO();   
		            dto.setMalfunction(node.getName().replace("_", " "));
		            dto.setPercentage(((ProbabilisticNode)node).getMarginalAt(0));
		            tempList.add(dto);
		        }
		        
		        double sum = 0;
		        
		        for(BayesDTO dto : tempList) {
		        	sum += dto.getPercentage();
		        }
		        
		        for(BayesDTO dto : tempList) {
			        double percentage = dto.getPercentage()/sum;
		            long factor = (long) Math.pow(10, 2);
		        	double value = percentage * factor * 100;
		        	long tmp = Math.round(value);
		            dto.setPercentage((double) tmp / factor);
		            dtoList.add(dto);
		        }
		        return dtoList;
			} catch (LoadException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		return dtoList;
	}
	
}
