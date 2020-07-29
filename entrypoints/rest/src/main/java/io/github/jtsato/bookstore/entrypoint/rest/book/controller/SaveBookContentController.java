package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.RegisterBookRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.RegisterBookResponse;
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

@Tag(name = "Books")
@FunctionalInterface
public interface SaveBookContentController {

    @Operation(summary = "Save Book content")

    @Parameter(name =  "Accept-Language", example = "pt_BR", in = ParameterIn.HEADER, description = "Represents a specific geographical, political, or cultural region. Language & Country.")

    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusConstants.CREATED_201, description = HttpStatusConstants.CREATED_201_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.BAD_REQUEST_400, description  = HttpStatusConstants.BAD_REQUEST_400_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.INTERNAL_SERVER_ERROR_500, description  = HttpStatusConstants.INTERNAL_SERVER_ERROR_500_MESSAGE),})
    
    public RegisterBookResponse registerBook(final RegisterBookRequest request);
}
