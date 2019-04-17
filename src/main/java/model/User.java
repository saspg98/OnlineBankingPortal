package model;

import org.davidmoten.rx.jdbc.annotations.Column;

import java.util.Date;

//Interface for Rx2-java
public interface User {
    //TODO: Convert to interface
    @Column
    Long uid();
    
    @Column("Name")
    String getName();
    
    @Column
    String Address();
    
    @Column
    Date DOB();
    
    @Column
    char Sex();
    
    @Column
    String Username();
    
    @Column
    String Password();
    
    @Column
    String Email();
    
    }
