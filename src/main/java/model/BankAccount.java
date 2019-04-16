package model;

import org.davidmoten.rx.jdbc.annotations.Column;

public interface BankAccount {
    @Column
    public double balance();
    @Column
    public Long accNo();
    @Column
    public String bcode() ;
    @Column
    public Long uid();

}
