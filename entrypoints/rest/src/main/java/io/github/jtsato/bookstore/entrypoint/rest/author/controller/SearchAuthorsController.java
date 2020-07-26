package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;

import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.SearchAuthorsRequest;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.SearchAuthorsResponse;
import io.github.jtsato.bookstore.entrypoint.rest.common.HttpStatusConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Jorge Takeshi Sato  
 */

@Tag(name = "Authors")
@FunctionalInterface
public interface SearchAuthorsController {

    @Operation(summary = "Search Authors")

    @Parameter(name =  "Accept-Language", example = "pt_BR", in = ParameterIn.HEADER, description = "Represents a specific geographical, political, or cultural region. Language & Country.")

    @PageableAsQueryParam
    
    @Parameter(in = ParameterIn.QUERY, name = "id", description = "Author id that need to be considered for filter.", content = @Content(schema = @Schema(type = "long")))
    @Parameter(in = ParameterIn.QUERY, name = "name", description = "Author name that need to be considered for filter.", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.QUERY, name = "gender", description = "Author gender that need to be considered for filter.", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.QUERY, name = "startBirthday", description = "Filters author's birthday after the specified date. Format: YYYY-MM-DD", content = @Content(schema = @Schema(type = "string")))
    @Parameter(in = ParameterIn.QUERY, name = "endBirthday", description = "Filters author's birthday before the specified date. Format: YYYY-MM-DD", content = @Content(schema = @Schema(type = "string")))  
    
    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusConstants.OK_200, description = HttpStatusConstants.OK_200_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.BAD_REQUEST_400, description = HttpStatusConstants.BAD_REQUEST_400_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.NOT_FOUND_404, description = HttpStatusConstants.NOT_FOUND_404_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.INTERNAL_SERVER_ERROR_500, description = HttpStatusConstants.INTERNAL_SERVER_ERROR_500_MESSAGE),})
    
    public SearchAuthorsResponse searchAuthors(@Parameter(hidden = true) final Pageable pageable, @Parameter(hidden = true) final SearchAuthorsRequest searchAuthorsRequest);
}
