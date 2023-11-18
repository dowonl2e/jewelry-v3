package com.jewelry.common.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {
	
	/** 현재 페이지 */
	private Integer currentPage;
	
	/** 페이지당 출력할 데이터 개수 */
	private Integer recordCount;
	
	/** 화면 하단에 출력할 페이지 사이즈 */
	private int pageSize;
	
	/** 전체 데이터 개수 */
	private Long totalCount;

	/** 전체 페이지 개수 */
	private Integer totalPage;

	/** 페이지 리스트의 첫 페이지 번호 */
	private Integer firstPage;

	/** 페이지 리스트의 마지막 페이지 번호 */
	private Integer lastPage;

	/** SQL의 조건절에 사용되는 첫 RNUM */
	private int firstrecordindex;

	/** SQL의 조건절에 사용되는 마지막 RNUM */
	private int lastrecordindex;

	/** 이전 페이지 존재 여부 */
	private Boolean hasPrev;

	/** 다음 페이지 존재 여부 */
	private Boolean hasNext;
	
	private Integer startPage;
	private Integer endPage;
	
	public Pagination() {
		this.currentPage = 1;
		this.recordCount = 20;
		this.pageSize = 10;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		calculation();
	}

	private void calculation() {
		/* 페이지 리스트의 첫 페이지 번호 */
		firstPage = ((currentPage - 1) / pageSize) * pageSize + 1;

		/* 페이지 리스트의 마지막 페이지 번호 (마지막 페이지가 전체 페이지 수보다 크면 마지막 페이지에 전체 페이지 수를 저장) */
		lastPage = firstPage + pageSize - 1;
		if (lastPage > totalPage) {
			lastPage = totalPage;
		}

		//시작 페이지 인덱스
		this.startPage = (int)(currentPage%pageSize == 0 ? currentPage-1 : currentPage)/pageSize * pageSize;

		//종료 페이지 인덱스
		this.endPage = startPage + pageSize;
		this.endPage = endPage > totalPage ? totalPage : endPage;
	}

	
}
