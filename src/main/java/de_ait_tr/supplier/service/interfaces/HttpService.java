package de_ait_tr.supplier.service.interfaces;

import de.ait_tr.g_40_shop.domain.dto.ProductSupplyDto;

import java.util.List;

public interface HttpService {

    List<ProductSupplyDto> getAvailableProducts();

}
