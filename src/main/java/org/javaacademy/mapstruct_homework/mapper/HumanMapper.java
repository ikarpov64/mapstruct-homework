package org.javaacademy.mapstruct_homework.mapper;

import org.javaacademy.mapstruct_homework.dto.PersonCreditDto;
import org.javaacademy.mapstruct_homework.dto.PersonDriverLicenceDto;
import org.javaacademy.mapstruct_homework.dto.PersonInsuranceDto;
import org.javaacademy.mapstruct_homework.entity.Address;
import org.javaacademy.mapstruct_homework.entity.Human;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper
public interface HumanMapper {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passportNumber", source = ".", qualifiedByName = "getPassportData")
    @Mapping(target = "fullAddress", source = ".", qualifiedByName = "getFullAddress")
    @Mapping(target = "salary", source = ".", qualifiedByName = "getSalary")
    PersonCreditDto convertToCreditDto(Human human);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullPassportData", source = ".", qualifiedByName = "getFullPassportData")
    @Mapping(target = "birthDate", source = ".", qualifiedByName = "getBirthDate")
    PersonDriverLicenceDto convertToDriverLicenceDto(Human human);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullAddress", source = ".", qualifiedByName = "getFullAddress")
    @Mapping(target = "fullAge", source = ".", qualifiedByName = "getFullAge")
    PersonInsuranceDto convertToInsuranceDto(Human human);

    @Named("getFullAddress")
    default String getFullAddress(Human human) {
        Address address = human.getLivingAddress();
        if (address == null) {
            return "";
        }

        return Stream.of(
                        address.getRegion(),
                        address.getCity(),
                        address.getStreet(),
                        address.getHouse(),
                        address.getBuilding(),
                        address.getFlat()
                )
                .filter(addressPart -> addressPart != null && !addressPart.isEmpty())
                .collect(Collectors.joining(" "));
    }

    @Named("getFullAge")
    default int getFullAge(Human human) {
        LocalDate birthDate = LocalDate.of(human.getBirthYear(), human.getBirthMonth(), human.getBirthDay());
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    @Named("getPassportData")
    default String getPassportData(Human human) {
        return "%s%s".formatted(
                human.getPassport().getSeries(),
                human.getPassport().getNumber()
        );
    }

    @Named("getFullPassportData")
    default String getFullPassportData(Human human) {
        return "%s %s".formatted(
                getPassportData(human),
                human.getPassport().getIssueDate().format(DATE_TIME_FORMATTER));
    }

    @Named("getSalary")
    default String getSalary(Human human) {
        return "%s %s".formatted(
                human.getWork().getSalary(),
                human.getWork().getCurrency()
        );
    }

    @Named("getBirthDate")
    default String getBirthDate(Human human) {
        LocalDate birthDate = LocalDate.of(human.getBirthYear(), human.getBirthMonth(), human.getBirthDay());
        return birthDate.format(DATE_TIME_FORMATTER);
    }

    @Named("getFullName")
    default String getFullName(Human human) {
        return Stream.of(
                        human.getFirstName(),
                        human.getLastName(),
                        human.getMiddleName()
                )
                .filter(namePart -> namePart != null && !namePart.isEmpty())
                .collect(Collectors.joining(" "));
    }
}
