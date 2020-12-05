package com.nemtool.explorer.service.impl;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nemtool.explorer.mapper.BlocksMapper;
import com.nemtool.explorer.pojo.Blocks;
import com.nemtool.explorer.pojo.BlocksExample;
import com.nemtool.explorer.service.BlocksService;
import com.nemtool.explorer.service.NisService;

/**
 * @Description: block service impl
 * @author Masker
 * @date 2020.06.28
 */
@Transactional
@Service
public class BlocksServiceImpl implements BlocksService{

	@Autowired
	private BlocksMapper blocksMapper;
	
	@Autowired
	private NisService nisService;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;

	
	@Override
	public void add(Blocks block) {
		blocksMapper.insert(block);
	}

	@Override
	public void delete(int[] heights) {
		for (int height : heights) {
			BlocksExample blocksExample = new BlocksExample();
			blocksExample.createCriteria().andHeightEqualTo(height);
			blocksMapper.deleteByExample(blocksExample);
		}
	}

	@Override
	public List<Blocks> findAll() {
		return blocksMapper.selectByExample(null);
	}

	@Override
	public void update(Blocks block) {
		BlocksExample blocksExample = new BlocksExample();
		blocksExample.createCriteria().andHeightEqualTo(block.getHeight());
		blocksMapper.updateByExample(block, blocksExample);
	}

	@Override
	public Blocks findByHeight(int height) {
		BlocksExample blocksExample = new BlocksExample();
		blocksExample.createCriteria().andHeightEqualTo(height);
		List<Blocks> selectByExample = blocksMapper.selectByExample(blocksExample);
		Blocks block = null;
		for (Blocks blocks : selectByExample) {
			block = blocks;
		}
		return block;
	}

	@Override
	public List<Blocks> latest10Blcoks() {
		return blocksMapper.latest10Blocks();
	}

	@Override
	public void insertList(List<Blocks> blocksList) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		BlocksMapper mapper = session.getMapper(BlocksMapper.class);
		int count=0;
		for (int i = 0; i < blocksList.size(); i++) {
			mapper.insert(blocksList.get(i));
			count++;
			if(count%1000==999) {
				session.commit();
				session.clearCache();
			}
		}
		session.commit();
		session.clearCache();
		session.close();
	}

	@Override
	public JSONArray blockListFromNis(int height) {
		String reqData = "{\"height\":" + height + "}";
		String blockList = nisService.localChainBlocksAfter(reqData);
		if(blockList==null || "".equals(blockList.trim())){
			System.out.println("fail to get the blocks ["+(height+1)+" - "+(height+11)+"]");
			return null;
		}
		JSONObject partChain = JSON.parseObject(blockList);
		if(partChain==null){
			System.out.println("fail to get the blocks ["+(height+1)+" - "+(height+11)+"]");
			return null;
		}
		JSONArray blockArr = partChain.getJSONArray("data");
		if(blockArr==null || blockArr.size()==0){
			return null;
		}
		return blockArr;
	}

	@Override
	public int maxBlockHeightFromNis() {
		String blockHeight = nisService.blockHeight();
		if(blockHeight==null || "".equals(blockHeight.trim())){
			System.out.println("fail to get current height from NIS!");
			return -1;
		}
		JSONObject heightJSON = JSON.parseObject(blockHeight);
		if(heightJSON==null){
			System.out.println("fail to get current height from NIS!");
			return -1;
		}
		return heightJSON.getIntValue("height");
	}

	@Override
	public Blocks maxBlocksFromDB() {
		return blocksMapper.maxBlock();
	}

	@Override
	public Blocks minBlocksFromDB() {
		List<Blocks> minBlockList;
		try {
			minBlockList = blocksMapper.minBlock();
			System.out.println("min:==========" + minBlockList.toString());
		} catch (Exception e) {
			return null;
		}
		if(minBlockList.size() <2) {
			return null;
		}
		
		return minBlockList.get(1);
	}

	@Override
	public JSONObject blockAtPublic(int height) {
		String reqData = "{\"height\":" + height + "}";
		String blockAtPublic = nisService.blockAtPublic(reqData);
		if(blockAtPublic==null || "".equals(blockAtPublic.trim())){
			System.out.println("fail to get the Nemesis block data!");
			return null;
		}
		JSONObject block = JSON.parseObject(blockAtPublic);
		if(block==null){
			System.out.println("fail to get the Nemesis block data!");
			return null;
		}
		return block;
	}

}
