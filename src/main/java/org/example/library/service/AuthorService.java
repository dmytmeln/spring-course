package org.example.library.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.library.domain.Author;
import org.example.library.dto.AuthorDto;
import org.example.library.mapper.AuthorMapper;
import org.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    public List<AuthorDto> getAllAuthors(String fullName) {
        return mapper.toDto(repository.findByFullNameContaining(fullName));
    }

    public Author getExistingAuthor(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Автора не знайдено!"));
    }

    public AuthorDto getExistingAuthorDto(Integer id) {
        return mapper.toDto(getExistingAuthor(id));
    }

    public AuthorDto createAuthor(AuthorDto dto) {
        var savedAuthor = repository.save(mapper.toEntity(dto));
        return mapper.toDto(savedAuthor);
    }

    public AuthorDto updateAuthor(Integer id, AuthorDto dto) {
        requireExistsById(id);
        var updatedAuthor = repository.save(mapper.toEntity(dto, id));
        return mapper.toDto(updatedAuthor);
    }

    private void requireExistsById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Автора не знайдено!");
        }
    }

    public void deleteAuthor(Integer id) {
        repository.deleteById(id);
    }

}
