package model;

import org.davidmoten.rx.jdbc.annotations.Column;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public interface Transaction {
    @Column("ID")
    Long getID();

    @Column("Sender")
    BigInteger getSender();

    @Column("Receiver")
    BigInteger getReceiver();

    @Column("Time")
    Date getTime();

    @Column("Amount")
    BigDecimal getAmount();

}
