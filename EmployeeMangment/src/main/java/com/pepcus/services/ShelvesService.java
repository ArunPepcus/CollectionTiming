package com.pepcus.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pepcus.exception.ResourceNotFoundException;
import com.pepcus.models.Book;
import com.pepcus.models.Shelves;
import com.pepcus.repositorys.ShelvesRepository;

@Service
public class ShelvesService {

  @Autowired
  private ShelvesRepository shelvesRepository;
  
  public Shelves addBookInShelves(List<Book> books, Integer shelvesId) {
    Shelves existingShelves = shelvesRepository.findById(shelvesId)
        .orElseThrow(() -> new ResourceNotFoundException("Shelves id not found", "Id", shelvesId));

    List<Book> existBooks = existingShelves.getBookRelation();
    for (Book book : books) {
      book.setAddedOn(new Date());
      existBooks.add(book);

    }
    return shelvesRepository.save(existingShelves);

  }
}
