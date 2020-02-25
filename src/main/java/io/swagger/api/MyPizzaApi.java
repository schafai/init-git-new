package io.swagger.api;

import io.swagger.model.AllergensResponse;
import io.swagger.model.BasesResponse;
import io.swagger.model.IngredientsResponse;
import io.swagger.model.OrderRequest;
import io.swagger.model.OrderResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/MyPizza")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJAXRSSpecServerCodegen", date = "2020-02-24T14:58:34.673Z[GMT]")
public class MyPizzaApi {

    @GET
    @Path("/allergens")
    @Produces({ "application/json" })
    @Operation(summary = "", description = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = AllergensResponse.class)))
    })
    public Response findAllergens(  @QueryParam("sort") 

  String sort
,  @QueryParam("page") 

  Integer page
,  @QueryParam("per_page") 

  Integer perPage
,  @HeaderParam("Accept-Language") 

 String acceptLanguage
) {
        return Response.ok().entity("magic!").build();
    }
    @GET
    @Path("/base")
    @Produces({ "application/json" })
    @Operation(summary = "", description = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BasesResponse.class)))
    })
    public Response findBases(  @HeaderParam("Accept-Language") 

 String acceptLanguage
) {
        return Response.ok().entity("magic!").build();
    }
    @GET
    @Path("/ingredients")
    @Produces({ "application/json" })
    @Operation(summary = "", description = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = IngredientsResponse.class)))
    })
    public Response findIngredients(  @QueryParam("allergens") 

  List<String> allergens
,  @QueryParam("sort") 

  String sort
,  @QueryParam("page") 

  Integer page
,  @QueryParam("per_page") 

  Integer perPage
,  @HeaderParam("Accept-Language") 

 String acceptLanguage
) {
        return Response.ok().entity("magic!").build();
    }
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "", description = "", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = OrderResponse.class)))
    })
    public Response orderPizza(@Valid OrderRequest body,  @HeaderParam("Accept-Language") 

 String acceptLanguage
) {
        return Response.ok().entity("magic!").build();
    }}
