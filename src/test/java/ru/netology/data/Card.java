package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
    private String cardNumber;
    private String month;
    private String year;
    private String owner;
    private String cvc;
}
