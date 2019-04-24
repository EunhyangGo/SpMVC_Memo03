package com.biz.memo.service;

import java.io.File;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.biz.memo.mapper.FileDao;
import com.biz.memo.mapper.MemoDao;
import com.biz.memo.model.FileVO;
import com.biz.memo.model.MemoVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {

	/*
	 * ServletContext는 Tomcat이 실행되는 환경에서 다양한 
	 * 정보를 가지고 있는 ㄴ매우 중요한 클래스
	 * CONTEXT로부터 서버에 대한 정보를 추출해 볼 수 있다.
	 */
	@Autowired
	ServletContext context;
	
	@Autowired
	MemoDao mDao;
	
	@Autowired
	FileDao fDao;
	
	public int fileSave(String fileName) {
		
		return 0;
	}
	
	/*
	 * @Transactional Annotation을 사용하여
	 * tbl_memo와 tbl_file table에 insert update를 보장하자
	 * 이 Annotation을 사용하기 위해서
	 * mybatis-context.xml에 transaction설정을 해두어야 한다.
	 */
	@Transactional
	public String fileUpload(MemoVO memoVO, MultipartFile m_file) {
		//업로드할 원래 파일 이름을 추출
		String fileName = m_file.getOriginalFilename();
		String saveFileName ="";
		
		// 루트의 실제위치가 무엇인지 묻는 것.
		// tomcat server가 실행해서 보여주는 context root가 어디냐
		String realPath = context.getRealPath("/");
		
		// http://localhost:8080/memo03/file/ 문자
		String upLoadPath = realPath + "files/";
		log.debug("realPath: "+ realPath);
		log.debug("upLoadPath:" + upLoadPath);
		
		// 만약 파일이 업로드 되지 않은 상태에서 업로드등을 수행하던 ㅁ낳은 오류가 발생하고
		// 실제 환경에서는 보안에 매우 위험할 수 있다
		// 다양한 방법으로  안전하게 업로드가 되는지 확인 해야 한다.
		if(m_file != null) {
			// servlet-cnrkgkrl
			//혹시 지정된 크기보다 큰 파일이 업로드 되면
			// 무시해버리자
			if(m_file.getSize() > 1048576) {
				return null;			}
			// 파일 이름을 검사해서 null이 아니고, ""<도 아닌 경우만
			// 처리하겠다
			if(fileName != null && !fileName.equals("")) {
				/* 
				 * UUID=universially uique identified
				 * 범용 유일 식별자
				 * 유일한 단독 키값
				 * 16진수 32자리의 임의 키 값을 생성
				 * 16의 32승만큼 임의 값 중 하나 생성
				 * uuid키값을 붙여서 파일을 랜덤으로 만들겠다
				 * 
				 * 오리지널 파일 이름으로 업로드를 하면 혹시 악의적인 사용자에 의해서
				 * 파일이 변조될 수 있으므로 
				 * 업로드할때는 임의 키값을 생성해서 업로드를 실행한다.
				 * 
				 * UUID는 자바5(1.5)부터 사용가능하고
				 * 그전에는 timestamp
				 */
				saveFileName = UUID.randomUUID().toString() + fileName ;
				
				// 파일을 업로드할 폴더가 만들어 있는지 검사하여 없으면 생성을 하도록
				File dir = new File(upLoadPath);
				if(!dir.exists()) { //폴더가 자동으로 생성되지 않았으면
					dir.mkdir() ; //폴더를 만들어라
				}
				// 운영체제 독립적인 구조의 파일 이름 체계를 생성
				File upLoadFile = new File(upLoadPath, saveFileName);
			
				try {
					// nio 패키지의 파일(1.7이상) 클래스의 도움을 받아 
					// 사용자 컴퓨터에서 Tomcat서버의 디렉토리로 파일을 한번 전송할 수 있게 되었다.
					m_file.transferTo(upLoadFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			
				}//try
			}//if
			// tbl_files 테이블에 파일 정보를 저장할 예정
			if(memoVO.getId()> 0)
				 mDao.update(memoVO);
			else
				mDao.insert(memoVO);
			// Builder pattern
			// fileVO 객체를 생성하면서 
			// 각 속성(필드, 맴버변수)의 값을 세팅하고자 할 때
			// 사용하는 방법
			FileVO fileVO = FileVO.builder()
						.parent_id(memoVO.getId())
						.file_name(fileName)
						.save_file_name(saveFileName).build();
			/*
			fileVO = new FileVO();
			fileVO.setParent_id(memoVO.getId());
			
			// 생성자에 직접 속성값을 전달하면서 객체를 생성하는 생성자 pattern
			fileVO = new FileVO(0L, memoVO.getId(), fileName, saveFileName);
			*/
			

			fDao.insert(fileVO);
			return saveFileName;
		}//if
		
		return null;
	}//end fileUpLoad

	public int delete(long id) {
		
		 return fDao.delete(id);
	}
}// end class
