package dev.bituum.service;

import dev.bituum.model.Quotes;

import java.util.List;

public interface CBRService {
    Quotes getOneByCharCode(List<Quotes> list, String charCode);
    int compareTwoQuotes(List<Quotes> list, String charCodeONE, String charCodeTWO);
    int calculateQuote(List<Quotes> list, String charCodeONE, String charCodeTWO);
    double multiplyQuotes(List<Quotes> list, String charCode, int amount);
}
