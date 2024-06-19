package de_ait_tr.supplier.service.interfaces;

import de.ait_tr.g_40_shop.domain.dto.ProductSupplyDto;

import java.util.List;
import java.util.Map;

public interface RequestCalculator {

    Map<String, Integer> calculateRequest(List<ProductSupplyDto> products);
}
