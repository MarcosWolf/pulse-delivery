package br.marcoswolf.pulsedelivery.dto;

import java.math.BigDecimal;

public record OrderItemDTO(
   Long id,
   String productName,
   Integer quantity,
   BigDecimal price
) {}
