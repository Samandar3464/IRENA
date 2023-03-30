package com.example.irena.service;

import com.example.irena.entity.AttachmentEntity;
import com.example.irena.entity.NewsEntity;
import com.example.irena.exception.RecordNotFountException;
import com.example.irena.model.request.NewsRegisterDto;
import com.example.irena.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .active(true)
                .happenDate(newsRegisterDto.getHappenDate())
                .newsBody(newsRegisterDto.getNewsBody())
                .titlePhoto(titlePhoto)
                .bodyPhoto(bodyPhoto)
                .build();
        return ResponseEntity.ok(newsRepository.save(newsEntity));
    }

    public ResponseEntity<?> getNewsList(int page, int size, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort).descending());
        List<NewsEntity> all = newsRepository.findAllByActive(true,pageRequest);
        return ResponseEntity.ok(all);
    }

    public ResponseEntity<?> deleteNews(Long id) {
        NewsEntity newsEntity = newsRepository.findById(id).orElseThrow(() -> new RecordNotFountException("News not found"));
        newsEntity.setActive(false);
        newsRepository.save(newsEntity);
        return ResponseEntity.ok("news deleted");
    }
    public ResponseEntity<?> getNewsById(Long id) {
        Optional<NewsEntity> byIdAndActive = newsRepository.findByIdAndActive(id, true);
        if (byIdAndActive.isEmpty()){
            throw  new RecordNotFountException("News not found");
        }
        return ResponseEntity.ok(byIdAndActive.get());
    }
}
