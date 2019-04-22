package model;

import org.davidmoten.rx.jdbc.annotations.Column;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface BankAccount {
    @Column
    BigDecimal balance();

    @Column("AccNo")
    BigInteger accNo();

    @Column
    long bcode();

    @Column("UID")
    Long uid();

    @Column
    String Acctype();
}
