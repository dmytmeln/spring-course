package org.example.library.service;

import org.example.library.dto.CategoryDto;
import org.example.library.exception.NotFoundException;
import org.example.library.mapper.CategoryMapper;
import org.example.library.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.example.library.factory.CategoryFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;
    @Mock
    private CategoryMapper mapper;
    @InjectMocks
    private CategoryService service;

    @Test
    public void givenCategoriesInDb_whenGetAll_thenReturnCategoryDtoList() {
        var expectedDtos = setupMockedCategoryListInDb();

        var actualDtos = service.getAllCategories();

        assertIterableEquals(expectedDtos, actualDtos);
    }

    private List<CategoryDto> setupMockedCategoryListInDb() {
        var categories = List.of(newDefaultCategory());
        var dtos = List.of(newDefaultCategoryDto());
        when(repository.findAll()).thenReturn(categories);
        when(mapper.toDto(categories)).thenReturn(dtos);
        return dtos;
    }

    @Test
    public void givenNoCategoriesInDb_whenGetAll_thenReturnEmptyCategoryDtoList() {
        when(repository.findAll()).thenReturn(List.of());
        when(mapper.toDto(List.of())).thenReturn(List.of());

        var actualDtos = service.getAllCategories();

        assertTrue(actualDtos.isEmpty());
    }

    @Test
    public void givenExistingId_whenGetCategory_thenReturnCategory() {
        var expectedCategory = newDefaultCategory();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(expectedCategory));

        var actualCategory = service.getExistingCategory(DEFAULT_ID);

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    public void givenNotExistingId_whenGetCategory_thenThrowNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.getExistingCategory(DEFAULT_ID));
    }

    @Test
    public void givenExistingId_whenGetCategoryDto_thenReturnDto() {
        var expectedDto = setupMockedCategoryInDbMappedToDto();

        var actualDto = service.getExistingCategoryDto(DEFAULT_ID);

        assertEquals(expectedDto, actualDto);
    }

    private CategoryDto setupMockedCategoryInDbMappedToDto() {
        var category = newDefaultCategory();
        var dto = newDefaultCategoryDto();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(category));
        when(mapper.toDto(category)).thenReturn(dto);
        return dto;
    }

    @Test
    public void givenNotExistingId_whenGetCategoryDto_thenThrowNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.getExistingCategoryDto(DEFAULT_ID));
    }

    @Test
    public void givenDto_whenCreateCategory_thenReturnSavedCategoryDto() {
        var inputDto = newDefaultCategoryDtoWithoutId();
        var expectedDto = setupMockedCreationFlow(inputDto);

        var actualDto = service.createCategory(inputDto);

        assertEquals(expectedDto, actualDto);
    }

    private CategoryDto setupMockedCreationFlow(CategoryDto dto) {
        var category = newDefaultCategoryWithoutId();
        var savedCategory = newDefaultCategory();
        var savedCategoryDto = newDefaultCategoryDto();
        when(repository.existsByName(dto.getName())).thenReturn(false);
        when(mapper.toEntity(dto)).thenReturn(category);
        when(repository.save(category)).thenReturn(savedCategory);
        when(mapper.toDto(savedCategory)).thenReturn(savedCategoryDto);
        return savedCategoryDto;
    }

    @Test
    public void givenDtoWithAlreadyExistingName_whenCreateCategory_thenThrowIllegalArgumentException() {
        var inputDto = newDefaultCategoryDtoWithoutId();
        when(repository.existsByName(inputDto.getName())).thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createCategory(inputDto));
    }

    @Test
    public void givenExistingIdAndDto_whenUpdateCategory_thenReturnUpdatedCategoryDto() {
        var inputDto = newDefaultCategoryDtoWithoutId();
        var expectedDto = setupMockedUpdateFlow(inputDto);

        var actualDto = service.updateCategory(DEFAULT_ID, inputDto);

        assertEquals(expectedDto, actualDto);
        assertEquals(DEFAULT_ID, actualDto.getId());
    }

    private CategoryDto setupMockedUpdateFlow(CategoryDto dto) {
        String newName = "Non-Fiction";
        dto.setName(newName);
        var categoryDb = newDefaultCategory();
        var updatedCategory = newDefaultCategory();
        updatedCategory.setName(newName);
        var updatedCategoryDto = newDefaultCategoryDto();
        updatedCategoryDto.setName(newName);
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(categoryDb));
        when(repository.existsByName(dto.getName())).thenReturn(false);
        when(mapper.toEntity(dto, DEFAULT_ID)).thenReturn(categoryDb);
        when(repository.save(categoryDb)).thenReturn(updatedCategory);
        when(mapper.toDto(updatedCategory)).thenReturn(updatedCategoryDto);
        return updatedCategoryDto;
    }

    @Test
    public void givenExistingIdAndDtoWithTheSameNameAsInDb_whenUpdateCategory_thenReturnUpdatedCategoryDto() {
        var inputDto = newDefaultCategoryDtoWithoutId();
        var expectedDto = setupMockedUpdateFlow_withoutNameChanges(inputDto);

        var actualDto = service.updateCategory(DEFAULT_ID, inputDto);

        assertEquals(expectedDto, actualDto);
        assertEquals(DEFAULT_ID, actualDto.getId());
    }

    private CategoryDto setupMockedUpdateFlow_withoutNameChanges(CategoryDto dto) {
        var categoryDb = newDefaultCategory();
        var updatedCategory = newDefaultCategory();
        var updatedCategoryDto = newDefaultCategoryDto();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(categoryDb));
        when(mapper.toEntity(dto, DEFAULT_ID)).thenReturn(categoryDb);
        when(repository.save(categoryDb)).thenReturn(updatedCategory);
        when(mapper.toDto(updatedCategory)).thenReturn(updatedCategoryDto);
        return updatedCategoryDto;
    }

    @Test
    public void givenExistingIdAndDtoWithAlreadyExistingName_whenUpdateCategory_thenThrowIllegalArgumentException() {
        var inputDto = newDefaultCategoryDtoWithoutId();
        setupMockedUpdateFlowToThrowIllegalArgumentException(inputDto);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateCategory(DEFAULT_ID, inputDto));
    }

    private void setupMockedUpdateFlowToThrowIllegalArgumentException(CategoryDto dto) {
        String newName = "Non-Fiction";
        dto.setName(newName);
        var categoryDb = newDefaultCategory();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(categoryDb));
        when(repository.existsByName(dto.getName())).thenReturn(true);
    }

    @Test
    public void givenNonExistingId_whenUpdateCategory_thenThrowNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.updateCategory(DEFAULT_ID, newDefaultCategoryDtoWithoutId()));
    }

    @Test
    public void givenAnyId_whenDeleteCategory_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> service.deleteCategory(DEFAULT_ID));
        assertDoesNotThrow(() -> service.deleteCategory(-1));
    }

}
