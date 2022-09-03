package pl.MuszKarol.DiscountAPI.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTORequest;
import pl.MuszKarol.DiscountAPI.dto.DiscountDTOResponse;
import pl.MuszKarol.DiscountAPI.exception.ImageNotFoundException;
import pl.MuszKarol.DiscountAPI.exception.InvalidImageExtensionException;
import pl.MuszKarol.DiscountAPI.service.DiscountService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @Operation(summary = "Get a list of discounts published last month")
    @ApiResponse(
            responseCode = "200",
            description = "Discounts posted last month were found",
            content = { @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = DiscountDTOResponse.class))
            ) }
    )
    @GetMapping(value = "/month")
    public ResponseEntity<List<DiscountDTOResponse>> getAllDiscountsNoOlderThanOneMonth() {
        return ResponseEntity.ok().body(
                discountService.takeDiscountsByDate(
                        Date.valueOf(LocalDate.now().minusMonths(1)),
                        Date.valueOf(LocalDate.now())
                ));
    }

    @Operation(summary = "Get a list of discounts published last week")
    @ApiResponse(
            responseCode = "200",
            description = "Discounts posted last week were found",
            content = { @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = DiscountDTOResponse.class))
            ) }
    )
    @GetMapping(value = "/week")
    public ResponseEntity<List<DiscountDTOResponse>> getAllDiscountsNoOlderThanOneWeek() {
        return ResponseEntity.ok().body(
                discountService.takeDiscountsByDate(
                        Date.valueOf(LocalDate.now().minusWeeks(1)),
                        Date.valueOf(LocalDate.now())
                ));
    }

    @Operation(summary = "Download the image using the passed parameters")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "An image with the specified parameters was found",
                    content = { @Content(
                        mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                        schema = @Schema(implementation = Resource.class)
                    ) }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Cannot find an image with the specified name",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "The image file extension is invalid",
                    content = @Content
            )
    })
    @GetMapping("/images")
    public ResponseEntity<Resource> downloadImage(
            @Parameter(
                    name = "imageName",
                    schema = @Schema(implementation = String.class),
                    description = "Use the received image name from the endpoint response"
            )
            @RequestParam String imageName,
            @Parameter(
                    name = "extension",
                    schema = @Schema(type = "string", allowableValues = {"jpg", "png", "gif"}),
                    description = "The most popular image file extensions are supported"
            )
            @RequestParam String extension
    ) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(discountService.getImageAsResource(imageName, extension));
        } catch (ImageNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (InvalidImageExtensionException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @Operation(summary = "New discount")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The image and discount details have been successfully sent",
                    content = { @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DiscountDTOResponse.class)
                    ) }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The image has not been saved",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "The image file extension is invalid",
                    content = @Content
            )
    })
    @PostMapping(value = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DiscountDTOResponse> saveDiscount(
            @RequestPart(value = "discount") @Valid DiscountDTORequest discountDTORequest,
            @RequestPart(value = "image") @Valid @NotNull @NotBlank MultipartFile image
    ) {
        try {
            return ResponseEntity.ok().body(discountService.saveDiscount(image, discountDTORequest));
        } catch (FileUploadException e) {
            return ResponseEntity.badRequest().build();
        } catch (InvalidImageExtensionException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
