package pl.musz.karol.discountapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.musz.karol.discountapi.dto.DiscountDTORequest;
import pl.musz.karol.discountapi.dto.DiscountDTOResponse;
import pl.musz.karol.discountapi.service.DiscountService;

import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @ApiResponse(
            responseCode = "400",
            description = "Incorrect data detected during validation"
    )
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body("Invalid attribute in request body: " + e.getParameter().getParameterType());
    }

    @Operation(summary = "Get all published discounts")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DiscountDTOResponse.class))
                    )
            }
    )
    @GetMapping(value = "/all")
    public ResponseEntity<Page<DiscountDTOResponse>> getAllDiscounts(
            @ParameterObject Pageable pageable,
            @RequestParam("product") Optional<String> productName) {
        return ResponseEntity.ok()
                .body(discountService.getAllDiscounts(pageable, productName));
    }


    @Operation(summary = "Get discounts published last month")
    @ApiResponse(
            responseCode = "200",
            description = "Discounts posted last month were found",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DiscountDTOResponse.class))
                    )
            }
    )
    @GetMapping(value = "/month")
    public ResponseEntity<Page<DiscountDTOResponse>> getAllDiscountsNoOlderThanOneMonth(
            @ParameterObject Pageable pageable,
            @RequestParam("product") Optional<String> productName) {
        return ResponseEntity.ok()
                .body(discountService.getAllDiscountsOfTheCurrentMonth(pageable, productName));
    }


    @Operation(summary = "Get discounts published last week")
    @ApiResponse(
            responseCode = "200",
            description = "Discounts posted last week were found",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = DiscountDTOResponse.class))
                    )
            }
    )
    @GetMapping(value = "/week")
    public ResponseEntity<Page<DiscountDTOResponse>> getAllDiscountsNoOlderThanOneWeek(
            @ParameterObject Pageable pageable,
            @RequestParam("product") Optional<String> productName) {
        return ResponseEntity.ok()
                .body(discountService.getAllDiscountsOfTheCurrentWeek(pageable, productName));
    }


    @Operation(summary = "Post a new discount")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "A new discount has been created.",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DiscountDTOResponse.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/new")
    public ResponseEntity<DiscountDTOResponse> saveDiscount(@Valid @RequestBody DiscountDTORequest discountDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(discountService.saveDiscount(discountDTORequest));
    }
}
