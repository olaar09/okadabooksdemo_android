package co.okadabooksdemo.object;

/**
 * Created by olaar on 8/23/16.
 */
public class Book {

    public int id;

    public Book(int id, double book_price, String book_name) {
        this.id = id;
        this.book_price = book_price;
        this.book_name = book_name;
    }

    public int getid() {

        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public double getbook_price() {
        return book_price;
    }

    public void setbook_price(double book_price) {
        this.book_price = book_price;
    }

    public String getbook_name() {
        return book_name;
    }

    public void setbook_name(String book_name) {
        this.book_name = book_name;
    }

    public Book() {

    }

    public String book_name;
    public double book_price;

}
