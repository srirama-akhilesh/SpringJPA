package com.loya.onetomanybidirectionaldemo.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loya.onetomanybidirectionaldemo.dao.AuthorDao;
import com.loya.onetomanybidirectionaldemo.dao.BookDao;
import com.loya.onetomanybidirectionaldemo.entity.Author;
import com.loya.onetomanybidirectionaldemo.entity.Book;
import com.loya.onetomanybidirectionaldemo.exception.ResourceNotFoundException;
import com.loya.onetomanybidirectionaldemo.service.AuthorService;
import com.loya.onetomanybidirectionaldemo.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;


@RestController
@Api(value="Library")
@Validated
public class AuthorBookController {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;
    
    @Autowired 
    private AuthorDao authorDao;

    @Autowired
    BookDao bookDao;
    
    
    @RequestMapping(value = "/getAllAuthors", method = RequestMethod.GET)
    @ApiOperation(value = "View a list of available Authors", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            })
    @Cacheable(value= "authors")
    public List<Author> getAuthors() throws InterruptedException  {
    	
    	
        return authorService.getAuthors();
    }

    //
    @RequestMapping(value = "/author", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create an author", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created an author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Author createAuthor(@ApiParam(value = "First name, Last name  are required for creating new Author record", required = true)@Valid @RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @RequestMapping(value = "/author/{authorId}", method = RequestMethod.GET)
    @ApiOperation(value = "Find an author by Id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Author"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<Author> getAuthorById(@PathVariable(value = "authorId") @Min(1) Long authorId) throws ResourceNotFoundException{
        
    	Author author1= authorDao.findById(authorId).orElseThrow(()-> new ResourceNotFoundException("Author doesn't exists: " + authorId));
    			
    	return (ResponseEntity<Author>) ResponseEntity.ok().body(author1);
    }

    @RequestMapping(value = "/updateAuthor/{authorId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Author updateAuthor(@ApiParam(value = "AuthorID that need to be updated", required = true)@PathVariable(value = "authorId") @Min(1) Long authorId, @RequestBody Author author) {
        return authorService.updateAuthorById(authorId, author);
    }

    @RequestMapping(value = "/deleteAuthor/{authorId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAuthorById(@ApiParam(value = "AuthorID that need to be deleted", required = true)@PathVariable(value = "authorId") long authorId) {
        return authorService.deleteAuthorById(authorId);
    }

    @RequestMapping(value = "/getAllBooks", method = RequestMethod.GET)
    public List<Book> getBooks() {
    	
        return bookService.getAllBooks();
    }


    //
    @RequestMapping(value = "/{authorId}/book", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book createBook(@ApiParam(value = "Title, genre, AuthorID parameters are required for creating new Book record", required = true)@PathVariable(value = "authorId") Long authorId, @RequestBody Book book) {
        return bookService.createBook(authorId, book);
    }

    @RequestMapping(value = "/book/{bookId}", method = RequestMethod.GET)
    @ApiOperation(value = "Find a Book by Id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Book"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    })
    public ResponseEntity<Book> getBookById(@PathVariable(value = "bookId") Long bookId) throws ResourceNotFoundException{
    	
    	Book book1= bookDao.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book doesn't exists::"+ bookId));
    	
        return ResponseEntity.ok().body(book1);
    }


    @RequestMapping(value = "/book", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book updateBook(@ApiParam(value = "BookID that needs to be updated", required = true)@PathVariable(value = "bookId") Long bookId, @ApiParam(value = "Updated Book object", required = true)@RequestBody Book book) {
        return bookService.updateBookById(bookId, book);
    }

    @RequestMapping(value = "/deleteBook/{bookId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "To delete a Book",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted Book from list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    }
    )
    public ResponseEntity<Object> deleteBookById(@PathVariable(value = "bookId") long bookId) {
        return bookService.deleteBookById(bookId);
    }
 
    @RequestMapping(value = "/getBooksByGenre", method = RequestMethod.GET)
    @ApiOperation(value = "View a list of available books by genre", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            })
   public ResponseEntity<Book> findByGenre() {
    	
    	
    	return bookService.findByGenre();
   }

}