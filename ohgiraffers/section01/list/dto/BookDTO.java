package com.ohgiraffers.section01.list.dto;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

public class BookDTO implements Comparable<BookDTO> {
  /* 도서 정보를 저장할 DTO 클래스를 만들어보자 */
  private int number;
  private String title;
  private String author;
  private int price;
  private String condition;

  // 기본 생성자
  public BookDTO() {
     this.condition = "good";
  }

  public BookDTO(BookDTO other){ //전달받은 객체를 새 객체에 이용
    this.number = other.number;
    this.title = other.title;
    this.author = other.author;
    this.price = other.price;
    this.condition = other.condition;
  }

  public BookDTO(int number, String title, String author, int price) {
    this(number, title, author, price, "good");
  }


  public BookDTO(int number, String title, String author, int price, String condition) {
    super();
    this.number = number;
    this.title = title;
    this.author = author;
    this.price = price;
    this.condition = condition;
  }

  /* setter/getter */
  public void setNumber(int number) {
    this.number = number;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getNumber() {
    return number;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public int getPrice() {
    return price;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }


  /* toString 오버라이딩 */
  @Override
  public String toString() {
    return "BookDTO [number=" + number +
        ", title=" + title + ", author="
        + author + ", price=" + price + ", condition= "+ condition + "]";
  }

  /* Comparable<T> 인터페이스
  * 같은 인스턴스 끼리의 기본 비교 방법을 정의하는
  * compareTo() 메서드 제공 인터페이스 */


  @Override
  public int compareTo(BookDTO o) { // o : 다른 BookDTO 객체
    // 가격 순서
    // return this.price - o.price;

    // 이름 순서 (String의 compareTo() 활용)
    return this.title.compareTo(o.title);
  }
}
