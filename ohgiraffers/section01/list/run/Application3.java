package com.ohgiraffers.section01.list.run;

import com.ohgiraffers.section01.list.dto.BookDTO;
import com.ohgiraffers.section01.list.service.BookService;

import java.awt.print.Book;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application3 {
  private Scanner sc = new  Scanner(System.in); // 필드에 생성
  private BookService bookService = new BookService();
  
  public static void main(String[] args) {
    new  Application3().run(); // 현재 클래스 객체 생성한 다음 run 하겠다 input이 0이 아니면 계속 도는 형태
  }
  
  public void run() {
    
    int input = 0;
    
    do {
      // 컬렉션을 써서 해결하기
      System.out.println("\n===== 도서 관리 프로그램 =====\n");
      System.out.println("1. 모든 도서 목록 조회");
      System.out.println("2. 도서 상세 조회(도서 번호 입력)");
      System.out.println("3. 도서 추가");
      System.out.println("4. 도서 수정");
      System.out.println("5. 도서 제거");
      System.out.println("6. 도서 검색");
      System.out.println("7. 도서 정렬");
      System.out.println("0. 프로그램 종료");

      //비정상 종료 방지를 위해 예외처리 구문 적기
      try {
        System.out.print("번호 선택 >> ");
        input = sc.nextInt();
        sc.nextLine(); // 입력 버퍼 개행 문자 제거

        switch (input) {
          case 1: selectAll(); break; //1번 selectAll() 호출
          case 2: selectBookNumber(); break;
          case 3: addBook(); break;
          case 4: updateBook(); break;
          case 5: deleteBook(); break;
          case 6: searchBook(); break;
          case 7: sortBookList(); break;
          case 0:
            System.out.println("!@#$% 프로그램을 종료합니다. %$#@!");break;
          default: // 없는 숫자 입력
            System.out.println("@@@@@ 메뉴 목록에 존재하는 번호를 입력하세요. @@@@@");
        }

      } catch (InputMismatchException e) { // 스캐너에서 값을 잘못 입력했을 떄 발생하는 예외
        System.out.println("##### 입력 형식이 잘못되었습니다. #####");
        sc.nextLine(); // 입력 버퍼에 남은 잘못된 문자열을 제거  // ex. 메뉴 입력 오류나면 sc.nextLine() 쓰면 대부분 해결
        input = -1; // 첫 반복 입력 오류 시 비정상 종료 막기 아무 숫자 넣어도 되는데 그냥 메뉴에 없는 숫자 넣음

      } catch (Exception e) { // 나머지 예외 발생 처리
        System.out.println("##### 예외 발생. 관리자에게 문의하세요. #####");
        e.printStackTrace(); // 콘솔에 빨갛게 에러 뜨는 것처럼 에러 내용이 보임 //예외처리되어 비정상 종료는 안됨
      }



    }while(input != 0);
  }

  /* 1. 모든 도서 목록 조회 */
  private void selectAll() {
    // BookService 가져와서 출력해야함 //  private BookService bookService = new BookService(); 생성
    List<BookDTO> books =  bookService.getBookList(); //반환받으면 반환형 List<BookDTO>


    // 향상된 for문
    for (BookDTO book : books) {
      System.out.println(book); // 자동으로 book뒤에
    }


  }

  /**
   * 2. 도서 상세 조회(도서 번호)
   * @throws InputMismatchException
   */
  private void selectBookNumber() throws InputMismatchException{
    System.out.print("\n*** 도서 상세 조회(도서 번호) ***\n");
    System.out.print("상세 조회할 도서 번호 입력 : ");
    int bookNumber = sc.nextInt();

    // 서비스 호출
    BookDTO book = bookService.selectBookNumber(bookNumber);

    if(book == null){
      System.out.println("@@@ 일치하는 번호의 책이 없습니다. @@@");
      return;
    }

    System.out.println("책 번호: " + book.getNumber());
    System.out.println("책 제목: " + book.getTitle());
    System.out.println("책 저자: " + book.getAuthor());
    System.out.println("책 가격: " + book.getPrice() + "원");
  }

  /**
   * 3. 도서 추가
   */
  private void addBook(){
    System.out.print("\n*** 도서 추가 ***\n");

    System.out.print("책 제목 입력 : ");
    String title = sc.nextLine();

    System.out.print("책 저자 입력 : ");
    String author = sc.nextLine();

    System.out.print("책 가격 입력 : ");
    int price = sc.nextInt();
    sc.nextLine(); // 개행 문자 제거

    // DTO(Data Transfer Object) : 계층간 데이터 전달 목적의 객체
    // 메서드명(a,b,c,d,e); 매개변수 최대 4~5개 생각하기 넘지 xx 메서드명(title,author,price) 순서 잘못 보내면 xx 가능하면 묶어서 쓰기
    // BookDTO 객체 생성 및 값 대입

    BookDTO book = new BookDTO(); // 기본생성자로 만들기
    book.setTitle(title);
    book.setAuthor(author);
    book.setPrice(price);

    // 서비스 호출 // 원래 5개 있으니까 6번이 반환됨
    int bookNumber = bookService.addBook(book);

    if(bookNumber == 0){
      System.out.println("@@@ 책 추가에 실패했습니다. @@@");
      return;
    }

    System.out.println(bookNumber + "번 책이 추가 되었습니다.");
  }

  /* 4. 도서 수정
  * */
  private void updateBook(){
    System.out.print("\n*** 도서 수정 ***\n");


    // 1. 책 번호로 수정할 책이 있는지 검색
    // -> 없으면 메서드 종료
    System.out.print("수정할 도서 번호 입력 : ");
    int bookNumber = sc.nextInt();
    sc.nextLine(); // 입력 버퍼 개행 문자 제거


    // 자바는 객체지향 언어 서로의 주소를 참조
    // 얕은 복사를 이용한
    // 서비스 호출
    BookDTO book = bookService.selectBookNumber(bookNumber);

    if(book == null){
      System.out.println("@@@ 일치하는 번호의 책이 없습니다. @@@");
      return;
    }



    // 2. 해당 책의 제목, 저자, 가격 수정

    System.out.print("수정할 책 제목 : ");
    String updateTitle = sc.nextLine();

    System.out.print("수정할 책 저자 : ");
    String updateAuthor = sc.nextLine();

    System.out.print("수정할 책 가격 : ");
    int updatePrice = sc.nextInt();
    sc.nextLine();

    book.setTitle(updateTitle);
    book.setAuthor(updateAuthor);
    book.setPrice(updatePrice);

    System.out.println("*** 책 정보가 수정 되었습니다. *** ");
  }

  /**
   * 5. 도서 제거
   */

  private void deleteBook(){
    System.out.print("\n*** 도서 제거 메뉴***\n");

    System.out.println("1. 도서 번호로 삭제");
    System.out.println("2. 상태가 bad인 책 전체 삭제");
    System.out.println("3. 특정 제목에서 good 제외 삭제");

    System.out.println("0. 삭제 메뉴 나가기");
    System.out.print("메뉴 선택 >> ");

    int menu = sc.nextInt();
    sc.nextLine();

    switch (menu) {
      case 1:
        deleteSingleBook();
        break;
      case 2:
        deleteBadBooksMenu();
        break;
      case 3:
        deleteNotGoodBooksByTitleMenu();
        break;
      case 0:
        System.out.println("삭제 메뉴를 종료합니다.");
        break;
      default:
        System.out.println("잘못된 번호입니다. 삭제 메뉴로 다시 시도해주세요.");
    }
  }

   // 5-1 도서 단일 제거
  private void deleteSingleBook() {
    System.out.print("\n*** 도서 번호로 단일 삭제 ***\n");

    System.out.print("제거할 도서 번호 입력 : ");
    int bookNumber = sc.nextInt();
    sc.nextLine();

    // 삭제 전에 상세 정보 미리 보기
    BookDTO target = bookService.selectBookNumber(bookNumber);

    if (target == null) {
      System.out.println("해당 번호의 책이 존재하지 않습니다. ");
      return;
    }

    // 상세 정보 출력
    System.out.println("\n[삭제하려는 책 정보]");
    System.out.println("번호: " + target.getNumber());
    System.out.println("제목: " + target.getTitle());
    System.out.println("저자: " + target.getAuthor());
    System.out.println("가격: " + target.getPrice() + "원");
    System.out.println("상태: " + target.getCondition());

    // 삭제 하시겠어요 ? Y/N 확인
    System.out.print("\n정말 삭제할까요? (Y/N) : ");
    String answer = sc.nextLine().trim().toUpperCase();

    if (!answer.equals("Y") && !answer.equals("YES")) {
      System.out.println("삭제가 취소되었습니다.");
      return;
    }

    BookDTO deletedBook = bookService.delectBook(bookNumber);

    if (deletedBook == null) {
      System.out.println("삭제 실패: 책이 존재하지 않습니다.");
      return;
    }

    System.out.println(deletedBook.getTitle() + "(이/가) 제거 되었습니다.");
  }


 // 5-2 도서 상태 'bad' 제거
  private void deleteBadBooksMenu() {

    System.out.println("\n*** 상태가 bad인 책 전체 삭제 ***");

    System.out.print("정말 'bad' 상태의 책을 모두 삭제하시겠습니까? (Y/N) : ");
    String answer = sc.nextLine().trim().toUpperCase();

    if (!answer.equals("Y") && !answer.equals("YES")) {
      System.out.println("삭제가 취소되었습니다.");
      return;
    }

    List<BookDTO> removedList = bookService.deleteBadBooks();

    if (removedList.isEmpty()) {
      System.out.println("삭제할 'bad' 상태의 책이 없습니다.");
      return;
    }

    System.out.println("\n[삭제된 도서 목록]");
    for (BookDTO book : removedList) {
      System.out.println("- " + book.getNumber() + " | " + book.getTitle() + " | 상태: " + book.getCondition());
    }

    System.out.println("총 " + removedList.size() + "권 삭제 완료!");
  }


    // 5-3 특정 제목 good 제외 삭제
  private void deleteNotGoodBooksByTitleMenu() {

    System.out.println("\n*** 특정 제목에서 good 제외 삭제 ***");

    System.out.print("기준이 될 책 제목 입력 : ");
    String title = sc.nextLine();

    System.out.print("'" + title + "' 제목의 도서 중 good이 아닌 책을 모두 삭제하시겠습니까? (Y/N) : ");
    String answer = sc.nextLine().trim().toUpperCase();

    if (!answer.equals("Y") && !answer.equals("YES")) {
      System.out.println("삭제가 취소되었습니다.");
      return;
    }

    List<BookDTO> removedList = bookService.deleteNotGoodBooksByTitle(title);

    if (removedList.isEmpty()) {
      System.out.println("삭제 대상이 없습니다. (해당 제목이 없거나, 모두 good 상태입니다.)");
      return;
    }

    System.out.println("\n[삭제된 도서 목록]");
    for (BookDTO book : removedList) {
      System.out.println("- " + book.getNumber() + " | " + book.getTitle()
          + " | 상태: " + book.getCondition());
    }

    System.out.println("총 " + removedList.size() + "권 삭제 완료!");
  }



  /**
   * 6. 도서 검색
   */
  private void searchBook(){
    System.out.print("\n*** 도서 검색(제목 부분 일치 검색) ***\n");

    System.out.print("검색할 책 제목 입력 : ");
    String keyword = sc.nextLine();

    // 서비스 호출
    List<BookDTO> searchBookList = bookService.searchBook(keyword);

    if(searchBookList == null || searchBookList.isEmpty()){
      System.out.println("검색 결과가 없습니다.");
      return;
    }

    for(BookDTO book : searchBookList){
      System.out.println(book);
    }
  }

  /**
   * 7. 도서 정렬 (원본은 유지하고 싶다!!)
   * //Collections.sort() 원본리스트 자체를 정렬 (원본훼손이 일어남) 정렬할때 복사본을 넣을거임
   */
  private void sortBookList(){
    System.out.print("\n*** 도서 정렬 목록 조회 ***\n");

    System.out.println("1. 도서명 오름차순");
    System.out.println("2. 가격 오름차순");
    int sortingNumber = sc.nextInt();
    sc.nextLine();

    // 서비스 구현
    List<BookDTO> books = bookService.sortBookList(sortingNumber);

    for(BookDTO book : books){
      System.out.println(book);
    }


  }


}
