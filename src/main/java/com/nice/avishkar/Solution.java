package com.nice.avishkar;

import java.nio.file.Path;
import com.nice.avishkar.CandidateVotes;
import com.nice.avishkar.ConstituencyResult;
import com.nice.avishkar.ElectionResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution {

	public static List<ConstituencyResult> constituencyResultList= new ArrayList<>();



	public static void ReadCsv(String filePath){
		String line = "";

		Map<String, Map<String, Integer>> mapOfmap = new HashMap<>();
		Map<String, Integer> mp = new HashMap<>();


		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			line = bufferedReader.readLine();

			while((line = bufferedReader.readLine()) != null){
				String[] data = line.split(",");

				String key = data[3];
				Integer value = mp.getOrDefault(key, 0);

				mp.put(key, Integer.valueOf(value.intValue()+1));

				mapOfmap.put(data[1], mp);
			}
		}catch(IOException e){

		}

		for (Map.Entry<String, Integer> entry : mp.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println(key + " = " + value);
		}



		for (Map.Entry<String, Map<String, Integer>> entry : mapOfmap.entrySet()) {
			String winner="";
			Integer votes=0;
			String constituency = entry.getKey();
			Map<String, Integer> innerMap = entry.getValue();
			List<CandidateVotes> candidateVotesList= new ArrayList<>();
			for (Map.Entry<String, Integer> e : innerMap.entrySet()) {
				if(votes<=e.getValue()){
					if(!e.getKey().equals("NOTA")){
						if(votes==e.getValue()){
							winner = "NO_WINNER";
						}
						votes = e.getValue();
						winner = e.getKey();
					}
				}
				CandidateVotes candidates = new CandidateVotes(e.getKey(), e.getValue());
				candidateVotesList.add(candidates);
			}

			ConstituencyResult constituencyResult = new ConstituencyResult(constituency, winner, candidateVotesList);
			constituencyResultList.add(constituencyResult);

			candidateVotesList.clear();
		}

	}

	public ElectionResult execute(Path candidateFile, Path votingFile) {
		ReadCsv("../resources/votingFile.csv");
		ElectionResult resultData = new ElectionResult(constituencyResultList);

		// Write code here to read CSV files and process them

		return resultData;
	}
}


