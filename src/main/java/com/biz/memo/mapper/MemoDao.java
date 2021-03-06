package com.biz.memo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.memo.model.FileVO;
import com.biz.memo.model.MemoVO;

public interface MemoDao {

	@Select("SELECT * FROM tbl_memo")
	@Results({
		  @Result(property="id",column="id"),//2
		  @Result(property="files",column="id", //4,7
				   javaType=List.class,
				   many=@Many(select="getFiles")) //3
})
	public List<MemoVO> selectAll();
	/*
	 * 1.tbl_memo에서 데이터를 select
	 * 2.tbl_memo 데이터 중에서 id 칼럼의 값을 내가 사용을 하겠다.
	 * 3.getFiles 메서드를 호출하는데 
	 * 4.매개변수(parent_id)로 전달을 하겠다.
	 * 5.getFiles는 parent_id를 기준으로 데이터를 select한 다음에
	 * 6.List<FileVO>로 return을 할 것이다
	 * 7.그 결과를 memoVO의 files변수에 받아라
	 */
	@Select("SELECT * FROM tbl_memo WHERE m_auth = #{m_userid}")
	@Results({
			  @Result(property="id",column="id"),//2
			  @Result(property="files",column="parent_id", //4,7
					   javaType=List.class,
					   many=@Many(select="getFiles")) //3
	})
	
	public List<MemoVO> selectByUserId(String m_userid); 
	
	@Select("SELECT * FROM tbl_files WHERE parent_id =#{parent_id}")
	public List<FileVO> getFiles(long parent_id); //5,6
	
	@Select("SELECT * FROM tbl_memo WHERE id =#{id}")
	@Results({
		  @Result(property="id",column="id"),//2
		  @Result(property="files",column="parent_id", //4,7
				   javaType=List.class,
				   many=@Many(select="getFiles")) //3
})
	public MemoVO findById(long id);
	
	/*
	 * InsertProvider 문장이 실행되기전에 (before가 true이므로)
	 * SELECT SEQ_MEMO.NEXTVAL FROM DUAL을 실행해서그 결과값을 
	 * id라는 변수에 담아라
	 * 이때 이 id라는 변수의 자료형은 Long형이다.(대문자써야함)
	 * 아주중요함 ********
	 */
	
	/*
	 * 	@SelectKey(statement="SELECT COUNT(*) + 1 FROM tbl_memo",
				keyProperty="id",
				before=true,
				resultType = Long.class)
				이렇게할수도 있지만 이렇게하면 가운데에 하나 빠지면 중복코드 발생
	 */
	@SelectKey(statement=" SELECT SEQ_MEMO.NEXTVAL FROM DUAL ",
				keyProperty = "id", before=true, resultType = Long.class)
	@InsertProvider(type=MemoSQL.class, method="memo_insert_sql")
	public int insert(MemoVO memoVO);
	
	@UpdateProvider(type=MemoSQL.class, method="memo_update_sql")
	public int update(MemoVO memoVO);
	
	@Delete("DELETE FROM tbl_memo WHERE id=#{id}")
	public void delete();
}
