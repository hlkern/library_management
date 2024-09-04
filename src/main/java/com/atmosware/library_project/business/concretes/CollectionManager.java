package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.CollectionService;
import com.atmosware.library_project.business.dtos.CollectionDTO;
import com.atmosware.library_project.business.dtos.CollectionResponse;
import com.atmosware.library_project.business.messages.BusinessMessages;
import com.atmosware.library_project.core.utilities.exceptions.types.BusinessException;
import com.atmosware.library_project.core.utilities.mapping.CollectionMapper;
import com.atmosware.library_project.dataAccess.CollectionRepository;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.Collection;
import com.atmosware.library_project.entities.Book;
import com.atmosware.library_project.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.atmosware.library_project.business.messages.BusinessMessages.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class CollectionManager implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public Collection createCollection(CollectionDTO collectionDTO) {
        Collection collection = new Collection();
        collection.setName(collectionDTO.getName());
        collection.setUserId(collectionDTO.getUserId());

        return collectionRepository.save(collection);
    }

    @Override
    public void addBooksToCollection(Long collectionId, List<Long> bookIds) {
        Optional<Collection> optionalCollection = collectionRepository.findById(collectionId);
        if (optionalCollection.isPresent()) {
            Collection collection = optionalCollection.get();
            List<Book> existingBooksInCollection = collectionRepository.findBooksByCollectionId(collectionId);

            List<Book> booksToAdd = bookRepository.findAllById(bookIds);

            for (Book bookToAdd : booksToAdd) {
                List<Book> existingBooks = bookRepository.findByTitleAndAuthor(bookToAdd.getTitle(), bookToAdd.getAuthor());

                if (existingBooks.isEmpty() ||
                        existingBooks.stream().noneMatch(existingBook ->
                                existingBooksInCollection.stream().anyMatch(existingBookInCollection ->
                                        existingBookInCollection.getId().equals(existingBook.getId())))) {

                    collection.getBooks().add(bookToAdd);
                }
                else {
                    throw new RuntimeException("This book is already in the collection");
                }
            }

            collectionRepository.save(collection);
        } else {
            throw new RuntimeException("Collection not found");
        }
    }

    public List<CollectionResponse> getUserCollections(Long userId) {

        // Kullanıcıyı kontrol et
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessMessages.USER_NOT_FOUND));

        // Koleksiyonları kontrol et
        List<Collection> collections = collectionRepository.findByUserId(userId);

        if (collections.isEmpty()) {
            throw new BusinessException("No collections found for user with id: " + userId); //TODO düzelt
        }

        return collections.stream()
                .map(CollectionMapper.INSTANCE::mapToResponse)
                .collect(Collectors.toList());
    }
}
