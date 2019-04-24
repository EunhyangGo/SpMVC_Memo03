package com.biz.memo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileVO {

	private long id;
	private long parent_id; //tbl_memo와 FK관계
	private String file_name;
	private String save_file_name;

}
