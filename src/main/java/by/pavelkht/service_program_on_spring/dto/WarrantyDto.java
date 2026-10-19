package by.pavelkht.service_program_on_spring.dto;

public record WarrantyDto(
        String equipmentName,
        Integer serialNumber,
        String oilLevel,
        String oilCondition,
        String reason,
        String descriptionDefect,
        String linkPhoto
) {
}
