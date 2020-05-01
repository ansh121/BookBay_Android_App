package com.example.bookbayandroid;

public class IncomingRequestData {
    String requestid,bookname,isbn,username,name,borrowduration,dateofrequest,flag;
    public IncomingRequestData(String requestid, String bookname, String isbn, String name, String username, String dateofrequest, String borrowduration, String flag) {
        this.requestid=requestid;
        this.bookname=bookname;
        this.isbn=isbn;
        this.username=username;
        this.name=name;
        this.borrowduration=borrowduration;
        this.dateofrequest=dateofrequest;
        this.flag=flag;
    }
}
