package de_ait_tr.supplier.scheduler;

import de.ait_tr.g_40_shop.domain.dto.ProductSupplyDto;
import de_ait_tr.supplier.service.interfaces.HttpService;
import de_ait_tr.supplier.service.interfaces.RequestCalculator;
import de_ait_tr.supplier.service.interfaces.SupplyRequestService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class SupplyTaskExecutor {

    private final HttpService httpService;

    private final RequestCalculator requestCalculator;

    private final SupplyRequestService service;

    public SupplyTaskExecutor(HttpService httpService, RequestCalculator requestCalculator, SupplyRequestService service) {
        this.httpService = httpService;
        this.requestCalculator = requestCalculator;
        this.service = service;
    }

    // Cron expression - * * * * * *
    // 0 0 9 * * * - ежедневно в 9 часов ровно
    // 0 10-15 10 * * * - ежедневно в 10 часов в промежуток с 10 до 15 минут
    // 0 0 7,9 * * * - ежедневно в 7 и в 9 часов

//    @Scheduled(cron = "0 0 23 * * *") // Отправка заявки поставщику ежедневно в 23 ч.
    @Scheduled(cron = "0,30 * * * * *") // В целях тестирования
    public void sendSupplyRequest(){

        // Алгоритм
        // 1. Получаем товарные остатки по всем продуктам из магазина
        // 2. Зная, какое количество продуктов нужно поддерживать в магазине,
        // вычисляем, сколько продуктов надо заказать поставщику. Формируем заказ


        List<ProductSupplyDto> products = httpService.getAvailableProducts();

        Map<String, Integer> supplyRequest = requestCalculator.calculateRequest(products);

        // 3. Отправляем заказ на е-мейл поставщику

        service.sendSupplyRequest(supplyRequest);




    }
}
