package br.marcoswolf.pulsedelivery.dto.seller;

import org.springframework.web.multipart.MultipartFile;

public record SellerImageDTO(
        MultipartFile storeImage
) {}
