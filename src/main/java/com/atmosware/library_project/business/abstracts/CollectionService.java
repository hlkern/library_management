package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.CollectionDTO;
import com.atmosware.library_project.business.dtos.CollectionResponse;
import com.atmosware.library_project.entities.Collection;

import java.util.List;

public interface CollectionService {
    Collection createCollection(CollectionDTO collectionDTO);

    void addBooksToCollection(Long collectionId, List<Long> bookIds);

    List<CollectionResponse> getUserCollections(Long userId);
}
