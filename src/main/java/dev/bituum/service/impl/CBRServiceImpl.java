package dev.bituum.service.impl;

import dev.bituum.model.Quotes;
import dev.bituum.service.CBRService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CBRServiceImpl implements CBRService {

    @Override
    public Quotes getOneByCharCode(List<Quotes> list, String CharCode) {
        return findByCharCode(list, CharCode);
    }

    @Override
    public int compareTwoQuotes(List<Quotes> list, String charCodeONE, String charCodeTWO) {
        Quotes first = findByCharCode(list, charCodeONE);
        Quotes second = findByCharCode(list, charCodeTWO);
        Comparator<Quotes> comparator = new Comparator<Quotes>() {
            @Override
            public int compare(Quotes first, Quotes second) {
                double trueValueFirst = first.getValue() / (double)first.getNominal();
                double trueValueSecond = second.getValue() / (double)second.getNominal();
                return trueValueFirst > trueValueSecond ? 1 : 0;
            }
        };
        return comparator.compare(first, second);
    }

    @Override
    public int calculateQuote(List<Quotes> list, String charCodeONE, String charCodeTWO) {
        return 0;
    }

    @Override
    public double multiplyQuotes(List<Quotes> list, String charCode, int amount) {
        Quotes quote = findByCharCode(list, charCode);
        return quote.getValue() * amount / (double) quote.getNominal();
    }

    private Quotes findByCharCode(List<Quotes> list, String charCode){
        //TODO че за нахуй?

        return list.stream()
                .filter(quote -> quote.getCharCode().equals(charCode))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException("wrong charCode")
                );
    }
}
