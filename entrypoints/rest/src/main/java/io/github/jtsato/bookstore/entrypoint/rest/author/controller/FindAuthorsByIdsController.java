package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.FindAuthorsByIdsRequest;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.FindAuthorsByIdsResponse;
import io.github.jtsato.bookstore.entrypoint.rest.common.HttpStatusConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Jorge Takeshi Sato  
 */

@Tag(name = "Authors")
@FunctionalInterface
public interface FindAuthorsByIdsController {

    @Operation(summary = "Find Authors by IDs")

    @Parameter(name = "Accept-Language",
               example = "pt_BR",
               in = ParameterIn.HEADER,
               description = "Represents a specific geographical, political, or cultural region. Language & Country.")

    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusConstants.OK_200, description = HttpStatusConstants.OK_200_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.BAD_REQUEST_400, description = HttpStatusConstants.BAD_REQUEST_400_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.NOT_FOUND_404, description = HttpStatusConstants.NOT_FOUND_404_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.INTERNAL_SERVER_ERROR_500,
                                        description = HttpStatusConstants.INTERNAL_SERVER_ERROR_500_MESSAGE),})
    FindAuthorsByIdsResponse findAuthorsByIds(@Parameter(description = "Authors Ids") final FindAuthorsByIdsRequest findAuthorsByIdsRequest);
}
