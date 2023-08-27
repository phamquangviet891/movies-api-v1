package com.example.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

//import java.util.function.BiFunction;

//import static org.springframework.data.mongodb.core.query.UntypedExampleMatcher.matching;

@Service
public class ReviewService {
    @Autowired
    private ReviewReponsitory ReviewReponsitory;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview(String reviewBody, String ImdbId ) {
        Review review = ReviewReponsitory.insert(new Review(reviewBody));

        mongoTemplate.update(Movie.class)
            .matching(Criteria.where("imdbId").is(ImdbId))
            .apply(new Update().push("reviewIds").value(review))
            .first();

        return review;
     }




}
