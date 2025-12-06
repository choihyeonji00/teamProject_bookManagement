package com.ohgiraffers.section01.list.run;

import com.ohgiraffers.section01.list.dto.BookDTO;
import com.ohgiraffers.section01.list.service.BookService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application3 {

    private Scanner sc = new Scanner(System.in);
    private BookService bookService = new BookService();

    public static void main(String[] args) {
        new Application3().run();
    }

    public void run(){

        int input = 0;

        do{
            System.out.println("\n===== 도서 관리 프로그램 =====\n");
            System.out.println("1. 모든 도서 목록 조회");
            System.out.println("2. 도서 상세 조회(도서 번호)");
            System.out.println("3. 도서 추가");
            System.out.println("4. 도서 수정");
            System.out.println("5. 도서 제거");
            System.out.println("6. 도서 검색");
            System.out.println("7. 도서 정렬");
            System.out.println("0. 프로그램 종료");

            try{
                System.out.print("번호 선택 >> ");
                input = sc.nextInt();
                sc.nextLine(); // 입력 버퍼 개행 문자 제거

                switch(input){
                    case 1: selectAll(); break;
                    case 2: selectBookNumber(); break;
                    case 3: addBook(); break;
                    case 4: updateBook(); break;
                    case 5: deleteBook(); break;
                    case 6: searchBook(); break;
                    case 7: sortBookList(); break;
                    case 0:
                        System.out.println("!@#$% 프로그램을 종료합니다. %$#@!"); break;
                    default:
                        System.out.println("@@@@@ 메뉴 목록에 존재하는 번호를 입력하세요. @@@@@");
                }

            }catch (InputMismatchException e){
                System.out.println("##### 입력 형식이 잘못되었습니다. ######");
                sc.nextLine(); // 입력 버퍼에 남은 잘못된 문자열 제거
                input = -1; // 첫 반복 입력 오류 시 비정상 종료 막기

            }catch (Exception e){ // 나머지 예외 발생 처리
                System.out.println("##### 예외 발생. 관리자에게 문의하세요. #####");
                e.printStackTrace();
            }

        }while(input != 0);
    }


    /**
     * 1. 모든 도서 목록 조회
     */
    private void selectAll(){
        System.out.println("\n*** 도서 목록 조회 ***\n");
        List<BookDTO> books = bookService.getBookList();

        // 향상된 for문
        for(BookDTO book : books){
            System.out.println(book);
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
        System.out.println("책 분류: " + book.getGenre());
        System.out.println("출판사: " + book.getPublisher());
        System.out.println("출판년도: " + book.getYear());
        System.out.println("책 가격: " + book.getPrice() + "원");
        System.out.println("대여가능 여부: " + book.isAvailable() + "원");
        System.out.println("책 상태: " + book.getCondition() + "원");
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

        System.out.print("책 분류 입력 : ");
        String genre = sc.nextLine();

        System.out.print("출판사 입력 : ");
        String publisher = sc.nextLine();

        System.out.print("출판년도 입력 : ");
        int year = sc.nextInt();
        sc.nextLine();

        System.out.print("책 가격 입력 : ");
        int price = sc.nextInt();
        sc.nextLine();

        System.out.print("대여가능 여부 입력 : ");
        boolean isAvailable = sc.nextBoolean();
        sc.nextLine();

        System.out.print("책 상태 입력 : ");
        String condition = sc.nextLine();

        // DTO(Data Transfer Object) : 계층간 데이터 전달 목적의 객체
        // BookDTO 객체 생성 및 값 대입
        BookDTO book = new BookDTO();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublisher(publisher);
        book.setYear(year);
        book.setPrice(price);
        book.setAvailable(isAvailable);
        book.setCondition(condition);

        // 서비스 호출
        int bookNumber = bookService.addBook(book);

        if(bookNumber == 0){
            System.out.println("@@@ 책 추가에 실패했습니다. @@@");
            return;
        }

        System.out.println(bookNumber + "번 책이 추가 되었습니다.");
    }


    /**
     * 4. 도서 수정
     */
    private void updateBook(){
        System.out.print("\n*** 도서 수정 ***\n");

        // 1. 책 번호로 수정할 책이 있는지 검색
        //  -> 없으면 메서드 종료
        System.out.print("수정할 도서 번호 입력 : ");
        int bookNumber = sc.nextInt();
        sc.nextLine(); // 입력 버퍼 개행 문자 제거

        // 서비스 호출
        BookDTO book = bookService.selectBookNumber(bookNumber);

        if(book == null){
            System.out.println("@@@ 일치하는 번호의 책이 없습니다. @@@");
            return;
        }

        // 2. 해당 책의 제목, 저자, 가격을 수정
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

        System.out.println("*** 책 정보가 수정 되었습니다 ***");
    }

    /**
     * 5. 도서 제거
     */
    private void deleteBook(){
        System.out.print("\n*** 도서 제거 ***\n");

        System.out.print("제거할 도서 번호 입력 : ");
        int bookNumber = sc.nextInt();
        sc.nextLine();

        // 서비스 호출
        BookDTO deletedBook = bookService.deleteBook(bookNumber);

        if(deletedBook == null){
            System.out.println("@@@ 일치하는 번호의 책이 없습니다. @@@");
            return;
        }

        System.out.println(deletedBook.getTitle() + "이/가 제거 되었습니다.");

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
     * 7. 도서 정렬 (원본은 유지하고 싶다!)
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
