package by.pavelkht.service_program_on_spring.service;

import by.pavelkht.service_program_on_spring.database.repository.WarrantyRepository;
import by.pavelkht.service_program_on_spring.dto.WarrantyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarrantyService {
    private final WarrantyRepository warrantyRepository;

    public Optional<WarrantyDto> getWarrantyByOrderId(Long orderId) {
        return warrantyRepository
                .findWarrantyByOrder_Id(orderId)
                .map(warranty -> {
                    return new WarrantyDto(
                            warranty.getEquipment().getName(),
                            warranty.getSerialNumber(),
                            warranty.getOilLevel(),
                            warranty.getOilCondition(),
                            warranty.getReason(),
                            warranty.getDescriptionDefect(),
                            warranty.getLinkPhoto()
                    );
                }
                );
    }

}
