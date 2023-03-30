package com.example.irena.service;

import com.example.irena.entity.AttachmentEntity;
import com.example.irena.entity.BookEntity;
import com.example.irena.exception.RecordNotFountException;
import com.example.irena.model.request.BookRegisterDto;
import com.example.irena.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final AttachmentForDocumentService attachmentForDocumentService;
    private final AttachmentForMediaService attachmentForMediaService;
    private final BookRepository bookRepository;

    public ResponseEntity<?> addBook(BookRegisterDto bookRegisterDto){
        AttachmentEntity bookPhoto = attachmentForMediaService.saveToSystem(bookRegisterDto.getBookPhoto());
        AttachmentEntity bodyPhoto = attachmentForMediaService.saveToSystem(bookRegisterDto.getBodyPhoto());
        AttachmentEntity bookDoc = attachmentForDocumentService.saveToSystem(bookRegisterDto.getBook());
        BookEntity book = BookEntity.builder()
                .ISBN(bookRegisterDto.getISBN())
                .title(bookRegisterDto.getTittle())
                .active(true)
                .description(bookRegisterDto.getDescription())
                .bookPhoto(bookPhoto)
                .bodyPhoto(bodyPhoto)
                .book(bookDoc)
                .build();
        return ResponseEntity.ok(bookRepository.save(book));
    }

    public ResponseEntity<?> getBook(int id) {
        return null;
    }

    public ResponseEntity<?> delete(int id) {
        BookEntity book = bookRepository.findById(id).orElseThrow(() -> new RecordNotFountException("Book not found"));
        book.setActive(false);
        bookRepository.save(book);
        return ResponseEntity.ok("Book deleted");
    }
}
