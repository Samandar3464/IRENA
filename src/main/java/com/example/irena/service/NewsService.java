package com.example.irena.service;

import com.example.irena.entity.AttachmentEntity;
import com.example.irena.entity.NewsEntity;
import com.example.irena.exception.RecordNotFountException;
import com.example.irena.model.NewsRegisterDto;
import com.example.irena.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final AttachmentForMediaService attachmentForMediaService;
    public ResponseEntity<?> addNews(NewsRegisterDto newsRegisterDto) {
        AttachmentEntity titlePhoto = attachmentForMediaService.saveToSystem(newsRegisterDto.getTitlePhoto());
        AttachmentEntity bodyPhoto = attachmentForMediaService.saveToSystem(newsRegisterDto.getBodyPhoto());
        NewsEntity newsEntity = NewsEntity.builder()
                .title(newsRegisterDto.getTittle())
                .newsBody(newsRegisterDto.getNewsBody())
                .titlePhoto(titlePhoto)
                .bodyPhoto(bodyPhoto)
                .build();
        return ResponseEntity.ok(newsRepository.save(newsEntity));
    }

    public ResponseEntity<?> getNewsList(int page, int size, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<NewsEntity> all = newsRepository.findAll(pageRequest);
        return ResponseEntity.ok(all);
    }

    public ResponseEntity<?> deleteNews(Long id) {
        newsRepository.findById(id).orElseThrow(() -> new RecordNotFountException("News not found"));
        newsRepository.deleteById(id);
        return ResponseEntity.ok("news deleted");
    }
}
