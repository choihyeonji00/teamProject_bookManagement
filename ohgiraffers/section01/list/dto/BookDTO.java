package com.ohgiraffers.section01.list.dto;

public class BookDTO implements Comparable<BookDTO> {

    /* 도서 정보를 저장할 DTO 클래스를 만들어보자 */
    private int number;
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private int year;
    private int price;
    private boolean isAvailable;
    private String condition;

    public BookDTO() {}

    public BookDTO(int number, String title, String author, String genre, String publisher,
                   int year, int price, boolean isAvailable, String condition) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.year = year;
        this.price = price;
        this.isAvailable = isAvailable;
        this.condition = condition;
    }

    /* 객체 복사 생성자 */
    public BookDTO(BookDTO other){
        this.number = other.number;
        this.title = other.title;
        this.author = other.author;
        this.genre = other.genre;
        this.publisher = other.publisher;
        this.year = other.year;
        this.price = other.price;
        this.isAvailable = other.isAvailable;
        this.condition = other.condition;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "number=" + number +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", condition='" + condition + '\'' +
                '}';
    }


    /* Comparable<T> 인터페이스
     * - 같은 인스턴스 끼리의 기본 비교 방법을 정의하는
     *   compareTo() 메서드 제공 인터페이스
     * */
    @Override
    public int compareTo(BookDTO o) {
        // 가격 순서
        // return this.price - o.price;

        // 이름 순서 (String의 compareTo() 활용)
        return this.title.compareTo(o.title);
    }
}
