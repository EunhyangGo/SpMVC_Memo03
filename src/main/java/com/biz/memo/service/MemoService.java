package com.biz.memo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.memo.mapper.MemoDao;
import com.biz.memo.model.MemoVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemoService {

	@Autowired
	MemoDao dao;
	
	public int insert(MemoVO memoVO) {
		// TODO Auto-generated method stub
		
		int ret = dao.insert(memoVO);
		return ret;
	}
	public List<MemoVO> selectAll() {
		// TODO Auto-generated method stub
		
		return dao.selectAll();
		
	}
	/*
	 * memoVO의 아이디값을 검사해서
	 * 0보다 크면 수정(Update)그렇지않으면 추가(Insert>_
	 */
	public int save(MemoVO memoVO) {
		// TODO Auto-generated method stub
		
		int ret = 0;
		if(memoVO.getId() > 0) {
			ret = dao.update(memoVO);
		}else {
			log.debug("Before Insert MemoId : " + memoVO.getId());
			ret = dao.insert(memoVO);
			log.debug("After Insert MemoID :" + memoVO.getId());
		}
		
		return 0;
	}
	public List<MemoVO> selectByUserId(String userid) {
		// TODO Auto-generated method stub
		return dao.selectByUserId(userid);
	}
	
	public MemoVO findById(long id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	
	
}
