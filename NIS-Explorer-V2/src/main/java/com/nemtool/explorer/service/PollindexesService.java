package com.nemtool.explorer.service;

import java.util.List;

import com.nemtool.explorer.pojo.Pollindexes;

/**
*
* @author Masker
* @date 2020.10.20
*/
public interface PollindexesService {
	
	public String pollList();
	
	public String pollDetail(String address);
	
	public String pollResult(String address);
	
	public String pollResultVoters(String address, String strings);
	
	public void savePollindex(Pollindexes pollindexes);
	
	public boolean checkIfExist(String address);
	
	public List<Pollindexes> findAllPollindexes();
	
	public Pollindexes findByAddress(String address);
	
	public Pollindexes findById(int id);
	
	public List<Pollindexes> findPages(int page, int pageSize);

}
