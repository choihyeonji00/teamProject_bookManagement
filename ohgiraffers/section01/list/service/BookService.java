package com.ohgiraffers.section01.list.service;

import com.ohgiraffers.section01.list.comparator.AscendingPrice;
import com.ohgiraffers.section01.list.dto.BookDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {

  private List<BookDTO> bookList; // 참조형의 기본값은 null

  public BookService() {
    bookList = new ArrayList<>();

    /* 도서 정보 추가 */ // 이거 지워도 유지되게 하는 거 ?? 어케하나
    bookList.add(new BookDTO(1, "홍길동전", "허균", 50000,"good"));
    bookList.add(new BookDTO(2, "목민심서", "정약용", 30000,"bad"));
    bookList.add(new BookDTO(3, "동의보감", "허준", 40000,"good"));
    bookList.add(new BookDTO(4, "삼국사기", "김부식", 46000,"bad"));
    bookList.add(new BookDTO(5, "삼국유사", "일연", 58000,"fine"));
    bookList.add(new BookDTO(6, "홍길동전", "허균", 50000,"bad"));
  }


  // getter
  public List<BookDTO> getBookList() {
    return bookList;
  }

  /* 책 목록에서 번호(number)가 일치하는 책을 찾아서 반환
   * bookDTO 또는 null */
  public BookDTO selectBookNumber(int bookNumber) {
    // 반복문을 이용해서 모든 책 인스턴스에 접근
    for (BookDTO book : bookList) { // 전체 책 목록에서 하나 꺼낸 책의 숫자와 검색하려했던 책의 숫자가 같으면 book을 리턴
      if (book.getNumber() == bookNumber)
        return book; // 이 book은 주소 // 얕은 복사.
    }
    return null; // 번호가 일치하는 책이 없음
  }

  /* 책 목록에 새로운 책 추가
   * 단, "제목"이 중복되는 책은 추가 X
   * 반환되는 number는 마지막 number + 1 */ // 번호보다 중복 체크가 우선임
  public int addBook(BookDTO newBook) {

    // 제목 중복 체크
    for (BookDTO book : bookList) {
      if (book.getTitle().equals(newBook.getTitle())) // 중복인 경우
        return 0; // 똑같은 걸 찾으면 0을 반환
    }

    // 다음 번호 생성
    int nextNum = bookList.get(bookList.size() - 1).getNumber() + 1;

    // 책 정보는 목록에 추가
    newBook.setNumber(nextNum);
    bookList.add(newBook);
    return newBook.getNumber(); // 생성된 책 번호 반환

  }

  /**
   * 도서 목록에서 번호가 일치하는 책 제거
   * @param bookNumber
   * @return 제거된 BookDTO 또는 null
   */
  public BookDTO delectBook(int bookNumber) {


    // 반복문을 이용해서 모든 책 인스턴스에 접근
    // -> 똑같은 번호의 책을 목록에서 제거 후 반환
    for (int i = 0; i < bookList.size(); i++) {
      if (bookList.get(i).getNumber() == bookNumber)
        return bookList.remove(i);
    }
    return null;
  }


  /**
   * 상태가 bad인 책 전체 삭제
   * @return 삭제된 책 목록(List<BookDTO>)
   */
  public List<BookDTO> deleteBadBooks() {

    List<BookDTO> removedList = new ArrayList<>();

    for (int i = bookList.size() - 1; i >= 0; i--) {
      BookDTO book = bookList.get(i);

      if ("bad".equalsIgnoreCase(book.getCondition())) {
        removedList.add(bookList.remove(i));
      }
    }
    Collections.reverse(removedList);
    return removedList;
  }

  /**
   * 특정 제목에 대해 good이 아닌 책들만 삭제
   * @param title 기준이 되는 제목
   * @return 삭제된 책 목록(List<BookDTO>)
   */
  public List<BookDTO> deleteNotGoodBooksByTitle(String title) {

    List<BookDTO> removedList = new ArrayList<>();

    for (int i = bookList.size() - 1; i >= 0; i--) {
      BookDTO book = bookList.get(i);

      if (book.getTitle().equals(title)
          && !"good".equalsIgnoreCase(book.getCondition())) {

        removedList.add(bookList.remove(i));
      }
    }

    // 출력 순서는 앞에서부터 보이게할거임
    Collections.reverse(removedList);

    return removedList;
  }

  /**
   * 책 제목 중 일부라도 keyword와 일치하는 책을 모두 반환
   *
   * @param keyword
   * @return searchBookList
   */
  public List<BookDTO> searchBook(String keyword) { //책이 몇 권 저장될지모르니 리스트를 만들어 차곡차곡쌓아 반환
    List<BookDTO> searchBookList = new ArrayList<>();
    for (BookDTO book : bookList) {
      // 제목에 keyword가 포함되어 있으면 true
      if (book.getTitle().contains(keyword))
        searchBookList.add(book); // 검색된 책 목록에 keyword 포함 책 추가

    }
    return searchBookList;
  }

  /**
   * List 복사본을 만들어서 정렬 후 반환 // 깊은 복사
   *
   * @param sortingNumber
   * @return sortedBookList
   */
  public List<BookDTO> sortBookList(int sortingNumber) {

    //Collections.sort() -> 원본 정렬 - . 12345 번 훼손되니까 복사본으로 정렬
    // BookDTO 찾기
    // 복사본 만들기  //stream() 향상된 for문 효과 map() 새로운 배열, 새로운 리스트를 만들 때 씀 // new 누르면 방금 만든 객체로 이동

    // 스트림을 이용한 List 깊은 복사 (내용물도 완전 복사된 것 만드는 거)
    List<BookDTO> sortBookList = bookList.stream().map(BookDTO::new).collect(Collectors.toList()); // collect스트림 리스트로 만들기

    if (sortingNumber == 1) { // 이름 오름차순(기본 정렬)
      Collections.sort(sortBookList);
    } else {
      Collections.sort(sortBookList, new AscendingPrice());
    }

    return sortBookList;

  }
  // 상태가 bad 책 전체 삭제
  // 책상태 good fine bad good제회 다 삭제?


}