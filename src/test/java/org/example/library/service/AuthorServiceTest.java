package org.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.library.domain.Author;
import org.example.library.dto.AuthorDto;
import org.example.library.mapper.AuthorMapper;
import org.example.library.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.example.library.factory.AuthorFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorMapper mapper;
    @Mock
    private AuthorRepository repository;
    @InjectMocks
    private AuthorService service;

    @Test
    public void givenAuthorsInDb_whenGetAll_thenReturnAuthorDtoList() {
        var expectedDtos = setupMockedAuthorListInDb();

        var actualDtos = service.getAllAuthors("");

        assertIterableEquals(expectedDtos, actualDtos);
    }

    private List<AuthorDto> setupMockedAuthorListInDb() {
        var authors = List.of(newDefaultAuthor());
        var dtos = List.of(newDefaultAuthorDto());
        when(repository.findByFullNameContaining("")).thenReturn(authors);
        when(mapper.toDto(authors)).thenReturn(dtos);
        return dtos;
    }

    @Test
    public void givenNoAuthorsInDb_whenGetAllAuthors_thenReturnEmptyAuthorDtoList() {
        when(repository.findByFullNameContaining("")).thenReturn(List.of());
        when(mapper.toDto(List.of())).thenReturn(List.of());

        var actualDtos = service.getAllAuthors("");

        assertTrue(actualDtos.isEmpty());
    }

    @Test
    public void givenExistingId_whenGetAuthor_thenReturnAuthor() {
        var expectedAuthor = newDefaultAuthor();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(expectedAuthor));

        var actualAuthor = service.getExistingAuthor(DEFAULT_ID);

        assertEquals(expectedAuthor, actualAuthor);
    }

    @Test
    public void givenNotExistingId_whenGetAuthor_thenThrowEntityNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.getExistingAuthor(DEFAULT_ID));
    }

    @Test
    public void givenExistingId_whenGetAuthorDto_thenReturnAuthorDto() {
        var expectedDto = setupMockedAuthorInDbMappedToDto();

        var actualDto = service.getExistingAuthorDto(DEFAULT_ID);

        assertEquals(expectedDto, actualDto);
    }

    private AuthorDto setupMockedAuthorInDbMappedToDto() {
        var author = newDefaultAuthor();
        var dto = newDefaultAuthorDto();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(author));
        when(mapper.toDto(author)).thenReturn(dto);
        return dto;
    }

    @Test
    public void givenNonExistingId_whenGetAuthorDto_thenThrowEntityNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> service.getExistingAuthor(DEFAULT_ID));
        verify(mapper, times(0)).toDto(any(Author.class));
    }

    @Test
    public void givenDto_whenCreateAuthor_thenReturnSavedAuthorDto() {
        var inputDto = newDefaultAuthorDtoWithoutId();
        var expectedDto = setupMockedCreationFlow(inputDto);

        var actualDto = service.createAuthor(inputDto);

        assertEquals(expectedDto, actualDto);
    }

    private AuthorDto setupMockedCreationFlow(AuthorDto dto) {
        var author = newDefaultAuthorWithoutId();
        var savedAuthor = newDefaultAuthor();
        var savedAuthorDto = newDefaultAuthorDto();
        when(mapper.toEntity(dto)).thenReturn(author);
        when(repository.save(author)).thenReturn(savedAuthor);
        when(mapper.toDto(savedAuthor)).thenReturn(savedAuthorDto);
        return savedAuthorDto;
    }

    @Test
    public void givenExistingIdAndDto_whenUpdateAuthor_thenReturnUpdatedAuthorDto() {
        var inputDto = newDefaultAuthorDtoWithoutId();
        var expectedDto = setupMockedUpdateFlow(inputDto);

        var actualDto = service.updateAuthor(DEFAULT_ID, inputDto);

        assertEquals(expectedDto, actualDto);
        assertEquals(DEFAULT_ID, actualDto.getId());
    }

    private AuthorDto setupMockedUpdateFlow(AuthorDto inputDto) {
        var newFullName = "Lorem Ipsum";
        inputDto.setFullName(newFullName);
        var author = newDefaultAuthor();
        author.setFullName(newFullName);
        var expectedDto = newDefaultAuthorDto();
        expectedDto.setFullName(newFullName);
        when(repository.existsById(DEFAULT_ID)).thenReturn(true);
        when(mapper.toEntity(inputDto, DEFAULT_ID)).thenReturn(author);
        when(repository.save(author)).thenReturn(author);
        when(mapper.toDto(author)).thenReturn(expectedDto);
        return expectedDto;
    }

    @Test
    public void givenNonExistingId_whenUpdateAuthor_thenThrowEntityNotFoundException() {
        when(repository.existsById(DEFAULT_ID)).thenReturn(false);

        verifyExceptionThrownAndMethodsWerentCalled();
    }

    private void verifyExceptionThrownAndMethodsWerentCalled() {
        assertThrows(
                EntityNotFoundException.class,
                () -> service.updateAuthor(DEFAULT_ID, newDefaultAuthorDto()));
        verify(mapper, times(0)).toEntity(any(AuthorDto.class));
        verify(mapper, times(0)).toDto(any(Author.class));
        verify(repository, times(0)).save(any(Author.class));
    }

    @Test
    public void givenAnyId_whenDeleteAuthor_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> service.deleteAuthor(DEFAULT_ID));
        assertDoesNotThrow(() -> service.deleteAuthor(-1));
    }

}
