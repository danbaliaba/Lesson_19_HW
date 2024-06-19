package de_ait_tr.supplier.service;

import de.ait_tr.g_40_shop.domain.dto.ProductSupplyDto;
import de_ait_tr.supplier.service.interfaces.RequestCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class RequestCalculatorImpl implements RequestCalculator {


    // Такое количество товара мы должны постоянно поддерживать на складе магазина
    private final Map<String , Integer> requiredQuantities = Map.of(
            "Coconut", 18,
            "Apple", 19,
            "Pineapple", 15,
            "Banana", 12,
            "Orange", 22,
            "Grapes", 14,
            "Peach", 20
    );
    @Override
    public Map<String, Integer> calculateRequest(List<ProductSupplyDto> products) {
        return products.stream()
                .collect(Collectors.toMap(
                        ProductSupplyDto::getTitle,
                        x -> requiredQuantities.get(x.getTitle()) - x.getQuantity()
                ));
    }
}
