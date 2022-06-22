package com.pepcus.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pepcus.models.Book;
import com.pepcus.models.User;
import com.pepcus.services.UserServices;

/**
 * @author admin
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {
  
  @Autowired
  private UserServices userService;
  

  /**
   * @param user
   * @return
   */
  @PostMapping
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
  }
  
  

  @PutMapping
  public ResponseEntity<String> issueBook(@Valid @RequestBody List<Book> book, @RequestParam Integer userId) {

    return new ResponseEntity<String>(userService.issueBook(userId, book), HttpStatus.CREATED);
  }

  @PatchMapping("/deactivate")
  public ResponseEntity<String> deActivate(@Valid @RequestParam Integer userId) {
    return new ResponseEntity<String>(userService.deActivated(userId), HttpStatus.OK);
  }

  @PatchMapping("/activate")
  public ResponseEntity<String> activateUser(@Valid @RequestParam Integer userId) {
    return new ResponseEntity<String>(userService.activateUser(userId), HttpStatus.OK);
  }

//  /*
//   * this handler for return book from user
//   */
//  @DeleteMapping
//  public ResponseEntity<String> returnBookFromUser(@Valid @RequestBody List<Book> book, @RequestParam Integer userId) {
//    return new ResponseEntity<String>(userService.returnBooks(userId, book), HttpStatus.OK);
//  }

}
