package model;

import org.davidmoten.rx.jdbc.annotations.Column;

import java.util.Date;

//Interface for Rx2-java
public interface User {
    @Column
    Long uid();

    @Column
    String name();

    @Column
    String Address();

    @Column
    Date DOB();

    @Column
    String Sex();
    //
    // @Column
    // String Username();
    //
    // @Column
    // String Password();

    @Column
    String Email();

}
