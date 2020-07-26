package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

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
public interface RemoveAuthorByIdController {

    @Operation(summary = "Remove Author by Id")

    @Parameter(name =  "Accept-Language", example = "pt_BR", in = ParameterIn.HEADER, description = "Represents a specific geographical, political, or cultural region. Language & Country.")

    @ApiResponses(value = {@ApiResponse(responseCode = HttpStatusConstants.NO_CONTENT_204, description = HttpStatusConstants.NO_CONTENT_204_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.BAD_REQUEST_400, description  = HttpStatusConstants.BAD_REQUEST_400_MESSAGE),
                           @ApiResponse(responseCode = HttpStatusConstants.INTERNAL_SERVER_ERROR_500, description  = HttpStatusConstants.INTERNAL_SERVER_ERROR_500_MESSAGE),})

    void removeAuthorById(@Parameter(description = "Author Id") final Long id);
}
