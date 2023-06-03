package pl.krax.charity.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import pl.krax.charity.dto.DonationDto;
import pl.krax.charity.entities.Category;
import pl.krax.charity.entities.Donation;
import pl.krax.charity.service.CategoryService;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryService.class})
public interface DonationMapper {

    DonationMapper INSTANCE = Mappers.getMapper(DonationMapper.class);

    @Mapping(source = "institutionId", target = "institution.id")
    @Mapping(target = "categories", expression = "java(mapCategoryIdsListToCategories(donationDto.getCategoriesIdsList(), categoryService))")
    @Mapping(source = "userId", target = "user.id")
    Donation toEntity(DonationDto donationDto, @Context CategoryService categoryService);

    default List<Category> mapCategoryIdsListToCategories(List<Long> categoriesIdsList, CategoryService categoryService) {
        return categoryService.findAllByIds(categoriesIdsList);
    }
}




