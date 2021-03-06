package connector;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

import model.CaseDescription;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;
import ucm.gaia.jcolibri.util.FileIO;

public class CsvConnector implements Connector {
	
	@Override
	public Collection<CBRCase> retrieveAllCases() {
		LinkedList<CBRCase> cases = new LinkedList<CBRCase>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(FileIO.openFile("/computers.csv")));
			if (br == null)
				throw new Exception("Error opening file");

			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#") || (line.length() == 0))
					continue;
				String[] values = line.split(";");

				CBRCase cbrCase = new CBRCase();

				CaseDescription caseDescription = new CaseDescription();
		
				caseDescription.setProcessorBrand(values[0]);
				caseDescription.setProcessorName(values[1]);
				caseDescription.setCores(Integer.parseInt(values[2]));
				caseDescription.setProcessorFrequency(Double.parseDouble(values[3]));
				caseDescription.setRam(values[4]);
				caseDescription.setRamCapacity(Integer.parseInt(values[5]));
				caseDescription.setHardDisc(values[6]);
				caseDescription.setHardDiscCapacity(Integer.parseInt(values[7]));
				caseDescription.setGraphicsType(values[8]);
				caseDescription.setGraphicsCard(values[9]);
				caseDescription.setChipset(values[10]);
				
				cbrCase.setDescription(caseDescription);
				cases.add(cbrCase);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cases;
	}

	@Override
	public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter arg0) {
		return null;
	}

	@Override
	public void storeCases(Collection<CBRCase> arg0) {
	}
	
	@Override
	public void close() {
	}

	@Override
	public void deleteCases(Collection<CBRCase> arg0) {
	}

	@Override
	public void initFromXMLfile(URL arg0) throws InitializingException {
	}

}