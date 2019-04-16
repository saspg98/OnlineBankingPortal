package model;

import org.davidmoten.rx.jdbc.annotations.Column;

public interface BankAccount {
    @Column
    double balance();

    @Column
    Long accNo();

    @Column
    String bcode();

    @Column
    Long uid();

}
