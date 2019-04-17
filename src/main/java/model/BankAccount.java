package model;

import org.davidmoten.rx.jdbc.annotations.Column;

public interface BankAccount {
    @Column
    double balance();

    @Column
    long accNo();

    @Column
    long bcode();

    @Column
    long uid();

    @Column
    char Acctype();
}
