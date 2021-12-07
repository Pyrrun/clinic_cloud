package com.website.demo.API.visit.response;

import com.website.demo.API.visit.VisitDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnfinishedVisitResponse extends VisitResponse {

    private VisitDto visit;
}
