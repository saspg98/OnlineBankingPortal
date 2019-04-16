package model;

import org.davidmoten.rx.jdbc.annotations.Column;

//Interface for Rx2-java
public interface User {
    //TODO: Convert to interface
    @Column("Name")
    String getName();


}
