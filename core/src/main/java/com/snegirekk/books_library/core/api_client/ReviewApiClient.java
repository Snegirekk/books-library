package com.snegirekk.books_library.core.api_client;

import com.snegirekk.books_library.core.api_client.dto.NewReviewDto;
import com.snegirekk.books_library.core.dto.ExtendedReviewDto;
import com.snegirekk.books_library.core.dto.PageDto;
import com.snegirekk.books_library.core.dto.ReviewDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReviewApiClient {

    private final RestTemplate client;
    private final String url;
    private final ModelMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ReviewApiClient(RestTemplate client, String url, ModelMapper mapper) {
        this.client = client;
        this.url = url;
        this.mapper = mapper;
    }

    public ExtendedReviewDto retrieveReviewById(UUID reviewId) {
        try {
            return client.getForObject(url + "/review/" + reviewId, ExtendedReviewDto.class);
        } catch (RestClientException exception) {
            logger.error("Cannot retrieve a review with id {}. Service unavailable.", reviewId, exception);
            throw exception;
        }
    }

    public PageDto<ReviewDto> retrieveReviewsPageByBookId(UUID bookId, int pageNumber, int itemsPerPage, Sort sort) {
        if (sort.getOrderFor("createdAt") == null) {
            sort.and(Sort.by("createdAt").descending());
        }

        String sortString = sort.stream()
                .distinct()
                .map(order -> String.format("sort=%s,%s", order.getProperty(), order.getDirection()))
                .collect(Collectors.joining("&"));

        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(pageNumber));
        params.put("size", String.valueOf(itemsPerPage));
        params.put("bookId", bookId.toString());
        params.put("sort", sortString);

        PageDto<ReviewDto> page = new PageDto<>();

        try {
            return client.getForObject(
                    url + "/review?bookId={bookId}&page={page}&itemsPerPage={size}&{sort}",
                    (Class<PageDto<ReviewDto>>) page.getClass(),
                    params
            );
        } catch (RestClientException exception) {
            logger.error("Cannot retrieve a reviews page. Service unavailable.", exception);
            throw exception;
        }
    }

    public ExtendedReviewDto createReview(UUID bookId, ReviewDto reviewDto) {
        NewReviewDto newReview = mapper.map(reviewDto, NewReviewDto.class);
        newReview.setBookId(bookId);

        try {
            return client.postForObject(url + "/review", newReview, ExtendedReviewDto.class);
        } catch (RestClientException exception) {
            logger.error("Cannot create a review for book {}. Service unavailable.", bookId, exception);
            throw exception;
        }
    }
}
