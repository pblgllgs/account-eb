package com.pblgllgs.account.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author EazyBytes
 *
 */
@Getter
@Setter
@ToString
public class CustomerDetails {

    private Account account;
    private List<Loans> loans;
    private List<Cards> cards;

}