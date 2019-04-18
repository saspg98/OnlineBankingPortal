package model;

import org.davidmoten.rx.jdbc.annotations.Column;

import java.time.Instant;

public interface Transaction {
    @Column
    String ID();

    @Column
    String Sender();

    @Column
    String Receiver();

    @Column
    Instant Time();

}
