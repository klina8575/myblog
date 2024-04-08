package com.example.myblog.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Setter
@Getter
@Service
public class PagingUtil {
    private int dataCount; //총 게시물의 갯수
    private int numPerPage; //페이지당 보여줄 게시물의 갯수
    private int totalPage; //페이지의 전체 갯수
    private int currentPage = 1; //현재 페이지(디폴트는 첫번째 페이지를 보여주므로 1)
    private int start; // rownum의 시작값
    private int end; // rownum의 끝값

    public void resetPaging(int dataCount, int numPerPage) {
        this.dataCount = dataCount;
        this.numPerPage = numPerPage;
        this.totalPage = getPageCount();

        //현재 페이지가 totalPage보다 큰 경우는 있을 수 없으므로..
        if(this.currentPage > this.totalPage) this.currentPage = this.totalPage;

        this.start = (this.currentPage - 1) * numPerPage + 1; // 1 6 11 16..
        this.end = this.currentPage * numPerPage; // 5 10 15 20..
    }

    //전체 페이지 갯수 구하는 메소드
    public int getPageCount() {
        int pageCount = 0;
        pageCount = dataCount / numPerPage;

        if(dataCount % numPerPage != 0) pageCount++;

        return pageCount; //전체 페이지 갯수
    }

    //페이징 버튼을 만들어주는 메소드
    public String pageIndexList(String listUrl) {
        //문자열 데이터를 자주 추가하거나 삭제할때 메모리 낭비 방지를 위해 사용
        StringBuffer sb = new StringBuffer();

        int numPerBlock = 5;  // ◀이전 6 7 8 9 10 다음▶ 이전과 다음 사이의 숫자를 몇개로 표시할건지
        int currentPageSetup; // ◀이전 버튼에 들어갈 pageNum값
        int page; // 그냥 페이지 숫자를 클릭 했을때 들어갈 pageNum값

        if(currentPage == 0 || totalPage == 0) return ""; //데이터가 없을때

        // listUrl의 값: /list?searchKey=subject&searchValue=네번째
        if(listUrl.indexOf("?") != -1) { //쿼리스트링이 있을때(검색어가 있을때)
            listUrl += "&";
            // 검색어가 있을때: /list?searchKey=subject&searchValue=네번째&
        } else { //쿼리스트링이 없을때(검색어가 없을때) /list
            listUrl += "?";
            // 검색어가 없을때: /list?
        }


        //int numPerBlock = 5;  // ◀이전 6 7 8 9 10 다음▶ 이전과 다음 사이의 숫자를 몇개로 표시할건지
        //int currentPageSetup; //pageNum

        //1. ◀이전 버튼 만들기
        //currentPage=1, currentPageSetup=0
        //currentPage=2, currentPageSetup=0
        //currentPage=3, currentPageSetup=0
        // ...
        //currentPage=6, currentPageSetup=5
        //currentPage=11, currentPageSetup=10
        //currentPage=16, currentPageSetup=15
        currentPageSetup = (currentPage / numPerBlock) * numPerBlock;

        if(currentPage % numPerBlock == 0) {
            currentPageSetup = currentPageSetup - numPerBlock;
        }

        // 검색어가 있을때: /list?searchKey=subject&searchValue=네번째&pageNum=1
        // 검색어가 없을때: /list?pageNum=1

        //totalPage > numPerBlock: numPerBlock보다 전체 페이지가 많을때 이전버튼이 생겨야 하므로
        if(totalPage > numPerBlock && currentPageSetup > 0) {
            sb.append("<li class=\"page-item\">" +
                    "       <a class=\"page-link\" href=\"" + listUrl + "pageNum=" + currentPageSetup + "\" aria-label=\"Previous\">" +
                    "            <span aria-hidden=\"true\">이전</span>" +
                    "       </a>" +
                    " </li>");
        }

        //2. 그냥 페이지(6 7 8 9 10) 이동 버튼 만들기
        //int page; // 그냥 페이지 숫자를 클릭 했을때 들어갈 pageNum값
        //currentPage=1, currentPageSetup=0, page=1
        //currentPage=2, currentPageSetup=0, page=1
        //currentPage=3, currentPageSetup=0, page=1
        //currentPage=4, currentPageSetup=0, page=1
        //currentPage=5, currentPageSetup=0, page=1
        // ...
        //currentPage=6, currentPageSetup=5, page=6
        // ...
        //currentPage=11, currentPageSetup=10, page=11
        // ...
        //currentPage=16, currentPageSetup=15, page=16
        page = currentPageSetup + 1;

        while (page <= totalPage && page <= (currentPageSetup + numPerBlock)) {
            if(page == currentPage) {
                //현재 내가 선택한 페이지 일때
                sb.append("<li class=\"page-item active\"><a class=\"page-link\" href=\"" + listUrl + "pageNum=" + page + "\">" + page + "</a></li>");
            } else {
                //현재 선택한 페이지가 아닐때
                sb.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + listUrl + "pageNum=" + page + "\">" + page + "</a></li>");
            }

            page++; //페이지 번호 1씩 증가
        }

        //3. 다음▶ 버튼 만들기
        //currentPage=1, currentPageSetup=0, page=1
        //currentPage=2, currentPageSetup=0, page=2
        //currentPage=3, currentPageSetup=0, page=3
        //currentPage=4, currentPageSetup=0, page=4
        //currentPage=5, currentPageSetup=0, page=5
        // ...
        //currentPage=6, currentPageSetup=5, page=6
        // ...
        //currentPage=11, currentPageSetup=10, page=11
        // ...
        //currentPage=16, currentPageSetup=15, page=16
        if(totalPage - currentPageSetup > numPerBlock) {
            //pageNum: 6, 11, 16, 21....
            sb.append("<li class=\"page-item\">" +
                    "     <a class=\"page-link\" href=\"" + listUrl + "pageNum=" + (currentPageSetup + numPerBlock + 1) + "\" aria-label=\"Next\">" +
                    "         <span aria-hidden=\"true\">다음</span>" +
                    "     </a>" +
                    " </li>");
        }

        //4. 버튼 합쳐서 문자열로 리턴
        return sb.toString();
    }


}
